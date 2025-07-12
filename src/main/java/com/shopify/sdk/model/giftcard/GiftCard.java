package com.shopify.sdk.model.giftcard;

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
public class GiftCard {
    
    private String id;
    
    @JsonProperty("api_client_id")
    private String apiClientId;
    
    @JsonProperty("user_id")
    private String userId;
    
    @JsonProperty("order_id")
    private String orderId;
    
    @JsonProperty("line_item_id")
    private String lineItemId;
    
    private BigDecimal balance;
    
    @JsonProperty("currency")
    private String currency;
    
    private String code;
    
    @JsonProperty("last_characters")
    private String lastCharacters;
    
    private String note;
    
    @JsonProperty("template_suffix")
    private String templateSuffix;
    
    @JsonProperty("initial_value")
    private BigDecimal initialValue;
    
    @JsonProperty("customer_id")
    private String customerId;
    
    @JsonProperty("created_at")
    private Instant createdAt;
    
    @JsonProperty("updated_at")
    private Instant updatedAt;
    
    @JsonProperty("disabled_at")
    private Instant disabledAt;
    
    @JsonProperty("expires_on")
    private Instant expiresOn;
    
    @JsonProperty("admin_graphql_api_id")
    private String adminGraphqlApiId;
    
    public boolean isActive() {
        return disabledAt == null && !isExpired() && hasBalance();
    }
    
    public boolean isDisabled() {
        return disabledAt != null;
    }
    
    public boolean isExpired() {
        return expiresOn != null && expiresOn.isBefore(Instant.now());
    }
    
    public boolean hasBalance() {
        return balance != null && balance.compareTo(BigDecimal.ZERO) > 0;
    }
    
    public boolean isFullyUsed() {
        return balance == null || balance.compareTo(BigDecimal.ZERO) == 0;
    }
    
    public BigDecimal getUsedAmount() {
        if (initialValue == null || balance == null) {
            return BigDecimal.ZERO;
        }
        return initialValue.subtract(balance);
    }
    
    public double getUsagePercentage() {
        if (initialValue == null || initialValue.compareTo(BigDecimal.ZERO) == 0) {
            return 0.0;
        }
        
        BigDecimal used = getUsedAmount();
        return used.divide(initialValue, 4, BigDecimal.ROUND_HALF_UP)
                   .multiply(BigDecimal.valueOf(100))
                   .doubleValue();
    }
    
    public String getMaskedCode() {
        if (code == null || code.length() <= 4) {
            return code;
        }
        
        int visibleChars = 4;
        int maskedLength = code.length() - visibleChars;
        StringBuilder masked = new StringBuilder();
        
        for (int i = 0; i < maskedLength; i++) {
            masked.append("*");
        }
        
        masked.append(code.substring(maskedLength));
        return masked.toString();
    }
}