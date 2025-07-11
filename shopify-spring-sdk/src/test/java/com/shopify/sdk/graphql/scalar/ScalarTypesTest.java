package com.shopify.sdk.graphql.scalar;

import com.shopify.sdk.model.common.*;
import graphql.GraphQLContext;
import graphql.execution.CoercedVariables;
import graphql.language.StringValue;
import graphql.schema.CoercingParseValueException;
import graphql.schema.CoercingSerializeException;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

import static org.junit.jupiter.api.Assertions.*;

class ScalarTypesTest {
    
    private final GraphQLContext graphQLContext = GraphQLContext.getDefault();
    private final Locale locale = Locale.getDefault();
    private final CoercedVariables variables = CoercedVariables.emptyVariables();
    
    @Test
    void testIDScalarSerialization() {
        IDScalar scalar = new IDScalar();
        
        // Test with ID object
        ID id = new ID("gid://shopify/Product/123");
        String serialized = scalar.serialize(id, graphQLContext, locale);
        assertEquals("gid://shopify/Product/123", serialized);
        
        // Test with String
        String stringId = "gid://shopify/Order/456";
        serialized = scalar.serialize(stringId, graphQLContext, locale);
        assertEquals("gid://shopify/Order/456", serialized);
        
        // Test with invalid type
        assertThrows(CoercingSerializeException.class, () -> {
            scalar.serialize(123, graphQLContext, locale);
        });
    }
    
    @Test
    void testIDScalarParsing() {
        IDScalar scalar = new IDScalar();
        
        // Test parseValue
        String input = "gid://shopify/Product/123";
        ID parsed = scalar.parseValue(input, graphQLContext, locale);
        assertEquals("gid://shopify/Product/123", parsed.getValue());
        
        // Test parseLiteral
        StringValue literal = new StringValue("gid://shopify/Order/456");
        parsed = scalar.parseLiteral(literal, variables, graphQLContext, locale);
        assertEquals("gid://shopify/Order/456", parsed.getValue());
        
        // Test with invalid input
        assertThrows(CoercingParseValueException.class, () -> {
            scalar.parseValue(123, graphQLContext, locale);
        });
    }
    
    @Test
    void testDateTimeScalarSerialization() {
        DateTimeScalar scalar = new DateTimeScalar();
        
        // Test with DateTime object
        String isoDateTime = "2024-01-15T10:30:00Z";
        DateTime dateTime = new DateTime(isoDateTime);
        String serialized = scalar.serialize(dateTime, graphQLContext, locale);
        assertEquals(isoDateTime, serialized);
        
        // Test with String
        serialized = scalar.serialize(isoDateTime, graphQLContext, locale);
        assertEquals(isoDateTime, serialized);
        
        // Test with ZonedDateTime
        ZonedDateTime zdt = ZonedDateTime.parse(isoDateTime);
        serialized = scalar.serialize(zdt, graphQLContext, locale);
        assertTrue(serialized.contains("2024-01-15"));
        
        // Test with invalid format
        assertThrows(CoercingSerializeException.class, () -> {
            scalar.serialize("invalid-date", graphQLContext, locale);
        });
    }
    
    @Test
    void testDateTimeScalarParsing() {
        DateTimeScalar scalar = new DateTimeScalar();
        
        // Test parseValue
        String isoDateTime = "2024-01-15T10:30:00Z";
        DateTime parsed = scalar.parseValue(isoDateTime, graphQLContext, locale);
        assertEquals(isoDateTime, parsed.getValue());
        
        // Test parseLiteral
        StringValue literal = new StringValue("2024-01-15T10:30:00Z");
        parsed = scalar.parseLiteral(literal, variables, graphQLContext, locale);
        assertEquals("2024-01-15T10:30:00Z", parsed.getValue());
        
        // Test with invalid format
        assertThrows(CoercingParseValueException.class, () -> {
            scalar.parseValue("not-a-date", graphQLContext, locale);
        });
    }
    
    @Test
    void testMoneyScalarSerialization() {
        MoneyScalar scalar = new MoneyScalar();
        
        // Test with Money object
        Money money = Money.builder().amount("19.99").build();
        String serialized = scalar.serialize(money, graphQLContext, locale);
        assertEquals("19.99", serialized);
        
        // Test with String
        serialized = scalar.serialize("25.50", graphQLContext, locale);
        assertEquals("25.50", serialized);
        
        // Test with BigDecimal
        BigDecimal decimal = new BigDecimal("100.00");
        serialized = scalar.serialize(decimal, graphQLContext, locale);
        assertEquals("100.00", serialized);
        
        // Test with invalid format
        assertThrows(CoercingSerializeException.class, () -> {
            scalar.serialize("not-a-number", graphQLContext, locale);
        });
    }
    
    @Test
    void testMoneyScalarParsing() {
        MoneyScalar scalar = new MoneyScalar();
        
        // Test parseValue
        String amount = "19.99";
        Money parsed = scalar.parseValue(amount, graphQLContext, locale);
        assertEquals("19.99", parsed.getAmount());
        
        // Test parseLiteral
        StringValue literal = new StringValue("25.50");
        parsed = scalar.parseLiteral(literal, variables, graphQLContext, locale);
        assertEquals("25.50", parsed.getAmount());
        
        // Test with invalid format
        assertThrows(CoercingParseValueException.class, () -> {
            scalar.parseValue("invalid", graphQLContext, locale);
        });
    }
    
