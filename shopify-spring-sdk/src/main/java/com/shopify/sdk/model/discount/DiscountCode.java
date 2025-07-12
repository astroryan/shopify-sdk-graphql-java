package com.shopify.sdk.model.discount;

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
public class DiscountCode {
    
    private String id;
    private String code;
    
    @JsonProperty("usage_count")
    private Integer usageCount;
    
    @JsonProperty("created_at")
    private Instant createdAt;
    
    @JsonProperty("updated_at")
    private Instant updatedAt;
    
    @JsonProperty("price_rule_id")
    private String priceRuleId;
    
    public boolean hasBeenUsed() {
        return usageCount != null && usageCount > 0;
    }
}