package com.shopify.sdk.model.localization;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.shopify.sdk.model.common.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Locale {
    
    @JsonProperty("isoCode")
    private String isoCode;
    
    @JsonProperty("name")
    private String name;
    
    @JsonProperty("primary")
    private Boolean primary;
    
    @JsonProperty("published")
    private Boolean published;
}

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ShopLocale {
    
    @JsonProperty("locale")
    private String locale;
    
    @JsonProperty("name")
    private String name;
    
    @JsonProperty("primary")
    private Boolean primary;
    
    @JsonProperty("published")
    private Boolean published;
}

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Translation {
    
    @JsonProperty("key")
    private String key;
    
    @JsonProperty("locale")
    private String locale;
    
    @JsonProperty("outdated")
    private Boolean outdated;
    
    @JsonProperty("updatedAt")
    private DateTime updatedAt;
    
    @JsonProperty("value")
    private String value;
}

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TranslatableResource {
    
    @JsonProperty("resourceId")
    private ID resourceId;
    
    @JsonProperty("translatableContent")
    private List<TranslatableContent> translatableContent;
    
    @JsonProperty("translations")
    private List<Translation> translations;
}

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TranslatableContent {
    
    @JsonProperty("digest")
    private String digest;
    
    @JsonProperty("key")
    private String key;
    
    @JsonProperty("locale")
    private String locale;
    
    @JsonProperty("type")
    private LocalizationExtensionPurpose type;
    
    @JsonProperty("value")
    private String value;
}

public enum LocalizationExtensionPurpose {
    SHIPPING_FILTER_ERROR,
    SHIPPING_FILTER_WARNING,
    SHIPPING_FILTER_CAPTION,
    SHIPPING_RATE_ERROR,
    SHIPPING_RATE_TITLE,
    SHIPPING_RATE_DESCRIPTION,
    ORDER_DISCOUNT_MESSAGE,
    PRODUCT_DISCOUNT_MESSAGE,
    SHIPPING_DISCOUNT_MESSAGE,
    PAYMENT_GATEWAY_NAME,
    PAYMENT_METHOD_NAME
}

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TranslatableResourceConnection {
    
    @JsonProperty("edges")
    private List<TranslatableResourceEdge> edges;
    
    @JsonProperty("pageInfo")
    private PageInfo pageInfo;
}

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TranslatableResourceEdge {
    
    @JsonProperty("node")
    private TranslatableResource node;
    
    @JsonProperty("cursor")
    private String cursor;
}

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MarketLocalization {
    
    @JsonProperty("id")
    private ID id;
    
    @JsonProperty("key")
    private String key;
    
    @JsonProperty("outdated")
    private Boolean outdated;
    
    @JsonProperty("updatedAt")
    private DateTime updatedAt;
    
    @JsonProperty("value")
    private String value;
}

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LocalizationExtension {
    
    @JsonProperty("countryCode")
    private CountryCode countryCode;
    
    @JsonProperty("key")
    private LocalizationExtensionKey key;
    
    @JsonProperty("purpose")
    private LocalizationExtensionPurpose purpose;
    
    @JsonProperty("title")
    private String title;
    
    @JsonProperty("value")
    private String value;
}

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

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LocalizationExtensionConnection {
    
    @JsonProperty("edges")
    private List<LocalizationExtensionEdge> edges;
    
    @JsonProperty("pageInfo")
    private PageInfo pageInfo;
}

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LocalizationExtensionEdge {
    
    @JsonProperty("node")
    private LocalizationExtension node;
    
    @JsonProperty("cursor")
    private String cursor;
}

// Input classes for mutations
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TranslationInput {
    
    @JsonProperty("key")
    private String key;
    
    @JsonProperty("locale")
    private String locale;
    
    @JsonProperty("value")
    private String value;
    
    @JsonProperty("translatableContentDigest")
    private String translatableContentDigest;
}

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MarketLocalizationRegisterInput {
    
    @JsonProperty("marketId")
    private String marketId;
    
    @JsonProperty("marketLocalizationKeys")
    private List<String> marketLocalizationKeys;
}

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MarketLocalizationInput {
    
    @JsonProperty("key")
    private String key;
    
    @JsonProperty("value")
    private String value;
}

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LocalizationExtensionInput {
    
    @JsonProperty("key")
    private LocalizationExtensionKey key;
    
    @JsonProperty("value")
    private String value;
}

// Language and region models
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Language {
    
    @JsonProperty("endonymName")
    private String endonymName;
    
    @JsonProperty("isoCode")
    private LanguageCode isoCode;
    
    @JsonProperty("name")
    private String name;
}

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Country {
    
    @JsonProperty("currency")
    private Currency currency;
    
    @JsonProperty("isoCode")
    private CountryCode isoCode;
    
    @JsonProperty("name")
    private String name;
    
    @JsonProperty("unitSystem")
    private UnitSystem unitSystem;
}

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Currency {
    
    @JsonProperty("isoCode")
    private CurrencyCode isoCode;
    
    @JsonProperty("name")
    private String name;
    
    @JsonProperty("symbol")
    private String symbol;
}

public enum UnitSystem {
    IMPERIAL_SYSTEM,
    METRIC_SYSTEM
}

// Weight and measurement units
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Weight {
    
    @JsonProperty("unit")
    private WeightUnit unit;
    
    @JsonProperty("value")
    private Double value;
}

public enum WeightUnit {
    GRAMS,
    KILOGRAMS,
    OUNCES,
    POUNDS
}

// Date and time formats
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DateFormat {
    
    @JsonProperty("format")
    private String format;
    
    @JsonProperty("example")
    private String example;
}

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TimeFormat {
    
    @JsonProperty("format")
    private String format;
    
    @JsonProperty("example")
    private String example;
}

// Shop policy translations
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ShopPolicyTranslation {
    
    @JsonProperty("id")
    private ID id;
    
    @JsonProperty("locale")
    private String locale;
    
    @JsonProperty("title")
    private String title;
    
    @JsonProperty("body")
    private String body;
}

// Storefront translations
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StorefrontLocale {
    
    @JsonProperty("locale")
    private String locale;
    
    @JsonProperty("name")
    private String name;
    
    @JsonProperty("endonymName")
    private String endonymName;
    
    @JsonProperty("rootUrl")
    private String rootUrl;
}

// Translatable resource types
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

// Translation error codes
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TranslationUserError {
    
    @JsonProperty("field")
    private List<String> field;
    
    @JsonProperty("message")
    private String message;
    
    @JsonProperty("code")
    private TranslationErrorCode code;
}

public enum TranslationErrorCode {
    BLANK,
    INVALID,
    RESOURCE_NOT_FOUND,
    TOO_MANY_KEYS_FOR_RESOURCE,
    TRANSLATION_ALREADY_EXISTS,
    TRANSLATION_KEY_INVALID,
    TRANSLATION_MUST_BE_DIFFERENT_FROM_DEFAULT,
    TRANSLATION_VALUE_INVALID,
    FAILS_GATEWAYS_VALIDATION,
    INVALID_KEY_FOR_MODEL,
    INVALID_LOCALE_FOR_SHOP,
    INVALID_TRANSLATABLE_CONTENT,
    RESOURCE_TYPE_NOT_TRANSLATABLE
}