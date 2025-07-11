package com.shopify.sdk.fixtures;

import com.fasterxml.jackson.databind.ObjectMapper;
import okhttp3.mockwebserver.MockResponse;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Map;

/**
 * Test Fixtures for Shopify API responses
 * 
 * 실제 Shopify API 응답을 기반으로 만든 fixture 데이터를 제공합니다.
 * SDK 사용자들도 이 클래스를 사용하여 Shopify Store 없이 테스트할 수 있습니다.
 */
public class ShopifyTestFixtures {
    
    private static final ObjectMapper mapper = new ObjectMapper();
    
    // Product 관련 Fixtures
    public static String getProductResponse() {
        return readFixture("fixtures/product_response.json");
    }
    
    public static String getProductsListResponse() {
        return readFixture("fixtures/products_list_response.json");
    }
    
    public static String getProductCreateResponse() {
        return """
            {
                "data": {
                    "productCreate": {
                        "product": %s,
                        "userErrors": []
                    }
                }
            }
            """.formatted(getProductResponse().replaceFirst("\\{\\s*\"data\":\\s*\\{\\s*\"product\":", "").replaceFirst("\\}\\s*,\\s*\"extensions\".*$", ""));
    }
    
    // Order 관련 Fixtures
    public static String getOrdersResponse() {
        return readFixture("fixtures/orders_response.json");
    }
    
    public static String getSingleOrderResponse() {
        return """
            {
                "data": {
                    "order": {
                        "id": "gid://shopify/Order/5432109876543",
                        "name": "#1001",
                        "email": "john.doe@example.com",
                        "totalPrice": {
                            "amount": "199.99",
                            "currencyCode": "USD"
                        },
                        "financialStatus": "PAID",
                        "fulfillmentStatus": "UNFULFILLED",
                        "createdAt": "2024-01-15T10:30:00Z"
                    }
                }
            }
            """;
    }
    
    // Customer 관련 Fixtures
    public static String getCustomerResponse() {
        return """
            {
                "data": {
                    "customer": {
                        "id": "gid://shopify/Customer/6789012345678",
                        "email": "john.doe@example.com",
                        "firstName": "John",
                        "lastName": "Doe",
                        "displayName": "John Doe",
                        "phone": "+1-555-123-4567",
                        "acceptsMarketing": true,
                        "state": "ENABLED",
                        "createdAt": "2023-06-01T00:00:00Z",
                        "updatedAt": "2024-01-15T00:00:00Z",
                        "verifiedEmail": true,
                        "taxExempt": false,
                        "tags": ["VIP", "Loyal"],
                        "note": "Excellent customer, always pays on time",
                        "addresses": {
                            "edges": [
                                {
                                    "node": {
                                        "id": "gid://shopify/CustomerAddress/7890123456789",
                                        "address1": "123 Main Street",
                                        "address2": "Apt 4B",
                                        "city": "New York",
                                        "province": "New York",
                                        "provinceCode": "NY",
                                        "country": "United States",
                                        "countryCode": "US",
                                        "zip": "10001",
                                        "default": true
                                    }
                                }
                            ]
                        }
                    }
                }
            }
            """;
    }
    
    // Error Response Fixtures
    public static MockResponse rateLimitResponse() {
        return new MockResponse()
            .setResponseCode(429)
            .setHeader("Retry-After", "2.0")
            .setHeader("X-Shopify-Shop-Api-Call-Limit", "40/40")
            .setBody("{\"errors\":\"Throttled\"}");
    }
    
    public static String graphQLErrorResponse(String message, String code) {
        return """
            {
                "errors": [
                    {
                        "message": "%s",
                        "extensions": {
                            "code": "%s"
                        },
                        "locations": [
                            {
                                "line": 2,
                                "column": 3
                            }
                        ]
                    }
                ],
                "extensions": {
                    "cost": {
                        "requestedQueryCost": 0,
                        "actualQueryCost": 0,
                        "throttleStatus": {
                            "maximumAvailable": 2000,
                            "currentlyAvailable": 2000,
                            "restoreRate": 100
                        }
                    }
                }
            }
            """.formatted(message, code);
    }
    
    public static String unauthorizedResponse() {
        return """
            {
                "errors": "Unauthorized"
            }
            """;
    }
    
    // Validation Error Response
    public static String validationErrorResponse(String field, String message) {
        return """
            {
                "data": {
                    "productCreate": {
                        "product": null,
                        "userErrors": [
                            {
                                "field": ["%s"],
                                "message": "%s",
                                "code": "INVALID"
                            }
                        ]
                    }
                }
            }
            """.formatted(field, message);
    }
    
