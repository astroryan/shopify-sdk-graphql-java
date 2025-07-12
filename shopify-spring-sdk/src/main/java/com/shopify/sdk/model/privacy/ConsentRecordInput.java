package com.shopify.sdk.model.privacy;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Input for creating or updating a consent record.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ConsentRecordInput {
    /**
     * The customer ID.
     */
    @JsonProperty("customerId")
    private String customerId;
    
    /**
     * The email address.
     */
    @JsonProperty("email")
    private String email;
    
    /**
     * The consent level.
     */
    @JsonProperty("consentLevel")
    private MarketingConsentLevel consentLevel;
    
    /**
     * Where the consent was collected from.
     */
    @JsonProperty("collectedFrom")
    private ConsentCollectedFrom collectedFrom;
    
    /**
     * The marketing opt-in level.
     */
    @JsonProperty("marketingOptInLevel")
    private CustomerMarketingOptInLevel marketingOptInLevel;
    
    /**
     * The email marketing state.
     */
    @JsonProperty("emailMarketingState")
    private CustomerEmailMarketingState emailMarketingState;
    
    /**
     * The SMS marketing state.
     */
    @JsonProperty("smsMarketingState")
    private CustomerSmsMarketingState smsMarketingState;
}