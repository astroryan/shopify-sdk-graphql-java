package com.shopify.sdk.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.shopify.sdk.ShopifyApi;
import com.shopify.sdk.client.HttpClientConfig;
import com.shopify.sdk.client.HttpClientService;
import com.shopify.sdk.client.ShopifyGraphQLClient;
import com.shopify.sdk.client.ShopifyRestClient;
import com.shopify.sdk.client.graphql.GraphQLClient;
import com.shopify.sdk.client.rest.RestClient;
import com.shopify.sdk.client.rest.RestClientImpl;
import com.shopify.sdk.auth.ShopifyOAuth;
import com.shopify.sdk.auth.JwtTokenValidator;
import com.shopify.sdk.session.SessionManager;
import com.shopify.sdk.session.SessionStore;
import com.shopify.sdk.session.InMemorySessionStore;
import com.shopify.sdk.service.order.OrderService;
import com.shopify.sdk.service.product.ProductService;
import com.shopify.sdk.service.rest.RestOrderService;
import com.shopify.sdk.service.rest.RestProductService;
import com.shopify.sdk.service.billing.BillingService;
import com.shopify.sdk.service.inventory.InventoryService;
import com.shopify.sdk.service.fulfillment.FulfillmentService;
import com.shopify.sdk.service.metafield.MetafieldService;
import com.shopify.sdk.service.location.LocationService;
import com.shopify.sdk.service.discount.DiscountService;
import com.shopify.sdk.service.giftcard.GiftCardService;
import com.shopify.sdk.service.draftorder.DraftOrderService;
import com.shopify.sdk.service.bulk.BulkOperationService;
import com.shopify.sdk.service.file.FileUploadService;
import com.shopify.sdk.service.proxy.AppProxyService;
import com.shopify.sdk.service.scripttag.ScriptTagService;
import com.shopify.sdk.ratelimit.RateLimitConfig;
import com.shopify.sdk.ratelimit.RateLimitService;
import com.shopify.sdk.retry.RetryConfig;
import com.shopify.sdk.retry.RetryService;
import com.shopify.sdk.monitoring.MonitoringService;
import com.shopify.sdk.webhook.WebhookProcessor;
import com.shopify.sdk.webhook.WebhookHandler;
import com.shopify.sdk.webhook.DefaultWebhookHandler;
import com.shopify.sdk.webhook.BillingWebhookHandler;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;

import java.util.List;

/**
 * Auto-configuration for Shopify SDK.
 */
@Configuration
@EnableConfigurationProperties(ShopifyProperties.class)
@EnableScheduling
public class ShopifyAutoConfiguration {
    
    @Bean
    @ConditionalOnMissingBean
    public ObjectMapper shopifyObjectMapper() {
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        return mapper;
    }
    
    @Bean
    @ConditionalOnMissingBean
    public ShopifyConfigFactory shopifyConfigFactory() {
        return new ShopifyConfigFactory();
    }
    
    @Bean
    @ConditionalOnMissingBean
    public ShopifyAuthContext shopifyAuthContext(ShopifyProperties properties, ShopifyConfigFactory configFactory) {
        return configFactory.createContext(properties);
    }
    
    @Bean
    @ConditionalOnMissingBean
    public HttpClientConfig httpClientConfig() {
        return new HttpClientConfig();
    }
    
    @Bean
    @ConditionalOnMissingBean
    public HttpClientService httpClientService(HttpClientConfig httpClientConfig) {
        return new HttpClientService(httpClientConfig);
    }
    
    @Bean
    @ConditionalOnMissingBean
    public GraphQLClient graphQLClient(HttpClientConfig httpClientConfig, ObjectMapper objectMapper) {
        return new GraphQLClient(httpClientConfig, objectMapper);
    }
    
    @Bean
    @ConditionalOnMissingBean
    public ShopifyGraphQLClient shopifyGraphQLClient(GraphQLClient graphQLClient, ShopifyAuthContext context) {
        return new ShopifyGraphQLClient(graphQLClient, context);
    }
    
    @Bean
    @ConditionalOnMissingBean
    public RestClient restClient(HttpClientConfig httpClientConfig, ObjectMapper objectMapper) {
        return new RestClientImpl(httpClientConfig, objectMapper);
    }
    
    @Bean
    @ConditionalOnMissingBean
    public ShopifyRestClient shopifyRestClient(RestClient restClient, ShopifyAuthContext context) {
        return new ShopifyRestClient(restClient, context);
    }
    
