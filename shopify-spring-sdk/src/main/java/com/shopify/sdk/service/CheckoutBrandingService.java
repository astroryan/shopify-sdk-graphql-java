package com.shopify.sdk.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.shopify.sdk.model.graphql.GraphQLRequest;
import com.shopify.sdk.model.graphql.GraphQLResponse;
import com.shopify.sdk.client.ShopifyGraphQLClient;
import com.shopify.sdk.auth.ShopifyAuthContext;
import com.shopify.sdk.exception.ShopifyApiException;
import com.shopify.sdk.model.checkout.CheckoutBrandingInput;
import com.shopify.sdk.model.checkout.CheckoutBrandingSettings;
import com.shopify.sdk.model.common.UserError;
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
public class CheckoutBrandingService {
    
    private final ShopifyGraphQLClient graphQLClient;
    
    // Query for checkout branding settings
    private static final String GET_CHECKOUT_BRANDING_QUERY = """
        query checkoutBranding($checkoutProfileId: ID!) {
            checkoutProfile(id: $checkoutProfileId) {
                id
                name
                checkoutBrandingSettings {
                    customizations {
                        buyerJourney {
                            visibility
                        }
                        cartLink {
                            visibility
                        }
                        checkbox {
                            cornerRadius {
                                borderRadius
                            }
                        }
                        choiceList {
                            group {
                                spacing
                            }
                        }
                        control {
                            border {
                                style
                                width
                            }
                            color
                            cornerRadius {
                                borderRadius
                            }
                            labelPosition
                        }
                        favicon {
                            mediaContentType
                            originalSrc
                        }
                        global {
                            cornerRadius {
                                borderRadius
                            }
                            typography {
                                kerning
                                letterCase
                            }
                        }
                        header {
                            alignment
                            banner {
                                mediaContentType
                                originalSrc
                            }
                            logo {
                                image {
                                    mediaContentType
                                    originalSrc
                                }
                                maxWidth
                            }
                            position
                        }
                        headingLevel1 {
                            typography {
                                font
                                kerning
                                letterCase
                                size
                                weight
                            }
                        }
                        headingLevel2 {
                            typography {
                                font
                                kerning
                                letterCase
                                size
                                weight
                            }
                        }
                        main {
                            backgroundImage {
                                image {
                                    mediaContentType
                                    originalSrc
                                }
                            }
                            colorScheme
                        }
                        merchandiseThumbnail {
                            border {
                                style
                                width
                            }
                            cornerRadius {
                                borderRadius
                            }
                        }
                        orderSummary {
                            backgroundImage {
                                image {
                                    mediaContentType
                                    originalSrc
                                }
                            }
                            colorScheme
                        }
                        primaryButton {
                            background {
                                style
                            }
                            blockPadding
                            border {
                                style
                                width
                            }
                            cornerRadius {
                                borderRadius
                            }
                            inlinePadding
                            typography {
                                font
                                kerning
                                letterCase
                                size
                                weight
                            }
                        }
                        secondaryButton {
                            background {
                                style
                            }
                            blockPadding
                            border {
                                style
                                width
                            }
                            cornerRadius {
                                borderRadius
                            }
                            inlinePadding
                            typography {
                                font
                                kerning
                                letterCase
                                size
                                weight
                            }
                        }
                        select {
                            border {
                                style
                                width
                            }
                            typography {
                                font
                                kerning
                                letterCase
                                size
                            }
                        }
                        textField {
                            border {
                                style
                                width
                            }
                            typography {
                                font
                                kerning
                                letterCase
                                size
                            }
                        }
                    }
                    designSystem {
                        colors {
                            global {
                                accent
                                brand
                                critical
                                decorative
                                info
                                success
                                warning
                            }
                            schemes {
                                scheme1 {
                                    base {
                                        accent
                                        background
                                        border
                                        decorative
                                        icon
                                        selected
                                        text
                                    }
                                    control {
                                        accent
                                        background
                                        border
                                        selected
                                        text
                                    }
                                    primaryButton {
                                        accent
                                        background
                                        border
                                        hover
                                        text
                                    }
                                    secondaryButton {
                                        accent
                                        background
                                        border
                                        hover
                                        text
                                    }
                                }
                                scheme2 {
                                    base {
                                        accent
                                        background
                                        border
                                        decorative
                                        icon
                                        selected
                                        text
                                    }
                                    control {
                                        accent
                                        background
                                        border
                                        selected
                                        text
                                    }
                                    primaryButton {
                                        accent
                                        background
                                        border
                                        hover
                                        text
                                    }
                                    secondaryButton {
                                        accent
                                        background
                                        border
                                        hover
                                        text
                                    }
                                }
                            }
                        }
                        cornerRadius {
                            base {
                                borderRadius
                            }
                            large {
                                borderRadius
                            }
                            small {
                                borderRadius
                            }
                        }
                        typography {
                            primary {
                                base {
                                    sources
                                    weight
                                }
                                bold {
                                    sources
                                    weight
                                }
                                loadingStrategy
                                name
                            }
                            secondary {
                                base {
                                    sources
                                    weight
                                }
                                bold {
                                    sources
                                    weight
                                }
                                loadingStrategy
                                name
                            }
                            size {
                                base
                                ratio
                            }
                        }
                    }
                }
            }
        }
        """;
    
    // Query for checkout profiles
    private static final String GET_CHECKOUT_PROFILES_QUERY = """
        query checkoutProfiles($first: Int!, $after: String) {
            checkoutProfiles(first: $first, after: $after) {
                edges {
                    node {
                        id
                        name
                        isPublished
                        createdAt
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
        """;
    
