package com.shopify.sdk.model.marketing;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.shopify.sdk.model.common.Node;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.OffsetDateTime;

/**
 * Represents a marketing event.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MarketingEvent implements Node {
    /**
     * The unique identifier for the marketing event.
     */
    @JsonProperty("id")
    private String id;
    
    /**
     * The app that created the marketing event.
     */
    @JsonProperty("app")
    private String app;
    
    /**
     * The channel of the marketing event.
     */
    @JsonProperty("channel")
    private String channel;
    
    /**
     * The description of the marketing event.
     */
    @JsonProperty("description")
    private String description;
    
    /**
     * When the marketing event started.
     */
    @JsonProperty("startedAt")
    private OffsetDateTime startedAt;
    
    /**
     * When the marketing event ended.
     */
    @JsonProperty("endedAt")
    private OffsetDateTime endedAt;
    
    /**
     * The manage URL for the marketing event.
     */
    @JsonProperty("manageUrl")
    private String manageUrl;
    
    /**
     * The preview URL for the marketing event.
     */
    @JsonProperty("previewUrl")
    private String previewUrl;
    
    /**
     * The remote ID of the marketing event.
     */
    @JsonProperty("remoteId")
    private String remoteId;
    
    /**
     * The scheduled end time of the marketing event.
     */
    @JsonProperty("scheduledToEndAt")
    private OffsetDateTime scheduledToEndAt;
    
    /**
     * The source and medium of the marketing event.
     */
    @JsonProperty("sourceAndMedium")
    private String sourceAndMedium;
    
    /**
     * The type of the marketing event.
     */
    @JsonProperty("type")
    private String type;
    
    /**
     * The UTM parameters for the marketing event.
     */
    @JsonProperty("utmParameters")
    private String utmParameters;
}