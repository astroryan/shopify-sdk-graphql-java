package com.shopify.sdk.model.metafield;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * Input for setting metafields.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MetafieldsSetInput {
    /**
     * The metafields to be set.
     */
    @JsonProperty("metafields")
    private List<MetafieldInput> metafields;
}