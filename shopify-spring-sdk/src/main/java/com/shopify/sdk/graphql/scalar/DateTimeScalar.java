package com.shopify.sdk.graphql.scalar;

import graphql.GraphQLContext;
import graphql.execution.CoercedVariables;
import graphql.language.StringValue;
import graphql.language.Value;
import graphql.schema.*;
import com.shopify.sdk.model.common.DateTime;
import org.jetbrains.annotations.NotNull;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Locale;

public class DateTimeScalar implements Coercing<DateTime, String> {
    
    public static final GraphQLScalarType INSTANCE = GraphQLScalarType.newScalar()
            .name("DateTime")
            .description("An ISO-8601 encoded UTC date time string. Example value: \"2019-07-03T20:47:55Z\"")
            .coercing(new DateTimeScalar())
            .build();
    
    private static final DateTimeFormatter ISO_8601_FORMATTER = DateTimeFormatter.ISO_OFFSET_DATE_TIME;
    
    @Override
    public String serialize(@NotNull Object dataFetcherResult, @NotNull GraphQLContext graphQLContext, @NotNull Locale locale) throws CoercingSerializeException {
        if (dataFetcherResult instanceof DateTime) {
            return ((DateTime) dataFetcherResult).getValue();
        } else if (dataFetcherResult instanceof String) {
            // Validate the string format
            try {
                ZonedDateTime.parse((String) dataFetcherResult, ISO_8601_FORMATTER);
                return (String) dataFetcherResult;
            } catch (DateTimeParseException e) {
                throw new CoercingSerializeException("Invalid DateTime format: " + dataFetcherResult);
            }
        } else if (dataFetcherResult instanceof ZonedDateTime) {
            return ISO_8601_FORMATTER.format((ZonedDateTime) dataFetcherResult);
        } else {
            throw new CoercingSerializeException("Expected DateTime, String or ZonedDateTime but was " + dataFetcherResult.getClass().getSimpleName());
        }
    }
    
    @Override
    public DateTime parseValue(@NotNull Object input, @NotNull GraphQLContext graphQLContext, @NotNull Locale locale) throws CoercingParseValueException {
        if (input instanceof String) {
            try {
                // Validate the format
                ZonedDateTime.parse((String) input, ISO_8601_FORMATTER);
                return new DateTime((String) input);
            } catch (DateTimeParseException e) {
                throw new CoercingParseValueException("Invalid DateTime format: " + input);
            }
        } else {
            throw new CoercingParseValueException("Expected String but was " + input.getClass().getSimpleName());
        }
    }
    
    @Override
    public DateTime parseLiteral(@NotNull Value<?> input, @NotNull CoercedVariables variables, @NotNull GraphQLContext graphQLContext, @NotNull Locale locale) throws CoercingParseLiteralException {
        if (input instanceof StringValue) {
            String value = ((StringValue) input).getValue();
            try {
                // Validate the format
                ZonedDateTime.parse(value, ISO_8601_FORMATTER);
                return new DateTime(value);
            } catch (DateTimeParseException e) {
                throw new CoercingParseLiteralException("Invalid DateTime format: " + value);
            }
        } else {
            throw new CoercingParseLiteralException("Expected StringValue but was " + input.getClass().getSimpleName());
        }
    }
}