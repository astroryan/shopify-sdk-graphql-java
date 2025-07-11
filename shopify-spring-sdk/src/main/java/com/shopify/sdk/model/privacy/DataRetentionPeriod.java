package com.shopify.sdk.model.privacy;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.shopify.sdk.model.common.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;


public enum DataRetentionPeriod {
    THREE_MONTHS,
    SIX_MONTHS,
    TWELVE_MONTHS,
    EIGHTEEN_MONTHS,
    TWENTY_FOUR_MONTHS,
    THIRTY_SIX_MONTHS,
    CUSTOM
}