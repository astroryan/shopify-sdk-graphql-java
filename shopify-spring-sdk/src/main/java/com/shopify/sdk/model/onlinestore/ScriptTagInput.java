package com.shopify.sdk.model.onlinestore;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Input for creating or updating a script tag.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ScriptTagInput {
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
}