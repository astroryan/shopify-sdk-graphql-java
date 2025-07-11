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
public class MarketingActivity {
    
    @JsonProperty("id")
    private ID id;
    
    @JsonProperty("activityTitle")
    private String activityTitle;
    
    @JsonProperty("adSpend")
    private MoneyV2 adSpend;
    
    @JsonProperty("app")
    private App app;
    
    @JsonProperty("appErrors")
    private MarketingActivityExtensionAppErrors appErrors;
    
    @JsonProperty("budget")
    private MarketingBudget budget;
    
    @JsonProperty("createdAt")
    private DateTime createdAt;
    
    @JsonProperty("engagementSummary")
    private MarketingEngagement engagementSummary;
    
    @JsonProperty("errors")
    private List<String> errors;
    
    @JsonProperty("formData")
    private String formData;
    
    @JsonProperty("hierarchyLevel")
    private MarketingActivityHierarchyLevel hierarchyLevel;
    
    @JsonProperty("inMainWorkflow")
    private Boolean inMainWorkflow;
    
    @JsonProperty("marketingChannel")
    private MarketingChannel marketingChannel;
    
    @JsonProperty("marketingChannelType")
    private MarketingChannelType marketingChannelType;
    
    @JsonProperty("marketingEvent")
    private MarketingEvent marketingEvent;
    
    @JsonProperty("parentActivityId")
    private ID parentActivityId;
    
    @JsonProperty("parentRemoteId")
    private String parentRemoteId;
    
    @JsonProperty("sourceAndMedium")
    private String sourceAndMedium;
    
    @JsonProperty("status")
    private MarketingActivityStatus status;
    
    @JsonProperty("statusBadgeType")
    private MarketingActivityStatusBadgeType statusBadgeType;
    
    @JsonProperty("statusBadgeTypeV2")
    private MarketingActivityStatusBadgeType statusBadgeTypeV2;
    
    @JsonProperty("statusLabel")
    private String statusLabel;
    
    @JsonProperty("statusTransitionedAt")
    private DateTime statusTransitionedAt;
    
    @JsonProperty("tactic")
    private MarketingTactic tactic;
    
    @JsonProperty("targetStatus")
    private MarketingActivityStatus targetStatus;
    
    @JsonProperty("title")
    private String title;
    
    @JsonProperty("updatedAt")
    private DateTime updatedAt;
    
    @JsonProperty("urlParameterValue")
    private String urlParameterValue;
    
    @JsonProperty("utmParameters")
    private UTMParameters utmParameters;
}

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
class App {
    
    @JsonProperty("id")
    private ID id;
    
    @JsonProperty("title")
    private String title;
}

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
class MarketingActivityExtensionAppErrors {
    
    @JsonProperty("code")
    private String code;
}

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
class MarketingBudget {
    
    @JsonProperty("budgetType")
    private MarketingBudgetBudgetType budgetType;
    
    @JsonProperty("total")
    private MoneyV2 total;
}

public enum MarketingBudgetBudgetType {
    DAILY,
    LIFETIME
}

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
class MarketingEngagement {
    
    @JsonProperty("adSpend")
    private MoneyV2 adSpend;
    
    @JsonProperty("clicksCount")
    private Integer clicksCount;
    
    @JsonProperty("commentsCount")
    private Integer commentsCount;
    
    @JsonProperty("complaintsCount")
    private Integer complaintsCount;
    
    @JsonProperty("failsCount")
    private Integer failsCount;
    
    @JsonProperty("favoritesCount")
    private Integer favoritesCount;
    
    @JsonProperty("fetchedAt")
    private DateTime fetchedAt;
    
    @JsonProperty("impressionsCount")
    private Integer impressionsCount;
    
    @JsonProperty("isCumulative")
    private Boolean isCumulative;
    
    @JsonProperty("occurredOn")
    private Date occurredOn;
    
    @JsonProperty("sendsCount")
    private Integer sendsCount;
    
    @JsonProperty("sharesCount")
    private Integer sharesCount;
    
    @JsonProperty("uniqueClicksCount")
    private Integer uniqueClicksCount;
    
    @JsonProperty("uniqueViewsCount")
    private Integer uniqueViewsCount;
    
    @JsonProperty("unsubscribesCount")
    private Integer unsubscribesCount;
    
    @JsonProperty("utcOffset")
    private String utcOffset;
    
    @JsonProperty("viewsCount")
    private Integer viewsCount;
}

public enum MarketingActivityHierarchyLevel {
    AD,
    AD_GROUP,
    CAMPAIGN
}

public enum MarketingActivityStatus {
    ACTIVE,
    DELETED,
    DELETED_EXTERNALLY,
    DISCONNECTED,
    DRAFT,
    FAILED,
    INACTIVE,
    PAUSED,
    PENDING,
    SCHEDULED,
    UNDEFINED
}

public enum MarketingActivityStatusBadgeType {
    DEFAULT,
    INFO,
    SUCCESS,
    WARNING
}

public enum MarketingChannel {
    AFFILIATE,
    DISPLAY,
    EMAIL,
    REFERRAL,
    SEARCH,
    SOCIAL
}

public enum MarketingChannelType {
    AFFILIATE,
    DISPLAY,
    EMAIL,
    MESSAGE,
    REFERRAL,
    SEARCH,
    SOCIAL
}

public enum MarketingTactic {
    ABANDONED_CART,
    AD,
    AFFILIATE,
    DIRECT,
    DISPLAY,
    LINK,
    MESSAGE,
    NEWSLETTER,
    NOTIFICATION,
    POST,
    RETARGETING,
    SEO,
    STOREFRONT_APP,
    TRANSACTIONAL
}

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
class UTMParameters {
    
    @JsonProperty("campaign")
    private String campaign;
    
    @JsonProperty("content")
    private String content;
    
    @JsonProperty("medium")
    private String medium;
    
    @JsonProperty("source")
    private String source;
    
    @JsonProperty("term")
    private String term;
}

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
class Date {
    
    @JsonProperty("__typename")
    private String typename;
}