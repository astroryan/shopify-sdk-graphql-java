package com.shopify.sdk.model.markets;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.shopify.sdk.model.common.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MarketLocalization {
    
    @JsonProperty("key")
    private String key;
    
    @JsonProperty("outdated")
    private Boolean outdated;
    
    @JsonProperty("value")
    private String value;
}

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MarketLocalizableContent {
    
    @JsonProperty("key")
    private String key;
    
    @JsonProperty("value")
    private String value;
    
    @JsonProperty("digest")
    private String digest;
    
    @JsonProperty("locale")
    private String locale;
}

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MarketLocalizableResource {
    
    @JsonProperty("marketId")
    private ID marketId;
    
    @JsonProperty("marketLocalizations")
    private List<MarketLocalization> marketLocalizations;
    
    @JsonProperty("resourceId")
    private ID resourceId;
    
    @JsonProperty("resourceType")
    private MarketLocalizableResourceType resourceType;
    
    @JsonProperty("translatableContent")
    private List<MarketLocalizableContent> translatableContent;
}

public enum MarketLocalizableResourceType {
    COLLECTION,
    LINK,
    MENU,
    METAFIELD,
    METAOBJECT,
    ONLINE_STORE_ARTICLE,
    ONLINE_STORE_BLOG,
    ONLINE_STORE_PAGE,
    ONLINE_STORE_THEME,
    PACKING_SLIP_TEMPLATE,
    PAYMENT_GATEWAY,
    PRODUCT,
    PRODUCT_OPTION,
    PRODUCT_OPTION_VALUE,
    PRODUCT_VARIANT,
    SELLING_PLAN,
    SELLING_PLAN_GROUP,
    SHOP,
    SHOP_POLICY
}

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MarketRegionalSubdivision {
    
    @JsonProperty("code")
    private String code;
    
    @JsonProperty("name")
    private String name;
}

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SellingPlanPricingPolicy {
    
    @JsonProperty("__typename")
    private String typename;
}

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SellingPlanRecurringPricingPolicy implements SellingPlanPricingPolicy {
    
    @JsonProperty("adjustmentType")
    private SellingPlanPricingPolicyAdjustmentType adjustmentType;
    
    @JsonProperty("adjustmentValue")
    private SellingPlanPricingPolicyAdjustmentValue adjustmentValue;
    
    @JsonProperty("afterCycle")
    private Integer afterCycle;
    
    @JsonProperty("createdAt")
    private DateTime createdAt;
}

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SellingPlanFixedPricingPolicy implements SellingPlanPricingPolicy {
    
    @JsonProperty("adjustmentType")
    private SellingPlanPricingPolicyAdjustmentType adjustmentType;
    
    @JsonProperty("adjustmentValue")
    private SellingPlanPricingPolicyAdjustmentValue adjustmentValue;
    
    @JsonProperty("createdAt")
    private DateTime createdAt;
}

public enum SellingPlanPricingPolicyAdjustmentType {
    PERCENTAGE,
    FIXED_AMOUNT,
    PRICE
}

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SellingPlanPricingPolicyAdjustmentValue {
    
    @JsonProperty("__typename")
    private String typename;
}

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SellingPlanPricingPolicyPercentageValue implements SellingPlanPricingPolicyAdjustmentValue {
    
    @JsonProperty("percentage")
    private Double percentage;
}

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MoneyV2 implements SellingPlanPricingPolicyAdjustmentValue {
    
    @JsonProperty("amount")
    private String amount;
    
    @JsonProperty("currencyCode")
    private CurrencyCode currencyCode;
}

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SellingPlan {
    
    @JsonProperty("id")
    private ID id;
    
    @JsonProperty("billingPolicy")
    private SellingPlanBillingPolicy billingPolicy;
    
    @JsonProperty("category")
    private SellingPlanCategory category;
    
    @JsonProperty("createdAt")
    private DateTime createdAt;
    
    @JsonProperty("deliveryPolicy")
    private SellingPlanDeliveryPolicy deliveryPolicy;
    
    @JsonProperty("description")
    private String description;
    
    @JsonProperty("inventoryPolicy")
    private SellingPlanInventoryPolicy inventoryPolicy;
    
    @JsonProperty("name")
    private String name;
    
    @JsonProperty("options")
    private List<String> options;
    
    @JsonProperty("position")
    private Integer position;
    
    @JsonProperty("pricingPolicies")
    private List<SellingPlanPricingPolicy> pricingPolicies;
}

