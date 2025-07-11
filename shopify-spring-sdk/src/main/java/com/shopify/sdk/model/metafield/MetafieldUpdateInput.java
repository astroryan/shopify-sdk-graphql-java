package com.shopify.sdk.model.metafield;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MetafieldUpdateInput {
    
    @JsonProperty("id")
    private String id;
    
    @JsonProperty("value")
    private Object value;
    
    @JsonProperty("type")
    private String type;
}