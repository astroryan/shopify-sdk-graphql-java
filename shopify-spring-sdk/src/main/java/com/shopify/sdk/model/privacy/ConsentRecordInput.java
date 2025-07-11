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
public class ConsentRecordInput {
    
    @JsonProperty("customerId")
    private ID customerId;
    
    @JsonProperty("email")
    private String email;
    
    @JsonProperty("phone")
    private String phone;
    
    @JsonProperty("emailConsentState")
    private CustomerEmailMarketingState emailConsentState;
    
    @JsonProperty("smsConsentState")
    private CustomerSmsMarketingState smsConsentState;
    
    @JsonProperty("consentCollectedFrom")
    private ConsentCollectedFrom consentCollectedFrom;
}