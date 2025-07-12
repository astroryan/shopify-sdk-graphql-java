package com.shopify.sdk.service.inventory;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.shopify.sdk.client.ShopifyGraphQLClient;
import com.shopify.sdk.client.ShopifyRestClient;
import com.shopify.sdk.model.inventory.InventoryItem;
import com.shopify.sdk.model.inventory.InventoryLevel;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class InventoryService {
    
    private final ShopifyGraphQLClient graphQLClient;
    private final ShopifyRestClient restClient;
    private final ObjectMapper objectMapper;
    
    private static final String INVENTORY_LEVELS_QUERY = """
        query getInventoryLevels($first: Int, $after: String, $locationIds: [ID!]) {
            inventoryLevels(first: $first, after: $after, query: $locationIds) {
                edges {
                    cursor
                    node {
                        id
                        quantities(names: ["available", "committed", "incoming", "on_hand"]) {
                            name
                            quantity
                        }
                        item {
                            id
                            sku
                            tracked
                            requiresShipping
                            countryCodeOfOrigin
                            harmonizedSystemCode
                            createdAt
                            updatedAt
                        }
                        location {
                            id
                            name
                        }
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
        }
        """;
    
    private static final String INVENTORY_ITEM_QUERY = """
        query getInventoryItem($id: ID!) {
            inventoryItem(id: $id) {
                id
                sku
                tracked
                requiresShipping
                countryCodeOfOrigin
                provinceCodeOfOrigin
                harmonizedSystemCode
                createdAt
                updatedAt
                countryHarmonizedSystemCodes {
                    countryCode
                    harmonizedSystemCode
                }
                duplicateSkuCount
                inventoryLevels(first: 50) {
                    edges {
                        node {
                            id
                            quantities(names: ["available", "committed", "incoming", "on_hand"]) {
                                name
                                quantity
                            }
                            location {
                                id
                                name
                            }
                            updatedAt
                        }
                    }
                }
            }
        }
        """;
    
    private static final String INVENTORY_ADJUST_MUTATION = """
        mutation inventoryAdjustQuantity($input: InventoryAdjustQuantityInput!) {
            inventoryAdjustQuantity(input: $input) {
                inventoryLevel {
                    id
                    quantities(names: ["available", "committed", "incoming", "on_hand"]) {
                        name
                        quantity
                    }
                    updatedAt
                }
                userErrors {
                    field
                    message
                }
            }
        }
        """;
    
    private static final String INVENTORY_BULK_ADJUST_MUTATION = """
        mutation inventoryBulkAdjustQuantityAtLocation($inventoryItemAdjustments: [InventoryAdjustItemInput!]!, $locationId: ID!) {
            inventoryBulkAdjustQuantityAtLocation(inventoryItemAdjustments: $inventoryItemAdjustments, locationId: $locationId) {
                inventoryLevels {
                    id
                    quantities(names: ["available", "committed", "incoming", "on_hand"]) {
                        name
                        quantity
                    }
                    item {
                        id
                        sku
                    }
                    location {
                        id
                        name
                    }
                    updatedAt
                }
                userErrors {
                    field
                    message
                }
            }
        }
        """;
    
    public Mono<List<InventoryLevel>> getInventoryLevels(String shop, String accessToken, 
                                                        List<String> locationIds, 
                                                        Integer first, String after) {
        Map<String, Object> variables = Map.of(
            "first", first != null ? first : 50,
            "after", after != null ? after : "",
            "locationIds", locationIds != null ? locationIds : List.of()
        );
        
        return graphQLClient.query(shop, accessToken, INVENTORY_LEVELS_QUERY, variables)
            .map(response -> {
                try {
                    var edges = response.getData().get("inventoryLevels").get("edges");
                    return objectMapper.convertValue(edges, 
                        objectMapper.getTypeFactory().constructCollectionType(List.class, InventoryLevel.class));
                } catch (Exception e) {
                    log.error("Failed to parse inventory levels response", e);
                    throw new RuntimeException("Failed to parse inventory levels response", e);
                }
            });
    }
    
    public Mono<InventoryItem> getInventoryItem(String shop, String accessToken, String inventoryItemId) {
        Map<String, Object> variables = Map.of("id", inventoryItemId);
        
        return graphQLClient.query(shop, accessToken, INVENTORY_ITEM_QUERY, variables)
            .map(response -> {
                try {
                    var data = response.getData().get("inventoryItem");
                    return objectMapper.convertValue(data, InventoryItem.class);
                } catch (Exception e) {
                    log.error("Failed to parse inventory item response", e);
                    throw new RuntimeException("Failed to parse inventory item response", e);
                }
            });
    }
    
    public Mono<InventoryLevel> adjustInventoryQuantity(String shop, String accessToken, 
                                                       String inventoryLevelId, 
                                                       String quantityName,
                                                       int quantityDelta) {
        Map<String, Object> input = Map.of(
            "inventoryLevelId", inventoryLevelId,
            "quantityName", quantityName,
            "quantityDelta", quantityDelta
        );
        
        Map<String, Object> variables = Map.of("input", input);
        
        return graphQLClient.query(shop, accessToken, INVENTORY_ADJUST_MUTATION, variables)
            .map(response -> {
                try {
                    var data = response.getData().get("inventoryAdjustQuantity").get("inventoryLevel");
                    return objectMapper.convertValue(data, InventoryLevel.class);
                } catch (Exception e) {
                    log.error("Failed to parse inventory adjust response", e);
                    throw new RuntimeException("Failed to parse inventory adjust response", e);
                }
            });
    }
    
    public Mono<List<InventoryLevel>> bulkAdjustInventory(String shop, String accessToken,
                                                         String locationId,
                                                         List<InventoryAdjustment> adjustments) {
        Map<String, Object> variables = Map.of(
            "locationId", locationId,
            "inventoryItemAdjustments", adjustments
        );
        
        return graphQLClient.query(shop, accessToken, INVENTORY_BULK_ADJUST_MUTATION, variables)
            .map(response -> {
                try {
                    var data = response.getData().get("inventoryBulkAdjustQuantityAtLocation").get("inventoryLevels");
                    return objectMapper.convertValue(data, 
                        objectMapper.getTypeFactory().constructCollectionType(List.class, InventoryLevel.class));
                } catch (Exception e) {
                    log.error("Failed to parse bulk inventory adjust response", e);
                    throw new RuntimeException("Failed to parse bulk inventory adjust response", e);
                }
            });
    }
    
    public Mono<List<InventoryItem>> getInventoryItems(String shop, String accessToken, List<String> ids) {
        String endpoint = "/admin/api/2023-10/inventory_items.json";
        Map<String, Object> params = Map.of("ids", String.join(",", ids));
        
        return restClient.get(shop, accessToken, endpoint, params)
            .map(response -> {
                try {
                    var data = response.get("inventory_items");
                    return objectMapper.convertValue(data,
                        objectMapper.getTypeFactory().constructCollectionType(List.class, InventoryItem.class));
                } catch (Exception e) {
                    log.error("Failed to parse inventory items response", e);
                    throw new RuntimeException("Failed to parse inventory items response", e);
                }
            });
    }
    
    public Mono<InventoryItem> updateInventoryItem(String shop, String accessToken, String inventoryItemId, InventoryItemUpdate update) {
        String endpoint = "/admin/api/2023-10/inventory_items/" + inventoryItemId + ".json";
        
        return restClient.post(shop, accessToken, endpoint, Map.of("inventory_item", update))
            .map(response -> {
                try {
                    var data = response.get("inventory_item");
                    return objectMapper.convertValue(data, InventoryItem.class);
                } catch (Exception e) {
                    log.error("Failed to parse update inventory item response", e);
                    throw new RuntimeException("Failed to parse update inventory item response", e);
                }
            });
    }
    
    public Mono<List<InventoryLevel>> getInventoryLevelsRestAPI(String shop, String accessToken, 
                                                               List<String> inventoryItemIds,
                                                               List<String> locationIds) {
        String endpoint = "/admin/api/2023-10/inventory_levels.json";
        Map<String, Object> params = Map.of(
            "inventory_item_ids", inventoryItemIds != null ? String.join(",", inventoryItemIds) : "",
            "location_ids", locationIds != null ? String.join(",", locationIds) : ""
        );
        
        return restClient.get(shop, accessToken, endpoint, params)
            .map(response -> {
                try {
                    var data = response.get("inventory_levels");
                    return objectMapper.convertValue(data,
                        objectMapper.getTypeFactory().constructCollectionType(List.class, InventoryLevel.class));
                } catch (Exception e) {
                    log.error("Failed to parse inventory levels REST response", e);
                    throw new RuntimeException("Failed to parse inventory levels REST response", e);
                }
            });
    }
    
    public Mono<InventoryLevel> setInventoryLevel(String shop, String accessToken, 
                                                 String inventoryItemId, String locationId, Integer available) {
        String endpoint = "/admin/api/2023-10/inventory_levels/set.json";
        Map<String, Object> body = Map.of(
            "inventory_item_id", inventoryItemId,
            "location_id", locationId,
            "available", available
        );
        
        return restClient.post(shop, accessToken, endpoint, body)
            .map(response -> {
                try {
                    var data = response.get("inventory_level");
                    return objectMapper.convertValue(data, InventoryLevel.class);
                } catch (Exception e) {
                    log.error("Failed to parse set inventory level response", e);
                    throw new RuntimeException("Failed to parse set inventory level response", e);
                }
            });
    }
    
    public Mono<InventoryLevel> adjustInventoryLevel(String shop, String accessToken,
                                                    String inventoryItemId, String locationId, Integer quantityAdjustment) {
        String endpoint = "/admin/api/2023-10/inventory_levels/adjust.json";
        Map<String, Object> body = Map.of(
            "inventory_item_id", inventoryItemId,
            "location_id", locationId,
            "quantity_adjustment", quantityAdjustment
        );
        
        return restClient.post(shop, accessToken, endpoint, body)
            .map(response -> {
                try {
                    var data = response.get("inventory_level");
                    return objectMapper.convertValue(data, InventoryLevel.class);
                } catch (Exception e) {
                    log.error("Failed to parse adjust inventory level response", e);
                    throw new RuntimeException("Failed to parse adjust inventory level response", e);
                }
            });
    }
    
    @lombok.Data
    @lombok.NoArgsConstructor
    @lombok.AllArgsConstructor
    @lombok.Builder
    public static class InventoryAdjustment {
        private String inventoryItemId;
        private String quantityName;
        private Integer quantityDelta;
    }
    
    @lombok.Data
    @lombok.NoArgsConstructor
    @lombok.AllArgsConstructor
    @lombok.Builder
    public static class InventoryItemUpdate {
        private String sku;
        private Boolean tracked;
        private String countryCodeOfOrigin;
        private String provinceCodeOfOrigin;
        private String harmonizedSystemCode;
        private Boolean requiresShipping;
    }
}