package com.shopify.sdk.service.scripttag;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.shopify.sdk.client.ShopifyRestClient;
import com.shopify.sdk.model.scripttag.ScriptTag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class ScriptTagService {
    
    private final ShopifyRestClient restClient;
    private final ObjectMapper objectMapper;
    
    /**
     * Gets all script tags for a store.
     */
    public Mono<List<ScriptTag>> getScriptTags(String shop, String accessToken) {
        String endpoint = "/admin/api/2023-10/script_tags.json";
        
        return restClient.get(shop, accessToken, endpoint)
            .map(response -> {
                try {
                    var data = response.get("script_tags");
                    return objectMapper.convertValue(data,
                        objectMapper.getTypeFactory().constructCollectionType(List.class, ScriptTag.class));
                } catch (Exception e) {
                    log.error("Failed to parse script tags response", e);
                    throw new RuntimeException("Failed to parse script tags response", e);
                }
            });
    }
    
    /**
     * Gets script tags with filtering options.
     */
    public Mono<List<ScriptTag>> getScriptTags(String shop, String accessToken, String src, Integer limit, 
                                             Integer sinceId, String createdAtMin, String createdAtMax,
                                             String updatedAtMin, String updatedAtMax, String fields) {
        String endpoint = "/admin/api/2023-10/script_tags.json";
        
        Map<String, Object> params = Map.of(
            "src", src != null ? src : "",
            "limit", limit != null ? limit : 50,
            "since_id", sinceId != null ? sinceId : 0,
            "created_at_min", createdAtMin != null ? createdAtMin : "",
            "created_at_max", createdAtMax != null ? createdAtMax : "",
            "updated_at_min", updatedAtMin != null ? updatedAtMin : "",
            "updated_at_max", updatedAtMax != null ? updatedAtMax : "",
            "fields", fields != null ? fields : ""
        );
        
        // Remove empty parameters
        params.entrySet().removeIf(entry -> {
            Object value = entry.getValue();
            return value == null || (value instanceof String && ((String) value).isEmpty()) ||
                   (value instanceof Integer && ((Integer) value) == 0);
        });
        
        return restClient.get(shop, accessToken, endpoint, params)
            .map(response -> {
                try {
                    var data = response.get("script_tags");
                    return objectMapper.convertValue(data,
                        objectMapper.getTypeFactory().constructCollectionType(List.class, ScriptTag.class));
                } catch (Exception e) {
                    log.error("Failed to parse script tags response", e);
                    throw new RuntimeException("Failed to parse script tags response", e);
                }
            });
    }
    
    /**
     * Gets a specific script tag by ID.
     */
    public Mono<ScriptTag> getScriptTag(String shop, String accessToken, String scriptTagId) {
        String endpoint = "/admin/api/2023-10/script_tags/" + scriptTagId + ".json";
        
        return restClient.get(shop, accessToken, endpoint)
            .map(response -> {
                try {
                    var data = response.get("script_tag");
                    return objectMapper.convertValue(data, ScriptTag.class);
                } catch (Exception e) {
                    log.error("Failed to parse script tag response", e);
                    throw new RuntimeException("Failed to parse script tag response", e);
                }
            });
    }
    
    /**
     * Creates a new script tag.
     */
    public Mono<ScriptTag> createScriptTag(String shop, String accessToken, ScriptTagCreateRequest request) {
        String endpoint = "/admin/api/2023-10/script_tags.json";
        
        return restClient.post(shop, accessToken, endpoint, Map.of("script_tag", request))
            .map(response -> {
                try {
                    var data = response.get("script_tag");
                    return objectMapper.convertValue(data, ScriptTag.class);
                } catch (Exception e) {
                    log.error("Failed to parse create script tag response", e);
                    throw new RuntimeException("Failed to parse create script tag response", e);
                }
            });
    }
    
    /**
     * Updates an existing script tag.
     */
    public Mono<ScriptTag> updateScriptTag(String shop, String accessToken, String scriptTagId, ScriptTagUpdateRequest request) {
        String endpoint = "/admin/api/2023-10/script_tags/" + scriptTagId + ".json";
        
        return restClient.put(shop, accessToken, endpoint, Map.of("script_tag", request))
            .map(response -> {
                try {
                    var data = response.get("script_tag");
                    return objectMapper.convertValue(data, ScriptTag.class);
                } catch (Exception e) {
                    log.error("Failed to parse update script tag response", e);
                    throw new RuntimeException("Failed to parse update script tag response", e);
                }
            });
    }
    
    /**
     * Deletes a script tag.
     */
    public Mono<Void> deleteScriptTag(String shop, String accessToken, String scriptTagId) {
        String endpoint = "/admin/api/2023-10/script_tags/" + scriptTagId + ".json";
        
        return restClient.delete(shop, accessToken, endpoint)
            .then();
    }
    
    /**
     * Gets the count of script tags.
     */
    public Mono<Integer> getScriptTagCount(String shop, String accessToken) {
        String endpoint = "/admin/api/2023-10/script_tags/count.json";
        
        return restClient.get(shop, accessToken, endpoint)
            .map(response -> {
                try {
                    return response.get("count").asInt();
                } catch (Exception e) {
                    log.error("Failed to parse script tag count response", e);
                    throw new RuntimeException("Failed to parse script tag count response", e);
                }
            });
    }
    
    // Convenience methods
    
    /**
     * Gets script tags that load on page load.
     */
    public Mono<List<ScriptTag>> getOnLoadScriptTags(String shop, String accessToken) {
        return getScriptTags(shop, accessToken)
            .map(scriptTags -> scriptTags.stream()
                .filter(ScriptTag::isOnLoad)
                .toList());
    }
    
    /**
     * Gets script tags that load when DOM is ready.
     */
    public Mono<List<ScriptTag>> getDomReadyScriptTags(String shop, String accessToken) {
        return getScriptTags(shop, accessToken)
            .map(scriptTags -> scriptTags.stream()
                .filter(ScriptTag::isDomReady)
                .toList());
    }
    
    /**
     * Gets script tags for online store.
     */
    public Mono<List<ScriptTag>> getOnlineStoreScriptTags(String shop, String accessToken) {
        return getScriptTags(shop, accessToken)
            .map(scriptTags -> scriptTags.stream()
                .filter(ScriptTag::isOnlineStore)
                .toList());
    }
    
    /**
     * Gets script tags for order status page.
     */
    public Mono<List<ScriptTag>> getOrderStatusScriptTags(String shop, String accessToken) {
        return getScriptTags(shop, accessToken)
            .map(scriptTags -> scriptTags.stream()
                .filter(ScriptTag::isOrderStatus)
                .toList());
    }
    
    /**
     * Finds script tags by source URL.
     */
    public Mono<List<ScriptTag>> findScriptTagsBySrc(String shop, String accessToken, String srcPattern) {
        return getScriptTags(shop, accessToken)
            .map(scriptTags -> scriptTags.stream()
                .filter(scriptTag -> scriptTag.getSrc() != null && 
                                   scriptTag.getSrc().contains(srcPattern))
                .toList());
    }
    
    /**
     * Creates a script tag that loads on page load.
     */
    public Mono<ScriptTag> createOnLoadScriptTag(String shop, String accessToken, String src, String displayScope, Boolean cache) {
        ScriptTagCreateRequest request = ScriptTagCreateRequest.builder()
            .src(src)
            .event(ScriptTag.EVENT_ONLOAD)
            .displayScope(displayScope)
            .cache(cache)
            .build();
        
        return createScriptTag(shop, accessToken, request);
    }
    
    /**
     * Creates a script tag that loads when DOM is ready.
     */
    public Mono<ScriptTag> createDomReadyScriptTag(String shop, String accessToken, String src, String displayScope, Boolean cache) {
        ScriptTagCreateRequest request = ScriptTagCreateRequest.builder()
            .src(src)
            .event(ScriptTag.EVENT_DOM_READY)
            .displayScope(displayScope)
            .cache(cache)
            .build();
        
        return createScriptTag(shop, accessToken, request);
    }
    
    @lombok.Data
    @lombok.NoArgsConstructor
    @lombok.AllArgsConstructor
    @lombok.Builder
    public static class ScriptTagCreateRequest {
        private String src;
        private String event;
        private String displayScope;
        private Boolean cache;
    }
    
    @lombok.Data
    @lombok.NoArgsConstructor
    @lombok.AllArgsConstructor
    @lombok.Builder
    public static class ScriptTagUpdateRequest {
        private String src;
        private String event;
        private String displayScope;
        private Boolean cache;
    }
}