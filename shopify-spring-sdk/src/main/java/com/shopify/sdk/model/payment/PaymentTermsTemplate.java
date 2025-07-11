package com.shopify.sdk.model.payment;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.shopify.sdk.model.common.ID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PaymentTermsTemplate {
    
    @JsonProperty("id")
    private ID id;
    
    @JsonProperty("name")
    private String name;
    
    @JsonProperty("paymentTermsType")
    private PaymentTermsType paymentTermsType;
    
    @JsonProperty("dueInDays")
    private Integer dueInDays;
    
    @JsonProperty("description")
    private String description;
}

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PaymentSettings {
    
    @JsonProperty("supportedDigitalWallets")
    private List<DigitalWallet> supportedDigitalWallets;
}

public enum DigitalWallet {
    ANDROID_PAY,
    APPLE_PAY,
    GOOGLE_PAY,
    SHOPIFY_PAY
}

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RefundDuty {
    
    @JsonProperty("amountSet")
    private MoneyBag amountSet;
    
    @JsonProperty("dutyId")
    private String dutyId;
}

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RefundLineItem {
    
    @JsonProperty("lineItemId")
    private String lineItemId;
    
    @JsonProperty("locationId")
    private String locationId;
    
    @JsonProperty("quantity")
    private Integer quantity;
    
    @JsonProperty("restockType")
    private RefundLineItemRestockType restockType;
}

public enum RefundLineItemRestockType {
    NO_RESTOCK,
    CANCEL,
    RETURN
}

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MoneyBag {
    
    @JsonProperty("presentmentMoney")
    private MoneyInput presentmentMoney;
    
    @JsonProperty("shopMoney")
    private MoneyInput shopMoney;
}

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MoneyInput {
    
    @JsonProperty("amount")
    private String amount;
    
    @JsonProperty("currencyCode")
    private String currencyCode;
}

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SellingPlanGroupInput {
    
    @JsonProperty("name")
    private String name;
    
    @JsonProperty("merchantCode")
    private String merchantCode;
    
    @JsonProperty("options")
    private List<String> options;
    
    @JsonProperty("position")
    private Integer position;
    
    @JsonProperty("sellingPlansToCreate")
    private List<SellingPlanInput> sellingPlansToCreate;
}

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SellingPlanInput {
    
    @JsonProperty("name")
    private String name;
    
    @JsonProperty("options")
    private List<String> options;
    
    @JsonProperty("position")
    private Integer position;
    
    @JsonProperty("billingPolicy")
    private SellingPlanBillingPolicyInput billingPolicy;
    
    @JsonProperty("deliveryPolicy")
    private SellingPlanDeliveryPolicyInput deliveryPolicy;
    
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
    
    @JsonProperty("remainingBalanceChargeExactTime")
    private String remainingBalanceChargeExactTime;
    
    @JsonProperty("remainingBalanceChargeTimeAfterCheckout")
    private String remainingBalanceChargeTimeAfterCheckout;
    
    @JsonProperty("remainingBalanceChargeTrigger")
    private SellingPlanRemainingBalanceChargeTrigger remainingBalanceChargeTrigger;
}

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SellingPlanRecurringBillingPolicyInput {
    
    @JsonProperty("interval")
    private SellingPlanInterval interval;
    
    @JsonProperty("intervalCount")
    private Integer intervalCount;
    
    @JsonProperty("anchors")
    private List<SellingPlanAnchorInput> anchors;
    
    @JsonProperty("minCycles")
    private Integer minCycles;
    
    @JsonProperty("maxCycles")
    private Integer maxCycles;
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

public enum SellingPlanCheckoutChargeType {
    PERCENTAGE,
    PRICE
}

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SellingPlanCheckoutChargeValueInput {
    
    @JsonProperty("fixedValue")
    private String fixedValue;
    
    @JsonProperty("percentage")
    private String percentage;
}

public enum SellingPlanRemainingBalanceChargeTrigger {
    EXACT_TIME,
    NO_REMAINING_BALANCE,
    TIME_AFTER_CHECKOUT
}

public enum SellingPlanInterval {
    DAY,
    MONTH,
    WEEK,
    YEAR
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

public enum SellingPlanAnchorType {
    MONTHDAY,
    WEEKDAY,
    YEARDAY
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
    private String cutoff;
    
    @JsonProperty("fulfillmentExactTime")
    private String fulfillmentExactTime;
    
    @JsonProperty("fulfillmentTrigger")
    private SellingPlanFulfillmentTrigger fulfillmentTrigger;
    
    @JsonProperty("intent")
    private SellingPlanFixedDeliveryPolicyIntent intent;
    
    @JsonProperty("preAnchorBehavior")
    private SellingPlanFixedDeliveryPolicyPreAnchorBehavior preAnchorBehavior;
}

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SellingPlanRecurringDeliveryPolicyInput {
    
    @JsonProperty("anchors")
    private List<SellingPlanAnchorInput> anchors;
    
    @JsonProperty("cutoff")
    private String cutoff;
    
    @JsonProperty("intent")
    private SellingPlanRecurringDeliveryPolicyIntent intent;
    
    @JsonProperty("interval")
    private SellingPlanInterval interval;
    
    @JsonProperty("intervalCount")
    private Integer intervalCount;
    
    @JsonProperty("preAnchorBehavior")
    private SellingPlanRecurringDeliveryPolicyPreAnchorBehavior preAnchorBehavior;
}

public enum SellingPlanFulfillmentTrigger {
    ANCHOR,
    ASAP,
    EXACT_TIME,
    UNKNOWN
}

public enum SellingPlanFixedDeliveryPolicyIntent {
    FULFILLMENT_BEGIN
}

public enum SellingPlanFixedDeliveryPolicyPreAnchorBehavior {
    ASAP,
    NEXT
}

public enum SellingPlanRecurringDeliveryPolicyIntent {
    FULFILLMENT_BEGIN
}

public enum SellingPlanRecurringDeliveryPolicyPreAnchorBehavior {
    ASAP,
    NEXT
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

public enum SellingPlanPricingPolicyAdjustmentType {
    PERCENTAGE,
    FIXED_AMOUNT,
    PRICE
}

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SellingPlanPricingPolicyValueInput {
    
    @JsonProperty("fixedValue")
    private String fixedValue;
    
    @JsonProperty("percentage")
    private String percentage;
}