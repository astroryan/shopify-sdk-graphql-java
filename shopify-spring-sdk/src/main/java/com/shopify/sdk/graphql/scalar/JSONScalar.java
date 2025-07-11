package com.shopify.sdk.graphql.scalar;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import graphql.GraphQLContext;
import graphql.execution.CoercedVariables;
import graphql.language.StringValue;
import graphql.language.Value;
import graphql.schema.*;
import org.jetbrains.annotations.NotNull;

import java.util.Locale;
import java.util.Map;

public class JSONScalar implements Coercing<Object, Object> {
    
    public static final GraphQLScalarType INSTANCE = GraphQLScalarType.newScalar()
            .name("JSON")
            .description("Represents untyped JSON")
            .coercing(new JSONScalar())
            .build();
    
    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();
    
    @Override
    public Object serialize(@NotNull Object dataFetcherResult, @NotNull GraphQLContext graphQLContext, @NotNull Locale locale) throws CoercingSerializeException {
        if (dataFetcherResult == null) {
            return null;
        }
        
        if (dataFetcherResult instanceof String) {
            // Validate it's valid JSON
            try {
                return OBJECT_MAPPER.readValue((String) dataFetcherResult, Object.class);
            } catch (JsonProcessingException e) {
                throw new CoercingSerializeException("Invalid JSON format: " + e.getMessage());
            }
        } else if (dataFetcherResult instanceof Map || dataFetcherResult instanceof java.util.List) {
            return dataFetcherResult;
        } else {
            // Try to convert to JSON representation
            try {
                String json = OBJECT_MAPPER.writeValueAsString(dataFetcherResult);
                return OBJECT_MAPPER.readValue(json, Object.class);
            } catch (JsonProcessingException e) {
                throw new CoercingSerializeException("Cannot serialize to JSON: " + e.getMessage());
            }
        }
    }
    
    @Override
    public Object parseValue(@NotNull Object input, @NotNull GraphQLContext graphQLContext, @NotNull Locale locale) throws CoercingParseValueException {
        if (input instanceof String) {
            try {
                return OBJECT_MAPPER.readValue((String) input, Object.class);
            } catch (JsonProcessingException e) {
                throw new CoercingParseValueException("Invalid JSON format: " + e.getMessage());
            }
        } else if (input instanceof Map || input instanceof java.util.List) {
            return input;
        } else {
            throw new CoercingParseValueException("Expected String, Map or List but was " + input.getClass().getSimpleName());
        }
    }
    
    @Override
    public Object parseLiteral(@NotNull Value<?> input, @NotNull CoercedVariables variables, @NotNull GraphQLContext graphQLContext, @NotNull Locale locale) throws CoercingParseLiteralException {
        if (input instanceof StringValue) {
            String value = ((StringValue) input).getValue();
            try {
                return OBJECT_MAPPER.readValue(value, Object.class);
            } catch (JsonProcessingException e) {
                throw new CoercingParseLiteralException("Invalid JSON format: " + e.getMessage());
            }
        } else {
            throw new CoercingParseLiteralException("Expected StringValue but was " + input.getClass().getSimpleName());
        }
    }
}