    @Test
    void testDecimalScalarSerialization() {
        DecimalScalar scalar = new DecimalScalar();
        
        // Test with Decimal object
        Decimal decimal = new Decimal("123.456");
        String serialized = scalar.serialize(decimal, graphQLContext, locale);
        assertEquals("123.456", serialized);
        
        // Test with String
        serialized = scalar.serialize("789.012", graphQLContext, locale);
        assertEquals("789.012", serialized);
        
        // Test with BigDecimal
        BigDecimal bd = new BigDecimal("999.999");
        serialized = scalar.serialize(bd, graphQLContext, locale);
        assertEquals("999.999", serialized);
        
        // Test with Number
        serialized = scalar.serialize(42.5, graphQLContext, locale);
        assertEquals("42.5", serialized);
        
        // Test with invalid format
        assertThrows(CoercingSerializeException.class, () -> {
            scalar.serialize("not-decimal", graphQLContext, locale);
        });
    }
    
    @Test
    void testDecimalScalarParsing() {
        DecimalScalar scalar = new DecimalScalar();
        
        // Test parseValue with String
        String value = "123.456";
        Decimal parsed = scalar.parseValue(value, graphQLContext, locale);
        assertEquals("123.456", parsed.getValue());
        
        // Test parseValue with Number
        parsed = scalar.parseValue(789.012, graphQLContext, locale);
        assertEquals("789.012", parsed.getValue());
        
        // Test parseLiteral
        StringValue literal = new StringValue("999.999");
        parsed = scalar.parseLiteral(literal, variables, graphQLContext, locale);
        assertEquals("999.999", parsed.getValue());
        
        // Test with invalid format
        assertThrows(CoercingParseValueException.class, () -> {
            scalar.parseValue("invalid", graphQLContext, locale);
        });
    }
    
    @Test
    void testURLScalarSerialization() {
        URLScalar scalar = new URLScalar();
        
        // Test with URL object
        URL url = new URL("https://example.com/path");
        String serialized = scalar.serialize(url, graphQLContext, locale);
        assertEquals("https://example.com/path", serialized);
        
        // Test with valid URL String
        serialized = scalar.serialize("https://test.com", graphQLContext, locale);
        assertEquals("https://test.com", serialized);
        
        // Test with valid URI (not URL)
        serialized = scalar.serialize("urn:isbn:0451450523", graphQLContext, locale);
        assertEquals("urn:isbn:0451450523", serialized);
        
        // Test with invalid format
        assertThrows(CoercingSerializeException.class, () -> {
            scalar.serialize("not a url", graphQLContext, locale);
        });
    }
    
    @Test
    void testURLScalarParsing() {
        URLScalar scalar = new URLScalar();
        
        // Test parseValue with URL
        String urlString = "https://example.com/path";
        URL parsed = scalar.parseValue(urlString, graphQLContext, locale);
        assertEquals("https://example.com/path", parsed.getValue());
        
        // Test parseValue with URI
        String uriString = "urn:isbn:0451450523";
        parsed = scalar.parseValue(uriString, graphQLContext, locale);
        assertEquals("urn:isbn:0451450523", parsed.getValue());
        
        // Test parseLiteral
        StringValue literal = new StringValue("https://test.com");
        parsed = scalar.parseLiteral(literal, variables, graphQLContext, locale);
        assertEquals("https://test.com", parsed.getValue());
        
        // Test with invalid format
        assertThrows(CoercingParseValueException.class, () -> {
            scalar.parseValue("not a url", graphQLContext, locale);
        });
    }
    
    @Test
    void testJSONScalarSerialization() {
        JSONScalar scalar = new JSONScalar();
        
        // Test with JSON string
        String jsonString = "{\"key\":\"value\"}";
        Object serialized = scalar.serialize(jsonString, graphQLContext, locale);
        assertNotNull(serialized);
        assertTrue(serialized instanceof java.util.Map);
        
        // Test with Map
        java.util.Map<String, Object> map = new java.util.HashMap<>();
        map.put("test", "value");
        serialized = scalar.serialize(map, graphQLContext, locale);
        assertEquals(map, serialized);
        
        // Test with List
        java.util.List<String> list = java.util.Arrays.asList("a", "b", "c");
        serialized = scalar.serialize(list, graphQLContext, locale);
        assertEquals(list, serialized);
        
        // Test with invalid JSON
        assertThrows(CoercingSerializeException.class, () -> {
            scalar.serialize("not-json", graphQLContext, locale);
        });
    }
    
    @Test
    void testJSONScalarParsing() {
        JSONScalar scalar = new JSONScalar();
        
        // Test parseValue with JSON string
        String jsonString = "{\"key\":\"value\"}";
        Object parsed = scalar.parseValue(jsonString, graphQLContext, locale);
        assertNotNull(parsed);
        assertTrue(parsed instanceof java.util.Map);
        
        // Test parseValue with Map
        java.util.Map<String, Object> map = new java.util.HashMap<>();
        map.put("test", "value");
        parsed = scalar.parseValue(map, graphQLContext, locale);
        assertEquals(map, parsed);
        
        // Test parseLiteral
        StringValue literal = new StringValue("[1,2,3]");
        parsed = scalar.parseLiteral(literal, variables, graphQLContext, locale);
        assertNotNull(parsed);
        assertTrue(parsed instanceof java.util.List);
        
        // Test with invalid JSON
        assertThrows(CoercingParseValueException.class, () -> {
            scalar.parseValue("invalid-json", graphQLContext, locale);
        });
    }
}