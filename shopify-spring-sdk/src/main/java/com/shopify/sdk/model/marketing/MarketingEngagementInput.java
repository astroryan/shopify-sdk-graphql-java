package com.shopify.sdk.model.marketing;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.OffsetDateTime;

/**
 * Input for creating a marketing engagement.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MarketingEngagementInput {
    /**
     * The marketing activity ID.
     */
    @JsonProperty("marketingActivityId")
    private String marketingActivityId;
    
    /**
     * The remote ID.
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
     * The number of impressions.
     */
    @JsonProperty("impressionsCount")
    private Integer impressionsCount;
    
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