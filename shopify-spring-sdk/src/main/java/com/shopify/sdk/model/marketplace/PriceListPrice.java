package com.shopify.sdk.model.marketplace;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.shopify.sdk.model.common.*;
import com.shopify.sdk.model.product.ProductVariant;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Represents a price in a price list
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PriceListPrice {
    /**
     * The ID of the price list price
     */
    @JsonProperty("id")
    private ID id;
    
    /**
     * The price amount
     */
    @JsonProperty("price")
    private Money price;
    
    /**
     * The compare at price
     */
    @JsonProperty("compareAtPrice")
    private Money compareAtPrice;
    
    /**
     * The product variant this price applies to
     */
    @JsonProperty("variant")
    private ProductVariant variant;
}