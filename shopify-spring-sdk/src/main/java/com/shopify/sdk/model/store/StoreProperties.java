package com.shopify.sdk.model.store;

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
public class Shop {
    
    @JsonProperty("id")
    private ID id;
    
    @JsonProperty("name")
    private String name;
    
    @JsonProperty("email")
    private String email;
    
    @JsonProperty("description")
    private String description;
    
    @JsonProperty("contactEmail")
    private String contactEmail;
    
    @JsonProperty("currencyCode")
    private CurrencyCode currencyCode;
    
    @JsonProperty("currencyFormats")
    private CurrencyFormats currencyFormats;
    
    @JsonProperty("customerAccounts")
    private ShopCustomerAccountsSetting customerAccounts;
    
    @JsonProperty("domains")
    private DomainConnection domains;
    
    @JsonProperty("enabledCurrencies")
    private List<CurrencyCode> enabledCurrencies;
    
    @JsonProperty("enabledLocales")
    private List<ShopLocale> enabledLocales;
    
    @JsonProperty("features")
    private ShopFeatures features;
    
    @JsonProperty("fulfillmentServices")
    private FulfillmentServiceConnection fulfillmentServices;
    
    @JsonProperty("ianaTimezone")
    private String ianaTimezone;
    
    @JsonProperty("myshopifyDomain")
    private String myshopifyDomain;
    
    @JsonProperty("orderNumberFormatPrefix")
    private String orderNumberFormatPrefix;
    
    @JsonProperty("orderNumberFormatSuffix")
    private String orderNumberFormatSuffix;
    
    @JsonProperty("paymentSettings")
    private PaymentSettings paymentSettings;
    
    @JsonProperty("plan")
    private ShopPlan plan;
    
    @JsonProperty("primaryDomain")
    private Domain primaryDomain;
    
    @JsonProperty("privateMetafields")
    private PrivateMetafieldConnection privateMetafields;
    
    @JsonProperty("publicationCount")
    private Integer publicationCount;
    
    @JsonProperty("resourceLimits")
    private ShopResourceLimits resourceLimits;
    
    @JsonProperty("richTextEditorUrl")
    private String richTextEditorUrl;
    
    @JsonProperty("search")
    private SearchResult search;
    
    @JsonProperty("setupRequired")
    private Boolean setupRequired;
    
    @JsonProperty("shipsToCountries")
    private List<CountryCode> shipsToCountries;
    
    @JsonProperty("shopOwnerName")
    private String shopOwnerName;
    
    @JsonProperty("storefrontAccessTokens")
    private StorefrontAccessTokenConnection storefrontAccessTokens;
    
    @JsonProperty("taxShipping")
    private Boolean taxShipping;
    
    @JsonProperty("taxesIncluded")
    private Boolean taxesIncluded;
    
    @JsonProperty("timezone")
    private String timezone;
    
    @JsonProperty("transactionalSmsDisabled")
    private Boolean transactionalSmsDisabled;
    
    @JsonProperty("translations")
    private List<PublishedTranslation> translations;
    
    @JsonProperty("unitSystem")
    private UnitSystem unitSystem;
    
    @JsonProperty("updatedAt")
    private DateTime updatedAt;
    
    @JsonProperty("uploadedImagesByIds")
    private List<Image> uploadedImagesByIds;
    
    @JsonProperty("url")
    private String url;
    
    @JsonProperty("weightUnit")
    private WeightUnit weightUnit;
}

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CurrencyFormats {
    
    @JsonProperty("moneyFormat")
    private String moneyFormat;
    
    @JsonProperty("moneyInEmailsFormat")
    private String moneyInEmailsFormat;
    
    @JsonProperty("moneyWithCurrencyFormat")
    private String moneyWithCurrencyFormat;
    
    @JsonProperty("moneyWithCurrencyInEmailsFormat")
    private String moneyWithCurrencyInEmailsFormat;
}

