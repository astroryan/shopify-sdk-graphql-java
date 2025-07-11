package com.shopify.sdk.model.app;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.shopify.sdk.model.common.DateTime;
import com.shopify.sdk.model.common.ID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class App {
    
    @JsonProperty("id")
    private ID id;
    
    @JsonProperty("title")
    private String title;
    
    @JsonProperty("description")
    private String description;
    
    @JsonProperty("developerName")
    private String developerName;
    
    @JsonProperty("developerType")
    private DeveloperType developerType;
    
    @JsonProperty("privacyPolicyUrl")
    private String privacyPolicyUrl;
    
    @JsonProperty("apiKey")
    private String apiKey;
    
    @JsonProperty("appStoreAppUrl")
    private String appStoreAppUrl;
    
    @JsonProperty("appStoreDeveloperUrl")
    private String appStoreDeveloperUrl;
    
    @JsonProperty("banner")
    private AppBanner banner;
    
    @JsonProperty("embedded")
    private Boolean embedded;
    
    @JsonProperty("features")
    private List<String> features;
    
    @JsonProperty("handle")
    private String handle;
    
    @JsonProperty("icon")
    private AppIcon icon;
    
    @JsonProperty("installUrl")
    private String installUrl;
    
    @JsonProperty("pricingDetails")
    private String pricingDetails;
    
    @JsonProperty("pricingDetailsSummary")
    private String pricingDetailsSummary;
    
    @JsonProperty("published")
    private Boolean published;
    
    @JsonProperty("redirectUrlWhitelist")
    private List<String> redirectUrlWhitelist;
    
    @JsonProperty("requestedAccessScopes")
    private List<String> requestedAccessScopes;
    
    @JsonProperty("screenshots")
    private List<AppScreenshot> screenshots;
    
    @JsonProperty("shopifyDeveloped")
    private Boolean shopifyDeveloped;
    
    @JsonProperty("supportEmail")
    private String supportEmail;
    
    @JsonProperty("supportUrl")
    private String supportUrl;
    
    @JsonProperty("uninstallMessage")
    private String uninstallMessage;
    
    @JsonProperty("webhookApiVersion")
    private String webhookApiVersion;
}