package com.shopify.sdk;

import org.junit.platform.suite.api.*;

/**
 * Shopify SDK 전체 테스트 스위트
 * 
 * 테스트를 카테고리별로 그룹화하여 실행할 수 있습니다.
 */
@Suite
@SuiteDisplayName("Shopify Spring SDK Test Suite")
@SelectPackages("com.shopify.sdk")
@IncludeClassNamePatterns(".*Test")
@ExcludeTags("integration")  // 기본적으로 통합 테스트는 제외
public class ShopifySDKTestSuite {
    // 이 클래스는 테스트 스위트 실행을 위한 마커 클래스입니다.
}

/**
 * 단위 테스트만 실행하는 스위트
 */
@Suite
@SuiteDisplayName("Unit Tests Only")
@SelectPackages("com.shopify.sdk")
@IncludeTags("unit")
@ExcludeTags({"integration", "contract"})
class UnitTestSuite {
}

/**
 * 서비스 레이어 테스트만 실행하는 스위트
 */
@Suite
@SuiteDisplayName("Service Layer Tests")
@SelectPackages("com.shopify.sdk.service")
@IncludeClassNamePatterns(".*ServiceTest")
class ServiceTestSuite {
}

/**
 * 계약 테스트만 실행하는 스위트
 */
@Suite
@SuiteDisplayName("Contract Tests")
@SelectPackages("com.shopify.sdk.contract")
@IncludeTags("contract")
class ContractTestSuite {
}

/**
 * 통합 테스트만 실행하는 스위트 (실제 Shopify Store 필요)
 */
@Suite
@SuiteDisplayName("Integration Tests")
@SelectPackages("com.shopify.sdk")
@IncludeTags("integration")
@ConfigurationParameter(
    key = "junit.platform.execution.order.random.seed",
    value = "42"
)
class IntegrationTestSuite {
}