package com.shopify.sdk.service.b2b;

import com.shopify.sdk.auth.ShopifyAuthContext;
import com.shopify.sdk.client.GraphQLClient;
import com.shopify.sdk.model.b2b.Company;
import com.shopify.sdk.model.b2b.CompanyContact;
import com.shopify.sdk.model.b2b.CompanyLocation;
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
public class CompanyService {
    
    private final GraphQLClient graphQLClient;
    
    private static final String GET_COMPANY_QUERY = """
        query getCompany($id: ID!) {
            company(id: $id) {
                id
                name
                externalId
                note
                since
                createdAt
                updatedAt
                customerSince
                lifetimeDuration
                totalSpent {
                    amount
                    currencyCode
                }
                mainContact {
                    id
                    customer {
                        id
                        firstName
                        lastName
                        email
                    }
                    title
                    isMainContact
                }
                contactCount
                orderCount
                hasTimelineComment
            }
        }
        """;
    
    private static final String LIST_COMPANIES_QUERY = """
        query listCompanies($first: Int!, $after: String, $query: String) {
            companies(first: $first, after: $after, query: $query) {
                edges {
                    node {
                        id
                        name
                        externalId
                        note
                        since
                        createdAt
                        updatedAt
                        customerSince
                        lifetimeDuration
                        contactCount
                        orderCount
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
    
    private static final String CREATE_COMPANY_MUTATION = """
        mutation companyCreate($company: CompanyCreateInput!) {
            companyCreate(company: $company) {
                company {
                    id
                    name
                    externalId
                    note
                }
                userErrors {
                    field
                    message
                    code
                }
            }
        }
        """;
    
    private static final String UPDATE_COMPANY_MUTATION = """
        mutation companyUpdate($companyId: ID!, $company: CompanyUpdateInput!) {
            companyUpdate(companyId: $companyId, company: $company) {
                company {
                    id
                    name
                    externalId
                    note
                    updatedAt
                }
                userErrors {
                    field
                    message
                    code
                }
            }
        }
        """;
    
    private static final String DELETE_COMPANY_MUTATION = """
        mutation companyDelete($id: ID!) {
            companyDelete(id: $id) {
                deletedCompanyId
                userErrors {
                    field
                    message
                    code
                }
            }
        }
        """;
    
    private static final String ASSIGN_COMPANY_CONTACT_MUTATION = """
        mutation companyContactAssign($companyContactId: ID!, $companyId: ID!) {
            companyContactAssign(companyContactId: $companyContactId, companyId: $companyId) {
                companyContact {
                    id
                    company {
                        id
                        name
                    }
                    customer {
                        id
                        firstName
                        lastName
                        email
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
    
    public Company getCompany(ShopifyAuthContext context, String companyId) {
        Map<String, Object> variables = new HashMap<>();
        variables.put("id", companyId);
        
        GraphQLRequest request = GraphQLRequest.builder()
                .query(GET_COMPANY_QUERY)
                .variables(variables)
                .build();
        
        GraphQLResponse<CompanyData> response = graphQLClient.execute(
                request,
                CompanyData.class
        );
        
        if (response.hasErrors()) {
            log.error("Failed to get company: {}", response.getErrors());
            throw new RuntimeException("Failed to get company");
        }
        
        return response.getData().getCompany();
    }
    
    public Company createCompany(ShopifyAuthContext context, CompanyCreateInput input) {
        Map<String, Object> variables = new HashMap<>();
        variables.put("company", input);
        
        GraphQLRequest request = GraphQLRequest.builder()
                .query(CREATE_COMPANY_MUTATION)
                .variables(variables)
                .build();
        
        GraphQLResponse<CompanyCreateData> response = graphQLClient.execute(
                request,
                CompanyCreateData.class
        );
        
        if (response.hasErrors()) {
            log.error("Failed to create company: {}", response.getErrors());
            throw new RuntimeException("Failed to create company");
        }
        
        CompanyCreateResponse createResponse = response.getData().getCompanyCreate();
        if (createResponse.getUserErrors() != null && !createResponse.getUserErrors().isEmpty()) {
            log.error("User errors creating company: {}", createResponse.getUserErrors());
            throw new RuntimeException("Failed to create company: " + createResponse.getUserErrors());
        }
        
        return createResponse.getCompany();
    }
    
    public Company updateCompany(ShopifyAuthContext context, String companyId, CompanyUpdateInput input) {
        Map<String, Object> variables = new HashMap<>();
        variables.put("companyId", companyId);
        variables.put("company", input);
        
        GraphQLRequest request = GraphQLRequest.builder()
                .query(UPDATE_COMPANY_MUTATION)
                .variables(variables)
                .build();
        
        GraphQLResponse<CompanyUpdateData> response = graphQLClient.execute(
                request,
                CompanyUpdateData.class
        );
        
        if (response.hasErrors()) {
            log.error("Failed to update company: {}", response.getErrors());
            throw new RuntimeException("Failed to update company");
        }
        
        CompanyUpdateResponse updateResponse = response.getData().getCompanyUpdate();
        if (updateResponse.getUserErrors() != null && !updateResponse.getUserErrors().isEmpty()) {
            log.error("User errors updating company: {}", updateResponse.getUserErrors());
            throw new RuntimeException("Failed to update company: " + updateResponse.getUserErrors());
        }
        
        return updateResponse.getCompany();
    }
    
    public String deleteCompany(ShopifyAuthContext context, String companyId) {
        Map<String, Object> variables = new HashMap<>();
        variables.put("id", companyId);
        
        GraphQLRequest request = GraphQLRequest.builder()
                .query(DELETE_COMPANY_MUTATION)
                .variables(variables)
                .build();
        
        GraphQLResponse<CompanyDeleteData> response = graphQLClient.execute(
                request,
                CompanyDeleteData.class
        );
        
        if (response.hasErrors()) {
            log.error("Failed to delete company: {}", response.getErrors());
            throw new RuntimeException("Failed to delete company");
        }
        
        CompanyDeleteResponse deleteResponse = response.getData().getCompanyDelete();
        if (deleteResponse.getUserErrors() != null && !deleteResponse.getUserErrors().isEmpty()) {
            log.error("User errors deleting company: {}", deleteResponse.getUserErrors());
            throw new RuntimeException("Failed to delete company: " + deleteResponse.getUserErrors());
        }
        
        return deleteResponse.getDeletedCompanyId();
    }
    
    @Data
    private static class CompanyData {
        private Company company;
    }
    
    @Data
    private static class CompanyCreateData {
        private CompanyCreateResponse companyCreate;
    }
    
    @Data
    private static class CompanyUpdateData {
        private CompanyUpdateResponse companyUpdate;
    }
    
    @Data
    private static class CompanyDeleteData {
        private CompanyDeleteResponse companyDelete;
    }
    
    @Data
    public static class CompanyCreateResponse {
        private Company company;
        private List<UserError> userErrors;
    }
    
    @Data
    public static class CompanyUpdateResponse {
        private Company company;
        private List<UserError> userErrors;
    }
    
    @Data
    public static class CompanyDeleteResponse {
        private String deletedCompanyId;
        private List<UserError> userErrors;
    }
    
    @Data
    public static class UserError {
        private List<String> field;
        private String message;
        private String code;
    }
    
    @Data
    public static class CompanyCreateInput {
        private String name;
        private String externalId;
        private String note;
    }
    
    @Data
    public static class CompanyUpdateInput {
        private String name;
        private String externalId;
        private String note;
    }
}