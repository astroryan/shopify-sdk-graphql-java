package com.shopify.sdk.model.privacy;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.shopify.sdk.model.common.Node;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.OffsetDateTime;

/**
 * Represents a consent record.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ConsentRecord implements Node {
    /**
     * The unique identifier for the consent record.
     */
    @JsonProperty("id")
    private String id;
    
    /**
     * The customer ID associated with the consent.
     */
    @JsonProperty("customerId")
    private String customerId;
    
    /**
     * The email address associated with the consent.
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
    
    /**
     * When the consent was collected.
     */
    @JsonProperty("collectedAt")
    private OffsetDateTime collectedAt;
    
    /**
     * When the consent was created.
     */
    @JsonProperty("createdAt")
    private OffsetDateTime createdAt;
    
    /**
     * When the consent was updated.
     */
    @JsonProperty("updatedAt")
    private OffsetDateTime updatedAt;
}