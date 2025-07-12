package com.shopify.sdk.model.app;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.shopify.sdk.model.common.Node;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.OffsetDateTime;
import java.util.List;

/**
 * Represents a Shopify app that merchants can install.
 * Apps extend the functionality of Shopify stores.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class App implements Node {
    /**
     * A globally unique identifier.
     */
    @JsonProperty("id")
    private String id;
    
    /**
     * The API key for the app.
     */
    @JsonProperty("apiKey")
    private String apiKey;
    
    /**
     * The handle (unique identifier) for the app.
     */
    @JsonProperty("handle")
    private String handle;
    
    /**
     * The title of the app.
     */
    @JsonProperty("title")
    private String title;
    
    /**
     * A description of the app.
     */
    @JsonProperty("description")
    private String description;
    
    /**
     * The name of the app developer.
     */
    @JsonProperty("developerName")
    private String developerName;
    
    /**
     * The URL to the app's icon.
     */
    @JsonProperty("icon")
    private AppIcon icon;
    
    /**
     * Whether the app is listed in the Shopify App Store.
     */
    @JsonProperty("isListed")
    private Boolean isListed;
    
    /**
     * The date and time when the app was created.
     */
    @JsonProperty("createdAt")
    private OffsetDateTime createdAt;
    
    /**
     * The date and time when the app was last updated.
     */
    @JsonProperty("updatedAt")
    private OffsetDateTime updatedAt;
    
    /**
     * The app's website URL.
     */
    @JsonProperty("appStoreAppUrl")
    private String appStoreAppUrl;
    
    /**
     * The app's developer website URL.
     */
    @JsonProperty("appStoreDeveloperUrl")
    private String appStoreDeveloperUrl;
    
    /**
     * The pricing details page URL.
     */
    @JsonProperty("pricingDetailsUrl")
    private String pricingDetailsUrl;
    
    /**
     * The privacy policy URL.
     */
    @JsonProperty("privacyPolicyUrl")
    private String privacyPolicyUrl;
    
    /**
     * The features provided by the app.
     */
    @JsonProperty("features")
    private List<String> features;
    
    /**
     * Whether the app is developed by Shopify.
     */
    @JsonProperty("developedByShopify")
    private Boolean developedByShopify;
    
    /**
     * The feedback from the app developer.
     */
    @JsonProperty("feedback")
    private AppFeedback feedback;
    
    /**
     * The public category for the app.
     */
    @JsonProperty("publicCategory")
    private String publicCategory;
}

/**
 * Represents an app's icon with different sizes.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
class AppIcon {
    /**
     * The alternative text for the icon.
     */
    @JsonProperty("altText")
    private String altText;
    
    /**
     * The original URL of the icon.
     */
    @JsonProperty("originalSrc")
    private String originalSrc;
    
    /**
     * The transformed URL of the icon.
     */
    @JsonProperty("transformedSrc")
    private String transformedSrc;
}

/**
 * Represents feedback for an app.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
class AppFeedback {
    /**
     * The app's rating.
     */
    @JsonProperty("averageRating")
    private Double averageRating;
    
    /**
     * The total number of ratings.
     */
    @JsonProperty("numberOfRatings")
    private Integer numberOfRatings;
}