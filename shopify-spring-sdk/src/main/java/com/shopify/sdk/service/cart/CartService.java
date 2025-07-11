package com.shopify.sdk.service.cart;

import com.shopify.sdk.auth.ShopifyAuthContext;
import com.shopify.sdk.client.GraphQLClient;
import com.shopify.sdk.model.cart.Cart;
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
public class CartService {
    
    private final GraphQLClient graphQLClient;
    
    private static final String CREATE_CART_MUTATION = """
        mutation cartCreate($input: CartInput!) {
            cartCreate(input: $input) {
                cart {
                    id
                    checkoutUrl
                    createdAt
                    updatedAt
                    note
                    totalQuantity
                    cost {
                        subtotalAmount {
                            amount
                            currencyCode
                        }
                        totalAmount {
                            amount
                            currencyCode
                        }
                        totalTaxAmount {
                            amount
                            currencyCode
                        }
                    }
                    lines(first: 100) {
                        edges {
                            node {
                                id
                                quantity
                                merchandise {
                                    ... on ProductVariant {
                                        id
                                        title
                                        sku
                                        price {
                                            amount
                                            currencyCode
                                        }
                                    }
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
    
    private static final String UPDATE_CART_LINES_MUTATION = """
        mutation cartLinesUpdate($cartId: ID!, $lines: [CartLineUpdateInput!]!) {
            cartLinesUpdate(cartId: $cartId, lines: $lines) {
                cart {
                    id
                    checkoutUrl
                    totalQuantity
                    cost {
                        subtotalAmount {
                            amount
                            currencyCode
                        }
                        totalAmount {
                            amount
                            currencyCode
                        }
                    }
                    lines(first: 100) {
                        edges {
                            node {
                                id
                                quantity
                                merchandise {
                                    ... on ProductVariant {
                                        id
                                        title
                                        sku
                                    }
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
    
    private static final String ADD_CART_LINES_MUTATION = """
        mutation cartLinesAdd($cartId: ID!, $lines: [CartLineInput!]!) {
            cartLinesAdd(cartId: $cartId, lines: $lines) {
                cart {
                    id
                    checkoutUrl
                    totalQuantity
                    cost {
                        subtotalAmount {
                            amount
                            currencyCode
                        }
                        totalAmount {
                            amount
                            currencyCode
                        }
                    }
                    lines(first: 100) {
                        edges {
                            node {
                                id
                                quantity
                                merchandise {
                                    ... on ProductVariant {
                                        id
                                        title
                                        sku
                                    }
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
    
    private static final String REMOVE_CART_LINES_MUTATION = """
        mutation cartLinesRemove($cartId: ID!, $lineIds: [ID!]!) {
            cartLinesRemove(cartId: $cartId, lineIds: $lineIds) {
                cart {
                    id
                    checkoutUrl
                    totalQuantity
                    cost {
                        subtotalAmount {
                            amount
                            currencyCode
                        }
                        totalAmount {
                            amount
                            currencyCode
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
    
    private static final String GET_CART_QUERY = """
        query getCart($id: ID!) {
            cart(id: $id) {
                id
                checkoutUrl
                createdAt
                updatedAt
                note
                totalQuantity
                buyerIdentity {
                    customer {
                        id
                        email
                        firstName
                        lastName
                    }
                    email
                    phone
                    countryCode
                }
                cost {
                    subtotalAmount {
                        amount
                        currencyCode
                    }
                    totalAmount {
                        amount
                        currencyCode
                    }
                    totalTaxAmount {
                        amount
                        currencyCode
                    }
                    totalDutyAmount {
                        amount
                        currencyCode
                    }
                }
                lines(first: 100) {
                    edges {
                        node {
                            id
                            quantity
                            merchandise {
                                ... on ProductVariant {
                                    id
                                    title
                                    sku
                                    price {
                                        amount
                                        currencyCode
                                    }
                                    product {
                                        id
                                        title
                                        handle
                                    }
                                }
                            }
                            cost {
                                amountPerQuantity {
                                    amount
                                    currencyCode
                                }
                                compareAtAmountPerQuantity {
                                    amount
                                    currencyCode
                                }
                                subtotalAmount {
                                    amount
                                    currencyCode
                                }
                                totalAmount {
                                    amount
                                    currencyCode
                                }
                            }
                        }
                    }
                    pageInfo {
                        hasNextPage
                        hasPreviousPage
                        startCursor
                        endCursor
                    }
                }
            }
        }
        """;
    
    public Cart createCart(ShopifyAuthContext context, CartInput input) {
        Map<String, Object> variables = new HashMap<>();
        variables.put("input", input);
        
        GraphQLRequest request = GraphQLRequest.builder()
                .query(CREATE_CART_MUTATION)
                .variables(variables)
                .build();
        
        GraphQLResponse<CartCreateData> response = graphQLClient.execute(
                request,
                CartCreateData.class
        );
        
        if (response.hasErrors()) {
            log.error("Failed to create cart: {}", response.getErrors());
            throw new RuntimeException("Failed to create cart");
        }
        
        CartCreateResponse createResponse = response.getData().getCartCreate();
        if (createResponse.getUserErrors() != null && !createResponse.getUserErrors().isEmpty()) {
            log.error("User errors creating cart: {}", createResponse.getUserErrors());
            throw new RuntimeException("Failed to create cart: " + createResponse.getUserErrors());
        }
        
        return createResponse.getCart();
    }
    
    public Cart getCart(ShopifyAuthContext context, String cartId) {
        Map<String, Object> variables = new HashMap<>();
        variables.put("id", cartId);
        
        GraphQLRequest request = GraphQLRequest.builder()
                .query(GET_CART_QUERY)
                .variables(variables)
                .build();
        
        GraphQLResponse<CartData> response = graphQLClient.execute(
                request,
                CartData.class
        );
        
        if (response.hasErrors()) {
            log.error("Failed to get cart: {}", response.getErrors());
            throw new RuntimeException("Failed to get cart");
        }
        
        return response.getData().getCart();
    }
    
    public Cart addCartLines(ShopifyAuthContext context, String cartId, List<CartLineInput> lines) {
        Map<String, Object> variables = new HashMap<>();
        variables.put("cartId", cartId);
        variables.put("lines", lines);
        
        GraphQLRequest request = GraphQLRequest.builder()
                .query(ADD_CART_LINES_MUTATION)
                .variables(variables)
                .build();
        
        GraphQLResponse<CartLinesAddData> response = graphQLClient.execute(
                request,
                CartLinesAddData.class
        );
        
        if (response.hasErrors()) {
            log.error("Failed to add cart lines: {}", response.getErrors());
            throw new RuntimeException("Failed to add cart lines");
        }
        
        CartLinesAddResponse addResponse = response.getData().getCartLinesAdd();
        if (addResponse.getUserErrors() != null && !addResponse.getUserErrors().isEmpty()) {
            log.error("User errors adding cart lines: {}", addResponse.getUserErrors());
            throw new RuntimeException("Failed to add cart lines: " + addResponse.getUserErrors());
        }
        
        return addResponse.getCart();
    }
    
    public Cart updateCartLines(ShopifyAuthContext context, String cartId, List<CartLineUpdateInput> lines) {
        Map<String, Object> variables = new HashMap<>();
        variables.put("cartId", cartId);
        variables.put("lines", lines);
        
        GraphQLRequest request = GraphQLRequest.builder()
                .query(UPDATE_CART_LINES_MUTATION)
                .variables(variables)
                .build();
        
        GraphQLResponse<CartLinesUpdateData> response = graphQLClient.execute(
                request,
                CartLinesUpdateData.class
        );
        
        if (response.hasErrors()) {
            log.error("Failed to update cart lines: {}", response.getErrors());
            throw new RuntimeException("Failed to update cart lines");
        }
        
        CartLinesUpdateResponse updateResponse = response.getData().getCartLinesUpdate();
        if (updateResponse.getUserErrors() != null && !updateResponse.getUserErrors().isEmpty()) {
            log.error("User errors updating cart lines: {}", updateResponse.getUserErrors());
            throw new RuntimeException("Failed to update cart lines: " + updateResponse.getUserErrors());
        }
        
        return updateResponse.getCart();
    }
    
    public Cart removeCartLines(ShopifyAuthContext context, String cartId, List<String> lineIds) {
        Map<String, Object> variables = new HashMap<>();
        variables.put("cartId", cartId);
        variables.put("lineIds", lineIds);
        
        GraphQLRequest request = GraphQLRequest.builder()
                .query(REMOVE_CART_LINES_MUTATION)
                .variables(variables)
                .build();
        
        GraphQLResponse<CartLinesRemoveData> response = graphQLClient.execute(
                request,
                CartLinesRemoveData.class
        );
        
        if (response.hasErrors()) {
            log.error("Failed to remove cart lines: {}", response.getErrors());
            throw new RuntimeException("Failed to remove cart lines");
        }
        
        CartLinesRemoveResponse removeResponse = response.getData().getCartLinesRemove();
        if (removeResponse.getUserErrors() != null && !removeResponse.getUserErrors().isEmpty()) {
            log.error("User errors removing cart lines: {}", removeResponse.getUserErrors());
            throw new RuntimeException("Failed to remove cart lines: " + removeResponse.getUserErrors());
        }
        
        return removeResponse.getCart();
    }
    
    @Data
    private static class CartData {
        private Cart cart;
    }
    
    @Data
    private static class CartCreateData {
        private CartCreateResponse cartCreate;
    }
    
    @Data
    private static class CartLinesAddData {
        private CartLinesAddResponse cartLinesAdd;
    }
    
    @Data
    private static class CartLinesUpdateData {
        private CartLinesUpdateResponse cartLinesUpdate;
    }
    
    @Data
    private static class CartLinesRemoveData {
        private CartLinesRemoveResponse cartLinesRemove;
    }
    
    @Data
    public static class CartCreateResponse {
        private Cart cart;
        private List<CartUserError> userErrors;
    }
    
    @Data
    public static class CartLinesAddResponse {
        private Cart cart;
        private List<CartUserError> userErrors;
    }
    
    @Data
    public static class CartLinesUpdateResponse {
        private Cart cart;
        private List<CartUserError> userErrors;
    }
    
    @Data
    public static class CartLinesRemoveResponse {
        private Cart cart;
        private List<CartUserError> userErrors;
    }
    
    @Data
    public static class CartUserError {
        private List<String> field;
        private String message;
        private String code;
    }
    
    @Data
    public static class CartInput {
        private List<CartLineInput> lines;
        private CartBuyerIdentityInput buyerIdentity;
        private String note;
        private List<AttributeInput> attributes;
        private List<String> discountCodes;
    }
    
    @Data
    public static class CartLineInput {
        private String merchandiseId;
        private Integer quantity;
        private List<AttributeInput> attributes;
    }
    
    @Data
    public static class CartLineUpdateInput {
        private String id;
        private Integer quantity;
        private String merchandiseId;
        private List<AttributeInput> attributes;
    }
    
    @Data
    public static class CartBuyerIdentityInput {
        private String email;
        private String phone;
        private String countryCode;
        private String customerAccessToken;
        private List<DeliveryAddressInput> deliveryAddressPreferences;
    }
    
    @Data
    public static class AttributeInput {
        private String key;
        private String value;
    }
    
    @Data
    public static class DeliveryAddressInput {
        private MailingAddressInput deliveryAddress;
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
}