# Publishing Guide for Shopify Spring SDK

This guide explains how to publish the Shopify Spring SDK to GitHub Packages.

## Prerequisites

1. **GitHub Repository Access**: Ensure you have write access to the repository
2. **GitHub Token**: Create a Personal Access Token with `write:packages` scope
3. **Gradle**: Gradle 8.0+ installed locally

## Publishing Process

### 1. Manual Publishing (Local)

#### Set Environment Variables
```bash
export GITHUB_ACTOR=your-github-username
export GITHUB_TOKEN=your-personal-access-token
```

#### Update Version
Edit `shopify-spring-sdk/build.gradle`:
```gradle
version = '1.1.0'  // Update to new version
```

#### Build and Test
```bash
cd shopify-spring-sdk
./gradlew clean build test
```

#### Publish to GitHub Packages
```bash
./gradlew publish
```

### 2. Automated Publishing (GitHub Actions)

The SDK includes automated publishing workflows:

#### Release-based Publishing
1. Create a new release on GitHub
2. The `publish.yml` workflow will automatically:
   - Build the SDK
   - Run all tests
   - Publish to GitHub Packages
   - Update release notes

#### Manual Workflow Dispatch
1. Go to Actions → Publish to GitHub Packages
2. Click "Run workflow"
3. Enter the version to publish
4. The workflow will handle the rest

## Version Management

### Version Format
Follow semantic versioning (MAJOR.MINOR.PATCH):
- **MAJOR**: Breaking API changes
- **MINOR**: New features, backward compatible
- **PATCH**: Bug fixes

### Version Examples
- `1.0.0` - Initial release
- `1.1.0` - New features added
- `1.1.1` - Bug fixes
- `2.0.0` - Breaking changes

## Verification

### Check Published Package
Visit: https://github.com/astroryan/shopify-sdk-graphql-java/packages

### Test Installation
Create a test project:

```gradle
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
    implementation 'com.shopify:shopify-spring-sdk:1.1.0'
}
```

## Troubleshooting

### Authentication Errors
```
Could not find com.shopify:shopify-spring-sdk:1.1.0
```
**Solution**: Verify your GitHub token has `read:packages` scope

### Publishing Failures
```
Received status code 401 from server: Unauthorized
```
**Solution**: Ensure your token has `write:packages` scope

### Version Already Exists
```
Cannot publish artifact with version that already exists
```
**Solution**: Increment the version number in build.gradle

## Release Checklist

Before publishing a new version:

- [ ] Update version in `build.gradle`
- [ ] Update `CHANGELOG.md` with release notes
- [ ] Run all tests: `./gradlew test`
- [ ] Run integration tests (if applicable)
- [ ] Update documentation if needed
- [ ] Create git tag: `git tag v1.1.0`
- [ ] Push tag: `git push origin v1.1.0`
- [ ] Create GitHub release
- [ ] Verify package published correctly
- [ ] Test installation in a sample project

## Security Best Practices

1. **Never commit tokens**: Keep tokens in environment variables
2. **Use minimal scopes**: Only grant necessary permissions
3. **Rotate tokens regularly**: Update tokens periodically
4. **Use GitHub Secrets**: For CI/CD, use GitHub repository secrets

## CI/CD Integration

The repository includes GitHub Actions workflows:

### CI Workflow (`ci.yml`)
- Runs on every push and PR
- Executes unit tests
- Generates coverage reports
- Checks code quality

### Publish Workflow (`publish.yml`)
- Triggered by releases or manual dispatch
- Builds and tests the SDK
- Publishes to GitHub Packages
- Updates release notes

## Support

For publishing issues:
1. Check GitHub Actions logs
2. Verify credentials and permissions
3. Open an issue on GitHub if problems persist