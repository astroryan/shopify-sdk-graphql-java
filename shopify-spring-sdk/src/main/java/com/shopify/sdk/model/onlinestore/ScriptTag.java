package com.shopify.sdk.model.onlinestore;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.shopify.sdk.model.common.Node;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.OffsetDateTime;

/**
 * Represents a script tag.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ScriptTag implements Node {
    /**
     * A globally unique identifier.
     */
    @JsonProperty("id")
    private String id;
    
    /**
     * The URL of the script.
     */
    @JsonProperty("src")
    private String src;
    
    /**
     * The event that triggers the script.
     */
    @JsonProperty("event")
    private ScriptTagEvent event;
    
    /**
     * Where to display the script.
     */
    @JsonProperty("displayScope")
    private ScriptTagDisplayScope displayScope;
    
    /**
     * The date and time when the script tag was created.
     */
    @JsonProperty("createdAt")
    private OffsetDateTime createdAt;
    
    /**
     * The date and time when the script tag was last modified.
     */
    @JsonProperty("updatedAt")
    private OffsetDateTime updatedAt;
}