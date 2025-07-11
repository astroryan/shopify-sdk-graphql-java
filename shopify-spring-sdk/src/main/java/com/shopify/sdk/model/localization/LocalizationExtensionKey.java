package com.shopify.sdk.model.localization;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.shopify.sdk.model.common.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;

public enum LocalizationExtensionKey {
    SHIPPING_CREDENTIAL_BR_CNPJ,
    SHIPPING_CREDENTIAL_BR_CPF,
    SHIPPING_CREDENTIAL_CN_ID_NUMBER,
    SHIPPING_CREDENTIAL_KR_CUSTOMS_ID_NUMBER,
    TAX_CREDENTIAL_BR_CNPJ,
    TAX_CREDENTIAL_BR_CPF,
    TAX_CREDENTIAL_IT_SDI,
    TAX_CREDENTIAL_IT_PEC,
    TAX_CREDENTIAL_IT_CF,
    TAX_CREDENTIAL_JP_MY_NUMBER,
    TAX_CREDENTIAL_JP_CORPORATE_NUMBER,
    TAX_EMAIL_IT,
    PHONE_VERIFICATION_NUMBER,
    DEFAULT_CURRENCY
}
