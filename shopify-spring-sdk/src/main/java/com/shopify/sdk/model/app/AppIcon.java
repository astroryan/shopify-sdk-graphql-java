package com.shopify.sdk.model.app;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.shopify.sdk.model.common.ID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AppIcon {
    
    @JsonProperty("id")
    private ID id;
    
    @JsonProperty("altText")
    private String altText;
    
    @JsonProperty("url")
    private String url;
    
    @JsonProperty("width")
    private Integer width;
    
    @JsonProperty("height")
    private Integer height;
}