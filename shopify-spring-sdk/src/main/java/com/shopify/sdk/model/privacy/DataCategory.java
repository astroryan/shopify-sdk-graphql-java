package com.shopify.sdk.model.privacy;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.shopify.sdk.model.common.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;


public enum DataCategory {
    CUSTOMER_DATA,
    ORDER_DATA,
    DRAFT_ORDER_DATA,
    PRODUCT_DATA,
    COLLECTION_DATA,
    INVENTORY_DATA,
    LOCATION_DATA,
    MARKETING_DATA,
    ANALYTICS_DATA,
    FINANCIAL_DATA,
    SHIPPING_DATA,
    TAX_DATA
}