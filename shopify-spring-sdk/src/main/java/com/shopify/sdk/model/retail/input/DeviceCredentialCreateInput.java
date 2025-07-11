package com.shopify.sdk.model.retail.input;

import io.leangen.graphql.annotations.GraphQLQuery;
import lombok.*;

import java.time.Instant;

/**
 * Input for creating device credentials.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DeviceCredentialCreateInput {
    
    @GraphQLQuery(name = "deviceId", description = "The ID of the device")
    private String deviceId;
    
    @GraphQLQuery(name = "expiresAt", description = "When the credentials should expire")
    private Instant expiresAt;
    
    @GraphQLQuery(name = "scopes", description = "The scopes for the credentials")
    private String scopes;
}