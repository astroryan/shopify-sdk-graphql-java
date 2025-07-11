package com.shopify.sdk.model.retail;

import com.shopify.sdk.model.common.Node;
import io.leangen.graphql.annotations.GraphQLQuery;
import lombok.*;

import java.time.Instant;

/**
 * Represents credentials for a device.
 * Device credentials are used to authenticate devices for retail operations.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DeviceCredential implements Node {
    
    @GraphQLQuery(name = "id", description = "A globally-unique ID")
    private String id;
    
    @GraphQLQuery(name = "device", description = "The device associated with these credentials")
    private Device device;
    
    @GraphQLQuery(name = "token", description = "The authentication token")
    private String token;
    
    @GraphQLQuery(name = "active", description = "Whether the credentials are active")
    private Boolean active;
    
    @GraphQLQuery(name = "expiresAt", description = "When the credentials expire")
    private Instant expiresAt;
    
    @GraphQLQuery(name = "createdAt", description = "The date and time when the credentials were created")
    private Instant createdAt;
    
    @GraphQLQuery(name = "updatedAt", description = "The date and time when the credentials were last updated")
    private Instant updatedAt;
    
    @GraphQLQuery(name = "lastUsedAt", description = "The date and time when the credentials were last used")
    private Instant lastUsedAt;
}