package com.shopify.sdk.model.store;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.shopify.sdk.model.common.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MarketRegionEdge {
    
    @JsonProperty("cursor")
    private String cursor;
    
    @JsonProperty("node")
    private MarketRegion node;
}
