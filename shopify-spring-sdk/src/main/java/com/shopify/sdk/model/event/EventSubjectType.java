package com.shopify.sdk.model.event;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.shopify.sdk.model.common.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;

public enum EventSubjectType {
    ARTICLE,
    BLOG,
    COLLECTION,
    COMMENT,
    CUSTOMER,
    DISCOUNT,
    DRAFT_ORDER,
    ORDER,
    PAGE,
    PRODUCT,
    VARIANT
}
