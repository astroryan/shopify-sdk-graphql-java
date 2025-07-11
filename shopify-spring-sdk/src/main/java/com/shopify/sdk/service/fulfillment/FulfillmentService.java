package com.shopify.sdk.service.fulfillment;

import com.shopify.sdk.auth.ShopifyAuthContext;
import com.shopify.sdk.client.GraphQLClient;
import com.shopify.sdk.model.fulfillment.*;
import com.shopify.sdk.model.order.Fulfillment;
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
public class FulfillmentService {
    
    private final GraphQLClient graphQLClient;
    
    private static final String GET_FULFILLMENT_ORDER_QUERY = """
        query getFulfillmentOrder($id: ID!) {
            fulfillmentOrder(id: $id) {
                id
                assignedLocation {
                    address1
                    address2
                    city
                    countryCode
                    location {
                        id
                        name
                    }
                    name
                    phone
                    province
                    zip
                }
                channelId
                createdAt
                deliveryMethod {
                    id
                    methodType
                }
                destination {
                    id
                    address1
                    address2
                    city
                    company
                    countryCode
                    email
                    firstName
                    lastName
                    phone
                    province
                    zip
                }
                fulfillAt
                fulfillBy
                fulfillmentHolds {
                    reason
                    reasonNotes
                }
                fulfillments(first: 10) {
                    edges {
                        node {
                            id
                            status
                            displayStatus
                            createdAt
                            updatedAt
                            trackingInfo {
                                company
                                number
                                url
                            }
                        }
                    }
                }
                internationalDuties {
                    chargeType
                    incoterm
                }
                lineItems(first: 50) {
                    edges {
                        node {
                            id
                            inventoryItemId
                            lineItem {
                                id
                                title
                                quantity
                                variant {
                                    id
                                    title
                                    sku
                                }
                            }
                            quantity
                        }
                    }
                }
                locationsForMove(first: 10) {
                    location {
                        id
                        name
                    }
                    message
                    movable
                }
                merchantRequests(first: 10) {
                    edges {
                        node {
                            id
                            kind
                            message
                            requestOptions
                            responseData
                            sentAt
                        }
                    }
                }
                order {
                    id
                    name
                }
                requestStatus
                status
                supportedActions
                updatedAt
            }
        }
        """;
    
