package com.shopify.sdk.model.payment;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.shopify.sdk.model.common.Address;
import com.shopify.sdk.model.common.Node;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * Represents evidence submitted for a Shopify Payments dispute.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ShopifyPaymentsDisputeEvidence implements Node {
    /**
     * A globally unique identifier.
     */
    @JsonProperty("id")
    private String id;
    
    /**
     * The access activity log.
     */
    @JsonProperty("accessActivityLog")
    private String accessActivityLog;
    
    /**
     * The billing address associated with the payment.
     */
    @JsonProperty("billingAddress")
    private Address billingAddress;
    
    /**
     * The cancellation policy disclosure text.
     */
    @JsonProperty("cancellationPolicyDisclosure")
    private String cancellationPolicyDisclosure;
    
    /**
     * The cancellation policy file.
     */
    @JsonProperty("cancellationPolicyFile")
    private ShopifyPaymentsDisputeFileUpload cancellationPolicyFile;
    
    /**
     * The cancellation rebuttal text.
     */
    @JsonProperty("cancellationRebuttal")
    private String cancellationRebuttal;
    
    /**
     * The customer communication file.
     */
    @JsonProperty("customerCommunicationFile")
    private ShopifyPaymentsDisputeFileUpload customerCommunicationFile;
    
    /**
     * The customer email address.
     */
    @JsonProperty("customerEmailAddress")
    private String customerEmailAddress;
    
    /**
     * The customer's first name.
     */
    @JsonProperty("customerFirstName")
    private String customerFirstName;
    
    /**
     * The customer's last name.
     */
    @JsonProperty("customerLastName")
    private String customerLastName;
    
    /**
     * The customer's purchase IP address.
     */
    @JsonProperty("customerPurchaseIp")
    private String customerPurchaseIp;
    
    /**
     * The dispute this evidence belongs to.
     */
    @JsonProperty("dispute")
    private ShopifyPaymentsDispute dispute;
    
    /**
     * All dispute file uploads.
     */
    @JsonProperty("disputeFileUploads")
    private List<ShopifyPaymentsDisputeFileUpload> disputeFileUploads;
    
    /**
     * The product description.
     */
    @JsonProperty("productDescription")
    private String productDescription;
    
    /**
     * The refund policy disclosure text.
     */
    @JsonProperty("refundPolicyDisclosure")
    private String refundPolicyDisclosure;
    
    /**
     * The refund policy file.
     */
    @JsonProperty("refundPolicyFile")
    private ShopifyPaymentsDisputeFileUpload refundPolicyFile;
    
    /**
     * The refund refusal explanation.
     */
    @JsonProperty("refundRefusalExplanation")
    private String refundRefusalExplanation;
    
    /**
     * The service documentation file.
     */
    @JsonProperty("serviceDocumentationFile")
    private ShopifyPaymentsDisputeFileUpload serviceDocumentationFile;
    
    /**
     * The shipping address.
     */
    @JsonProperty("shippingAddress")
    private Address shippingAddress;
    
    /**
     * The shipping documentation file.
     */
    @JsonProperty("shippingDocumentationFile")
    private ShopifyPaymentsDisputeFileUpload shippingDocumentationFile;
    
    /**
     * Whether the evidence has been submitted.
     */
    @JsonProperty("submitted")
    private Boolean submitted;
    
    /**
     * The uncategorized file.
     */
    @JsonProperty("uncategorizedFile")
    private ShopifyPaymentsDisputeFileUpload uncategorizedFile;
    
    /**
     * The uncategorized text.
     */
    @JsonProperty("uncategorizedText")
    private String uncategorizedText;
}

/**
 * Represents a file upload for dispute evidence.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
class ShopifyPaymentsDisputeFileUpload {
    /**
     * The globally unique identifier.
     */
    @JsonProperty("id")
    private String id;
    
    /**
     * The type of dispute evidence.
     */
    @JsonProperty("disputeEvidenceType")
    private DisputeEvidenceType disputeEvidenceType;
    
    /**
     * The file size in bytes.
     */
    @JsonProperty("fileSize")
    private Long fileSize;
    
    /**
     * The file type.
     */
    @JsonProperty("fileType")
    private String fileType;
    
    /**
     * The filename.
     */
    @JsonProperty("filename")
    private String filename;
    
    /**
     * The URL to access the file.
     */
    @JsonProperty("url")
    private String url;
}

/**
 * The type of dispute evidence.
 */
enum DisputeEvidenceType {
    @JsonProperty("CUSTOMER_COMMUNICATION")
    CUSTOMER_COMMUNICATION,
    
    @JsonProperty("UNCATEGORIZED_FILE")
    UNCATEGORIZED_FILE,
    
    @JsonProperty("SERVICE_DOCUMENTATION")
    SERVICE_DOCUMENTATION,
    
    @JsonProperty("SHIPPING_DOCUMENTATION")
    SHIPPING_DOCUMENTATION,
    
    @JsonProperty("CANCELLATION_POLICY_FILE")
    CANCELLATION_POLICY_FILE,
    
    @JsonProperty("REFUND_POLICY_FILE")
    REFUND_POLICY_FILE,
    
    @JsonProperty("RECEIPT")
    RECEIPT
}