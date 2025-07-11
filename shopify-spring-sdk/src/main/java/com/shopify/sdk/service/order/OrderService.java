package com.shopify.sdk.service.order;

import com.shopify.sdk.auth.ShopifyAuthContext;
import com.shopify.sdk.client.GraphQLClient;
import com.shopify.sdk.model.order.Order;
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
public class OrderService {
    
    private final GraphQLClient graphQLClient;
    
    private static final String GET_ORDER_QUERY = """
        query getOrder($id: ID!) {
            order(id: $id) {
                id
                name
                email
                phone
                createdAt
                updatedAt
                processedAt
                closedAt
                cancelledAt
                cancelReason
                displayFinancialStatus
                displayFulfillmentStatus
                returnStatus
                currencyCode
                currentSubtotalPriceSet {
                    presentmentMoney {
                        amount
                        currencyCode
                    }
                    shopMoney {
                        amount
                        currencyCode
                    }
                }
                currentTotalPriceSet {
                    presentmentMoney {
                        amount
                        currencyCode
                    }
                    shopMoney {
                        amount
                        currencyCode
                    }
                }
                currentTotalTaxSet {
                    presentmentMoney {
                        amount
                        currencyCode
                    }
                    shopMoney {
                        amount
                        currencyCode
                    }
                }
                customer {
                    id
                    email
                    firstName
                    lastName
                    phone
                }
                lineItems(first: 100) {
                    edges {
                        node {
                            id
                            title
                            quantity
                            variant {
                                id
                                title
                                sku
                                price {
                                    amount
                                    currencyCode
                                }
                            }
                            originalTotalSet {
                                presentmentMoney {
                                    amount
                                    currencyCode
                                }
                                shopMoney {
                                    amount
                                    currencyCode
                                }
                            }
                            discountedTotalSet {
                                presentmentMoney {
                                    amount
                                    currencyCode
                                }
                                shopMoney {
                                    amount
                                    currencyCode
                                }
                            }
                        }
                    }
                    pageInfo {
                        hasNextPage
                        hasPreviousPage
                    }
                }
                shippingAddress {
                    formatted
                    firstName
                    lastName
                    company
                    address1
                    address2
                    city
                    country
                    countryCodeV2
                    phone
                    province
                    provinceCode
                    zip
                }
                billingAddress {
                    formatted
                    firstName
                    lastName
                    company
                    address1
                    address2
                    city
                    country
                    countryCodeV2
                    phone
                    province
                    provinceCode
                    zip
                }
                note
                tags
                test
                confirmed
                fullyPaid
                unpaid
                riskLevel
                sourceIdentifier
                sourceName
                taxesIncluded
            }
        }
        """;
    