public enum SellingPlanCategory {
    PRE_ORDER,
    SUBSCRIPTION,
    TRY_BEFORE_YOU_BUY,
    OTHER
}

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SellingPlanBillingPolicy {
    
    @JsonProperty("__typename")
    private String typename;
}

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SellingPlanRecurringBillingPolicy implements SellingPlanBillingPolicy {
    
    @JsonProperty("anchors")
    private List<SellingPlanAnchor> anchors;
    
    @JsonProperty("createdAt")
    private DateTime createdAt;
    
    @JsonProperty("interval")
    private SellingPlanInterval interval;
    
    @JsonProperty("intervalCount")
    private Integer intervalCount;
    
    @JsonProperty("maxCycles")
    private Integer maxCycles;
    
    @JsonProperty("minCycles")
    private Integer minCycles;
}

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SellingPlanFixedBillingPolicy implements SellingPlanBillingPolicy {
    
    @JsonProperty("checkoutCharge")
    private SellingPlanCheckoutCharge checkoutCharge;
    
    @JsonProperty("remainingBalanceChargeTrigger")
    private SellingPlanRemainingBalanceChargeTrigger remainingBalanceChargeTrigger;
}

public enum SellingPlanInterval {
    DAY,
    WEEK,
    MONTH,
    YEAR
}

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SellingPlanAnchor {
    
    @JsonProperty("cutoffDay")
    private Integer cutoffDay;
    
    @JsonProperty("day")
    private Integer day;
    
    @JsonProperty("month")
    private Integer month;
    
    @JsonProperty("type")
    private SellingPlanAnchorType type;
}

public enum SellingPlanAnchorType {
    WEEKDAY,
    MONTHDAY,
    YEARDAY
}

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SellingPlanCheckoutCharge {
    
    @JsonProperty("type")
    private SellingPlanCheckoutChargeType type;
    
    @JsonProperty("value")
    private SellingPlanCheckoutChargeValue value;
}

public enum SellingPlanCheckoutChargeType {
    PERCENTAGE,
    PRICE
}

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SellingPlanCheckoutChargeValue {
    
    @JsonProperty("__typename")
    private String typename;
}

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SellingPlanCheckoutChargePercentageValue implements SellingPlanCheckoutChargeValue {
    
    @JsonProperty("percentage")
    private Double percentage;
}

public enum SellingPlanRemainingBalanceChargeTrigger {
    NO_REMAINING_BALANCE,
    EXACT_TIME,
    TIME_AFTER_CHECKOUT
}

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SellingPlanDeliveryPolicy {
    
    @JsonProperty("__typename")
    private String typename;
}

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SellingPlanRecurringDeliveryPolicy implements SellingPlanDeliveryPolicy {
    
    @JsonProperty("anchors")
    private List<SellingPlanAnchor> anchors;
    
    @JsonProperty("createdAt")
    private DateTime createdAt;
    
    @JsonProperty("cutoff")
    private Integer cutoff;
    
    @JsonProperty("intent")
    private SellingPlanRecurringDeliveryPolicyIntent intent;
    
    @JsonProperty("interval")
    private SellingPlanInterval interval;
    
    @JsonProperty("intervalCount")
    private Integer intervalCount;
}

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SellingPlanFixedDeliveryPolicy implements SellingPlanDeliveryPolicy {
    
    @JsonProperty("anchors")
    private List<SellingPlanAnchor> anchors;
    
    @JsonProperty("cutoff")
    private Integer cutoff;
    
    @JsonProperty("fulfillmentExactTime")
    private DateTime fulfillmentExactTime;
    
    @JsonProperty("fulfillmentTrigger")
    private SellingPlanFulfillmentTrigger fulfillmentTrigger;
    
    @JsonProperty("intent")
    private SellingPlanFixedDeliveryPolicyIntent intent;
}

public enum SellingPlanRecurringDeliveryPolicyIntent {
    FULFILLMENT_BEGIN
}

public enum SellingPlanFixedDeliveryPolicyIntent {
    FULFILLMENT_BEGIN
}

public enum SellingPlanFulfillmentTrigger {
    ANCHOR,
    ASAP,
    EXACT_TIME,
    UNKNOWN
}

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SellingPlanInventoryPolicy {
    
    @JsonProperty("reserve")
    private SellingPlanReserve reserve;
}

public enum SellingPlanReserve {
    ON_FULFILLMENT,
    ON_SALE
}

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SellingPlanGroup {
    
    @JsonProperty("id")
    private ID id;
    
    @JsonProperty("appId")
    private String appId;
    
    @JsonProperty("appliesToProduct")
    private Boolean appliesToProduct;
    
    @JsonProperty("appliesToProductVariant")
    private Boolean appliesToProductVariant;
    
    @JsonProperty("appliesToProductVariants")
    private Boolean appliesToProductVariants;
    
    @JsonProperty("createdAt")
    private DateTime createdAt;
    
    @JsonProperty("description")
    private String description;
    
    @JsonProperty("merchantCode")
    private String merchantCode;
    
    @JsonProperty("name")
    private String name;
    
    @JsonProperty("options")
    private List<String> options;
    
    @JsonProperty("position")
    private Integer position;
    
    @JsonProperty("productCount")
    private Integer productCount;
    
    @JsonProperty("productVariantCount")
    private Integer productVariantCount;
    
    @JsonProperty("productVariants")
    private ProductVariantConnection productVariants;
    
    @JsonProperty("products")
    private ProductConnection products;
    
    @JsonProperty("sellingPlans")
    private SellingPlanConnection sellingPlans;
    
    @JsonProperty("summary")
    private String summary;
}

