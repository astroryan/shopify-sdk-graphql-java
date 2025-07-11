package com.shopify.sdk.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.shopify.sdk.model.graphql.GraphQLRequest;
import com.shopify.sdk.model.graphql.GraphQLResponse;
import com.shopify.sdk.client.ShopifyGraphQLClient;
import com.shopify.sdk.auth.ShopifyAuthContext;
import com.shopify.sdk.exception.ShopifyApiException;
import com.shopify.sdk.model.common.PageInfo;
import com.shopify.sdk.model.common.UserError;
import com.shopify.sdk.model.payment.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.*;

@Slf4j
@Service
@RequiredArgsConstructor
public class ShopifyPaymentsService {
    
    private final ShopifyGraphQLClient graphQLClient;
    
    // Query for Shopify Payments account
    private static final String GET_SHOPIFY_PAYMENTS_ACCOUNT_QUERY = """
        query shopifyPaymentsAccount {
            shopifyPaymentsAccount {
                id
                activated
                balance {
                    available {
                        amount
                        currencyCode
                    }
                    onHold {
                        amount
                        currencyCode
                    }
                    pending {
                        amount
                        currencyCode
                    }
                }
                bankAccounts(first: 10) {
                    edges {
                        node {
                            id
                            country
                            createdAt
                            currency
                            status
                            accountNumberLastDigits
                            bankName
                            routingNumber
                        }
                    }
                    pageInfo {
                        hasNextPage
                        endCursor
                    }
                }
                chargeStatementDescriptor
                chargeStatementDescriptors {
                    default
                    prefix
                }
                country
                defaultCurrency
                fraudSettings {
                    declineChargeOnAvsFailure
                    declineChargeOnCvcFailure
                }
                notificationSettings {
                    payouts
                }
                onboardable
                payoutSchedule {
                    interval
                    monthlyAnchor
                    weeklyAnchor
                }
                payoutStatementDescriptor
            }
        }
        """;
    
    // Query for payment sessions
    private static final String GET_PAYMENT_SESSION_QUERY = """
        query node($id: ID!) {
            node(id: $id) {
                ... on PaymentSession {
                    id
                    state
                    paymentDetails {
                        creditCardBrand
                        creditCardLastFourDigits
                        creditCardMaskedNumber
                        creditCardName
                        paymentMethodName
                    }
                    nextAction {
                        action
                        context {
                            redirectUrl
                        }
                    }
                    amount {
                        amount
                        currencyCode
                    }
                    currency
                    customerId
                    order {
                        id
                        name
                    }
                    sourceIdentifier
                    test
                    kind
                    errorMessage
                    group
                    receipt
                }
            }
        }
        """;
    
    // Query for payouts
    private static final String GET_PAYOUTS_QUERY = """
        query shopifyPaymentsAccount($first: Int!, $after: String) {
            shopifyPaymentsAccount {
                payouts(first: $first, after: $after) {
                    edges {
                        node {
                            id
                            bankAccount {
                                id
                                accountNumberLastDigits
                                bankName
                            }
                            gross {
                                amount
                                currencyCode
                            }
                            issuedAt
                            legacyResourceId
                            net {
                                amount
                                currencyCode
                            }
                            status
                            summary {
                                adjustmentsFee {
                                    amount
                                    currencyCode
                                }
                                adjustmentsGross {
                                    amount
                                    currencyCode
                                }
                                chargesFee {
                                    amount
                                    currencyCode
                                }
                                chargesGross {
                                    amount
                                    currencyCode
                                }
                                refundsFee {
                                    amount
                                    currencyCode
                                }
                                refundsFeeGross {
                                    amount
                                    currencyCode
                                }
                                reservedFundsFee {
                                    amount
                                    currencyCode
                                }
                                reservedFundsGross {
                                    amount
                                    currencyCode
                                }
                                retriedPayoutsFee {
                                    amount
                                    currencyCode
                                }
                                retriedPayoutsGross {
                                    amount
                                    currencyCode
                                }
                            }
                            transactionType
                        }
                        cursor
                    }
                    pageInfo {
                        hasNextPage
                        endCursor
                    }
                }
            }
        }
        """;
    
    // Query for dispute
    private static final String GET_DISPUTE_QUERY = """
        query node($id: ID!) {
            node(id: $id) {
                ... on ShopifyPaymentsDispute {
                    id
                    amount {
                        amount
                        currencyCode
                    }
                    evidenceDueBy
                    evidenceSentOn
                    finalizedOn
                    initiatedAt
                    legacyResourceId
                    networkReasonCode
                    order {
                        id
                        name
                    }
                    reasonDetails {
                        networkReasonCode
                        reason
                    }
                    status
                    type
                }
            }
        }
        """;
    
