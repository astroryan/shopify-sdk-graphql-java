package com.shopify.sdk.service.customer;

import com.shopify.sdk.auth.ShopifyAuthContext;
import com.shopify.sdk.client.GraphQLClient;
import com.shopify.sdk.model.customer.Customer;
import com.shopify.sdk.model.graphql.GraphQLRequest;
import com.shopify.sdk.model.graphql.GraphQLResponse;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class CustomerService {
    
    private final GraphQLClient graphQLClient;
    
    private static final String GET_CUSTOMER_QUERY = """
        query getCustomer($id: ID!) {
            customer(id: $id) {
                id
                email
                phone
                firstName
                lastName
                displayName
                acceptsMarketing
                acceptsMarketingUpdatedAt
                createdAt
                updatedAt
                note
                verifiedEmail
                validEmailAddress
                tags
                lifetimeDuration
                numberOfOrders
                marketingOptInLevel
                productSubscriberStatus
                state
                taxExempt
                taxExemptions
                defaultAddress {
                    id
                    formatted
                    firstName
                    lastName
                    company
                    address1
                    address2
                    city
                    country
                    countryCodeV2
                    name
                    phone
                    province
                    provinceCode
                    zip
                }
                amountSpent {
                    amount
                    currencyCode
                }
                image {
                    id
                    altText
                    url
                }
            }
        }
        """;
    
    private static final String LIST_CUSTOMERS_QUERY = """
        query listCustomers($first: Int!, $after: String, $query: String) {
            customers(first: $first, after: $after, query: $query) {
                edges {
                    node {
                        id
                        email
                        phone
                        firstName
                        lastName
                        displayName
                        acceptsMarketing
                        createdAt
                        updatedAt
                        verifiedEmail
                        state
                        numberOfOrders
                        amountSpent {
                            amount
                            currencyCode
                        }
                    }
                    cursor
                }
                pageInfo {
                    hasNextPage
                    hasPreviousPage
                    startCursor
                    endCursor
                }
            }
        }
        """;
    
    private static final String CREATE_CUSTOMER_MUTATION = """
        mutation customerCreate($input: CustomerInput!) {
            customerCreate(input: $input) {
                customer {
                    id
                    email
                    phone
                    firstName
                    lastName
                    displayName
                    acceptsMarketing
                    createdAt
                    updatedAt
                    verifiedEmail
                    state
                }
                userErrors {
                    field
                    message
                    code
                }
            }
        }
        """;
    
    private static final String UPDATE_CUSTOMER_MUTATION = """
        mutation customerUpdate($input: CustomerInput!) {
            customerUpdate(input: $input) {
                customer {
                    id
                    email
                    phone
                    firstName
                    lastName
                    displayName
                    acceptsMarketing
                    updatedAt
                    note
                    tags
                }
                userErrors {
                    field
                    message
                    code
                }
            }
        }
        """;
    
    private static final String DELETE_CUSTOMER_MUTATION = """
        mutation customerDelete($input: CustomerDeleteInput!) {
            customerDelete(input: $input) {
                deletedCustomerId
                shop {
                    id
                    name
                }
                userErrors {
                    field
                    message
                    code
                }
            }
        }
        """;
    
    private static final String SEND_INVITE_MUTATION = """
        mutation customerSendAccountInviteEmail($customerId: ID!, $email: CustomerEmailInput) {
            customerSendAccountInviteEmail(customerId: $customerId, email: $email) {
                customer {
                    id
                    email
                    state
                }
                userErrors {
                    field
                    message
                    code
                }
            }
        }
        """;
    
    public Customer getCustomer(ShopifyAuthContext context, String customerId) {
        Map<String, Object> variables = new HashMap<>();
        variables.put("id", customerId);
        
        GraphQLRequest request = GraphQLRequest.builder()
                .query(GET_CUSTOMER_QUERY)
                .variables(variables)
                .build();
        
        GraphQLResponse<CustomerData> response = graphQLClient.execute(
                request,
                CustomerData.class
        );
        
        if (response.hasErrors()) {
            log.error("Failed to get customer: {}", response.getErrors());
            throw new RuntimeException("Failed to get customer");
        }
        
        return response.getData().getCustomer();
    }
    
    public CustomerConnection listCustomers(ShopifyAuthContext context, int first, String after, String query) {
        Map<String, Object> variables = new HashMap<>();
        variables.put("first", first);
        if (after != null) {
            variables.put("after", after);
        }
        if (query != null) {
            variables.put("query", query);
        }
        
        GraphQLRequest request = GraphQLRequest.builder()
                .query(LIST_CUSTOMERS_QUERY)
                .variables(variables)
                .build();
        
        GraphQLResponse<CustomersData> response = graphQLClient.execute(
                request,
                CustomersData.class
        );
        
        if (response.hasErrors()) {
            log.error("Failed to list customers: {}", response.getErrors());
            throw new RuntimeException("Failed to list customers");
        }
        
        return response.getData().getCustomers();
    }
    
    public Customer createCustomer(ShopifyAuthContext context, CustomerInput input) {
        Map<String, Object> variables = new HashMap<>();
        variables.put("input", input);
        
        GraphQLRequest request = GraphQLRequest.builder()
                .query(CREATE_CUSTOMER_MUTATION)
                .variables(variables)
                .build();
        
        GraphQLResponse<CustomerCreateData> response = graphQLClient.execute(
                request,
                CustomerCreateData.class
        );
        
        if (response.hasErrors()) {
            log.error("Failed to create customer: {}", response.getErrors());
            throw new RuntimeException("Failed to create customer");
        }
        
        CustomerCreateResponse createResponse = response.getData().getCustomerCreate();
        if (createResponse.getUserErrors() != null && !createResponse.getUserErrors().isEmpty()) {
            log.error("User errors creating customer: {}", createResponse.getUserErrors());
            throw new RuntimeException("Failed to create customer: " + createResponse.getUserErrors());
        }
        
        return createResponse.getCustomer();
    }
    
    public Customer updateCustomer(ShopifyAuthContext context, CustomerInput input) {
        Map<String, Object> variables = new HashMap<>();
        variables.put("input", input);
        
        GraphQLRequest request = GraphQLRequest.builder()
                .query(UPDATE_CUSTOMER_MUTATION)
                .variables(variables)
                .build();
        
        GraphQLResponse<CustomerUpdateData> response = graphQLClient.execute(
                request,
                CustomerUpdateData.class
        );
        
        if (response.hasErrors()) {
            log.error("Failed to update customer: {}", response.getErrors());
            throw new RuntimeException("Failed to update customer");
        }
        
        CustomerUpdateResponse updateResponse = response.getData().getCustomerUpdate();
        if (updateResponse.getUserErrors() != null && !updateResponse.getUserErrors().isEmpty()) {
            log.error("User errors updating customer: {}", updateResponse.getUserErrors());
            throw new RuntimeException("Failed to update customer: " + updateResponse.getUserErrors());
        }
        
        return updateResponse.getCustomer();
    }
    
    public String deleteCustomer(ShopifyAuthContext context, String customerId) {
        Map<String, Object> variables = new HashMap<>();
        Map<String, Object> input = new HashMap<>();
        input.put("id", customerId);
        variables.put("input", input);
        
        GraphQLRequest request = GraphQLRequest.builder()
                .query(DELETE_CUSTOMER_MUTATION)
                .variables(variables)
                .build();
        
        GraphQLResponse<CustomerDeleteData> response = graphQLClient.execute(
                request,
                CustomerDeleteData.class
        );
        
        if (response.hasErrors()) {
            log.error("Failed to delete customer: {}", response.getErrors());
            throw new RuntimeException("Failed to delete customer");
        }
        
        CustomerDeleteResponse deleteResponse = response.getData().getCustomerDelete();
        if (deleteResponse.getUserErrors() != null && !deleteResponse.getUserErrors().isEmpty()) {
            log.error("User errors deleting customer: {}", deleteResponse.getUserErrors());
            throw new RuntimeException("Failed to delete customer: " + deleteResponse.getUserErrors());
        }
        
        return deleteResponse.getDeletedCustomerId();
    }
    
    public Customer sendAccountInvite(ShopifyAuthContext context, String customerId, CustomerEmailInput emailInput) {
        Map<String, Object> variables = new HashMap<>();
        variables.put("customerId", customerId);
        if (emailInput != null) {
            variables.put("email", emailInput);
        }
        
        GraphQLRequest request = GraphQLRequest.builder()
                .query(SEND_INVITE_MUTATION)
                .variables(variables)
                .build();
        
        GraphQLResponse<CustomerSendInviteData> response = graphQLClient.execute(
                request,
                CustomerSendInviteData.class
        );
        
        if (response.hasErrors()) {
            log.error("Failed to send account invite: {}", response.getErrors());
            throw new RuntimeException("Failed to send account invite");
        }
        
        CustomerSendInviteResponse inviteResponse = response.getData().getCustomerSendAccountInviteEmail();
        if (inviteResponse.getUserErrors() != null && !inviteResponse.getUserErrors().isEmpty()) {
            log.error("User errors sending invite: {}", inviteResponse.getUserErrors());
            throw new RuntimeException("Failed to send invite: " + inviteResponse.getUserErrors());
        }
        
        return inviteResponse.getCustomer();
    }
    
    @Data
    private static class CustomerData {
        private Customer customer;
    }
    
    @Data
    private static class CustomersData {
        private CustomerConnection customers;
    }
    
    @Data
    private static class CustomerCreateData {
        private CustomerCreateResponse customerCreate;
    }
    
    @Data
    private static class CustomerUpdateData {
        private CustomerUpdateResponse customerUpdate;
    }
    
    @Data
    private static class CustomerDeleteData {
        private CustomerDeleteResponse customerDelete;
    }
    
    @Data
    private static class CustomerSendInviteData {
        private CustomerSendInviteResponse customerSendAccountInviteEmail;
    }
    
    @Data
    public static class CustomerCreateResponse {
        private Customer customer;
        private List<UserError> userErrors;
    }
    
    @Data
    public static class CustomerUpdateResponse {
        private Customer customer;
        private List<UserError> userErrors;
    }
    
    @Data
    public static class CustomerDeleteResponse {
        private String deletedCustomerId;
        private Shop shop;
        private List<UserError> userErrors;
    }
    
    @Data
    public static class CustomerSendInviteResponse {
        private Customer customer;
        private List<UserError> userErrors;
    }
    
    @Data
    public static class UserError {
        private List<String> field;
        private String message;
        private String code;
    }
    
    @Data
    public static class CustomerInput {
        private String id;
        private String email;
        private String phone;
        private String firstName;
        private String lastName;
        private Boolean acceptsMarketing;
        private Boolean acceptsMarketingUpdatedAt;
        private List<MailingAddressInput> addresses;
        private List<MetafieldInput> metafields;
        private String note;
        private List<String> tags;
        private Boolean taxExempt;
        private List<String> taxExemptions;
    }
    
    @Data
    public static class CustomerEmailInput {
        private String subject;
        private String customMessage;
    }
    
    @Data
    public static class Shop {
        private String id;
        private String name;
    }
    
    @Data
    public static class MailingAddressInput {
        private String address1;
        private String address2;
        private String city;
        private String company;
        private String country;
        private String firstName;
        private String lastName;
        private String phone;
        private String province;
        private String zip;
    }
    
    @Data
    public static class MetafieldInput {
        private String id;
        private String namespace;
        private String key;
        private String value;
        private String type;
    }
    
    @Data
    public static class CustomerConnection {
        private List<CustomerEdge> edges;
        private PageInfo pageInfo;
    }
    
    @Data
    public static class CustomerEdge {
        private Customer node;
        private String cursor;
    }
    
    @Data
    public static class PageInfo {
        private boolean hasNextPage;
        private boolean hasPreviousPage;
        private String startCursor;
        private String endCursor;
    }
}