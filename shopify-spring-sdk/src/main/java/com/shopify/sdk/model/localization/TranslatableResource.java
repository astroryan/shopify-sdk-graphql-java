package com.shopify.sdk.model.localization;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.shopify.sdk.model.common.Node;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * Represents a translatable resource in Shopify.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TranslatableResource implements Node {
    /**
     * A globally unique identifier.
     */
    @JsonProperty("id")
    private String id;
    
    /**
     * The type of the resource.
     */
    @JsonProperty("resourceType")
    private String resourceType;
    
    /**
     * The translations for this resource.
     */
    @JsonProperty("translations")
    private List<Translation> translations;
    
    /**
     * The translatable content fields.
     */
    @JsonProperty("translatableContent")
    private List<TranslatableContent> translatableContent;
}