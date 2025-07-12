package com.shopify.sdk.model.fulfillment;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * A connection to merchant requests.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FulfillmentOrderMerchantRequestConnection {
    /**
     * The edges in the connection.
     */
    @JsonProperty("edges")
    private List<FulfillmentOrderMerchantRequestEdge> edges;
}