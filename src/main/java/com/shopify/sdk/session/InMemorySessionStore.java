package com.shopify.sdk.session;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

/**
 * In-memory implementation of SessionStore.
 * This is suitable for development and single-instance deployments.
 * For production deployments with multiple instances, consider using a Redis-based implementation.
 */
@Slf4j
@Component
@ConditionalOnMissingBean(SessionStore.class)
public class InMemorySessionStore implements SessionStore {
    
    private static final int MAX_SESSIONS = 10000; // Maximum number of sessions to prevent unbounded growth
    private static final long CLEANUP_INTERVAL_MS = 5 * 60 * 1000; // 5 minutes
    
    private final ConcurrentHashMap<String, ShopifySession> sessions = new ConcurrentHashMap<>();
    private final AtomicInteger sessionCount = new AtomicInteger(0);
    private final ScheduledExecutorService cleanupExecutor = new ScheduledThreadPoolExecutor(1);
    
    @PostConstruct
    public void init() {
        // Schedule automatic cleanup of expired sessions
        cleanupExecutor.scheduleWithFixedDelay(
            this::cleanupExpiredSessions,
            CLEANUP_INTERVAL_MS,
            CLEANUP_INTERVAL_MS,
            TimeUnit.MILLISECONDS
        );
        log.info("InMemorySessionStore initialized with automatic cleanup every {} minutes", 
                 CLEANUP_INTERVAL_MS / 60000);
    }
    
    @PreDestroy
    public void destroy() {
        cleanupExecutor.shutdown();
        try {
            if (!cleanupExecutor.awaitTermination(5, TimeUnit.SECONDS)) {
                cleanupExecutor.shutdownNow();
            }
        } catch (InterruptedException e) {
            cleanupExecutor.shutdownNow();
            Thread.currentThread().interrupt();
        }
        log.info("InMemorySessionStore cleanup executor shut down");
    }
    
    @Override
    public Mono<Void> storeSession(ShopifySession session) {
        return Mono.fromRunnable(() -> {
            // Check if we've hit the maximum session limit
            if (sessionCount.get() >= MAX_SESSIONS) {
                // Force cleanup of expired sessions
                cleanupExpiredSessions();
                
                // If still at limit, reject new session
                if (sessionCount.get() >= MAX_SESSIONS) {
                    throw new IllegalStateException(
                        "Session store has reached maximum capacity of " + MAX_SESSIONS + " sessions"
                    );
                }
            }
            
            if (session.getId() == null) {
                session.setId(ShopifySession.createSessionId(session.getShop(), session.isOnline(), session.getUserId()));
            }
            
            if (session.getCreatedAt() == null) {
                session.setCreatedAt(Instant.now());
            }
            session.setUpdatedAt(Instant.now());
            
            ShopifySession existing = sessions.put(session.getId(), session);
            if (existing == null) {
                sessionCount.incrementAndGet();
            }
            
            log.debug("Stored session: {} (total sessions: {})", session.getId(), sessionCount.get());
        });
    }
    
    @Override
    public Mono<Optional<ShopifySession>> loadSession(String sessionId) {
        return Mono.fromSupplier(() -> {
            ShopifySession session = sessions.get(sessionId);
            if (session != null && session.isValid()) {
                log.debug("Loaded session: {}", sessionId);
                return Optional.of(session);
            } else if (session != null && session.isExpired()) {
                // Clean up expired session
                sessions.remove(sessionId);
                log.debug("Removed expired session: {}", sessionId);
            }
            return Optional.empty();
        });
    }
    
    @Override
    public Mono<Void> deleteSession(String sessionId) {
        return Mono.fromRunnable(() -> {
            ShopifySession removed = sessions.remove(sessionId);
            if (removed != null) {
                sessionCount.decrementAndGet();
                log.debug("Deleted session: {} (total sessions: {})", sessionId, sessionCount.get());
            }
        });
    }
    
