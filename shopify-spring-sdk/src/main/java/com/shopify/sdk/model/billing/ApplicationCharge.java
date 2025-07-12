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
public class ApplicationCharge {
    
    private String id;
    private String name;
    private String status;
    private BigDecimal price;
    
    @JsonProperty("currency_code")
    private String currencyCode;
    
    @JsonProperty("created_at")
    private Instant createdAt;
    
    @JsonProperty("updated_at")
    private Instant updatedAt;
    
    @JsonProperty("charge_type")
    private String chargeType;
    
    @JsonProperty("return_url")
    private String returnUrl;
    
    @JsonProperty("confirmation_url")
    private String confirmationUrl;
    
    @JsonProperty("test")
    private Boolean test;
    
    @JsonProperty("decorated_return_url")
    private String decoratedReturnUrl;
    
    public boolean isActive() {
        return "active".equals(status);
    }
    
    public boolean isPending() {
        return "pending".equals(status);
    }
    
    public boolean isDeclined() {
        return "declined".equals(status);
    }
    
    public boolean isExpired() {
        return "expired".equals(status);
    }
    
    public boolean isTest() {
        return test != null && test;
    }
    
    public boolean isOneTimeCharge() {
        return chargeType == null || "theme".equals(chargeType) || "credit".equals(chargeType);
    }
}