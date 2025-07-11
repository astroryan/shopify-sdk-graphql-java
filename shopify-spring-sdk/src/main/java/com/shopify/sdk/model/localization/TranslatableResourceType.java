package com.shopify.sdk.model.localization;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.shopify.sdk.model.common.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;

public enum TranslatableResourceType {
    COLLECTION,
    DELIVERY_METHOD_DEFINITION,
    EMAIL_TEMPLATE,
    LINK,
    METAFIELD,
    METAOBJECT,
    ONLINE_STORE_ARTICLE,
    ONLINE_STORE_BLOG,
    ONLINE_STORE_MENU,
    ONLINE_STORE_PAGE,
    ONLINE_STORE_THEME,
    PAYMENT_GATEWAY,
    PRODUCT,
    PRODUCT_OPTION,
    PRODUCT_VARIANT,
    SELLING_PLAN,
    SELLING_PLAN_GROUP,
    SHOP,
    SHOP_POLICY
}
