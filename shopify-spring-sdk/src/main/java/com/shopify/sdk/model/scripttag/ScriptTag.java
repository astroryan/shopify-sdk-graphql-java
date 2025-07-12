package com.shopify.sdk.model.scripttag;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.time.Instant;

/**
 * Represents a Shopify script tag.
 */
@Data
public class ScriptTag {
    
    private String id;
    private String src;
    private String event;
    
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss'Z'", timezone = "UTC")
    @JsonProperty("created_at")
    private Instant createdAt;
    
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss'Z'", timezone = "UTC")
    @JsonProperty("updated_at")
    private Instant updatedAt;
    
    @JsonProperty("display_scope")
    private String displayScope;
    
    @JsonProperty("cache")
    private Boolean cache;
    
    // Event constants
    public static final String EVENT_ONLOAD = "onload";
    public static final String EVENT_DOM_READY = "DOMContentLoaded";
    
    // Display scope constants
    public static final String DISPLAY_SCOPE_ONLINE_STORE = "online_store";
    public static final String DISPLAY_SCOPE_ORDER_STATUS = "order_status";
    public static final String DISPLAY_SCOPE_ALL = "all";
    
    /**
     * Check if the script tag loads on page load.
     */
    public boolean isOnLoad() {
        return EVENT_ONLOAD.equals(event);
    }
    
    /**
     * Check if the script tag loads when DOM is ready.
     */
    public boolean isDomReady() {
        return EVENT_DOM_READY.equals(event);
    }
    
    /**
     * Check if the script tag is displayed on online store.
     */
    public boolean isOnlineStore() {
        return DISPLAY_SCOPE_ONLINE_STORE.equals(displayScope) || 
               DISPLAY_SCOPE_ALL.equals(displayScope);
    }
    
    /**
     * Check if the script tag is displayed on order status page.
     */
    public boolean isOrderStatus() {
        return DISPLAY_SCOPE_ORDER_STATUS.equals(displayScope) || 
               DISPLAY_SCOPE_ALL.equals(displayScope);
    }
    
    /**
     * Check if caching is enabled.
     */
    public boolean isCacheEnabled() {
        return cache != null && cache;
    }
}