package com.shopify.sdk.service.fulfillment;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.shopify.sdk.client.ShopifyRestClient;
import com.shopify.sdk.model.fulfillment.Fulfillment;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class FulfillmentService {
    
    private final ShopifyRestClient restClient;
    private final ObjectMapper objectMapper;
    
    public Mono<List<Fulfillment>> getFulfillments(String shop, String accessToken, String orderId) {
        String endpoint = "/admin/api/2023-10/orders/" + orderId + "/fulfillments.json";
        
        return restClient.get(shop, accessToken, endpoint)
            .map(response -> {
                try {
                    var data = response.get("fulfillments");
                    return objectMapper.convertValue(data,
                        objectMapper.getTypeFactory().constructCollectionType(List.class, Fulfillment.class));
                } catch (Exception e) {
                    log.error("Failed to parse fulfillments response", e);
                    throw new RuntimeException("Failed to parse fulfillments response", e);
                }
            });
    }
    
    public Mono<Fulfillment> getFulfillment(String shop, String accessToken, String orderId, String fulfillmentId) {
        String endpoint = "/admin/api/2023-10/orders/" + orderId + "/fulfillments/" + fulfillmentId + ".json";
        
        return restClient.get(shop, accessToken, endpoint)
            .map(response -> {
                try {
                    var data = response.get("fulfillment");
                    return objectMapper.convertValue(data, Fulfillment.class);
                } catch (Exception e) {
                    log.error("Failed to parse fulfillment response", e);
                    throw new RuntimeException("Failed to parse fulfillment response", e);
                }
            });
    }
    
    public Mono<Fulfillment> createFulfillment(String shop, String accessToken, String orderId, CreateFulfillmentRequest request) {
        String endpoint = "/admin/api/2023-10/orders/" + orderId + "/fulfillments.json";
        
        return restClient.post(shop, accessToken, endpoint, Map.of("fulfillment", request))
            .map(response -> {
                try {
                    var data = response.get("fulfillment");
                    return objectMapper.convertValue(data, Fulfillment.class);
                } catch (Exception e) {
                    log.error("Failed to parse create fulfillment response", e);
                    throw new RuntimeException("Failed to parse create fulfillment response", e);
                }
            });
    }
    
    public Mono<Fulfillment> updateFulfillment(String shop, String accessToken, String orderId, String fulfillmentId, UpdateFulfillmentRequest request) {
        String endpoint = "/admin/api/2023-10/orders/" + orderId + "/fulfillments/" + fulfillmentId + ".json";
        
        return restClient.post(shop, accessToken, endpoint, Map.of("fulfillment", request))
            .map(response -> {
                try {
                    var data = response.get("fulfillment");
                    return objectMapper.convertValue(data, Fulfillment.class);
                } catch (Exception e) {
                    log.error("Failed to parse update fulfillment response", e);
                    throw new RuntimeException("Failed to parse update fulfillment response", e);
                }
            });
    }
    
    public Mono<Fulfillment> completeFulfillment(String shop, String accessToken, String orderId, String fulfillmentId) {
        String endpoint = "/admin/api/2023-10/orders/" + orderId + "/fulfillments/" + fulfillmentId + "/complete.json";
        
        return restClient.post(shop, accessToken, endpoint, Map.of())
            .map(response -> {
                try {
                    var data = response.get("fulfillment");
                    return objectMapper.convertValue(data, Fulfillment.class);
                } catch (Exception e) {
                    log.error("Failed to parse complete fulfillment response", e);
                    throw new RuntimeException("Failed to parse complete fulfillment response", e);
                }
            });
    }
    
    public Mono<Fulfillment> openFulfillment(String shop, String accessToken, String orderId, String fulfillmentId) {
        String endpoint = "/admin/api/2023-10/orders/" + orderId + "/fulfillments/" + fulfillmentId + "/open.json";
        
        return restClient.post(shop, accessToken, endpoint, Map.of())
            .map(response -> {
                try {
                    var data = response.get("fulfillment");
                    return objectMapper.convertValue(data, Fulfillment.class);
                } catch (Exception e) {
                    log.error("Failed to parse open fulfillment response", e);
                    throw new RuntimeException("Failed to parse open fulfillment response", e);
                }
            });
    }
    
    public Mono<Fulfillment> cancelFulfillment(String shop, String accessToken, String orderId, String fulfillmentId) {
        String endpoint = "/admin/api/2023-10/orders/" + orderId + "/fulfillments/" + fulfillmentId + "/cancel.json";
        
        return restClient.post(shop, accessToken, endpoint, Map.of())
            .map(response -> {
                try {
                    var data = response.get("fulfillment");
                    return objectMapper.convertValue(data, Fulfillment.class);
                } catch (Exception e) {
                    log.error("Failed to parse cancel fulfillment response", e);
                    throw new RuntimeException("Failed to parse cancel fulfillment response", e);
                }
            });
    }
    
    public Mono<Fulfillment> updateTrackingInfo(String shop, String accessToken, String orderId, String fulfillmentId, TrackingInfo trackingInfo) {
        String endpoint = "/admin/api/2023-10/orders/" + orderId + "/fulfillments/" + fulfillmentId + "/update_tracking.json";
        
        return restClient.post(shop, accessToken, endpoint, Map.of("fulfillment", trackingInfo))
            .map(response -> {
                try {
                    var data = response.get("fulfillment");
                    return objectMapper.convertValue(data, Fulfillment.class);
                } catch (Exception e) {
                    log.error("Failed to parse update tracking response", e);
                    throw new RuntimeException("Failed to parse update tracking response", e);
                }
            });
    }
    
    @lombok.Data
    @lombok.NoArgsConstructor
    @lombok.AllArgsConstructor
    @lombok.Builder
    public static class CreateFulfillmentRequest {
        private String locationId;
        private String trackingNumber;
        private List<String> trackingNumbers;
        private String trackingCompany;
        private List<String> trackingUrls;
        private Boolean notifyCustomer;
        private List<FulfillmentLineItemRequest> lineItems;
        private String service;
        private String shipmentStatus;
        
        @lombok.Data
        @lombok.NoArgsConstructor
        @lombok.AllArgsConstructor
        @lombok.Builder
        public static class FulfillmentLineItemRequest {
            private String id;
            private Integer quantity;
        }
    }
    
    @lombok.Data
    @lombok.NoArgsConstructor
    @lombok.AllArgsConstructor
    @lombok.Builder
    public static class UpdateFulfillmentRequest {
        private String trackingNumber;
        private List<String> trackingNumbers;
        private String trackingCompany;
        private List<String> trackingUrls;
        private Boolean notifyCustomer;
    }
    
    @lombok.Data
    @lombok.NoArgsConstructor
    @lombok.AllArgsConstructor
    @lombok.Builder
    public static class TrackingInfo {
        private String trackingNumber;
        private List<String> trackingNumbers;
        private String trackingCompany;
        private List<String> trackingUrls;
        private Boolean notifyCustomer;
    }
}