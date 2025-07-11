package com.shopify.sdk.model.graphql;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import java.util.List;
import java.util.Map;

/**
 * Represents a location in a GraphQL query/response
 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class Location {
    private int line;
    private int column;
}
