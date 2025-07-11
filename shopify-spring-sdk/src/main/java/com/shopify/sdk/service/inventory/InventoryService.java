package com.shopify.sdk.service.inventory;

import com.shopify.sdk.auth.ShopifyAuthContext;
import com.shopify.sdk.client.GraphQLClient;
import com.shopify.sdk.model.inventory.InventoryItem;
import com.shopify.sdk.model.inventory.InventoryLevel;
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
public class InventoryService {
    
    private final GraphQLClient graphQLClient;
    
    private static final String INVENTORY_ADJUST_QUANTITIES_MUTATION = """
        mutation inventoryAdjustQuantities($input: InventoryAdjustQuantitiesInput!) {
            inventoryAdjustQuantities(input: $input) {
                inventoryAdjustmentGroup {
                    id
                    reason
                    referenceDocumentUri
                    changes {
                        name
                        delta
                    }
                }
                userErrors {
                    field
                    message
                }
            }
        }
        """;
    
    private static final String INVENTORY_SET_QUANTITIES_MUTATION = """
        mutation inventorySetOnHandQuantities($input: InventorySetOnHandQuantitiesInput!) {
            inventorySetOnHandQuantities(input: $input) {
                inventoryAdjustmentGroup {
                    id
                    reason
                    referenceDocumentUri
                    changes {
                        name
                        delta
                    }
                }
                userErrors {
                    field
                    message
                }
            }
        }
        """;
    
    private static final String INVENTORY_ACTIVATE_MUTATION = """
        mutation inventoryActivate($inventoryItemId: ID!, $locationId: ID!) {
            inventoryActivate(inventoryItemId: $inventoryItemId, locationId: $locationId) {
                inventoryLevel {
                    id
                    available
                    location {
                        id
                        name
                    }
                    inventoryItem {
                        id
                        sku
                    }
                }
                userErrors {
                    field
                    message
                }
            }
        }
        """;
    
    private static final String INVENTORY_DEACTIVATE_MUTATION = """
        mutation inventoryDeactivate($inventoryLevelId: ID!) {
            inventoryDeactivate(inventoryLevelId: $inventoryLevelId) {
                userErrors {
                    field
                    message
                }
            }
        }
        """;
    
    private static final String GET_INVENTORY_ITEM_QUERY = """
        query getInventoryItem($id: ID!) {
            inventoryItem(id: $id) {
                id
                countryCodeOfOrigin
                createdAt
                duplicateSkuCount
                harmonizedSystemCode
                inventoryHistoryUrl
                legacyResourceId
                locationsCount
                provinceCodeOfOrigin
                requiresShipping
                sku
                tracked
                trackedEditable {
                    locked
                    reason
                }
                unitCost {
                    amount
                    currencyCode
                }
                updatedAt
                variant {
                    id
                    title
                    sku
                    product {
                        id
                        title
                    }
                }
                inventoryLevels(first: 50) {
                    edges {
                        node {
                            id
                            available
                            incoming
                            location {
                                id
                                name
                            }
                        }
                    }
                }
            }
        }
        """;
    
    private static final String LIST_INVENTORY_ITEMS_QUERY = """
        query listInventoryItems($first: Int!, $after: String, $query: String) {
            inventoryItems(first: $first, after: $after, query: $query) {
                edges {
                    node {
                        id
                        sku
                        tracked
                        requiresShipping
                        createdAt
                        updatedAt
                        locationsCount
                        variant {
                            id
                            title
                            product {
                                id
                                title
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
            }
        }
        """;
    
