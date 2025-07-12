package com.shopify.sdk.model.privacy;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.shopify.sdk.model.common.Node;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.OffsetDateTime;

/**
 * Represents a customer data request.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CustomerDataRequest implements Node {
    /**
     * The unique identifier for the customer data request.
     */
    @JsonProperty("id")
    private String id;
    
    /**
     * The customer ID associated with the request.
     */
    @JsonProperty("customerId")
    private String customerId;
    
    /**
     * The type of data request.
     */
    @JsonProperty("requestType")
    private CustomerRequestType requestType;
    
    /**
     * The state of the data request.
     */
    @JsonProperty("state")
    private CustomerDataRequestState state;
    
    /**
     * When the request was created.
     */
    @JsonProperty("createdAt")
    private OffsetDateTime createdAt;
    
    /**
     * When the request was updated.
     */
    @JsonProperty("updatedAt")
    private OffsetDateTime updatedAt;
    
    /**
     * When the request was completed.
     */
    @JsonProperty("completedAt")
    private OffsetDateTime completedAt;
}