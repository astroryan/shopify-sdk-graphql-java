package com.shopify.sdk.auth;

import io.jsonwebtoken.Claims;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.Date;
import java.util.Map;

/**
 * Represents the claims contained in a Shopify JWT token.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class JwtClaims {
    
    /**
     * The issuer of the token (shop domain).
     */
    private String issuer;
    
    /**
     * The subject of the token (user ID).
     */
    private String subject;
    
    /**
     * The audience of the token (API key).
     */
    private String audience;
    
    /**
     * The expiration time of the token.
     */
    private Instant expiration;
    
    /**
     * The issued at time of the token.
     */
    private Instant issuedAt;
    
    /**
     * The not before time of the token.
     */
    private Instant notBefore;
    
    /**
     * The JWT ID.
     */
    private String jwtId;
    
    /**
     * The destination URL from the token.
     */
    private String destination;
    
    /**
     * The shop domain extracted from the issuer.
     */
    private String shop;
    
    /**
     * The user ID as a Long.
     */
    private Long userId;
    
    /**
     * All additional custom claims.
     */
    private Map<String, Object> additionalClaims;
    
    /**
     * Creates JwtClaims from JWT Claims object.
     *
     * @param claims the JWT claims
     * @return JwtClaims instance
     */
    public static JwtClaims fromJwtClaims(Claims claims) {
        JwtClaimsBuilder builder = JwtClaims.builder()
            .issuer(claims.getIssuer())
            .subject(claims.getSubject())
            .audience(claims.getAudience() != null && !claims.getAudience().isEmpty() 
                ? claims.getAudience().iterator().next() : null)
            .jwtId(claims.getId());
            
        // Convert dates to Instant
        if (claims.getExpiration() != null) {
            builder.expiration(claims.getExpiration().toInstant());
        }
        if (claims.getIssuedAt() != null) {
            builder.issuedAt(claims.getIssuedAt().toInstant());
        }
        if (claims.getNotBefore() != null) {
            builder.notBefore(claims.getNotBefore().toInstant());
        }
        
        // Extract destination
        Object dest = claims.get("dest");
        if (dest instanceof String) {
            builder.destination((String) dest);
        }
        
        JwtClaims jwtClaims = builder.build();
        
        // Extract shop domain from issuer
        if (jwtClaims.getIssuer() != null) {
            jwtClaims.setShop(extractShopFromIssuer(jwtClaims.getIssuer()));
        }
        
        // Extract user ID from subject
        if (jwtClaims.getSubject() != null) {
            try {
                jwtClaims.setUserId(Long.parseLong(jwtClaims.getSubject()));
            } catch (NumberFormatException e) {
                // Subject is not a number, leave userId as null
            }
        }
        
        // Store additional claims
        Map<String, Object> additionalClaims = new java.util.HashMap<>(claims);
        additionalClaims.remove("iss");
        additionalClaims.remove("sub");
        additionalClaims.remove("aud");
        additionalClaims.remove("exp");
        additionalClaims.remove("iat");
        additionalClaims.remove("nbf");
        additionalClaims.remove("jti");
        additionalClaims.remove("dest");
        
        jwtClaims.setAdditionalClaims(additionalClaims);
        
        return jwtClaims;
    }
    
    /**
     * Checks if the token is expired.
     *
     * @return true if expired, false otherwise
     */
    public boolean isExpired() {
        return expiration != null && expiration.isBefore(Instant.now());
    }
    
    /**
     * Checks if the token is not yet valid.
     *
     * @return true if not yet valid, false otherwise
     */
    public boolean isNotYetValid() {
        return notBefore != null && notBefore.isAfter(Instant.now());
    }
    
    /**
     * Gets the remaining time until expiration in seconds.
     *
     * @return seconds until expiration, or -1 if no expiration
     */
    public long getSecondsUntilExpiration() {
        if (expiration == null) {
            return -1;
        }
        return expiration.getEpochSecond() - Instant.now().getEpochSecond();
    }
    
    /**
     * Gets a custom claim value.
     *
     * @param claimName the claim name
     * @return the claim value or null if not found
     */
    public Object getClaim(String claimName) {
        return additionalClaims != null ? additionalClaims.get(claimName) : null;
    }
    
    /**
     * Gets a custom claim value as a specific type.
     *
     * @param claimName the claim name
     * @param clazz the expected type
     * @param <T> the type parameter
     * @return the claim value cast to the specified type, or null if not found or wrong type
     */
    @SuppressWarnings("unchecked")
    public <T> T getClaim(String claimName, Class<T> clazz) {
        Object value = getClaim(claimName);
        if (value != null && clazz.isAssignableFrom(value.getClass())) {
            return (T) value;
        }
        return null;
    }
    
    private static String extractShopFromIssuer(String issuer) {
        if (issuer == null) {
            return null;
        }
        
        // Remove protocol if present
        if (issuer.startsWith("https://")) {
            issuer = issuer.substring(8);
        } else if (issuer.startsWith("http://")) {
            issuer = issuer.substring(7);
        }
        
        // Remove path if present
        int slashIndex = issuer.indexOf('/');
        if (slashIndex > 0) {
            issuer = issuer.substring(0, slashIndex);
        }
        
        return issuer;
    }
}