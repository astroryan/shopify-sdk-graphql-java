package com.shopify.sdk.model.billing;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Builder;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AppSubscriptionLineItem {
    
    private String id;
    private String plan;
    
    @JsonProperty("usage_records")
    private UsageRecordConnection usageRecords;
    
    public static class UsageRecordConnection {
        private String id;
        private String description;
        private BigDecimal price;
        private String category;
        private String subcategory;
        
        @JsonProperty("billing_on")
        private String billingOn;
        
        @JsonProperty("balance_used")
        private BigDecimal balanceUsed;
        
        @JsonProperty("balance_remaining")
        private BigDecimal balanceRemaining;
        
        @JsonProperty("risk_level")
        private Integer riskLevel;
    }
}