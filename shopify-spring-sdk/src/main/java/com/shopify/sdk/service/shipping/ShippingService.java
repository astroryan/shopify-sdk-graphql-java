package com.shopify.sdk.service.shipping;

import com.shopify.sdk.auth.ShopifyAuthContext;
import com.shopify.sdk.client.GraphQLClient;
import com.shopify.sdk.model.shipping.*;
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
public class ShippingService {
    
    private final GraphQLClient graphQLClient;
    
    private static final String GET_DELIVERY_PROFILE_QUERY = """
        query getDeliveryProfile($id: ID!) {
            deliveryProfile(id: $id) {
                id
                name
                default
                activeMethodDefinitionsCount
                locationsWithoutRatesCount
                originLocationCount
                zoneCountryCount
                productVariantsCountV2 {
                    capped
                    count
                }
                profileLocationGroups {
                    id
                    locationGroup {
                        id
                        locations(first: 10) {
                            edges {
                                node {
                                    id
                                    name
                                }
                            }
                        }
                    }
                    locationGroupZones(first: 10) {
                        edges {
                            node {
                                id
                                zone {
                                    id
                                    name
                                    countries {
                                        id
                                        name
                                        code {
                                            countryCode
                                            restOfWorld
                                        }
                                        provinces {
                                            id
                                            code
                                            name
                                        }
                                    }
                                }
                                methodDefinitions(first: 10) {
                                    edges {
                                        node {
                                            id
                                            active
                                            name
                                            description
                                            methodConditions {
                                                id
                                                field
                                                operator
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        """;
    
