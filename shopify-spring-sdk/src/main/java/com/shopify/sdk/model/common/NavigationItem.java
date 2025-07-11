package com.shopify.sdk.model.common;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Represents a navigation item in a channel or app.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class NavigationItem {
    /**
     * The ID of the navigation item
     */
    private String id;
    
    /**
     * The title of the navigation item
     */
    private String title;
    
    /**
     * The URL of the navigation item
     */
    private String url;
}