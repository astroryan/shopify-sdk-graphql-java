package com.shopify.sdk.model.marketing;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.shopify.sdk.model.common.DateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MarketingActivityCreateInput {
    
    @JsonProperty("marketingActivityTitle")
    private String marketingActivityTitle;
    
    @JsonProperty("formData")
    private String formData;
    
    @JsonProperty("marketingActivityExtensionId")
    private String marketingActivityExtensionId;
    
    @JsonProperty("context")
    private String context;
    
    @JsonProperty("status")
    private MarketingActivityStatus status;
    
    @JsonProperty("utmParameters")
    private UTMInput utmParameters;
}

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MarketingActivityUpdateInput {
    
    @JsonProperty("id")
    private String id;
    
    @JsonProperty("marketingRecommendationId")
    private String marketingRecommendationId;
    
    @JsonProperty("title")
    private String title;
    
    @JsonProperty("budget")
    private MarketingActivityBudgetInput budget;
    
    @JsonProperty("adSpend")
    private MoneyInput adSpend;
    
    @JsonProperty("status")
    private MarketingActivityStatus status;
    
    @JsonProperty("targetStatus")
    private MarketingActivityStatus targetStatus;
    
    @JsonProperty("formData")
    private String formData;
    
    @JsonProperty("utmParameters")
    private UTMInput utmParameters;
    
    @JsonProperty("marketedResources")
    private List<String> marketedResources;
    
    @JsonProperty("errors")
    private String errors;
}

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MarketingEngagementInput {
    
    @JsonProperty("occurredOn")
    private String occurredOn;
    
    @JsonProperty("impressionsCount")
    private Integer impressionsCount;
    
    @JsonProperty("viewsCount")
    private Integer viewsCount;
    
    @JsonProperty("clicksCount")
    private Integer clicksCount;
    
    @JsonProperty("sharesCount")
    private Integer sharesCount;
    
    @JsonProperty("favoritesCount")
    private Integer favoritesCount;
    
    @JsonProperty("commentsCount")
    private Integer commentsCount;
    
    @JsonProperty("unsubscribesCount")
    private Integer unsubscribesCount;
    
    @JsonProperty("complaintsCount")
    private Integer complaintsCount;
    
    @JsonProperty("failsCount")
    private Integer failsCount;
    
    @JsonProperty("sendsCount")
    private Integer sendsCount;
    
    @JsonProperty("uniqueViewsCount")
    private Integer uniqueViewsCount;
    
    @JsonProperty("uniqueClicksCount")
    private Integer uniqueClicksCount;
    
    @JsonProperty("adSpend")
    private MoneyInput adSpend;
    
    @JsonProperty("isCumulative")
    private Boolean isCumulative;
    
    @JsonProperty("utcOffset")
    private String utcOffset;
    
    @JsonProperty("fetchedAt")
    private DateTime fetchedAt;
}

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
class UTMInput {
    
    @JsonProperty("campaign")
    private String campaign;
    
    @JsonProperty("source")
    private String source;
    
    @JsonProperty("medium")
    private String medium;
    
    @JsonProperty("term")
    private String term;
    
    @JsonProperty("content")
    private String content;
}

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
class MarketingActivityBudgetInput {
    
    @JsonProperty("budgetType")
    private MarketingBudgetBudgetType budgetType;
    
    @JsonProperty("total")
    private MoneyInput total;
}

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
class MoneyInput {
    
    @JsonProperty("amount")
    private String amount;
    
    @JsonProperty("currencyCode")
    private String currencyCode;
}