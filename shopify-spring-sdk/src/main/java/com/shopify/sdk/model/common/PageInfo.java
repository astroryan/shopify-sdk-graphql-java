package com.shopify.sdk.model.common;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PageInfo {
    
    @JsonProperty("hasNextPage")
    private boolean hasNextPage;
    
    @JsonProperty("hasPreviousPage")
    private boolean hasPreviousPage;
    
    @JsonProperty("startCursor")
    private String startCursor;
    
    @JsonProperty("endCursor")
    private String endCursor;
}