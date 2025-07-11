package com.shopify.sdk.model.onlinestore;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.shopify.sdk.model.common.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;

public enum MenuItemType {
    BLOG,
    CATALOG,
    COLLECTION,
    COLLECTIONS,
    FRONTPAGE,
    HTTP,
    LINK,
    PAGE,
    PRODUCT,
    SEARCH,
    SHOP_POLICY
}
