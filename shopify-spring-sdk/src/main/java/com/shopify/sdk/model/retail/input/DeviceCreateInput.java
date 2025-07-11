package com.shopify.sdk.model.retail.input;

import io.leangen.graphql.annotations.GraphQLQuery;
import lombok.*;

/**
 * Input for creating a device.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DeviceCreateInput {
    
    @GraphQLQuery(name = "name", description = "The name of the device")
    private String name;
    
    @GraphQLQuery(name = "type", description = "The type of the device")
    private String type;
    
    @GraphQLQuery(name = "locationId", description = "The ID of the location where the device will be used")
    private String locationId;
    
    @GraphQLQuery(name = "staffMemberId", description = "The ID of the staff member assigned to the device")
    private String staffMemberId;
    
    @GraphQLQuery(name = "enabled", description = "Whether the device should be enabled")
    private Boolean enabled;
    
    @GraphQLQuery(name = "metadata", description = "Additional metadata for the device")
    private String metadata;
}