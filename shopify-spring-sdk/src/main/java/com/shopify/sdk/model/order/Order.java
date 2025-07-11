package com.shopify.sdk.model.order;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.shopify.sdk.model.common.*;
import com.shopify.sdk.model.customer.Customer;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

/**
 * Represents a Shopify order
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Order {
    /**
     * The globally unique identifier for the order
     */
    @JsonProperty("id")
    private ID id;
    
    /**
     * The name of the order (e.g., #1001)
     */
    @JsonProperty("name")
    private String name;
    
    /**
     * The order number
     */
    @JsonProperty("orderNumber")
    private Integer orderNumber;
    
    /**
     * The email address associated with the order
     */
    @JsonProperty("email")
    private String email;
    
    /**
     * The phone number associated with the order
     */
    @JsonProperty("phone")
    private String phone;
    
    /**
     * The date and time when the order was created
     */
    @JsonProperty("createdAt")
    private DateTime createdAt;
    
    /**
     * The date and time when the order was last updated
     */
    @JsonProperty("updatedAt")
    private DateTime updatedAt;
    
    /**
     * The date and time when the order was processed
     */
    @JsonProperty("processedAt")
    private DateTime processedAt;
    
    /**
     * The date and time when the order was closed
     */
    @JsonProperty("closedAt")
    private DateTime closedAt;
    
    /**
     * The date and time when the order was cancelled
     */
    @JsonProperty("cancelledAt")
    private DateTime cancelledAt;
    
    /**
     * The reason for cancellation
     */
    @JsonProperty("cancelReason")
    private OrderCancelReason cancelReason;
    
    /**
     * The customer associated with the order
     */
    @JsonProperty("customer")
    private Customer customer;
    
    /**
     * The line items in the order
     */
    @JsonProperty("lineItems")
    private Connection<LineItem> lineItems;
    
    /**
     * The billing address
     */
    @JsonProperty("billingAddress")
    private MailingAddress billingAddress;
    
    /**
     * The shipping address
     */
    @JsonProperty("shippingAddress")
    private MailingAddress shippingAddress;
    
    /**
     * The shipping lines for the order
     */
    @JsonProperty("shippingLines")
    private Connection<ShippingLine> shippingLines;
    
    /**
     * The total price of the order
     */
    @JsonProperty("totalPriceSet")
    private MoneyBag totalPriceSet;
    
    /**
     * The subtotal price of the order
     */
    @JsonProperty("subtotalPriceSet")
    private MoneyBag subtotalPriceSet;
    
    /**
     * The total tax for the order
     */
    @JsonProperty("totalTaxSet")
    private MoneyBag totalTaxSet;
    
    /**
     * The total shipping price for the order
     */
    @JsonProperty("totalShippingPriceSet")
    private MoneyBag totalShippingPriceSet;
    
    /**
     * The total discounts for the order
     */
    @JsonProperty("totalDiscountsSet")
    private MoneyBag totalDiscountsSet;
    
    /**
     * The financial status of the order
     */
    @JsonProperty("displayFinancialStatus")
    private OrderDisplayFinancialStatus displayFinancialStatus;
    
    /**
     * The fulfillment status of the order
     */
    @JsonProperty("displayFulfillmentStatus")
    private OrderDisplayFulfillmentStatus displayFulfillmentStatus;
    
    /**
     * Whether the order has been fully paid
     */
    @JsonProperty("fullyPaid")
    private Boolean fullyPaid;
    
    /**
     * Whether taxes are included in the order
     */
    @JsonProperty("taxesIncluded")
    private Boolean taxesIncluded;
    
    /**
     * Whether this is a test order
     */
    @JsonProperty("test")
    private Boolean test;
    
    /**
     * The note attached to the order
     */
    @JsonProperty("note")
    private String note;
    
    /**
     * Tags associated with the order
     */
    @JsonProperty("tags")
    private List<String> tags;
    
    /**
     * Custom attributes for the order
     */
    @JsonProperty("customAttributes")
    private List<Attribute> customAttributes;
}