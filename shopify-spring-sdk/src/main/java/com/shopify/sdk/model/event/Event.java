package com.shopify.sdk.model.event;

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
public class Event {
    
    @JsonProperty("id")
    private ID id;
    
    @JsonProperty("action")
    private String action;
    
    @JsonProperty("appTitle")
    private String appTitle;
    
    @JsonProperty("attributeToApp")
    private Boolean attributeToApp;
    
    @JsonProperty("attributeToUser")
    private Boolean attributeToUser;
    
    @JsonProperty("createdAt")
    private DateTime createdAt;
    
    @JsonProperty("criticalAlert")
    private Boolean criticalAlert;
    
    @JsonProperty("message")
    private String message;
    
    @JsonProperty("path")
    private String path;
    
    @JsonProperty("subjectId")
    private ID subjectId;
    
    @JsonProperty("subjectType")
    private EventSubjectType subjectType;
    
    @JsonProperty("verb")
    private String verb;
}

public enum EventSubjectType {
    ARTICLE,
    BLOG,
    COLLECTION,
    COMMENT,
    CUSTOMER,
    DISCOUNT,
    DRAFT_ORDER,
    ORDER,
    PAGE,
    PRODUCT,
    VARIANT
}

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EventConnection {
    
    @JsonProperty("edges")
    private List<EventEdge> edges;
    
    @JsonProperty("pageInfo")
    private PageInfo pageInfo;
}

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EventEdge {
    
    @JsonProperty("node")
    private Event node;
    
    @JsonProperty("cursor")
    private String cursor;
}

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
    
    @JsonProperty("budget")
    private MarketingBudget budget;
    
    @JsonProperty("marketingActivity")
    private MarketingActivity marketingActivity;
}

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
    private MarketingActivityAppErrors appErrors;
    
    @JsonProperty("budget")
    private MarketingBudget budget;
    
    @JsonProperty("createdAt")
    private DateTime createdAt;
    
    @JsonProperty("formData")
    private String formData;
    
    @JsonProperty("hierarchyLevel")
    private MarketingActivityHierarchyLevel hierarchyLevel;
    
    @JsonProperty("inMainWorkflow")
    private Boolean inMainWorkflow;
    
    @JsonProperty("marketingChannel")
    private MarketingChannel marketingChannel;
    
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
    private MarketingActivityExtensionAppErrorCode statusBadgeTypeV2;
    
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
public class MarketingChannel {
    
    @JsonProperty("id")
    private ID id;
}

