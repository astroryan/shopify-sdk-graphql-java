package com.shopify.sdk.model.marketplace;

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
public class Channel {
    
    @JsonProperty("id")
    private ID id;
    
    @JsonProperty("app")
    private ChannelApp app;
    
    @JsonProperty("collectionPublicationsV3")
    private ResourcePublicationV2Connection collectionPublicationsV3;
    
    @JsonProperty("handle")
    private String handle;
    
    @JsonProperty("hasCollection")
    private Boolean hasCollection;
    
    @JsonProperty("name")
    private String name;
    
    @JsonProperty("navigationItems")
    private List<NavigationItem> navigationItems;
    
    @JsonProperty("overviewPath")
    private String overviewPath;
    
    @JsonProperty("productPublications")
    private ProductPublicationConnection productPublications;
    
    @JsonProperty("productPublicationsV3")
    private ResourcePublicationV2Connection productPublicationsV3;
    
    @JsonProperty("productsCount")
    private ProductsCount productsCount;
    
    @JsonProperty("supportsFuturePublishing")
    private Boolean supportsFuturePublishing;
}

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ChannelApp {
    
    @JsonProperty("id")
    private ID id;
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
public class ProductsCount {
    
    @JsonProperty("count")
    private Integer count;
    
    @JsonProperty("precision")
    private CountPrecision precision;
}

public enum CountPrecision {
    AT_LEAST,
    AT_MOST,
    EXACT
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
public class Publication {
    
    @JsonProperty("id")
    private ID id;
    
    @JsonProperty("app")
    private PublicationApp app;
    
    @JsonProperty("autoPublish")
    private Boolean autoPublish;
    
    @JsonProperty("catalog")
    private Catalog catalog;
    
    @JsonProperty("collectionPublicationsV3")
    private ResourcePublicationV2Connection collectionPublicationsV3;
    
    @JsonProperty("collections")
    private CollectionConnection collections;
    
    @JsonProperty("hasCollection")
    private Boolean hasCollection;
    
    @JsonProperty("name")
    private String name;
    
    @JsonProperty("operation")
    private PublicationOperation operation;
    
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
public class PublicationApp {
    
    @JsonProperty("id")
    private ID id;
}

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Catalog {
    
    @JsonProperty("id")
    private ID id;
    
    @JsonProperty("apps")
    private AppConnection apps;
    
    @JsonProperty("operations")
    private PublicationOperation operations;
    
    @JsonProperty("priceList")
    private PriceList priceList;
    
    @JsonProperty("publication")
    private Publication publication;
    
    @JsonProperty("status")
    private CatalogStatus status;
    
    @JsonProperty("title")
    private String title;
}

public enum CatalogStatus {
    ACTIVE,
    ARCHIVED
}

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PriceList {
    
    @JsonProperty("id")
    private ID id;
    
    @JsonProperty("catalog")
    private Catalog catalog;
    
    @JsonProperty("contextRule")
    private PriceListContextRule contextRule;
    
    @JsonProperty("currency")
    private CurrencyCode currency;
    
    @JsonProperty("fixedPricesCount")
    private Integer fixedPricesCount;
    
    @JsonProperty("name")
    private String name;
    
    @JsonProperty("parent")
    private PriceListParent parent;
    
    @JsonProperty("prices")
    private PriceListPriceConnection prices;
}

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PriceListContextRule {
    
    @JsonProperty("countries")
    private List<CountryCode> countries;
    
    @JsonProperty("market")
    private Market market;
}

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Market {
    
    @JsonProperty("id")
    private ID id;
    
    @JsonProperty("catalogs")
    private CatalogConnection catalogs;
    
    @JsonProperty("catalogsCount")
    private CatalogsCount catalogsCount;
    
    @JsonProperty("currencySettings")
    private MarketCurrencySettingsConnection currencySettings;
    
    @JsonProperty("enabled")
    private Boolean enabled;
    
    @JsonProperty("handle")
    private String handle;
    
    @JsonProperty("metafield")
    private Metafield metafield;
    
    @JsonProperty("metafields")
    private MetafieldConnection metafields;
    
    @JsonProperty("name")
    private String name;
    
    @JsonProperty("priceList")
    private PriceList priceList;
    
    @JsonProperty("primary")
    private Boolean primary;
    
    @JsonProperty("regions")
    private MarketRegionConnection regions;
    
    @JsonProperty("webPresence")
    private MarketWebPresence webPresence;
    
    @JsonProperty("webPresences")
    private MarketWebPresenceConnection webPresences;
}

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CatalogsCount {
    
    @JsonProperty("count")
    private Integer count;
    
    @JsonProperty("precision")
    private CountPrecision precision;
}

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MarketCurrencySettings {
    
    @JsonProperty("baseCurrency")
    private CurrencySetting baseCurrency;
    
    @JsonProperty("localCurrencies")
    private Boolean localCurrencies;
}

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CurrencySetting {
    
    @JsonProperty("currencyCode")
    private CurrencyCode currencyCode;
    
    @JsonProperty("currencyName")
    private String currencyName;
    
    @JsonProperty("enabled")
    private Boolean enabled;
    
    @JsonProperty("rateProvider")
    private CurrencySettingRateProvider rateProvider;
}

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CurrencySettingRateProvider {
    
    @JsonProperty("__typename")
    private String typename;
}

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MarketRegion {
    
    @JsonProperty("id")
    private ID id;
    
    @JsonProperty("name")
    private String name;
}

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MarketRegionCountry implements MarketRegion {
    
    @JsonProperty("id")
    private ID id;
    
    @JsonProperty("name")
    private String name;
    
    @JsonProperty("code")
    private CountryCode code;
    
    @JsonProperty("currency")
    private CurrencySetting currency;
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
    
    @JsonProperty("market")
    private Market market;
    
    @JsonProperty("rootUrls")
    private List<MarketWebPresenceRootUrl> rootUrls;
    
    @JsonProperty("subfolderSuffix")
    private String subfolderSuffix;
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
    
    @JsonProperty("marketWebPresence")
    private MarketWebPresence marketWebPresence;
    
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
public class ProductPublicationConnection {
    
    @JsonProperty("edges")
    private List<ProductPublicationEdge> edges;
    
    @JsonProperty("pageInfo")
    private PageInfo pageInfo;
}

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductPublicationEdge {
    
    @JsonProperty("node")
    private ProductPublication node;
    
    @JsonProperty("cursor")
    private String cursor;
}

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductPublication {
    
    @JsonProperty("channel")
    private Channel channel;
    
    @JsonProperty("isPublished")
    private Boolean isPublished;
    
    @JsonProperty("product")
    private Product product;
    
    @JsonProperty("publishDate")
    private DateTime publishDate;
}

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CatalogConnection {
    
    @JsonProperty("edges")
    private List<CatalogEdge> edges;
    
    @JsonProperty("pageInfo")
    private PageInfo pageInfo;
}

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CatalogEdge {
    
    @JsonProperty("node")
    private Catalog node;
    
    @JsonProperty("cursor")
    private String cursor;
}

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MarketConnection {
    
    @JsonProperty("edges")
    private List<MarketEdge> edges;
    
    @JsonProperty("pageInfo")
    private PageInfo pageInfo;
}

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MarketEdge {
    
    @JsonProperty("node")
    private Market node;
    
    @JsonProperty("cursor")
    private String cursor;
}

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MarketCurrencySettingsConnection {
    
    @JsonProperty("edges")
    private List<MarketCurrencySettingsEdge> edges;
    
    @JsonProperty("pageInfo")
    private PageInfo pageInfo;
}

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MarketCurrencySettingsEdge {
    
    @JsonProperty("node")
    private MarketCurrencySettings node;
    
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
public class MarketWebPresenceConnection {
    
    @JsonProperty("edges")
    private List<MarketWebPresenceEdge> edges;
    
    @JsonProperty("pageInfo")
    private PageInfo pageInfo;
}

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MarketWebPresenceEdge {
    
    @JsonProperty("node")
    private MarketWebPresence node;
    
    @JsonProperty("cursor")
    private String cursor;
}

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PriceListPriceConnection {
    
    @JsonProperty("edges")
    private List<PriceListPriceEdge> edges;
    
    @JsonProperty("pageInfo")
    private PageInfo pageInfo;
}

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PriceListPriceEdge {
    
    @JsonProperty("node")
    private PriceListPrice node;
    
    @JsonProperty("cursor")
    private String cursor;
}

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PriceListPrice {
    
    @JsonProperty("compareAtPrice")
    private MoneyV2 compareAtPrice;
    
    @JsonProperty("originType")
    private PriceListPriceOriginType originType;
    
    @JsonProperty("price")
    private MoneyV2 price;
    
    @JsonProperty("variant")
    private ProductVariant variant;
}

public enum PriceListPriceOriginType {
    FIXED,
    RELATIVE
}

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PriceListParent {
    
    @JsonProperty("adjustment")
    private PriceListAdjustment adjustment;
}

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PriceListAdjustment {
    
    @JsonProperty("type")
    private PriceListAdjustmentType type;
    
    @JsonProperty("value")
    private Double value;
}

public enum PriceListAdjustmentType {
    PERCENTAGE_DECREASE,
    PERCENTAGE_INCREASE
}

// Simplified model references
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
public class ProductVariant {
    
    @JsonProperty("id")
    private ID id;
    
    @JsonProperty("title")
    private String title;
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
public class AppConnection {
    
    @JsonProperty("edges")
    private List<AppEdge> edges;
    
    @JsonProperty("pageInfo")
    private PageInfo pageInfo;
}

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AppEdge {
    
    @JsonProperty("node")
    private App node;
    
    @JsonProperty("cursor")
    private String cursor;
}

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class App {
    
    @JsonProperty("id")
    private ID id;
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

public enum PublicationOperation {
    ADD,
    REMOVE
}