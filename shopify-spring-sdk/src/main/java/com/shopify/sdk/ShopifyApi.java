package com.shopify.sdk;

import com.shopify.sdk.auth.JwtTokenValidator;
import com.shopify.sdk.auth.ShopifyOAuth;
import com.shopify.sdk.client.ShopifyGraphQLClient;
import com.shopify.sdk.client.ShopifyRestClient;
import com.shopify.sdk.config.ShopifyAuthContext;
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
import com.shopify.sdk.ratelimit.RateLimitService;
import com.shopify.sdk.retry.RetryService;
import com.shopify.sdk.monitoring.MonitoringService;
import com.shopify.sdk.session.SessionManager;
import com.shopify.sdk.webhook.WebhookProcessor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

/**
 * Main entry point for the Shopify SDK.
 * This is the Java equivalent of the Node.js shopifyApi() factory function.
 */
@Component
@RequiredArgsConstructor
public class ShopifyApi {
    
    private final ShopifyAuthContext context;
    private final ShopifyGraphQLClient graphQLClient;
    private final ShopifyRestClient restClient;
    private final ProductService productService;
    private final OrderService orderService;
    private final RestProductService restProductService;
    private final RestOrderService restOrderService;
    private final BillingService billingService;
    private final InventoryService inventoryService;
    private final FulfillmentService fulfillmentService;
    private final MetafieldService metafieldService;
    private final LocationService locationService;
    private final DiscountService discountService;
    private final GiftCardService giftCardService;
    private final DraftOrderService draftOrderService;
    private final BulkOperationService bulkOperationService;
    private final FileUploadService fileUploadService;
    private final AppProxyService appProxyService;
    private final ScriptTagService scriptTagService;
    private final RateLimitService rateLimitService;
    private final RetryService retryService;
    private final MonitoringService monitoringService;
    private final ShopifyOAuth shopifyOAuth;
    private final JwtTokenValidator jwtTokenValidator;
    private final SessionManager sessionManager;
    private final WebhookProcessor webhookProcessor;
    
    /**
     * Gets the current configuration context.
     *
     * @return the configuration context
     */
    public ShopifyAuthContext getConfig() {
        return context;
    }
    
    /**
     * Gets the GraphQL client for direct GraphQL operations.
     *
     * @return the GraphQL client
     */
    public ShopifyGraphQLClient getGraphQLClient() {
        return graphQLClient;
    }
    
    /**
     * Gets the REST client for direct REST API operations.
     *
     * @return the REST client
     */
    public ShopifyRestClient getRestClient() {
        return restClient;
    }
    
    /**
     * Gets the product service for product-related operations.
     *
     * @return the product service
     */
    public ProductService getProducts() {
        return productService;
    }
    
    /**
     * Gets the order service for order-related operations.
     *
     * @return the order service
     */
    public OrderService getOrders() {
        return orderService;
    }
    
    /**
     * Gets the REST product service for REST API product operations.
     *
     * @return the REST product service
     */
    public RestProductService getRestProducts() {
        return restProductService;
    }
    
    /**
     * Gets the REST order service for REST API order operations.
     *
     * @return the REST order service
     */
    public RestOrderService getRestOrders() {
        return restOrderService;
    }
    
    /**
     * Gets the billing service for subscription and payment operations.
     *
     * @return the billing service
     */
    public BillingService getBilling() {
        return billingService;
    }
    
    /**
     * Gets the inventory service for inventory management operations.
     *
     * @return the inventory service
     */
    public InventoryService getInventory() {
        return inventoryService;
    }
    
    /**
     * Gets the fulfillment service for order fulfillment operations.
     *
     * @return the fulfillment service
     */
    public FulfillmentService getFulfillment() {
        return fulfillmentService;
    }
    
    /**
     * Gets the metafield service for metadata management operations.
     *
     * @return the metafield service
     */
    public MetafieldService getMetafields() {
        return metafieldService;
    }
    
    /**
     * Gets the location service for store location operations.
     *
     * @return the location service
     */
    public LocationService getLocations() {
        return locationService;
    }
    
    /**
     * Gets the discount service for price rules and discount operations.
     *
     * @return the discount service
     */
    public DiscountService getDiscounts() {
        return discountService;
    }
    
    /**
     * Gets the gift card service for gift card management operations.
     *
     * @return the gift card service
     */
    public GiftCardService getGiftCards() {
        return giftCardService;
    }
    
    /**
     * Gets the draft order service for draft order operations.
     *
     * @return the draft order service
     */
    public DraftOrderService getDraftOrders() {
        return draftOrderService;
    }
    
    /**
     * Gets the bulk operation service for bulk data operations.
     *
     * @return the bulk operation service
     */
    public BulkOperationService getBulkOperations() {
        return bulkOperationService;
    }
    
