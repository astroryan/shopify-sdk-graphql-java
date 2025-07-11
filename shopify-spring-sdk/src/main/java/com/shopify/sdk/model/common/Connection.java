package com.shopify.sdk.model.common;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public abstract class Connection<T> {
    
    @JsonProperty("edges")
    private List<Edge<T>> edges;
    
    @JsonProperty("nodes")
    private List<T> nodes;
    
    @JsonProperty("pageInfo")
    private PageInfo pageInfo;
    
    @JsonProperty("totalCount")
    private Integer totalCount;
}