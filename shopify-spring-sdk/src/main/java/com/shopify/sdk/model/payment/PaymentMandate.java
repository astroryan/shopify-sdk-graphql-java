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

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
class PaymentInstrument {
    
    @JsonProperty("__typename")
    private String typename;
    
    @JsonProperty("billingAddress")
    private MailingAddress billingAddress;
}

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
class PaymentMandateEventConnection {
    
    @JsonProperty("edges")
    private List<PaymentMandateEventEdge> edges;
    
    @JsonProperty("pageInfo")
    private PageInfo pageInfo;
}

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
class PaymentMandateEventEdge {
    
    @JsonProperty("node")
    private PaymentMandateEvent node;
    
    @JsonProperty("cursor")
    private String cursor;
}

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
class PaymentMandateEvent {
    
    @JsonProperty("id")
    private ID id;
    
    @JsonProperty("code")
    private String code;
    
    @JsonProperty("eventTime")
    private DateTime eventTime;
    
    @JsonProperty("message")
    private String message;
}

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

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
class PaymentScheduleConnection {
    
    @JsonProperty("edges")
    private List<PaymentScheduleEdge> edges;
    
    @JsonProperty("pageInfo")
    private PageInfo pageInfo;
}

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
class PaymentScheduleEdge {
    
    @JsonProperty("node")
    private PaymentSchedule node;
    
    @JsonProperty("cursor")
    private String cursor;
}

public enum PaymentTermsType {
    NET,
    FIXED,
    RECEIPT,
    UNKNOWN
}