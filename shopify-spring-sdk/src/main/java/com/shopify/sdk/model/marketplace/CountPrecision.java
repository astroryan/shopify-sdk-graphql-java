package com.shopify.sdk.model.marketplace;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.shopify.sdk.model.common.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;

public enum CountPrecision {
    AT_LEAST,
    AT_MOST,
    EXACT
}
