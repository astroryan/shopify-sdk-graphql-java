package com.shopify.sdk.session;

import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Optional;

/**
 * Interface for storing and retrieving Shopify sessions.
 */
public interface SessionStore {
    
    /**
     * Stores a session.
     *
     * @param session the session to store
     * @return Mono that completes when the session is stored
     */
    Mono<Void> storeSession(ShopifySession session);
    
    /**
     * Retrieves a session by ID.
     *
     * @param sessionId the session ID
     * @return Mono containing the session, or empty if not found
     */
    Mono<Optional<ShopifySession>> loadSession(String sessionId);
    
    /**
     * Deletes a session by ID.
     *
     * @param sessionId the session ID
     * @return Mono that completes when the session is deleted
     */
    Mono<Void> deleteSession(String sessionId);
    
    /**
     * Deletes all sessions for a specific shop.
     *
     * @param shop the shop domain
     * @return Mono that completes when all sessions are deleted
     */
    Mono<Void> deleteSessionsByShop(String shop);
    
    /**
     * Finds sessions by shop domain.
     *
     * @param shop the shop domain
     * @return Mono containing list of sessions for the shop
     */
    Mono<List<ShopifySession>> findSessionsByShop(String shop);
    
    /**
     * Finds all active sessions (non-expired).
     *
     * @return Mono containing list of active sessions
     */
    Mono<List<ShopifySession>> findActiveSessions();
    
    /**
     * Deletes expired sessions.
     *
     * @return Mono containing the number of deleted sessions
     */
    Mono<Long> deleteExpiredSessions();
    
    /**
     * Checks if a session exists.
     *
     * @param sessionId the session ID
     * @return Mono containing true if exists, false otherwise
     */
    Mono<Boolean> sessionExists(String sessionId);
    
    /**
     * Updates an existing session.
     *
     * @param session the session to update
     * @return Mono that completes when the session is updated
     */
    Mono<Void> updateSession(ShopifySession session);
    
    /**
     * Gets the total number of sessions.
     *
     * @return Mono containing the session count
     */
    Mono<Long> getSessionCount();
    
    /**
     * Gets the number of sessions for a specific shop.
     *
     * @param shop the shop domain
     * @return Mono containing the session count for the shop
     */
    Mono<Long> getSessionCountByShop(String shop);
}