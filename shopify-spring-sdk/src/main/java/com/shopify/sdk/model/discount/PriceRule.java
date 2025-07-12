package com.shopify.sdk.model.discount;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Builder;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PriceRule {
    
    private String id;
    private String title;
    
    @JsonProperty("target_type")
    private TargetType targetType;
    
    @JsonProperty("target_selection")
    private TargetSelection targetSelection;
    
    @JsonProperty("allocation_method")
    private AllocationMethod allocationMethod;
    
    @JsonProperty("value_type")
    private ValueType valueType;
    
    private BigDecimal value;
    
    @JsonProperty("once_per_customer")
    private Boolean oncePerCustomer;
    
    @JsonProperty("usage_limit")
    private Integer usageLimit;
    
    @JsonProperty("customer_selection")
    private CustomerSelection customerSelection;
    
    @JsonProperty("starts_at")
    private Instant startsAt;
    
    @JsonProperty("ends_at")
    private Instant endsAt;
    
    @JsonProperty("created_at")
    private Instant createdAt;
    
    @JsonProperty("updated_at")
    private Instant updatedAt;
    
    @JsonProperty("entitled_product_ids")
    private List<String> entitledProductIds;
    
    @JsonProperty("entitled_variant_ids")
    private List<String> entitledVariantIds;
    
    @JsonProperty("entitled_collection_ids")
    private List<String> entitledCollectionIds;
    
    @JsonProperty("entitled_country_ids")
    private List<String> entitledCountryIds;
    
    @JsonProperty("prerequisite_product_ids")
    private List<String> prerequisiteProductIds;
    
    @JsonProperty("prerequisite_variant_ids")
    private List<String> prerequisiteVariantIds;
    
    @JsonProperty("prerequisite_collection_ids")
    private List<String> prerequisiteCollectionIds;
    
    @JsonProperty("prerequisite_customer_ids")
    private List<String> prerequisiteCustomerIds;
    
    @JsonProperty("prerequisite_subtotal_range")
    private MoneyRange prerequisiteSubtotalRange;
    
    @JsonProperty("prerequisite_quantity_range")
    private QuantityRange prerequisiteQuantityRange;
    
    @JsonProperty("prerequisite_shipping_price_range")
    private MoneyRange prerequisiteShippingPriceRange;
    
    @JsonProperty("prerequisite_to_entitlement_quantity_ratio")
    private QuantityRatio prerequisiteToEntitlementQuantityRatio;
    
    @JsonProperty("prerequisite_to_entitlement_purchase")
    private PrerequisiteToEntitlementPurchase prerequisiteToEntitlementPurchase;
    
    @JsonProperty("admin_graphql_api_id")
    private String adminGraphqlApiId;
    
    public boolean isActive() {
        Instant now = Instant.now();
        return (startsAt == null || startsAt.isBefore(now) || startsAt.equals(now)) &&
               (endsAt == null || endsAt.isAfter(now));
    }
    
    public boolean hasUsageLimit() {
        return usageLimit != null && usageLimit > 0;
    }
    
    public boolean isPercentageDiscount() {
        return valueType == ValueType.PERCENTAGE;
    }
    
    public boolean isFixedAmountDiscount() {
        return valueType == ValueType.FIXED_AMOUNT;
    }
    
    public boolean targetsLineItems() {
        return targetType == TargetType.LINE_ITEM;
    }
    
    public boolean targetsShipping() {
        return targetType == TargetType.SHIPPING_LINE;
    }
    
    public enum TargetType {
        @JsonProperty("line_item")
        LINE_ITEM,
        
        @JsonProperty("shipping_line")
        SHIPPING_LINE
    }
    
    public enum TargetSelection {
        @JsonProperty("all")
        ALL,
        
        @JsonProperty("entitled")
        ENTITLED
    }
    
    public enum AllocationMethod {
        @JsonProperty("each")
        EACH,
        
        @JsonProperty("across")
        ACROSS
    }
    
    public enum ValueType {
        @JsonProperty("percentage")
        PERCENTAGE,
        
        @JsonProperty("fixed_amount")
        FIXED_AMOUNT
    }
    
    public enum CustomerSelection {
        @JsonProperty("all")
        ALL,
        
        @JsonProperty("prerequisite")
        PREREQUISITE
    }
    
    public enum PrerequisiteToEntitlementPurchase {
        @JsonProperty("all")
        ALL,
        
        @JsonProperty("entitled")
        ENTITLED
    }
    
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class MoneyRange {
        @JsonProperty("greater_than_or_equal_to")
        private BigDecimal greaterThanOrEqualTo;
        
        @JsonProperty("less_than_or_equal_to")
        private BigDecimal lessThanOrEqualTo;
    }
    
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class QuantityRange {
        @JsonProperty("greater_than_or_equal_to")
        private Integer greaterThanOrEqualTo;
        
        @JsonProperty("less_than_or_equal_to")
        private Integer lessThanOrEqualTo;
    }
    
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class QuantityRatio {
        @JsonProperty("prerequisite_quantity")
        private Integer prerequisiteQuantity;
        
        @JsonProperty("entitled_quantity")
        private Integer entitledQuantity;
    }
}