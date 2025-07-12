package com.shopify.sdk.model.marketing;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.shopify.sdk.model.common.Node;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.OffsetDateTime;

/**
 * Represents a marketing activity.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MarketingActivity implements Node {
    /**
     * The unique identifier for the marketing activity.
     */
    @JsonProperty("id")
    private String id;
    
    /**
     * The title of the marketing activity.
     */
    @JsonProperty("title")
    private String title;
    
    /**
     * The marketing channel.
     */
    @JsonProperty("marketingChannel")
    private String marketingChannel;
    
    /**
     * The status of the marketing activity.
     */
    @JsonProperty("status")
    private MarketingActivityStatus status;
    
    /**
     * The tactic of the marketing activity.
     */
    @JsonProperty("tactic")
    private MarketingTactic tactic;
    
    /**
     * The budget for the marketing activity.
     */
    @JsonProperty("budget")
    private String budget;
    
    /**
     * When the marketing activity was created.
     */
    @JsonProperty("createdAt")
    private OffsetDateTime createdAt;
    
    /**
     * When the marketing activity was last updated.
     */
    @JsonProperty("updatedAt")
    private OffsetDateTime updatedAt;
}