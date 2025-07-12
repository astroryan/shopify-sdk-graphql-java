package com.shopify.sdk.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.shopify.sdk.model.graphql.GraphQLRequest;
import com.shopify.sdk.model.graphql.GraphQLResponse;
import com.shopify.sdk.client.ShopifyGraphQLClient;
import com.shopify.sdk.auth.ShopifyAuthContext;
import com.shopify.sdk.exception.ShopifyApiException;
import com.shopify.sdk.model.common.UserError;
import com.shopify.sdk.model.privacy.*;
import com.shopify.sdk.model.customer.Customer;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.*;

@Slf4j
@Service
@RequiredArgsConstructor
public class PrivacyService {
    
    private final ShopifyGraphQLClient graphQLClient;
    
    // Query for customer privacy
    private static final String GET_CUSTOMER_PRIVACY_QUERY = """
        query customerPrivacy {
            customerPrivacy {
                messageEncrypted
                translatableContent
            }
        }
        """;
    
    // Query for customer data requests
    private static final String GET_CUSTOMER_DATA_REQUESTS_QUERY = """
        query customerDataRequests($first: Int!, $after: String, $query: String) {
            shop {
                customerDataRequests(first: $first, after: $after, query: $query) {
                    edges {
                        node {
                            id
                            createdAt
                            customer {
                                id
                                email
                                firstName
                                lastName
                            }
                            dataAccessType
                            email
                            failedAt
                            failureReason
                            requestedAt
                            state
                            updatedAt
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
    
    // Query for customer requests
    private static final String GET_CUSTOMER_REQUESTS_QUERY = """
        query customerRequests($first: Int!, $after: String, $status: CustomerRequestStatus, $requestType: CustomerRequestType) {
            customerRequests(
                first: $first,
                after: $after,
                status: $status,
                requestType: $requestType
            ) {
                edges {
                    node {
                        id
                        customerId
                        email
                        firstName
                        lastName
                        phone
                        requestDate
                        requestType
                        status
                    }
                    cursor
                }
                pageInfo {
                    hasNextPage
                    endCursor
                }
            }
        }
        """;
    
    // Query for consent records
    private static final String GET_CONSENT_RECORDS_QUERY = """
        query consentRecords($first: Int!, $after: String, $query: String) {
            consentRecords(first: $first, after: $after, query: $query) {
                edges {
                    node {
                        id
                        consentCollectedFrom
                        consentDate
                        consentUpdatedAt
                        customer {
                            id
                            email
                        }
                        email
                        emailConsentState
                        emailMarketingConsent {
                            consentUpdatedAt
                            marketingOptInLevel
                            marketingState
                        }
                        phone
                        smsConsentState
                        smsMarketingConsent {
                            consentCollectedFrom
                            consentUpdatedAt
                            marketingOptInLevel
                            marketingState
                        }
                    }
                    cursor
                }
                pageInfo {
                    hasNextPage
                    endCursor
                }
            }
        }
        """;
    
    // Query for GDPR requests
    private static final String GET_GDPR_REQUESTS_QUERY = """
        query gdprRequests($first: Int!, $after: String, $state: GDPRRequestState, $requestType: GDPRRequestType) {
            gdprRequests(
                first: $first,
                after: $after,
                state: $state,
                requestType: $requestType
            ) {
                edges {
                    node {
                        id
                        createdAt
                        customer {
                            id
                            email
                        }
                        customerEmail
                        dataCategories
                        processingState
                        requestType
                        requestedAt
                        completedAt
                        deniedAt
                        deniedReason
                    }
                    cursor
                }
                pageInfo {
                    hasNextPage
                    endCursor
                }
            }
        }
        """;
    
    // Query for privacy policy
    private static final String GET_PRIVACY_POLICY_QUERY = """
        query shop {
            shop {
                privacyPolicy {
                    id
                    body
                    createdAt
                    handle
                    title
                    updatedAt
                    url
                }
            }
        }
        """;
    
    // Query for data protection settings
    private static final String GET_DATA_PROTECTION_SETTINGS_QUERY = """
        query shop {
            shop {
                dataProtectionSetting {
                    dataRetentionPeriod
                    dataRetentionPeriodDays
                    requestProcessingDays
                    rightToBeInformed
                    rightOfAccess
                    rightToRectification
                    rightToErasure
                    rightToRestriction
                    rightToPortability
                    rightToObject
                    automatedDecisionMaking
                }
            }
        }
        """;
    
    // Mutation to create customer request
    private static final String CREATE_CUSTOMER_REQUEST_MUTATION = """
        mutation customerRequestCreate($input: CustomerRequestInput!) {
            customerRequestCreate(input: $input) {
                customerRequest {
                    id
                    customerId
                    email
                    requestType
                    status
                }
                userErrors {
                    field
                    message
                }
            }
        }
        """;
    
    // Mutation to update customer request
    private static final String UPDATE_CUSTOMER_REQUEST_MUTATION = """
        mutation customerRequestUpdate($id: ID!, $status: CustomerRequestStatus!) {
            customerRequestUpdate(id: $id, status: $status) {
                customerRequest {
                    id
                    status
                }
                userErrors {
                    field
                    message
                }
            }
        }
        """;
    
    // Mutation to create consent record
    private static final String CREATE_CONSENT_RECORD_MUTATION = """
        mutation consentRecordCreate($input: ConsentRecordInput!) {
            consentRecordCreate(input: $input) {
                consentRecord {
                    id
                    email
                    emailConsentState
                    smsConsentState
                }
                userErrors {
                    field
                    message
                }
            }
        }
        """;
    
    // Mutation to update data protection settings
    private static final String UPDATE_DATA_PROTECTION_SETTINGS_MUTATION = """
        mutation dataProtectionSettingsUpdate($input: DataProtectionSettingInput!) {
            dataProtectionSettingsUpdate(input: $input) {
                dataProtectionSetting {
                    dataRetentionPeriod
                    dataRetentionPeriodDays
                    requestProcessingDays
                }
                userErrors {
                    field
                    message
                }
            }
        }
        """;
    
    // Mutation to request customer data
    private static final String REQUEST_CUSTOMER_DATA_MUTATION = """
        mutation customerDataRequestCreate($customerId: ID!, $dataAccessType: DataAccessType!) {
            customerDataRequestCreate(
                customerId: $customerId,
                dataAccessType: $dataAccessType
            ) {
                customerDataRequest {
                    id
                    state
                    dataAccessType
                }
                userErrors {
                    field
                    message
                }
            }
        }
        """;
    
    // Mutation to update customer email marketing consent
    private static final String UPDATE_CUSTOMER_EMAIL_CONSENT_MUTATION = """
        mutation customerEmailMarketingConsentUpdate(
            $input: CustomerEmailMarketingConsentUpdateInput!
        ) {
            customerEmailMarketingConsentUpdate(input: $input) {
                customer {
                    id
                    emailMarketingConsent {
                        consentUpdatedAt
                        marketingOptInLevel
                        marketingState
                    }
                }
                userErrors {
                    field
                    message
                }
            }
        }
        """;
    
    // Mutation to update customer SMS marketing consent
    private static final String UPDATE_CUSTOMER_SMS_CONSENT_MUTATION = """
        mutation customerSmsMarketingConsentUpdate(
            $input: CustomerSmsMarketingConsentUpdateInput!
        ) {
            customerSmsMarketingConsentUpdate(input: $input) {
                customer {
                    id
                    smsMarketingConsent {
                        consentCollectedFrom
                        consentUpdatedAt
                        marketingOptInLevel
                        marketingState
                    }
                }
                userErrors {
                    field
                    message
                }
            }
        }
        """;
    
    // Service methods
    public CustomerPrivacy getCustomerPrivacy(ShopifyAuthContext context) {
        ShopifyGraphQLClient.setAuthContext(context);
        
        GraphQLRequest request = GraphQLRequest.builder()
                .query(GET_CUSTOMER_PRIVACY_QUERY)
                .variables(new HashMap<>())
                .build();
        
        GraphQLResponse<CustomerPrivacyResponse> response = graphQLClient.execute(
                request,
                new TypeReference<GraphQLResponse<CustomerPrivacyResponse>>() {}
        );
        
        if (response.hasErrors()) {
            throw new ShopifyApiException("Failed to get customer privacy", response.getErrors());
        }
        
        return response.getData().customerPrivacy;
    }
    
    public List<CustomerDataRequest> getCustomerDataRequests(
            ShopifyAuthContext context,
            int first,
            String after,
            String query) {
        ShopifyGraphQLClient.setAuthContext(context);
        
        Map<String, Object> variables = new HashMap<>();
        variables.put("first", first);
        if (after != null) {
            variables.put("after", after);
        }
        if (query != null) {
            variables.put("query", query);
        }
        
        GraphQLRequest request = GraphQLRequest.builder()
                .query(GET_CUSTOMER_DATA_REQUESTS_QUERY)
                .variables(variables)
                .build();
        
        GraphQLResponse<CustomerDataRequestsResponse> response = graphQLClient.execute(
                request,
                new TypeReference<GraphQLResponse<CustomerDataRequestsResponse>>() {}
        );
        
        if (response.hasErrors()) {
            throw new ShopifyApiException("Failed to get customer data requests", response.getErrors());
        }
        
        List<CustomerDataRequest> requests = new ArrayList<>();
        response.getData().shop.customerDataRequests.edges.forEach(edge -> 
            requests.add(edge.node)
        );
        
        return requests;
    }
    
    public List<CustomerRequest> getCustomerRequests(
            ShopifyAuthContext context,
            int first,
            String after,
            CustomerRequestStatus status,
            CustomerRequestType requestType) {
        ShopifyGraphQLClient.setAuthContext(context);
        
        Map<String, Object> variables = new HashMap<>();
        variables.put("first", first);
        if (after != null) {
            variables.put("after", after);
        }
        if (status != null) {
            variables.put("status", status);
        }
        if (requestType != null) {
            variables.put("requestType", requestType);
        }
        
        GraphQLRequest request = GraphQLRequest.builder()
                .query(GET_CUSTOMER_REQUESTS_QUERY)
                .variables(variables)
                .build();
        
        GraphQLResponse<CustomerRequestsResponse> response = graphQLClient.execute(
                request,
                new TypeReference<GraphQLResponse<CustomerRequestsResponse>>() {}
        );
        
        if (response.hasErrors()) {
            throw new ShopifyApiException("Failed to get customer requests", response.getErrors());
        }
        
        List<CustomerRequest> requests = new ArrayList<>();
        response.getData().customerRequests.edges.forEach(edge -> 
            requests.add(edge.node)
        );
        
        return requests;
    }
    
    public List<ConsentRecord> getConsentRecords(
            ShopifyAuthContext context,
            int first,
            String after,
            String query) {
        ShopifyGraphQLClient.setAuthContext(context);
        
        Map<String, Object> variables = new HashMap<>();
        variables.put("first", first);
        if (after != null) {
            variables.put("after", after);
        }
        if (query != null) {
            variables.put("query", query);
        }
        
        GraphQLRequest request = GraphQLRequest.builder()
                .query(GET_CONSENT_RECORDS_QUERY)
                .variables(variables)
                .build();
        
        GraphQLResponse<ConsentRecordsResponse> response = graphQLClient.execute(
                request,
                new TypeReference<GraphQLResponse<ConsentRecordsResponse>>() {}
        );
        
        if (response.hasErrors()) {
            throw new ShopifyApiException("Failed to get consent records", response.getErrors());
        }
        
        List<ConsentRecord> records = new ArrayList<>();
        response.getData().consentRecords.edges.forEach(edge -> 
            records.add(edge.node)
        );
        
        return records;
    }
    
    public List<GDPRRequest> getGDPRRequests(
            ShopifyAuthContext context,
            int first,
            String after,
            GDPRRequestState state,
            GDPRRequestType requestType) {
        ShopifyGraphQLClient.setAuthContext(context);
        
        Map<String, Object> variables = new HashMap<>();
        variables.put("first", first);
        if (after != null) {
            variables.put("after", after);
        }
        if (state != null) {
            variables.put("state", state);
        }
        if (requestType != null) {
            variables.put("requestType", requestType);
        }
        
        GraphQLRequest request = GraphQLRequest.builder()
                .query(GET_GDPR_REQUESTS_QUERY)
                .variables(variables)
                .build();
        
        GraphQLResponse<GDPRRequestsResponse> response = graphQLClient.execute(
                request,
                new TypeReference<GraphQLResponse<GDPRRequestsResponse>>() {}
        );
        
        if (response.hasErrors()) {
            throw new ShopifyApiException("Failed to get GDPR requests", response.getErrors());
        }
        
        List<GDPRRequest> requests = new ArrayList<>();
        response.getData().gdprRequests.edges.forEach(edge -> 
            requests.add(edge.node)
        );
        
        return requests;
    }
    
    public PrivacyPolicy getPrivacyPolicy(ShopifyAuthContext context) {
        ShopifyGraphQLClient.setAuthContext(context);
        
        GraphQLRequest request = GraphQLRequest.builder()
                .query(GET_PRIVACY_POLICY_QUERY)
                .variables(new HashMap<>())
                .build();
        
        GraphQLResponse<PrivacyPolicyResponse> response = graphQLClient.execute(
                request,
                new TypeReference<GraphQLResponse<PrivacyPolicyResponse>>() {}
        );
        
        if (response.hasErrors()) {
            throw new ShopifyApiException("Failed to get privacy policy", response.getErrors());
        }
        
        return response.getData().shop.privacyPolicy;
    }
    
    public DataProtectionSetting getDataProtectionSettings(ShopifyAuthContext context) {
        ShopifyGraphQLClient.setAuthContext(context);
        
        GraphQLRequest request = GraphQLRequest.builder()
                .query(GET_DATA_PROTECTION_SETTINGS_QUERY)
                .variables(new HashMap<>())
                .build();
        
        GraphQLResponse<DataProtectionSettingsResponse> response = graphQLClient.execute(
                request,
                new TypeReference<GraphQLResponse<DataProtectionSettingsResponse>>() {}
        );
        
        if (response.hasErrors()) {
            throw new ShopifyApiException("Failed to get data protection settings", response.getErrors());
        }
        
        return response.getData().shop.dataProtectionSetting;
    }
    
    public CustomerRequest createCustomerRequest(
            ShopifyAuthContext context,
            CustomerRequestInput input) {
        ShopifyGraphQLClient.setAuthContext(context);
        
        Map<String, Object> variables = new HashMap<>();
        variables.put("input", input);
        
        GraphQLRequest request = GraphQLRequest.builder()
                .query(CREATE_CUSTOMER_REQUEST_MUTATION)
                .variables(variables)
                .build();
        
        GraphQLResponse<CustomerRequestCreateResponse> response = graphQLClient.execute(
                request,
                new TypeReference<GraphQLResponse<CustomerRequestCreateResponse>>() {}
        );
        
        if (response.hasErrors()) {
            throw new ShopifyApiException("Failed to create customer request", response.getErrors());
        }
        
        if (response.getData().customerRequestCreate.userErrors != null && 
            !response.getData().customerRequestCreate.userErrors.isEmpty()) {
            throw new ShopifyApiException(
                    "Failed to create customer request",
                    response.getData().customerRequestCreate.userErrors
            );
        }
        
        return response.getData().customerRequestCreate.customerRequest;
    }
    
    public CustomerRequest updateCustomerRequest(
            ShopifyAuthContext context,
            String id,
            CustomerRequestStatus status) {
        ShopifyGraphQLClient.setAuthContext(context);
        
        Map<String, Object> variables = new HashMap<>();
        variables.put("id", id);
        variables.put("status", status);
        
        GraphQLRequest request = GraphQLRequest.builder()
                .query(UPDATE_CUSTOMER_REQUEST_MUTATION)
                .variables(variables)
                .build();
        
        GraphQLResponse<CustomerRequestUpdateResponse> response = graphQLClient.execute(
                request,
                new TypeReference<GraphQLResponse<CustomerRequestUpdateResponse>>() {}
        );
        
        if (response.hasErrors()) {
            throw new ShopifyApiException("Failed to update customer request", response.getErrors());
        }
        
        if (response.getData().customerRequestUpdate.userErrors != null && 
            !response.getData().customerRequestUpdate.userErrors.isEmpty()) {
            throw new ShopifyApiException(
                    "Failed to update customer request",
                    response.getData().customerRequestUpdate.userErrors
            );
        }
        
        return response.getData().customerRequestUpdate.customerRequest;
    }
    
    public ConsentRecord createConsentRecord(
            ShopifyAuthContext context,
            ConsentRecordInput input) {
        ShopifyGraphQLClient.setAuthContext(context);
        
        Map<String, Object> variables = new HashMap<>();
        variables.put("input", input);
        
        GraphQLRequest request = GraphQLRequest.builder()
                .query(CREATE_CONSENT_RECORD_MUTATION)
                .variables(variables)
                .build();
        
        GraphQLResponse<ConsentRecordCreateResponse> response = graphQLClient.execute(
                request,
                new TypeReference<GraphQLResponse<ConsentRecordCreateResponse>>() {}
        );
        
        if (response.hasErrors()) {
            throw new ShopifyApiException("Failed to create consent record", response.getErrors());
        }
        
        if (response.getData().consentRecordCreate.userErrors != null && 
            !response.getData().consentRecordCreate.userErrors.isEmpty()) {
            throw new ShopifyApiException(
                    "Failed to create consent record",
                    response.getData().consentRecordCreate.userErrors
            );
        }
        
        return response.getData().consentRecordCreate.consentRecord;
    }
    
    public DataProtectionSetting updateDataProtectionSettings(
            ShopifyAuthContext context,
            DataProtectionSettingInput input) {
        ShopifyGraphQLClient.setAuthContext(context);
        
        Map<String, Object> variables = new HashMap<>();
        variables.put("input", input);
        
        GraphQLRequest request = GraphQLRequest.builder()
                .query(UPDATE_DATA_PROTECTION_SETTINGS_MUTATION)
                .variables(variables)
                .build();
        
        GraphQLResponse<DataProtectionSettingsUpdateResponse> response = graphQLClient.execute(
                request,
                new TypeReference<GraphQLResponse<DataProtectionSettingsUpdateResponse>>() {}
        );
        
        if (response.hasErrors()) {
            throw new ShopifyApiException("Failed to update data protection settings", response.getErrors());
        }
        
        if (response.getData().dataProtectionSettingsUpdate.userErrors != null && 
            !response.getData().dataProtectionSettingsUpdate.userErrors.isEmpty()) {
            throw new ShopifyApiException(
                    "Failed to update data protection settings",
                    response.getData().dataProtectionSettingsUpdate.userErrors
            );
        }
        
        return response.getData().dataProtectionSettingsUpdate.dataProtectionSetting;
    }
    
    public CustomerDataRequest requestCustomerData(
            ShopifyAuthContext context,
            String customerId,
            DataAccessType dataAccessType) {
        ShopifyGraphQLClient.setAuthContext(context);
        
        Map<String, Object> variables = new HashMap<>();
        variables.put("customerId", customerId);
        variables.put("dataAccessType", dataAccessType);
        
        GraphQLRequest request = GraphQLRequest.builder()
                .query(REQUEST_CUSTOMER_DATA_MUTATION)
                .variables(variables)
                .build();
        
        GraphQLResponse<CustomerDataRequestCreateResponse> response = graphQLClient.execute(
                request,
                new TypeReference<GraphQLResponse<CustomerDataRequestCreateResponse>>() {}
        );
        
        if (response.hasErrors()) {
            throw new ShopifyApiException("Failed to request customer data", response.getErrors());
        }
        
        if (response.getData().customerDataRequestCreate.userErrors != null && 
            !response.getData().customerDataRequestCreate.userErrors.isEmpty()) {
            throw new ShopifyApiException(
                    "Failed to request customer data",
                    response.getData().customerDataRequestCreate.userErrors
            );
        }
        
        return response.getData().customerDataRequestCreate.customerDataRequest;
    }
    
    public Customer updateCustomerEmailConsent(
            ShopifyAuthContext context,
            String customerId,
            CustomerEmailMarketingConsentInput emailConsent) {
        ShopifyGraphQLClient.setAuthContext(context);
        
        Map<String, Object> input = new HashMap<>();
        input.put("customerId", customerId);
        input.put("emailMarketingConsent", emailConsent);
        
        Map<String, Object> variables = new HashMap<>();
        variables.put("input", input);
        
        GraphQLRequest request = GraphQLRequest.builder()
                .query(UPDATE_CUSTOMER_EMAIL_CONSENT_MUTATION)
                .variables(variables)
                .build();
        
        GraphQLResponse<CustomerEmailConsentUpdateResponse> response = graphQLClient.execute(
                request,
                new TypeReference<GraphQLResponse<CustomerEmailConsentUpdateResponse>>() {}
        );
        
        if (response.hasErrors()) {
            throw new ShopifyApiException("Failed to update customer email consent", response.getErrors());
        }
        
        if (response.getData().customerEmailMarketingConsentUpdate.userErrors != null && 
            !response.getData().customerEmailMarketingConsentUpdate.userErrors.isEmpty()) {
            throw new ShopifyApiException(
                    "Failed to update customer email consent",
                    response.getData().customerEmailMarketingConsentUpdate.userErrors
            );
        }
        
        return response.getData().customerEmailMarketingConsentUpdate.customer;
    }
    
    public Customer updateCustomerSmsConsent(
            ShopifyAuthContext context,
            String customerId,
            CustomerSmsMarketingConsentInput smsConsent) {
        ShopifyGraphQLClient.setAuthContext(context);
        
        Map<String, Object> input = new HashMap<>();
        input.put("customerId", customerId);
        input.put("smsMarketingConsent", smsConsent);
        
        Map<String, Object> variables = new HashMap<>();
        variables.put("input", input);
        
        GraphQLRequest request = GraphQLRequest.builder()
                .query(UPDATE_CUSTOMER_SMS_CONSENT_MUTATION)
                .variables(variables)
                .build();
        
        GraphQLResponse<CustomerSmsConsentUpdateResponse> response = graphQLClient.execute(
                request,
                new TypeReference<GraphQLResponse<CustomerSmsConsentUpdateResponse>>() {}
        );
        
        if (response.hasErrors()) {
            throw new ShopifyApiException("Failed to update customer SMS consent", response.getErrors());
        }
        
        if (response.getData().customerSmsMarketingConsentUpdate.userErrors != null && 
            !response.getData().customerSmsMarketingConsentUpdate.userErrors.isEmpty()) {
            throw new ShopifyApiException(
                    "Failed to update customer SMS consent",
                    response.getData().customerSmsMarketingConsentUpdate.userErrors
            );
        }
        
        return response.getData().customerSmsMarketingConsentUpdate.customer;
    }
    
    // Response classes
    @Data
    private static class CustomerPrivacyResponse {
        private CustomerPrivacy customerPrivacy;
    }
    
    @Data
    private static class CustomerDataRequestsResponse {
        private Shop shop;
    }
    
    @Data
    private static class Shop {
        private CustomerDataRequestConnection customerDataRequests;
        private PrivacyPolicy privacyPolicy;
        private DataProtectionSetting dataProtectionSetting;
    }
    
    @Data
    private static class CustomerRequestsResponse {
        private CustomerRequestConnection customerRequests;
    }
    
    @Data
    private static class ConsentRecordsResponse {
        private ConsentRecordConnection consentRecords;
    }
    
    @Data
    private static class GDPRRequestsResponse {
        private GDPRRequestConnection gdprRequests;
    }
    
    @Data
    private static class PrivacyPolicyResponse {
        private Shop shop;
    }
    
    @Data
    private static class DataProtectionSettingsResponse {
        private Shop shop;
    }
    
    @Data
    private static class CustomerRequestCreateResponse {
        private CustomerRequestCreateResult customerRequestCreate;
    }
    
    @Data
    private static class CustomerRequestCreateResult {
        private CustomerRequest customerRequest;
        private List<UserError> userErrors;
    }
    
    @Data
    private static class CustomerRequestUpdateResponse {
        private CustomerRequestUpdateResult customerRequestUpdate;
    }
    
    @Data
    private static class CustomerRequestUpdateResult {
        private CustomerRequest customerRequest;
        private List<UserError> userErrors;
    }
    
    @Data
    private static class ConsentRecordCreateResponse {
        private ConsentRecordCreateResult consentRecordCreate;
    }
    
    @Data
    private static class ConsentRecordCreateResult {
        private ConsentRecord consentRecord;
        private List<UserError> userErrors;
    }
    
    @Data
    private static class DataProtectionSettingsUpdateResponse {
        private DataProtectionSettingsUpdateResult dataProtectionSettingsUpdate;
    }
    
    @Data
    private static class DataProtectionSettingsUpdateResult {
        private DataProtectionSetting dataProtectionSetting;
        private List<UserError> userErrors;
    }
    
    @Data
    private static class CustomerDataRequestCreateResponse {
        private CustomerDataRequestCreateResult customerDataRequestCreate;
    }
    
    @Data
    private static class CustomerDataRequestCreateResult {
        private CustomerDataRequest customerDataRequest;
        private List<UserError> userErrors;
    }
    
    @Data
    private static class CustomerEmailConsentUpdateResponse {
        private CustomerEmailConsentUpdateResult customerEmailMarketingConsentUpdate;
    }
    
    @Data
    private static class CustomerEmailConsentUpdateResult {
        private Customer customer;
        private List<UserError> userErrors;
    }
    
    @Data
    private static class CustomerSmsConsentUpdateResponse {
        private CustomerSmsConsentUpdateResult customerSmsMarketingConsentUpdate;
    }
    
    @Data
    private static class CustomerSmsConsentUpdateResult {
        private Customer customer;
        private List<UserError> userErrors;
    }
}