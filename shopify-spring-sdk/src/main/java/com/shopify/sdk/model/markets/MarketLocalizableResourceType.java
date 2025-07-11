package com.shopify.sdk.model.markets;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.shopify.sdk.model.common.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;

public enum MarketLocalizableResourceType {
    COLLECTION,
    LINK,
    MENU,
    METAFIELD,
    METAOBJECT,
    ONLINE_STORE_ARTICLE,
    ONLINE_STORE_BLOG,
    ONLINE_STORE_PAGE,
    ONLINE_STORE_THEME,
    PACKING_SLIP_TEMPLATE,
    PAYMENT_GATEWAY,
    PRODUCT,
    PRODUCT_OPTION,
    PRODUCT_OPTION_VALUE,
    PRODUCT_VARIANT,
    SELLING_PLAN,
    SELLING_PLAN_GROUP,
    SHOP,
    SHOP_POLICY
}
