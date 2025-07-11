package com.shopify.sdk.model.retail.input;

import io.leangen.graphql.annotations.GraphQLQuery;
import lombok.*;

import java.math.BigDecimal;

/**
 * Input for closing a retail session.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RetailSessionCloseInput {
    
    @GraphQLQuery(name = "sessionId", description = "The ID of the session to close")
    private String sessionId;
    
    @GraphQLQuery(name = "closingCashAmount", description = "The actual closing cash amount")
    private BigDecimal closingCashAmount;
    
    @GraphQLQuery(name = "notes", description = "Notes about the session closing")
    private String notes;
}