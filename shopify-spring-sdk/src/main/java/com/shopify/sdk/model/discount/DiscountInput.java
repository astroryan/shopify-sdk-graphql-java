package com.shopify.sdk.model.discount;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.shopify.sdk.model.common.DateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DiscountCodeBasicInput {
    
    @JsonProperty("appliesOncePerCustomer")
    private Boolean appliesOncePerCustomer;
    
    @JsonProperty("codes")
    private List<String> codes;
    
    @JsonProperty("combinesWith")
    private DiscountCombinesWithInput combinesWith;
    
    @JsonProperty("customerGets")
    private DiscountCustomerGetsInput customerGets;
    
    @JsonProperty("customerSelection")
    private DiscountCustomerSelectionInput customerSelection;
    
    @JsonProperty("endsAt")
    private DateTime endsAt;
    
    @JsonProperty("minimumRequirement")
    private DiscountMinimumRequirementInput minimumRequirement;
    
    @JsonProperty("recurringCycleLimit")
    private Integer recurringCycleLimit;
    
    @JsonProperty("startsAt")
    private DateTime startsAt;
    
    @JsonProperty("title")
    private String title;
    
    @JsonProperty("usageLimit")
    private Integer usageLimit;
}

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DiscountCodeBxgyInput {
    
    @JsonProperty("appliesOncePerCustomer")
    private Boolean appliesOncePerCustomer;
    
    @JsonProperty("codes")
    private List<String> codes;
    
    @JsonProperty("combinesWith")
    private DiscountCombinesWithInput combinesWith;
    
    @JsonProperty("customerBuys")
    private DiscountCustomerBuysInput customerBuys;
    
    @JsonProperty("customerGets")
    private DiscountCustomerGetsInput customerGets;
    
    @JsonProperty("customerSelection")
    private DiscountCustomerSelectionInput customerSelection;
    
    @JsonProperty("endsAt")
    private DateTime endsAt;
    
    @JsonProperty("startsAt")
    private DateTime startsAt;
    
    @JsonProperty("title")
    private String title;
    
    @JsonProperty("usageLimit")
    private Integer usageLimit;
    
    @JsonProperty("usesPerOrderLimit")
    private Integer usesPerOrderLimit;
}

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DiscountCodeFreeShippingInput {
    
    @JsonProperty("appliesOncePerCustomer")
    private Boolean appliesOncePerCustomer;
    
    @JsonProperty("codes")
    private List<String> codes;
    
    @JsonProperty("combinesWith")
    private DiscountCombinesWithInput combinesWith;
    
    @JsonProperty("customerSelection")
    private DiscountCustomerSelectionInput customerSelection;
    
    @JsonProperty("destinationSelection")
    private DiscountShippingDestinationSelectionInput destinationSelection;
    
    @JsonProperty("endsAt")
    private DateTime endsAt;
    
    @JsonProperty("maximumShippingPrice")
    private String maximumShippingPrice;
    
    @JsonProperty("minimumRequirement")
    private DiscountMinimumRequirementInput minimumRequirement;
    
    @JsonProperty("recurringCycleLimit")
    private Integer recurringCycleLimit;
    
    @JsonProperty("startsAt")
    private DateTime startsAt;
    
    @JsonProperty("title")
    private String title;
    
    @JsonProperty("usageLimit")
    private Integer usageLimit;
}

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
class DiscountCombinesWithInput {
    
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
class DiscountCustomerGetsInput {
    
    @JsonProperty("appliesOnOneTimePurchase")
    private Boolean appliesOnOneTimePurchase;
    
    @JsonProperty("appliesOnSubscription")
    private Boolean appliesOnSubscription;
    
    @JsonProperty("items")
    private DiscountItemsInput items;
    
    @JsonProperty("value")
    private DiscountCustomerGetsValueInput value;
}

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
class DiscountCustomerBuysInput {
    
    @JsonProperty("items")
    private DiscountItemsInput items;
    
    @JsonProperty("value")
    private DiscountCustomerBuysValueInput value;
}

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
class DiscountItemsInput {
    
    @JsonProperty("all")
    private Boolean all;
    
    @JsonProperty("collections")
    private DiscountCollectionsInput collections;
    
    @JsonProperty("products")
    private DiscountProductsInput products;
}

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
class DiscountCollectionsInput {
    
    @JsonProperty("add")
    private List<String> add;
    
    @JsonProperty("remove")
    private List<String> remove;
}

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
class DiscountProductsInput {
    
    @JsonProperty("productsToAdd")
    private List<String> productsToAdd;
    
    @JsonProperty("productsToRemove")
    private List<String> productsToRemove;
    
    @JsonProperty("productVariantsToAdd")
    private List<String> productVariantsToAdd;
    
    @JsonProperty("productVariantsToRemove")
    private List<String> productVariantsToRemove;
}

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
class DiscountCustomerGetsValueInput {
    
    @JsonProperty("discountAmount")
    private DiscountAmountInput discountAmount;
    
    @JsonProperty("percentage")
    private Double percentage;
    
    @JsonProperty("discountOnQuantity")
    private DiscountOnQuantityInput discountOnQuantity;
}

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
class DiscountCustomerBuysValueInput {
    
    @JsonProperty("amount")
    private String amount;
    
    @JsonProperty("quantity")
    private String quantity;
}

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
class DiscountAmountInput {
    
    @JsonProperty("amount")
    private String amount;
    
    @JsonProperty("appliesOnEachItem")
    private Boolean appliesOnEachItem;
}

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
class DiscountOnQuantityInput {
    
    @JsonProperty("effect")
    private DiscountEffectInput effect;
    
    @JsonProperty("quantity")
    private String quantity;
}

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
class DiscountEffectInput {
    
    @JsonProperty("percentage")
    private Double percentage;
}

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
class DiscountCustomerSelectionInput {
    
    @JsonProperty("all")
    private Boolean all;
    
    @JsonProperty("customers")
    private DiscountCustomersInput customers;
    
    @JsonProperty("customerSegments")
    private DiscountCustomerSegmentsInput customerSegments;
}

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
class DiscountCustomersInput {
    
    @JsonProperty("add")
    private List<String> add;
    
    @JsonProperty("remove")
    private List<String> remove;
}

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
class DiscountCustomerSegmentsInput {
    
    @JsonProperty("add")
    private List<String> add;
    
    @JsonProperty("remove")
    private List<String> remove;
}

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
class DiscountMinimumRequirementInput {
    
    @JsonProperty("quantity")
    private DiscountMinimumQuantityInput quantity;
    
    @JsonProperty("subtotal")
    private DiscountMinimumSubtotalInput subtotal;
}

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
class DiscountMinimumQuantityInput {
    
    @JsonProperty("greaterThanOrEqualToQuantity")
    private String greaterThanOrEqualToQuantity;
}

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
class DiscountMinimumSubtotalInput {
    
    @JsonProperty("greaterThanOrEqualToSubtotal")
    private String greaterThanOrEqualToSubtotal;
}

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
class DiscountShippingDestinationSelectionInput {
    
    @JsonProperty("all")
    private Boolean all;
    
    @JsonProperty("countries")
    private DiscountCountriesInput countries;
}

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
class DiscountCountriesInput {
    
    @JsonProperty("add")
    private List<String> add;
    
    @JsonProperty("includeRestOfWorld")
    private Boolean includeRestOfWorld;
    
    @JsonProperty("remove")
    private List<String> remove;
}