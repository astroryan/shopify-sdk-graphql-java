package com.shopify.sdk.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.shopify.sdk.model.graphql.GraphQLRequest;
import com.shopify.sdk.model.graphql.GraphQLResponse;
import com.shopify.sdk.client.ShopifyGraphQLClient;
import com.shopify.sdk.auth.ShopifyAuthContext;
import com.shopify.sdk.exception.ShopifyApiException;
import com.shopify.sdk.model.common.UserError;
import com.shopify.sdk.model.retail.*;
import com.shopify.sdk.model.retail.input.*;
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
public class RetailService {
    
    private final ShopifyGraphQLClient graphQLClient;
    
    // Query for devices
    private static final String GET_DEVICES_QUERY = """
        query devices($first: Int!, $after: String, $query: String) {
            devices(first: $first, after: $after, query: $query) {
                edges {
                    node {
                        id
                        name
                        icon
                        createdAt
                        enabledCardReaderTypes
                        location {
                            id
                            name
                        }
                        type
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
    
    // Query for single device
    private static final String GET_DEVICE_QUERY = """
        query device($id: ID!) {
            device(id: $id) {
                id
                name
                icon
                createdAt
                enabledCardReaderTypes
                location {
                    id
                    name
                    address {
                        address1
                        address2
                        city
                        country
                        countryCode
                        province
                        provinceCode
                        zip
                    }
                }
                type
                updatedAt
            }
        }
        """;
    
    // Query for device credentials
    private static final String GET_DEVICE_CREDENTIALS_QUERY = """
        query deviceCredentials($first: Int!, $after: String, $deviceId: ID, $staffMemberId: ID) {
            deviceCredentials(
                first: $first,
                after: $after,
                deviceId: $deviceId,
                staffMemberId: $staffMemberId
            ) {
                edges {
                    node {
                        id
                        activatedAt
                        createdAt
                        device {
                            id
                            name
                        }
                        expiresAt
                        revokedAt
                        staffMember {
                            id
                            name
                            email
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
    
    // Query for staff members
    private static final String GET_STAFF_MEMBERS_QUERY = """
        query staffMembers($first: Int!, $after: String, $query: String, $status: StaffMemberStatus) {
            staffMembers(
                first: $first,
                after: $after,
                query: $query,
                status: $status
            ) {
                edges {
                    node {
                        id
                        active
                        avatar {
                            url
                        }
                        email
                        exists
                        firstName
                        initials
                        isShopOwner
                        lastName
                        locale
                        name
                        phone
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
    
    // Query for POS external devices
    private static final String GET_POS_EXTERNAL_DEVICES_QUERY = """
        query posExternalDevices($first: Int!, $after: String, $locationId: ID) {
            posExternalDevices(
                first: $first,
                after: $after,
                locationId: $locationId
            ) {
                edges {
                    node {
                        id
                        apiClient {
                            id
                            title
                        }
                        createdAt
                        externalId
                        location {
                            id
                            name
                        }
                        name
                        status
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
    
    // Query for retail sessions
    private static final String GET_RETAIL_SESSIONS_QUERY = """
        query retailSessions($first: Int!, $after: String, $locationId: ID, $state: RetailSessionState) {
            retailSessions(
                first: $first,
                after: $after,
                locationId: $locationId,
                state: $state
            ) {
                edges {
                    node {
                        id
                        cashTrackingEnabled
                        closedAt
                        closingBalance {
                            amount
                            currencyCode
                        }
                        closingNote
                        device {
                            id
                            name
                        }
                        openedAt
                        openingBalance {
                            amount
                            currencyCode
                        }
                        openingNote
                        staffMember {
                            id
                            name
                        }
                        state
                        totalCashMovement {
                            amount
                            currencyCode
                        }
                        totalCashPayments {
                            amount
                            currencyCode
                        }
                        totalCashRefunds {
                            amount
                            currencyCode
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
    
    // Query for cash tracking sessions
    private static final String GET_CASH_TRACKING_SESSIONS_QUERY = """
        query cashTrackingSessions($first: Int!, $after: String, $locationId: ID, $state: CashTrackingSessionState) {
            cashTrackingSessions(
                first: $first,
                after: $after,
                locationId: $locationId,
                state: $state
            ) {
                edges {
                    node {
                        id
                        closedAt
                        closingBalance {
                            amount
                            currencyCode
                        }
                        device {
                            id
                            name
                        }
                        expectedBalance {
                            amount
                            currencyCode
                        }
                        location {
                            id
                            name
                        }
                        openedAt
                        openingBalance {
                            amount
                            currencyCode
                        }
                        staffMember {
                            id
                            name
                        }
                        state
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
    
    // Mutation to create device
    private static final String CREATE_DEVICE_MUTATION = """
        mutation deviceCreate($input: DeviceCreateInput!) {
            deviceCreate(input: $input) {
                device {
                    id
                    name
                    type
                    location {
                        id
                        name
                    }
                }
                userErrors {
                    field
                    message
                }
            }
        }
        """;
    
    // Mutation to update device
    private static final String UPDATE_DEVICE_MUTATION = """
        mutation deviceUpdate($id: ID!, $input: DeviceUpdateInput!) {
            deviceUpdate(id: $id, input: $input) {
                device {
                    id
                    name
                    enabledCardReaderTypes
                }
                userErrors {
                    field
                    message
                }
            }
        }
        """;
    
    // Mutation to delete device
    private static final String DELETE_DEVICE_MUTATION = """
        mutation deviceDelete($id: ID!) {
            deviceDelete(id: $id) {
                deletedDeviceId
                userErrors {
                    field
                    message
                }
            }
        }
        """;
    
    // Mutation to create device credential
    private static final String CREATE_DEVICE_CREDENTIAL_MUTATION = """
        mutation deviceCredentialCreate($input: DeviceCredentialCreateInput!) {
            deviceCredentialCreate(input: $input) {
                deviceCredential {
                    id
                    token
                    device {
                        id
                        name
                    }
                    staffMember {
                        id
                        name
                    }
                }
                userErrors {
                    field
                    message
                }
            }
        }
        """;
    
    // Mutation to revoke device credential
    private static final String REVOKE_DEVICE_CREDENTIAL_MUTATION = """
        mutation deviceCredentialRevoke($id: ID!) {
            deviceCredentialRevoke(id: $id) {
                deviceCredential {
                    id
                    revokedAt
                }
                userErrors {
                    field
                    message
                }
            }
        }
        """;
    
    // Mutation to create POS external device
    private static final String CREATE_POS_EXTERNAL_DEVICE_MUTATION = """
        mutation posExternalDeviceCreate($input: PosExternalDeviceCreateInput!) {
            posExternalDeviceCreate(input: $input) {
                posExternalDevice {
                    id
                    externalId
                    name
                    status
                }
                userErrors {
                    field
                    message
                }
            }
        }
        """;
    
    // Mutation to delete POS external device
    private static final String DELETE_POS_EXTERNAL_DEVICE_MUTATION = """
        mutation posExternalDeviceDelete($id: ID!) {
            posExternalDeviceDelete(id: $id) {
                deletedPosExternalDeviceId
                userErrors {
                    field
                    message
                }
            }
        }
        """;
    
    // Mutation to open retail session
    private static final String OPEN_RETAIL_SESSION_MUTATION = """
        mutation retailSessionOpen($input: RetailSessionOpenInput!) {
            retailSessionOpen(input: $input) {
                retailSession {
                    id
                    state
                    openedAt
                    device {
                        id
                        name
                    }
                }
                userErrors {
                    field
                    message
                }
            }
        }
        """;
    
    // Mutation to close retail session
    private static final String CLOSE_RETAIL_SESSION_MUTATION = """
        mutation retailSessionClose($input: RetailSessionCloseInput!) {
            retailSessionClose(input: $input) {
                retailSession {
                    id
                    state
                    closedAt
                    closingBalance {
                        amount
                        currencyCode
                    }
                }
                userErrors {
                    field
                    message
                }
            }
        }
        """;
    
    // Mutation to open cash tracking session
    private static final String OPEN_CASH_TRACKING_SESSION_MUTATION = """
        mutation cashTrackingSessionOpen($input: CashTrackingSessionOpenInput!) {
            cashTrackingSessionOpen(input: $input) {
                cashTrackingSession {
                    id
                    state
                    openedAt
                    openingBalance {
                        amount
                        currencyCode
                    }
                }
                userErrors {
                    field
                    message
                }
            }
        }
        """;
    
    // Mutation to close cash tracking session
    private static final String CLOSE_CASH_TRACKING_SESSION_MUTATION = """
        mutation cashTrackingSessionClose($input: CashTrackingSessionCloseInput!) {
            cashTrackingSessionClose(input: $input) {
                cashTrackingSession {
                    id
                    state
                    closedAt
                    closingBalance {
                        amount
                        currencyCode
                    }
                }
                userErrors {
                    field
                    message
                }
            }
        }
        """;
    
    // Mutation to create cash adjustment
    private static final String CREATE_CASH_ADJUSTMENT_MUTATION = """
        mutation cashAdjustmentCreate($input: CashAdjustmentCreateInput!) {
            cashAdjustmentCreate(input: $input) {
                cashAdjustment {
                    id
                    amount {
                        amount
                        currencyCode
                    }
                    note
                }
                userErrors {
                    field
                    message
                }
            }
        }
        """;
    
    // Service methods
    public List<Device> getDevices(
            ShopifyAuthContext context,
            int first,
            String after,
            String query) {
        graphQLClient.setAuthContext(context);
        
        Map<String, Object> variables = new HashMap<>();
        variables.put("first", first);
        if (after != null) {
            variables.put("after", after);
        }
        if (query != null) {
            variables.put("query", query);
        }
        
        GraphQLRequest request = GraphQLRequest.builder()
                .query(GET_DEVICES_QUERY)
                .variables(variables)
                .build();
        
        GraphQLResponse<DevicesResponse> response = graphQLClient.execute(
                request,
                new TypeReference<GraphQLResponse<DevicesResponse>>() {}
        );
        
        if (response.hasErrors()) {
            throw new ShopifyApiException("Failed to get devices", response.getErrors());
        }
        
        List<Device> devices = new ArrayList<>();
        response.getData().devices.edges.forEach(edge -> 
            devices.add(edge.node)
        );
        
        return devices;
    }
    
    public Device getDevice(ShopifyAuthContext context, String id) {
        graphQLClient.setAuthContext(context);
        
        Map<String, Object> variables = new HashMap<>();
        variables.put("id", id);
        
        GraphQLRequest request = GraphQLRequest.builder()
                .query(GET_DEVICE_QUERY)
                .variables(variables)
                .build();
        
        GraphQLResponse<DeviceResponse> response = graphQLClient.execute(
                request,
                new TypeReference<GraphQLResponse<DeviceResponse>>() {}
        );
        
        if (response.hasErrors()) {
            throw new ShopifyApiException("Failed to get device", response.getErrors());
        }
        
        return response.getData().device;
    }
    
    public List<DeviceCredential> getDeviceCredentials(
            ShopifyAuthContext context,
            int first,
            String after,
            String deviceId,
            String staffMemberId) {
        graphQLClient.setAuthContext(context);
        
        Map<String, Object> variables = new HashMap<>();
        variables.put("first", first);
        if (after != null) {
            variables.put("after", after);
        }
        if (deviceId != null) {
            variables.put("deviceId", deviceId);
        }
        if (staffMemberId != null) {
            variables.put("staffMemberId", staffMemberId);
        }
        
        GraphQLRequest request = GraphQLRequest.builder()
                .query(GET_DEVICE_CREDENTIALS_QUERY)
                .variables(variables)
                .build();
        
        GraphQLResponse<DeviceCredentialsResponse> response = graphQLClient.execute(
                request,
                new TypeReference<GraphQLResponse<DeviceCredentialsResponse>>() {}
        );
        
        if (response.hasErrors()) {
            throw new ShopifyApiException("Failed to get device credentials", response.getErrors());
        }
        
        List<DeviceCredential> credentials = new ArrayList<>();
        response.getData().deviceCredentials.edges.forEach(edge -> 
            credentials.add(edge.node)
        );
        
        return credentials;
    }
    
    public List<StaffMember> getStaffMembers(
            ShopifyAuthContext context,
            int first,
            String after,
            String query,
            StaffMemberStatus status) {
        graphQLClient.setAuthContext(context);
        
        Map<String, Object> variables = new HashMap<>();
        variables.put("first", first);
        if (after != null) {
            variables.put("after", after);
        }
        if (query != null) {
            variables.put("query", query);
        }
        if (status != null) {
            variables.put("status", status);
        }
        
        GraphQLRequest request = GraphQLRequest.builder()
                .query(GET_STAFF_MEMBERS_QUERY)
                .variables(variables)
                .build();
        
        GraphQLResponse<StaffMembersResponse> response = graphQLClient.execute(
                request,
                new TypeReference<GraphQLResponse<StaffMembersResponse>>() {}
        );
        
        if (response.hasErrors()) {
            throw new ShopifyApiException("Failed to get staff members", response.getErrors());
        }
        
        List<StaffMember> staffMembers = new ArrayList<>();
        response.getData().staffMembers.edges.forEach(edge -> 
            staffMembers.add(edge.node)
        );
        
        return staffMembers;
    }
    
    public List<PosExternalDevice> getPosExternalDevices(
            ShopifyAuthContext context,
            int first,
            String after,
            String locationId) {
        graphQLClient.setAuthContext(context);
        
        Map<String, Object> variables = new HashMap<>();
        variables.put("first", first);
        if (after != null) {
            variables.put("after", after);
        }
        if (locationId != null) {
            variables.put("locationId", locationId);
        }
        
        GraphQLRequest request = GraphQLRequest.builder()
                .query(GET_POS_EXTERNAL_DEVICES_QUERY)
                .variables(variables)
                .build();
        
        GraphQLResponse<PosExternalDevicesResponse> response = graphQLClient.execute(
                request,
                new TypeReference<GraphQLResponse<PosExternalDevicesResponse>>() {}
        );
        
        if (response.hasErrors()) {
            throw new ShopifyApiException("Failed to get POS external devices", response.getErrors());
        }
        
        List<PosExternalDevice> devices = new ArrayList<>();
        response.getData().posExternalDevices.edges.forEach(edge -> 
            devices.add(edge.node)
        );
        
        return devices;
    }
    
    public List<RetailSession> getRetailSessions(
            ShopifyAuthContext context,
            int first,
            String after,
            String locationId,
            RetailSessionState state) {
        graphQLClient.setAuthContext(context);
        
        Map<String, Object> variables = new HashMap<>();
        variables.put("first", first);
        if (after != null) {
            variables.put("after", after);
        }
        if (locationId != null) {
            variables.put("locationId", locationId);
        }
        if (state != null) {
            variables.put("state", state);
        }
        
        GraphQLRequest request = GraphQLRequest.builder()
                .query(GET_RETAIL_SESSIONS_QUERY)
                .variables(variables)
                .build();
        
        GraphQLResponse<RetailSessionsResponse> response = graphQLClient.execute(
                request,
                new TypeReference<GraphQLResponse<RetailSessionsResponse>>() {}
        );
        
        if (response.hasErrors()) {
            throw new ShopifyApiException("Failed to get retail sessions", response.getErrors());
        }
        
        List<RetailSession> sessions = new ArrayList<>();
        response.getData().retailSessions.edges.forEach(edge -> 
            sessions.add(edge.node)
        );
        
        return sessions;
    }
    
    public List<CashTrackingSession> getCashTrackingSessions(
            ShopifyAuthContext context,
            int first,
            String after,
            String locationId,
            CashTrackingSessionState state) {
        graphQLClient.setAuthContext(context);
        
        Map<String, Object> variables = new HashMap<>();
        variables.put("first", first);
        if (after != null) {
            variables.put("after", after);
        }
        if (locationId != null) {
            variables.put("locationId", locationId);
        }
        if (state != null) {
            variables.put("state", state);
        }
        
        GraphQLRequest request = GraphQLRequest.builder()
                .query(GET_CASH_TRACKING_SESSIONS_QUERY)
                .variables(variables)
                .build();
        
        GraphQLResponse<CashTrackingSessionsResponse> response = graphQLClient.execute(
                request,
                new TypeReference<GraphQLResponse<CashTrackingSessionsResponse>>() {}
        );
        
        if (response.hasErrors()) {
            throw new ShopifyApiException("Failed to get cash tracking sessions", response.getErrors());
        }
        
        List<CashTrackingSession> sessions = new ArrayList<>();
        response.getData().cashTrackingSessions.edges.forEach(edge -> 
            sessions.add(edge.node)
        );
        
        return sessions;
    }
    
    public Device createDevice(ShopifyAuthContext context, DeviceCreateInput input) {
        graphQLClient.setAuthContext(context);
        
        Map<String, Object> variables = new HashMap<>();
        variables.put("input", input);
        
        GraphQLRequest request = GraphQLRequest.builder()
                .query(CREATE_DEVICE_MUTATION)
                .variables(variables)
                .build();
        
        GraphQLResponse<DeviceCreateResponse> response = graphQLClient.execute(
                request,
                new TypeReference<GraphQLResponse<DeviceCreateResponse>>() {}
        );
        
        if (response.hasErrors()) {
            throw new ShopifyApiException("Failed to create device", response.getErrors());
        }
        
        if (response.getData().deviceCreate.userErrors != null && 
            !response.getData().deviceCreate.userErrors.isEmpty()) {
            throw new ShopifyApiException(
                    "Failed to create device",
                    response.getData().deviceCreate.userErrors
            );
        }
        
        return response.getData().deviceCreate.device;
    }
    
    public Device updateDevice(ShopifyAuthContext context, String id, DeviceUpdateInput input) {
        graphQLClient.setAuthContext(context);
        
        Map<String, Object> variables = new HashMap<>();
        variables.put("id", id);
        variables.put("input", input);
        
        GraphQLRequest request = GraphQLRequest.builder()
                .query(UPDATE_DEVICE_MUTATION)
                .variables(variables)
                .build();
        
        GraphQLResponse<DeviceUpdateResponse> response = graphQLClient.execute(
                request,
                new TypeReference<GraphQLResponse<DeviceUpdateResponse>>() {}
        );
        
        if (response.hasErrors()) {
            throw new ShopifyApiException("Failed to update device", response.getErrors());
        }
        
        if (response.getData().deviceUpdate.userErrors != null && 
            !response.getData().deviceUpdate.userErrors.isEmpty()) {
            throw new ShopifyApiException(
                    "Failed to update device",
                    response.getData().deviceUpdate.userErrors
            );
        }
        
        return response.getData().deviceUpdate.device;
    }
    
    public String deleteDevice(ShopifyAuthContext context, String id) {
        graphQLClient.setAuthContext(context);
        
        Map<String, Object> variables = new HashMap<>();
        variables.put("id", id);
        
        GraphQLRequest request = GraphQLRequest.builder()
                .query(DELETE_DEVICE_MUTATION)
                .variables(variables)
                .build();
        
        GraphQLResponse<DeviceDeleteResponse> response = graphQLClient.execute(
                request,
                new TypeReference<GraphQLResponse<DeviceDeleteResponse>>() {}
        );
        
        if (response.hasErrors()) {
            throw new ShopifyApiException("Failed to delete device", response.getErrors());
        }
        
        if (response.getData().deviceDelete.userErrors != null && 
            !response.getData().deviceDelete.userErrors.isEmpty()) {
            throw new ShopifyApiException(
                    "Failed to delete device",
                    response.getData().deviceDelete.userErrors
            );
        }
        
        return response.getData().deviceDelete.deletedDeviceId;
    }
    
    public DeviceCredential createDeviceCredential(ShopifyAuthContext context, DeviceCredentialCreateInput input) {
        graphQLClient.setAuthContext(context);
        
        Map<String, Object> variables = new HashMap<>();
        variables.put("input", input);
        
        GraphQLRequest request = GraphQLRequest.builder()
                .query(CREATE_DEVICE_CREDENTIAL_MUTATION)
                .variables(variables)
                .build();
        
        GraphQLResponse<DeviceCredentialCreateResponse> response = graphQLClient.execute(
                request,
                new TypeReference<GraphQLResponse<DeviceCredentialCreateResponse>>() {}
        );
        
        if (response.hasErrors()) {
            throw new ShopifyApiException("Failed to create device credential", response.getErrors());
        }
        
        if (response.getData().deviceCredentialCreate.userErrors != null && 
            !response.getData().deviceCredentialCreate.userErrors.isEmpty()) {
            throw new ShopifyApiException(
                    "Failed to create device credential",
                    response.getData().deviceCredentialCreate.userErrors
            );
        }
        
        return response.getData().deviceCredentialCreate.deviceCredential;
    }
    
    public DeviceCredential revokeDeviceCredential(ShopifyAuthContext context, String id) {
        graphQLClient.setAuthContext(context);
        
        Map<String, Object> variables = new HashMap<>();
        variables.put("id", id);
        
        GraphQLRequest request = GraphQLRequest.builder()
                .query(REVOKE_DEVICE_CREDENTIAL_MUTATION)
                .variables(variables)
                .build();
        
        GraphQLResponse<DeviceCredentialRevokeResponse> response = graphQLClient.execute(
                request,
                new TypeReference<GraphQLResponse<DeviceCredentialRevokeResponse>>() {}
        );
        
        if (response.hasErrors()) {
            throw new ShopifyApiException("Failed to revoke device credential", response.getErrors());
        }
        
        if (response.getData().deviceCredentialRevoke.userErrors != null && 
            !response.getData().deviceCredentialRevoke.userErrors.isEmpty()) {
            throw new ShopifyApiException(
                    "Failed to revoke device credential",
                    response.getData().deviceCredentialRevoke.userErrors
            );
        }
        
        return response.getData().deviceCredentialRevoke.deviceCredential;
    }
    
    public PosExternalDevice createPosExternalDevice(ShopifyAuthContext context, PosExternalDeviceCreateInput input) {
        graphQLClient.setAuthContext(context);
        
        Map<String, Object> variables = new HashMap<>();
        variables.put("input", input);
        
        GraphQLRequest request = GraphQLRequest.builder()
                .query(CREATE_POS_EXTERNAL_DEVICE_MUTATION)
                .variables(variables)
                .build();
        
        GraphQLResponse<PosExternalDeviceCreateResponse> response = graphQLClient.execute(
                request,
                new TypeReference<GraphQLResponse<PosExternalDeviceCreateResponse>>() {}
        );
        
        if (response.hasErrors()) {
            throw new ShopifyApiException("Failed to create POS external device", response.getErrors());
        }
        
        if (response.getData().posExternalDeviceCreate.userErrors != null && 
            !response.getData().posExternalDeviceCreate.userErrors.isEmpty()) {
            throw new ShopifyApiException(
                    "Failed to create POS external device",
                    response.getData().posExternalDeviceCreate.userErrors
            );
        }
        
        return response.getData().posExternalDeviceCreate.posExternalDevice;
    }
    
    public String deletePosExternalDevice(ShopifyAuthContext context, String id) {
        graphQLClient.setAuthContext(context);
        
        Map<String, Object> variables = new HashMap<>();
        variables.put("id", id);
        
        GraphQLRequest request = GraphQLRequest.builder()
                .query(DELETE_POS_EXTERNAL_DEVICE_MUTATION)
                .variables(variables)
                .build();
        
        GraphQLResponse<PosExternalDeviceDeleteResponse> response = graphQLClient.execute(
                request,
                new TypeReference<GraphQLResponse<PosExternalDeviceDeleteResponse>>() {}
        );
        
        if (response.hasErrors()) {
            throw new ShopifyApiException("Failed to delete POS external device", response.getErrors());
        }
        
        if (response.getData().posExternalDeviceDelete.userErrors != null && 
            !response.getData().posExternalDeviceDelete.userErrors.isEmpty()) {
            throw new ShopifyApiException(
                    "Failed to delete POS external device",
                    response.getData().posExternalDeviceDelete.userErrors
            );
        }
        
        return response.getData().posExternalDeviceDelete.deletedPosExternalDeviceId;
    }
    
    public RetailSession openRetailSession(ShopifyAuthContext context, RetailSessionOpenInput input) {
        graphQLClient.setAuthContext(context);
        
        Map<String, Object> variables = new HashMap<>();
        variables.put("input", input);
        
        GraphQLRequest request = GraphQLRequest.builder()
                .query(OPEN_RETAIL_SESSION_MUTATION)
                .variables(variables)
                .build();
        
        GraphQLResponse<RetailSessionOpenResponse> response = graphQLClient.execute(
                request,
                new TypeReference<GraphQLResponse<RetailSessionOpenResponse>>() {}
        );
        
        if (response.hasErrors()) {
            throw new ShopifyApiException("Failed to open retail session", response.getErrors());
        }
        
        if (response.getData().retailSessionOpen.userErrors != null && 
            !response.getData().retailSessionOpen.userErrors.isEmpty()) {
            throw new ShopifyApiException(
                    "Failed to open retail session",
                    response.getData().retailSessionOpen.userErrors
            );
        }
        
        return response.getData().retailSessionOpen.retailSession;
    }
    
    public RetailSession closeRetailSession(ShopifyAuthContext context, RetailSessionCloseInput input) {
        graphQLClient.setAuthContext(context);
        
        Map<String, Object> variables = new HashMap<>();
        variables.put("input", input);
        
        GraphQLRequest request = GraphQLRequest.builder()
                .query(CLOSE_RETAIL_SESSION_MUTATION)
                .variables(variables)
                .build();
        
        GraphQLResponse<RetailSessionCloseResponse> response = graphQLClient.execute(
                request,
                new TypeReference<GraphQLResponse<RetailSessionCloseResponse>>() {}
        );
        
        if (response.hasErrors()) {
            throw new ShopifyApiException("Failed to close retail session", response.getErrors());
        }
        
        if (response.getData().retailSessionClose.userErrors != null && 
            !response.getData().retailSessionClose.userErrors.isEmpty()) {
            throw new ShopifyApiException(
                    "Failed to close retail session",
                    response.getData().retailSessionClose.userErrors
            );
        }
        
        return response.getData().retailSessionClose.retailSession;
    }
    
    public CashTrackingSession openCashTrackingSession(ShopifyAuthContext context, CashTrackingSessionOpenInput input) {
        graphQLClient.setAuthContext(context);
        
        Map<String, Object> variables = new HashMap<>();
        variables.put("input", input);
        
        GraphQLRequest request = GraphQLRequest.builder()
                .query(OPEN_CASH_TRACKING_SESSION_MUTATION)
                .variables(variables)
                .build();
        
        GraphQLResponse<CashTrackingSessionOpenResponse> response = graphQLClient.execute(
                request,
                new TypeReference<GraphQLResponse<CashTrackingSessionOpenResponse>>() {}
        );
        
        if (response.hasErrors()) {
            throw new ShopifyApiException("Failed to open cash tracking session", response.getErrors());
        }
        
        if (response.getData().cashTrackingSessionOpen.userErrors != null && 
            !response.getData().cashTrackingSessionOpen.userErrors.isEmpty()) {
            throw new ShopifyApiException(
                    "Failed to open cash tracking session",
                    response.getData().cashTrackingSessionOpen.userErrors
            );
        }
        
        return response.getData().cashTrackingSessionOpen.cashTrackingSession;
    }
    
    public CashTrackingSession closeCashTrackingSession(ShopifyAuthContext context, CashTrackingSessionCloseInput input) {
        graphQLClient.setAuthContext(context);
        
        Map<String, Object> variables = new HashMap<>();
        variables.put("input", input);
        
        GraphQLRequest request = GraphQLRequest.builder()
                .query(CLOSE_CASH_TRACKING_SESSION_MUTATION)
                .variables(variables)
                .build();
        
        GraphQLResponse<CashTrackingSessionCloseResponse> response = graphQLClient.execute(
                request,
                new TypeReference<GraphQLResponse<CashTrackingSessionCloseResponse>>() {}
        );
        
        if (response.hasErrors()) {
            throw new ShopifyApiException("Failed to close cash tracking session", response.getErrors());
        }
        
        if (response.getData().cashTrackingSessionClose.userErrors != null && 
            !response.getData().cashTrackingSessionClose.userErrors.isEmpty()) {
            throw new ShopifyApiException(
                    "Failed to close cash tracking session",
                    response.getData().cashTrackingSessionClose.userErrors
            );
        }
        
        return response.getData().cashTrackingSessionClose.cashTrackingSession;
    }
    
    public CashAdjustment createCashAdjustment(ShopifyAuthContext context, CashAdjustmentCreateInput input) {
        graphQLClient.setAuthContext(context);
        
        Map<String, Object> variables = new HashMap<>();
        variables.put("input", input);
        
        GraphQLRequest request = GraphQLRequest.builder()
                .query(CREATE_CASH_ADJUSTMENT_MUTATION)
                .variables(variables)
                .build();
        
        GraphQLResponse<CashAdjustmentCreateResponse> response = graphQLClient.execute(
                request,
                new TypeReference<GraphQLResponse<CashAdjustmentCreateResponse>>() {}
        );
        
        if (response.hasErrors()) {
            throw new ShopifyApiException("Failed to create cash adjustment", response.getErrors());
        }
        
        if (response.getData().cashAdjustmentCreate.userErrors != null && 
            !response.getData().cashAdjustmentCreate.userErrors.isEmpty()) {
            throw new ShopifyApiException(
                    "Failed to create cash adjustment",
                    response.getData().cashAdjustmentCreate.userErrors
            );
        }
        
        return response.getData().cashAdjustmentCreate.cashAdjustment;
    }
    
    // Response classes
    @Data
    private static class DevicesResponse {
        private DeviceConnection devices;
    }
    
    @Data
    private static class DeviceResponse {
        private Device device;
    }
    
    @Data
    private static class DeviceCredentialsResponse {
        private DeviceCredentialConnection deviceCredentials;
    }
    
    @Data
    private static class StaffMembersResponse {
        private StaffMemberConnection staffMembers;
    }
    
    @Data
    private static class PosExternalDevicesResponse {
        private PosExternalDeviceConnection posExternalDevices;
    }
    
    @Data
    private static class RetailSessionsResponse {
        private RetailSessionConnection retailSessions;
    }
    
    @Data
    private static class CashTrackingSessionsResponse {
        private CashTrackingSessionConnection cashTrackingSessions;
    }
    
    @Data
    private static class DeviceCreateResponse {
        private DeviceCreateResult deviceCreate;
    }
    
    @Data
    private static class DeviceCreateResult {
        private Device device;
        private List<UserError> userErrors;
    }
    
    @Data
    private static class DeviceUpdateResponse {
        private DeviceUpdateResult deviceUpdate;
    }
    
    @Data
    private static class DeviceUpdateResult {
        private Device device;
        private List<UserError> userErrors;
    }
    
    @Data
    private static class DeviceDeleteResponse {
        private DeviceDeleteResult deviceDelete;
    }
    
    @Data
    private static class DeviceDeleteResult {
        private String deletedDeviceId;
        private List<UserError> userErrors;
    }
    
    @Data
    private static class DeviceCredentialCreateResponse {
        private DeviceCredentialCreateResult deviceCredentialCreate;
    }
    
    @Data
    private static class DeviceCredentialCreateResult {
        private DeviceCredential deviceCredential;
        private List<UserError> userErrors;
    }
    
    @Data
    private static class DeviceCredentialRevokeResponse {
        private DeviceCredentialRevokeResult deviceCredentialRevoke;
    }
    
    @Data
    private static class DeviceCredentialRevokeResult {
        private DeviceCredential deviceCredential;
        private List<UserError> userErrors;
    }
    
    @Data
    private static class PosExternalDeviceCreateResponse {
        private PosExternalDeviceCreateResult posExternalDeviceCreate;
    }
    
    @Data
    private static class PosExternalDeviceCreateResult {
        private PosExternalDevice posExternalDevice;
        private List<UserError> userErrors;
    }
    
    @Data
    private static class PosExternalDeviceDeleteResponse {
        private PosExternalDeviceDeleteResult posExternalDeviceDelete;
    }
    
    @Data
    private static class PosExternalDeviceDeleteResult {
        private String deletedPosExternalDeviceId;
        private List<UserError> userErrors;
    }
    
    @Data
    private static class RetailSessionOpenResponse {
        private RetailSessionOpenResult retailSessionOpen;
    }
    
    @Data
    private static class RetailSessionOpenResult {
        private RetailSession retailSession;
        private List<UserError> userErrors;
    }
    
    @Data
    private static class RetailSessionCloseResponse {
        private RetailSessionCloseResult retailSessionClose;
    }
    
    @Data
    private static class RetailSessionCloseResult {
        private RetailSession retailSession;
        private List<UserError> userErrors;
    }
    
    @Data
    private static class CashTrackingSessionOpenResponse {
        private CashTrackingSessionOpenResult cashTrackingSessionOpen;
    }
    
    @Data
    private static class CashTrackingSessionOpenResult {
        private CashTrackingSession cashTrackingSession;
        private List<UserError> userErrors;
    }
    
    @Data
    private static class CashTrackingSessionCloseResponse {
        private CashTrackingSessionCloseResult cashTrackingSessionClose;
    }
    
    @Data
    private static class CashTrackingSessionCloseResult {
        private CashTrackingSession cashTrackingSession;
        private List<UserError> userErrors;
    }
    
    @Data
    private static class CashAdjustmentCreateResponse {
        private CashAdjustmentCreateResult cashAdjustmentCreate;
    }
    
    @Data
    private static class CashAdjustmentCreateResult {
        private CashAdjustment cashAdjustment;
        private List<UserError> userErrors;
    }
    
}