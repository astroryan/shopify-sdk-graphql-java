package com.shopify.sdk.graphql.scalar;

import graphql.GraphQLContext;
import graphql.execution.CoercedVariables;
import graphql.language.StringValue;
import graphql.language.Value;
import graphql.schema.*;
import com.shopify.sdk.model.common.URL;
import org.jetbrains.annotations.NotNull;

import java.net.MalformedURLException;
import java.util.Locale;

public class URLScalar implements Coercing<URL, String> {
    
    public static final GraphQLScalarType INSTANCE = GraphQLScalarType.newScalar()
            .name("URL")
            .description("Represents an RFC 3986 and RFC 3987 compliant URI string")
            .coercing(new URLScalar())
            .build();
    
    @Override
    public String serialize(@NotNull Object dataFetcherResult, @NotNull GraphQLContext graphQLContext, @NotNull Locale locale) throws CoercingSerializeException {
        if (dataFetcherResult instanceof URL) {
            return ((URL) dataFetcherResult).getValue();
        } else if (dataFetcherResult instanceof String) {
            // Validate the URL format
            try {
                new java.net.URL((String) dataFetcherResult);
                return (String) dataFetcherResult;
            } catch (MalformedURLException e) {
                // Try as URI if not a valid URL (some URIs are not URLs)
                try {
                    java.net.URI.create((String) dataFetcherResult);
                    return (String) dataFetcherResult;
                } catch (IllegalArgumentException ex) {
                    throw new CoercingSerializeException("Invalid URL format: " + dataFetcherResult);
                }
            }
        } else {
            throw new CoercingSerializeException("Expected URL or String but was " + dataFetcherResult.getClass().getSimpleName());
        }
    }
    
    @Override
    public URL parseValue(@NotNull Object input, @NotNull GraphQLContext graphQLContext, @NotNull Locale locale) throws CoercingParseValueException {
        if (input instanceof String) {
            try {
                // Try to validate as URL first
                new java.net.URL((String) input);
                return new URL((String) input);
            } catch (MalformedURLException e) {
                // Try as URI if not a valid URL
                try {
                    java.net.URI.create((String) input);
                    return new URL((String) input);
                } catch (IllegalArgumentException ex) {
                    throw new CoercingParseValueException("Invalid URL format: " + input);
                }
            }
        } else {
            throw new CoercingParseValueException("Expected String but was " + input.getClass().getSimpleName());
        }
    }
    
    @Override
    public URL parseLiteral(@NotNull Value<?> input, @NotNull CoercedVariables variables, @NotNull GraphQLContext graphQLContext, @NotNull Locale locale) throws CoercingParseLiteralException {
        if (input instanceof StringValue) {
            String value = ((StringValue) input).getValue();
            try {
                // Try to validate as URL first
                new java.net.URL(value);
                return new URL(value);
            } catch (MalformedURLException e) {
                // Try as URI if not a valid URL
                try {
                    java.net.URI.create(value);
                    return new URL(value);
                } catch (IllegalArgumentException ex) {
                    throw new CoercingParseLiteralException("Invalid URL format: " + value);
                }
            }
        } else {
            throw new CoercingParseLiteralException("Expected StringValue but was " + input.getClass().getSimpleName());
        }
    }
}