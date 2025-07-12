# Publishing Guide - Shopify Spring SDK

Complete guide for publishing the Shopify Spring SDK to GitHub Packages and managing releases.

## 📋 Prerequisites

### Required Access & Tools

- **GitHub Repository Access**: Write access to [astroryan/shopify-sdk-graphql-java](https://github.com/astroryan/shopify-sdk-graphql-java)
- **GitHub Personal Access Token**: Token with `write:packages` and `repo` scopes
- **Java Development Kit**: JDK 17 or higher
- **Gradle**: Version 8.0 or higher (wrapper included)
- **Git**: For version tagging and repository management

### Environment Setup

Create a GitHub Personal Access Token:

1. Go to GitHub Settings → Developer settings → Personal access tokens → Tokens (classic)
2. Generate new token with the following scopes:
   - `write:packages` - Required for publishing to GitHub Packages
   - `repo` - Required for creating releases and managing repository
   - `workflow` - Required if using GitHub Actions for automated publishing
3. Copy and securely store the token

## 🔧 Publishing Methods

### Method 1: Manual Publishing (Recommended for Testing)

#### Step 1: Set Environment Variables

```bash
# Set your GitHub credentials
export GITHUB_ACTOR=your-github-username
export GITHUB_TOKEN=your-personal-access-token

# Verify environment variables are set
echo "GitHub Actor: $GITHUB_ACTOR"
echo "GitHub Token: ${GITHUB_TOKEN:0:8}..." # Shows only first 8 characters for security
```

#### Step 2: Update Version

Edit the version in `build.gradle`:

```gradle
group = 'com.shopify'
version = '1.2.0'  // Update to your target version
```

#### Step 3: Run Pre-Publishing Checks

```bash
# Clean previous builds
./gradlew clean

# Run all tests
./gradlew test

# Run integration tests (optional, requires Shopify store credentials)
export SHOPIFY_TEST_STORE_DOMAIN="your-dev-store.myshopify.com"
export SHOPIFY_TEST_ACCESS_TOKEN="your-test-access-token"
./gradlew integrationTest

# Generate test coverage report
./gradlew jacocoTestReport

# Build the project
./gradlew build

# Verify publication configuration
./gradlew publishInfo
```

#### Step 4: Publish to GitHub Packages

```bash
# Publish all publications
./gradlew publish

# Alternative: Publish specific publication
./gradlew publishShopifySdkPublicationToGitHubPackagesRepository
```

#### Step 5: Create Git Tag and GitHub Release

```bash
# Create and push git tag
git tag v1.2.0
git push origin v1.2.0

# Create GitHub release (using GitHub CLI)
gh release create v1.2.0 \
  --title "Shopify Spring SDK v1.2.0" \
  --notes "Release notes for version 1.2.0" \
  --target main
```

### Method 2: Automated Publishing (GitHub Actions)

#### Release-Based Publishing

The repository includes automated workflows that trigger on release creation:

1. **Create Release on GitHub Web Interface**:
   - Go to repository → Releases → Create a new release
   - Tag version: `v1.2.0`
   - Release title: `Shopify Spring SDK v1.2.0`
   - Add release notes
   - Publish release

2. **Workflow Execution**:
   - The `publish.yml` workflow automatically triggers
   - Builds the SDK with all tests
   - Publishes to GitHub Packages
   - Updates release with build artifacts

#### Manual Workflow Dispatch

For on-demand publishing:

1. Go to Actions → "Publish to GitHub Packages"
2. Click "Run workflow"
3. Select branch (usually `main`)
4. Enter version to publish (e.g., `1.2.0`)
5. Click "Run workflow"

### Method 3: CI/CD Integration

#### GitHub Actions Workflow Configuration

The repository includes these workflows:

**`.github/workflows/ci.yml`** - Continuous Integration:
```yaml
name: CI
on: [push, pull_request]
jobs:
  test:
    runs-on: ubuntu-latest
    steps:
      - uses: actions/checkout@v4
      - name: Set up JDK 17
        uses: actions/setup-java@v3
        with:
          java-version: '17'
          distribution: 'temurin'
      - name: Cache Gradle packages
        uses: actions/cache@v3
        with:
          path: |
            ~/.gradle/caches
            ~/.gradle/wrapper
          key: ${{ runner.os }}-gradle-${{ hashFiles('**/*.gradle*', '**/gradle-wrapper.properties') }}
      - name: Run tests
        run: ./gradlew test jacocoTestReport
      - name: Upload coverage reports
        uses: codecov/codecov-action@v3
```

**`.github/workflows/publish.yml`** - Publishing:
```yaml
name: Publish to GitHub Packages
on:
  release:
    types: [published]
  workflow_dispatch:
    inputs:
      version:
        description: 'Version to publish'
        required: true
        type: string

jobs:
  publish:
    runs-on: ubuntu-latest
    permissions:
      contents: read
      packages: write
    steps:
      - uses: actions/checkout@v4
      - name: Set up JDK 17
        uses: actions/setup-java@v3
        with:
          java-version: '17'
          distribution: 'temurin'
      - name: Update version
        if: github.event.inputs.version
        run: |
          sed -i "s/version = '.*'/version = '${{ github.event.inputs.version }}'/" build.gradle
      - name: Build and test
        run: ./gradlew build test
      - name: Publish to GitHub Packages
        run: ./gradlew publish
        env:
          GITHUB_ACTOR: ${{ github.actor }}
          GITHUB_TOKEN: ${{ secrets.GITHUB_TOKEN }}
```

## 📝 Version Management

### Semantic Versioning Strategy

Follow [Semantic Versioning 2.0.0](https://semver.org/):

- **MAJOR** (X.0.0): Breaking API changes, incompatible changes
- **MINOR** (X.Y.0): New features, backward compatible additions
- **PATCH** (X.Y.Z): Bug fixes, backward compatible fixes

### Version Examples and Guidelines

| Version | Type | Description | Example Changes |
|---------|------|-------------|-----------------|
| `1.0.0` | Initial | First stable release | Complete SDK implementation |
| `1.1.0` | Minor | New features | Added webhook support, new API endpoints |
| `1.1.1` | Patch | Bug fixes | Fixed authentication issue, memory leak |
| `1.2.0` | Minor | API additions | New GraphQL scalars, bulk operations |
| `2.0.0` | Major | Breaking changes | Changed method signatures, removed deprecated APIs |

### Pre-Release Versions

For testing and development:

- **Alpha**: `1.2.0-alpha.1` - Early development, unstable
- **Beta**: `1.2.0-beta.1` - Feature complete, testing phase
- **Release Candidate**: `1.2.0-rc.1` - Final testing before release

## ✅ Pre-Publishing Checklist

### Code Quality & Testing

- [ ] **All tests pass**: `./gradlew test`
- [ ] **Integration tests pass**: `./gradlew integrationTest` (if applicable)
- [ ] **Code coverage meets threshold**: `./gradlew jacocoTestCoverageVerification`
- [ ] **No compilation warnings**: Clean build without warnings
- [ ] **Documentation updated**: README, API docs, examples

### Version & Release Management

- [ ] **Version updated in `build.gradle`**
- [ ] **CHANGELOG.md updated** with new version and changes
- [ ] **Version follows semantic versioning**
- [ ] **Breaking changes documented** (for major versions)
- [ ] **Migration guide provided** (for breaking changes)

### Configuration & Dependencies

- [ ] **Dependencies are up to date** and compatible
- [ ] **Gradle wrapper updated** (if needed)
- [ ] **Publishing configuration verified**
- [ ] **GitHub repository settings configured**

### Security & Compliance

- [ ] **No secrets or credentials in code**
- [ ] **Security scan completed** (if available)
- [ ] **License compliance verified**
- [ ] **Third-party dependency licenses reviewed**

## 🔍 Verification & Testing

### Verify Published Package

1. **Check GitHub Packages**: 
   - Visit: `https://github.com/astroryan/shopify-sdk-graphql-java/packages`
   - Verify new version is listed and accessible

2. **Test Package Download**:
   ```bash
   # Create temporary test project
   mkdir sdk-test && cd sdk-test
   gradle init --type java-application
   
   # Add repository and dependency to build.gradle
   cat >> build.gradle << EOF
   repositories {
       maven {
           url = uri("https://maven.pkg.github.com/astroryan/shopify-sdk-graphql-java")
           credentials {
               username = System.getenv("GITHUB_ACTOR")
               password = System.getenv("GITHUB_TOKEN")
           }
       }
   }
   dependencies {
       implementation 'com.shopify:shopify-spring-sdk:1.2.0'
   }
   EOF
   
   # Test dependency resolution
   ./gradlew dependencies --configuration runtimeClasspath
   ```

### Integration Testing

Create a test Spring Boot application:

```java
@SpringBootApplication
public class SdkTestApplication {
    
    @Autowired
    private ShopifyApi shopifyApi;
    
    @PostConstruct
    public void testSdk() {
        log.info("Shopify SDK loaded successfully: {}", 
            shopifyApi.getClass().getSimpleName());
    }
    
    public static void main(String[] args) {
        SpringApplication.run(SdkTestApplication.class, args);
    }
}
```

## 🚨 Troubleshooting

### Common Publishing Issues

#### Authentication Errors

**Error**: `Received status code 401 from server: Unauthorized`

**Solutions**:
- Verify GitHub token has `write:packages` scope
- Check token expiration date
- Ensure GITHUB_ACTOR matches token owner
- Verify repository permissions

```bash
# Test authentication
curl -H "Authorization: token $GITHUB_TOKEN" \
     https://api.github.com/user
```

#### Package Already Exists

**Error**: `Cannot publish artifact with version that already exists`

**Solutions**:
- Increment version number in `build.gradle`
- Use semantic versioning for new releases
- Delete existing version (if in development)

#### Dependency Resolution Issues

**Error**: `Could not find com.shopify:shopify-spring-sdk:X.X.X`

**Solutions**:
- Verify repository URL in consumer project
- Check authentication credentials
- Ensure package visibility is correct
- Wait for package propagation (usually < 5 minutes)

#### Build Failures

**Error**: Build fails during publishing

**Solutions**:
- Check all tests pass locally
- Verify Java version compatibility
- Clear Gradle cache: `./gradlew clean --refresh-dependencies`
- Check for dependency conflicts

### GitHub Actions Debugging

#### Workflow Fails

1. **Check workflow logs**:
   - Go to Actions tab → Select failed workflow
   - Review each step's logs

2. **Common issues**:
   - Missing secrets configuration
   - Incorrect permissions
   - Environment differences

3. **Debug steps**:
   ```yaml
   - name: Debug environment
     run: |
       echo "Java version: $(java -version)"
       echo "Gradle version: $(./gradlew --version)"
       echo "Working directory: $(pwd)"
       ls -la
   ```

### Emergency Rollback

If a published version has critical issues:

1. **Immediate steps**:
   - Update documentation to recommend previous version
   - Create hotfix branch for critical fixes
   - Communicate issue to users via GitHub Issues

2. **Create hotfix release**:
   ```bash
   # Create hotfix branch from last stable tag
   git checkout v1.1.9
   git checkout -b hotfix/1.1.10
   
   # Apply critical fixes
   # ... make necessary changes ...
   
   # Create hotfix release
   git tag v1.1.10
   git push origin v1.1.10
   ./gradlew publish
   ```

## 📚 Additional Resources

### Documentation

- **Gradle Publishing Plugin**: [Official Documentation](https://docs.gradle.org/current/userguide/publishing_maven.html)
- **GitHub Packages**: [Maven Registry Guide](https://docs.github.com/en/packages/working-with-a-github-packages-registry/working-with-the-apache-maven-registry)
- **Semantic Versioning**: [SemVer.org](https://semver.org/)
- **GitHub Actions**: [Publishing Packages](https://docs.github.com/en/actions/publishing-packages)

### Tools

- **GitHub CLI**: For release management - `gh release create`
- **Gradle Wrapper**: Ensure consistent builds - `./gradlew wrapper --gradle-version 8.5`
- **JReleaser**: Advanced release management - [JReleaser](https://jreleaser.org/)

### Security

- **Token Management**: Use organization secrets for shared repositories
- **Least Privilege**: Grant minimum required permissions
- **Token Rotation**: Regularly rotate personal access tokens
- **Audit Logs**: Monitor package access and downloads

## 📞 Support

For publishing issues:

1. **Check existing issues**: [GitHub Issues](https://github.com/astroryan/shopify-sdk-graphql-java/issues)
2. **GitHub Actions logs**: Review workflow execution details
3. **Gradle build logs**: Run with `--info` or `--debug` flags
4. **Create new issue**: Include logs, environment details, and reproduction steps

**Quick Help Commands**:
```bash
# Get current version
./gradlew properties | grep version

# Test publishing (dry run)
./gradlew publishToMavenLocal

# Check publication configuration
./gradlew publishInfo

# Validate build.gradle
./gradlew help --task publish
```