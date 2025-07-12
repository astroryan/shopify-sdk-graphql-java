package com.shopify.sdk.model.billing;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.shopify.sdk.model.common.Node;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.OffsetDateTime;

/**
 * Represents an app usage record.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AppUsageRecord implements Node {
    /**
     * The unique identifier for the app usage record.
     */
    @JsonProperty("id")
    private String id;
    
    /**
     * The subscription ID.
     */
    @JsonProperty("subscriptionId")
    private String subscriptionId;
    
    /**
     * The description of the usage.
     */
    @JsonProperty("description")
    private String description;
    
    /**
     * The price of the usage.
     */
    @JsonProperty("price")
    private String price;
    
    /**
     * The created at timestamp.
     */
    @JsonProperty("createdAt")
    private OffsetDateTime createdAt;
}