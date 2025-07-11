package com.shopify.sdk.model.retail;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.shopify.sdk.model.common.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;

public enum CardReaderType {
    VERIFONE_P400,
    CHIPPER_2X,
    WISEPAD_3,
    STRIPE_M2,
    TAP_TO_PAY_IOS
}
