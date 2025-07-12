package com.shopify.sdk.model.fulfillment;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Builder;

import java.time.Instant;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Fulfillment {
    
    private String id;
    
    @JsonProperty("order_id")
    private String orderId;
    
    private FulfillmentStatus status;
    
    @JsonProperty("created_at")
    private Instant createdAt;
    
    @JsonProperty("updated_at")
    private Instant updatedAt;
    
    @JsonProperty("tracking_company")
    private String trackingCompany;
    
    @JsonProperty("tracking_number")
    private String trackingNumber;
    
    @JsonProperty("tracking_numbers")
    private List<String> trackingNumbers;
    
    @JsonProperty("tracking_url")
    private String trackingUrl;
    
    @JsonProperty("tracking_urls")
    private List<String> trackingUrls;
    
    private Receipt receipt;
    
    @JsonProperty("name")
    private String name;
    
    @JsonProperty("service")
    private String service;
    
    @JsonProperty("shipment_status")
    private String shipmentStatus;
    
    @JsonProperty("location_id")
    private String locationId;
    
    @JsonProperty("origin_address")
    private Address originAddress;
    
    @JsonProperty("destination")
    private Address destination;
    
    @JsonProperty("line_items")
    private List<FulfillmentLineItem> lineItems;
    
    @JsonProperty("notify_customer")
    private Boolean notifyCustomer;
    
    @JsonProperty("admin_graphql_api_id")
    private String adminGraphqlApiId;
    
    public boolean hasTracking() {
        return trackingNumber != null || (trackingNumbers != null && !trackingNumbers.isEmpty());
    }
    
    public boolean isCompleted() {
        return status != null && status.isCompleted();
    }
    
    public boolean isFailed() {
        return status != null && status.isFailed();
    }
    
    public boolean isPending() {
        return status != null && status.isPending();
    }
    
    public int getItemCount() {
        return lineItems != null ? lineItems.size() : 0;
    }
    
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class Receipt {
        @JsonProperty("testcase")
        private Boolean testcase;
        
        @JsonProperty("authorization")
        private String authorization;
    }
    
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class Address {
        private String address1;
        private String address2;
        private String city;
        private String company;
        private String country;
        
        @JsonProperty("country_code")
        private String countryCode;
        
        @JsonProperty("first_name")
        private String firstName;
        
        @JsonProperty("last_name")
        private String lastName;
        
        private String phone;
        private String province;
        
        @JsonProperty("province_code")
        private String provinceCode;
        
        private String zip;
        
        public String getFullName() {
            StringBuilder name = new StringBuilder();
            if (firstName != null) {
                name.append(firstName);
            }
            if (lastName != null) {
                if (name.length() > 0) name.append(" ");
                name.append(lastName);
            }
            return name.toString();
        }
    }
    
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class FulfillmentLineItem {
        private String id;
        
        @JsonProperty("variant_id")
        private String variantId;
        
        private String title;
        private Integer quantity;
        
        @JsonProperty("sku")
        private String sku;
        
        @JsonProperty("variant_title")
        private String variantTitle;
        
        @JsonProperty("vendor")
        private String vendor;
        
        @JsonProperty("fulfillment_service")
        private String fulfillmentService;
        
        @JsonProperty("product_id")
        private String productId;
        
        @JsonProperty("requires_shipping")
        private Boolean requiresShipping;
        
        @JsonProperty("taxable")
        private Boolean taxable;
        
        @JsonProperty("gift_card")
        private Boolean giftCard;
        
        @JsonProperty("name")
        private String name;
        
        @JsonProperty("variant_inventory_management")
        private String variantInventoryManagement;
        
        @JsonProperty("properties")
        private List<LineItemProperty> properties;
        
        @JsonProperty("product_exists")
        private Boolean productExists;
        
        @JsonProperty("fulfillable_quantity")
        private Integer fulfillableQuantity;
        
        @JsonProperty("grams")
        private Integer grams;
        
        @JsonProperty("price")
        private String price;
        
        @JsonProperty("total_discount")
        private String totalDiscount;
        
        @JsonProperty("fulfillment_status")
        private String fulfillmentStatus;
        
        @JsonProperty("price_set")
        private PriceSet priceSet;
        
        @JsonProperty("total_discount_set")
        private PriceSet totalDiscountSet;
        
        @JsonProperty("discount_allocations")
        private List<DiscountAllocation> discountAllocations;
        
        @JsonProperty("duties")
        private List<Duty> duties;
        
        @JsonProperty("admin_graphql_api_id")
        private String adminGraphqlApiId;
        
        @JsonProperty("tax_lines")
        private List<TaxLine> taxLines;
        
        public boolean isGiftCard() {
            return giftCard != null && giftCard;
        }
        
        public boolean requiresShipping() {
            return requiresShipping == null || requiresShipping;
        }
        
        public boolean isTaxable() {
            return taxable == null || taxable;
        }
    }
    
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class LineItemProperty {
        private String name;
        private String value;
    }
    
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class PriceSet {
        @JsonProperty("shop_money")
        private Money shopMoney;
        
        @JsonProperty("presentment_money")
        private Money presentmentMoney;
    }
    
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class Money {
        private String amount;
        
        @JsonProperty("currency_code")
        private String currencyCode;
    }
    
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class DiscountAllocation {
        private String amount;
        
        @JsonProperty("amount_set")
        private PriceSet amountSet;
        
        @JsonProperty("discount_application_index")
        private Integer discountApplicationIndex;
    }
    
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class Duty {
        private String id;
        
        @JsonProperty("harmonized_system_code")
        private String harmonizedSystemCode;
        
        @JsonProperty("country_code_of_origin")
        private String countryCodeOfOrigin;
        
        @JsonProperty("shop_money")
        private Money shopMoney;
        
        @JsonProperty("presentment_money")
        private Money presentmentMoney;
        
        @JsonProperty("tax_lines")
        private List<TaxLine> taxLines;
        
        @JsonProperty("admin_graphql_api_id")
        private String adminGraphqlApiId;
    }
    
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class TaxLine {
        private String title;
        private String price;
        private Double rate;
        
        @JsonProperty("price_set")
        private PriceSet priceSet;
        
        @JsonProperty("channel_liable")
        private Boolean channelLiable;
    }
}