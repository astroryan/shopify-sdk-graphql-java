package com.shopify.sdk.model.product;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.shopify.sdk.model.common.Money;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductPriceRange {
    
    @JsonProperty("minVariantPrice")
    private Money minVariantPrice;
    
    @JsonProperty("maxVariantPrice")
    private Money maxVariantPrice;
}