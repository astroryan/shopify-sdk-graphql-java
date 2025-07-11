package com.shopify.sdk.model.app;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.shopify.sdk.model.access.AccessScope;
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
public class AppInstallation {
    
    @JsonProperty("id")
    private ID id;
    
    @JsonProperty("app")
    private App app;
    
    @JsonProperty("channel")
    private Channel channel;
    
    @JsonProperty("credits")
    private AppCredit credits;
    
    @JsonProperty("accessScopes")
    private List<AccessScope> accessScopes;
    
    @JsonProperty("activeSubscriptions")
    private List<AppSubscription> activeSubscriptions;
    
    @JsonProperty("allSubscriptions")
    private AppSubscriptionConnection allSubscriptions;
    
    @JsonProperty("createdAt")
    private DateTime createdAt;
    
    @JsonProperty("launchUrl")
    private String launchUrl;
    
    @JsonProperty("oneTimePurchases")
    private AppPurchaseOneTimeConnection oneTimePurchases;
    
    @JsonProperty("publication")
    private Publication publication;
    
    @JsonProperty("revenueAttributionRecords")
    private AppRevenueAttributionRecordConnection revenueAttributionRecords;
    
    @JsonProperty("uninstallUrl")
    private String uninstallUrl;
}