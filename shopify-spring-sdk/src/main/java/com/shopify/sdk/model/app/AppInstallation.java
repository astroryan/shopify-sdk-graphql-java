package com.shopify.sdk.model.app;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.shopify.sdk.model.common.Node;
import com.shopify.sdk.model.access.AccessScope;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.OffsetDateTime;
import java.util.List;

/**
 * Represents an installed app on a shop.
 * Contains information about the app installation including permissions and status.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AppInstallation implements Node {
    /**
     * A globally unique identifier.
     */
    @JsonProperty("id")
    private String id;
    
    /**
     * The app that was installed.
     */
    @JsonProperty("app")
    private App app;
    
    /**
     * The date and time when the app was installed.
     */
    @JsonProperty("installedAt")
    private OffsetDateTime installedAt;
    
    /**
     * The date and time when the app installation was last updated.
     */
    @JsonProperty("updatedAt")
    private OffsetDateTime updatedAt;
    
    /**
     * The OAuth access scopes granted to the app.
     */
    @JsonProperty("accessScopes")
    private List<AccessScope> accessScopes;
    
    /**
     * Whether the app is active and accessible in the admin.
     */
    @JsonProperty("activeSubscriptions")
    private Boolean activeSubscriptions;
    
    /**
     * All active subscriptions billed to the shop on behalf of the app.
     */
    @JsonProperty("allSubscriptions")
    private AppSubscriptionConnection allSubscriptions;
    
    /**
     * The URL to launch the app.
     */
    @JsonProperty("launchUrl")
    private String launchUrl;
    
    /**
     * The publication associated with the installed app.
     */
    @JsonProperty("publication")
    private Publication publication;
    
    /**
     * The date and time when the app was uninstalled, if applicable.
     */
    @JsonProperty("uninstalledAt")
    private OffsetDateTime uninstalledAt;
    
    /**
     * The app plan that is currently subscribed.
     */
    @JsonProperty("subscribedPlan")
    private AppPlan subscribedPlan;
}

/**
 * Represents an app subscription plan.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
class AppPlan {
    /**
     * The name of the app plan.
     */
    @JsonProperty("displayName")
    private String displayName;
    
    /**
     * Whether this is a test plan.
     */
    @JsonProperty("pricingDetails")
    private String pricingDetails;
}

/**
 * A connection to app subscriptions.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
class AppSubscriptionConnection {
    /**
     * The total count of subscriptions.
     */
    @JsonProperty("totalCount")
    private Integer totalCount;
}

/**
 * Represents a publication for an app.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
class Publication {
    /**
     * The globally unique identifier.
     */
    @JsonProperty("id")
    private String id;
    
    /**
     * The name of the publication.
     */
    @JsonProperty("name")
    private String name;
}