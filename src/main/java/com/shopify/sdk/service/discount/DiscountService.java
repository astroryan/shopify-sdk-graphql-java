package com.shopify.sdk.service.discount;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.shopify.sdk.client.ShopifyRestClient;
import com.shopify.sdk.model.discount.DiscountCode;
import com.shopify.sdk.model.discount.PriceRule;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class DiscountService {
    
    private final ShopifyRestClient restClient;
    private final ObjectMapper objectMapper;
    
    // Price Rules
    
    public Mono<List<PriceRule>> getPriceRules(String shop, String accessToken) {
        String endpoint = "/admin/api/2023-10/price_rules.json";
        
        return restClient.get(shop, accessToken, endpoint)
            .map(response -> {
                try {
                    var data = response.get("price_rules");
                    return objectMapper.convertValue(data,
                        objectMapper.getTypeFactory().constructCollectionType(List.class, PriceRule.class));
                } catch (Exception e) {
                    log.error("Failed to parse price rules response", e);
                    throw new RuntimeException("Failed to parse price rules response", e);
                }
            });
    }
    
    public Mono<PriceRule> getPriceRule(String shop, String accessToken, String priceRuleId) {
        String endpoint = "/admin/api/2023-10/price_rules/" + priceRuleId + ".json";
        
        return restClient.get(shop, accessToken, endpoint)
            .map(response -> {
                try {
                    var data = response.get("price_rule");
                    return objectMapper.convertValue(data, PriceRule.class);
                } catch (Exception e) {
                    log.error("Failed to parse price rule response", e);
                    throw new RuntimeException("Failed to parse price rule response", e);
                }
            });
    }
    
    public Mono<PriceRule> createPriceRule(String shop, String accessToken, PriceRuleCreateRequest request) {
        String endpoint = "/admin/api/2023-10/price_rules.json";
        
        return restClient.post(shop, accessToken, endpoint, Map.of("price_rule", request))
            .map(response -> {
                try {
                    var data = response.get("price_rule");
                    return objectMapper.convertValue(data, PriceRule.class);
                } catch (Exception e) {
                    log.error("Failed to parse create price rule response", e);
                    throw new RuntimeException("Failed to parse create price rule response", e);
                }
            });
    }
    
    public Mono<PriceRule> updatePriceRule(String shop, String accessToken, String priceRuleId, PriceRuleUpdateRequest request) {
        String endpoint = "/admin/api/2023-10/price_rules/" + priceRuleId + ".json";
        
        return restClient.post(shop, accessToken, endpoint, Map.of("price_rule", request))
            .map(response -> {
                try {
                    var data = response.get("price_rule");
                    return objectMapper.convertValue(data, PriceRule.class);
                } catch (Exception e) {
                    log.error("Failed to parse update price rule response", e);
                    throw new RuntimeException("Failed to parse update price rule response", e);
                }
            });
    }
    
    public Mono<Void> deletePriceRule(String shop, String accessToken, String priceRuleId) {
        String endpoint = "/admin/api/2023-10/price_rules/" + priceRuleId + ".json";
        
        return restClient.delete(shop, accessToken, endpoint)
            .then();
    }
    
    // Discount Codes
    
    public Mono<List<DiscountCode>> getDiscountCodes(String shop, String accessToken, String priceRuleId) {
        String endpoint = "/admin/api/2023-10/price_rules/" + priceRuleId + "/discount_codes.json";
        
        return restClient.get(shop, accessToken, endpoint)
            .map(response -> {
                try {
                    var data = response.get("discount_codes");
                    return objectMapper.convertValue(data,
                        objectMapper.getTypeFactory().constructCollectionType(List.class, DiscountCode.class));
                } catch (Exception e) {
                    log.error("Failed to parse discount codes response", e);
                    throw new RuntimeException("Failed to parse discount codes response", e);
                }
            });
    }
    
    public Mono<DiscountCode> getDiscountCode(String shop, String accessToken, String priceRuleId, String discountCodeId) {
        String endpoint = "/admin/api/2023-10/price_rules/" + priceRuleId + "/discount_codes/" + discountCodeId + ".json";
        
        return restClient.get(shop, accessToken, endpoint)
            .map(response -> {
                try {
                    var data = response.get("discount_code");
                    return objectMapper.convertValue(data, DiscountCode.class);
                } catch (Exception e) {
                    log.error("Failed to parse discount code response", e);
                    throw new RuntimeException("Failed to parse discount code response", e);
                }
            });
    }
    
    public Mono<DiscountCode> createDiscountCode(String shop, String accessToken, String priceRuleId, DiscountCodeCreateRequest request) {
        String endpoint = "/admin/api/2023-10/price_rules/" + priceRuleId + "/discount_codes.json";
        
        return restClient.post(shop, accessToken, endpoint, Map.of("discount_code", request))
            .map(response -> {
                try {
                    var data = response.get("discount_code");
                    return objectMapper.convertValue(data, DiscountCode.class);
                } catch (Exception e) {
                    log.error("Failed to parse create discount code response", e);
                    throw new RuntimeException("Failed to parse create discount code response", e);
                }
            });
    }
    
    public Mono<DiscountCode> updateDiscountCode(String shop, String accessToken, String priceRuleId, String discountCodeId, DiscountCodeUpdateRequest request) {
        String endpoint = "/admin/api/2023-10/price_rules/" + priceRuleId + "/discount_codes/" + discountCodeId + ".json";
        
        return restClient.post(shop, accessToken, endpoint, Map.of("discount_code", request))
            .map(response -> {
                try {
                    var data = response.get("discount_code");
                    return objectMapper.convertValue(data, DiscountCode.class);
                } catch (Exception e) {
                    log.error("Failed to parse update discount code response", e);
                    throw new RuntimeException("Failed to parse update discount code response", e);
                }
            });
    }
    
    public Mono<Void> deleteDiscountCode(String shop, String accessToken, String priceRuleId, String discountCodeId) {
        String endpoint = "/admin/api/2023-10/price_rules/" + priceRuleId + "/discount_codes/" + discountCodeId + ".json";
        
        return restClient.delete(shop, accessToken, endpoint)
            .then();
    }
    
    public Mono<List<DiscountCode>> createBatchDiscountCodes(String shop, String accessToken, String priceRuleId, BatchDiscountCodeRequest request) {
        String endpoint = "/admin/api/2023-10/price_rules/" + priceRuleId + "/batch.json";
        
        return restClient.post(shop, accessToken, endpoint, Map.of("discount_codes", request.getCodes()))
            .map(response -> {
                try {
                    var data = response.get("discount_codes");
                    return objectMapper.convertValue(data,
                        objectMapper.getTypeFactory().constructCollectionType(List.class, DiscountCode.class));
                } catch (Exception e) {
                    log.error("Failed to parse batch discount codes response", e);
                    throw new RuntimeException("Failed to parse batch discount codes response", e);
                }
            });
    }
    
    // Convenience methods
    
    public Mono<List<PriceRule>> getActivePriceRules(String shop, String accessToken) {
        return getPriceRules(shop, accessToken)
            .map(priceRules -> priceRules.stream()
                .filter(PriceRule::isActive)
                .toList());
    }
    
    public Mono<DiscountCode> lookupDiscountCode(String shop, String accessToken, String code) {
        String endpoint = "/admin/api/2023-10/discount_codes/lookup.json";
        Map<String, Object> params = Map.of("code", code);
        
        return restClient.get(shop, accessToken, endpoint, params)
            .map(response -> {
                try {
                    var data = response.get("discount_code");
                    return objectMapper.convertValue(data, DiscountCode.class);
                } catch (Exception e) {
                    log.error("Failed to parse discount code lookup response", e);
                    throw new RuntimeException("Failed to parse discount code lookup response", e);
                }
            });
    }
    
    @lombok.Data
    @lombok.NoArgsConstructor
    @lombok.AllArgsConstructor
    @lombok.Builder
    public static class PriceRuleCreateRequest {
        private String title;
        private String targetType;
        private String targetSelection;
        private String allocationMethod;
        private String valueType;
        private BigDecimal value;
        private Boolean oncePerCustomer;
        private Integer usageLimit;
        private String customerSelection;
        private Instant startsAt;
        private Instant endsAt;
        private List<String> entitledProductIds;
        private List<String> entitledVariantIds;
        private List<String> entitledCollectionIds;
        private List<String> entitledCountryIds;
        private List<String> prerequisiteProductIds;
        private List<String> prerequisiteVariantIds;
        private List<String> prerequisiteCollectionIds;
        private List<String> prerequisiteCustomerIds;
        private PriceRule.MoneyRange prerequisiteSubtotalRange;
        private PriceRule.QuantityRange prerequisiteQuantityRange;
        private PriceRule.MoneyRange prerequisiteShippingPriceRange;
        private PriceRule.QuantityRatio prerequisiteToEntitlementQuantityRatio;
        private String prerequisiteToEntitlementPurchase;
    }
    
    @lombok.Data
    @lombok.NoArgsConstructor
    @lombok.AllArgsConstructor
    @lombok.Builder
    public static class PriceRuleUpdateRequest {
        private String title;
        private String targetType;
        private String targetSelection;
        private String allocationMethod;
        private String valueType;
        private BigDecimal value;
        private Boolean oncePerCustomer;
        private Integer usageLimit;
        private String customerSelection;
        private Instant startsAt;
        private Instant endsAt;
        private List<String> entitledProductIds;
        private List<String> entitledVariantIds;
        private List<String> entitledCollectionIds;
        private List<String> entitledCountryIds;
        private List<String> prerequisiteProductIds;
        private List<String> prerequisiteVariantIds;
        private List<String> prerequisiteCollectionIds;
        private List<String> prerequisiteCustomerIds;
        private PriceRule.MoneyRange prerequisiteSubtotalRange;
        private PriceRule.QuantityRange prerequisiteQuantityRange;
        private PriceRule.MoneyRange prerequisiteShippingPriceRange;
        private PriceRule.QuantityRatio prerequisiteToEntitlementQuantityRatio;
        private String prerequisiteToEntitlementPurchase;
    }
    
    @lombok.Data
    @lombok.NoArgsConstructor
    @lombok.AllArgsConstructor
    @lombok.Builder
    public static class DiscountCodeCreateRequest {
        private String code;
    }
    
    @lombok.Data
    @lombok.NoArgsConstructor
    @lombok.AllArgsConstructor
    @lombok.Builder
    public static class DiscountCodeUpdateRequest {
        private String code;
    }
    
    @lombok.Data
    @lombok.NoArgsConstructor
    @lombok.AllArgsConstructor
    @lombok.Builder
    public static class BatchDiscountCodeRequest {
        private List<BatchDiscountCode> codes;
        
        @lombok.Data
        @lombok.NoArgsConstructor
        @lombok.AllArgsConstructor
        @lombok.Builder
        public static class BatchDiscountCode {
            private String code;
        }
    }
}