// Connection types
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MarketLocalizableResourceConnection {
    
    @JsonProperty("edges")
    private List<MarketLocalizableResourceEdge> edges;
    
    @JsonProperty("pageInfo")
    private PageInfo pageInfo;
}

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MarketLocalizableResourceEdge {
    
    @JsonProperty("node")
    private MarketLocalizableResource node;
    
    @JsonProperty("cursor")
    private String cursor;
}

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SellingPlanConnection {
    
    @JsonProperty("edges")
    private List<SellingPlanEdge> edges;
    
    @JsonProperty("pageInfo")
    private PageInfo pageInfo;
}

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SellingPlanEdge {
    
    @JsonProperty("node")
    private SellingPlan node;
    
    @JsonProperty("cursor")
    private String cursor;
}

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SellingPlanGroupConnection {
    
    @JsonProperty("edges")
    private List<SellingPlanGroupEdge> edges;
    
    @JsonProperty("pageInfo")
    private PageInfo pageInfo;
}

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SellingPlanGroupEdge {
    
    @JsonProperty("node")
    private SellingPlanGroup node;
    
    @JsonProperty("cursor")
    private String cursor;
}

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductVariantConnection {
    
    @JsonProperty("edges")
    private List<ProductVariantEdge> edges;
    
    @JsonProperty("pageInfo")
    private PageInfo pageInfo;
}

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductVariantEdge {
    
    @JsonProperty("node")
    private ProductVariant node;
    
    @JsonProperty("cursor")
    private String cursor;
}

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductConnection {
    
    @JsonProperty("edges")
    private List<ProductEdge> edges;
    
    @JsonProperty("pageInfo")
    private PageInfo pageInfo;
}

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductEdge {
    
    @JsonProperty("node")
    private Product node;
    
    @JsonProperty("cursor")
    private String cursor;
}

