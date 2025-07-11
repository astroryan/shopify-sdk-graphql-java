package com.shopify.sdk.model.customer;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.shopify.sdk.model.common.DateTime;
import com.shopify.sdk.model.common.ID;
import com.shopify.sdk.model.common.Image;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Customer {
    
    @JsonProperty("id")
    private ID id;
    
    @JsonProperty("email")
    private String email;
    
    @JsonProperty("phone")
    private String phone;
    
    @JsonProperty("firstName")
    private String firstName;
    
    @JsonProperty("lastName")
    private String lastName;
    
    @JsonProperty("displayName")
    private String displayName;
    
    @JsonProperty("image")
    private Image image;
    
    @JsonProperty("acceptsMarketing")
    private Boolean acceptsMarketing;
    
    @JsonProperty("acceptsMarketingUpdatedAt")
    private DateTime acceptsMarketingUpdatedAt;
    
    @JsonProperty("createdAt")
    private DateTime createdAt;
    
    @JsonProperty("updatedAt")
    private DateTime updatedAt;
    
    @JsonProperty("note")
    private String note;
    
    @JsonProperty("verifiedEmail")
    private Boolean verifiedEmail;
    
    @JsonProperty("validEmailAddress")
    private Boolean validEmailAddress;
    
    @JsonProperty("tags")
    private List<String> tags;
    
    @JsonProperty("lifetimeDuration")
    private String lifetimeDuration;
    
    @JsonProperty("defaultAddress")
    private MailingAddress defaultAddress;
    
    @JsonProperty("addresses")
    private MailingAddressConnection addresses;
    
    @JsonProperty("orders")
    private OrderConnection orders;
    
    @JsonProperty("numberOfOrders")
    private String numberOfOrders;
    
    @JsonProperty("amountSpent")
    private CustomerSpending amountSpent;
    
    @JsonProperty("lastOrder")
    private Order lastOrder;
    
    @JsonProperty("marketingOptInLevel")
    private CustomerMarketingOptInLevel marketingOptInLevel;
    
    @JsonProperty("metafields")
    private MetafieldConnection metafields;
    
    @JsonProperty("productSubscriberStatus")
    private CustomerProductSubscriberStatus productSubscriberStatus;
    
    @JsonProperty("state")
    private CustomerState state;
    
    @JsonProperty("statistics")
    private CustomerStatistics statistics;
    
    @JsonProperty("subscriptionContracts")
    private SubscriptionContractConnection subscriptionContracts;
    
    @JsonProperty("taxExempt")
    private Boolean taxExempt;
    
    @JsonProperty("taxExemptions")
    private List<TaxExemption> taxExemptions;
}