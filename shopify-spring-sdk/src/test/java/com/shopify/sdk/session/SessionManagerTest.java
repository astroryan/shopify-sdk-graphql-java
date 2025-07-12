package com.shopify.sdk.session;

import com.shopify.sdk.auth.AccessTokenResponse;
import com.shopify.sdk.auth.JwtTokenValidator;
import com.shopify.sdk.auth.JwtClaims;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class SessionManagerTest {
    
    @Mock
    private SessionStore sessionStore;
    
    @Mock
    private JwtTokenValidator jwtTokenValidator;
    
    private SessionManager sessionManager;
    
    @BeforeEach
    void setUp() {
        sessionManager = new SessionManager(sessionStore, jwtTokenValidator);
    }
    
    @Test
    @DisplayName("Should create session from access token response")
    void testCreateSession() {
        // Given
        String shop = "test-shop.myshopify.com";
        AccessTokenResponse tokenResponse = new AccessTokenResponse();
        tokenResponse.setAccessToken("test-access-token");
        tokenResponse.setScope("read_products,write_orders");
        
        when(sessionStore.storeSession(any(ShopifySession.class)))
            .thenReturn(Mono.empty());
        
        // When
        Mono<ShopifySession> result = sessionManager.createSession(shop, tokenResponse, true);
        
        // Then
        StepVerifier.create(result)
            .assertNext(session -> {
                assertThat(session).isNotNull();
                assertThat(session.getShop()).isEqualTo("test-shop");
                assertThat(session.getAccessToken()).isEqualTo("test-access-token");
                assertThat(session.isOnline()).isTrue();
                assertThat(session.getScope()).contains("read_products", "write_orders");
            })
            .verifyComplete();
        
        verify(sessionStore).storeSession(any(ShopifySession.class));
    }
    
    @Test
    @DisplayName("Should load session by ID")
    void testLoadSession() {
        // Given
        String sessionId = "test-shop.myshopify.com_12345";
        ShopifySession mockSession = ShopifySession.builder()
            .id(sessionId)
            .shop("test-shop.myshopify.com")
            .accessToken("test-token")
            .isOnline(true)
            .build();
        
        when(sessionStore.loadSession(sessionId))
            .thenReturn(Mono.just(Optional.of(mockSession)));
        
        // When
        Mono<ShopifySession> result = sessionManager.getSession(sessionId);
        
        // Then
        StepVerifier.create(result)
            .assertNext(session -> {
                assertThat(session).isNotNull();
                assertThat(session.getId()).isEqualTo(sessionId);
                assertThat(session.getShop()).isEqualTo("test-shop.myshopify.com");
            })
            .verifyComplete();
    }
    
    @Test
    @DisplayName("Should find sessions by shop")
    void testFindSessionsByShop() {
        // Given
        String shop = "test-shop.myshopify.com";
        ShopifySession onlineSession = ShopifySession.builder()
            .id("online-session")
            .shop(shop)
            .isOnline(true)
            .accessToken("online-token")
            .build();
        
        ShopifySession offlineSession = ShopifySession.builder()
            .id("offline-session")
            .shop(shop)
            .isOnline(false)
            .accessToken("offline-token")
            .build();
        
        when(sessionStore.findSessionsByShop("test-shop"))
            .thenReturn(Mono.just(java.util.Arrays.asList(onlineSession, offlineSession)));
        
        // When
        Mono<List<ShopifySession>> result = sessionManager.getSessionsForShop(shop);
        
        // Then
        StepVerifier.create(result)
            .assertNext(sessions -> {
                assertThat(sessions).hasSize(2);
                assertThat(sessions).extracting(ShopifySession::getId)
                    .containsExactlyInAnyOrder("online-session", "offline-session");
            })
            .verifyComplete();
    }
    
    
    @Test
    @DisplayName("Should create session from JWT")
    void testCreateSessionFromJwt() {
        // Given
        String jwt = "test.jwt.token";
        String accessToken = "test-access-token";
        JwtClaims claims = JwtClaims.builder()
            .destination("https://test-shop.myshopify.com")
            .subject("12345")
            .userId(12345L)
            .shop("test-shop.myshopify.com")
            .expiration(Instant.now().plusSeconds(3600))
            .build();
        
        when(jwtTokenValidator.validateToken(jwt))
            .thenReturn(claims);
        
        when(sessionStore.storeSession(any(ShopifySession.class)))
            .thenReturn(Mono.empty());
        
        // When
        Mono<ShopifySession> result = sessionManager.createSessionFromJwt(jwt, accessToken);
        
        // Then
        StepVerifier.create(result)
            .assertNext(session -> {
                assertThat(session).isNotNull();
                assertThat(session.getShop()).isEqualTo("test-shop");
                assertThat(session.getUserId()).isEqualTo(12345L);
                assertThat(session.isOnline()).isTrue();
                assertThat(session.getExpiresAt()).isNotNull();
            })
            .verifyComplete();
    }
    
    
    @Test
    @DisplayName("Should delete session")
    void testDeleteSession() {
        // Given
        String sessionId = "test-session-id";
        
        when(sessionStore.deleteSession(sessionId))
            .thenReturn(Mono.empty());
        
        // When
        Mono<Void> result = sessionManager.deleteSession(sessionId);
        
        // Then
        StepVerifier.create(result)
            .verifyComplete();
        
        verify(sessionStore).deleteSession(sessionId);
    }
    
    @Test
    @DisplayName("Should delete sessions by shop")
    void testDeleteSessionsByShop() {
        // Given
        String shop = "test-shop.myshopify.com";
        
        when(sessionStore.deleteSessionsByShop("test-shop"))
            .thenReturn(Mono.empty());
        
        // When
        Mono<Void> result = sessionManager.deleteSessionsForShop(shop);
        
        // Then
        StepVerifier.create(result)
            .verifyComplete();
        
        verify(sessionStore).deleteSessionsByShop("test-shop");
    }
}