    // Query for dispute evidence
    private static final String GET_DISPUTE_EVIDENCE_QUERY = """
        query node($id: ID!) {
            node(id: $id) {
                ... on ShopifyPaymentsDisputeEvidence {
                    id
                    accessActivityLog
                    billingAddress {
                        address1
                        address2
                        city
                        province
                        country
                        zip
                    }
                    cancellationPolicyDisclosure
                    cancellationPolicyFile {
                        id
                        disputeEvidenceType
                        fileSize
                        fileType
                        filename
                        url
                    }
                    cancellationRebuttal
                    customerCommunicationFile {
                        id
                        disputeEvidenceType
                        fileSize
                        fileType
                        filename
                        url
                    }
                    customerEmailAddress
                    customerFirstName
                    customerLastName
                    customerPurchaseIp
                    dispute {
                        id
                    }
                    disputeFileUploads {
                        id
                        disputeEvidenceType
                        fileSize
                        fileType
                        filename
                        url
                    }
                    productDescription
                    refundPolicyDisclosure
                    refundPolicyFile {
                        id
                        disputeEvidenceType
                        fileSize
                        fileType
                        filename
                        url
                    }
                    refundRefusalExplanation
                    serviceDocumentationFile {
                        id
                        disputeEvidenceType
                        fileSize
                        fileType
                        filename
                        url
                    }
                    shippingAddress {
                        address1
                        address2
                        city
                        province
                        country
                        zip
                    }
                    shippingDocumentationFile {
                        id
                        disputeEvidenceType
                        fileSize
                        fileType
                        filename
                        url
                    }
                    submitted
                    uncategorizedFile {
                        id
                        disputeEvidenceType
                        fileSize
                        fileType
                        filename
                        url
                    }
                    uncategorizedText
                }
            }
        }
        """;
    
    // Mutation to update dispute evidence
    private static final String UPDATE_DISPUTE_EVIDENCE_MUTATION = """
        mutation disputeEvidenceUpdate($id: ID!, $evidence: ShopifyPaymentsDisputeEvidenceUpdateInput!) {
            disputeEvidenceUpdate(id: $id, evidence: $evidence) {
                disputeEvidence {
                    id
                    submitted
                }
                userErrors {
                    field
                    message
                    code
                }
            }
        }
        """;
    
    // Mutation to submit dispute evidence
    private static final String SUBMIT_DISPUTE_EVIDENCE_MUTATION = """
        mutation disputeEvidenceSubmit($id: ID!) {
            disputeEvidenceSubmit(id: $id) {
                disputeEvidence {
                    id
                    submitted
                }
                userErrors {
                    field
                    message
                    code
                }
            }
        }
        """;
    
    // Mutation to configure payout
    private static final String CONFIGURE_PAYOUT_MUTATION = """
        mutation payoutScheduleManage($schedule: PayoutScheduleInput!) {
            payoutScheduleManage(schedule: $schedule) {
                shopifyPaymentsAccount {
                    id
                    payoutSchedule {
                        interval
                        monthlyAnchor
                        weeklyAnchor
                    }
                }
                userErrors {
                    field
                    message
                    code
                }
            }
        }
        """;
    
    // Input classes
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ShopifyPaymentsDisputeEvidenceUpdateInput {
        private String accessActivityLog;
        private String cancellationPolicyDisclosure;
        private String cancellationRebuttal;
        private String customerEmailAddress;
        private String customerFirstName;
        private String customerLastName;
        private String customerPurchaseIp;
        private String productDescription;
        private String refundPolicyDisclosure;
        private String refundRefusalExplanation;
        private String uncategorizedText;
    }
    
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class PayoutScheduleInput {
        private ShopifyPaymentsPayoutInterval interval;
        private Integer monthlyAnchor;
        private ShopifyPaymentsPayoutWeeklyAnchor weeklyAnchor;
    }
    
    // Service methods
    public ShopifyPaymentsAccount getShopifyPaymentsAccount(ShopifyAuthContext context) {
        ShopifyGraphQLClient.setAuthContext(context);
        
        GraphQLRequest request = GraphQLRequest.builder()
                .query(GET_SHOPIFY_PAYMENTS_ACCOUNT_QUERY)
                .variables(new HashMap<>())
                .build();
        
        GraphQLResponse<ShopifyPaymentsAccountResponse> response = graphQLClient.execute(
                request,
                new TypeReference<GraphQLResponse<ShopifyPaymentsAccountResponse>>() {}
        );
        
        if (response.hasErrors()) {
            throw new ShopifyApiException("Failed to get Shopify Payments account", response.getErrors());
        }
        
        return response.getData().shopifyPaymentsAccount;
    }
    
