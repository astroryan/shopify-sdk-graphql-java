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
public class PaymentMandate {
    
    @JsonProperty("id")
    private ID id;
    
    @JsonProperty("paymentInstrument")
    private PaymentInstrument paymentInstrument;
    
    @JsonProperty("events")
    private PaymentMandateEventConnection events;
}
