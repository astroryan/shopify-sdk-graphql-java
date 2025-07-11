package com.shopify.sdk.model.retail.input;

import io.leangen.graphql.annotations.GraphQLQuery;
import lombok.*;

/**
 * Input for creating a POS external device.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PosExternalDeviceCreateInput {
    
    @GraphQLQuery(name = "name", description = "The name of the external device")
    private String name;
    
    @GraphQLQuery(name = "externalId", description = "The external ID of the device")
    private String externalId;
    
    @GraphQLQuery(name = "type", description = "The type of external device")
    private String type;
    
    @GraphQLQuery(name = "vendor", description = "The vendor of the device")
    private String vendor;
    
    @GraphQLQuery(name = "model", description = "The model of the device")
    private String model;
    
    @GraphQLQuery(name = "serialNumber", description = "The serial number of the device")
    private String serialNumber;
    
    @GraphQLQuery(name = "locationId", description = "The ID of the location where the device will be used")
    private String locationId;
}