    // Webhook Fixtures
    public static String getWebhookPayload() {
        return """
            {
                "id": 5432109876543,
                "email": "john.doe@example.com",
                "closed_at": null,
                "created_at": "2024-01-15T10:30:00-05:00",
                "updated_at": "2024-01-15T11:00:00-05:00",
                "number": 1001,
                "note": "Please handle with care",
                "token": "abcdef123456",
                "gateway": "manual",
                "test": false,
                "total_price": "199.99",
                "subtotal_price": "179.99",
                "total_weight": 5200,
                "total_tax": "20.00",
                "taxes_included": true,
                "currency": "USD",
                "financial_status": "paid",
                "confirmed": true,
                "total_discounts": "10.00",
                "total_line_items_price": "189.99",
                "cart_token": null,
                "buyer_accepts_marketing": true,
                "name": "#1001",
                "referring_site": null,
                "landing_site": null,
                "cancelled_at": null,
                "cancel_reason": null,
                "user_id": null,
                "location_id": null,
                "processed_at": "2024-01-15T10:31:00-05:00",
                "device_id": null,
                "phone": "+1-555-123-4567",
                "customer_locale": "en-US",
                "app_id": 1234567,
                "browser_ip": "192.168.1.1",
                "landing_site_ref": null,
                "order_number": 2001,
                "discount_codes": [],
                "note_attributes": [
                    {
                        "name": "gift_message",
                        "value": "Happy Birthday!"
                    }
                ],
                "payment_gateway_names": ["manual"],
                "processing_method": "manual",
                "source_identifier": null,
                "source_name": "web",
                "source_url": null,
                "tags": "VIP, urgent",
                "line_items": [
                    {
                        "id": 11111111111,
                        "variant_id": 41234567890123,
                        "title": "The Collection Snowboard: Liquid",
                        "quantity": 1,
                        "price": "699.99",
                        "sku": "SNOW-LIQUID-156",
                        "variant_title": "156cm",
                        "vendor": "Hydrogen Vendor",
                        "fulfillment_service": "manual",
                        "product_id": 7234567890123,
                        "requires_shipping": true,
                        "taxable": true,
                        "gift_card": false,
                        "name": "The Collection Snowboard: Liquid - 156cm",
                        "variant_inventory_management": "shopify",
                        "properties": [],
                        "product_exists": true,
                        "fulfillable_quantity": 1,
                        "grams": 2358,
                        "total_discount": "0.00",
                        "fulfillment_status": null,
                        "tax_lines": [
                            {
                                "title": "State Tax",
                                "price": "56.00",
                                "rate": 0.08
                            }
                        ]
                    }
                ],
                "shipping_lines": [
                    {
                        "id": 987654321,
                        "title": "Standard Shipping",
                        "price": "10.00",
                        "code": "STANDARD",
                        "source": "shopify",
                        "phone": null,
                        "requested_fulfillment_service_id": null,
                        "delivery_category": null,
                        "carrier_identifier": null,
                        "tax_lines": []
                    }
                ],
                "billing_address": {
                    "first_name": "John",
                    "address1": "123 Main Street",
                    "phone": "+1-555-123-4567",
                    "city": "New York",
                    "zip": "10001",
                    "province": "New York",
                    "country": "United States",
                    "last_name": "Doe",
                    "address2": "Apt 4B",
                    "company": null,
                    "latitude": 40.7128,
                    "longitude": -74.0060,
                    "name": "John Doe",
                    "country_code": "US",
                    "province_code": "NY"
                },
                "shipping_address": {
                    "first_name": "John",
                    "address1": "123 Main Street",
                    "phone": "+1-555-123-4567",
                    "city": "New York",
                    "zip": "10001",
                    "province": "New York",
                    "country": "United States",
                    "last_name": "Doe",
                    "address2": "Apt 4B",
                    "company": null,
                    "latitude": 40.7128,
                    "longitude": -74.0060,
                    "name": "John Doe",
                    "country_code": "US",
                    "province_code": "NY"
                },
                "fulfillments": [],
                "refunds": [],
                "customer": {
                    "id": 6789012345678,
                    "email": "john.doe@example.com",
                    "accepts_marketing": true,
                    "created_at": "2023-06-01T00:00:00-05:00",
                    "updated_at": "2024-01-15T00:00:00-05:00",
                    "first_name": "John",
                    "last_name": "Doe",
                    "orders_count": 5,
                    "state": "enabled",
                    "total_spent": "999.95",
                    "last_order_id": 5432109876543,
                    "note": null,
                    "verified_email": true,
                    "multipass_identifier": null,
                    "tax_exempt": false,
                    "phone": "+1-555-123-4567",
                    "tags": "VIP, Loyal",
                    "last_order_name": "#1001",
                    "default_address": {
                        "id": 7890123456789,
                        "customer_id": 6789012345678,
                        "first_name": "John",
                        "last_name": "Doe",
                        "company": null,
                        "address1": "123 Main Street",
                        "address2": "Apt 4B",
                        "city": "New York",
                        "province": "New York",
                        "country": "United States",
                        "zip": "10001",
                        "phone": "+1-555-123-4567",
                        "name": "John Doe",
                        "province_code": "NY",
                        "country_code": "US",
                        "country_name": "United States",
                        "default": true
                    }
                }
            }
            """;
    }
    
    // Helper method to read fixture files
    private static String readFixture(String path) {
        try (InputStream is = ShopifyTestFixtures.class.getClassLoader().getResourceAsStream(path)) {
            if (is == null) {
                throw new RuntimeException("Fixture not found: " + path);
            }
            return new String(is.readAllBytes(), StandardCharsets.UTF_8);
        } catch (IOException e) {
            throw new RuntimeException("Failed to read fixture: " + path, e);
        }
    }
    
    // Mock Response Builder
    public static MockResponse createSuccessResponse(String body) {
        return new MockResponse()
            .setResponseCode(200)
            .setHeader("Content-Type", "application/json")
            .setHeader("X-Shopify-Shop-Api-Call-Limit", "40/40")
            .setBody(body);
    }
    
    public static MockResponse createErrorResponse(int code, String body) {
        return new MockResponse()
            .setResponseCode(code)
            .setHeader("Content-Type", "application/json")
            .setBody(body);
    }
}