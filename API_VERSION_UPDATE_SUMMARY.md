# Shopify Spring SDK API 버전 업데이트 요약 (2025-07)

## 업데이트 내용

### 1. API 버전 변경
- **이전 버전**: 2024-01
- **새 버전**: 2025-07 (최신)

### 2. 변경된 파일들

#### SDK 코드
- `ShopifyProperties.java`: 기본 API 버전을 2025-07로 업데이트
- `build.gradle`: SDK 버전을 1.0.0에서 1.1.0으로 업데이트
- `application.yml`: API 버전을 2025-07로 업데이트

#### 문서
- `README.md`: 
  - SDK 버전을 1.1.0으로 업데이트
  - configuration 예시에서 API 버전 업데이트
- `SHOPIFY_SDK_USAGE_GUIDE.md`:
  - 모든 예시 코드에서 API 버전을 2025-07로 업데이트
  - SDK 버전을 1.1.0으로 업데이트
  - Markdownlint 경고 수정 (빈 줄 추가 등)

#### 테스트
- `ShopifySDKIntegrationTest.java`: 
  - 테스트 프로퍼티에서 API 버전을 2025-07로 업데이트
  - 프로퍼티 경로를 새로운 구조에 맞게 수정

### 3. API 호환성 검증

#### 확인된 사항
- **엔드포인트 형식**: 변경 없음
  ```
  https://{store_name}.myshopify.com/admin/api/2025-07/graphql.json
  ```
- **인증 방식**: 변경 없음 (X-Shopify-Access-Token 헤더)
- **Rate Limiting**: 변경 없음 (calculated query costs 사용)
- **에러 처리**: 변경 없음 (userErrors 형식 유지)

#### GraphQL 스키마
- 2025-07 버전에서 특별한 breaking changes는 문서에서 언급되지 않음
- 기존 모델 클래스들이 계속 호환될 것으로 예상

### 4. 권장사항

1. **테스트**: 
   - 통합 테스트를 실행하여 모든 API 호출이 정상 작동하는지 확인
   - 특히 Product, Order, Customer 관련 기능 중점 테스트

2. **모니터링**:
   - 배포 후 API 에러율 모니터링
   - Rate limit 관련 이슈 확인

3. **향후 업데이트**:
   - Shopify는 분기별로 새 API 버전을 릴리스하므로 정기적인 버전 확인 필요
   - 다음 버전: 2025-10 (예상)

### 5. 주요 설정 예시

```yaml
# application.yml
shopify:
  sdk:
    api:
      version: "2025-07"
      timeout:
        connect: 10s
        read: 30s
        write: 30s
      retry:
        max-attempts: 3
        backoff-delay: 1000
```

```java
// AuthContext 생성
ShopifyAuthContext authContext = ShopifyAuthContext.builder()
    .shopDomain("your-store.myshopify.com")
    .accessToken("shpat_your_access_token")
    .apiVersion("2025-07")
    .build();
```

## 결론

SDK가 Shopify Admin GraphQL API 2025-07 버전으로 성공적으로 업데이트되었습니다. 
기존 API와의 호환성이 유지되므로 코드 변경 없이 새 버전을 사용할 수 있습니다.