    private static final String LIST_DELIVERY_PROFILES_QUERY = """
        query listDeliveryProfiles($first: Int!, $after: String) {
            deliveryProfiles(first: $first, after: $after) {
                edges {
                    node {
                        id
                        name
                        default
                        activeMethodDefinitionsCount
                        locationsWithoutRatesCount
                        originLocationCount
                        productVariantsCountV2 {
                            capped
                            count
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
    
    private static final String CREATE_DELIVERY_PROFILE_MUTATION = """
        mutation deliveryProfileCreate($profile: DeliveryProfileInput!) {
            deliveryProfileCreate(profile: $profile) {
                profile {
                    id
                    name
                    default
                }
                userErrors {
                    field
                    message
                    code
                }
            }
        }
        """;
    
    private static final String UPDATE_DELIVERY_PROFILE_MUTATION = """
        mutation deliveryProfileUpdate($id: ID!, $profile: DeliveryProfileInput!, $leaveLegacyModeProfiles: Boolean) {
            deliveryProfileUpdate(id: $id, profile: $profile, leaveLegacyModeProfiles: $leaveLegacyModeProfiles) {
                profile {
                    id
                    name
                    default
                }
                userErrors {
                    field
                    message
                    code
                }
            }
        }
        """;
    
    private static final String LIST_CARRIER_SERVICES_QUERY = """
        query listCarrierServices {
            carrierServices {
                id
                formattedName
                icon {
                    url
                    altText
                }
                name
            }
        }
        """;
    
    private static final String CREATE_DELIVERY_ZONE_MUTATION = """
        mutation deliveryProfileLocationGroupZoneCreate($locationGroupId: ID!, $zone: DeliveryLocationGroupZoneInput!) {
            deliveryProfileLocationGroupZoneCreate(locationGroupId: $locationGroupId, zone: $zone) {
                locationGroupZone {
                    id
                    zone {
                        id
                        name
                        countries {
                            id
                            name
                            code {
                                countryCode
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
    
    private static final String UPDATE_DELIVERY_ZONE_MUTATION = """
        mutation deliveryProfileLocationGroupZoneUpdate($id: ID!, $zone: DeliveryLocationGroupZoneInput!) {
            deliveryProfileLocationGroupZoneUpdate(id: $id, zone: $zone) {
                locationGroupZone {
                    id
                    zone {
                        id
                        name
                        countries {
                            id
                            name
                            code {
                                countryCode
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
    
    private static final String DELETE_DELIVERY_ZONE_MUTATION = """
        mutation deliveryProfileLocationGroupZoneDelete($id: ID!) {
            deliveryProfileLocationGroupZoneDelete(id: $id) {
                deletedId
                userErrors {
                    field
                    message
                    code
                }
            }
        }
        """;
    
    private static final String CREATE_DELIVERY_METHOD_MUTATION = """
        mutation deliveryMethodDefinitionCreate($id: ID!, $methodDefinition: DeliveryMethodDefinitionInput!) {
            deliveryMethodDefinitionCreate(id: $id, methodDefinition: $methodDefinition) {
                methodDefinition {
                    id
                    active
                    name
                    description
                }
                userErrors {
                    field
                    message
                    code
                }
            }
        }
        """;
    
    private static final String UPDATE_DELIVERY_METHOD_MUTATION = """
        mutation deliveryMethodDefinitionUpdate($id: ID!, $methodDefinition: DeliveryMethodDefinitionInput!) {
            deliveryMethodDefinitionUpdate(id: $id, methodDefinition: $methodDefinition) {
                methodDefinition {
                    id
                    active
                    name
                    description
                }
                userErrors {
                    field
                    message
                    code
                }
            }
        }
        """;
    
    public DeliveryProfile getDeliveryProfile(ShopifyAuthContext context, String profileId) {
        Map<String, Object> variables = new HashMap<>();
        variables.put("id", profileId);
        
        GraphQLRequest request = GraphQLRequest.builder()
                .query(GET_DELIVERY_PROFILE_QUERY)
                .variables(variables)
                .build();
        
        GraphQLResponse<DeliveryProfileData> response = graphQLClient.execute(
                request,
                DeliveryProfileData.class
        );
        
        if (response.hasErrors()) {
            log.error("Failed to get delivery profile: {}", response.getErrors());
            throw new RuntimeException("Failed to get delivery profile");
        }
        
        return response.getData().getDeliveryProfile();
    }
    
    public DeliveryProfileConnection listDeliveryProfiles(ShopifyAuthContext context, int first, String after) {
        Map<String, Object> variables = new HashMap<>();
        variables.put("first", first);
        if (after != null) variables.put("after", after);
        
        GraphQLRequest request = GraphQLRequest.builder()
                .query(LIST_DELIVERY_PROFILES_QUERY)
                .variables(variables)
                .build();
        
        GraphQLResponse<DeliveryProfilesData> response = graphQLClient.execute(
                request,
                DeliveryProfilesData.class
        );
        
        if (response.hasErrors()) {
            log.error("Failed to list delivery profiles: {}", response.getErrors());
            throw new RuntimeException("Failed to list delivery profiles");
        }
        
        return response.getData().getDeliveryProfiles();
    }
    
    public DeliveryProfile createDeliveryProfile(ShopifyAuthContext context, DeliveryProfileInput input) {
        Map<String, Object> variables = new HashMap<>();
        variables.put("profile", input);
        
        GraphQLRequest request = GraphQLRequest.builder()
                .query(CREATE_DELIVERY_PROFILE_MUTATION)
                .variables(variables)
                .build();
        
        GraphQLResponse<DeliveryProfileCreateData> response = graphQLClient.execute(
                request,
                DeliveryProfileCreateData.class
        );
        
        if (response.hasErrors()) {
            log.error("Failed to create delivery profile: {}", response.getErrors());
            throw new RuntimeException("Failed to create delivery profile");
        }
        
        DeliveryProfileCreateResponse createResponse = response.getData().getDeliveryProfileCreate();
        if (createResponse.getUserErrors() != null && !createResponse.getUserErrors().isEmpty()) {
            log.error("User errors creating delivery profile: {}", createResponse.getUserErrors());
            throw new RuntimeException("Failed to create delivery profile: " + createResponse.getUserErrors());
        }
        
        return createResponse.getProfile();
    }
    
    public DeliveryProfile updateDeliveryProfile(
            ShopifyAuthContext context,
            String profileId,
            DeliveryProfileInput input,
            Boolean leaveLegacyModeProfiles) {
        
        Map<String, Object> variables = new HashMap<>();
        variables.put("id", profileId);
        variables.put("profile", input);
        if (leaveLegacyModeProfiles != null) {
            variables.put("leaveLegacyModeProfiles", leaveLegacyModeProfiles);
        }
        
        GraphQLRequest request = GraphQLRequest.builder()
                .query(UPDATE_DELIVERY_PROFILE_MUTATION)
                .variables(variables)
                .build();
        
        GraphQLResponse<DeliveryProfileUpdateData> response = graphQLClient.execute(
                request,
                DeliveryProfileUpdateData.class
        );
        
        if (response.hasErrors()) {
            log.error("Failed to update delivery profile: {}", response.getErrors());
            throw new RuntimeException("Failed to update delivery profile");
        }
        
        DeliveryProfileUpdateResponse updateResponse = response.getData().getDeliveryProfileUpdate();
        if (updateResponse.getUserErrors() != null && !updateResponse.getUserErrors().isEmpty()) {
            log.error("User errors updating delivery profile: {}", updateResponse.getUserErrors());
            throw new RuntimeException("Failed to update delivery profile: " + updateResponse.getUserErrors());
        }
        
        return updateResponse.getProfile();
    }
    
    public List<DeliveryCarrierService> listCarrierServices(ShopifyAuthContext context) {
        GraphQLRequest request = GraphQLRequest.builder()
                .query(LIST_CARRIER_SERVICES_QUERY)
                .build();
        
        GraphQLResponse<CarrierServicesData> response = graphQLClient.execute(
                request,
                CarrierServicesData.class
        );
        
        if (response.hasErrors()) {
            log.error("Failed to list carrier services: {}", response.getErrors());
            throw new RuntimeException("Failed to list carrier services");
        }
        
        return response.getData().getCarrierServices();
    }
    
    public DeliveryLocationGroupZone createDeliveryZone(
            ShopifyAuthContext context,
            String locationGroupId,
            DeliveryLocationGroupZoneInput input) {
        
        Map<String, Object> variables = new HashMap<>();
        variables.put("locationGroupId", locationGroupId);
        variables.put("zone", input);
        
        GraphQLRequest request = GraphQLRequest.builder()
                .query(CREATE_DELIVERY_ZONE_MUTATION)
                .variables(variables)
                .build();
        
        GraphQLResponse<DeliveryLocationGroupZoneCreateData> response = graphQLClient.execute(
                request,
                DeliveryLocationGroupZoneCreateData.class
        );
        
        if (response.hasErrors()) {
            log.error("Failed to create delivery zone: {}", response.getErrors());
            throw new RuntimeException("Failed to create delivery zone");
        }
        
        DeliveryLocationGroupZoneCreateResponse createResponse = response.getData().getDeliveryProfileLocationGroupZoneCreate();
        if (createResponse.getUserErrors() != null && !createResponse.getUserErrors().isEmpty()) {
            log.error("User errors creating delivery zone: {}", createResponse.getUserErrors());
            throw new RuntimeException("Failed to create delivery zone: " + createResponse.getUserErrors());
        }
        
        return createResponse.getLocationGroupZone();
    }
    
    public DeliveryLocationGroupZone updateDeliveryZone(
            ShopifyAuthContext context,
            String zoneId,
            DeliveryLocationGroupZoneInput input) {
        
        Map<String, Object> variables = new HashMap<>();
        variables.put("id", zoneId);
        variables.put("zone", input);
        
        GraphQLRequest request = GraphQLRequest.builder()
                .query(UPDATE_DELIVERY_ZONE_MUTATION)
                .variables(variables)
                .build();
        
        GraphQLResponse<DeliveryLocationGroupZoneUpdateData> response = graphQLClient.execute(
                request,
                DeliveryLocationGroupZoneUpdateData.class
        );
        
        if (response.hasErrors()) {
            log.error("Failed to update delivery zone: {}", response.getErrors());
            throw new RuntimeException("Failed to update delivery zone");
        }
        
        DeliveryLocationGroupZoneUpdateResponse updateResponse = response.getData().getDeliveryProfileLocationGroupZoneUpdate();
        if (updateResponse.getUserErrors() != null && !updateResponse.getUserErrors().isEmpty()) {
            log.error("User errors updating delivery zone: {}", updateResponse.getUserErrors());
            throw new RuntimeException("Failed to update delivery zone: " + updateResponse.getUserErrors());
        }
        
        return updateResponse.getLocationGroupZone();
    }
    
    public String deleteDeliveryZone(ShopifyAuthContext context, String zoneId) {
        Map<String, Object> variables = new HashMap<>();
        variables.put("id", zoneId);
        
        GraphQLRequest request = GraphQLRequest.builder()
                .query(DELETE_DELIVERY_ZONE_MUTATION)
                .variables(variables)
                .build();
        
        GraphQLResponse<DeliveryLocationGroupZoneDeleteData> response = graphQLClient.execute(
                request,
                DeliveryLocationGroupZoneDeleteData.class
        );
        
        if (response.hasErrors()) {
            log.error("Failed to delete delivery zone: {}", response.getErrors());
            throw new RuntimeException("Failed to delete delivery zone");
        }
        
        DeliveryLocationGroupZoneDeleteResponse deleteResponse = response.getData().getDeliveryProfileLocationGroupZoneDelete();
        if (deleteResponse.getUserErrors() != null && !deleteResponse.getUserErrors().isEmpty()) {
            log.error("User errors deleting delivery zone: {}", deleteResponse.getUserErrors());
            throw new RuntimeException("Failed to delete delivery zone: " + deleteResponse.getUserErrors());
        }
        
        return deleteResponse.getDeletedId();
    }
    
    public DeliveryMethodDefinition createDeliveryMethod(
            ShopifyAuthContext context,
            String locationGroupZoneId,
            DeliveryMethodDefinitionInput input) {
        
        Map<String, Object> variables = new HashMap<>();
        variables.put("id", locationGroupZoneId);
        variables.put("methodDefinition", input);
        
        GraphQLRequest request = GraphQLRequest.builder()
                .query(CREATE_DELIVERY_METHOD_MUTATION)
                .variables(variables)
                .build();
        
        GraphQLResponse<DeliveryMethodDefinitionCreateData> response = graphQLClient.execute(
                request,
                DeliveryMethodDefinitionCreateData.class
        );
        
        if (response.hasErrors()) {
            log.error("Failed to create delivery method: {}", response.getErrors());
            throw new RuntimeException("Failed to create delivery method");
        }
        
        DeliveryMethodDefinitionCreateResponse createResponse = response.getData().getDeliveryMethodDefinitionCreate();
        if (createResponse.getUserErrors() != null && !createResponse.getUserErrors().isEmpty()) {
            log.error("User errors creating delivery method: {}", createResponse.getUserErrors());
            throw new RuntimeException("Failed to create delivery method: " + createResponse.getUserErrors());
        }
        
        return createResponse.getMethodDefinition();
    }
    
    public DeliveryMethodDefinition updateDeliveryMethod(
            ShopifyAuthContext context,
            String methodId,
            DeliveryMethodDefinitionInput input) {
        
        Map<String, Object> variables = new HashMap<>();
        variables.put("id", methodId);
        variables.put("methodDefinition", input);
        
        GraphQLRequest request = GraphQLRequest.builder()
                .query(UPDATE_DELIVERY_METHOD_MUTATION)
                .variables(variables)
                .build();
        
        GraphQLResponse<DeliveryMethodDefinitionUpdateData> response = graphQLClient.execute(
                request,
                DeliveryMethodDefinitionUpdateData.class
        );
        
        if (response.hasErrors()) {
            log.error("Failed to update delivery method: {}", response.getErrors());
            throw new RuntimeException("Failed to update delivery method");
        }
        
        DeliveryMethodDefinitionUpdateResponse updateResponse = response.getData().getDeliveryMethodDefinitionUpdate();
        if (updateResponse.getUserErrors() != null && !updateResponse.getUserErrors().isEmpty()) {
            log.error("User errors updating delivery method: {}", updateResponse.getUserErrors());
            throw new RuntimeException("Failed to update delivery method: " + updateResponse.getUserErrors());
        }
        
        return updateResponse.getMethodDefinition();
    }
    
    @Data
    private static class DeliveryProfileData {
        private DeliveryProfile deliveryProfile;
    }
    
    @Data
    private static class DeliveryProfilesData {
        private DeliveryProfileConnection deliveryProfiles;
    }
    
    @Data
    private static class DeliveryProfileCreateData {
        private DeliveryProfileCreateResponse deliveryProfileCreate;
    }
    
    @Data
    private static class DeliveryProfileUpdateData {
        private DeliveryProfileUpdateResponse deliveryProfileUpdate;
    }
    
    @Data
    private static class CarrierServicesData {
        private List<DeliveryCarrierService> carrierServices;
    }
    
    @Data
    private static class DeliveryLocationGroupZoneCreateData {
        private DeliveryLocationGroupZoneCreateResponse deliveryProfileLocationGroupZoneCreate;
    }
    
    @Data
    private static class DeliveryLocationGroupZoneUpdateData {
        private DeliveryLocationGroupZoneUpdateResponse deliveryProfileLocationGroupZoneUpdate;
    }
    
    @Data
    private static class DeliveryLocationGroupZoneDeleteData {
        private DeliveryLocationGroupZoneDeleteResponse deliveryProfileLocationGroupZoneDelete;
    }
    
    @Data
    private static class DeliveryMethodDefinitionCreateData {
        private DeliveryMethodDefinitionCreateResponse deliveryMethodDefinitionCreate;
    }
    
    @Data
    private static class DeliveryMethodDefinitionUpdateData {
        private DeliveryMethodDefinitionUpdateResponse deliveryMethodDefinitionUpdate;
    }
    
    @Data
    public static class DeliveryProfileCreateResponse {
        private DeliveryProfile profile;
        private List<UserError> userErrors;
    }
    
    @Data
    public static class DeliveryProfileUpdateResponse {
        private DeliveryProfile profile;
        private List<UserError> userErrors;
    }
    
    @Data
    public static class DeliveryLocationGroupZoneCreateResponse {
        private DeliveryLocationGroupZone locationGroupZone;
        private List<UserError> userErrors;
    }
    
    @Data
    public static class DeliveryLocationGroupZoneUpdateResponse {
        private DeliveryLocationGroupZone locationGroupZone;
        private List<UserError> userErrors;
    }
    
    @Data
    public static class DeliveryLocationGroupZoneDeleteResponse {
        private String deletedId;
        private List<UserError> userErrors;
    }
    
    @Data
    public static class DeliveryMethodDefinitionCreateResponse {
        private DeliveryMethodDefinition methodDefinition;
        private List<UserError> userErrors;
    }
    
    @Data
    public static class DeliveryMethodDefinitionUpdateResponse {
        private DeliveryMethodDefinition methodDefinition;
        private List<UserError> userErrors;
    }
    
    @Data
    public static class UserError {
        private List<String> field;
        private String message;
        private String code;
    }
    
    @Data
    public static class DeliveryProfileConnection {
        private List<DeliveryProfileEdge> edges;
        private PageInfo pageInfo;
    }
    
    @Data
    public static class DeliveryProfileEdge {
        private DeliveryProfile node;
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