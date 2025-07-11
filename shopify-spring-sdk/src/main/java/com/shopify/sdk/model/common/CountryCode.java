package com.shopify.sdk.model.common;

import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum CountryCode {
    US("US", "United States"),
    CA("CA", "Canada"),
    GB("GB", "United Kingdom"),
    AU("AU", "Australia"),
    DE("DE", "Germany"),
    FR("FR", "France"),
    IT("IT", "Italy"),
    ES("ES", "Spain"),
    JP("JP", "Japan"),
    CN("CN", "China"),
    KR("KR", "South Korea"),
    IN("IN", "India"),
    BR("BR", "Brazil"),
    MX("MX", "Mexico"),
    RU("RU", "Russia"),
    NL("NL", "Netherlands"),
    SE("SE", "Sweden"),
    NO("NO", "Norway"),
    DK("DK", "Denmark"),
    FI("FI", "Finland"),
    PL("PL", "Poland"),
    CH("CH", "Switzerland"),
    AT("AT", "Austria"),
    BE("BE", "Belgium"),
    PT("PT", "Portugal"),
    GR("GR", "Greece"),
    CZ("CZ", "Czech Republic"),
    HU("HU", "Hungary"),
    RO("RO", "Romania"),
    IE("IE", "Ireland"),
    SG("SG", "Singapore"),
    HK("HK", "Hong Kong"),
    TW("TW", "Taiwan"),
    NZ("NZ", "New Zealand"),
    ZA("ZA", "South Africa"),
    IL("IL", "Israel"),
    AE("AE", "United Arab Emirates"),
    SA("SA", "Saudi Arabia"),
    AR("AR", "Argentina"),
    CL("CL", "Chile"),
    CO("CO", "Colombia"),
    PE("PE", "Peru"),
    TH("TH", "Thailand"),
    MY("MY", "Malaysia"),
    PH("PH", "Philippines"),
    ID("ID", "Indonesia"),
    VN("VN", "Vietnam"),
    TR("TR", "Turkey"),
    EG("EG", "Egypt"),
    NG("NG", "Nigeria"),
    KE("KE", "Kenya");
    
    @JsonValue
    private final String code;
    private final String name;
}