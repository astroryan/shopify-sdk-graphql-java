package com.shopify.sdk.model.billing;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.shopify.sdk.model.common.Node;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.OffsetDateTime;

/**
 * Represents an app subscription.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AppSubscription implements Node {
    /**
     * The unique identifier for the app subscription.
     */
    @JsonProperty("id")
    private String id;
    
    /**
     * The name of the subscription.
     */
    @JsonProperty("name")
    private String name;
    
    /**
     * The return URL.
     */
    @JsonProperty("returnUrl")
    private String returnUrl;
    
    /**
     * The status of the subscription.
     */
    @JsonProperty("status")
    private String status;
    
    /**
     * The test flag.
     */
    @JsonProperty("test")
    private Boolean test;
    
    /**
     * The created at timestamp.
     */
    @JsonProperty("createdAt")
    private OffsetDateTime createdAt;
    
    /**
     * The current period end timestamp.
     */
    @JsonProperty("currentPeriodEnd")
    private OffsetDateTime currentPeriodEnd;
    
    /**
     * The trial days.
     */
    @JsonProperty("trialDays")
    private Integer trialDays;
    
    /**
     * The line items.
     */
    @JsonProperty("lineItems")
    private String lineItems;
}