    private static final String LIST_FULFILLMENT_ORDERS_QUERY = """
        query listFulfillmentOrders($first: Int!, $after: String, $assignmentStatus: FulfillmentOrderAssignmentStatus, $query: String, $sortKey: FulfillmentOrderSortKeys, $reverse: Boolean) {
            fulfillmentOrders(first: $first, after: $after, assignmentStatus: $assignmentStatus, query: $query, sortKey: $sortKey, reverse: $reverse) {
                edges {
                    node {
                        id
                        assignedLocation {
                            name
                            location {
                                id
                                name
                            }
                        }
                        createdAt
                        deliveryMethod {
                            id
                            methodType
                        }
                        destination {
                            firstName
                            lastName
                            city
                            countryCode
                        }
                        order {
                            id
                            name
                        }
                        requestStatus
                        status
                        supportedActions
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
    
    private static final String SUBMIT_FULFILLMENT_REQUEST_MUTATION = """
        mutation fulfillmentOrderSubmitFulfillmentRequest($id: ID!, $message: String, $notifyCustomer: Boolean) {
            fulfillmentOrderSubmitFulfillmentRequest(id: $id, message: $message, notifyCustomer: $notifyCustomer) {
                fulfillmentOrder {
                    id
                    status
                    requestStatus
                }
                userErrors {
                    field
                    message
                }
            }
        }
        """;
    
    private static final String CANCEL_FULFILLMENT_ORDER_MUTATION = """
        mutation fulfillmentOrderCancel($id: ID!) {
            fulfillmentOrderCancel(id: $id) {
                fulfillmentOrder {
                    id
                    status
                    requestStatus
                }
                replacementFulfillmentOrder {
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
    
    private static final String MOVE_FULFILLMENT_ORDER_MUTATION = """
        mutation fulfillmentOrderMove($id: ID!, $newLocationId: ID!, $fulfillmentOrderLineItems: [FulfillmentOrderLineItemInput!]) {
            fulfillmentOrderMove(id: $id, newLocationId: $newLocationId, fulfillmentOrderLineItems: $fulfillmentOrderLineItems) {
                movedFulfillmentOrder {
                    id
                    status
                    assignedLocation {
                        location {
                            id
                            name
                        }
                    }
                }
                originalFulfillmentOrder {
                    id
                    status
                }
                remainingFulfillmentOrder {
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
    
    private static final String SPLIT_FULFILLMENT_ORDER_MUTATION = """
        mutation fulfillmentOrderSplit($fulfillmentOrderSplits: [FulfillmentOrderSplitInput!]!) {
            fulfillmentOrderSplit(fulfillmentOrderSplits: $fulfillmentOrderSplits) {
                splitFulfillmentOrders {
                    id
                    status
                    lineItems(first: 50) {
                        edges {
                            node {
                                id
                                quantity
                            }
                        }
                    }
                }
                userErrors {
                    field
                    message
                }
            }
        }
        """;
    
    private static final String HOLD_FULFILLMENT_ORDER_MUTATION = """
        mutation fulfillmentOrderHold($fulfillmentHold: FulfillmentOrderHoldInput!) {
            fulfillmentOrderHold(fulfillmentHold: $fulfillmentHold) {
                fulfillmentOrder {
                    id
                    status
                    fulfillmentHolds {
                        reason
                        reasonNotes
                    }
                }
                userErrors {
                    field
                    message
                }
            }
        }
        """;
    
    private static final String RELEASE_HOLD_MUTATION = """
        mutation fulfillmentOrderReleaseHold($id: ID!) {
            fulfillmentOrderReleaseHold(id: $id) {
                fulfillmentOrder {
                    id
                    status
                    fulfillmentHolds {
                        reason
                        reasonNotes
                    }
                }
                userErrors {
                    field
                    message
                }
            }
        }
        """;
    
    private static final String RESCHEDULE_FULFILLMENT_ORDER_MUTATION = """
        mutation fulfillmentOrderReschedule($fulfillAt: DateTime!, $id: ID!) {
            fulfillmentOrderReschedule(fulfillAt: $fulfillAt, id: $id) {
                fulfillmentOrder {
                    id
                    fulfillAt
                    status
                }
                userErrors {
                    field
                    message
                }
            }
        }
        """;
    
    private static final String CREATE_FULFILLMENT_FROM_ORDER_MUTATION = """
        mutation fulfillmentCreateV2($fulfillment: FulfillmentV2Input!) {
            fulfillmentCreateV2(fulfillment: $fulfillment) {
                fulfillment {
                    id
                    status
                    displayStatus
                    createdAt
                    updatedAt
                    trackingInfo {
                        company
                        number
                        url
                    }
                    fulfillmentLineItems(first: 100) {
                        edges {
                            node {
                                id
                                quantity
                                lineItem {
                                    id
                                    title
                                }
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
    
    public FulfillmentOrder getFulfillmentOrder(ShopifyAuthContext context, String fulfillmentOrderId) {
        Map<String, Object> variables = new HashMap<>();
        variables.put("id", fulfillmentOrderId);
        
        GraphQLRequest request = GraphQLRequest.builder()
                .query(GET_FULFILLMENT_ORDER_QUERY)
                .variables(variables)
                .build();
        
        GraphQLResponse<FulfillmentOrderData> response = graphQLClient.execute(
                request,
                FulfillmentOrderData.class
        );
        
        if (response.hasErrors()) {
            log.error("Failed to get fulfillment order: {}", response.getErrors());
            throw new RuntimeException("Failed to get fulfillment order");
        }
        
        return response.getData().getFulfillmentOrder();
    }
    
    public FulfillmentOrderConnection listFulfillmentOrders(
            ShopifyAuthContext context,
            int first,
            String after,
            String assignmentStatus,
            String query,
            String sortKey,
            Boolean reverse) {
        
        Map<String, Object> variables = new HashMap<>();
        variables.put("first", first);
        if (after != null) variables.put("after", after);
        if (assignmentStatus != null) variables.put("assignmentStatus", assignmentStatus);
        if (query != null) variables.put("query", query);
        if (sortKey != null) variables.put("sortKey", sortKey);
        if (reverse != null) variables.put("reverse", reverse);
        
        GraphQLRequest request = GraphQLRequest.builder()
                .query(LIST_FULFILLMENT_ORDERS_QUERY)
                .variables(variables)
                .build();
        
        GraphQLResponse<FulfillmentOrdersData> response = graphQLClient.execute(
                request,
                FulfillmentOrdersData.class
        );
        
        if (response.hasErrors()) {
            log.error("Failed to list fulfillment orders: {}", response.getErrors());
            throw new RuntimeException("Failed to list fulfillment orders");
        }
        
        return response.getData().getFulfillmentOrders();
    }
    
    public FulfillmentOrder submitFulfillmentRequest(
            ShopifyAuthContext context,
            String fulfillmentOrderId,
            String message,
            Boolean notifyCustomer) {
        
        Map<String, Object> variables = new HashMap<>();
        variables.put("id", fulfillmentOrderId);
        if (message != null) variables.put("message", message);
        if (notifyCustomer != null) variables.put("notifyCustomer", notifyCustomer);
        
        GraphQLRequest request = GraphQLRequest.builder()
                .query(SUBMIT_FULFILLMENT_REQUEST_MUTATION)
                .variables(variables)
                .build();
        
        GraphQLResponse<FulfillmentOrderSubmitRequestData> response = graphQLClient.execute(
                request,
                FulfillmentOrderSubmitRequestData.class
        );
        
        if (response.hasErrors()) {
            log.error("Failed to submit fulfillment request: {}", response.getErrors());
            throw new RuntimeException("Failed to submit fulfillment request");
        }
        
        FulfillmentOrderSubmitRequestResponse submitResponse = response.getData().getFulfillmentOrderSubmitFulfillmentRequest();
        if (submitResponse.getUserErrors() != null && !submitResponse.getUserErrors().isEmpty()) {
            log.error("User errors submitting fulfillment request: {}", submitResponse.getUserErrors());
            throw new RuntimeException("Failed to submit fulfillment request: " + submitResponse.getUserErrors());
        }
        
        return submitResponse.getFulfillmentOrder();
    }
    
    public FulfillmentOrderCancelResponse cancelFulfillmentOrder(ShopifyAuthContext context, String fulfillmentOrderId) {
        Map<String, Object> variables = new HashMap<>();
        variables.put("id", fulfillmentOrderId);
        
        GraphQLRequest request = GraphQLRequest.builder()
                .query(CANCEL_FULFILLMENT_ORDER_MUTATION)
                .variables(variables)
                .build();
        
        GraphQLResponse<FulfillmentOrderCancelData> response = graphQLClient.execute(
                request,
                FulfillmentOrderCancelData.class
        );
        
        if (response.hasErrors()) {
            log.error("Failed to cancel fulfillment order: {}", response.getErrors());
            throw new RuntimeException("Failed to cancel fulfillment order");
        }
        
        FulfillmentOrderCancelResponse cancelResponse = response.getData().getFulfillmentOrderCancel();
        if (cancelResponse.getUserErrors() != null && !cancelResponse.getUserErrors().isEmpty()) {
            log.error("User errors canceling fulfillment order: {}", cancelResponse.getUserErrors());
            throw new RuntimeException("Failed to cancel fulfillment order: " + cancelResponse.getUserErrors());
        }
        
        return cancelResponse;
    }
    
    public FulfillmentOrderMoveResponse moveFulfillmentOrder(
            ShopifyAuthContext context,
            String fulfillmentOrderId,
            String newLocationId,
            List<FulfillmentOrderLineItemInput> lineItems) {
        
        Map<String, Object> variables = new HashMap<>();
        variables.put("id", fulfillmentOrderId);
        variables.put("newLocationId", newLocationId);
        if (lineItems != null) variables.put("fulfillmentOrderLineItems", lineItems);
        
        GraphQLRequest request = GraphQLRequest.builder()
                .query(MOVE_FULFILLMENT_ORDER_MUTATION)
                .variables(variables)
                .build();
        
        GraphQLResponse<FulfillmentOrderMoveData> response = graphQLClient.execute(
                request,
                FulfillmentOrderMoveData.class
        );
        
        if (response.hasErrors()) {
            log.error("Failed to move fulfillment order: {}", response.getErrors());
            throw new RuntimeException("Failed to move fulfillment order");
        }
        
        FulfillmentOrderMoveResponse moveResponse = response.getData().getFulfillmentOrderMove();
        if (moveResponse.getUserErrors() != null && !moveResponse.getUserErrors().isEmpty()) {
            log.error("User errors moving fulfillment order: {}", moveResponse.getUserErrors());
            throw new RuntimeException("Failed to move fulfillment order: " + moveResponse.getUserErrors());
        }
        
        return moveResponse;
    }
    
    public List<FulfillmentOrder> splitFulfillmentOrder(ShopifyAuthContext context, List<FulfillmentOrderSplitInput> splits) {
        Map<String, Object> variables = new HashMap<>();
        variables.put("fulfillmentOrderSplits", splits);
        
        GraphQLRequest request = GraphQLRequest.builder()
                .query(SPLIT_FULFILLMENT_ORDER_MUTATION)
                .variables(variables)
                .build();
        
        GraphQLResponse<FulfillmentOrderSplitData> response = graphQLClient.execute(
                request,
                FulfillmentOrderSplitData.class
        );
        
        if (response.hasErrors()) {
            log.error("Failed to split fulfillment order: {}", response.getErrors());
            throw new RuntimeException("Failed to split fulfillment order");
        }
        
        FulfillmentOrderSplitResponse splitResponse = response.getData().getFulfillmentOrderSplit();
        if (splitResponse.getUserErrors() != null && !splitResponse.getUserErrors().isEmpty()) {
            log.error("User errors splitting fulfillment order: {}", splitResponse.getUserErrors());
            throw new RuntimeException("Failed to split fulfillment order: " + splitResponse.getUserErrors());
        }
        
        return splitResponse.getSplitFulfillmentOrders();
    }
    
    public FulfillmentOrder holdFulfillmentOrder(ShopifyAuthContext context, FulfillmentOrderHoldInput holdInput) {
        Map<String, Object> variables = new HashMap<>();
        variables.put("fulfillmentHold", holdInput);
        
        GraphQLRequest request = GraphQLRequest.builder()
                .query(HOLD_FULFILLMENT_ORDER_MUTATION)
                .variables(variables)
                .build();
        
        GraphQLResponse<FulfillmentOrderHoldData> response = graphQLClient.execute(
                request,
                FulfillmentOrderHoldData.class
        );
        
        if (response.hasErrors()) {
            log.error("Failed to hold fulfillment order: {}", response.getErrors());
            throw new RuntimeException("Failed to hold fulfillment order");
        }
        
        FulfillmentOrderHoldResponse holdResponse = response.getData().getFulfillmentOrderHold();
        if (holdResponse.getUserErrors() != null && !holdResponse.getUserErrors().isEmpty()) {
            log.error("User errors holding fulfillment order: {}", holdResponse.getUserErrors());
            throw new RuntimeException("Failed to hold fulfillment order: " + holdResponse.getUserErrors());
        }
        
        return holdResponse.getFulfillmentOrder();
    }
    
    public FulfillmentOrder releaseHold(ShopifyAuthContext context, String fulfillmentOrderId) {
        Map<String, Object> variables = new HashMap<>();
        variables.put("id", fulfillmentOrderId);
        
        GraphQLRequest request = GraphQLRequest.builder()
                .query(RELEASE_HOLD_MUTATION)
                .variables(variables)
                .build();
        
        GraphQLResponse<FulfillmentOrderReleaseHoldData> response = graphQLClient.execute(
                request,
                FulfillmentOrderReleaseHoldData.class
        );
        
        if (response.hasErrors()) {
            log.error("Failed to release hold: {}", response.getErrors());
            throw new RuntimeException("Failed to release hold");
        }
        
        FulfillmentOrderReleaseHoldResponse releaseResponse = response.getData().getFulfillmentOrderReleaseHold();
        if (releaseResponse.getUserErrors() != null && !releaseResponse.getUserErrors().isEmpty()) {
            log.error("User errors releasing hold: {}", releaseResponse.getUserErrors());
            throw new RuntimeException("Failed to release hold: " + releaseResponse.getUserErrors());
        }
        
        return releaseResponse.getFulfillmentOrder();
    }
    
    public FulfillmentOrder rescheduleFulfillmentOrder(ShopifyAuthContext context, String fulfillmentOrderId, String fulfillAt) {
        Map<String, Object> variables = new HashMap<>();
        variables.put("id", fulfillmentOrderId);
        variables.put("fulfillAt", fulfillAt);
        
        GraphQLRequest request = GraphQLRequest.builder()
                .query(RESCHEDULE_FULFILLMENT_ORDER_MUTATION)
                .variables(variables)
                .build();
        
        GraphQLResponse<FulfillmentOrderRescheduleData> response = graphQLClient.execute(
                request,
                FulfillmentOrderRescheduleData.class
        );
        
        if (response.hasErrors()) {
            log.error("Failed to reschedule fulfillment order: {}", response.getErrors());
            throw new RuntimeException("Failed to reschedule fulfillment order");
        }
        
        FulfillmentOrderRescheduleResponse rescheduleResponse = response.getData().getFulfillmentOrderReschedule();
        if (rescheduleResponse.getUserErrors() != null && !rescheduleResponse.getUserErrors().isEmpty()) {
            log.error("User errors rescheduling fulfillment order: {}", rescheduleResponse.getUserErrors());
            throw new RuntimeException("Failed to reschedule fulfillment order: " + rescheduleResponse.getUserErrors());
        }
        
        return rescheduleResponse.getFulfillmentOrder();
    }
    
    public Fulfillment createFulfillmentFromOrder(ShopifyAuthContext context, FulfillmentV2Input input) {
        Map<String, Object> variables = new HashMap<>();
        variables.put("fulfillment", input);
        
        GraphQLRequest request = GraphQLRequest.builder()
                .query(CREATE_FULFILLMENT_FROM_ORDER_MUTATION)
                .variables(variables)
                .build();
        
        GraphQLResponse<FulfillmentCreateV2Data> response = graphQLClient.execute(
                request,
                FulfillmentCreateV2Data.class
        );
        
        if (response.hasErrors()) {
            log.error("Failed to create fulfillment: {}", response.getErrors());
            throw new RuntimeException("Failed to create fulfillment");
        }
        
        FulfillmentCreateV2Response createResponse = response.getData().getFulfillmentCreateV2();
        if (createResponse.getUserErrors() != null && !createResponse.getUserErrors().isEmpty()) {
            log.error("User errors creating fulfillment: {}", createResponse.getUserErrors());
            throw new RuntimeException("Failed to create fulfillment: " + createResponse.getUserErrors());
        }
        
        return createResponse.getFulfillment();
    }
    
    @Data
    private static class FulfillmentOrderData {
        private FulfillmentOrder fulfillmentOrder;
    }
    
    @Data
    private static class FulfillmentOrdersData {
        private FulfillmentOrderConnection fulfillmentOrders;
    }
    
    @Data
    private static class FulfillmentOrderSubmitRequestData {
        private FulfillmentOrderSubmitRequestResponse fulfillmentOrderSubmitFulfillmentRequest;
    }
    
    @Data
    private static class FulfillmentOrderCancelData {
        private FulfillmentOrderCancelResponse fulfillmentOrderCancel;
    }
    
    @Data
    private static class FulfillmentOrderMoveData {
        private FulfillmentOrderMoveResponse fulfillmentOrderMove;
    }
    
    @Data
    private static class FulfillmentOrderSplitData {
        private FulfillmentOrderSplitResponse fulfillmentOrderSplit;
    }
    
    @Data
    private static class FulfillmentOrderHoldData {
        private FulfillmentOrderHoldResponse fulfillmentOrderHold;
    }
    
    @Data
    private static class FulfillmentOrderReleaseHoldData {
        private FulfillmentOrderReleaseHoldResponse fulfillmentOrderReleaseHold;
    }
    
    @Data
    private static class FulfillmentOrderRescheduleData {
        private FulfillmentOrderRescheduleResponse fulfillmentOrderReschedule;
    }
    
    @Data
    private static class FulfillmentCreateV2Data {
        private FulfillmentCreateV2Response fulfillmentCreateV2;
    }
    
    @Data
    public static class FulfillmentOrderSubmitRequestResponse {
        private FulfillmentOrder fulfillmentOrder;
        private List<UserError> userErrors;
    }
    
    @Data
    public static class FulfillmentOrderCancelResponse {
        private FulfillmentOrder fulfillmentOrder;
        private FulfillmentOrder replacementFulfillmentOrder;
        private List<UserError> userErrors;
    }
    
    @Data
    public static class FulfillmentOrderMoveResponse {
        private FulfillmentOrder movedFulfillmentOrder;
        private FulfillmentOrder originalFulfillmentOrder;
        private FulfillmentOrder remainingFulfillmentOrder;
        private List<UserError> userErrors;
    }
    
    @Data
    public static class FulfillmentOrderSplitResponse {
        private List<FulfillmentOrder> splitFulfillmentOrders;
        private List<UserError> userErrors;
    }
    
    @Data
    public static class FulfillmentOrderHoldResponse {
        private FulfillmentOrder fulfillmentOrder;
        private List<UserError> userErrors;
    }
    
    @Data
    public static class FulfillmentOrderReleaseHoldResponse {
        private FulfillmentOrder fulfillmentOrder;
        private List<UserError> userErrors;
    }
    
    @Data
    public static class FulfillmentOrderRescheduleResponse {
        private FulfillmentOrder fulfillmentOrder;
        private List<UserError> userErrors;
    }
    
    @Data
    public static class FulfillmentCreateV2Response {
        private Fulfillment fulfillment;
        private List<UserError> userErrors;
    }
    
    @Data
    public static class UserError {
        private List<String> field;
        private String message;
        private String code;
    }
    
    @Data
    public static class PageInfo {
        private boolean hasNextPage;
        private boolean hasPreviousPage;
        private String startCursor;
        private String endCursor;
    }
}