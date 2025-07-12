package com.shopify.sdk.model.billing;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Builder;

import java.math.BigDecimal;
import java.time.Instant;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UsageRecord {
    
    private String id;
    private String description;
    private BigDecimal price;
    
    @JsonProperty("created_at")
    private Instant createdAt;
    
    @JsonProperty("updated_at")
    private Instant updatedAt;
    
    @JsonProperty("currency_code")
    private String currencyCode;
    
    @JsonProperty("balance_used")
    private BigDecimal balanceUsed;
    
    @JsonProperty("balance_remaining")
    private BigDecimal balanceRemaining;
    
    @JsonProperty("risk_level")
    private Integer riskLevel;
    
    @JsonProperty("subscription_line_item_id")
    private String subscriptionLineItemId;
    
    @JsonProperty("recurring_application_charge_id")
    private String recurringApplicationChargeId;
    
    public boolean isRisky() {
        return riskLevel != null && riskLevel > 0;
    }
    
    public boolean hasBalance() {
        return balanceRemaining != null && balanceRemaining.compareTo(BigDecimal.ZERO) > 0;
    }
}