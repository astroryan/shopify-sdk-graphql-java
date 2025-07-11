package com.shopify.sdk.model.markets;

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
public class Product {
    
    @JsonProperty("id")
    private String id;
    
    @JsonProperty("title")
    private String title;
    
    @JsonProperty("handle")
    private String handle;
    
    @JsonProperty("description")
    private String description;
    
    @JsonProperty("vendor")
    private String vendor;
    
    @JsonProperty("productType")
    private String productType;
    
    @JsonProperty("status")
    private String status;
    
    @JsonProperty("tags")
    private List<String> tags;
    
    @JsonProperty("createdAt")
    private String createdAt;
    
    @JsonProperty("updatedAt")
    private String updatedAt;
}
