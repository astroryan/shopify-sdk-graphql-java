package com.shopify.sdk.model.privacy;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * An edge in a customer data request connection.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CustomerDataRequestEdge {
    /**
     * The cursor for pagination.
     */
    @JsonProperty("cursor")
    private String cursor;
    
    /**
     * The node.
     */
    @JsonProperty("node")
    private CustomerDataRequest node;
}