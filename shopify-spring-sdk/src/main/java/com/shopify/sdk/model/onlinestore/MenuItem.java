package com.shopify.sdk.model.onlinestore;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * Represents a menu item.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MenuItem {
    /**
     * The ID of the menu item.
     */
    @JsonProperty("id")
    private String id;
    
    /**
     * The title of the menu item.
     */
    @JsonProperty("title")
    private String title;
    
    /**
     * The URL of the menu item.
     */
    @JsonProperty("url")
    private String url;
    
    /**
     * The type of the menu item.
     */
    @JsonProperty("type")
    private MenuItemType type;
    
    /**
     * The child items of this menu item.
     */
    @JsonProperty("items")
    private List<MenuItem> items;
}