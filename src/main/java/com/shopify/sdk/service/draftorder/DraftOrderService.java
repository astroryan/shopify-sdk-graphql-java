package com.shopify.sdk.service.draftorder;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.shopify.sdk.client.ShopifyRestClient;
import com.shopify.sdk.model.draftorder.DraftOrder;
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
public class DraftOrderService {
    
    private final ShopifyRestClient restClient;
    private final ObjectMapper objectMapper;
    
    public Mono<List<DraftOrder>> getDraftOrders(String shop, String accessToken, String status, Integer limit) {
        String endpoint = "/admin/api/2023-10/draft_orders.json";
        Map<String, Object> params = Map.of(
            "status", status != null ? status : "open",
            "limit", limit != null ? limit : 50
        );
        
        return restClient.get(shop, accessToken, endpoint, params)
            .map(response -> {
                try {
                    var data = response.get("draft_orders");
                    return objectMapper.convertValue(data,
                        objectMapper.getTypeFactory().constructCollectionType(List.class, DraftOrder.class));
                } catch (Exception e) {
                    log.error("Failed to parse draft orders response", e);
                    throw new RuntimeException("Failed to parse draft orders response", e);
                }
            });
    }
    
    public Mono<DraftOrder> getDraftOrder(String shop, String accessToken, String draftOrderId) {
        String endpoint = "/admin/api/2023-10/draft_orders/" + draftOrderId + ".json";
        
        return restClient.get(shop, accessToken, endpoint)
            .map(response -> {
                try {
                    var data = response.get("draft_order");
                    return objectMapper.convertValue(data, DraftOrder.class);
                } catch (Exception e) {
                    log.error("Failed to parse draft order response", e);
                    throw new RuntimeException("Failed to parse draft order response", e);
                }
            });
    }
    
    public Mono<DraftOrder> createDraftOrder(String shop, String accessToken, DraftOrderCreateRequest request) {
        String endpoint = "/admin/api/2023-10/draft_orders.json";
        
        return restClient.post(shop, accessToken, endpoint, Map.of("draft_order", request))
            .map(response -> {
                try {
                    var data = response.get("draft_order");
                    return objectMapper.convertValue(data, DraftOrder.class);
                } catch (Exception e) {
                    log.error("Failed to parse create draft order response", e);
                    throw new RuntimeException("Failed to parse create draft order response", e);
                }
            });
    }
    
    public Mono<DraftOrder> updateDraftOrder(String shop, String accessToken, String draftOrderId, DraftOrderUpdateRequest request) {
        String endpoint = "/admin/api/2023-10/draft_orders/" + draftOrderId + ".json";
        
        return restClient.post(shop, accessToken, endpoint, Map.of("draft_order", request))
            .map(response -> {
                try {
                    var data = response.get("draft_order");
                    return objectMapper.convertValue(data, DraftOrder.class);
                } catch (Exception e) {
                    log.error("Failed to parse update draft order response", e);
                    throw new RuntimeException("Failed to parse update draft order response", e);
                }
            });
    }
    
    public Mono<Void> deleteDraftOrder(String shop, String accessToken, String draftOrderId) {
        String endpoint = "/admin/api/2023-10/draft_orders/" + draftOrderId + ".json";
        
        return restClient.delete(shop, accessToken, endpoint)
            .then();
    }
    
    public Mono<DraftOrder> completeDraftOrder(String shop, String accessToken, String draftOrderId, Boolean paymentPending) {
        String endpoint = "/admin/api/2023-10/draft_orders/" + draftOrderId + "/complete.json";
        Map<String, Object> body = paymentPending != null ? 
            Map.of("payment_pending", paymentPending) : 
            Map.of();
        
        return restClient.post(shop, accessToken, endpoint, body)
            .map(response -> {
                try {
                    var data = response.get("draft_order");
                    return objectMapper.convertValue(data, DraftOrder.class);
                } catch (Exception e) {
                    log.error("Failed to parse complete draft order response", e);
                    throw new RuntimeException("Failed to parse complete draft order response", e);
                }
            });
    }
    
    public Mono<String> sendDraftOrderInvoice(String shop, String accessToken, String draftOrderId, InvoiceRequest request) {
        String endpoint = "/admin/api/2023-10/draft_orders/" + draftOrderId + "/send_invoice.json";
        
        return restClient.post(shop, accessToken, endpoint, Map.of("draft_order_invoice", request))
            .map(response -> {
                try {
                    var data = response.get("draft_order_invoice");
                    return data.get("to").asText();
                } catch (Exception e) {
                    log.error("Failed to parse send invoice response", e);
                    throw new RuntimeException("Failed to parse send invoice response", e);
                }
            });
    }
    
