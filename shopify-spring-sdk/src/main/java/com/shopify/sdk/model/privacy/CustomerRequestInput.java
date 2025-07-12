package com.shopify.sdk.model.privacy;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Input for creating or updating a customer request.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CustomerRequestInput {
    /**
     * The customer ID.
     */
    @JsonProperty("customerId")
    private String customerId;
    
    /**
     * The email address.
     */
    @JsonProperty("email")
    private String email;
    
    /**
     * The type of customer request.
     */
    @JsonProperty("requestType")
    private CustomerRequestType requestType;
    
    /**
     * The status of the request.
     */
    @JsonProperty("status")
    private CustomerRequestStatus status;
}