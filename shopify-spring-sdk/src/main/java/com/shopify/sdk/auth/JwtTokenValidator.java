package com.shopify.sdk.auth;

import com.shopify.sdk.config.ShopifyAuthContext;
import com.shopify.sdk.exception.ShopifyApiException;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.util.Date;
import java.util.Map;

/**
 * JWT token validator for Shopify applications.
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class JwtTokenValidator {
    
    private final ShopifyAuthContext context;
    
    /**
     * Validates and parses a JWT token from Shopify.
     *
     * @param token the JWT token to validate
     * @return the parsed JWT claims
     * @throws ShopifyApiException if the token is invalid
     */
    public JwtClaims validateToken(String token) {
        if (token == null || token.trim().isEmpty()) {
            throw new ShopifyApiException("JWT token cannot be null or empty");
        }
        
        try {
            SecretKey key = Keys.hmacShaKeyFor(context.getApiSecret().getBytes(StandardCharsets.UTF_8));
            
            Jws<Claims> jws = Jwts.parser()
                .verifyWith(key)
                .build()
                .parseSignedClaims(token);
                
            Claims claims = jws.getBody();
            
            // Validate required claims
            validateRequiredClaims(claims);
            
            return JwtClaims.fromJwtClaims(claims);
            
        } catch (ExpiredJwtException e) {
            log.warn("JWT token has expired: {}", e.getMessage());
            throw new ShopifyApiException("JWT token has expired", e);
        } catch (UnsupportedJwtException e) {
            log.warn("Unsupported JWT token: {}", e.getMessage());
            throw new ShopifyApiException("Unsupported JWT token", e);
        } catch (MalformedJwtException e) {
            log.warn("Malformed JWT token: {}", e.getMessage());
            throw new ShopifyApiException("Malformed JWT token", e);
        } catch (SignatureException e) {
            log.warn("Invalid JWT signature: {}", e.getMessage());
            throw new ShopifyApiException("Invalid JWT signature", e);
        } catch (IllegalArgumentException e) {
            log.warn("Invalid JWT token: {}", e.getMessage());
            throw new ShopifyApiException("Invalid JWT token", e);
        }
    }
    
    /**
     * Validates a JWT token without throwing exceptions.
     *
     * @param token the JWT token to validate
     * @return true if valid, false otherwise
     */
    public boolean isTokenValid(String token) {
        try {
            validateToken(token);
            return true;
        } catch (Exception e) {
            log.debug("Token validation failed: {}", e.getMessage());
            return false;
        }
    }
    
    /**
     * Extracts the shop domain from a JWT token without full validation.
     *
     * @param token the JWT token
     * @return the shop domain or null if not found
     */
    public String extractShopDomain(String token) {
        try {
            // Parse without verification to extract shop domain
            int firstDot = token.indexOf('.');
            int secondDot = token.indexOf('.', firstDot + 1);
            
            if (firstDot == -1 || secondDot == -1) {
                return null;
            }
            
            String payload = token.substring(firstDot + 1, secondDot);
            String decodedPayload = new String(
                java.util.Base64.getUrlDecoder().decode(payload), 
                StandardCharsets.UTF_8
            );
            
            // Simple extraction - in production you might want to use a JSON parser
            if (decodedPayload.contains("\"dest\":")) {
                int destStart = decodedPayload.indexOf("\"dest\":\"") + 8;
                int destEnd = decodedPayload.indexOf("\"", destStart);
                if (destStart > 7 && destEnd > destStart) {
                    String dest = decodedPayload.substring(destStart, destEnd);
                    // Extract shop domain from URL
                    if (dest.startsWith("https://")) {
                        String domain = dest.substring(8);
                        int slashIndex = domain.indexOf('/');
                        if (slashIndex > 0) {
                            domain = domain.substring(0, slashIndex);
                        }
                        return domain;
                    }
                }
            }
            
            return null;
        } catch (Exception e) {
            log.debug("Error extracting shop domain from token: {}", e.getMessage());
            return null;
        }
    }
    
    /**
     * Creates a JWT token for testing purposes.
     *
     * @param shop the shop domain
     * @param userId the user ID (optional)
     * @return the generated JWT token
     */
    public String createTestToken(String shop, Long userId) {
        SecretKey key = Keys.hmacShaKeyFor(context.getApiSecret().getBytes(StandardCharsets.UTF_8));
        
        JwtBuilder builder = Jwts.builder()
            .setIssuer(shop)
            .setSubject(userId != null ? userId.toString() : "1")
            .setAudience(context.getApiKey())
            .setIssuedAt(Date.from(Instant.now()))
            .setExpiration(Date.from(Instant.now().plusSeconds(3600)))
            .claim("dest", "https://" + shop)
            .signWith(key, SignatureAlgorithm.HS256);
            
        if (userId != null) {
            builder.claim("sub", userId.toString());
        }
        
        return builder.compact();
    }
    
    private void validateRequiredClaims(Claims claims) {
        // Validate issuer (shop domain)
        String issuer = claims.getIssuer();
        if (issuer == null || !issuer.endsWith(".myshopify.com")) {
            throw new ShopifyApiException("Invalid or missing issuer claim");
        }
        
        // Validate audience (API key)
        if (claims.getAudience() == null || !claims.getAudience().contains(context.getApiKey())) {
            throw new ShopifyApiException("Invalid audience claim");
        }
        
        // Validate expiration
        Date expiration = claims.getExpiration();
        if (expiration != null && expiration.before(new Date())) {
            throw new ShopifyApiException("Token has expired");
        }
        
        // Validate issued at time (not too far in the future)
        Date issuedAt = claims.getIssuedAt();
        if (issuedAt != null && issuedAt.after(new Date(System.currentTimeMillis() + 300000))) { // 5 minutes tolerance
            throw new ShopifyApiException("Token issued too far in the future");
        }
    }
}