    public Mono<Integer> getDraftOrderCount(String shop, String accessToken, String status) {
        String endpoint = "/admin/api/2023-10/draft_orders/count.json";
        Map<String, Object> params = status != null ? Map.of("status", status) : Map.of();
        
        return restClient.get(shop, accessToken, endpoint, params)
            .map(response -> {
                try {
                    return response.get("count").asInt();
                } catch (Exception e) {
                    log.error("Failed to parse draft order count response", e);
                    throw new RuntimeException("Failed to parse draft order count response", e);
                }
            });
    }
    
    // Convenience methods
    
    public Mono<List<DraftOrder>> getOpenDraftOrders(String shop, String accessToken) {
        return getDraftOrders(shop, accessToken, "open", null);
    }
    
    public Mono<List<DraftOrder>> getCompletedDraftOrders(String shop, String accessToken) {
        return getDraftOrders(shop, accessToken, "completed", null);
    }
    
    public Mono<List<DraftOrder>> getDraftOrdersByCustomer(String shop, String accessToken, String customerId) {
        return getDraftOrders(shop, accessToken, null, null)
            .map(draftOrders -> draftOrders.stream()
                .filter(draftOrder -> draftOrder.getCustomer() != null && 
                                     customerId.equals(draftOrder.getCustomer().getId()))
                .toList());
    }
    
    public Mono<List<DraftOrder>> getDraftOrdersAboveAmount(String shop, String accessToken, BigDecimal minAmount) {
        return getDraftOrders(shop, accessToken, null, null)
            .map(draftOrders -> draftOrders.stream()
                .filter(draftOrder -> draftOrder.getTotalPrice() != null && 
                                     draftOrder.getTotalPrice().compareTo(minAmount) >= 0)
                .toList());
    }
    
    @lombok.Data
    @lombok.NoArgsConstructor
    @lombok.AllArgsConstructor
    @lombok.Builder
    public static class DraftOrderCreateRequest {
        private List<DraftOrderLineItemRequest> lineItems;
        private DraftOrder.Customer customer;
        private DraftOrder.Address shippingAddress;
        private DraftOrder.Address billingAddress;
        private String note;
        private String email;
        private String currency;
        private String invoiceUrl;
        private DraftOrder.AppliedDiscount appliedDiscount;
        private DraftOrder.ShippingLine shippingLine;
        private List<DraftOrder.TaxLine> taxLines;
        private String tags;
        private List<DraftOrder.NoteAttribute> noteAttributes;
        private Boolean taxExempt;
        private Boolean taxesIncluded;
        private Boolean useCustomerDefaultAddress;
        
        @lombok.Data
        @lombok.NoArgsConstructor
        @lombok.AllArgsConstructor
        @lombok.Builder
        public static class DraftOrderLineItemRequest {
            private String variantId;
            private String productId;
            private String title;
            private String variantTitle;
            private String sku;
            private String vendor;
            private Integer quantity;
            private Boolean requiresShipping;
            private Boolean taxable;
            private Boolean giftCard;
            private String fulfillmentService;
            private Integer grams;
            private List<DraftOrder.TaxLine> taxLines;
            private DraftOrder.AppliedDiscount appliedDiscount;
            private String name;
            private Boolean custom;
            private BigDecimal price;
            private List<DraftOrder.LineItemProperty> properties;
        }
    }
    
    @lombok.Data
    @lombok.NoArgsConstructor
    @lombok.AllArgsConstructor
    @lombok.Builder
    public static class DraftOrderUpdateRequest {
        private List<DraftOrderCreateRequest.DraftOrderLineItemRequest> lineItems;
        private DraftOrder.Customer customer;
        private DraftOrder.Address shippingAddress;
        private DraftOrder.Address billingAddress;
        private String note;
        private String email;
        private String currency;
        private String invoiceUrl;
        private DraftOrder.AppliedDiscount appliedDiscount;
        private DraftOrder.ShippingLine shippingLine;
        private List<DraftOrder.TaxLine> taxLines;
        private String tags;
        private List<DraftOrder.NoteAttribute> noteAttributes;
        private Boolean taxExempt;
        private Boolean taxesIncluded;
        private Boolean useCustomerDefaultAddress;
    }
    
    @lombok.Data
    @lombok.NoArgsConstructor
    @lombok.AllArgsConstructor
    @lombok.Builder
    public static class InvoiceRequest {
        private String to;
        private String from;
        private String subject;
        private String customMessage;
        private String bcc;
    }
}