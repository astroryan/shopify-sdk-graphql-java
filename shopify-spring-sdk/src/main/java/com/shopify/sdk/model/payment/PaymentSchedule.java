package com.shopify.sdk.model.payment;

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
public class PaymentSchedule {
    
    @JsonProperty("id")
    private ID id;
    
    @JsonProperty("amount")
    private MoneyV2 amount;
    
    @JsonProperty("completedAt")
    private DateTime completedAt;
    
    @JsonProperty("dueAt")
    private DateTime dueAt;
    
    @JsonProperty("issuedAt")
    private DateTime issuedAt;
}