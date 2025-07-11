package com.shopify.sdk.graphql.scalar;

import graphql.GraphQLContext;
import graphql.execution.CoercedVariables;
import graphql.language.StringValue;
import graphql.language.Value;
import graphql.schema.*;
import com.shopify.sdk.model.common.Money;
import org.jetbrains.annotations.NotNull;

import java.math.BigDecimal;
import java.util.Locale;

public class MoneyScalar implements Coercing<Money, String> {
    
    public static final GraphQLScalarType INSTANCE = GraphQLScalarType.newScalar()
            .name("Money")
            .description("Represents an amount of money in a specific currency")
            .coercing(new MoneyScalar())
            .build();
    
    @Override
    public String serialize(@NotNull Object dataFetcherResult, @NotNull GraphQLContext graphQLContext, @NotNull Locale locale) throws CoercingSerializeException {
        if (dataFetcherResult instanceof Money) {
            return ((Money) dataFetcherResult).getAmount();
        } else if (dataFetcherResult instanceof String) {
            // Validate the string is a valid decimal
            try {
                new BigDecimal((String) dataFetcherResult);
                return (String) dataFetcherResult;
            } catch (NumberFormatException e) {
                throw new CoercingSerializeException("Invalid Money format: " + dataFetcherResult);
            }
        } else if (dataFetcherResult instanceof BigDecimal) {
            return dataFetcherResult.toString();
        } else {
            throw new CoercingSerializeException("Expected Money, String or BigDecimal but was " + dataFetcherResult.getClass().getSimpleName());
        }
    }
    
    @Override
    public Money parseValue(@NotNull Object input, @NotNull GraphQLContext graphQLContext, @NotNull Locale locale) throws CoercingParseValueException {
        if (input instanceof String) {
            try {
                new BigDecimal((String) input);
                return Money.builder()
                        .amount((String) input)
                        .build();
            } catch (NumberFormatException e) {
                throw new CoercingParseValueException("Invalid Money format: " + input);
            }
        } else {
            throw new CoercingParseValueException("Expected String but was " + input.getClass().getSimpleName());
        }
    }
    
    @Override
    public Money parseLiteral(@NotNull Value<?> input, @NotNull CoercedVariables variables, @NotNull GraphQLContext graphQLContext, @NotNull Locale locale) throws CoercingParseLiteralException {
        if (input instanceof StringValue) {
            String value = ((StringValue) input).getValue();
            try {
                new BigDecimal(value);
                return Money.builder()
                        .amount(value)
                        .build();
            } catch (NumberFormatException e) {
                throw new CoercingParseLiteralException("Invalid Money format: " + value);
            }
        } else {
            throw new CoercingParseLiteralException("Expected StringValue but was " + input.getClass().getSimpleName());
        }
    }
}