    public InventoryAdjustmentGroup adjustQuantities(ShopifyAuthContext context, InventoryAdjustQuantitiesInput input) {
        Map<String, Object> variables = new HashMap<>();
        variables.put("input", input);
        
        GraphQLRequest request = GraphQLRequest.builder()
                .query(INVENTORY_ADJUST_QUANTITIES_MUTATION)
                .variables(variables)
                .build();
        
        GraphQLResponse<InventoryAdjustQuantitiesData> response = graphQLClient.execute(
                request,
                InventoryAdjustQuantitiesData.class
        );
        
        if (response.hasErrors()) {
            log.error("Failed to adjust inventory quantities: {}", response.getErrors());
            throw new RuntimeException("Failed to adjust inventory quantities");
        }
        
        InventoryAdjustQuantitiesResponse adjustResponse = response.getData().getInventoryAdjustQuantities();
        if (adjustResponse.getUserErrors() != null && !adjustResponse.getUserErrors().isEmpty()) {
            log.error("User errors adjusting inventory: {}", adjustResponse.getUserErrors());
            throw new RuntimeException("Failed to adjust inventory: " + adjustResponse.getUserErrors());
        }
        
        return adjustResponse.getInventoryAdjustmentGroup();
    }
    
    public InventoryAdjustmentGroup setOnHandQuantities(ShopifyAuthContext context, InventorySetOnHandQuantitiesInput input) {
        Map<String, Object> variables = new HashMap<>();
        variables.put("input", input);
        
        GraphQLRequest request = GraphQLRequest.builder()
                .query(INVENTORY_SET_QUANTITIES_MUTATION)
                .variables(variables)
                .build();
        
        GraphQLResponse<InventorySetOnHandQuantitiesData> response = graphQLClient.execute(
                request,
                InventorySetOnHandQuantitiesData.class
        );
        
        if (response.hasErrors()) {
            log.error("Failed to set inventory quantities: {}", response.getErrors());
            throw new RuntimeException("Failed to set inventory quantities");
        }
        
        InventorySetOnHandQuantitiesResponse setResponse = response.getData().getInventorySetOnHandQuantities();
        if (setResponse.getUserErrors() != null && !setResponse.getUserErrors().isEmpty()) {
            log.error("User errors setting inventory: {}", setResponse.getUserErrors());
            throw new RuntimeException("Failed to set inventory: " + setResponse.getUserErrors());
        }
        
        return setResponse.getInventoryAdjustmentGroup();
    }
    
    public InventoryLevel activateInventory(ShopifyAuthContext context, String inventoryItemId, String locationId) {
        Map<String, Object> variables = new HashMap<>();
        variables.put("inventoryItemId", inventoryItemId);
        variables.put("locationId", locationId);
        
        GraphQLRequest request = GraphQLRequest.builder()
                .query(INVENTORY_ACTIVATE_MUTATION)
                .variables(variables)
                .build();
        
        GraphQLResponse<InventoryActivateData> response = graphQLClient.execute(
                request,
                InventoryActivateData.class
        );
        
        if (response.hasErrors()) {
            log.error("Failed to activate inventory: {}", response.getErrors());
            throw new RuntimeException("Failed to activate inventory");
        }
        
        InventoryActivateResponse activateResponse = response.getData().getInventoryActivate();
        if (activateResponse.getUserErrors() != null && !activateResponse.getUserErrors().isEmpty()) {
            log.error("User errors activating inventory: {}", activateResponse.getUserErrors());
            throw new RuntimeException("Failed to activate inventory: " + activateResponse.getUserErrors());
        }
        
        return activateResponse.getInventoryLevel();
    }
    
    public void deactivateInventory(ShopifyAuthContext context, String inventoryLevelId) {
        Map<String, Object> variables = new HashMap<>();
        variables.put("inventoryLevelId", inventoryLevelId);
        
        GraphQLRequest request = GraphQLRequest.builder()
                .query(INVENTORY_DEACTIVATE_MUTATION)
                .variables(variables)
                .build();
        
        GraphQLResponse<InventoryDeactivateData> response = graphQLClient.execute(
                request,
                InventoryDeactivateData.class
        );
        
        if (response.hasErrors()) {
            log.error("Failed to deactivate inventory: {}", response.getErrors());
            throw new RuntimeException("Failed to deactivate inventory");
        }
        
        InventoryDeactivateResponse deactivateResponse = response.getData().getInventoryDeactivate();
        if (deactivateResponse.getUserErrors() != null && !deactivateResponse.getUserErrors().isEmpty()) {
            log.error("User errors deactivating inventory: {}", deactivateResponse.getUserErrors());
            throw new RuntimeException("Failed to deactivate inventory: " + deactivateResponse.getUserErrors());
        }
    }
    
