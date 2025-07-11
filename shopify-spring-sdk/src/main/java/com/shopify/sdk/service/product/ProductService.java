package com.shopify.sdk.service.product;

import com.shopify.sdk.auth.ShopifyAuthContext;
import com.shopify.sdk.client.GraphQLClient;
import com.shopify.sdk.model.common.ID;
import com.shopify.sdk.model.graphql.GraphQLRequest;
import com.shopify.sdk.model.graphql.GraphQLResponse;
import com.shopify.sdk.model.product.Product;
import com.shopify.sdk.model.product.ProductConnection;
import com.shopify.sdk.model.product.ProductStatus;
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
public class ProductService {
    
    private final GraphQLClient graphQLClient;
    
    private static final String GET_PRODUCT_QUERY = """
        query getProduct($id: ID!) {
            product(id: $id) {
                id
                title
                description
                descriptionHtml
                handle
                vendor
                productType
                tags
                status
                publishedAt
                createdAt
                updatedAt
                totalInventory
                tracksInventory
                requiresSellingPlan
                giftCard
                featuredImage {
                    id
                    altText
                    url
                    width
                    height
                }
                seo {
                    title
                    description
                }
                priceRange {
                    minVariantPrice {
                        amount
                        currencyCode
                    }
                    maxVariantPrice {
                        amount
                        currencyCode
                    }
                }
                options {
                    id
                    name
                    position
                    values
                }
                variants(first: 100) {
                    edges {
                        node {
                            id
                            title
                            sku
                            barcode
                            price {
                                amount
                                currencyCode
                            }
                            compareAtPrice {
                                amount
                                currencyCode
                            }
                            weight
                            weightUnit
                            inventoryQuantity
                            availableForSale
                            requiresShipping
                            taxable
                            taxCode
                            position
                            selectedOptions {
                                name
                                value
                            }
                            image {
                                id
                                altText
                                url
                                width
                                height
                            }
                            createdAt
                            updatedAt
                        }
                    }
                    pageInfo {
                        hasNextPage
                        hasPreviousPage
                        startCursor
                        endCursor
                    }
                }
                images(first: 100) {
                    edges {
                        node {
                            id
                            altText
                            url
                            width
                            height
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
    
    private static final String LIST_PRODUCTS_QUERY = """
        query listProducts($first: Int!, $after: String, $query: String, $status: ProductStatus) {
            products(first: $first, after: $after, query: $query) {
                edges {
                    node {
                        id
                        title
                        description
                        handle
                        vendor
                        productType
                        tags
                        status
                        publishedAt
                        createdAt
                        updatedAt
                        totalInventory
                        tracksInventory
                        requiresSellingPlan
                        giftCard
                        featuredImage {
                            id
                            altText
                            url
                            width
                            height
                        }
                        priceRange {
                            minVariantPrice {
                                amount
                                currencyCode
                            }
                            maxVariantPrice {
                                amount
                                currencyCode
                            }
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
                totalCount
            }
        }
        """;
    
    private static final String CREATE_PRODUCT_MUTATION = """
        mutation productCreate($input: ProductInput!) {
            productCreate(input: $input) {
                product {
                    id
                    title
                    handle
                    status
                    vendor
                    productType
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
    
    private static final String UPDATE_PRODUCT_MUTATION = """
        mutation productUpdate($input: ProductInput!) {
            productUpdate(input: $input) {
                product {
                    id
                    title
                    description
                    handle
                    status
                    vendor
                    productType
                    tags
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
    
    private static final String DELETE_PRODUCT_MUTATION = """
        mutation productDelete($input: ProductDeleteInput!) {
            productDelete(input: $input) {
                deletedProductId
                userErrors {
                    field
                    message
                    code
                }
            }
        }
        """;
    
    public Product getProduct(ShopifyAuthContext context, String productId) {
        Map<String, Object> variables = new HashMap<>();
        variables.put("id", productId);
        
        GraphQLRequest request = GraphQLRequest.builder()
                .query(GET_PRODUCT_QUERY)
                .variables(variables)
                .build();
        
        GraphQLResponse<ProductData> response = graphQLClient.execute(
                request,
                ProductData.class
        );
        
        if (response.hasErrors()) {
            log.error("Failed to get product: {}", response.getErrors());
            throw new RuntimeException("Failed to get product");
        }
        
        return response.getData().getProduct();
    }
    
    public ProductConnection listProducts(ShopifyAuthContext context, int first, String after, String query, ProductStatus status) {
        Map<String, Object> variables = new HashMap<>();
        variables.put("first", first);
        if (after != null) {
            variables.put("after", after);
        }
        if (query != null) {
            variables.put("query", query);
        }
        if (status != null) {
            variables.put("status", status.getValue());
        }
        
        GraphQLRequest request = GraphQLRequest.builder()
                .query(LIST_PRODUCTS_QUERY)
                .variables(variables)
                .build();
        
        GraphQLResponse<ProductsData> response = graphQLClient.execute(
                request,
                ProductsData.class
        );
        
        if (response.hasErrors()) {
            log.error("Failed to list products: {}", response.getErrors());
            throw new RuntimeException("Failed to list products");
        }
        
        return response.getData().getProducts();
    }
    
    public Product createProduct(ShopifyAuthContext context, ProductInput input) {
        Map<String, Object> variables = new HashMap<>();
        variables.put("input", input);
        
        GraphQLRequest request = GraphQLRequest.builder()
                .query(CREATE_PRODUCT_MUTATION)
                .variables(variables)
                .build();
        
        GraphQLResponse<ProductCreateData> response = graphQLClient.execute(
                request,
                ProductCreateData.class
        );
        
        if (response.hasErrors()) {
            log.error("Failed to create product: {}", response.getErrors());
            throw new RuntimeException("Failed to create product");
        }
        
        ProductCreateResponse createResponse = response.getData().getProductCreate();
        if (createResponse.getUserErrors() != null && !createResponse.getUserErrors().isEmpty()) {
            log.error("User errors creating product: {}", createResponse.getUserErrors());
            throw new RuntimeException("Failed to create product: " + createResponse.getUserErrors());
        }
        
        return createResponse.getProduct();
    }
    
    public Product updateProduct(ShopifyAuthContext context, ProductInput input) {
        Map<String, Object> variables = new HashMap<>();
        variables.put("input", input);
        
        GraphQLRequest request = GraphQLRequest.builder()
                .query(UPDATE_PRODUCT_MUTATION)
                .variables(variables)
                .build();
        
        GraphQLResponse<ProductUpdateData> response = graphQLClient.execute(
                request,
                ProductUpdateData.class
        );
        
        if (response.hasErrors()) {
            log.error("Failed to update product: {}", response.getErrors());
            throw new RuntimeException("Failed to update product");
        }
        
        ProductUpdateResponse updateResponse = response.getData().getProductUpdate();
        if (updateResponse.getUserErrors() != null && !updateResponse.getUserErrors().isEmpty()) {
            log.error("User errors updating product: {}", updateResponse.getUserErrors());
            throw new RuntimeException("Failed to update product: " + updateResponse.getUserErrors());
        }
        
        return updateResponse.getProduct();
    }
    
    public String deleteProduct(ShopifyAuthContext context, String productId) {
        Map<String, Object> variables = new HashMap<>();
        Map<String, Object> input = new HashMap<>();
        input.put("id", productId);
        variables.put("input", input);
        
        GraphQLRequest request = GraphQLRequest.builder()
                .query(DELETE_PRODUCT_MUTATION)
                .variables(variables)
                .build();
        
        GraphQLResponse<ProductDeleteData> response = graphQLClient.execute(
                request,
                ProductDeleteData.class
        );
        
        if (response.hasErrors()) {
            log.error("Failed to delete product: {}", response.getErrors());
            throw new RuntimeException("Failed to delete product");
        }
        
        ProductDeleteResponse deleteResponse = response.getData().getProductDelete();
        if (deleteResponse.getUserErrors() != null && !deleteResponse.getUserErrors().isEmpty()) {
            log.error("User errors deleting product: {}", deleteResponse.getUserErrors());
            throw new RuntimeException("Failed to delete product: " + deleteResponse.getUserErrors());
        }
        
        return deleteResponse.getDeletedProductId();
    }
    
    @Data
    private static class ProductData {
        private Product product;
    }
    
    @Data
    private static class ProductsData {
        private ProductConnection products;
    }
    
    @Data
    private static class ProductCreateData {
        private ProductCreateResponse productCreate;
    }
    
    @Data
    private static class ProductUpdateData {
        private ProductUpdateResponse productUpdate;
    }
    
    @Data
    private static class ProductDeleteData {
        private ProductDeleteResponse productDelete;
    }
    
    @Data
    public static class ProductCreateResponse {
        private Product product;
        private List<UserError> userErrors;
    }
    
    @Data
    public static class ProductUpdateResponse {
        private Product product;
        private List<UserError> userErrors;
    }
    
    @Data
    public static class ProductDeleteResponse {
        private String deletedProductId;
        private List<UserError> userErrors;
    }
    
    @Data
    public static class UserError {
        private List<String> field;
        private String message;
        private String code;
    }
}