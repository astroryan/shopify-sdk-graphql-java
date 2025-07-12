package com.shopify.sdk.model.discount;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.OffsetDateTime;
import java.util.List;

/**
 * Input for creating or updating a basic discount code.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DiscountCodeBasicInput {
    /**
     * The title of the discount.
     */
    @JsonProperty("title")
    private String title;
    
    /**
     * The code for the discount.
     */
    @JsonProperty("code")
    private String code;
    
    /**
     * The date and time when the discount starts.
     */
    @JsonProperty("startsAt")
    private OffsetDateTime startsAt;
    
    /**
     * The date and time when the discount ends.
     */
    @JsonProperty("endsAt")
    private OffsetDateTime endsAt;
    
    /**
     * The usage limit of the discount code.
     */
    @JsonProperty("usageLimit")
    private Integer usageLimit;
    
    /**
     * Whether the discount applies once per customer.
     */
    @JsonProperty("appliesOncePerCustomer")
    private Boolean appliesOncePerCustomer;
    
    /**
     * The minimum requirements for the discount.
     */
    @JsonProperty("minimumRequirement")
    private DiscountMinimumRequirementInput minimumRequirement;
    
    /**
     * The customer gets for the discount.
     */
    @JsonProperty("customerGets")
    private DiscountCustomerGetsInput customerGets;
    
    /**
     * The customer selection for the discount.
     */
    @JsonProperty("customerSelection")
    private DiscountCustomerSelectionInput customerSelection;
}

/**
 * Input for minimum requirements.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
class DiscountMinimumRequirementInput {
    /**
     * The minimum quantity.
     */
    @JsonProperty("quantity")
    private DiscountMinimumQuantityInput quantity;
    
    /**
     * The minimum subtotal.
     */
    @JsonProperty("subtotal")
    private DiscountMinimumSubtotalInput subtotal;
}

/**
 * Input for minimum quantity.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
class DiscountMinimumQuantityInput {
    /**
     * The minimum quantity value.
     */
    @JsonProperty("greaterThanOrEqualToQuantity")
    private Integer greaterThanOrEqualToQuantity;
}

/**
 * Input for minimum subtotal.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
class DiscountMinimumSubtotalInput {
    /**
     * The minimum subtotal amount.
     */
    @JsonProperty("greaterThanOrEqualToSubtotal")
    private String greaterThanOrEqualToSubtotal;
}

/**
 * Input for what the customer gets.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
class DiscountCustomerGetsInput {
    /**
     * The value of the discount.
     */
    @JsonProperty("value")
    private DiscountValueInput value;
    
    /**
     * The items the discount applies to.
     */
    @JsonProperty("items")
    private DiscountItemsInput items;
}

/**
 * Input for discount value.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
class DiscountValueInput {
    /**
     * The percentage value.
     */
    @JsonProperty("percentageValue")
    private Double percentageValue;
    
    /**
     * The amount off.
     */
    @JsonProperty("amountOff")
    private DiscountAmountInput amountOff;
}

/**
 * Input for discount amount.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
class DiscountAmountInput {
    /**
     * The amount.
     */
    @JsonProperty("amount")
    private String amount;
    
    /**
     * Whether to apply to each item.
     */
    @JsonProperty("appliesOnEachItem")
    private Boolean appliesOnEachItem;
}

/**
 * Input for discount items.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
class DiscountItemsInput {
    /**
     * Whether all items are eligible.
     */
    @JsonProperty("all")
    private Boolean all;
    
    /**
     * Specific products.
     */
    @JsonProperty("products")
    private DiscountProductsInput products;
    
    /**
     * Specific collections.
     */
    @JsonProperty("collections")
    private DiscountCollectionsInput collections;
}

/**
 * Input for discount products.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
class DiscountProductsInput {
    /**
     * Product IDs to add.
     */
    @JsonProperty("productsToAdd")
    private List<String> productsToAdd;
    
    /**
     * Product IDs to remove.
     */
    @JsonProperty("productsToRemove")
    private List<String> productsToRemove;
}

/**
 * Input for discount collections.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
class DiscountCollectionsInput {
    /**
     * Collection IDs to add.
     */
    @JsonProperty("collectionsToAdd")
    private List<String> collectionsToAdd;
    
    /**
     * Collection IDs to remove.
     */
    @JsonProperty("collectionsToRemove")
    private List<String> collectionsToRemove;
}

/**
 * Input for customer selection.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
class DiscountCustomerSelectionInput {
    /**
     * Whether all customers are eligible.
     */
    @JsonProperty("all")
    private Boolean all;
    
    /**
     * Specific customers.
     */
    @JsonProperty("customers")
    private DiscountCustomersInput customers;
}

/**
 * Input for discount customers.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
class DiscountCustomersInput {
    /**
     * Customer IDs to add.
     */
    @JsonProperty("customersToAdd")
    private List<String> customersToAdd;
    
    /**
     * Customer IDs to remove.
     */
    @JsonProperty("customersToRemove")
    private List<String> customersToRemove;
}