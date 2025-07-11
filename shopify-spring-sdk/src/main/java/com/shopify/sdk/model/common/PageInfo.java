package com.shopify.sdk.model.common;

import io.leangen.graphql.annotations.GraphQLQuery;
import lombok.*;

/**
 * Information about pagination in a connection.
 * Used for cursor-based pagination in GraphQL connections.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PageInfo {
    
    @GraphQLQuery(name = "hasNextPage", description = "Whether there are more pages to fetch following the current page")
    private Boolean hasNextPage;
    
    @GraphQLQuery(name = "hasPreviousPage", description = "Whether there are more pages to fetch prior to the current page")
    private Boolean hasPreviousPage;
    
    @GraphQLQuery(name = "startCursor", description = "The cursor corresponding to the first node in edges")
    private String startCursor;
    
    @GraphQLQuery(name = "endCursor", description = "The cursor corresponding to the last node in edges")
    private String endCursor;
}