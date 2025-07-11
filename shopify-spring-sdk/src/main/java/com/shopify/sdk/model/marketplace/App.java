package com.shopify.sdk.model.marketplace;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.shopify.sdk.model.common.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Represents a Shopify app
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class App {
    /**
     * The ID of the app
     */
    @JsonProperty("id")
    private ID id;
    
    /**
     * The name of the app
     */
    @JsonProperty("title")
    private String title;
    
    /**
     * The description of the app
     */
    @JsonProperty("description")
    private String description;
    
    /**
     * The handle of the app
     */
    @JsonProperty("handle")
    private String handle;
    
    /**
     * The app's icon URL
     */
    @JsonProperty("icon")
    private URL icon;
    
    /**
     * The developer name
     */
    @JsonProperty("developerName")
    private String developerName;
    
    /**
     * Whether the app is published
     */
    @JsonProperty("published")
    private Boolean published;
}