    public InventoryItem getInventoryItem(ShopifyAuthContext context, String inventoryItemId) {
        Map<String, Object> variables = new HashMap<>();
        variables.put("id", inventoryItemId);
        
        GraphQLRequest request = GraphQLRequest.builder()
                .query(GET_INVENTORY_ITEM_QUERY)
                .variables(variables)
                .build();
        
        GraphQLResponse<InventoryItemData> response = graphQLClient.execute(
                request,
                InventoryItemData.class
        );
        
        if (response.hasErrors()) {
            log.error("Failed to get inventory item: {}", response.getErrors());
            throw new RuntimeException("Failed to get inventory item");
        }
        
        return response.getData().getInventoryItem();
    }
    
    @Data
    private static class InventoryAdjustQuantitiesData {
        private InventoryAdjustQuantitiesResponse inventoryAdjustQuantities;
    }
    
    @Data
    private static class InventorySetOnHandQuantitiesData {
        private InventorySetOnHandQuantitiesResponse inventorySetOnHandQuantities;
    }
    
    @Data
    private static class InventoryActivateData {
        private InventoryActivateResponse inventoryActivate;
    }
    
    @Data
    private static class InventoryDeactivateData {
        private InventoryDeactivateResponse inventoryDeactivate;
    }
    
    @Data
    private static class InventoryItemData {
        private InventoryItem inventoryItem;
    }
    
    @Data
    public static class InventoryAdjustQuantitiesResponse {
        private InventoryAdjustmentGroup inventoryAdjustmentGroup;
        private List<UserError> userErrors;
    }
    
    @Data
    public static class InventorySetOnHandQuantitiesResponse {
        private InventoryAdjustmentGroup inventoryAdjustmentGroup;
        private List<UserError> userErrors;
    }
    
    @Data
    public static class InventoryActivateResponse {
        private InventoryLevel inventoryLevel;
        private List<UserError> userErrors;
    }
    
    @Data
    public static class InventoryDeactivateResponse {
        private List<UserError> userErrors;
    }
    
    @Data
    public static class UserError {
        private List<String> field;
        private String message;
    }
    
    @Data
    public static class InventoryAdjustQuantitiesInput {
        private String reason;
        private String name;
        private String referenceDocumentUri;
        private List<InventoryChangeInput> changes;
    }
    
    @Data
    public static class InventorySetOnHandQuantitiesInput {
        private String reason;
        private String referenceDocumentUri;
        private List<InventorySetQuantityInput> quantities;
    }
    
    @Data
    public static class InventoryChangeInput {
        private Integer delta;
        private String inventoryItemId;
        private String locationId;
    }
    
    @Data
    public static class InventorySetQuantityInput {
        private String inventoryItemId;
        private String locationId;
        private Integer quantity;
    }
    
    @Data
    public static class InventoryAdjustmentGroup {
        private String id;
        private String reason;
        private String referenceDocumentUri;
        private List<InventoryChange> changes;
    }
    
    @Data
    public static class InventoryChange {
        private String name;
        private Integer delta;
    }
    
    @Data
    public static class Location {
        private String id;
        private String name;
    }
    
    @Data
    public static class EditableProperty {
        private Boolean locked;
        private String reason;
    }
    
    @Data
    public static class ProductVariant {
        private String id;
        private String title;
        private String sku;
        private Product product;
    }
    
    @Data
    public static class Product {
        private String id;
        private String title;
    }
    
    @Data
    public static class InventoryLevelConnection {
        private List<InventoryLevelEdge> edges;
        private PageInfo pageInfo;
    }
    
    @Data
    public static class InventoryLevelEdge {
        private InventoryLevel node;
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