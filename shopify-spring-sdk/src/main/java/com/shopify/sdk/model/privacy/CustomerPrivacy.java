package com.shopify.sdk.model.privacy;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Represents customer privacy settings.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CustomerPrivacy {
    /**
     * Whether the customer's personal information should be processed.
     */
    @JsonProperty("dataProcessingOptInLevel")
    private DataProcessingOptInLevel dataProcessingOptInLevel;
    
    /**
     * The customer's consent level for marketing.
     */
    @JsonProperty("marketingConsentLevel")
    private MarketingConsentLevel marketingConsentLevel;
}