package com.shopify.sdk.model.marketing;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.shopify.sdk.model.common.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MarketingEvent {
    
    @JsonProperty("id")
    private ID id;
    
    @JsonProperty("app")
    private App app;
    
    @JsonProperty("channel")
    private MarketingChannel channel;
    
    @JsonProperty("description")
    private String description;
    
    @JsonProperty("endedAt")
    private DateTime endedAt;
    
    @JsonProperty("legacyResourceId")
    private String legacyResourceId;
    
    @JsonProperty("manageUrl")
    private String manageUrl;
    
    @JsonProperty("previewUrl")
    private String previewUrl;
    
    @JsonProperty("remoteId")
    private String remoteId;
    
    @JsonProperty("scheduledToEndAt")
    private DateTime scheduledToEndAt;
    
    @JsonProperty("sourceAndMedium")
    private String sourceAndMedium;
    
    @JsonProperty("startedAt")
    private DateTime startedAt;
    
    @JsonProperty("targetTypeDisplayText")
    private String targetTypeDisplayText;
    
    @JsonProperty("type")
    private MarketingTactic type;
    
    @JsonProperty("utmCampaign")
    private String utmCampaign;
    
    @JsonProperty("utmMedium")
    private String utmMedium;
    
    @JsonProperty("utmSource")
    private String utmSource;
}

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MarketingActivityConnection {
    
    @JsonProperty("edges")
    private List<MarketingActivityEdge> edges;
    
    @JsonProperty("pageInfo")
    private PageInfo pageInfo;
}

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
class MarketingActivityEdge {
    
    @JsonProperty("node")
    private MarketingActivity node;
    
    @JsonProperty("cursor")
    private String cursor;
}

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MarketingEventConnection {
    
    @JsonProperty("edges")
    private List<MarketingEventEdge> edges;
    
    @JsonProperty("pageInfo")
    private PageInfo pageInfo;
}

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
class MarketingEventEdge {
    
    @JsonProperty("node")
    private MarketingEvent node;
    
    @JsonProperty("cursor")
    private String cursor;
}