public enum ShopCustomerAccountsSetting {
    OPTIONAL,
    REQUIRED,
    DISABLED
}

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Domain {
    
    @JsonProperty("id")
    private ID id;
    
    @JsonProperty("host")
    private String host;
    
    @JsonProperty("localization")
    private DomainLocalization localization;
    
    @JsonProperty("sslEnabled")
    private Boolean sslEnabled;
    
    @JsonProperty("url")
    private String url;
}

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DomainLocalization {
    
    @JsonProperty("alternateLocales")
    private List<String> alternateLocales;
    
    @JsonProperty("country")
    private String country;
    
    @JsonProperty("defaultLocale")
    private String defaultLocale;
}

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ShopLocale {
    
    @JsonProperty("locale")
    private String locale;
    
    @JsonProperty("name")
    private String name;
    
    @JsonProperty("primary")
    private Boolean primary;
    
    @JsonProperty("published")
    private Boolean published;
}

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ShopFeatures {
    
    @JsonProperty("avalaraAvatax")
    private Boolean avalaraAvatax;
    
    @JsonProperty("branding")
    private ShopBranding branding;
    
    @JsonProperty("bundles")
    private Boolean bundles;
    
    @JsonProperty("captcha")
    private Boolean captcha;
    
    @JsonProperty("captchaExternalDomains")
    private Boolean captchaExternalDomains;
    
    @JsonProperty("deliveryProfiles")
    private Boolean deliveryProfiles;
    
    @JsonProperty("dynamicRemarketing")
    private Boolean dynamicRemarketing;
    
    @JsonProperty("eligibleForSubscriptionMigration")
    private Boolean eligibleForSubscriptionMigration;
    
    @JsonProperty("eligibleForSubscriptions")
    private Boolean eligibleForSubscriptions;
    
    @JsonProperty("giftCards")
    private Boolean giftCards;
    
    @JsonProperty("harmonizedSystemCode")
    private Boolean harmonizedSystemCode;
    
    @JsonProperty("internationalDomains")
    private Boolean internationalDomains;
    
    @JsonProperty("internationalPriceOverrides")
    private Boolean internationalPriceOverrides;
    
    @JsonProperty("internationalPriceRules")
    private Boolean internationalPriceRules;
    
    @JsonProperty("legacySubscriptionGatewayEnabled")
    private Boolean legacySubscriptionGatewayEnabled;
    
    @JsonProperty("liveView")
    private Boolean liveView;
    
    @JsonProperty("multiLocation")
    private Boolean multiLocation;
    
    @JsonProperty("onboardingVisual")
    private Boolean onboardingVisual;
    
    @JsonProperty("productPublishing")
    private Boolean productPublishing;
    
    @JsonProperty("reports")
    private Boolean reports;
    
    @JsonProperty("sellsSubscriptions")
    private Boolean sellsSubscriptions;
    
    @JsonProperty("showMetrics")
    private Boolean showMetrics;
    
    @JsonProperty("storefront")
    private Boolean storefront;
    
    @JsonProperty("usingShopifyBalance")
    private Boolean usingShopifyBalance;
}

public enum ShopBranding {
    SHOPIFY_GOLD,
    SHOPIFY_PLUS,
    ROGERS,
    SHOPIFY
}

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PaymentSettings {
    
    @JsonProperty("supportedDigitalWallets")
    private List<DigitalWallet> supportedDigitalWallets;
}

public enum DigitalWallet {
    APPLE_PAY,
    ANDROID_PAY,
    GOOGLE_PAY,
    SHOPIFY_PAY
}

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ShopPlan {
    
    @JsonProperty("displayName")
    private String displayName;
    
    @JsonProperty("partnerDevelopment")
    private Boolean partnerDevelopment;
    
    @JsonProperty("shopifyPlus")
    private Boolean shopifyPlus;
}

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ShopResourceLimits {
    
    @JsonProperty("locationLimit")
    private Integer locationLimit;
    
    @JsonProperty("maxProductOptions")
    private Integer maxProductOptions;
    
    @JsonProperty("maxProductVariants")
    private Integer maxProductVariants;
    
    @JsonProperty("redirectLimitReached")
    private Boolean redirectLimitReached;
    
    @JsonProperty("skuResourceLimits")
    private ResourceLimit skuResourceLimits;
}

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ResourceLimit {
    
    @JsonProperty("available")
    private Boolean available;
    
    @JsonProperty("quantityAvailable")
    private Integer quantityAvailable;
    
    @JsonProperty("quantityLimit")
    private Integer quantityLimit;
    
    @JsonProperty("quantityUsed")
    private Integer quantityUsed;
}

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StorefrontAccessToken {
    
    @JsonProperty("id")
    private ID id;
    
    @JsonProperty("accessScopes")
    private List<AccessScope> accessScopes;
    
    @JsonProperty("accessToken")
    private String accessToken;
    
    @JsonProperty("createdAt")
    private DateTime createdAt;
    
    @JsonProperty("title")
    private String title;
    
    @JsonProperty("updatedAt")
    private DateTime updatedAt;
}

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AccessScope {
    
    @JsonProperty("handle")
    private String handle;
    
    @JsonProperty("description")
    private String description;
}

