package com.shopify.sdk.model.discount;

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
public class DiscountAutomaticNode extends DiscountNode {
    
    @JsonProperty("automaticDiscount")
    private AutomaticDiscount automaticDiscount;
}

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
class AutomaticDiscount {
    
    @JsonProperty("__typename")
    private String typename;
    
    @JsonProperty("title")
    private String title;
    
    @JsonProperty("status")
    private DiscountStatus status;
    
    @JsonProperty("summary")
    private String summary;
    
    @JsonProperty("startsAt")
    private DateTime startsAt;
    
    @JsonProperty("endsAt")
    private DateTime endsAt;
    
    @JsonProperty("asyncUsageCount")
    private Integer asyncUsageCount;
    
    @JsonProperty("createdAt")
    private DateTime createdAt;
    
    @JsonProperty("updatedAt")
    private DateTime updatedAt;
}

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DiscountAutomaticBasic extends AutomaticDiscount {
    
    @JsonProperty("combinesWith")
    private DiscountCombinesWith combinesWith;
    
    @JsonProperty("customerGets")
    private DiscountCustomerGets customerGets;
    
    @JsonProperty("minimumRequirement")
    private DiscountMinimumRequirement minimumRequirement;
    
    @JsonProperty("recurringCycleLimit")
    private Integer recurringCycleLimit;
    
    @JsonProperty("shortSummary")
    private String shortSummary;
}

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DiscountAutomaticBxgy extends AutomaticDiscount {
    
    @JsonProperty("combinesWith")
    private DiscountCombinesWith combinesWith;
    
    @JsonProperty("customerBuys")
    private DiscountCustomerBuys customerBuys;
    
    @JsonProperty("customerGets")
    private DiscountCustomerGets customerGets;
    
    @JsonProperty("usesPerOrderLimit")
    private Integer usesPerOrderLimit;
}

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DiscountAutomaticFreeShipping extends AutomaticDiscount {
    
    @JsonProperty("combinesWith")
    private DiscountCombinesWith combinesWith;
    
    @JsonProperty("countries")
    private DiscountCountries countries;
    
    @JsonProperty("destinationSelection")
    private DiscountShippingDestinationSelection destinationSelection;
    
    @JsonProperty("excludesShippingRates")
    private Boolean excludesShippingRates;
    
    @JsonProperty("maximumShippingPrice")
    private MoneyV2 maximumShippingPrice;
    
    @JsonProperty("minimumRequirement")
    private DiscountMinimumRequirement minimumRequirement;
    
    @JsonProperty("recurringCycleLimit")
    private Integer recurringCycleLimit;
    
    @JsonProperty("shortSummary")
    private String shortSummary;
}

public enum DiscountStatus {
    ACTIVE,
    EXPIRED,
    SCHEDULED
}

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
class DiscountCombinesWith {
    
    @JsonProperty("orderDiscounts")
    private Boolean orderDiscounts;
    
    @JsonProperty("productDiscounts")
    private Boolean productDiscounts;
    
    @JsonProperty("shippingDiscounts")
    private Boolean shippingDiscounts;
}

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
class DiscountCustomerGets {
    
    @JsonProperty("appliesOnOneTimePurchase")
    private Boolean appliesOnOneTimePurchase;
    
    @JsonProperty("appliesOnSubscription")
    private Boolean appliesOnSubscription;
    
    @JsonProperty("items")
    private DiscountItems items;
    
    @JsonProperty("value")
    private DiscountCustomerGetsValue value;
}

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
class DiscountCustomerBuys {
    
    @JsonProperty("items")
    private DiscountItems items;
    
    @JsonProperty("value")
    private DiscountCustomerBuysValue value;
}

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
class DiscountItems {
    
    @JsonProperty("__typename")
    private String typename;
}

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
class DiscountCustomerGetsValue {
    
    @JsonProperty("__typename")
    private String typename;
}

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
class DiscountCustomerBuysValue {
    
    @JsonProperty("__typename")
    private String typename;
}

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
class DiscountMinimumRequirement {
    
    @JsonProperty("__typename")
    private String typename;
}

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
class DiscountCountries {
    
    @JsonProperty("includeRestOfWorld")
    private Boolean includeRestOfWorld;
}

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
class DiscountShippingDestinationSelection {
    
    @JsonProperty("__typename")
    private String typename;
}