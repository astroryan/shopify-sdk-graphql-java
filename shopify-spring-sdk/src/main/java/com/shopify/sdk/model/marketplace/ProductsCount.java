package com.shopify.sdk.model.marketplace;

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
public class ProductsCount {
    
    @JsonProperty("count")
    private Integer count;
    
    @JsonProperty("precision")
    private CountPrecision precision;
}
