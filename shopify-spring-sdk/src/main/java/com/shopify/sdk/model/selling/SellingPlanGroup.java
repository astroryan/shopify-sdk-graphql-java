package com.shopify.sdk.model.selling;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.shopify.sdk.model.common.Node;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.OffsetDateTime;
import java.util.List;

/**
 * Represents a selling plan group.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SellingPlanGroup implements Node {
    /**
     * A globally unique identifier.
     */
    @JsonProperty("id")
    private String id;
    
    /**
     * The name of the selling plan group.
     */
    @JsonProperty("name")
    private String name;
    
    /**
     * The summary of the selling plan group.
     */
    @JsonProperty("summary")
    private String summary;
    
    /**
     * The merchant code of the selling plan group.
     */
    @JsonProperty("merchantCode")
    private String merchantCode;
    
    /**
     * The app that created the selling plan group.
     */
    @JsonProperty("appId")
    private String appId;
    
    /**
     * The options for the selling plan group.
     */
    @JsonProperty("options")
    private List<String> options;
    
    /**
     * The selling plans in this group.
     */
    @JsonProperty("sellingPlans")
    private SellingPlanConnection sellingPlans;
    
    /**
     * The date and time when the selling plan group was created.
     */
    @JsonProperty("createdAt")
    private OffsetDateTime createdAt;
}