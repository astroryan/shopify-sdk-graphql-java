package com.shopify.sdk.model.common;

import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum CurrencyCode {
    USD("USD"),
    EUR("EUR"),
    GBP("GBP"),
    CAD("CAD"),
    AUD("AUD"),
    JPY("JPY"),
    CNY("CNY"),
    KRW("KRW"),
    INR("INR"),
    BRL("BRL"),
    MXN("MXN"),
    SGD("SGD"),
    NZD("NZD"),
    CHF("CHF"),
    SEK("SEK"),
    NOK("NOK"),
    DKK("DKK"),
    PLN("PLN"),
    CZK("CZK"),
    HUF("HUF"),
    ILS("ILS"),
    RUB("RUB"),
    ZAR("ZAR"),
    TRY("TRY"),
    THB("THB"),
    MYR("MYR"),
    PHP("PHP"),
    IDR("IDR"),
    HKD("HKD"),
    TWD("TWD"),
    AED("AED"),
    SAR("SAR"),
    ARS("ARS"),
    CLP("CLP"),
    COP("COP"),
    PEN("PEN");
    
    @JsonValue
    private final String value;
}