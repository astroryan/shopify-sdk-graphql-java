package com.shopify.sdk.model.privacy;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.shopify.sdk.model.common.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ConsentRecord {
    
    @JsonProperty("id")
    private ID id;
    
    @JsonProperty("consentCollectedFrom")
    private ConsentCollectedFrom consentCollectedFrom;
    
    @JsonProperty("consentDate")
    private DateTime consentDate;
    
    @JsonProperty("consentUpdatedAt")
    private DateTime consentUpdatedAt;
    
    @JsonProperty("customer")
    private Customer customer;
    
    @JsonProperty("email")
    private String email;
    
    @JsonProperty("emailConsentState")
    private CustomerEmailMarketingState emailConsentState;
    
    @JsonProperty("emailMarketingConsent")
    private CustomerEmailMarketingConsent emailMarketingConsent;
    
    @JsonProperty("phone")
    private String phone;
    
    @JsonProperty("smsConsentState")
    private CustomerSmsMarketingState smsConsentState;
    
    @JsonProperty("smsMarketingConsent")
    private CustomerSmsMarketingConsent smsMarketingConsent;
}