    public PaymentSession getPaymentSession(ShopifyAuthContext context, String id) {
        ShopifyGraphQLClient.setAuthContext(context);
        
        Map<String, Object> variables = new HashMap<>();
        variables.put("id", id);
        
        GraphQLRequest request = GraphQLRequest.builder()
                .query(GET_PAYMENT_SESSION_QUERY)
                .variables(variables)
                .build();
        
        GraphQLResponse<NodeResponse<PaymentSession>> response = graphQLClient.execute(
                request,
                new TypeReference<GraphQLResponse<NodeResponse<PaymentSession>>>() {}
        );
        
        if (response.hasErrors()) {
            throw new ShopifyApiException("Failed to get payment session", response.getErrors());
        }
        
        return response.getData().node;
    }
    
    public PayoutsConnection getPayouts(ShopifyAuthContext context, int first, String after) {
        ShopifyGraphQLClient.setAuthContext(context);
        
        Map<String, Object> variables = new HashMap<>();
        variables.put("first", first);
        if (after != null) {
            variables.put("after", after);
        }
        
        GraphQLRequest request = GraphQLRequest.builder()
                .query(GET_PAYOUTS_QUERY)
                .variables(variables)
                .build();
        
        GraphQLResponse<ShopifyPaymentsAccountPayoutsResponse> response = graphQLClient.execute(
                request,
                new TypeReference<GraphQLResponse<ShopifyPaymentsAccountPayoutsResponse>>() {}
        );
        
        if (response.hasErrors()) {
            throw new ShopifyApiException("Failed to get payouts", response.getErrors());
        }
        
        return response.getData().shopifyPaymentsAccount.payouts;
    }
    
    public ShopifyPaymentsDispute getDispute(ShopifyAuthContext context, String id) {
        ShopifyGraphQLClient.setAuthContext(context);
        
        Map<String, Object> variables = new HashMap<>();
        variables.put("id", id);
        
        GraphQLRequest request = GraphQLRequest.builder()
                .query(GET_DISPUTE_QUERY)
                .variables(variables)
                .build();
        
        GraphQLResponse<NodeResponse<ShopifyPaymentsDispute>> response = graphQLClient.execute(
                request,
                new TypeReference<GraphQLResponse<NodeResponse<ShopifyPaymentsDispute>>>() {}
        );
        
        if (response.hasErrors()) {
            throw new ShopifyApiException("Failed to get dispute", response.getErrors());
        }
        
        return response.getData().node;
    }
    
    public ShopifyPaymentsDisputeEvidence getDisputeEvidence(ShopifyAuthContext context, String id) {
        ShopifyGraphQLClient.setAuthContext(context);
        
        Map<String, Object> variables = new HashMap<>();
        variables.put("id", id);
        
        GraphQLRequest request = GraphQLRequest.builder()
                .query(GET_DISPUTE_EVIDENCE_QUERY)
                .variables(variables)
                .build();
        
        GraphQLResponse<NodeResponse<ShopifyPaymentsDisputeEvidence>> response = graphQLClient.execute(
                request,
                new TypeReference<GraphQLResponse<NodeResponse<ShopifyPaymentsDisputeEvidence>>>() {}
        );
        
        if (response.hasErrors()) {
            throw new ShopifyApiException("Failed to get dispute evidence", response.getErrors());
        }
        
        return response.getData().node;
    }
    
    public ShopifyPaymentsDisputeEvidence updateDisputeEvidence(
            ShopifyAuthContext context,
            String id,
            ShopifyPaymentsDisputeEvidenceUpdateInput evidence) {
        ShopifyGraphQLClient.setAuthContext(context);
        
        Map<String, Object> variables = new HashMap<>();
        variables.put("id", id);
        variables.put("evidence", evidence);
        
        GraphQLRequest request = GraphQLRequest.builder()
                .query(UPDATE_DISPUTE_EVIDENCE_MUTATION)
                .variables(variables)
                .build();
        
        GraphQLResponse<DisputeEvidenceUpdateResponse> response = graphQLClient.execute(
                request,
                new TypeReference<GraphQLResponse<DisputeEvidenceUpdateResponse>>() {}
        );
        
        if (response.hasErrors()) {
            throw new ShopifyApiException("Failed to update dispute evidence", response.getErrors());
        }
        
        if (response.getData().disputeEvidenceUpdate.userErrors != null && 
            !response.getData().disputeEvidenceUpdate.userErrors.isEmpty()) {
            throw new ShopifyApiException(
                    "Failed to update dispute evidence",
                    response.getData().disputeEvidenceUpdate.userErrors
            );
        }
        
        return response.getData().disputeEvidenceUpdate.disputeEvidence;
    }
    