    @Override
    public Mono<Void> deleteSessionsByShop(String shop) {
        return Mono.fromRunnable(() -> {
            String normalizedShop = normalizeShop(shop);
            
            List<String> sessionsToDelete = sessions.values().stream()
                .filter(session -> normalizedShop.equals(normalizeShop(session.getShop())))
                .map(ShopifySession::getId)
                .collect(Collectors.toList());
                
            for (String sessionId : sessionsToDelete) {
                sessions.remove(sessionId);
                sessionCount.decrementAndGet();
            }
            
            log.debug("Deleted {} sessions for shop: {}", sessionsToDelete.size(), shop);
        });
    }
    
    @Override
    public Mono<List<ShopifySession>> findSessionsByShop(String shop) {
        return Mono.fromSupplier(() -> {
            String normalizedShop = normalizeShop(shop);
            
            return sessions.values().stream()
                .filter(session -> normalizedShop.equals(normalizeShop(session.getShop())))
                .filter(ShopifySession::isValid)
                .collect(Collectors.toList());
        });
    }
    
    @Override
    public Mono<List<ShopifySession>> findActiveSessions() {
        return Mono.fromSupplier(() -> sessions.values().stream()
            .filter(ShopifySession::isValid)
            .collect(Collectors.toList()));
    }
    
    @Override
    public Mono<Long> deleteExpiredSessions() {
        return Mono.fromSupplier(() -> {
            List<String> expiredSessions = sessions.values().stream()
                .filter(ShopifySession::isExpired)
                .map(ShopifySession::getId)
                .collect(Collectors.toList());
                
            for (String sessionId : expiredSessions) {
                sessions.remove(sessionId);
                sessionCount.decrementAndGet();
            }
            
            log.debug("Deleted {} expired sessions", expiredSessions.size());
            return (long) expiredSessions.size();
        });
    }
    
    @Override
    public Mono<Boolean> sessionExists(String sessionId) {
        return loadSession(sessionId)
            .map(Optional::isPresent);
    }
    
    @Override
    public Mono<Void> updateSession(ShopifySession session) {
        return Mono.fromRunnable(() -> {
            if (session.getId() != null && sessions.containsKey(session.getId())) {
                session.setUpdatedAt(Instant.now());
                sessions.put(session.getId(), session);
                log.debug("Updated session: {}", session.getId());
            } else {
                log.warn("Attempted to update non-existent session: {}", session.getId());
            }
        });
    }
    
    @Override
    public Mono<Long> getSessionCount() {
        return Mono.fromSupplier(() -> (long) sessions.size());
    }
    
    @Override
    public Mono<Long> getSessionCountByShop(String shop) {
        return findSessionsByShop(shop)
            .map(sessions -> (long) sessions.size());
    }
    
    /**
     * Gets all sessions (for debugging/monitoring).
     *
     * @return all sessions
     */
    public List<ShopifySession> getAllSessions() {
        return sessions.values().stream()
            .collect(Collectors.toList());
    }
    
    /**
     * Clears all sessions (for testing).
     */
    public void clearAllSessions() {
        sessions.clear();
        sessionCount.set(0);
        log.debug("Cleared all sessions");
    }
    
    /**
     * Scheduled cleanup of expired sessions.
     */
    @Scheduled(fixedDelay = CLEANUP_INTERVAL_MS)
    public void cleanupExpiredSessions() {
        deleteExpiredSessions()
            .subscribe(count -> {
                if (count > 0) {
                    log.info("Cleaned up {} expired sessions (remaining: {})", count, sessionCount.get());
                }
            });
    }
    
    /**
     * Gets current session count.
     */
    public int getCurrentSessionCount() {
        return sessionCount.get();
    }
    
    private String normalizeShop(String shop) {
        if (shop == null) {
            return null;
        }
        
        return shop.toLowerCase()
            .replace("https://", "")
            .replace("http://", "")
            .replace(".myshopify.com", "");
    }
}