package com.shopify.sdk.model.market;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * Input for deleting market regions.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MarketRegionDeleteInput {
    /**
     * The IDs of regions to delete.
     */
    @JsonProperty("ids")
    private List<String> ids;
}