package com.shopify.sdk.model.common;

/**
 * Available Shopify API versions.
 * Maps to Node.js ApiVersion enum.
 */
public enum ApiVersion {
    OCTOBER_22("2022-10"),
    JANUARY_23("2023-01"),
    APRIL_23("2023-04"),
    JULY_23("2023-07"),
    OCTOBER_23("2023-10"),
    JANUARY_24("2024-01"),
    APRIL_24("2024-04"),
    JULY_24("2024-07"),
    OCTOBER_24("2024-10"),
    JANUARY_25("2025-01"),
    APRIL_25("2025-04"),
    JULY_25("2025-07"),
    UNSTABLE("unstable");

    private final String version;

    ApiVersion(String version) {
        this.version = version;
    }

    public String getVersion() {
        return version;
    }

    @Override
    public String toString() {
        return version;
    }

    public static ApiVersion fromString(String version) {
        for (ApiVersion apiVersion : values()) {
            if (apiVersion.version.equals(version)) {
                return apiVersion;
            }
        }
        throw new IllegalArgumentException("Unknown API version: " + version);
    }

    public static final ApiVersion LATEST = APRIL_25;
    public static final ApiVersion RELEASE_CANDIDATE = JULY_25;
}