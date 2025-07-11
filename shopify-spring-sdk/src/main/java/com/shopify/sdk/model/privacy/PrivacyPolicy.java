package com.shopify.sdk.model.privacy;

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
public class PrivacyPolicy {
    
    @JsonProperty("id")
    private ID id;
    
    @JsonProperty("body")
    private String body;
    
    @JsonProperty("createdAt")
    private DateTime createdAt;
    
    @JsonProperty("handle")
    private String handle;
    
    @JsonProperty("title")
    private String title;
    
    @JsonProperty("updatedAt")
    private DateTime updatedAt;
    
    @JsonProperty("url")
    private String url;
}