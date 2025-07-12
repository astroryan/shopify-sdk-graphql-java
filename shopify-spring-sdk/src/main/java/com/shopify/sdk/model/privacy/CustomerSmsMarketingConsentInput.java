package com.shopify.sdk.model.privacy;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.OffsetDateTime;

/**
 * Input for updating customer SMS marketing consent.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CustomerSmsMarketingConsentInput {
    /**
     * The customer ID.
     */
    @JsonProperty("customerId")
    private String customerId;
    
    /**
     * The SMS marketing state.
     */
    @JsonProperty("marketingState")
    private CustomerSmsMarketingState marketingState;
    
    /**
     * The marketing opt-in level.
     */
    @JsonProperty("marketingOptInLevel")
    private CustomerMarketingOptInLevel marketingOptInLevel;
    
    /**
     * When consent was updated.
     */
    @JsonProperty("consentUpdatedAt")
    private OffsetDateTime consentUpdatedAt;
}