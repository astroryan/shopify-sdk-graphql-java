package com.shopify.sdk.model.market;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * Input for registering market localizations.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MarketLocalizationRegisterInput {
    /**
     * The market ID.
     */
    @JsonProperty("marketId")
    private String marketId;
    
    /**
     * The market localizations to register.
     */
    @JsonProperty("marketLocalizations")
    private List<MarketLocalizationInput> marketLocalizations;
}