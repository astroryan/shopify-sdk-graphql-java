package com.shopify.sdk.session;

import com.shopify.sdk.auth.AccessTokenResponse;
import com.shopify.sdk.auth.JwtClaims;
import com.shopify.sdk.auth.JwtTokenValidator;
import com.shopify.sdk.exception.ShopifyApiException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.time.Instant;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

/**
 * Service for managing Shopify sessions.
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class SessionManager {
    
    private final SessionStore sessionStore;
    private final JwtTokenValidator jwtTokenValidator;
    
    /**
     * Creates a new session from an access token response.
     *
     * @param shop the shop domain
     * @param accessTokenResponse the access token response
     * @param isOnline whether this is an online session
     * @return Mono containing the created session
     */
    public Mono<ShopifySession> createSession(String shop, AccessTokenResponse accessTokenResponse, boolean isOnline) {
        return Mono.fromSupplier(() -> {
            ShopifySession.ShopifySessionBuilder sessionBuilder = ShopifySession.builder()
                .shop(normalizeShop(shop))
                .accessToken(accessTokenResponse.getAccessToken())
                .isOnline(isOnline)
                .createdAt(Instant.now())
                .updatedAt(Instant.now());
            
            // Parse scopes
            if (accessTokenResponse.getScope() != null) {
                String[] scopes = accessTokenResponse.getScope().split(",");
                sessionBuilder.scope(new HashSet<>(Arrays.asList(scopes)));
            }
            
            // Set user information for online sessions
            if (isOnline && accessTokenResponse.getAssociatedUser() != null) {
                AccessTokenResponse.AssociatedUser user = accessTokenResponse.getAssociatedUser();
                sessionBuilder
                    .userId(user.getId())
                    .userEmail(user.getEmail())
                    .userFirstName(user.getFirstName())
                    .userLastName(user.getLastName())
                    .userEmailVerified(user.getEmailVerified())
                    .accountOwner(user.getAccountOwner())
                    .collaborator(user.getCollaborator())
                    .userLocale(user.getLocale());
                    
                // Set expiration for online tokens (typically 24 hours)
                sessionBuilder.expiresAt(Instant.now().plusSeconds(86400));
            }
            
            return sessionBuilder.build();
        })
        .flatMap(session -> {
            session.setId(ShopifySession.createSessionId(session.getShop(), session.isOnline(), session.getUserId()));
            return sessionStore.storeSession(session)
                .thenReturn(session);
        });
    }
    
    /**
     * Creates a session from a JWT token.
     *
     * @param jwtToken the JWT token
     * @param accessToken the access token (optional, can be extracted later)
     * @return Mono containing the created session
     */
    public Mono<ShopifySession> createSessionFromJwt(String jwtToken, String accessToken) {
        return Mono.fromSupplier(() -> jwtTokenValidator.validateToken(jwtToken))
            .map(jwtClaims -> {
                ShopifySession.ShopifySessionBuilder sessionBuilder = ShopifySession.builder()
                    .shop(normalizeShop(jwtClaims.getShop()))
                    .isOnline(jwtClaims.getUserId() != null)
                    .createdAt(Instant.now())
                    .updatedAt(Instant.now());
                
                if (accessToken != null) {
                    sessionBuilder.accessToken(accessToken);
                }
                
                if (jwtClaims.getUserId() != null) {
                    sessionBuilder
                        .userId(jwtClaims.getUserId())
                        .expiresAt(jwtClaims.getExpiration());
                }
                
                ShopifySession session = sessionBuilder.build();
                session.setId(ShopifySession.createSessionId(session.getShop(), session.isOnline(), session.getUserId()));
                
                return session;
            })
            .flatMap(session -> sessionStore.storeSession(session).thenReturn(session));
    }
    
    /**
     * Gets a session by ID.
     *
     * @param sessionId the session ID
     * @return Mono containing the session if found and valid
     */
    public Mono<ShopifySession> getSession(String sessionId) {
        return sessionStore.loadSession(sessionId)
            .map(optionalSession -> optionalSession.orElse(null))
            .filter(session -> session != null && session.isValid());
    }
    
    /**
     * Gets a session for a shop and user (for online sessions) or just shop (for offline sessions).
     *
     * @param shop the shop domain
     * @param isOnline whether to look for online session
     * @param userId the user ID (required for online sessions)
     * @return Mono containing the session if found and valid
     */
    public Mono<ShopifySession> getSessionForShop(String shop, boolean isOnline, Long userId) {
        String sessionId = ShopifySession.createSessionId(normalizeShop(shop), isOnline, userId);
        return getSession(sessionId);
    }
    
    /**
     * Gets the offline session for a shop.
     *
     * @param shop the shop domain
     * @return Mono containing the offline session if found and valid
     */
    public Mono<ShopifySession> getOfflineSession(String shop) {
        return getSessionForShop(shop, false, null);
    }
    
    /**
     * Gets all sessions for a shop.
     *
     * @param shop the shop domain
     * @return Mono containing list of sessions for the shop
     */
    public Mono<List<ShopifySession>> getSessionsForShop(String shop) {
        return sessionStore.findSessionsByShop(normalizeShop(shop));
    }
    
    /**
     * Updates a session.
     *
     * @param session the session to update
     * @return Mono that completes when the session is updated
     */
    public Mono<Void> updateSession(ShopifySession session) {
        return sessionStore.updateSession(session);
    }
    
    /**
     * Deletes a session.
     *
     * @param sessionId the session ID
     * @return Mono that completes when the session is deleted
     */
    public Mono<Void> deleteSession(String sessionId) {
        return sessionStore.deleteSession(sessionId);
    }
    
    /**
     * Deletes all sessions for a shop.
     *
     * @param shop the shop domain
     * @return Mono that completes when all sessions are deleted
     */
    public Mono<Void> deleteSessionsForShop(String shop) {
        return sessionStore.deleteSessionsByShop(normalizeShop(shop));
    }
    
    /**
     * Validates a session and returns it if valid.
     *
     * @param sessionId the session ID
     * @return Mono containing the session if valid, or error if invalid
     */
    public Mono<ShopifySession> validateSession(String sessionId) {
        return getSession(sessionId)
            .switchIfEmpty(Mono.error(new ShopifyApiException("Invalid or expired session: " + sessionId)));
    }
    
    /**
     * Validates that a session has the required scopes.
     *
     * @param session the session to validate
     * @param requiredScopes the required scopes
     * @return Mono containing the session if valid, or error if insufficient scopes
     */
    public Mono<ShopifySession> validateSessionScopes(ShopifySession session, List<String> requiredScopes) {
        if (session.hasAllScopes(requiredScopes)) {
            return Mono.just(session);
        } else {
            return Mono.error(new ShopifyApiException("Insufficient scopes. Required: " + requiredScopes + 
                ", Available: " + session.getScope()));
        }
    }
    
    /**
     * Refreshes a session's updated timestamp.
     *
     * @param sessionId the session ID
     * @return Mono that completes when the session is refreshed
     */
    public Mono<Void> touchSession(String sessionId) {
        return getSession(sessionId)
            .doOnNext(ShopifySession::touch)
            .flatMap(this::updateSession);
    }
    
    /**
     * Cleans up expired sessions.
     *
     * @return Mono containing the number of deleted sessions
     */
    public Mono<Long> cleanupExpiredSessions() {
        return sessionStore.deleteExpiredSessions();
    }
    
    /**
     * Gets session statistics.
     *
     * @return Mono containing session statistics
     */
    public Mono<SessionStats> getSessionStats() {
        return Mono.zip(
            sessionStore.getSessionCount(),
            sessionStore.findActiveSessions().map(sessions -> (long) sessions.size()),
            sessionStore.deleteExpiredSessions()
        ).map(tuple -> SessionStats.builder()
            .totalSessions(tuple.getT1())
            .activeSessions(tuple.getT2())
            .expiredSessions(tuple.getT3())
            .build());
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
    
    /**
     * Session statistics data class.
     */
    @lombok.Data
    @lombok.Builder
    @lombok.NoArgsConstructor
    @lombok.AllArgsConstructor
    public static class SessionStats {
        private Long totalSessions;
        private Long activeSessions;
        private Long expiredSessions;
    }
}