    @Bean
    @ConditionalOnMissingBean
    public ProductService productService(ShopifyGraphQLClient shopifyGraphQLClient, ObjectMapper objectMapper) {
        return new ProductService(shopifyGraphQLClient, objectMapper);
    }
    
    @Bean
    @ConditionalOnMissingBean
    public OrderService orderService(ShopifyGraphQLClient shopifyGraphQLClient, ObjectMapper objectMapper) {
        return new OrderService(shopifyGraphQLClient, objectMapper);
    }
    
    @Bean
    @ConditionalOnMissingBean
    public RestProductService restProductService(ShopifyRestClient shopifyRestClient, ObjectMapper objectMapper) {
        return new RestProductService(shopifyRestClient, objectMapper);
    }
    
    @Bean
    @ConditionalOnMissingBean
    public RestOrderService restOrderService(ShopifyRestClient shopifyRestClient, ObjectMapper objectMapper) {
        return new RestOrderService(shopifyRestClient, objectMapper);
    }
    
    @Bean
    @ConditionalOnMissingBean
    public JwtTokenValidator jwtTokenValidator(ShopifyAuthContext context) {
        return new JwtTokenValidator(context);
    }
    
    @Bean
    @ConditionalOnMissingBean
    public ShopifyOAuth shopifyOAuth(ShopifyAuthContext context, HttpClientService httpClientService, ObjectMapper objectMapper) {
        return new ShopifyOAuth(context, httpClientService, objectMapper);
    }
    
    @Bean
    @ConditionalOnMissingBean
    public SessionStore sessionStore() {
        return new InMemorySessionStore();
    }
    
    @Bean
    @ConditionalOnMissingBean
    public SessionManager sessionManager(SessionStore sessionStore, JwtTokenValidator jwtTokenValidator) {
        return new SessionManager(sessionStore, jwtTokenValidator);
    }
    
    @Bean
    @ConditionalOnMissingBean
    public WebhookProcessor webhookProcessor(ShopifyAuthContext context, 
                                           ShopifyOAuth shopifyOAuth, 
                                           ObjectMapper objectMapper, 
                                           List<WebhookHandler> webhookHandlers) {
        return new WebhookProcessor(context, shopifyOAuth, objectMapper, webhookHandlers);
    }
    
    @Bean
    @ConditionalOnMissingBean
    public BillingService billingService(ShopifyGraphQLClient shopifyGraphQLClient, 
                                       ShopifyRestClient shopifyRestClient, 
                                       ObjectMapper objectMapper) {
        return new BillingService(shopifyGraphQLClient, shopifyRestClient, objectMapper);
    }
    
    @Bean
    @ConditionalOnMissingBean
    public InventoryService inventoryService(ShopifyGraphQLClient shopifyGraphQLClient, 
                                           ShopifyRestClient shopifyRestClient, 
                                           ObjectMapper objectMapper) {
        return new InventoryService(shopifyGraphQLClient, shopifyRestClient, objectMapper);
    }
    
    @Bean
    @ConditionalOnMissingBean
    public FulfillmentService fulfillmentService(ShopifyRestClient shopifyRestClient, 
                                                ObjectMapper objectMapper) {
        return new FulfillmentService(shopifyRestClient, objectMapper);
    }
    
    @Bean
    @ConditionalOnMissingBean
    public MetafieldService metafieldService(ShopifyGraphQLClient shopifyGraphQLClient, 
                                           ShopifyRestClient shopifyRestClient, 
                                           ObjectMapper objectMapper) {
        return new MetafieldService(shopifyGraphQLClient, shopifyRestClient, objectMapper);
    }
    
    @Bean
    @ConditionalOnMissingBean
    public LocationService locationService(ShopifyRestClient shopifyRestClient, 
                                         ObjectMapper objectMapper) {
        return new LocationService(shopifyRestClient, objectMapper);
    }
    
    @Bean
    @ConditionalOnMissingBean
    public DiscountService discountService(ShopifyRestClient shopifyRestClient, 
                                         ObjectMapper objectMapper) {
        return new DiscountService(shopifyRestClient, objectMapper);
    }
    
    @Bean
    @ConditionalOnMissingBean
    public GiftCardService giftCardService(ShopifyRestClient shopifyRestClient, 
                                         ObjectMapper objectMapper) {
        return new GiftCardService(shopifyRestClient, objectMapper);
    }
    
