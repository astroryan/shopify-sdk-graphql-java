package com.shopify.sdk.integration;

import com.shopify.sdk.TestApplication;
import com.shopify.sdk.config.IntegrationTestConfiguration;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.condition.EnabledIfEnvironmentVariable;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

/**
 * Base class for integration tests.
 * Uses a minimal Spring Boot test configuration to avoid ApplicationContext loading issues.
 */
@Tag("integration")
@EnabledIfEnvironmentVariable(named = "SHOPIFY_TEST_STORE_DOMAIN", matches = ".+")
@SpringBootTest(classes = TestApplication.class)
@ContextConfiguration(classes = IntegrationTestConfiguration.class)
public abstract class BaseIntegrationTest {
    // Common setup for integration tests can go here
}