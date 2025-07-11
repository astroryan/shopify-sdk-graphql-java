package com.shopify.sdk.model.common;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * Represents a paginated connection in GraphQL
 * @param <T> The type of nodes in the connection
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Connection<T> {
    
    @JsonProperty("edges")
    private List<Edge<T>> edges;
    
    @JsonProperty("nodes")
    private List<T> nodes;
    
    @JsonProperty("pageInfo")
    private PageInfo pageInfo;
    
    @JsonProperty("totalCount")
    private Integer totalCount;
}