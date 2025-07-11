package com.shopify.sdk.model.payment;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.shopify.sdk.model.common.*;
import com.shopify.sdk.model.order.Order;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ShopifyPaymentsDispute {
    
    @JsonProperty("id")
    private ID id;
    
    @JsonProperty("amount")
    private MoneyV2 amount;
    
    @JsonProperty("evidenceDueBy")
    private Date evidenceDueBy;
    
    @JsonProperty("evidenceSentOn")
    private Date evidenceSentOn;
    
    @JsonProperty("finalizedOn")
    private Date finalizedOn;
    
    @JsonProperty("initiatedAt")
    private DateTime initiatedAt;
    
    @JsonProperty("legacyResourceId")
    private String legacyResourceId;
    
    @JsonProperty("networkReasonCode")
    private String networkReasonCode;
    
    @JsonProperty("order")
    private Order order;
    
    @JsonProperty("reasonDetails")
    private ShopifyPaymentsDisputeReasonDetails reasonDetails;
    
    @JsonProperty("status")
    private DisputeStatus status;
    
    @JsonProperty("type")
    private DisputeType type;
}

public enum DisputeStatus {
    ACCEPTED,
    CHARGE_REFUNDED,
    LOST,
    NEEDS_RESPONSE,
    UNDER_REVIEW,
    WON
}

public enum DisputeType {
    CHARGEBACK,
    INQUIRY
}

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
class ShopifyPaymentsDisputeReasonDetails {
    
    @JsonProperty("networkReasonCode")
    private String networkReasonCode;
    
    @JsonProperty("reason")
    private ShopifyPaymentsDisputeReason reason;
}

public enum ShopifyPaymentsDisputeReason {
    BANK_CANNOT_PROCESS,
    CREDIT_NOT_PROCESSED,
    CUSTOMER_INITIATED,
    DEBIT_NOT_AUTHORIZED,
    DUPLICATE,
    FRAUDULENT,
    GENERAL,
    INCORRECT_ACCOUNT_DETAILS,
    INSUFFICIENT_FUNDS,
    PRODUCT_NOT_RECEIVED,
    PRODUCT_UNACCEPTABLE,
    SUBSCRIPTION_CANCELLED,
    UNRECOGNIZED
}

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ShopifyPaymentsDisputeEvidence {
    
    @JsonProperty("id")
    private ID id;
    
    @JsonProperty("accessActivityLog")
    private String accessActivityLog;
    
    @JsonProperty("billingAddress")
    private MailingAddress billingAddress;
    
    @JsonProperty("cancellationPolicyDisclosure")
    private String cancellationPolicyDisclosure;
    
    @JsonProperty("cancellationPolicyFile")
    private ShopifyPaymentsDisputeFileUpload cancellationPolicyFile;
    
    @JsonProperty("cancellationRebuttal")
    private String cancellationRebuttal;
    
    @JsonProperty("customerCommunicationFile")
    private ShopifyPaymentsDisputeFileUpload customerCommunicationFile;
    
    @JsonProperty("customerEmailAddress")
    private String customerEmailAddress;
    
    @JsonProperty("customerFirstName")
    private String customerFirstName;
    
    @JsonProperty("customerLastName")
    private String customerLastName;
    
    @JsonProperty("customerPurchaseIp")
    private String customerPurchaseIp;
    
    @JsonProperty("dispute")
    private ShopifyPaymentsDispute dispute;
    
    @JsonProperty("disputeFileUploads")
    private List<ShopifyPaymentsDisputeFileUpload> disputeFileUploads;
    
    @JsonProperty("productDescription")
    private String productDescription;
    
    @JsonProperty("refundPolicyDisclosure")
    private String refundPolicyDisclosure;
    
    @JsonProperty("refundPolicyFile")
    private ShopifyPaymentsDisputeFileUpload refundPolicyFile;
    
    @JsonProperty("refundRefusalExplanation")
    private String refundRefusalExplanation;
    
    @JsonProperty("serviceDocumentationFile")
    private ShopifyPaymentsDisputeFileUpload serviceDocumentationFile;
    
    @JsonProperty("shippingAddress")
    private MailingAddress shippingAddress;
    
    @JsonProperty("shippingDocumentationFile")
    private ShopifyPaymentsDisputeFileUpload shippingDocumentationFile;
    
    @JsonProperty("submitted")
    private Boolean submitted;
    
    @JsonProperty("uncategorizedFile")
    private ShopifyPaymentsDisputeFileUpload uncategorizedFile;
    
    @JsonProperty("uncategorizedText")
    private String uncategorizedText;
}

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
class ShopifyPaymentsDisputeFileUpload {
    
    @JsonProperty("id")
    private ID id;
    
    @JsonProperty("disputeEvidenceType")
    private ShopifyPaymentsDisputeEvidenceType disputeEvidenceType;
    
    @JsonProperty("fileSize")
    private Integer fileSize;
    
    @JsonProperty("fileType")
    private String fileType;
    
    @JsonProperty("filename")
    private String filename;
    
    @JsonProperty("originalFileSize")
    private Integer originalFileSize;
    
    @JsonProperty("url")
    private String url;
}

public enum ShopifyPaymentsDisputeEvidenceType {
    CUSTOMER_COMMUNICATION_FILE,
    UNCATEGORIZED_FILE,
    SERVICE_DOCUMENTATION_FILE,
    SHIPPING_DOCUMENTATION_FILE,
    CANCELLATION_POLICY_FILE,
    REFUND_POLICY_FILE,
    CUSTOMER_SIGNATURE_FILE,
    BILLING_ADDRESS_FILE,
    RECEIPT_FILE
}

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ShopifyPaymentsDisputeConnection {
    
    @JsonProperty("edges")
    private List<ShopifyPaymentsDisputeEdge> edges;
    
    @JsonProperty("pageInfo")
    private PageInfo pageInfo;
}

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
class ShopifyPaymentsDisputeEdge {
    
    @JsonProperty("node")
    private ShopifyPaymentsDispute node;
    
    @JsonProperty("cursor")
    private String cursor;
}

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
class Date {
    
    @JsonProperty("__typename")
    private String typename;
}