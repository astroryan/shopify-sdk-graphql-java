package com.shopify.sdk.model.common;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Weight {
    
    @JsonProperty("value")
    private BigDecimal value;
    
    @JsonProperty("unit")
    private WeightUnit unit;
    
    public static Weight of(BigDecimal value, WeightUnit unit) {
        return Weight.builder()
                .value(value)
                .unit(unit)
                .build();
    }
    
    public static Weight of(double value, WeightUnit unit) {
        return Weight.builder()
                .value(BigDecimal.valueOf(value))
                .unit(unit)
                .build();
    }
    
    public static Weight of(String value, WeightUnit unit) {
        return Weight.builder()
                .value(new BigDecimal(value))
                .unit(unit)
                .build();
    }
}