    /**
     * Gets the file upload service for file upload operations.
     *
     * @return the file upload service
     */
    public FileUploadService getFileUpload() {
        return fileUploadService;
    }
    
    /**
     * Gets the app proxy service for proxy request validation.
     *
     * @return the app proxy service
     */
    public AppProxyService getAppProxy() {
        return appProxyService;
    }
    
    /**
     * Gets the script tag service for script tag management.
     *
     * @return the script tag service
     */
    public ScriptTagService getScriptTags() {
        return scriptTagService;
    }
    
    /**
     * Gets the rate limit service for managing API rate limits.
     *
     * @return the rate limit service
     */
    public RateLimitService getRateLimit() {
        return rateLimitService;
    }
    
    /**
     * Gets the retry service for handling automatic retries.
     *
     * @return the retry service
     */
    public RetryService getRetry() {
        return retryService;
    }
    
    /**
     * Gets the monitoring service for tracking API metrics.
     *
     * @return the monitoring service
     */
    public MonitoringService getMonitoring() {
        return monitoringService;
    }
    
    /**
     * Gets the OAuth helper for authentication operations.
     *
     * @return the OAuth helper
     */
    public ShopifyOAuth getOAuth() {
        return shopifyOAuth;
    }
    
    /**
     * Gets the JWT token validator.
     *
     * @return the JWT token validator
     */
    public JwtTokenValidator getJwtValidator() {
        return jwtTokenValidator;
    }
    
    /**
     * Gets the session manager.
     *
     * @return the session manager
     */
    public SessionManager getSessionManager() {
        return sessionManager;
    }
    
    /**
     * Gets the webhook processor for handling webhook events.
     *
     * @return the webhook processor
     */
    public WebhookProcessor getWebhooks() {
        return webhookProcessor;
    }
    
    /**
     * Creates a convenience method for accessing GraphQL client with shop and token.
     *
     * @param shop the shop domain
     * @param accessToken the access token
     * @return a pre-configured client wrapper
     */
    public ShopifyClientWrapper forShop(String shop, String accessToken) {
        return new ShopifyClientWrapper(shop, accessToken, this);
    }
    
    /**
     * Wrapper class that provides a convenient API for a specific shop.
     */
    public static class ShopifyClientWrapper {
        private final String shop;
        private final String accessToken;
        private final ShopifyApi api;
        
        public ShopifyClientWrapper(String shop, String accessToken, ShopifyApi api) {
            this.shop = shop;
            this.accessToken = accessToken;
            this.api = api;
        }
        
        public ProductService products() {
            return api.getProducts();
        }
        
        public OrderService orders() {
            return api.getOrders();
        }
        
        public ShopifyGraphQLClient graphql() {
            return api.getGraphQLClient();
        }
        
        public ShopifyRestClient rest() {
            return api.getRestClient();
        }
        
        public RestProductService restProducts() {
            return api.getRestProducts();
        }
        
        public RestOrderService restOrders() {
            return api.getRestOrders();
        }
        
        public BillingService billing() {
            return api.getBilling();
        }
        
        public InventoryService inventory() {
            return api.getInventory();
        }
        
        public FulfillmentService fulfillment() {
            return api.getFulfillment();
        }
        
        public MetafieldService metafields() {
            return api.getMetafields();
        }
        
        public LocationService locations() {
            return api.getLocations();
        }
        
        public DiscountService discounts() {
            return api.getDiscounts();
        }
        
        public GiftCardService giftCards() {
            return api.getGiftCards();
        }
        
        public DraftOrderService draftOrders() {
            return api.getDraftOrders();
        }
        
        public BulkOperationService bulkOperations() {
            return api.getBulkOperations();
        }
        
        public FileUploadService fileUpload() {
            return api.getFileUpload();
        }
        
        public AppProxyService appProxy() {
            return api.getAppProxy();
        }
        
        public ScriptTagService scriptTags() {
            return api.getScriptTags();
        }
        
        public RateLimitService rateLimit() {
            return api.getRateLimit();
        }
        
        public RetryService retry() {
            return api.getRetry();
        }
        
        public MonitoringService monitoring() {
            return api.getMonitoring();
        }
        
        public ShopifyOAuth oauth() {
            return api.getOAuth();
        }
        
        public JwtTokenValidator jwtValidator() {
            return api.getJwtValidator();
        }
        
        public SessionManager sessionManager() {
            return api.getSessionManager();
        }
        
        public WebhookProcessor webhooks() {
            return api.getWebhooks();
        }
        
        public String getShop() {
            return shop;
        }
        
        public String getAccessToken() {
            return accessToken;
        }
    }
}