package com.shopify.sdk.model.marketing;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.shopify.sdk.model.common.Node;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.OffsetDateTime;

/**
 * Represents a marketing engagement.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MarketingEngagement implements Node {
    /**
     * The unique identifier for the marketing engagement.
     */
    @JsonProperty("id")
    private String id;
    
    /**
     * The marketing activity ID.
     */
    @JsonProperty("marketingActivityId")
    private String marketingActivityId;
    
    /**
     * The remote ID of the engagement.
     */
    @JsonProperty("remoteId")
    private String remoteId;
    
    /**
     * The ad spend.
     */
    @JsonProperty("adSpend")
    private String adSpend;
    
    /**
     * The number of clicks.
     */
    @JsonProperty("clicksCount")
    private Integer clicksCount;
    
    /**
     * The number of comments.
     */
    @JsonProperty("commentsCount")
    private Integer commentsCount;
    
    /**
     * The number of complaints.
     */
    @JsonProperty("complaintsCount")
    private Integer complaintsCount;
    
    /**
     * The number of favorites.
     */
    @JsonProperty("favoritesCount")
    private Integer favoritesCount;
    
    /**
     * The number of impressions.
     */
    @JsonProperty("impressionsCount")
    private Integer impressionsCount;
    
    /**
     * The number of shares.
     */
    @JsonProperty("sharesCount")
    private Integer sharesCount;
    
    /**
     * The number of views.
     */
    @JsonProperty("viewsCount")
    private Integer viewsCount;
    
    /**
     * Whether this engagement is cumulative.
     */
    @JsonProperty("isCumulative")
    private Boolean isCumulative;
    
    /**
     * When the engagement occurred.
     */
    @JsonProperty("occurredOn")
    private OffsetDateTime occurredOn;
}