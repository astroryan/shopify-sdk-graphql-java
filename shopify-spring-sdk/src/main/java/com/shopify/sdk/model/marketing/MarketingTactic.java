package com.shopify.sdk.model.marketing;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.shopify.sdk.model.common.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;

public enum MarketingTactic {
    ABANDONED_CART,
    AD,
    AFFILIATE,
    DIRECT,
    DISPLAY,
    LINK,
    MESSAGE,
    NEWSLETTER,
    NOTIFICATION,
    POST,
    RETARGETING,
    SEO,
    STOREFRONT_APP,
    TRANSACTIONAL
}
