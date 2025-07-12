package com.shopify.sdk.model.discount;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.shopify.sdk.model.common.Node;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.OffsetDateTime;

/**
 * Represents a discount code in Shopify.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DiscountCodeNode implements Node {
    /**
     * A globally unique identifier.
     */
    @JsonProperty("id")
    private String id;
    
    /**
     * The discount code.
     */
    @JsonProperty("code")
    private String code;
    
    /**
     * The title of the discount.
     */
    @JsonProperty("title")
    private String title;
    
    /**
     * The status of the discount code.
     */
    @JsonProperty("status")
    private DiscountStatus status;
    
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
     * The number of times the discount has been used.
     */
    @JsonProperty("usageCount")
    private Integer usageCount;
    
    /**
     * The usage limit of the discount code.
     */
    @JsonProperty("usageLimit")
    private Integer usageLimit;
    
    /**
     * Whether the discount applies once per customer.
     */
    @JsonProperty("oncePerCustomer")
    private Boolean oncePerCustomer;
    
    /**
     * The customer selection for the discount.
     */
    @JsonProperty("customerSelection")
    private DiscountCustomerSelection customerSelection;
    
    /**
     * The minimum requirements for the discount.
     */
    @JsonProperty("minimumRequirements")
    private DiscountMinimumRequirements minimumRequirements;
    
    /**
     * The items to which the discount applies.
     */
    @JsonProperty("items")
    private DiscountItems items;
    
    /**
     * The value of the discount.
     */
    @JsonProperty("value")
    private DiscountValue value;
}

/**
 * The customer selection for a discount.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
class DiscountCustomerSelection {
    /**
     * Whether all customers are eligible.
     */
    @JsonProperty("all")
    private Boolean all;
    
    /**
     * Specific customer segments.
     */
    @JsonProperty("segments")
    private CustomerSegmentConnection segments;
}

/**
 * Customer segment connection.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
class CustomerSegmentConnection {
    /**
     * The total count of segments.
     */
    @JsonProperty("totalCount")
    private Integer totalCount;
}

/**
 * The minimum requirements for a discount.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
class DiscountMinimumRequirements {
    /**
     * The minimum quantity required.
     */
    @JsonProperty("quantity")
    private Integer quantity;
    
    /**
     * The minimum subtotal required.
     */
    @JsonProperty("subtotal")
    private Money subtotal;
}

/**
 * The items to which a discount applies.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
class DiscountItems {
    /**
     * Whether the discount applies to all items.
     */
    @JsonProperty("all")
    private Boolean all;
    
    /**
     * Specific products.
     */
    @JsonProperty("products")
    private ProductConnection products;
    
    /**
     * Specific collections.
     */
    @JsonProperty("collections")
    private CollectionConnection collections;
}

/**
 * The value of a discount.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
class DiscountValue {
    /**
     * The percentage value.
     */
    @JsonProperty("percentage")
    private Double percentage;
    
    /**
     * The fixed amount.
     */
    @JsonProperty("amount")
    private Money amount;
}

/**
 * Represents money.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
class Money {
    /**
     * The amount.
     */
    @JsonProperty("amount")
    private String amount;
    
    /**
     * The currency code.
     */
    @JsonProperty("currencyCode")
    private String currencyCode;
}

/**
 * Product connection.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
class ProductConnection {
    /**
     * The total count.
     */
    @JsonProperty("totalCount")
    private Integer totalCount;
}

/**
 * Collection connection.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
class CollectionConnection {
    /**
     * The total count.
     */
    @JsonProperty("totalCount")
    private Integer totalCount;
}