package com.shopify.sdk.model.order;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.shopify.sdk.model.common.*;
import com.shopify.sdk.model.customer.Customer;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Order {
    
    @JsonProperty("id")
    private ID id;
    
    @JsonProperty("name")
    private String name;
    
    @JsonProperty("email")
    private String email;
    
    @JsonProperty("phone")
    private String phone;
    
    @JsonProperty("createdAt")
    private DateTime createdAt;
    
    @JsonProperty("updatedAt")
    private DateTime updatedAt;
    
    @JsonProperty("processedAt")
    private DateTime processedAt;
    
    @JsonProperty("closedAt")
    private DateTime closedAt;
    
    @JsonProperty("cancelledAt")
    private DateTime cancelledAt;
    
    @JsonProperty("cancelReason")
    private OrderCancelReason cancelReason;
    
    @JsonProperty("displayFinancialStatus")
    private OrderDisplayFinancialStatus displayFinancialStatus;
    
    @JsonProperty("displayFulfillmentStatus")
    private OrderDisplayFulfillmentStatus displayFulfillmentStatus;
    
    @JsonProperty("returnStatus")
    private OrderReturnStatus returnStatus;
    
    @JsonProperty("currentSubtotalPriceSet")
    private MoneyBag currentSubtotalPriceSet;
    
    @JsonProperty("currentTotalPriceSet")
    private MoneyBag currentTotalPriceSet;
    
    @JsonProperty("currentTotalTaxSet")
    private MoneyBag currentTotalTaxSet;
    
    @JsonProperty("totalShippingPriceSet")
    private MoneyBag totalShippingPriceSet;
    
    @JsonProperty("totalDiscountsSet")
    private MoneyBag totalDiscountsSet;
    
    @JsonProperty("totalRefundedSet")
    private MoneyBag totalRefundedSet;
    
    @JsonProperty("totalPriceSet")
    private MoneyBag totalPriceSet;
    
    @JsonProperty("subtotalPriceSet")
    private MoneyBag subtotalPriceSet;
    
    @JsonProperty("totalTaxSet")
    private MoneyBag totalTaxSet;
    
    @JsonProperty("totalWeight")
    private Long totalWeight;
    
    @JsonProperty("currencyCode")
    private CurrencyCode currencyCode;
    
    @JsonProperty("customer")
    private Customer customer;
    
    @JsonProperty("customerAcceptsMarketing")
    private Boolean customerAcceptsMarketing;
    
    @JsonProperty("customerLocale")
    private String customerLocale;
    
    @JsonProperty("lineItems")
    private LineItemConnection lineItems;
    
    @JsonProperty("fulfillments")
    private List<Fulfillment> fulfillments;
    
    @JsonProperty("refunds")
    private List<Refund> refunds;
    
    @JsonProperty("shippingAddress")
    private MailingAddress shippingAddress;
    
    @JsonProperty("billingAddress")
    private MailingAddress billingAddress;
    
    @JsonProperty("shippingLine")
    private ShippingLine shippingLine;
    
    @JsonProperty("note")
    private String note;
    
    @JsonProperty("noteAttributes")
    private List<Attribute> noteAttributes;
    
    @JsonProperty("tags")
    private List<String> tags;
    
    @JsonProperty("test")
    private Boolean test;
    
    @JsonProperty("confirmed")
    private Boolean confirmed;
    
    @JsonProperty("fullyPaid")
    private Boolean fullyPaid;
    
    @JsonProperty("unpaid")
    private Boolean unpaid;
    
    @JsonProperty("riskLevel")
    private OrderRiskLevel riskLevel;
    
    @JsonProperty("sourceIdentifier")
    private String sourceIdentifier;
    
    @JsonProperty("sourceName")
    private String sourceName;
    
    @JsonProperty("taxesIncluded")
    private Boolean taxesIncluded;
    
    @JsonProperty("totalCapturableSet")
    private MoneyBag totalCapturableSet;
    
    @JsonProperty("totalOutstandingSet")
    private MoneyBag totalOutstandingSet;
    
    @JsonProperty("totalReceivedSet")
    private MoneyBag totalReceivedSet;
    
    @JsonProperty("transactions")
    private List<OrderTransaction> transactions;
}