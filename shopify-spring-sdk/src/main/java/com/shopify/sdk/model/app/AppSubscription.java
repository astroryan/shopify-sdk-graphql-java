package com.shopify.sdk.model.app;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.shopify.sdk.model.common.DateTime;
import com.shopify.sdk.model.common.ID;
import com.shopify.sdk.model.common.Money;
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
    
    @JsonProperty("lineItems")
    private List<AppSubscriptionLineItem> lineItems;
    
    @JsonProperty("returnUrl")
    private String returnUrl;
    
    @JsonProperty("test")
    private Boolean test;
    
    @JsonProperty("trialDays")
    private Integer trialDays;
}