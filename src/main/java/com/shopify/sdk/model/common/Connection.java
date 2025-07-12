package com.shopify.sdk.model.common;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Generic connection in GraphQL.
 * Represents a paginated list of items.
 *
 * @param <T> the type of nodes in this connection
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Connection<T> {
    
    /**
     * A list of edges.
     */
    @JsonProperty("edges")
    private List<Edge<T>> edges;
    
    /**
     * Information to aid in pagination.
     */
    @JsonProperty("pageInfo")
    private PageInfo pageInfo;
    
    /**
     * Convenience method to get just the nodes from the edges.
     *
     * @return list of nodes
     */
    public List<T> getNodes() {
        if (edges == null) {
            return List.of();
        }
        return edges.stream()
            .map(Edge::getNode)
            .collect(Collectors.toList());
    }
    
    /**
     * Gets the total count of items if available.
     * Note: Not all connections include totalCount.
     *
     * @return the total count, or null if not available
     */
    public Integer getTotalCount() {
        // This will be overridden in specific connection types that have totalCount
        return null;
    }
    
    /**
     * Checks if there are any items in this connection.
     *
     * @return true if there are items, false otherwise
     */
    public boolean hasItems() {
        return edges != null && !edges.isEmpty();
    }
    
    /**
     * Gets the cursor of the first item.
     *
     * @return the start cursor, or null if no items
     */
    public String getStartCursor() {
        return pageInfo != null ? pageInfo.getStartCursor() : null;
    }
    
    /**
     * Gets the cursor of the last item.
     *
     * @return the end cursor, or null if no items
     */
    public String getEndCursor() {
        return pageInfo != null ? pageInfo.getEndCursor() : null;
    }
    
    /**
     * Checks if there are more items when paginating forwards.
     *
     * @return true if there are more items, false otherwise
     */
    public boolean hasNextPage() {
        return pageInfo != null && Boolean.TRUE.equals(pageInfo.getHasNextPage());
    }
    
    /**
     * Checks if there are more items when paginating backwards.
     *
     * @return true if there are more items, false otherwise
     */
    public boolean hasPreviousPage() {
        return pageInfo != null && Boolean.TRUE.equals(pageInfo.getHasPreviousPage());
    }
}