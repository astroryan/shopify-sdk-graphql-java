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
public class PaymentTerms {
    
    @JsonProperty("id")
    private ID id;
    
    @JsonProperty("dueInDays")
    private Integer dueInDays;
    
    @JsonProperty("overdue")
    private Boolean overdue;
    
    @JsonProperty("paymentSchedules")
    private PaymentScheduleConnection paymentSchedules;
    
    @JsonProperty("paymentTermsName")
    private String paymentTermsName;
    
    @JsonProperty("paymentTermsType")
    private PaymentTermsType paymentTermsType;
    
    @JsonProperty("translatedName")
    private String translatedName;
}