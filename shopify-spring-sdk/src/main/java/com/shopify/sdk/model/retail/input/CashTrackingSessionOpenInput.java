package com.shopify.sdk.model.retail.input;

import io.leangen.graphql.annotations.GraphQLQuery;
import lombok.*;

import java.math.BigDecimal;

/**
 * Input for opening a cash tracking session.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CashTrackingSessionOpenInput {
    
    @GraphQLQuery(name = "retailSessionId", description = "The ID of the retail session")
    private String retailSessionId;
    
    @GraphQLQuery(name = "openingCashAmount", description = "The opening cash amount")
    private BigDecimal openingCashAmount;
    
    @GraphQLQuery(name = "currency", description = "The currency for the cash amount")
    private String currency;
    
    @GraphQLQuery(name = "notes", description = "Notes about the cash tracking session")
    private String notes;
}