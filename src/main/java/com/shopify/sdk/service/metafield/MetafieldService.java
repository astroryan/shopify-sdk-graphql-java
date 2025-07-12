package com.shopify.sdk.service.metafield;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.shopify.sdk.client.ShopifyGraphQLClient;
import com.shopify.sdk.client.ShopifyRestClient;
import com.shopify.sdk.model.metafield.Metafield;
import com.shopify.sdk.model.metafield.MetafieldType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class MetafieldService {
    
    private final ShopifyGraphQLClient graphQLClient;
    private final ShopifyRestClient restClient;
    private final ObjectMapper objectMapper;
    
    private static final String METAFIELDS_QUERY = """
        query getMetafields($first: Int, $after: String, $namespace: String, $owner: ID!) {
            node(id: $owner) {
                ... on Product {
                    metafields(first: $first, after: $after, namespace: $namespace) {
                        edges {
                            cursor
                            node {
                                id
                                namespace
                                key
                                value
                                type
                                description
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
                }
                ... on Customer {
                    metafields(first: $first, after: $after, namespace: $namespace) {
                        edges {
                            cursor
                            node {
                                id
                                namespace
                                key
                                value
                                type
                                description
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
                }
                ... on Order {
                    metafields(first: $first, after: $after, namespace: $namespace) {
                        edges {
                            cursor
                            node {
                                id
                                namespace
                                key
                                value
                                type
                                description
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
                }
                ... on Collection {
                    metafields(first: $first, after: $after, namespace: $namespace) {
                        edges {
                            cursor
                            node {
                                id
                                namespace
                                key
                                value
                                type
                                description
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
                }
            }
        }
        """;
    
    private static final String CREATE_METAFIELD_MUTATION = """
        mutation metafieldSet($metafields: [MetafieldsSetInput!]!) {
            metafieldsSet(metafields: $metafields) {
                metafields {
                    id
                    namespace
                    key
                    value
                    type
                    description
                    createdAt
                    updatedAt
                }
                userErrors {
                    field
                    message
                }
            }
        }
        """;
    
    private static final String DELETE_METAFIELD_MUTATION = """
        mutation metafieldDelete($input: MetafieldDeleteInput!) {
            metafieldDelete(input: $input) {
                deletedId
                userErrors {
                    field
                    message
                }
            }
        }
        """;
    
    public Mono<List<Metafield>> getMetafields(String shop, String accessToken, String ownerId, String namespace, Integer first, String after) {
        Map<String, Object> variables = Map.of(
            "first", first != null ? first : 50,
            "after", after != null ? after : "",
            "namespace", namespace,
            "owner", ownerId
        );
        
        return graphQLClient.query(shop, accessToken, METAFIELDS_QUERY, variables)
            .map(response -> {
                try {
                    var edges = response.getData().get("node").get("metafields").get("edges");
                    return objectMapper.convertValue(edges,
                        objectMapper.getTypeFactory().constructCollectionType(List.class, Metafield.class));
                } catch (Exception e) {
                    log.error("Failed to parse metafields response", e);
                    throw new RuntimeException("Failed to parse metafields response", e);
                }
            });
    }
    
    public Mono<List<Metafield>> createMetafields(String shop, String accessToken, List<MetafieldInput> metafields) {
        Map<String, Object> variables = Map.of("metafields", metafields);
        
        return graphQLClient.query(shop, accessToken, CREATE_METAFIELD_MUTATION, variables)
            .map(response -> {
                try {
                    var data = response.getData().get("metafieldsSet").get("metafields");
                    return objectMapper.convertValue(data,
                        objectMapper.getTypeFactory().constructCollectionType(List.class, Metafield.class));
                } catch (Exception e) {
                    log.error("Failed to parse create metafields response", e);
                    throw new RuntimeException("Failed to parse create metafields response", e);
                }
            });
    }
    
    public Mono<String> deleteMetafield(String shop, String accessToken, String metafieldId) {
        Map<String, Object> input = Map.of("id", metafieldId);
        Map<String, Object> variables = Map.of("input", input);
        
        return graphQLClient.query(shop, accessToken, DELETE_METAFIELD_MUTATION, variables)
            .map(response -> {
                try {
                    return response.getData().get("metafieldDelete").get("deletedId").asText();
                } catch (Exception e) {
                    log.error("Failed to parse delete metafield response", e);
                    throw new RuntimeException("Failed to parse delete metafield response", e);
                }
            });
    }
    
    // REST API methods for legacy support
    
    public Mono<List<Metafield>> getMetafieldsRest(String shop, String accessToken, String resourceType, String resourceId) {
        String endpoint = "/admin/api/2023-10/" + resourceType + "/" + resourceId + "/metafields.json";
        
        return restClient.get(shop, accessToken, endpoint)
            .map(response -> {
                try {
                    var data = response.get("metafields");
                    return objectMapper.convertValue(data,
                        objectMapper.getTypeFactory().constructCollectionType(List.class, Metafield.class));
                } catch (Exception e) {
                    log.error("Failed to parse metafields REST response", e);
                    throw new RuntimeException("Failed to parse metafields REST response", e);
                }
            });
    }
    
    public Mono<Metafield> getMetafieldRest(String shop, String accessToken, String metafieldId) {
        String endpoint = "/admin/api/2023-10/metafields/" + metafieldId + ".json";
        
        return restClient.get(shop, accessToken, endpoint)
            .map(response -> {
                try {
                    var data = response.get("metafield");
                    return objectMapper.convertValue(data, Metafield.class);
                } catch (Exception e) {
                    log.error("Failed to parse metafield REST response", e);
                    throw new RuntimeException("Failed to parse metafield REST response", e);
                }
            });
    }
    
    public Mono<Metafield> createMetafieldRest(String shop, String accessToken, String resourceType, String resourceId, MetafieldCreateRequest metafield) {
        String endpoint = "/admin/api/2023-10/" + resourceType + "/" + resourceId + "/metafields.json";
        
        return restClient.post(shop, accessToken, endpoint, Map.of("metafield", metafield))
            .map(response -> {
                try {
                    var data = response.get("metafield");
                    return objectMapper.convertValue(data, Metafield.class);
                } catch (Exception e) {
                    log.error("Failed to parse create metafield REST response", e);
                    throw new RuntimeException("Failed to parse create metafield REST response", e);
                }
            });
    }
    
    public Mono<Metafield> updateMetafieldRest(String shop, String accessToken, String metafieldId, MetafieldUpdateRequest metafield) {
        String endpoint = "/admin/api/2023-10/metafields/" + metafieldId + ".json";
        
        return restClient.post(shop, accessToken, endpoint, Map.of("metafield", metafield))
            .map(response -> {
                try {
                    var data = response.get("metafield");
                    return objectMapper.convertValue(data, Metafield.class);
                } catch (Exception e) {
                    log.error("Failed to parse update metafield REST response", e);
                    throw new RuntimeException("Failed to parse update metafield REST response", e);
                }
            });
    }
    
    public Mono<Void> deleteMetafieldRest(String shop, String accessToken, String metafieldId) {
        String endpoint = "/admin/api/2023-10/metafields/" + metafieldId + ".json";
        
        return restClient.delete(shop, accessToken, endpoint)
            .then();
    }
    
    // Shop-level metafields
    
    public Mono<List<Metafield>> getShopMetafields(String shop, String accessToken, String namespace) {
        String endpoint = "/admin/api/2023-10/metafields.json";
        Map<String, Object> params = namespace != null ? Map.of("namespace", namespace) : Map.of();
        
        return restClient.get(shop, accessToken, endpoint, params)
            .map(response -> {
                try {
                    var data = response.get("metafields");
                    return objectMapper.convertValue(data,
                        objectMapper.getTypeFactory().constructCollectionType(List.class, Metafield.class));
                } catch (Exception e) {
                    log.error("Failed to parse shop metafields response", e);
                    throw new RuntimeException("Failed to parse shop metafields response", e);
                }
            });
    }
    
    public Mono<Metafield> createShopMetafield(String shop, String accessToken, MetafieldCreateRequest metafield) {
        String endpoint = "/admin/api/2023-10/metafields.json";
        
        return restClient.post(shop, accessToken, endpoint, Map.of("metafield", metafield))
            .map(response -> {
                try {
                    var data = response.get("metafield");
                    return objectMapper.convertValue(data, Metafield.class);
                } catch (Exception e) {
                    log.error("Failed to parse create shop metafield response", e);
                    throw new RuntimeException("Failed to parse create shop metafield response", e);
                }
            });
    }
    
    @lombok.Data
    @lombok.NoArgsConstructor
    @lombok.AllArgsConstructor
    @lombok.Builder
    public static class MetafieldInput {
        private String ownerId;
        private String namespace;
        private String key;
        private String value;
        private MetafieldType type;
        private String description;
    }
    
    @lombok.Data
    @lombok.NoArgsConstructor
    @lombok.AllArgsConstructor
    @lombok.Builder
    public static class MetafieldCreateRequest {
        private String namespace;
        private String key;
        private String value;
        private String type;
        private String description;
    }
    
    @lombok.Data
    @lombok.NoArgsConstructor
    @lombok.AllArgsConstructor
    @lombok.Builder
    public static class MetafieldUpdateRequest {
        private String namespace;
        private String key;
        private String value;
        private String type;
        private String description;
    }
}