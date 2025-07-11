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