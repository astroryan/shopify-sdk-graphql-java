package com.shopify.sdk.model.retail.input;

import io.leangen.graphql.annotations.GraphQLQuery;
import lombok.*;

import java.math.BigDecimal;

/**
 * Input for closing a cash tracking session.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CashTrackingSessionCloseInput {
    
    @GraphQLQuery(name = "sessionId", description = "The ID of the cash tracking session to close")
    private String sessionId;
    
    @GraphQLQuery(name = "closingCashAmount", description = "The actual closing cash amount")
    private BigDecimal closingCashAmount;
    
    @GraphQLQuery(name = "notes", description = "Notes about the closing")
    private String notes;
}