package com.shopify.sdk.model.onlinestore;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.shopify.sdk.model.common.Node;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * Represents a navigation menu.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Menu implements Node {
    /**
     * A globally unique identifier.
     */
    @JsonProperty("id")
    private String id;
    
    /**
     * The title of the menu.
     */
    @JsonProperty("title")
    private String title;
    
    /**
     * The handle of the menu.
     */
    @JsonProperty("handle")
    private String handle;
    
    /**
     * The items in the menu.
     */
    @JsonProperty("items")
    private List<MenuItem> items;
}