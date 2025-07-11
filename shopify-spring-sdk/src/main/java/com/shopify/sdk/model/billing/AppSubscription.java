package com.shopify.sdk.model.billing;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.shopify.sdk.model.app.AppSubscriptionStatus;
import com.shopify.sdk.model.common.DateTime;
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
public class AppSubscription {
    
    @JsonProperty("id")
    private ID id;
    
    @JsonProperty("name")
    private String name;
    
    @JsonProperty("status")
    private AppSubscriptionStatus status;
    
    @JsonProperty("createdAt")
    private DateTime createdAt;
    
    @JsonProperty("currentPeriodEnd")
    private DateTime currentPeriodEnd;
    
    @JsonProperty("cancelledAt")
    private DateTime cancelledAt;
    
    @JsonProperty("lineItems")
    private List<AppSubscriptionLineItem> lineItems;
    
    @JsonProperty("returnUrl")
    private String returnUrl;
    
    @JsonProperty("test")
    private Boolean test;
    
    @JsonProperty("trialDays")
    private Integer trialDays;
}