public enum UnitSystem {
    METRIC_SYSTEM,
    IMPERIAL_SYSTEM
}

public enum WeightUnit {
    KILOGRAMS,
    GRAMS,
    POUNDS,
    OUNCES
}

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PublishedTranslation {
    
    @JsonProperty("key")
    private String key;
    
    @JsonProperty("locale")
    private String locale;
    
    @JsonProperty("marketId")
    private ID marketId;
    
    @JsonProperty("value")
    private String value;
}

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Image {
    
    @JsonProperty("id")
    private ID id;
    
    @JsonProperty("altText")
    private String altText;
    
    @JsonProperty("height")
    private Integer height;
    
    @JsonProperty("originalSrc")
    private String originalSrc;
    
    @JsonProperty("transformedSrc")
    private String transformedSrc;
    
    @JsonProperty("width")
    private Integer width;
}

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ShopPolicy {
    
    @JsonProperty("id")
    private ID id;
    
    @JsonProperty("body")
    private String body;
    
    @JsonProperty("createdAt")
    private DateTime createdAt;
    
    @JsonProperty("handle")
    private String handle;
    
    @JsonProperty("title")
    private String title;
    
    @JsonProperty("type")
    private ShopPolicyType type;
    
    @JsonProperty("updatedAt")
    private DateTime updatedAt;
    
    @JsonProperty("url")
    private String url;
}

public enum ShopPolicyType {
    REFUND_POLICY,
    SHIPPING_POLICY,
    PRIVACY_POLICY,
    TERMS_OF_SERVICE,
    TERMS_OF_SALE,
    LEGAL_NOTICE,
    SUBSCRIPTION_POLICY
}

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ShopLocalization {
    
    @JsonProperty("locale")
    private String locale;
    
    @JsonProperty("market")
    private Market market;
    
    @JsonProperty("name")
    private String name;
    
    @JsonProperty("published")
    private Boolean published;
}

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Market {
    
    @JsonProperty("id")
    private ID id;
    
    @JsonProperty("currencySettings")
    private List<MarketCurrencySetting> currencySettings;
    
    @JsonProperty("enabled")
    private Boolean enabled;
    
    @JsonProperty("name")
    private String name;
    
    @JsonProperty("primary")
    private Boolean primary;
    
    @JsonProperty("regions")
    private MarketRegionConnection regions;
    
    @JsonProperty("webPresence")
    private MarketWebPresence webPresence;
}

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MarketCurrencySetting {
    
    @JsonProperty("baseCurrency")
    private CurrencyCode baseCurrency;
    
    @JsonProperty("localCurrency")
    private Boolean localCurrency;
}

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MarketWebPresence {
    
    @JsonProperty("id")
    private ID id;
    
    @JsonProperty("alternateLocales")
    private List<String> alternateLocales;
    
    @JsonProperty("defaultLocale")
    private String defaultLocale;
    
    @JsonProperty("domain")
    private Domain domain;
    
    @JsonProperty("rootUrls")
    private List<MarketWebPresenceRootUrl> rootUrls;
    
    @JsonProperty("subfolderSuffix")
    private String subfolderSuffix;
}

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MarketWebPresenceRootUrl {
    
    @JsonProperty("locale")
    private String locale;
    
    @JsonProperty("url")
    private String url;
}

// Connection types
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DomainConnection {
    
    @JsonProperty("edges")
    private List<DomainEdge> edges;
    
    @JsonProperty("pageInfo")
    private PageInfo pageInfo;
}

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DomainEdge {
    
    @JsonProperty("node")
    private Domain node;
    
    @JsonProperty("cursor")
    private String cursor;
}

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FulfillmentServiceConnection {
    
    @JsonProperty("edges")
    private List<FulfillmentServiceEdge> edges;
    
    @JsonProperty("pageInfo")
    private PageInfo pageInfo;
}

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FulfillmentServiceEdge {
    
    @JsonProperty("node")
    private FulfillmentService node;
    
    @JsonProperty("cursor")
    private String cursor;
}

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FulfillmentService {
    
    @JsonProperty("id")
    private ID id;
    
    @JsonProperty("callbackUrl")
    private String callbackUrl;
    
    @JsonProperty("fulfillmentOrdersOptIn")
    private Boolean fulfillmentOrdersOptIn;
    
    @JsonProperty("handle")
    private String handle;
    
    @JsonProperty("inventoryManagement")
    private Boolean inventoryManagement;
    
    @JsonProperty("location")
    private Location location;
    
    @JsonProperty("permitsSkuSharing")
    private Boolean permitsSkuSharing;
    
    @JsonProperty("productBased")
    private Boolean productBased;
    
    @JsonProperty("type")
    private FulfillmentServiceType type;
}

