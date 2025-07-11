package com.shopify.sdk.model.metafield;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.shopify.sdk.model.common.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;


public enum MetafieldOwnerType {
    ARTICLE,
    BLOG,
    COLLECTION,
    COMPANY,
    COMPANY_LOCATION,
    CUSTOMER,
    DELIVERY_CUSTOMIZATION,
    DISCOUNT_NODE,
    DRAFTORDER,
    GENERIC_FILE,
    LOCATION,
    MARKET,
    MEDIA_IMAGE,
    ORDER,
    PAGE,
    PAYMENT_CUSTOMIZATION,
    PRODUCT,
    PRODUCTIMAGE,
    PRODUCTVARIANT,
    SHOP,
    VALIDATION
}