    // Mutation to update checkout branding
    private static final String UPDATE_CHECKOUT_BRANDING_MUTATION = """
        mutation checkoutBrandingUpsert($checkoutProfileId: ID!, $checkoutBrandingInput: CheckoutBrandingInput!) {
            checkoutBrandingUpsert(
                checkoutProfileId: $checkoutProfileId,
                checkoutBrandingInput: $checkoutBrandingInput
            ) {
                checkoutBranding {
                    customizations {
                        global {
                            cornerRadius {
                                borderRadius
                            }
                            typography {
                                kerning
                                letterCase
                            }
                        }
                        header {
                            alignment
                            position
                        }
                        main {
                            colorScheme
                        }
                        orderSummary {
                            colorScheme
                        }
                    }
                    designSystem {
                        colors {
                            global {
                                accent
                                brand
                                critical
                                decorative
                                info
                                success
                                warning
                            }
                        }
                        cornerRadius {
                            base {
                                borderRadius
                            }
                            large {
                                borderRadius
                            }
                            small {
                                borderRadius
                            }
                        }
                        typography {
                            primary {
                                name
                            }
                            secondary {
                                name
                            }
                            size {
                                base
                                ratio
                            }
                        }
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
    
    // Service methods
    public CheckoutBrandingSettings getCheckoutBranding(ShopifyAuthContext context, String checkoutProfileId) {
        ShopifyGraphQLClient.setAuthContext(context);
        
        Map<String, Object> variables = new HashMap<>();
        variables.put("checkoutProfileId", checkoutProfileId);
        
        GraphQLRequest request = GraphQLRequest.builder()
                .query(GET_CHECKOUT_BRANDING_QUERY)
                .variables(variables)
                .build();
        
        GraphQLResponse<CheckoutProfileResponse> response = graphQLClient.execute(
                request,
                new TypeReference<GraphQLResponse<CheckoutProfileResponse>>() {}
        );
        
        if (response.hasErrors()) {
            throw new ShopifyApiException("Failed to get checkout branding", response.getErrors());
        }
        
        return response.getData().checkoutProfile.checkoutBrandingSettings;
    }
    
    public List<CheckoutProfile> getCheckoutProfiles(ShopifyAuthContext context, int first, String after) {
        ShopifyGraphQLClient.setAuthContext(context);
        
        Map<String, Object> variables = new HashMap<>();
        variables.put("first", first);
        if (after != null) {
            variables.put("after", after);
        }
        
        GraphQLRequest request = GraphQLRequest.builder()
                .query(GET_CHECKOUT_PROFILES_QUERY)
                .variables(variables)
                .build();
        
        GraphQLResponse<CheckoutProfilesResponse> response = graphQLClient.execute(
                request,
                new TypeReference<GraphQLResponse<CheckoutProfilesResponse>>() {}
        );
        
        if (response.hasErrors()) {
            throw new ShopifyApiException("Failed to get checkout profiles", response.getErrors());
        }
        
        List<CheckoutProfile> profiles = new ArrayList<>();
        response.getData().checkoutProfiles.edges.forEach(edge -> 
            profiles.add(edge.node)
        );
        
        return profiles;
    }
    
    public CheckoutBrandingSettings updateCheckoutBranding(
            ShopifyAuthContext context,
            String checkoutProfileId,
            CheckoutBrandingInput brandingInput) {
        ShopifyGraphQLClient.setAuthContext(context);
        
        Map<String, Object> variables = new HashMap<>();
        variables.put("checkoutProfileId", checkoutProfileId);
        variables.put("checkoutBrandingInput", brandingInput);
        
        GraphQLRequest request = GraphQLRequest.builder()
                .query(UPDATE_CHECKOUT_BRANDING_MUTATION)
                .variables(variables)
                .build();
        
        GraphQLResponse<CheckoutBrandingUpsertResponse> response = graphQLClient.execute(
                request,
                new TypeReference<GraphQLResponse<CheckoutBrandingUpsertResponse>>() {}
        );
        
        if (response.hasErrors()) {
            throw new ShopifyApiException("Failed to update checkout branding", response.getErrors());
        }
        
        if (response.getData().checkoutBrandingUpsert.userErrors != null && 
            !response.getData().checkoutBrandingUpsert.userErrors.isEmpty()) {
            throw new ShopifyApiException(
                    "Failed to update checkout branding",
                    response.getData().checkoutBrandingUpsert.userErrors
            );
        }
        
        return response.getData().checkoutBrandingUpsert.checkoutBranding;
    }
    
    // Response classes
    @Data
    private static class CheckoutProfileResponse {
        private CheckoutProfileWithBranding checkoutProfile;
    }
    
    @Data
    private static class CheckoutProfileWithBranding {
        private String id;
        private String name;
        private CheckoutBrandingSettings checkoutBrandingSettings;
    }
    
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class CheckoutProfile {
        private String id;
        private String name;
        private Boolean isPublished;
        private String createdAt;
        private String updatedAt;
    }
    
    @Data
    private static class CheckoutProfilesResponse {
        private CheckoutProfileConnection checkoutProfiles;
    }
    
    @Data
    private static class CheckoutProfileConnection {
        private List<CheckoutProfileEdge> edges;
        private PageInfo pageInfo;
    }
    
    @Data
    private static class CheckoutProfileEdge {
        private CheckoutProfile node;
        private String cursor;
    }
    
    @Data
    private static class PageInfo {
        private Boolean hasNextPage;
        private String endCursor;
    }
    
    @Data
    private static class CheckoutBrandingUpsertResponse {
        private CheckoutBrandingUpsertResult checkoutBrandingUpsert;
    }
    
    @Data
    private static class CheckoutBrandingUpsertResult {
        private CheckoutBrandingSettings checkoutBranding;
        private List<UserError> userErrors;
    }
}