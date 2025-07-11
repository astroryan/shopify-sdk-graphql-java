# Shopify Spring SDK 테스트 구조 정리

## 테스트 디렉토리 구조

```
src/test/java/com/shopify/sdk/
├── service/                      # 서비스 레이어 테스트
│   ├── ProductServiceTest.java   # 상품 서비스 테스트
│   ├── OrderServiceTest.java     # 주문 서비스 테스트  
│   ├── CustomerServiceTest.java  # 고객 서비스 테스트
│   ├── ShopifyOAuthServiceTest.java  # OAuth 인증 테스트
│   ├── WebhookServiceTest.java   # 웹훅 서비스 테스트
│   ├── BulkOperationServiceTest.java # 대량 작업 테스트
│   ├── InventoryServiceTest.java # 재고 관리 테스트
│   ├── MetafieldServiceTest.java # 메타필드 테스트
│   └── FulfillmentServiceTest.java # 주문 이행 테스트
├── client/                       # 클라이언트 테스트
│   └── ShopifyGraphQLClientTest.java
├── graphql/scalar/               # GraphQL 스칼라 타입 테스트
│   └── ScalarTypesTest.java
├── contract/                     # API 계약 테스트
│   └── ShopifyApiContractTest.java
├── integration/                  # 통합 테스트 (Store 필요)
│   └── ShopifySDKIntegrationTest.java
├── test/                         # 테스트 유틸리티
│   └── ShopifyMockServer.java
├── fixtures/                     # 테스트 데이터
│   └── ShopifyTestFixtures.java
└── ShopifySDKTestSuite.java      # 테스트 스위트

src/test/resources/
└── fixtures/                     # JSON fixture 파일
    ├── product_response.json
    └── orders_response.json
```

## 테스트 분류

### 1. 단위 테스트 (Unit Tests) - 80%
- **위치**: `service/`, `client/`, `graphql/scalar/`
- **특징**: Mock 사용, Store 불필요
- **실행**: 항상 실행 (기본)

### 2. 계약 테스트 (Contract Tests) - 15%
- **위치**: `contract/`
- **특징**: Mock Server 사용, API 스키마 검증
- **실행**: 항상 실행

### 3. 통합 테스트 (Integration Tests) - 5%
- **위치**: `integration/`
- **특징**: 실제 Shopify Store 필요
- **실행**: 선택적 실행 (`@Tag("integration")`)

## 테스트 실행 방법

### 모든 테스트 실행 (통합 테스트 제외)
```bash
./gradlew test
```

### 통합 테스트만 실행
```bash
# 환경변수 설정 필요
export SHOPIFY_TEST_STORE_DOMAIN=your-store.myshopify.com
export SHOPIFY_TEST_ACCESS_TOKEN=your-access-token

./gradlew integrationTest
```

### 특정 카테고리 테스트 실행
```bash
# 서비스 테스트만
./gradlew test --tests "*ServiceTest"

# 계약 테스트만
./gradlew test --tests "*ContractTest"
```

### JUnit 스위트로 실행
```java
// IDE에서 직접 실행
@RunWith(JUnitPlatform.class)
@SelectClasses(ShopifySDKTestSuite.class)
```

## 추가된 테스트 파일

### 1. CustomerServiceTest
- 고객 CRUD 작업
- 고객 검색 및 필터링
- 주소 관리
- 마케팅 동의 처리
- 태그 관리

### 2. ShopifyOAuthServiceTest
- OAuth 인증 URL 생성
- Access Token 교환
- HMAC 서명 검증
- Webhook 서명 검증
- Shop 도메인 유효성 검증
- Nonce 재사용 방지

### 3. WebhookServiceTest
- 웹훅 구독 생성 (HTTP/EventBridge)
- 웹훅 목록 조회 및 필터링
- 웹훅 업데이트/삭제
- 알림 실패 조회
- 필수 웹훅 자동 생성

### 4. BulkOperationServiceTest
- 대량 쿼리/뮤테이션 작업 생성
- 작업 상태 확인 및 완료 대기
- 작업 취소
- 에러 처리 (동시 실행 제한)
- 타임아웃 처리

### 5. InventoryServiceTest
- 재고 아이템 조회
- 재고 수준 조회 (위치별)
- 재고 조정 및 설정
- 재고 이동 (위치 간)
- 재고 활성화/비활성화

### 6. MetafieldServiceTest
- 메타필드 생성 (단일/일괄)
- 메타필드 조회 (ID/소유자별)
- 메타필드 업데이트
- 메타필드 삭제 (단일/일괄)
- JSON 타입 메타필드 처리

### 7. FulfillmentServiceTest
- 주문 이행 생성 (전체/부분)
- 이행 상태 업데이트 및 추적
- 이행 취소
- 이행 주문 관리
- 이행 이벤트 생성

### 8. ShopifyTestFixtures
- 실제 API 응답 기반 테스트 데이터
- Product, Order, Customer 응답
- 에러 응답 시뮬레이션
- Mock Response 빌더

### 9. ShopifyMockServer
- 실제 HTTP 통신 시뮬레이션
- Shopify API 동작 정확히 모방
- 다양한 시나리오 지원
- SDK 사용자도 활용 가능

## 아직 테스트가 필요한 주요 서비스

1. **BillingService** - 결제 및 구독
2. **ShippingService** - 배송 관리
3. **DiscountService** - 할인 및 프로모션
4. **CollectionService** - 컬렉션 관리
5. **ReportService** - 리포트 및 분석

## 테스트 커버리지 목표

| 컴포넌트 | 현재 | 목표 | 상태 |
|---------|------|------|------|
| Service Layer | ~45% | 80% | 🟡 진행중 |
| Client Layer | 50% | 90% | 🟡 진행중 |
| Model Layer | 0% | 60% | 🔴 필요 |
| GraphQL Scalars | 100% | 100% | 🟢 완료 |
| 전체 | ~40% | 75% | 🟡 진행중 |

## CI/CD 권장 설정

```yaml
# .github/workflows/test.yml
name: Tests

on: [push, pull_request]

jobs:
  unit-tests:
    runs-on: ubuntu-latest
    steps:
      - uses: actions/checkout@v3
      - name: Run Unit Tests
        run: ./gradlew test
      
  integration-tests:
    if: github.ref == 'refs/heads/main'
    runs-on: ubuntu-latest
    steps:
      - uses: actions/checkout@v3
      - name: Run Integration Tests
        env:
          SHOPIFY_TEST_STORE_DOMAIN: ${{ secrets.SHOPIFY_TEST_STORE }}
          SHOPIFY_TEST_ACCESS_TOKEN: ${{ secrets.SHOPIFY_ACCESS_TOKEN }}
        run: ./gradlew integrationTest
```

## 결론

테스트 구조가 정리되어 일관성 있는 패키지 구조를 갖추었습니다. 
총 10개의 주요 서비스에 대한 테스트가 작성되었으며:
- ProductService, OrderService, CustomerService (핵심 리소스)
- ShopifyOAuthService, WebhookService (인증 및 이벤트)
- BulkOperationService (대량 처리)
- InventoryService, MetafieldService, FulfillmentService (고급 기능)

SDK 사용자들이 Shopify Store 없이도 개발하고 테스트할 수 있는 환경이 구축되었으며,
Mock Server와 Test Fixtures를 통해 실제 API 동작을 정확히 시뮬레이션할 수 있습니다.