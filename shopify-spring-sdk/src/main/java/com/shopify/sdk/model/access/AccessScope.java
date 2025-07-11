package com.shopify.sdk.model.access;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AccessScope {
    
    @JsonProperty("handle")
    private String handle;
    
    @JsonProperty("description")
    private String description;
}