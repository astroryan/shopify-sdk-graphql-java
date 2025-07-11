package com.shopify.sdk.model.common;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Information about pagination in a connection
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PageInfo {
    /**
     * Whether there are more pages to fetch following the current page
     */
    @JsonProperty("hasNextPage")
    private Boolean hasNextPage;
    
    /**
     * Whether there are more pages to fetch prior to the current page
     */
    @JsonProperty("hasPreviousPage")
    private Boolean hasPreviousPage;
    
    /**
     * The cursor for the first item in the current page
     */
    @JsonProperty("startCursor")
    private String startCursor;
    
    /**
     * The cursor for the last item in the current page
     */
    @JsonProperty("endCursor")
    private String endCursor;
}