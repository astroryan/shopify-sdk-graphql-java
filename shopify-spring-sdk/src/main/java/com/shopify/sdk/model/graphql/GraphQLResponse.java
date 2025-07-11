package com.shopify.sdk.model.graphql;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.JsonNode;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;

/**
 * Represents a GraphQL response
 * @param <T> The type of the data in the response
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class GraphQLResponse<T> {
    /**
     * The data portion of the response
     */
    @JsonProperty("data")
    private T data;
    
    /**
     * Any errors that occurred during the request
     */
    @JsonProperty("errors")
    private List<GraphQLError> errors;
    
    /**
     * Extensions data (optional)
     */
    @JsonProperty("extensions")
    private Map<String, Object> extensions;
    
    /**
     * Check if the response has errors
     * @return true if there are errors, false otherwise
     */
    public boolean hasErrors() {
        return errors != null && !errors.isEmpty();
    }
    
    /**
     * Get the first error message if any
     * @return The first error message or null
     */
    public String getFirstErrorMessage() {
        if (hasErrors()) {
            return errors.get(0).getMessage();
        }
        return null;
    }
    
    /**
     * Get the raw data as JsonNode (for backward compatibility)
     * @return The data as JsonNode
     */
    public JsonNode getDataAsJsonNode() {
        if (data == null) {
            return null;
        }
        // Convert data to JsonNode if needed
        if (data instanceof JsonNode) {
            return (JsonNode) data;
        }
        // For other types, this would need ObjectMapper conversion
        return null;
    }
    
    /**
     * Check if the response is successful (has data and no errors)
     * @return true if successful, false otherwise
     */
    public boolean isSuccessful() {
        return data != null && !hasErrors();
    }
}