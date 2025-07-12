package com.shopify.sdk.model.onlinestore;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.shopify.sdk.model.common.Node;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.OffsetDateTime;

/**
 * Represents an online store theme.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OnlineStoreTheme implements Node {
    /**
     * A globally unique identifier.
     */
    @JsonProperty("id")
    private String id;
    
    /**
     * The name of the theme.
     */
    @JsonProperty("name")
    private String name;
    
    /**
     * The role of the theme.
     */
    @JsonProperty("role")
    private ThemeRole role;
    
    /**
     * The date and time when the theme was created.
     */
    @JsonProperty("createdAt")
    private OffsetDateTime createdAt;
    
    /**
     * The date and time when the theme was last modified.
     */
    @JsonProperty("updatedAt")
    private OffsetDateTime updatedAt;
    
    /**
     * Whether the theme can be previewed.
     */
    @JsonProperty("previewable")
    private Boolean previewable;
    
    /**
     * Whether the theme is processing.
     */
    @JsonProperty("processing")
    private Boolean processing;
    
    /**
     * The theme store ID.
     */
    @JsonProperty("themeStoreId")
    private Long themeStoreId;
}