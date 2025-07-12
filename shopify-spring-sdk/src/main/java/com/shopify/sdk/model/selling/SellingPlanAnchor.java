package com.shopify.sdk.model.selling;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Represents an anchor for a selling plan.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SellingPlanAnchor {
    /**
     * The day of the month.
     */
    @JsonProperty("day")
    private Integer day;
    
    /**
     * The month of the year.
     */
    @JsonProperty("month")
    private Integer month;
    
    /**
     * The type of the anchor.
     */
    @JsonProperty("type")
    private SellingPlanAnchorType type;
}