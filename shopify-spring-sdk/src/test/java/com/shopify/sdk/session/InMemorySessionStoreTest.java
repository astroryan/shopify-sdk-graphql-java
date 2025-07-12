package com.shopify.sdk.session;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertTrue;

class InMemorySessionStoreTest {
    
    private InMemorySessionStore store;
    
    @BeforeEach
    void setUp() {
        store = new InMemorySessionStore();
    }
    
    @Test
    @DisplayName("Should store and load session")
    void testStoreAndLoad() {
        // Given
        ShopifySession session = createTestSession("test-id", "test-shop.myshopify.com");
        
        // When - Store
        Mono<Void> storeMono = store.storeSession(session);
        StepVerifier.create(storeMono).verifyComplete();
        
        // When - Load
        Mono<Optional<ShopifySession>> loadMono = store.loadSession("test-id");
        
        // Then
        StepVerifier.create(loadMono)
            .assertNext(loadedOpt -> {
                assertThat(loadedOpt).isPresent();
                ShopifySession loaded = loadedOpt.get();
                assertThat(loaded.getId()).isEqualTo("test-id");
                assertThat(loaded.getShop()).isEqualTo("test-shop.myshopify.com");
                assertThat(loaded.getAccessToken()).isEqualTo("test-token");
            })
            .verifyComplete();
    }
    
    @Test
    @DisplayName("Should delete session")
    void testDeleteSession() {
        // Given
        ShopifySession session = createTestSession("delete-id", "test-shop.myshopify.com");
        store.storeSession(session).block();
        
        // When - Delete
        Mono<Void> deleteMono = store.deleteSession("delete-id");
        StepVerifier.create(deleteMono).verifyComplete();
        
        // Then - Should not exist
        Mono<Optional<ShopifySession>> loadMono = store.loadSession("delete-id");
        StepVerifier.create(loadMono)
            .assertNext(optional -> assertThat(optional).isEmpty())
            .verifyComplete();
    }
    
    @Test
    @DisplayName("Should delete sessions by shop")
    void testDeleteSessionsByShop() {
        // Given
        String shop = "test-shop.myshopify.com";
        ShopifySession session1 = createTestSession("session-1", shop);
        ShopifySession session2 = createTestSession("session-2", shop);
        ShopifySession session3 = createTestSession("session-3", "other-shop.myshopify.com");
        
        store.storeSession(session1).block();
        store.storeSession(session2).block();
        store.storeSession(session3).block();
        
        // When - Delete by shop
        Mono<Void> deleteMono = store.deleteSessionsByShop(shop);
        StepVerifier.create(deleteMono).verifyComplete();
        
        // Then
        // Sessions for test-shop should be deleted
        assertThat(store.loadSession("session-1").block()).isEmpty();
        assertThat(store.loadSession("session-2").block()).isEmpty();
        
        // Session for other-shop should still exist
        assertThat(store.loadSession("session-3").block()).isPresent();
    }
    
    @Test
    @DisplayName("Should find sessions by shop")
    void testFindSessionsByShop() {
        // Given
        String shop = "test-shop.myshopify.com";
        ShopifySession session1 = createTestSession("find-1", shop);
        ShopifySession session2 = createTestSession("find-2", shop);
        ShopifySession session3 = createTestSession("find-3", "other-shop.myshopify.com");
        
        store.storeSession(session1).block();
        store.storeSession(session2).block();
        store.storeSession(session3).block();
        
        // When
        Mono<List<ShopifySession>> sessionsMono = store.findSessionsByShop(shop);
        
        // Then
        StepVerifier.create(sessionsMono)
            .assertNext(sessions -> {
                assertThat(sessions).hasSize(2);
                assertThat(sessions).extracting(ShopifySession::getId)
                    .containsExactlyInAnyOrder("find-1", "find-2");
            })
            .verifyComplete();
    }
    