    public ShopifyPaymentsDisputeEvidence submitDisputeEvidence(ShopifyAuthContext context, String id) {
        ShopifyGraphQLClient.setAuthContext(context);
        
        Map<String, Object> variables = new HashMap<>();
        variables.put("id", id);
        
        GraphQLRequest request = GraphQLRequest.builder()
                .query(SUBMIT_DISPUTE_EVIDENCE_MUTATION)
                .variables(variables)
                .build();
        
        GraphQLResponse<DisputeEvidenceSubmitResponse> response = graphQLClient.execute(
                request,
                new TypeReference<GraphQLResponse<DisputeEvidenceSubmitResponse>>() {}
        );
        
        if (response.hasErrors()) {
            throw new ShopifyApiException("Failed to submit dispute evidence", response.getErrors());
        }
        
        if (response.getData().disputeEvidenceSubmit.userErrors != null && 
            !response.getData().disputeEvidenceSubmit.userErrors.isEmpty()) {
            throw new ShopifyApiException(
                    "Failed to submit dispute evidence",
                    response.getData().disputeEvidenceSubmit.userErrors
            );
        }
        
        return response.getData().disputeEvidenceSubmit.disputeEvidence;
    }
    
    public ShopifyPaymentsAccount configurePayoutSchedule(
            ShopifyAuthContext context,
            PayoutScheduleInput schedule) {
        ShopifyGraphQLClient.setAuthContext(context);
        
        Map<String, Object> variables = new HashMap<>();
        variables.put("schedule", schedule);
        
        GraphQLRequest request = GraphQLRequest.builder()
                .query(CONFIGURE_PAYOUT_MUTATION)
                .variables(variables)
                .build();
        
        GraphQLResponse<PayoutScheduleManageResponse> response = graphQLClient.execute(
                request,
                new TypeReference<GraphQLResponse<PayoutScheduleManageResponse>>() {}
        );
        
        if (response.hasErrors()) {
            throw new ShopifyApiException("Failed to configure payout schedule", response.getErrors());
        }
        
        if (response.getData().payoutScheduleManage.userErrors != null && 
            !response.getData().payoutScheduleManage.userErrors.isEmpty()) {
            throw new ShopifyApiException(
                    "Failed to configure payout schedule",
                    response.getData().payoutScheduleManage.userErrors
            );
        }
        
        return response.getData().payoutScheduleManage.shopifyPaymentsAccount;
    }
    
    // Response classes
    @Data
    private static class ShopifyPaymentsAccountResponse {
        private ShopifyPaymentsAccount shopifyPaymentsAccount;
    }
    
    @Data
    private static class NodeResponse<T> {
        private T node;
    }
    
    @Data
    private static class ShopifyPaymentsAccountPayoutsResponse {
        private ShopifyPaymentsAccountPayouts shopifyPaymentsAccount;
    }
    
    @Data
    private static class ShopifyPaymentsAccountPayouts {
        private PayoutsConnection payouts;
    }
    
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class PayoutsConnection {
        private List<PayoutEdge> edges;
        private PageInfo pageInfo;
    }
    
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    private static class PayoutEdge {
        private ShopifyPaymentsPayout node;
        private String cursor;
    }
    
    @Data
    private static class DisputeEvidenceUpdateResponse {
        private DisputeEvidenceUpdateResult disputeEvidenceUpdate;
    }
    
    @Data
    private static class DisputeEvidenceUpdateResult {
        private ShopifyPaymentsDisputeEvidence disputeEvidence;
        private List<UserError> userErrors;
    }
    
    @Data
    private static class DisputeEvidenceSubmitResponse {
        private DisputeEvidenceSubmitResult disputeEvidenceSubmit;
    }
    
    @Data
    private static class DisputeEvidenceSubmitResult {
        private ShopifyPaymentsDisputeEvidence disputeEvidence;
        private List<UserError> userErrors;
    }
    
    @Data
    private static class PayoutScheduleManageResponse {
        private PayoutScheduleManageResult payoutScheduleManage;
    }
    
    @Data
    private static class PayoutScheduleManageResult {
        private ShopifyPaymentsAccount shopifyPaymentsAccount;
        private List<UserError> userErrors;
    }
}