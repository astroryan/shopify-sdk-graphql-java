package com.shopify.sdk.model.common;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Information about pagination in a connection.
 * Standard GraphQL pagination interface.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PageInfo {
    
    /**
     * When paginating forwards, are there more items?
     */
    @JsonProperty("hasNextPage")
    private Boolean hasNextPage;
    
    /**
     * When paginating backwards, are there more items?
     */
    @JsonProperty("hasPreviousPage")
    private Boolean hasPreviousPage;
    
    /**
     * When paginating backwards, the cursor to continue.
     */
    @JsonProperty("startCursor")
    private String startCursor;
    
    /**
     * When paginating forwards, the cursor to continue.
     */
    @JsonProperty("endCursor")
    private String endCursor;
}