public enum FulfillmentServiceType {
    GIFT_CARD,
    MANUAL,
    THIRD_PARTY
}

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Location {
    
    @JsonProperty("id")
    private ID id;
    
    @JsonProperty("name")
    private String name;
}

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PrivateMetafieldConnection {
    
    @JsonProperty("edges")
    private List<PrivateMetafieldEdge> edges;
    
    @JsonProperty("pageInfo")
    private PageInfo pageInfo;
}

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PrivateMetafieldEdge {
    
    @JsonProperty("node")
    private PrivateMetafield node;
    
    @JsonProperty("cursor")
    private String cursor;
}

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PrivateMetafield {
    
    @JsonProperty("id")
    private ID id;
    
    @JsonProperty("createdAt")
    private DateTime createdAt;
    
    @JsonProperty("key")
    private String key;
    
    @JsonProperty("namespace")
    private String namespace;
    
    @JsonProperty("updatedAt")
    private DateTime updatedAt;
    
    @JsonProperty("value")
    private String value;
    
    @JsonProperty("valueType")
    private PrivateMetafieldValueType valueType;
}

public enum PrivateMetafieldValueType {
    STRING,
    INTEGER,
    JSON_STRING
}

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StorefrontAccessTokenConnection {
    
    @JsonProperty("edges")
    private List<StorefrontAccessTokenEdge> edges;
    
    @JsonProperty("pageInfo")
    private PageInfo pageInfo;
}

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StorefrontAccessTokenEdge {
    
    @JsonProperty("node")
    private StorefrontAccessToken node;
    
    @JsonProperty("cursor")
    private String cursor;
}

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ShopPolicyConnection {
    
    @JsonProperty("edges")
    private List<ShopPolicyEdge> edges;
    
    @JsonProperty("pageInfo")
    private PageInfo pageInfo;
}

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ShopPolicyEdge {
    
    @JsonProperty("node")
    private ShopPolicy node;
    
    @JsonProperty("cursor")
    private String cursor;
}

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MarketRegionConnection {
    
    @JsonProperty("edges")
    private List<MarketRegionEdge> edges;
    
    @JsonProperty("pageInfo")
    private PageInfo pageInfo;
}

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MarketRegionEdge {
    
    @JsonProperty("node")
    private MarketRegion node;
    
    @JsonProperty("cursor")
    private String cursor;
}

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MarketRegion {
    
    @JsonProperty("id")
    private ID id;
    
    @JsonProperty("country")
    private Country country;
}

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Country {
    
    @JsonProperty("code")
    private CountryCode code;
    
    @JsonProperty("name")
    private String name;
}

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SearchResult {
    
    @JsonProperty("edges")
    private List<SearchResultEdge> edges;
    
    @JsonProperty("pageInfo")
    private PageInfo pageInfo;
}

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SearchResultEdge {
    
    @JsonProperty("node")
    private SearchResultItem node;
    
    @JsonProperty("cursor")
    private String cursor;
}

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SearchResultItem {
    
    @JsonProperty("__typename")
    private String typename;
    
    @JsonProperty("id")
    private ID id;
}

// Input classes
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ShopPolicyInput {
    
    @JsonProperty("type")
    private ShopPolicyType type;
    
    @JsonProperty("body")
    private String body;
}

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ShopPolicyUpdateInput {
    
    @JsonProperty("shopPolicyId")
    private ID shopPolicyId;
    
    @JsonProperty("body")
    private String body;
}

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StorefrontAccessTokenInput {
    
    @JsonProperty("title")
    private String title;
}

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StorePropertiesInput {
    
    @JsonProperty("name")
    private String name;
    
    @JsonProperty("email")
    private String email;
    
    @JsonProperty("description")
    private String description;
    
    @JsonProperty("ianaTimezone")
    private String ianaTimezone;
    
    @JsonProperty("currencyCode")
    private CurrencyCode currencyCode;
    
    @JsonProperty("customerAccounts")
    private ShopCustomerAccountsSetting customerAccounts;
    
    @JsonProperty("unitSystem")
    private UnitSystem unitSystem;
    
    @JsonProperty("weightUnit")
    private WeightUnit weightUnit;
}