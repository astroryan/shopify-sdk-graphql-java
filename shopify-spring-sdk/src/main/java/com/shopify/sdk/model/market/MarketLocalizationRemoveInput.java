package com.shopify.sdk.model.market;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * Input for removing market localizations.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MarketLocalizationRemoveInput {
    /**
     * The market localization IDs to remove.
     */
    @JsonProperty("marketLocalizationIds")
    private List<String> marketLocalizationIds;
}