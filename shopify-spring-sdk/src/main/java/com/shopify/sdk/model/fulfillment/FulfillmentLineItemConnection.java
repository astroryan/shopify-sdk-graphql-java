package com.shopify.sdk.model.fulfillment;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.shopify.sdk.model.common.PageInfo;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * A connection to fulfillment line items.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FulfillmentLineItemConnection {
    /**
     * The edges in the connection.
     */
    @JsonProperty("edges")
    private List<FulfillmentLineItemEdge> edges;
    
    /**
     * The nodes in the connection.
     */
    @JsonProperty("nodes")
    private List<FulfillmentLineItem> nodes;
    
    /**
     * Information about pagination.
     */
    @JsonProperty("pageInfo")
    private PageInfo pageInfo;
}