    @Bean
    @ConditionalOnMissingBean
    public DraftOrderService draftOrderService(ShopifyRestClient shopifyRestClient, 
                                              ObjectMapper objectMapper) {
        return new DraftOrderService(shopifyRestClient, objectMapper);
    }
    
    @Bean
    @ConditionalOnMissingBean
    public BulkOperationService bulkOperationService(ShopifyGraphQLClient shopifyGraphQLClient, 
                                                   ObjectMapper objectMapper) {
        return new BulkOperationService(shopifyGraphQLClient, objectMapper);
    }
    
    @Bean
    @ConditionalOnMissingBean
    public WebClient fileUploadWebClient() {
        return WebClient.builder().build();
    }
    
    @Bean
    @ConditionalOnMissingBean
    public FileUploadService fileUploadService(ShopifyGraphQLClient shopifyGraphQLClient, 
                                             ObjectMapper objectMapper,
                                             WebClient fileUploadWebClient) {
        return new FileUploadService(shopifyGraphQLClient, objectMapper, fileUploadWebClient);
    }
    
    @Bean
    @ConditionalOnMissingBean
    public AppProxyService appProxyService(ShopifyAuthContext context) {
        return new AppProxyService(context);
    }
    
    @Bean
    @ConditionalOnMissingBean
    public ScriptTagService scriptTagService(ShopifyRestClient shopifyRestClient, 
                                           ObjectMapper objectMapper) {
        return new ScriptTagService(shopifyRestClient, objectMapper);
    }
    
    @Bean
    @ConditionalOnMissingBean
    public RateLimitConfig rateLimitConfig() {
        return RateLimitConfig.builder().build();
    }
    
    @Bean
    @ConditionalOnMissingBean
    public RateLimitService rateLimitService(RateLimitConfig rateLimitConfig) {
        return new RateLimitService(rateLimitConfig);
    }
    
    @Bean
    @ConditionalOnMissingBean
    public RetryConfig retryConfig() {
        return RetryConfig.builder().build();
    }
    
    @Bean
    @ConditionalOnMissingBean
    public RetryService retryService(RetryConfig retryConfig) {
        return new RetryService(retryConfig);
    }
    
    @Bean
    @ConditionalOnMissingBean
    public MonitoringService monitoringService() {
        return new MonitoringService();
    }
    
    @Bean
    @ConditionalOnMissingBean
    public BillingWebhookHandler billingWebhookHandler(BillingService billingService) {
        return new BillingWebhookHandler(billingService);
    }
    
    @Bean
    @ConditionalOnMissingBean
    public DefaultWebhookHandler defaultWebhookHandler() {
        return new DefaultWebhookHandler();
    }
    
    @Bean
    @ConditionalOnMissingBean
    public ShopifyApi shopifyApi(ShopifyAuthContext context, 
                                 ShopifyGraphQLClient graphQLClient,
                                 ShopifyRestClient restClient,
                                 ProductService productService,
                                 OrderService orderService,
                                 RestProductService restProductService,
                                 RestOrderService restOrderService,
                                 BillingService billingService,
                                 InventoryService inventoryService,
                                 FulfillmentService fulfillmentService,
                                 MetafieldService metafieldService,
                                 LocationService locationService,
                                 DiscountService discountService,
                                 GiftCardService giftCardService,
                                 DraftOrderService draftOrderService,
                                 BulkOperationService bulkOperationService,
                                 FileUploadService fileUploadService,
                                 AppProxyService appProxyService,
                                 ScriptTagService scriptTagService,
                                 RateLimitService rateLimitService,
                                 RetryService retryService,
                                 MonitoringService monitoringService,
                                 ShopifyOAuth shopifyOAuth,
                                 JwtTokenValidator jwtTokenValidator,
                                 SessionManager sessionManager,
                                 WebhookProcessor webhookProcessor) {
        return new ShopifyApi(context, graphQLClient, restClient, productService, orderService, 
                             restProductService, restOrderService, billingService, inventoryService,
                             fulfillmentService, metafieldService, locationService, discountService,
                             giftCardService, draftOrderService, bulkOperationService, fileUploadService,
                             appProxyService, scriptTagService, rateLimitService, retryService, 
                             monitoringService, shopifyOAuth, jwtTokenValidator, sessionManager,
                             webhookProcessor);
    }
}