package com.shopify.sdk.graphql.scalar;

import graphql.GraphQLContext;
import graphql.execution.CoercedVariables;
import graphql.language.StringValue;
import graphql.language.Value;
import graphql.schema.*;
import com.shopify.sdk.model.common.ID;
import org.jetbrains.annotations.NotNull;

import java.util.Locale;

public class IDScalar implements Coercing<ID, String> {
    
    public static final GraphQLScalarType INSTANCE = GraphQLScalarType.newScalar()
            .name("ID")
            .description("Represents a unique identifier, encoded as a String")
            .coercing(new IDScalar())
            .build();
    
    @Override
    public String serialize(@NotNull Object dataFetcherResult, @NotNull GraphQLContext graphQLContext, @NotNull Locale locale) throws CoercingSerializeException {
        if (dataFetcherResult instanceof ID) {
            return ((ID) dataFetcherResult).getValue();
        } else if (dataFetcherResult instanceof String) {
            return (String) dataFetcherResult;
        } else {
            throw new CoercingSerializeException("Expected ID or String but was " + dataFetcherResult.getClass().getSimpleName());
        }
    }
    
    @Override
    public ID parseValue(@NotNull Object input, @NotNull GraphQLContext graphQLContext, @NotNull Locale locale) throws CoercingParseValueException {
        if (input instanceof String) {
            return new ID((String) input);
        } else {
            throw new CoercingParseValueException("Expected String but was " + input.getClass().getSimpleName());
        }
    }
    
    @Override
    public ID parseLiteral(@NotNull Value<?> input, @NotNull CoercedVariables variables, @NotNull GraphQLContext graphQLContext, @NotNull Locale locale) throws CoercingParseLiteralException {
        if (input instanceof StringValue) {
            return new ID(((StringValue) input).getValue());
        } else {
            throw new CoercingParseLiteralException("Expected StringValue but was " + input.getClass().getSimpleName());
        }
    }
}