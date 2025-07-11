package com.shopify.sdk.model.retail.input;

import io.leangen.graphql.annotations.GraphQLQuery;
import lombok.*;

import java.math.BigDecimal;

/**
 * Input for opening a retail session.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RetailSessionOpenInput {
    
    @GraphQLQuery(name = "deviceId", description = "The ID of the device for this session")
    private String deviceId;
    
    @GraphQLQuery(name = "staffMemberId", description = "The ID of the staff member opening the session")
    private String staffMemberId;
    
    @GraphQLQuery(name = "locationId", description = "The ID of the location for this session")
    private String locationId;
    
    @GraphQLQuery(name = "openingCashAmount", description = "The opening cash amount")
    private BigDecimal openingCashAmount;
    
    @GraphQLQuery(name = "currency", description = "The currency for the cash amount")
    private String currency;
    
    @GraphQLQuery(name = "notes", description = "Notes about the session opening")
    private String notes;
}