public enum MarketingTactic {
    ABANDONED_CART,
    AD,
    AFFILIATE,
    DIRECT,
    DISPLAY,
    EMAIL,
    EVENT,
    LINK,
    LOYALTY,
    MESSAGE,
    NEWSLETTER,
    NOTIFICATION,
    POST,
    RETARGETING,
    SEO,
    TRANSACTIONAL,
    STOREFRONT
}

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MarketingBudget {
    
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
public class MarketingActivityAppErrors {
    
    @JsonProperty("hasErrors")
    private Boolean hasErrors;
}

public enum MarketingActivityHierarchyLevel {
    CAMPAIGN,
    AD_GROUP,
    AD
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
    ATTENTION_REQUIRED,
    DRAFT,
    EXTAPP_ERROR,
    NOT_CONNECTED,
    PAUSED,
    PENDING,
    SCHEDULED,
    SUCCESS
}

public enum MarketingActivityExtensionAppErrorCode {
    NOT_ONBOARDED_ERROR,
    VALIDATION_ERROR,
    PLATFORM_ERROR,
    INSTALL_REQUIRED_ERROR
}

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UTMParameters {
    
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
public class App {
    
    @JsonProperty("id")
    private ID id;
    
    @JsonProperty("apiKey")
    private String apiKey;
    
    @JsonProperty("appStoreAppUrl")
    private String appStoreAppUrl;
    
    @JsonProperty("appStoreDeveloperUrl")
    private String appStoreDeveloperUrl;
    
    @JsonProperty("availableAccessScopes")
    private List<AccessScope> availableAccessScopes;
    
    @JsonProperty("banner")
    private Image banner;
    
    @JsonProperty("description")
    private String description;
    
    @JsonProperty("developerName")
    private String developerName;
    
    @JsonProperty("developerType")
    private AppDeveloperType developerType;
    
    @JsonProperty("embedded")
    private Boolean embedded;
    
    @JsonProperty("failedRequirements")
    private List<FailedRequirement> failedRequirements;
    
    @JsonProperty("features")
    private List<String> features;
    
    @JsonProperty("feedback")
    private AppFeedback feedback;
    
    @JsonProperty("handle")
    private String handle;
    
    @JsonProperty("icon")
    private Image icon;
    
    @JsonProperty("installUrl")
    private String installUrl;
    
    @JsonProperty("installation")
    private AppInstallation installation;
    
    @JsonProperty("isPostPurchaseAppInUse")
    private Boolean isPostPurchaseAppInUse;
    
    @JsonProperty("previouslyInstalled")
    private Boolean previouslyInstalled;
    
    @JsonProperty("pricingDetails")
    private String pricingDetails;
    
    @JsonProperty("pricingDetailsSummary")
    private String pricingDetailsSummary;
    
    @JsonProperty("privacyPolicyUrl")
    private String privacyPolicyUrl;
    
    @JsonProperty("publicCategory")
    private AppPublicCategory publicCategory;
    
    @JsonProperty("published")
    private Boolean published;
    
    @JsonProperty("requestedAccessScopes")
    private List<AccessScope> requestedAccessScopes;
    
    @JsonProperty("screenshots")
    private List<Image> screenshots;
    
    @JsonProperty("shopifyDeveloped")
    private Boolean shopifyDeveloped;
    
    @JsonProperty("title")
    private String title;
    
    @JsonProperty("uninstallMessage")
    private String uninstallMessage;
    
    @JsonProperty("webhookApiVersion")
    private String webhookApiVersion;
}

public enum AppDeveloperType {
    SHOPIFY,
    PARTNER,
    MERCHANT,
    UNKNOWN
}

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AccessScope {
    
    @JsonProperty("description")
    private String description;
    
    @JsonProperty("handle")
    private String handle;
}

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FailedRequirement {
    
    @JsonProperty("action")
    private NavigationItem action;
    
    @JsonProperty("message")
    private String message;
}

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class NavigationItem {
    
    @JsonProperty("id")
    private String id;
    
    @JsonProperty("title")
    private String title;
    
    @JsonProperty("url")
    private String url;
}

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AppFeedback {
    
    @JsonProperty("app")
    private App app;
    
    @JsonProperty("link")
    private Link link;
    
    @JsonProperty("messages")
    private List<UserError> messages;
}

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Link {
    
    @JsonProperty("label")
    private String label;
    
    @JsonProperty("url")
    private String url;
}

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Image {
    
    @JsonProperty("altText")
    private String altText;
    
    @JsonProperty("height")
    private Integer height;
    
    @JsonProperty("id")
    private ID id;
    
    @JsonProperty("metafield")
    private Metafield metafield;
    
    @JsonProperty("metafields")
    private MetafieldConnection metafields;
    
    @JsonProperty("originalSrc")
    private String originalSrc;
    
    @JsonProperty("transformedSrc")
    private String transformedSrc;
    
    @JsonProperty("url")
    private String url;
    
    @JsonProperty("width")
    private Integer width;
}

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Metafield {
    
    @JsonProperty("id")
    private ID id;
    
    @JsonProperty("namespace")
    private String namespace;
    
    @JsonProperty("key")
    private String key;
    
    @JsonProperty("value")
    private String value;
}

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MetafieldConnection {
    
    @JsonProperty("edges")
    private List<MetafieldEdge> edges;
    
    @JsonProperty("pageInfo")
    private PageInfo pageInfo;
}

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MetafieldEdge {
    
    @JsonProperty("node")
    private Metafield node;
    
    @JsonProperty("cursor")
    private String cursor;
}

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AppInstallation {
    
    @JsonProperty("id")
    private ID id;
    
    @JsonProperty("accessScopes")
    private List<AccessScope> accessScopes;
    
    @JsonProperty("activeSubscriptions")
    private List<AppSubscription> activeSubscriptions;
    
    @JsonProperty("allSubscriptions")
    private AppSubscriptionConnection allSubscriptions;
    
    @JsonProperty("app")
    private App app;
    
    @JsonProperty("credits")
    private AppCreditConnection credits;
    
    @JsonProperty("launchUrl")
    private String launchUrl;
    
    @JsonProperty("oneTimePurchases")
    private AppPurchaseOneTimeConnection oneTimePurchases;
    
    @JsonProperty("publication")
    private Publication publication;
    
    @JsonProperty("revenueAttributionRecords")
    private AppRevenueAttributionRecordConnection revenueAttributionRecords;
    
    @JsonProperty("uninstallUrl")
    private String uninstallUrl;
}

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AppSubscription {
    
    @JsonProperty("id")
    private ID id;
    
    @JsonProperty("createdAt")
    private DateTime createdAt;
    
    @JsonProperty("currentPeriodEnd")
    private DateTime currentPeriodEnd;
    
    @JsonProperty("name")
    private String name;
    
    @JsonProperty("returnUrl")
    private String returnUrl;
    
    @JsonProperty("status")
    private AppSubscriptionStatus status;
    
    @JsonProperty("test")
    private Boolean test;
    
    @JsonProperty("trialDays")
    private Integer trialDays;
}

public enum AppSubscriptionStatus {
    ACCEPTED,
    ACTIVE,
    CANCELLED,
    DECLINED,
    EXPIRED,
    FROZEN,
    PENDING
}

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AppSubscriptionConnection {
    
    @JsonProperty("edges")
    private List<AppSubscriptionEdge> edges;
    
    @JsonProperty("pageInfo")
    private PageInfo pageInfo;
}

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AppSubscriptionEdge {
    
    @JsonProperty("node")
    private AppSubscription node;
    
    @JsonProperty("cursor")
    private String cursor;
}

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AppCreditConnection {
    
    @JsonProperty("edges")
    private List<AppCreditEdge> edges;
    
    @JsonProperty("pageInfo")
    private PageInfo pageInfo;
}

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AppCreditEdge {
    
    @JsonProperty("node")
    private AppCredit node;
    
    @JsonProperty("cursor")
    private String cursor;
}

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AppCredit {
    
    @JsonProperty("id")
    private ID id;
    
    @JsonProperty("amount")
    private MoneyV2 amount;
    
    @JsonProperty("createdAt")
    private DateTime createdAt;
    
    @JsonProperty("description")
    private String description;
    
    @JsonProperty("test")
    private Boolean test;
}

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AppPurchaseOneTimeConnection {
    
    @JsonProperty("edges")
    private List<AppPurchaseOneTimeEdge> edges;
    
    @JsonProperty("pageInfo")
    private PageInfo pageInfo;
}

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AppPurchaseOneTimeEdge {
    
    @JsonProperty("node")
    private AppPurchaseOneTime node;
    
    @JsonProperty("cursor")
    private String cursor;
}

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AppPurchaseOneTime {
    
    @JsonProperty("id")
    private ID id;
    
    @JsonProperty("createdAt")
    private DateTime createdAt;
    
    @JsonProperty("name")
    private String name;
    
    @JsonProperty("price")
    private MoneyV2 price;
    
    @JsonProperty("status")
    private AppPurchaseStatus status;
    
    @JsonProperty("test")
    private Boolean test;
}

public enum AppPurchaseStatus {
    ACCEPTED,
    ACTIVE,
    DECLINED,
    EXPIRED,
    PENDING
}

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Publication {
    
    @JsonProperty("id")
    private ID id;
    
    @JsonProperty("app")
    private App app;
    
    @JsonProperty("collectionPublicationsV3")
    private ResourcePublicationV2Connection collectionPublicationsV3;
    
    @JsonProperty("collections")
    private CollectionConnection collections;
    
    @JsonProperty("hasCollection")
    private Boolean hasCollection;
    
    @JsonProperty("name")
    private String name;
    
    @JsonProperty("productPublicationsV3")
    private ResourcePublicationV2Connection productPublicationsV3;
    
    @JsonProperty("products")
    private ProductConnection products;
    
    @JsonProperty("supportsFuturePublishing")
    private Boolean supportsFuturePublishing;
}

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ResourcePublicationV2Connection {
    
    @JsonProperty("edges")
    private List<ResourcePublicationV2Edge> edges;
    
    @JsonProperty("pageInfo")
    private PageInfo pageInfo;
}

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ResourcePublicationV2Edge {
    
    @JsonProperty("node")
    private ResourcePublicationV2 node;
    
    @JsonProperty("cursor")
    private String cursor;
}

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ResourcePublicationV2 {
    
    @JsonProperty("isPublished")
    private Boolean isPublished;
    
    @JsonProperty("publication")
    private Publication publication;
    
    @JsonProperty("publishDate")
    private DateTime publishDate;
    
    @JsonProperty("publishable")
    private Publishable publishable;
}

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Publishable {
    
    @JsonProperty("__typename")
    private String typename;
}

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CollectionConnection {
    
    @JsonProperty("edges")
    private List<CollectionEdge> edges;
    
    @JsonProperty("pageInfo")
    private PageInfo pageInfo;
}

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CollectionEdge {
    
    @JsonProperty("node")
    private Collection node;
    
    @JsonProperty("cursor")
    private String cursor;
}

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Collection {
    
    @JsonProperty("id")
    private ID id;
    
    @JsonProperty("title")
    private String title;
}

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductConnection {
    
    @JsonProperty("edges")
    private List<ProductEdge> edges;
    
    @JsonProperty("pageInfo")
    private PageInfo pageInfo;
}

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductEdge {
    
    @JsonProperty("node")
    private Product node;
    
    @JsonProperty("cursor")
    private String cursor;
}

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Product {
    
    @JsonProperty("id")
    private ID id;
    
    @JsonProperty("title")
    private String title;
}

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AppRevenueAttributionRecordConnection {
    
    @JsonProperty("edges")
    private List<AppRevenueAttributionRecordEdge> edges;
    
    @JsonProperty("pageInfo")
    private PageInfo pageInfo;
}

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AppRevenueAttributionRecordEdge {
    
    @JsonProperty("node")
    private AppRevenueAttributionRecord node;
    
    @JsonProperty("cursor")
    private String cursor;
}

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AppRevenueAttributionRecord {
    
    @JsonProperty("id")
    private ID id;
    
    @JsonProperty("amount")
    private MoneyV2 amount;
    
    @JsonProperty("capturedAt")
    private DateTime capturedAt;
    
    @JsonProperty("createdAt")
    private DateTime createdAt;
    
    @JsonProperty("idempotencyKey")
    private String idempotencyKey;
    
    @JsonProperty("test")
    private Boolean test;
    
    @JsonProperty("type")
    private AppRevenueAttributionType type;
}

public enum AppRevenueAttributionType {
    APPLICATION_PURCHASE,
    APPLICATION_SUBSCRIPTION,
    APPLICATION_USAGE,
    OTHER
}

public enum AppPublicCategory {
    PRIVATE,
    PUBLIC,
    CUSTOM,
    OTHER
}