package com.shopify.sdk.model.metafield;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Input for deleting a metafield.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MetafieldDeleteInput {
    /**
     * The ID of the metafield to delete.
     */
    @JsonProperty("id")
    private String id;
}