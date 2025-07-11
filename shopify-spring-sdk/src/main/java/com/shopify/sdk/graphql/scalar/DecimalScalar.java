package com.shopify.sdk.graphql.scalar;

import graphql.GraphQLContext;
import graphql.execution.CoercedVariables;
import graphql.language.StringValue;
import graphql.language.Value;
import graphql.schema.*;
import com.shopify.sdk.model.common.Decimal;
import org.jetbrains.annotations.NotNull;

import java.math.BigDecimal;
import java.util.Locale;

public class DecimalScalar implements Coercing<Decimal, String> {
    
    public static final GraphQLScalarType INSTANCE = GraphQLScalarType.newScalar()
            .name("Decimal")
            .description("A signed decimal number, which supports arbitrary precision and is serialized as a string")
            .coercing(new DecimalScalar())
            .build();
    
    @Override
    public String serialize(@NotNull Object dataFetcherResult, @NotNull GraphQLContext graphQLContext, @NotNull Locale locale) throws CoercingSerializeException {
        if (dataFetcherResult instanceof Decimal) {
            return ((Decimal) dataFetcherResult).getValue();
        } else if (dataFetcherResult instanceof String) {
            // Validate the string is a valid decimal
            try {
                new BigDecimal((String) dataFetcherResult);
                return (String) dataFetcherResult;
            } catch (NumberFormatException e) {
                throw new CoercingSerializeException("Invalid Decimal format: " + dataFetcherResult);
            }
        } else if (dataFetcherResult instanceof BigDecimal) {
            return dataFetcherResult.toString();
        } else if (dataFetcherResult instanceof Number) {
            return dataFetcherResult.toString();
        } else {
            throw new CoercingSerializeException("Expected Decimal, String, BigDecimal or Number but was " + dataFetcherResult.getClass().getSimpleName());
        }
    }
    
    @Override
    public Decimal parseValue(@NotNull Object input, @NotNull GraphQLContext graphQLContext, @NotNull Locale locale) throws CoercingParseValueException {
        if (input instanceof String) {
            try {
                new BigDecimal((String) input);
                return new Decimal((String) input);
            } catch (NumberFormatException e) {
                throw new CoercingParseValueException("Invalid Decimal format: " + input);
            }
        } else if (input instanceof Number) {
            return new Decimal(input.toString());
        } else {
            throw new CoercingParseValueException("Expected String or Number but was " + input.getClass().getSimpleName());
        }
    }
    
    @Override
    public Decimal parseLiteral(@NotNull Value<?> input, @NotNull CoercedVariables variables, @NotNull GraphQLContext graphQLContext, @NotNull Locale locale) throws CoercingParseLiteralException {
        if (input instanceof StringValue) {
            String value = ((StringValue) input).getValue();
            try {
                new BigDecimal(value);
                return new Decimal(value);
            } catch (NumberFormatException e) {
                throw new CoercingParseLiteralException("Invalid Decimal format: " + value);
            }
        } else {
            throw new CoercingParseLiteralException("Expected StringValue but was " + input.getClass().getSimpleName());
        }
    }
}