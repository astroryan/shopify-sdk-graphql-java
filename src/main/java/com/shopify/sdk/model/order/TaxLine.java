package com.shopify.sdk.model.order;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.shopify.sdk.graphql.scalar.MoneyScalar;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Represents a tax line for an order.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TaxLine {
    
    /**
     * Whether the channel that submitted the tax line is liable for remitting.
     */
    @JsonProperty("channelLiable")
    private Boolean channelLiable;
    
    /**
     * The amount of tax to be charged.
     */
    @JsonProperty("price")
    private MoneyScalar price;
    
    /**
     * The proportion of the line item price that the tax represents as a decimal.
     */
    @JsonProperty("rate")
    private Double rate;
    
    /**
     * The proportion of the line item price that the tax represents as a percentage.
     */
    @JsonProperty("ratePercentage")
    private Double ratePercentage;
    
    /**
     * The name of the tax.
     */
    @JsonProperty("title")
    private String title;
}