    private static final String LIST_ORDERS_QUERY = """
        query listOrders($first: Int!, $after: String, $query: String) {
            orders(first: $first, after: $after, query: $query) {
                edges {
                    node {
                        id
                        name
                        email
                        createdAt
                        updatedAt
                        displayFinancialStatus
                        displayFulfillmentStatus
                        currencyCode
                        currentTotalPriceSet {
                            presentmentMoney {
                                amount
                                currencyCode
                            }
                            shopMoney {
                                amount
                                currencyCode
                            }
                        }
                        customer {
                            id
                            email
                            firstName
                            lastName
                        }
                        totalWeight
                        tags
                        test
                        confirmed
                        fullyPaid
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
    
    private static final String UPDATE_ORDER_MUTATION = """
        mutation orderUpdate($input: OrderInput!) {
            orderUpdate(input: $input) {
                order {
                    id
                    name
                    email
                    phone
                    note
                    tags
                    updatedAt
                }
                userErrors {
                    field
                    message
                }
            }
        }
        """;
    
    private static final String CANCEL_ORDER_MUTATION = """
        mutation orderCancel($orderId: ID!, $reason: OrderCancelReason!, $notifyCustomer: Boolean, $refund: Boolean, $restock: Boolean, $staffNote: String) {
            orderCancel(
                orderId: $orderId,
                reason: $reason,
                notifyCustomer: $notifyCustomer,
                refund: $refund,
                restock: $restock,
                staffNote: $staffNote
            ) {
                job {
                    id
                    done
                }
                orderCancelUserErrors {
                    field
                    message
                    code
                }
            }
        }
        """;
    
    private static final String CAPTURE_PAYMENT_MUTATION = """
        mutation orderCapture($input: OrderCaptureInput!) {
            orderCapture(input: $input) {
                transaction {
                    id
                    status
                    kind
                    amountSet {
                        presentmentMoney {
                            amount
                            currencyCode
                        }
                        shopMoney {
                            amount
                            currencyCode
                        }
                    }
                    createdAt
                    processedAt
                }
                userErrors {
                    field
                    message
                }
            }
        }
        """;
    
    private static final String CREATE_FULFILLMENT_MUTATION = """
        mutation fulfillmentCreate($fulfillment: FulfillmentInput!) {
            fulfillmentCreate(fulfillment: $fulfillment) {
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
                    fulfillmentOrders(first: 100) {
                        edges {
                            node {
                                id
                                status
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
    
    private static final String CANCEL_FULFILLMENT_MUTATION = """
        mutation fulfillmentCancel($id: ID!) {
            fulfillmentCancel(id: $id) {
                fulfillment {
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
    
    private static final String CREATE_REFUND_MUTATION = """
        mutation refundCreate($input: RefundInput!) {
            refundCreate(input: $input) {
                refund {
                    id
                    createdAt
                    note
                    totalRefundedSet {
                        presentmentMoney {
                            amount
                            currencyCode
                        }
                        shopMoney {
                            amount
                            currencyCode
                        }
                    }
                    refundLineItems(first: 100) {
                        edges {
                            node {
                                id
                                quantity
                                subtotalSet {
                                    presentmentMoney {
                                        amount
                                        currencyCode
                                    }
                                    shopMoney {
                                        amount
                                        currencyCode
                                    }
                                }
                                lineItem {
                                    id
                                    title
                                }
                            }
                        }
                    }
                    transactions(first: 10) {
                        id
                        status
                        kind
                        amountSet {
                            presentmentMoney {
                                amount
                                currencyCode
                            }
                            shopMoney {
                                amount
                                currencyCode
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
    
    private static final String CLOSE_ORDER_MUTATION = """
        mutation orderClose($input: OrderCloseInput!) {
            orderClose(input: $input) {
                order {
                    id
                    closedAt
                    displayFinancialStatus
                    displayFulfillmentStatus
                }
                userErrors {
                    field
                    message
                }
            }
        }
        """;
    
    private static final String OPEN_ORDER_MUTATION = """
        mutation orderOpen($input: OrderOpenInput!) {
            orderOpen(input: $input) {
                order {
                    id
                    closedAt
                    displayFinancialStatus
                    displayFulfillmentStatus
                }
                userErrors {
                    field
                    message
                }
            }
        }
        """;
    
    public Order getOrder(ShopifyAuthContext context, String orderId) {
        Map<String, Object> variables = new HashMap<>();
        variables.put("id", orderId);
        
        GraphQLRequest request = GraphQLRequest.builder()
                .query(GET_ORDER_QUERY)
                .variables(variables)
                .build();
        
        GraphQLResponse<OrderData> response = graphQLClient.execute(
                request,
                OrderData.class
        );
        
        if (response.hasErrors()) {
            log.error("Failed to get order: {}", response.getErrors());
            throw new RuntimeException("Failed to get order");
        }
        
        return response.getData().getOrder();
    }
    
    public OrderConnection listOrders(ShopifyAuthContext context, int first, String after, String query) {
        Map<String, Object> variables = new HashMap<>();
        variables.put("first", first);
        if (after != null) {
            variables.put("after", after);
        }
        if (query != null) {
            variables.put("query", query);
        }
        
        GraphQLRequest request = GraphQLRequest.builder()
                .query(LIST_ORDERS_QUERY)
                .variables(variables)
                .build();
        
        GraphQLResponse<OrdersData> response = graphQLClient.execute(
                request,
                OrdersData.class
        );
        
        if (response.hasErrors()) {
            log.error("Failed to list orders: {}", response.getErrors());
            throw new RuntimeException("Failed to list orders");
        }
        
        return response.getData().getOrders();
    }
    
    public Order updateOrder(ShopifyAuthContext context, OrderInput input) {
        Map<String, Object> variables = new HashMap<>();
        variables.put("input", input);
        
        GraphQLRequest request = GraphQLRequest.builder()
                .query(UPDATE_ORDER_MUTATION)
                .variables(variables)
                .build();
        
        GraphQLResponse<OrderUpdateData> response = graphQLClient.execute(
                request,
                OrderUpdateData.class
        );
        
        if (response.hasErrors()) {
            log.error("Failed to update order: {}", response.getErrors());
            throw new RuntimeException("Failed to update order");
        }
        
        OrderUpdateResponse updateResponse = response.getData().getOrderUpdate();
        if (updateResponse.getUserErrors() != null && !updateResponse.getUserErrors().isEmpty()) {
            log.error("User errors updating order: {}", updateResponse.getUserErrors());
            throw new RuntimeException("Failed to update order: " + updateResponse.getUserErrors());
        }
        
        return updateResponse.getOrder();
    }
    
    public Job cancelOrder(
            ShopifyAuthContext context,
            String orderId,
            String reason,
            Boolean notifyCustomer,
            Boolean refund,
            Boolean restock,
            String staffNote) {
        
        Map<String, Object> variables = new HashMap<>();
        variables.put("orderId", orderId);
        variables.put("reason", reason);
        if (notifyCustomer != null) variables.put("notifyCustomer", notifyCustomer);
        if (refund != null) variables.put("refund", refund);
        if (restock != null) variables.put("restock", restock);
        if (staffNote != null) variables.put("staffNote", staffNote);
        
        GraphQLRequest request = GraphQLRequest.builder()
                .query(CANCEL_ORDER_MUTATION)
                .variables(variables)
                .build();
        
        GraphQLResponse<OrderCancelData> response = graphQLClient.execute(
                request,
                OrderCancelData.class
        );
        
        if (response.hasErrors()) {
            log.error("Failed to cancel order: {}", response.getErrors());
            throw new RuntimeException("Failed to cancel order");
        }
        
        OrderCancelResponse cancelResponse = response.getData().getOrderCancel();
        if (cancelResponse.getOrderCancelUserErrors() != null && !cancelResponse.getOrderCancelUserErrors().isEmpty()) {
            log.error("User errors canceling order: {}", cancelResponse.getOrderCancelUserErrors());
            throw new RuntimeException("Failed to cancel order: " + cancelResponse.getOrderCancelUserErrors());
        }
        
        return cancelResponse.getJob();
    }
    
    public OrderTransaction capturePayment(ShopifyAuthContext context, OrderCaptureInput input) {
        Map<String, Object> variables = new HashMap<>();
        variables.put("input", input);
        
        GraphQLRequest request = GraphQLRequest.builder()
                .query(CAPTURE_PAYMENT_MUTATION)
                .variables(variables)
                .build();
        
        GraphQLResponse<OrderCaptureData> response = graphQLClient.execute(
                request,
                OrderCaptureData.class
        );
        
        if (response.hasErrors()) {
            log.error("Failed to capture payment: {}", response.getErrors());
            throw new RuntimeException("Failed to capture payment");
        }
        
        OrderCaptureResponse captureResponse = response.getData().getOrderCapture();
        if (captureResponse.getUserErrors() != null && !captureResponse.getUserErrors().isEmpty()) {
            log.error("User errors capturing payment: {}", captureResponse.getUserErrors());
            throw new RuntimeException("Failed to capture payment: " + captureResponse.getUserErrors());
        }
        
        return captureResponse.getTransaction();
    }
    
    public Fulfillment createFulfillment(ShopifyAuthContext context, FulfillmentInput input) {
        Map<String, Object> variables = new HashMap<>();
        variables.put("fulfillment", input);
        
        GraphQLRequest request = GraphQLRequest.builder()
                .query(CREATE_FULFILLMENT_MUTATION)
                .variables(variables)
                .build();
        
        GraphQLResponse<FulfillmentCreateData> response = graphQLClient.execute(
                request,
                FulfillmentCreateData.class
        );
        
        if (response.hasErrors()) {
            log.error("Failed to create fulfillment: {}", response.getErrors());
            throw new RuntimeException("Failed to create fulfillment");
        }
        
        FulfillmentCreateResponse createResponse = response.getData().getFulfillmentCreate();
        if (createResponse.getUserErrors() != null && !createResponse.getUserErrors().isEmpty()) {
            log.error("User errors creating fulfillment: {}", createResponse.getUserErrors());
            throw new RuntimeException("Failed to create fulfillment: " + createResponse.getUserErrors());
        }
        
        return createResponse.getFulfillment();
    }
    
    public Fulfillment cancelFulfillment(ShopifyAuthContext context, String fulfillmentId) {
        Map<String, Object> variables = new HashMap<>();
        variables.put("id", fulfillmentId);
        
        GraphQLRequest request = GraphQLRequest.builder()
                .query(CANCEL_FULFILLMENT_MUTATION)
                .variables(variables)
                .build();
        
        GraphQLResponse<FulfillmentCancelData> response = graphQLClient.execute(
                request,
                FulfillmentCancelData.class
        );
        
        if (response.hasErrors()) {
            log.error("Failed to cancel fulfillment: {}", response.getErrors());
            throw new RuntimeException("Failed to cancel fulfillment");
        }
        
        FulfillmentCancelResponse cancelResponse = response.getData().getFulfillmentCancel();
        if (cancelResponse.getUserErrors() != null && !cancelResponse.getUserErrors().isEmpty()) {
            log.error("User errors canceling fulfillment: {}", cancelResponse.getUserErrors());
            throw new RuntimeException("Failed to cancel fulfillment: " + cancelResponse.getUserErrors());
        }
        
        return cancelResponse.getFulfillment();
    }
    
    public Refund createRefund(ShopifyAuthContext context, RefundInput input) {
        Map<String, Object> variables = new HashMap<>();
        variables.put("input", input);
        
        GraphQLRequest request = GraphQLRequest.builder()
                .query(CREATE_REFUND_MUTATION)
                .variables(variables)
                .build();
        
        GraphQLResponse<RefundCreateData> response = graphQLClient.execute(
                request,
                RefundCreateData.class
        );
        
        if (response.hasErrors()) {
            log.error("Failed to create refund: {}", response.getErrors());
            throw new RuntimeException("Failed to create refund");
        }
        
        RefundCreateResponse createResponse = response.getData().getRefundCreate();
        if (createResponse.getUserErrors() != null && !createResponse.getUserErrors().isEmpty()) {
            log.error("User errors creating refund: {}", createResponse.getUserErrors());
            throw new RuntimeException("Failed to create refund: " + createResponse.getUserErrors());
        }
        
        return createResponse.getRefund();
    }
    
    public Order closeOrder(ShopifyAuthContext context, OrderCloseInput input) {
        Map<String, Object> variables = new HashMap<>();
        variables.put("input", input);
        
        GraphQLRequest request = GraphQLRequest.builder()
                .query(CLOSE_ORDER_MUTATION)
                .variables(variables)
                .build();
        
        GraphQLResponse<OrderCloseData> response = graphQLClient.execute(
                request,
                OrderCloseData.class
        );
        
        if (response.hasErrors()) {
            log.error("Failed to close order: {}", response.getErrors());
            throw new RuntimeException("Failed to close order");
        }
        
        OrderCloseResponse closeResponse = response.getData().getOrderClose();
        if (closeResponse.getUserErrors() != null && !closeResponse.getUserErrors().isEmpty()) {
            log.error("User errors closing order: {}", closeResponse.getUserErrors());
            throw new RuntimeException("Failed to close order: " + closeResponse.getUserErrors());
        }
        
        return closeResponse.getOrder();
    }
    
    public Order openOrder(ShopifyAuthContext context, OrderOpenInput input) {
        Map<String, Object> variables = new HashMap<>();
        variables.put("input", input);
        
        GraphQLRequest request = GraphQLRequest.builder()
                .query(OPEN_ORDER_MUTATION)
                .variables(variables)
                .build();
        
        GraphQLResponse<OrderOpenData> response = graphQLClient.execute(
                request,
                OrderOpenData.class
        );
        
        if (response.hasErrors()) {
            log.error("Failed to open order: {}", response.getErrors());
            throw new RuntimeException("Failed to open order");
        }
        
        OrderOpenResponse openResponse = response.getData().getOrderOpen();
        if (openResponse.getUserErrors() != null && !openResponse.getUserErrors().isEmpty()) {
            log.error("User errors opening order: {}", openResponse.getUserErrors());
            throw new RuntimeException("Failed to open order: " + openResponse.getUserErrors());
        }
        
        return openResponse.getOrder();
    }
    
    @Data
    private static class OrderData {
        private Order order;
    }
    
    @Data
    private static class OrdersData {
        private OrderConnection orders;
    }
    
    @Data
    private static class OrderUpdateData {
        private OrderUpdateResponse orderUpdate;
    }
    
    @Data
    private static class OrderCancelData {
        private OrderCancelResponse orderCancel;
    }
    
    @Data
    public static class OrderUpdateResponse {
        private Order order;
        private List<UserError> userErrors;
    }
    
    @Data
    public static class OrderCancelResponse {
        private Job job;
        private List<OrderCancelUserError> orderCancelUserErrors;
    }
    
    @Data
    public static class UserError {
        private List<String> field;
        private String message;
    }
    
    @Data
    public static class OrderCancelUserError {
        private List<String> field;
        private String message;
        private String code;
    }
    
    @Data
    public static class OrderInput {
        private String id;
        private String email;
        private String phone;
        private String note;
        private List<String> tags;
        private ShippingAddressInput shippingAddress;
        private List<MetafieldInput> metafields;
        private List<AttributeInput> customAttributes;
    }
    
    @Data
    public static class Job {
        private String id;
        private Boolean done;
    }
    
    @Data
    public static class OrderConnection {
        private List<OrderEdge> edges;
        private PageInfo pageInfo;
    }
    
    @Data
    public static class OrderEdge {
        private Order node;
        private String cursor;
    }
    
    @Data
    public static class PageInfo {
        private boolean hasNextPage;
        private boolean hasPreviousPage;
        private String startCursor;
        private String endCursor;
    }
    
    @Data
    public static class ShippingAddressInput {
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
    public static class AttributeInput {
        private String key;
        private String value;
    }
    
    @Data
    private static class OrderCaptureData {
        private OrderCaptureResponse orderCapture;
    }
    
    @Data
    public static class OrderCaptureResponse {
        private OrderTransaction transaction;
        private List<UserError> userErrors;
    }
    
    @Data
    private static class FulfillmentCreateData {
        private FulfillmentCreateResponse fulfillmentCreate;
    }
    
    @Data
    public static class FulfillmentCreateResponse {
        private Fulfillment fulfillment;
        private List<UserError> userErrors;
    }
    
    @Data
    private static class FulfillmentCancelData {
        private FulfillmentCancelResponse fulfillmentCancel;
    }
    
    @Data
    public static class FulfillmentCancelResponse {
        private Fulfillment fulfillment;
        private List<UserError> userErrors;
    }
    
    @Data
    private static class RefundCreateData {
        private RefundCreateResponse refundCreate;
    }
    
    @Data
    public static class RefundCreateResponse {
        private Refund refund;
        private List<UserError> userErrors;
    }
    
    @Data
    private static class OrderCloseData {
        private OrderCloseResponse orderClose;
    }
    
    @Data
    public static class OrderCloseResponse {
        private Order order;
        private List<UserError> userErrors;
    }
    
    @Data
    private static class OrderOpenData {
        private OrderOpenResponse orderOpen;
    }
    
    @Data
    public static class OrderOpenResponse {
        private Order order;
        private List<UserError> userErrors;
    }
    
    @Data
    public static class OrderCaptureInput {
        private String id;
        private String amount;
        private String currency;
        private String parentTransactionId;
    }
    
    @Data
    public static class FulfillmentInput {
        private List<FulfillmentLineItemInput> lineItemsByFulfillmentOrder;
        private Boolean notifyCustomer;
        private String trackingCompany;
        private List<String> trackingNumbers;
        private List<String> trackingUrls;
    }
    
    @Data
    public static class FulfillmentLineItemInput {
        private String fulfillmentOrderId;
        private List<FulfillmentOrderLineItemInput> fulfillmentOrderLineItems;
    }
    
    @Data
    public static class FulfillmentOrderLineItemInput {
        private String id;
        private Integer quantity;
    }
    
    @Data
    public static class RefundInput {
        private String orderId;
        private String currency;
        private Boolean notify;
        private String note;
        private List<RefundLineItemInput> refundLineItems;
        private RefundShippingInput shipping;
        private List<OrderTransactionInput> transactions;
    }
    
    @Data
    public static class RefundLineItemInput {
        private String lineItemId;
        private String locationId;
        private Integer quantity;
        private RefundLineItemRestockType restockType;
    }
    
    @Data
    public static class RefundShippingInput {
        private String amount;
        private Boolean fullRefund;
    }
    
    @Data
    public static class OrderTransactionInput {
        private String amount;
        private String gateway;
        private TransactionKind kind;
        private String orderId;
        private String parentId;
    }
    
    @Data
    public static class OrderCloseInput {
        private String id;
    }
    
    @Data
    public static class OrderOpenInput {
        private String id;
    }
}