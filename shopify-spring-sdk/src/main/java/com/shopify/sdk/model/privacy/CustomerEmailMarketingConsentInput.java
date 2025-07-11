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
public class CustomerEmailMarketingConsentInput {
    
    @JsonProperty("marketingOptInLevel")
    private CustomerMarketingOptInLevel marketingOptInLevel;
    
    @JsonProperty("marketingState")
    private CustomerEmailMarketingState marketingState;
}