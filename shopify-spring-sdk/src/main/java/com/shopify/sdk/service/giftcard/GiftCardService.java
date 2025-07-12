package com.shopify.sdk.service.giftcard;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.shopify.sdk.client.ShopifyRestClient;
import com.shopify.sdk.model.giftcard.GiftCard;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class GiftCardService {
    
    private final ShopifyRestClient restClient;
    private final ObjectMapper objectMapper;
    
    public Mono<List<GiftCard>> getGiftCards(String shop, String accessToken) {
        String endpoint = "/admin/api/2023-10/gift_cards.json";
        
        return restClient.get(shop, accessToken, endpoint)
            .map(response -> {
                try {
                    var data = response.get("gift_cards");
                    return objectMapper.convertValue(data,
                        objectMapper.getTypeFactory().constructCollectionType(List.class, GiftCard.class));
                } catch (Exception e) {
                    log.error("Failed to parse gift cards response", e);
                    throw new RuntimeException("Failed to parse gift cards response", e);
                }
            });
    }
    
    public Mono<GiftCard> getGiftCard(String shop, String accessToken, String giftCardId) {
        String endpoint = "/admin/api/2023-10/gift_cards/" + giftCardId + ".json";
        
        return restClient.get(shop, accessToken, endpoint)
            .map(response -> {
                try {
                    var data = response.get("gift_card");
                    return objectMapper.convertValue(data, GiftCard.class);
                } catch (Exception e) {
                    log.error("Failed to parse gift card response", e);
                    throw new RuntimeException("Failed to parse gift card response", e);
                }
            });
    }
    
    public Mono<GiftCard> createGiftCard(String shop, String accessToken, GiftCardCreateRequest request) {
        String endpoint = "/admin/api/2023-10/gift_cards.json";
        
        return restClient.post(shop, accessToken, endpoint, Map.of("gift_card", request))
            .map(response -> {
                try {
                    var data = response.get("gift_card");
                    return objectMapper.convertValue(data, GiftCard.class);
                } catch (Exception e) {
                    log.error("Failed to parse create gift card response", e);
                    throw new RuntimeException("Failed to parse create gift card response", e);
                }
            });
    }
    
    public Mono<GiftCard> updateGiftCard(String shop, String accessToken, String giftCardId, GiftCardUpdateRequest request) {
        String endpoint = "/admin/api/2023-10/gift_cards/" + giftCardId + ".json";
        
        return restClient.post(shop, accessToken, endpoint, Map.of("gift_card", request))
            .map(response -> {
                try {
                    var data = response.get("gift_card");
                    return objectMapper.convertValue(data, GiftCard.class);
                } catch (Exception e) {
                    log.error("Failed to parse update gift card response", e);
                    throw new RuntimeException("Failed to parse update gift card response", e);
                }
            });
    }
    
    public Mono<GiftCard> disableGiftCard(String shop, String accessToken, String giftCardId) {
        String endpoint = "/admin/api/2023-10/gift_cards/" + giftCardId + "/disable.json";
        
        return restClient.post(shop, accessToken, endpoint, Map.of())
            .map(response -> {
                try {
                    var data = response.get("gift_card");
                    return objectMapper.convertValue(data, GiftCard.class);
                } catch (Exception e) {
                    log.error("Failed to parse disable gift card response", e);
                    throw new RuntimeException("Failed to parse disable gift card response", e);
                }
            });
    }
    
    public Mono<List<GiftCard>> searchGiftCards(String shop, String accessToken, String query, Integer limit) {
        String endpoint = "/admin/api/2023-10/gift_cards/search.json";
        Map<String, Object> params = Map.of(
            "query", query,
            "limit", limit != null ? limit : 50
        );
        
        return restClient.get(shop, accessToken, endpoint, params)
            .map(response -> {
                try {
                    var data = response.get("gift_cards");
                    return objectMapper.convertValue(data,
                        objectMapper.getTypeFactory().constructCollectionType(List.class, GiftCard.class));
                } catch (Exception e) {
                    log.error("Failed to parse search gift cards response", e);
                    throw new RuntimeException("Failed to parse search gift cards response", e);
                }
            });
    }
    
    public Mono<Integer> getGiftCardCount(String shop, String accessToken, String status) {
        String endpoint = "/admin/api/2023-10/gift_cards/count.json";
        Map<String, Object> params = status != null ? Map.of("status", status) : Map.of();
        
        return restClient.get(shop, accessToken, endpoint, params)
            .map(response -> {
                try {
                    return response.get("count").asInt();
                } catch (Exception e) {
                    log.error("Failed to parse gift card count response", e);
                    throw new RuntimeException("Failed to parse gift card count response", e);
                }
            });
    }
    
    // Convenience methods
    
    public Mono<List<GiftCard>> getActiveGiftCards(String shop, String accessToken) {
        return getGiftCards(shop, accessToken)
            .map(giftCards -> giftCards.stream()
                .filter(GiftCard::isActive)
                .toList());
    }
    
    public Mono<List<GiftCard>> getGiftCardsByCustomer(String shop, String accessToken, String customerId) {
        return getGiftCards(shop, accessToken)
            .map(giftCards -> giftCards.stream()
                .filter(giftCard -> customerId.equals(giftCard.getCustomerId()))
                .toList());
    }
    
    public Mono<List<GiftCard>> getExpiredGiftCards(String shop, String accessToken) {
        return getGiftCards(shop, accessToken)
            .map(giftCards -> giftCards.stream()
                .filter(GiftCard::isExpired)
                .toList());
    }
    
    public Mono<List<GiftCard>> getGiftCardsWithBalance(String shop, String accessToken, BigDecimal minBalance) {
        return getGiftCards(shop, accessToken)
            .map(giftCards -> giftCards.stream()
                .filter(giftCard -> giftCard.getBalance() != null && 
                                   giftCard.getBalance().compareTo(minBalance) >= 0)
                .toList());
    }
    
    @lombok.Data
    @lombok.NoArgsConstructor
    @lombok.AllArgsConstructor
    @lombok.Builder
    public static class GiftCardCreateRequest {
        private String note;
        private BigDecimal initialValue;
        private String code;
        private String templateSuffix;
        private String customerId;
    }
    
    @lombok.Data
    @lombok.NoArgsConstructor
    @lombok.AllArgsConstructor
    @lombok.Builder
    public static class GiftCardUpdateRequest {
        private String note;
        private String templateSuffix;
        private String customerId;
    }
}