    @Test
    @DisplayName("Should handle concurrent access")
    void testConcurrentAccess() throws InterruptedException {
        // Given
        int threadCount = 100;
        CountDownLatch latch = new CountDownLatch(threadCount);
        AtomicInteger successCount = new AtomicInteger(0);
        
        // When - Multiple threads store and load sessions
        for (int i = 0; i < threadCount; i++) {
            final int index = i;
            new Thread(() -> {
                try {
                    String sessionId = "concurrent-" + index;
                    ShopifySession session = createTestSession(sessionId, "shop-" + index + ".myshopify.com");
                    
                    // Store
                    store.storeSession(session).block();
                    
                    // Load
                    Optional<ShopifySession> loadedOpt = store.loadSession(sessionId).block();
                    if (loadedOpt != null && loadedOpt.isPresent() && loadedOpt.get().getId().equals(sessionId)) {
                        successCount.incrementAndGet();
                    }
                } finally {
                    latch.countDown();
                }
            }).start();
        }
        
        // Then
        assertTrue(latch.await(10, TimeUnit.SECONDS));
        assertThat(successCount.get()).isEqualTo(threadCount);
    }
    
    @Test
    @DisplayName("Should update existing session")
    void testUpdateSession() {
        // Given
        String sessionId = "update-id";
        ShopifySession original = createTestSession(sessionId, "test-shop.myshopify.com");
        store.storeSession(original).block();
        
        // When - Update with new token
        ShopifySession updated = original.toBuilder()
            .accessToken("new-token")
            .scope(Set.of("read_products", "write_products", "read_orders"))
            .expiresAt(Instant.now().plusSeconds(7200))
            .build();
        
        store.storeSession(updated).block();
        
        // Then
        Optional<ShopifySession> loadedOpt = store.loadSession(sessionId).block();
        assertThat(loadedOpt).isPresent();
        assertThat(loadedOpt.get().getAccessToken()).isEqualTo("new-token");
        assertThat(loadedOpt.get().getScope()).contains("read_orders");
        assertThat(loadedOpt.get().getExpiresAt()).isAfter(Instant.now());
    }
    
    @Test
    @DisplayName("Should handle non-existent session gracefully")
    void testLoadNonExistentSession() {
        // When
        Mono<Optional<ShopifySession>> loadMono = store.loadSession("non-existent-id");
        
        // Then
        StepVerifier.create(loadMono)
            .assertNext(sessionOpt -> assertThat(sessionOpt).isEmpty())
            .verifyComplete();
    }
    
    @Test
    @DisplayName("Should delete non-existent session gracefully")
    void testDeleteNonExistentSession() {
        // When
        Mono<Void> deleteMono = store.deleteSession("non-existent-id");
        
        // Then
        StepVerifier.create(deleteMono).verifyComplete();
    }
    
    @Test
    @DisplayName("Should handle empty shop query")
    void testFindSessionsEmptyShop() {
        // Given
        ShopifySession session = createTestSession("test-id", "test-shop.myshopify.com");
        store.storeSession(session).block();
        
        // When
        Mono<List<ShopifySession>> sessionsMono = store.findSessionsByShop("non-existent-shop.myshopify.com");
        
        // Then
        StepVerifier.create(sessionsMono)
            .assertNext(sessions -> assertThat(sessions).isEmpty())
            .verifyComplete();
    }
    
    @Test
    @DisplayName("Should maintain session isolation")
    void testSessionIsolation() {
        // Given
        ShopifySession session1 = createTestSession("iso-1", "shop1.myshopify.com");
        ShopifySession session2 = createTestSession("iso-2", "shop2.myshopify.com");
        
        store.storeSession(session1).block();
        store.storeSession(session2).block();
        
        // When - Modify session1 reference
        session1 = session1.toBuilder().accessToken("modified-token").build();
        
        // Then - Stored session should not be affected
        Optional<ShopifySession> loadedOpt = store.loadSession("iso-1").block();
        assertThat(loadedOpt).isPresent();
        assertThat(loadedOpt.get().getAccessToken()).isEqualTo("test-token");
    }
    
    // Helper method
    private ShopifySession createTestSession(String id, String shop) {
        return ShopifySession.builder()
            .id(id)
            .shop(shop)
            .isOnline(true)
            .accessToken("test-token")
            .scope(Set.of("read_products", "write_products"))
            .expiresAt(Instant.now().plusSeconds(3600))
            .createdAt(Instant.now())
            .updatedAt(Instant.now())
            .build();
    }
}