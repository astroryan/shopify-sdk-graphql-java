package com.shopify.sdk.graphql.config;

import com.shopify.sdk.graphql.scalar.*;
import graphql.schema.GraphQLScalarType;
import graphql.schema.idl.RuntimeWiring;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
// import org.springframework.graphql.execution.RuntimeWiringConfigurer;

/**
 * GraphQL configuration for Shopify SDK.
 * Registers custom scalar types for proper serialization/deserialization
 * of Shopify-specific types in GraphQL operations.
 */
@Configuration
public class GraphQLConfiguration {
    
    /**
     * Configures runtime wiring for GraphQL with custom scalar types.
     * These scalars handle the conversion between Java types and GraphQL types.
     * Note: Uncomment and adjust based on your Spring GraphQL version
     */
    /*
    @Bean
    public RuntimeWiringConfigurer runtimeWiringConfigurer() {
        return wiringBuilder -> wiringBuilder
                .scalar(IDScalar.INSTANCE)
                .scalar(DateTimeScalar.INSTANCE)
                .scalar(MoneyScalar.INSTANCE)
                .scalar(DecimalScalar.INSTANCE)
                .scalar(URLScalar.INSTANCE)
                .scalar(JSONScalar.INSTANCE)
                .scalar(createHTMLScalar())
                .scalar(createFormattedStringScalar())
                .scalar(createUnsignedInt64Scalar());
    }
    */
    
    /**
     * Alternative configuration for GraphQL scalar types
     * Use this if not using Spring GraphQL
     */
    @Bean
    public RuntimeWiring runtimeWiring() {
        return RuntimeWiring.newRuntimeWiring()
                .scalar(IDScalar.INSTANCE)
                .scalar(DateTimeScalar.INSTANCE)
                .scalar(MoneyScalar.INSTANCE)
                .scalar(DecimalScalar.INSTANCE)
                .scalar(URLScalar.INSTANCE)
                .scalar(JSONScalar.INSTANCE)
                .scalar(createHTMLScalar())
                .scalar(createFormattedStringScalar())
                .scalar(createUnsignedInt64Scalar())
                .build();
    }
    
    /**
     * Creates a scalar for HTML content.
     * HTML is represented as a string but with HTML content validation.
     */
    private GraphQLScalarType createHTMLScalar() {
        return GraphQLScalarType.newScalar()
                .name("HTML")
                .description("A string containing HTML content")
                .coercing(new graphql.Scalars.GraphQLString().getCoercing())
                .build();
    }
    
    /**
     * Creates a scalar for formatted strings.
     * These are strings with specific formatting requirements.
     */
    private GraphQLScalarType createFormattedStringScalar() {
        return GraphQLScalarType.newScalar()
                .name("FormattedString")
                .description("A string with formatting options")
                .coercing(new graphql.Scalars.GraphQLString().getCoercing())
                .build();
    }
    
    /**
     * Creates a scalar for unsigned 64-bit integers.
     * Represented as strings to maintain precision in JavaScript.
     */
    private GraphQLScalarType createUnsignedInt64Scalar() {
        return GraphQLScalarType.newScalar()
                .name("UnsignedInt64")
                .description("An unsigned 64-bit integer encoded as a string")
                .coercing(new graphql.Scalars.GraphQLString().getCoercing())
                .build();
    }
}