// Input classes
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MarketLocalizationRegisterInput {
    
    @JsonProperty("marketId")
    private ID marketId;
    
    @JsonProperty("key")
    private String key;
    
    @JsonProperty("value")
    private String value;
    
    @JsonProperty("marketLocalizableContentDigest")
    private String marketLocalizableContentDigest;
    
    @JsonProperty("ifNotExists")
    private Boolean ifNotExists;
}

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MarketLocalizationRemoveInput {
    
    @JsonProperty("marketLocalizationKeys")
    private List<String> marketLocalizationKeys;
    
    @JsonProperty("marketId")
    private ID marketId;
    
    @JsonProperty("resourceId")
    private ID resourceId;
}

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MarketRegionCreateInput {
    
    @JsonProperty("marketId")
    private ID marketId;
    
    @JsonProperty("countries")
    private List<String> countries;
}

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MarketRegionDeleteInput {
    
    @JsonProperty("id")
    private ID id;
}

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MarketRegionUpdateInput {
    
    @JsonProperty("id")
    private ID id;
    
    @JsonProperty("countries")
    private List<String> countries;
}

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SellingPlanGroupInput {
    
    @JsonProperty("appId")
    private String appId;
    
    @JsonProperty("description")
    private String description;
    
    @JsonProperty("merchantCode")
    private String merchantCode;
    
    @JsonProperty("name")
    private String name;
    
    @JsonProperty("options")
    private List<String> options;
    
    @JsonProperty("position")
    private Long position;
    
    @JsonProperty("sellingPlansToCreate")
    private List<SellingPlanInput> sellingPlansToCreate;
    
    @JsonProperty("sellingPlansToDelete")
    private List<ID> sellingPlansToDelete;
    
    @JsonProperty("sellingPlansToUpdate")
    private List<SellingPlanInput> sellingPlansToUpdate;
}

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SellingPlanInput {
    
    @JsonProperty("id")
    private ID id;
    
    @JsonProperty("billingPolicy")
    private SellingPlanBillingPolicyInput billingPolicy;
    
    @JsonProperty("category")
    private SellingPlanCategory category;
    
    @JsonProperty("deliveryPolicy")
    private SellingPlanDeliveryPolicyInput deliveryPolicy;
    
    @JsonProperty("description")
    private String description;
    
    @JsonProperty("inventoryPolicy")
    private SellingPlanInventoryPolicyInput inventoryPolicy;
    
    @JsonProperty("name")
    private String name;
    
    @JsonProperty("options")
    private List<String> options;
    
    @JsonProperty("position")
    private Long position;
    
    @JsonProperty("pricingPolicies")
    private List<SellingPlanPricingPolicyInput> pricingPolicies;
}

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SellingPlanBillingPolicyInput {
    
    @JsonProperty("fixed")
    private SellingPlanFixedBillingPolicyInput fixed;
    
    @JsonProperty("recurring")
    private SellingPlanRecurringBillingPolicyInput recurring;
}

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SellingPlanFixedBillingPolicyInput {
    
    @JsonProperty("checkoutCharge")
    private SellingPlanCheckoutChargeInput checkoutCharge;
    
    @JsonProperty("remainingBalanceChargeTrigger")
    private SellingPlanRemainingBalanceChargeTrigger remainingBalanceChargeTrigger;
}

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SellingPlanRecurringBillingPolicyInput {
    
    @JsonProperty("anchors")
    private List<SellingPlanAnchorInput> anchors;
    
    @JsonProperty("interval")
    private SellingPlanInterval interval;
    
    @JsonProperty("intervalCount")
    private Integer intervalCount;
    
    @JsonProperty("maxCycles")
    private Integer maxCycles;
    
    @JsonProperty("minCycles")
    private Integer minCycles;
}

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SellingPlanCheckoutChargeInput {
    
    @JsonProperty("type")
    private SellingPlanCheckoutChargeType type;
    
    @JsonProperty("value")
    private SellingPlanCheckoutChargeValueInput value;
}

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SellingPlanCheckoutChargeValueInput {
    
    @JsonProperty("fixedValue")
    private String fixedValue;
    
    @JsonProperty("percentage")
    private Double percentage;
}

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SellingPlanAnchorInput {
    
    @JsonProperty("cutoffDay")
    private Integer cutoffDay;
    
    @JsonProperty("day")
    private Integer day;
    
    @JsonProperty("month")
    private Integer month;
    
    @JsonProperty("type")
    private SellingPlanAnchorType type;
}

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SellingPlanDeliveryPolicyInput {
    
    @JsonProperty("fixed")
    private SellingPlanFixedDeliveryPolicyInput fixed;
    
    @JsonProperty("recurring")
    private SellingPlanRecurringDeliveryPolicyInput recurring;
}

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SellingPlanFixedDeliveryPolicyInput {
    
    @JsonProperty("anchors")
    private List<SellingPlanAnchorInput> anchors;
    
    @JsonProperty("cutoff")
    private Integer cutoff;
    
    @JsonProperty("fulfillmentExactTime")
    private DateTime fulfillmentExactTime;
    
    @JsonProperty("fulfillmentTrigger")
    private SellingPlanFulfillmentTrigger fulfillmentTrigger;
    
    @JsonProperty("intent")
    private SellingPlanFixedDeliveryPolicyIntent intent;
}

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SellingPlanRecurringDeliveryPolicyInput {
    
    @JsonProperty("anchors")
    private List<SellingPlanAnchorInput> anchors;
    
    @JsonProperty("cutoff")
    private Integer cutoff;
    
    @JsonProperty("intent")
    private SellingPlanRecurringDeliveryPolicyIntent intent;
    
    @JsonProperty("interval")
    private SellingPlanInterval interval;
    
    @JsonProperty("intervalCount")
    private Integer intervalCount;
}

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SellingPlanInventoryPolicyInput {
    
    @JsonProperty("reserve")
    private SellingPlanReserve reserve;
}

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SellingPlanPricingPolicyInput {
    
    @JsonProperty("fixed")
    private SellingPlanFixedPricingPolicyInput fixed;
    
    @JsonProperty("recurring")
    private SellingPlanRecurringPricingPolicyInput recurring;
}

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SellingPlanFixedPricingPolicyInput {
    
    @JsonProperty("adjustmentType")
    private SellingPlanPricingPolicyAdjustmentType adjustmentType;
    
    @JsonProperty("adjustmentValue")
    private SellingPlanPricingPolicyValueInput adjustmentValue;
}

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SellingPlanRecurringPricingPolicyInput {
    
    @JsonProperty("adjustmentType")
    private SellingPlanPricingPolicyAdjustmentType adjustmentType;
    
    @JsonProperty("adjustmentValue")
    private SellingPlanPricingPolicyValueInput adjustmentValue;
    
    @JsonProperty("afterCycle")
    private Integer afterCycle;
}

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SellingPlanPricingPolicyValueInput {
    
    @JsonProperty("fixedValue")
    private String fixedValue;
    
    @JsonProperty("percentage")
    private Double percentage;
}

// Simplified model references
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Product {
    
    @JsonProperty("id")
    private ID id;
    
    @JsonProperty("title")
    private String title;
}

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductVariant {
    
    @JsonProperty("id")
    private ID id;
    
    @JsonProperty("title")
    private String title;
}