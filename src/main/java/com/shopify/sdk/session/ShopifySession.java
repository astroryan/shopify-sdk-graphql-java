package com.shopify.sdk.session;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.Instant;
import java.util.List;
import java.util.Set;

/**
 * Represents a Shopify session containing authentication and shop information.
 */
@Data
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
public class ShopifySession implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    /**
     * Unique session identifier.
     */
    @JsonProperty("id")
    private String id;
    
    /**
     * The shop domain.
     */
    @JsonProperty("shop")
    private String shop;
    
    /**
     * The access token for API requests.
     */
    @JsonProperty("accessToken")
    private String accessToken;
    
    /**
     * The granted scopes for this session.
     */
    @JsonProperty("scope")
    private Set<String> scope;
    
    /**
     * Whether this is an online access token.
     */
    @JsonProperty("isOnline")
    private boolean isOnline;
    
    /**
     * The user ID for online access tokens.
     */
    @JsonProperty("userId")
    private Long userId;
    
    /**
     * The user email for online access tokens.
     */
    @JsonProperty("userEmail")
    private String userEmail;
    
    /**
     * The user first name for online access tokens.
     */
    @JsonProperty("userFirstName")
    private String userFirstName;
    
    /**
     * The user last name for online access tokens.
     */
    @JsonProperty("userLastName")
    private String userLastName;
    
    /**
     * Whether the user's email is verified.
     */
    @JsonProperty("userEmailVerified")
    private Boolean userEmailVerified;
    
    /**
     * Whether the user is the account owner.
     */
    @JsonProperty("accountOwner")
    private Boolean accountOwner;
    
    /**
     * Whether the user is a collaborator.
     */
    @JsonProperty("collaborator")
    private Boolean collaborator;
    
    /**
     * The user's locale.
     */
    @JsonProperty("userLocale")
    private String userLocale;
    
    /**
     * When the session was created.
     */
    @JsonProperty("createdAt")
    private Instant createdAt;
    
    /**
     * When the session was last updated.
     */
    @JsonProperty("updatedAt")
    private Instant updatedAt;
    
    /**
     * When the session expires (for online tokens).
     */
    @JsonProperty("expiresAt")
    private Instant expiresAt;
    
    /**
     * Additional session metadata.
     */
    @JsonProperty("metadata")
    private java.util.Map<String, Object> metadata;
    
    /**
     * Checks if the session is valid and not expired.
     *
     * @return true if valid, false otherwise
     */
    @JsonIgnore
    public boolean isValid() {
        if (accessToken == null || accessToken.trim().isEmpty()) {
            return false;
        }
        
        if (shop == null || shop.trim().isEmpty()) {
            return false;
        }
        
        if (isOnline && expiresAt != null && expiresAt.isBefore(Instant.now())) {
            return false;
        }
        
        return true;
    }
    
    /**
     * Checks if the session is expired.
     *
     * @return true if expired, false otherwise
     */
    @JsonIgnore
    public boolean isExpired() {
        return isOnline && expiresAt != null && expiresAt.isBefore(Instant.now());
    }
    
    /**
     * Gets the remaining time until expiration in seconds.
     *
     * @return seconds until expiration, or -1 if no expiration
     */
    @JsonIgnore
    public long getSecondsUntilExpiration() {
        if (expiresAt == null) {
            return -1;
        }
        return expiresAt.getEpochSecond() - Instant.now().getEpochSecond();
    }
    
    /**
     * Checks if the session has a specific scope.
     *
     * @param scopeName the scope to check
     * @return true if the scope is granted, false otherwise
     */
    public boolean hasScope(String scopeName) {
        return scope != null && scope.contains(scopeName);
    }
    
    /**
     * Checks if the session has all the specified scopes.
     *
     * @param requiredScopes the scopes to check
     * @return true if all scopes are granted, false otherwise
     */
    public boolean hasAllScopes(List<String> requiredScopes) {
        if (scope == null || requiredScopes == null) {
            return false;
        }
        return scope.containsAll(requiredScopes);
    }
    
    /**
     * Gets the session type as a string.
     *
     * @return "online" or "offline"
     */
    @JsonIgnore
    public String getSessionType() {
        return isOnline ? "online" : "offline";
    }
    
    /**
     * Creates a session ID based on shop and user information.
     *
     * @param shop the shop domain
     * @param isOnline whether this is an online session
     * @param userId the user ID (for online sessions)
     * @return the session ID
     */
    public static String createSessionId(String shop, boolean isOnline, Long userId) {
        if (shop == null) {
            throw new IllegalArgumentException("Shop cannot be null");
        }
        
        String normalizedShop = shop.toLowerCase()
            .replace("https://", "")
            .replace("http://", "")
            .replace(".myshopify.com", "");
            
        if (isOnline && userId != null) {
            return String.format("online_%s_%d", normalizedShop, userId);
        } else {
            return String.format("offline_%s", normalizedShop);
        }
    }
    
    /**
     * Updates the session's updated timestamp.
     */
    public void touch() {
        this.updatedAt = Instant.now();
    }
    
    /**
     * Adds or updates metadata.
     *
     * @param key the metadata key
     * @param value the metadata value
     */
    public void setMetadata(String key, Object value) {
        if (metadata == null) {
            metadata = new java.util.HashMap<>();
        }
        metadata.put(key, value);
        touch();
    }
    
    /**
     * Gets metadata value.
     *
     * @param key the metadata key
     * @return the metadata value or null if not found
     */
    public Object getMetadata(String key) {
        return metadata != null ? metadata.get(key) : null;
    }
    
    /**
     * Gets metadata value as a specific type.
     *
     * @param key the metadata key
     * @param clazz the expected type
     * @param <T> the type parameter
     * @return the metadata value cast to the specified type, or null if not found or wrong type
     */
    @SuppressWarnings("unchecked")
    public <T> T getMetadata(String key, Class<T> clazz) {
        Object value = getMetadata(key);
        if (value != null && clazz.isAssignableFrom(value.getClass())) {
            return (T) value;
        }
        return null;
    }
}