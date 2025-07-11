package com.shopify.sdk.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.shopify.sdk.client.GraphQLRequest;
import com.shopify.sdk.client.GraphQLResponse;
import com.shopify.sdk.client.ShopifyGraphQLClient;
import com.shopify.sdk.config.ShopifyAuthContext;
import com.shopify.sdk.exception.ShopifyApiException;
import com.shopify.sdk.model.common.UserError;
import com.shopify.sdk.model.onlinestore.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.*;

@Slf4j
@Service
@RequiredArgsConstructor
public class OnlineStoreService {
    
    private final ShopifyGraphQLClient graphQLClient;
    
    // Query for pages
    private static final String GET_PAGES_QUERY = """
        query pages($first: Int!, $after: String, $query: String, $sortKey: PageSortKeys, $reverse: Boolean) {
            pages(first: $first, after: $after, query: $query, sortKey: $sortKey, reverse: $reverse) {
                edges {
                    node {
                        id
                        author
                        body
                        bodySummary
                        createdAt
                        handle
                        isPublished
                        onlineStorePreviewUrl
                        onlineStoreUrl
                        publishedAt
                        seo {
                            description
                            title
                        }
                        templateSuffix
                        title
                        updatedAt
                    }
                    cursor
                }
                pageInfo {
                    hasNextPage
                    endCursor
                }
            }
        }
        """;
    
    // Query for single page
    private static final String GET_PAGE_QUERY = """
        query page($id: ID!) {
            page(id: $id) {
                id
                author
                body
                bodySummary
                createdAt
                handle
                isPublished
                metafield(namespace: $namespace, key: $key) {
                    id
                    namespace
                    key
                    value
                }
                metafields(first: 50) {
                    edges {
                        node {
                            id
                            namespace
                            key
                            value
                        }
                    }
                }
                onlineStorePreviewUrl
                onlineStoreUrl
                publishedAt
                seo {
                    description
                    title
                }
                templateSuffix
                title
                updatedAt
            }
        }
        """;
    
    // Query for blogs
    private static final String GET_BLOGS_QUERY = """
        query blogs($first: Int!, $after: String, $query: String, $sortKey: BlogSortKeys, $reverse: Boolean) {
            blogs(first: $first, after: $after, query: $query, sortKey: $sortKey, reverse: $reverse) {
                edges {
                    node {
                        id
                        authors {
                            bio
                            displayName
                            email
                            firstName
                            lastName
                        }
                        commentPolicy
                        createdAt
                        handle
                        isModerated
                        onlineStorePreviewUrl
                        onlineStoreUrl
                        seo {
                            description
                            title
                        }
                        tags
                        templateSuffix
                        title
                        updatedAt
                    }
                    cursor
                }
                pageInfo {
                    hasNextPage
                    endCursor
                }
            }
        }
        """;
    
    // Query for articles
    private static final String GET_ARTICLES_QUERY = """
        query articles($first: Int!, $after: String, $query: String, $sortKey: ArticleSortKeys, $reverse: Boolean) {
            articles(first: $first, after: $after, query: $query, sortKey: $sortKey, reverse: $reverse) {
                edges {
                    node {
                        id
                        author {
                            bio
                            displayName
                            email
                            firstName
                            lastName
                        }
                        blog {
                            id
                            title
                        }
                        content
                        contentHtml
                        createdAt
                        excerpt
                        handle
                        image {
                            id
                            altText
                            url
                        }
                        isPublished
                        onlineStorePreviewUrl
                        onlineStoreUrl
                        publishedAt
                        seo {
                            description
                            title
                        }
                        summary
                        tags
                        templateSuffix
                        title
                        updatedAt
                    }
                    cursor
                }
                pageInfo {
                    hasNextPage
                    endCursor
                }
            }
        }
        """;
    
    // Query for themes
    private static final String GET_THEMES_QUERY = """
        query themes($first: Int!, $after: String) {
            themes(first: $first, after: $after) {
                edges {
                    node {
                        id
                        createdAt
                        name
                        previewable
                        processingErrors
                        role
                        themeStore
                        updatedAt
                    }
                    cursor
                }
                pageInfo {
                    hasNextPage
                    endCursor
                }
            }
        }
        """;
    
    // Query for script tags
    private static final String GET_SCRIPT_TAGS_QUERY = """
        query scriptTags($first: Int!, $after: String, $src: String) {
            scriptTags(first: $first, after: $after, src: $src) {
                edges {
                    node {
                        id
                        cache
                        createdAt
                        displayScope
                        src
                        updatedAt
                    }
                    cursor
                }
                pageInfo {
                    hasNextPage
                    endCursor
                }
            }
        }
        """;
    
    // Query for navigations/menus
    private static final String GET_MENUS_QUERY = """
        query menus($first: Int!, $after: String, $query: String) {
            menus(first: $first, after: $after, query: $query) {
                edges {
                    node {
                        id
                        handle
                        isDefault
                        items {
                            id
                            title
                            type
                            url
                            resourceId
                            tags
                        }
                        itemsCount
                        title
                    }
                    cursor
                }
                pageInfo {
                    hasNextPage
                    endCursor
                }
            }
        }
        """;
    
    // Query for URL redirects
    private static final String GET_URL_REDIRECTS_QUERY = """
        query urlRedirects($first: Int!, $after: String, $query: String) {
            urlRedirects(first: $first, after: $after, query: $query) {
                edges {
                    node {
                        id
                        path
                        target
                    }
                    cursor
                }
                pageInfo {
                    hasNextPage
                    endCursor
                }
            }
        }
        """;
    
    // Mutation to create page
    private static final String CREATE_PAGE_MUTATION = """
        mutation pageCreate($page: PageCreateInput!) {
            pageCreate(page: $page) {
                page {
                    id
                    title
                    handle
                    body
                    isPublished
                }
                userErrors {
                    field
                    message
                }
            }
        }
        """;
    
    // Mutation to update page
    private static final String UPDATE_PAGE_MUTATION = """
        mutation pageUpdate($id: ID!, $page: PageUpdateInput!) {
            pageUpdate(id: $id, page: $page) {
                page {
                    id
                    title
                    handle
                    body
                    isPublished
                }
                userErrors {
                    field
                    message
                }
            }
        }
        """;
    
    // Mutation to delete page
    private static final String DELETE_PAGE_MUTATION = """
        mutation pageDelete($id: ID!) {
            pageDelete(id: $id) {
                deletedPageId
                userErrors {
                    field
                    message
                }
            }
        }
        """;
    
    // Mutation to create blog
    private static final String CREATE_BLOG_MUTATION = """
        mutation blogCreate($blog: BlogCreateInput!) {
            blogCreate(blog: $blog) {
                blog {
                    id
                    title
                    handle
                    commentPolicy
                }
                userErrors {
                    field
                    message
                }
            }
        }
        """;
    
    // Mutation to update blog
    private static final String UPDATE_BLOG_MUTATION = """
        mutation blogUpdate($id: ID!, $blog: BlogUpdateInput!) {
            blogUpdate(id: $id, blog: $blog) {
                blog {
                    id
                    title
                    handle
                    commentPolicy
                }
                userErrors {
                    field
                    message
                }
            }
        }
        """;
    
    // Mutation to delete blog
    private static final String DELETE_BLOG_MUTATION = """
        mutation blogDelete($id: ID!) {
            blogDelete(id: $id) {
                deletedBlogId
                userErrors {
                    field
                    message
                }
            }
        }
        """;
    
    // Mutation to create article
    private static final String CREATE_ARTICLE_MUTATION = """
        mutation articleCreate($article: ArticleCreateInput!) {
            articleCreate(article: $article) {
                article {
                    id
                    title
                    handle
                    content
                    isPublished
                }
                userErrors {
                    field
                    message
                }
            }
        }
        """;
    
    // Mutation to update article
    private static final String UPDATE_ARTICLE_MUTATION = """
        mutation articleUpdate($id: ID!, $article: ArticleUpdateInput!) {
            articleUpdate(id: $id, article: $article) {
                article {
                    id
                    title
                    handle
                    content
                    isPublished
                }
                userErrors {
                    field
                    message
                }
            }
        }
        """;
    
    // Mutation to delete article
    private static final String DELETE_ARTICLE_MUTATION = """
        mutation articleDelete($id: ID!) {
            articleDelete(id: $id) {
                deletedArticleId
                userErrors {
                    field
                    message
                }
            }
        }
        """;
    
    // Mutation to create script tag
    private static final String CREATE_SCRIPT_TAG_MUTATION = """
        mutation scriptTagCreate($input: ScriptTagInput!) {
            scriptTagCreate(input: $input) {
                scriptTag {
                    id
                    src
                    displayScope
                    cache
                }
                userErrors {
                    field
                    message
                }
            }
        }
        """;
    
    // Mutation to update script tag
    private static final String UPDATE_SCRIPT_TAG_MUTATION = """
        mutation scriptTagUpdate($id: ID!, $input: ScriptTagInput!) {
            scriptTagUpdate(id: $id, input: $input) {
                scriptTag {
                    id
                    src
                    displayScope
                    cache
                }
                userErrors {
                    field
                    message
                }
            }
        }
        """;
    
    // Mutation to delete script tag
    private static final String DELETE_SCRIPT_TAG_MUTATION = """
        mutation scriptTagDelete($id: ID!) {
            scriptTagDelete(id: $id) {
                deletedScriptTagId
                userErrors {
                    field
                    message
                }
            }
        }
        """;
    
    // Mutation to create URL redirect
    private static final String CREATE_URL_REDIRECT_MUTATION = """
        mutation urlRedirectCreate($urlRedirect: UrlRedirectInput!) {
            urlRedirectCreate(urlRedirect: $urlRedirect) {
                urlRedirect {
                    id
                    path
                    target
                }
                userErrors {
                    field
                    message
                }
            }
        }
        """;
    
    // Mutation to update URL redirect
    private static final String UPDATE_URL_REDIRECT_MUTATION = """
        mutation urlRedirectUpdate($id: ID!, $urlRedirect: UrlRedirectInput!) {
            urlRedirectUpdate(id: $id, urlRedirect: $urlRedirect) {
                urlRedirect {
                    id
                    path
                    target
                }
                userErrors {
                    field
                    message
                }
            }
        }
        """;
    
    // Mutation to delete URL redirect
    private static final String DELETE_URL_REDIRECT_MUTATION = """
        mutation urlRedirectDelete($id: ID!) {
            urlRedirectDelete(id: $id) {
                deletedUrlRedirectId
                userErrors {
                    field
                    message
                }
            }
        }
        """;
    
    // Service methods
    public List<Page> getPages(
            ShopifyAuthContext context,
            int first,
            String after,
            String query,
            PageSortKeys sortKey,
            Boolean reverse) {
        ShopifyGraphQLClient.setAuthContext(context);
        
        Map<String, Object> variables = new HashMap<>();
        variables.put("first", first);
        if (after != null) {
            variables.put("after", after);
        }
        if (query != null) {
            variables.put("query", query);
        }
        if (sortKey != null) {
            variables.put("sortKey", sortKey);
        }
        if (reverse != null) {
            variables.put("reverse", reverse);
        }
        
        GraphQLRequest request = GraphQLRequest.builder()
                .query(GET_PAGES_QUERY)
                .variables(variables)
                .build();
        
        GraphQLResponse<PagesResponse> response = graphQLClient.execute(
                request,
                new TypeReference<GraphQLResponse<PagesResponse>>() {}
        );
        
        if (response.hasErrors()) {
            throw new ShopifyApiException("Failed to get pages", response.getErrors());
        }
        
        List<Page> pages = new ArrayList<>();
        response.getData().pages.edges.forEach(edge -> 
            pages.add(edge.node)
        );
        
        return pages;
    }
    
    public Page getPage(
            ShopifyAuthContext context,
            String id,
            String namespace,
            String key) {
        ShopifyGraphQLClient.setAuthContext(context);
        
        Map<String, Object> variables = new HashMap<>();
        variables.put("id", id);
        if (namespace != null) {
            variables.put("namespace", namespace);
        }
        if (key != null) {
            variables.put("key", key);
        }
        
        GraphQLRequest request = GraphQLRequest.builder()
                .query(GET_PAGE_QUERY)
                .variables(variables)
                .build();
        
        GraphQLResponse<PageResponse> response = graphQLClient.execute(
                request,
                new TypeReference<GraphQLResponse<PageResponse>>() {}
        );
        
        if (response.hasErrors()) {
            throw new ShopifyApiException("Failed to get page", response.getErrors());
        }
        
        return response.getData().page;
    }
    
    public List<Blog> getBlogs(
            ShopifyAuthContext context,
            int first,
            String after,
            String query,
            BlogSortKeys sortKey,
            Boolean reverse) {
        ShopifyGraphQLClient.setAuthContext(context);
        
        Map<String, Object> variables = new HashMap<>();
        variables.put("first", first);
        if (after != null) {
            variables.put("after", after);
        }
        if (query != null) {
            variables.put("query", query);
        }
        if (sortKey != null) {
            variables.put("sortKey", sortKey);
        }
        if (reverse != null) {
            variables.put("reverse", reverse);
        }
        
        GraphQLRequest request = GraphQLRequest.builder()
                .query(GET_BLOGS_QUERY)
                .variables(variables)
                .build();
        
        GraphQLResponse<BlogsResponse> response = graphQLClient.execute(
                request,
                new TypeReference<GraphQLResponse<BlogsResponse>>() {}
        );
        
        if (response.hasErrors()) {
            throw new ShopifyApiException("Failed to get blogs", response.getErrors());
        }
        
        List<Blog> blogs = new ArrayList<>();
        response.getData().blogs.edges.forEach(edge -> 
            blogs.add(edge.node)
        );
        
        return blogs;
    }
    
    public List<Article> getArticles(
            ShopifyAuthContext context,
            int first,
            String after,
            String query,
            ArticleSortKeys sortKey,
            Boolean reverse) {
        ShopifyGraphQLClient.setAuthContext(context);
        
        Map<String, Object> variables = new HashMap<>();
        variables.put("first", first);
        if (after != null) {
            variables.put("after", after);
        }
        if (query != null) {
            variables.put("query", query);
        }
        if (sortKey != null) {
            variables.put("sortKey", sortKey);
        }
        if (reverse != null) {
            variables.put("reverse", reverse);
        }
        
        GraphQLRequest request = GraphQLRequest.builder()
                .query(GET_ARTICLES_QUERY)
                .variables(variables)
                .build();
        
        GraphQLResponse<ArticlesResponse> response = graphQLClient.execute(
                request,
                new TypeReference<GraphQLResponse<ArticlesResponse>>() {}
        );
        
        if (response.hasErrors()) {
            throw new ShopifyApiException("Failed to get articles", response.getErrors());
        }
        
        List<Article> articles = new ArrayList<>();
        response.getData().articles.edges.forEach(edge -> 
            articles.add(edge.node)
        );
        
        return articles;
    }
    
    public List<OnlineStoreTheme> getThemes(
            ShopifyAuthContext context,
            int first,
            String after) {
        ShopifyGraphQLClient.setAuthContext(context);
        
        Map<String, Object> variables = new HashMap<>();
        variables.put("first", first);
        if (after != null) {
            variables.put("after", after);
        }
        
        GraphQLRequest request = GraphQLRequest.builder()
                .query(GET_THEMES_QUERY)
                .variables(variables)
                .build();
        
        GraphQLResponse<ThemesResponse> response = graphQLClient.execute(
                request,
                new TypeReference<GraphQLResponse<ThemesResponse>>() {}
        );
        
        if (response.hasErrors()) {
            throw new ShopifyApiException("Failed to get themes", response.getErrors());
        }
        
        List<OnlineStoreTheme> themes = new ArrayList<>();
        response.getData().themes.edges.forEach(edge -> 
            themes.add(edge.node)
        );
        
        return themes;
    }
    
    public List<ScriptTag> getScriptTags(
            ShopifyAuthContext context,
            int first,
            String after,
            String src) {
        ShopifyGraphQLClient.setAuthContext(context);
        
        Map<String, Object> variables = new HashMap<>();
        variables.put("first", first);
        if (after != null) {
            variables.put("after", after);
        }
        if (src != null) {
            variables.put("src", src);
        }
        
        GraphQLRequest request = GraphQLRequest.builder()
                .query(GET_SCRIPT_TAGS_QUERY)
                .variables(variables)
                .build();
        
        GraphQLResponse<ScriptTagsResponse> response = graphQLClient.execute(
                request,
                new TypeReference<GraphQLResponse<ScriptTagsResponse>>() {}
        );
        
        if (response.hasErrors()) {
            throw new ShopifyApiException("Failed to get script tags", response.getErrors());
        }
        
        List<ScriptTag> scriptTags = new ArrayList<>();
        response.getData().scriptTags.edges.forEach(edge -> 
            scriptTags.add(edge.node)
        );
        
        return scriptTags;
    }
    
    public List<Menu> getMenus(
            ShopifyAuthContext context,
            int first,
            String after,
            String query) {
        ShopifyGraphQLClient.setAuthContext(context);
        
        Map<String, Object> variables = new HashMap<>();
        variables.put("first", first);
        if (after != null) {
            variables.put("after", after);
        }
        if (query != null) {
            variables.put("query", query);
        }
        
        GraphQLRequest request = GraphQLRequest.builder()
                .query(GET_MENUS_QUERY)
                .variables(variables)
                .build();
        
        GraphQLResponse<MenusResponse> response = graphQLClient.execute(
                request,
                new TypeReference<GraphQLResponse<MenusResponse>>() {}
        );
        
        if (response.hasErrors()) {
            throw new ShopifyApiException("Failed to get menus", response.getErrors());
        }
        
        List<Menu> menus = new ArrayList<>();
        response.getData().menus.edges.forEach(edge -> 
            menus.add(edge.node)
        );
        
        return menus;
    }
    
    public List<UrlRedirect> getUrlRedirects(
            ShopifyAuthContext context,
            int first,
            String after,
            String query) {
        ShopifyGraphQLClient.setAuthContext(context);
        
        Map<String, Object> variables = new HashMap<>();
        variables.put("first", first);
        if (after != null) {
            variables.put("after", after);
        }
        if (query != null) {
            variables.put("query", query);
        }
        
        GraphQLRequest request = GraphQLRequest.builder()
                .query(GET_URL_REDIRECTS_QUERY)
                .variables(variables)
                .build();
        
        GraphQLResponse<UrlRedirectsResponse> response = graphQLClient.execute(
                request,
                new TypeReference<GraphQLResponse<UrlRedirectsResponse>>() {}
        );
        
        if (response.hasErrors()) {
            throw new ShopifyApiException("Failed to get URL redirects", response.getErrors());
        }
        
        List<UrlRedirect> urlRedirects = new ArrayList<>();
        response.getData().urlRedirects.edges.forEach(edge -> 
            urlRedirects.add(edge.node)
        );
        
        return urlRedirects;
    }
    
    public Page createPage(ShopifyAuthContext context, PageCreateInput page) {
        ShopifyGraphQLClient.setAuthContext(context);
        
        Map<String, Object> variables = new HashMap<>();
        variables.put("page", page);
        
        GraphQLRequest request = GraphQLRequest.builder()
                .query(CREATE_PAGE_MUTATION)
                .variables(variables)
                .build();
        
        GraphQLResponse<PageCreateResponse> response = graphQLClient.execute(
                request,
                new TypeReference<GraphQLResponse<PageCreateResponse>>() {}
        );
        
        if (response.hasErrors()) {
            throw new ShopifyApiException("Failed to create page", response.getErrors());
        }
        
        if (response.getData().pageCreate.userErrors != null && 
            !response.getData().pageCreate.userErrors.isEmpty()) {
            throw new ShopifyApiException(
                    "Failed to create page",
                    response.getData().pageCreate.userErrors
            );
        }
        
        return response.getData().pageCreate.page;
    }
    
    public Page updatePage(ShopifyAuthContext context, String id, PageUpdateInput page) {
        ShopifyGraphQLClient.setAuthContext(context);
        
        Map<String, Object> variables = new HashMap<>();
        variables.put("id", id);
        variables.put("page", page);
        
        GraphQLRequest request = GraphQLRequest.builder()
                .query(UPDATE_PAGE_MUTATION)
                .variables(variables)
                .build();
        
        GraphQLResponse<PageUpdateResponse> response = graphQLClient.execute(
                request,
                new TypeReference<GraphQLResponse<PageUpdateResponse>>() {}
        );
        
        if (response.hasErrors()) {
            throw new ShopifyApiException("Failed to update page", response.getErrors());
        }
        
        if (response.getData().pageUpdate.userErrors != null && 
            !response.getData().pageUpdate.userErrors.isEmpty()) {
            throw new ShopifyApiException(
                    "Failed to update page",
                    response.getData().pageUpdate.userErrors
            );
        }
        
        return response.getData().pageUpdate.page;
    }
    
    public String deletePage(ShopifyAuthContext context, String id) {
        ShopifyGraphQLClient.setAuthContext(context);
        
        Map<String, Object> variables = new HashMap<>();
        variables.put("id", id);
        
        GraphQLRequest request = GraphQLRequest.builder()
                .query(DELETE_PAGE_MUTATION)
                .variables(variables)
                .build();
        
        GraphQLResponse<PageDeleteResponse> response = graphQLClient.execute(
                request,
                new TypeReference<GraphQLResponse<PageDeleteResponse>>() {}
        );
        
        if (response.hasErrors()) {
            throw new ShopifyApiException("Failed to delete page", response.getErrors());
        }
        
        if (response.getData().pageDelete.userErrors != null && 
            !response.getData().pageDelete.userErrors.isEmpty()) {
            throw new ShopifyApiException(
                    "Failed to delete page",
                    response.getData().pageDelete.userErrors
            );
        }
        
        return response.getData().pageDelete.deletedPageId;
    }
    
    public Blog createBlog(ShopifyAuthContext context, BlogCreateInput blog) {
        ShopifyGraphQLClient.setAuthContext(context);
        
        Map<String, Object> variables = new HashMap<>();
        variables.put("blog", blog);
        
        GraphQLRequest request = GraphQLRequest.builder()
                .query(CREATE_BLOG_MUTATION)
                .variables(variables)
                .build();
        
        GraphQLResponse<BlogCreateResponse> response = graphQLClient.execute(
                request,
                new TypeReference<GraphQLResponse<BlogCreateResponse>>() {}
        );
        
        if (response.hasErrors()) {
            throw new ShopifyApiException("Failed to create blog", response.getErrors());
        }
        
        if (response.getData().blogCreate.userErrors != null && 
            !response.getData().blogCreate.userErrors.isEmpty()) {
            throw new ShopifyApiException(
                    "Failed to create blog",
                    response.getData().blogCreate.userErrors
            );
        }
        
        return response.getData().blogCreate.blog;
    }
    
    public Blog updateBlog(ShopifyAuthContext context, String id, BlogUpdateInput blog) {
        ShopifyGraphQLClient.setAuthContext(context);
        
        Map<String, Object> variables = new HashMap<>();
        variables.put("id", id);
        variables.put("blog", blog);
        
        GraphQLRequest request = GraphQLRequest.builder()
                .query(UPDATE_BLOG_MUTATION)
                .variables(variables)
                .build();
        
        GraphQLResponse<BlogUpdateResponse> response = graphQLClient.execute(
                request,
                new TypeReference<GraphQLResponse<BlogUpdateResponse>>() {}
        );
        
        if (response.hasErrors()) {
            throw new ShopifyApiException("Failed to update blog", response.getErrors());
        }
        
        if (response.getData().blogUpdate.userErrors != null && 
            !response.getData().blogUpdate.userErrors.isEmpty()) {
            throw new ShopifyApiException(
                    "Failed to update blog",
                    response.getData().blogUpdate.userErrors
            );
        }
        
        return response.getData().blogUpdate.blog;
    }
    
    public String deleteBlog(ShopifyAuthContext context, String id) {
        ShopifyGraphQLClient.setAuthContext(context);
        
        Map<String, Object> variables = new HashMap<>();
        variables.put("id", id);
        
        GraphQLRequest request = GraphQLRequest.builder()
                .query(DELETE_BLOG_MUTATION)
                .variables(variables)
                .build();
        
        GraphQLResponse<BlogDeleteResponse> response = graphQLClient.execute(
                request,
                new TypeReference<GraphQLResponse<BlogDeleteResponse>>() {}
        );
        
        if (response.hasErrors()) {
            throw new ShopifyApiException("Failed to delete blog", response.getErrors());
        }
        
        if (response.getData().blogDelete.userErrors != null && 
            !response.getData().blogDelete.userErrors.isEmpty()) {
            throw new ShopifyApiException(
                    "Failed to delete blog",
                    response.getData().blogDelete.userErrors
            );
        }
        
        return response.getData().blogDelete.deletedBlogId;
    }
    
    public Article createArticle(ShopifyAuthContext context, ArticleCreateInput article) {
        ShopifyGraphQLClient.setAuthContext(context);
        
        Map<String, Object> variables = new HashMap<>();
        variables.put("article", article);
        
        GraphQLRequest request = GraphQLRequest.builder()
                .query(CREATE_ARTICLE_MUTATION)
                .variables(variables)
                .build();
        
        GraphQLResponse<ArticleCreateResponse> response = graphQLClient.execute(
                request,
                new TypeReference<GraphQLResponse<ArticleCreateResponse>>() {}
        );
        
        if (response.hasErrors()) {
            throw new ShopifyApiException("Failed to create article", response.getErrors());
        }
        
        if (response.getData().articleCreate.userErrors != null && 
            !response.getData().articleCreate.userErrors.isEmpty()) {
            throw new ShopifyApiException(
                    "Failed to create article",
                    response.getData().articleCreate.userErrors
            );
        }
        
        return response.getData().articleCreate.article;
    }
    
    public Article updateArticle(ShopifyAuthContext context, String id, ArticleUpdateInput article) {
        ShopifyGraphQLClient.setAuthContext(context);
        
        Map<String, Object> variables = new HashMap<>();
        variables.put("id", id);
        variables.put("article", article);
        
        GraphQLRequest request = GraphQLRequest.builder()
                .query(UPDATE_ARTICLE_MUTATION)
                .variables(variables)
                .build();
        
        GraphQLResponse<ArticleUpdateResponse> response = graphQLClient.execute(
                request,
                new TypeReference<GraphQLResponse<ArticleUpdateResponse>>() {}
        );
        
        if (response.hasErrors()) {
            throw new ShopifyApiException("Failed to update article", response.getErrors());
        }
        
        if (response.getData().articleUpdate.userErrors != null && 
            !response.getData().articleUpdate.userErrors.isEmpty()) {
            throw new ShopifyApiException(
                    "Failed to update article",
                    response.getData().articleUpdate.userErrors
            );
        }
        
        return response.getData().articleUpdate.article;
    }
    
    public String deleteArticle(ShopifyAuthContext context, String id) {
        ShopifyGraphQLClient.setAuthContext(context);
        
        Map<String, Object> variables = new HashMap<>();
        variables.put("id", id);
        
        GraphQLRequest request = GraphQLRequest.builder()
                .query(DELETE_ARTICLE_MUTATION)
                .variables(variables)
                .build();
        
        GraphQLResponse<ArticleDeleteResponse> response = graphQLClient.execute(
                request,
                new TypeReference<GraphQLResponse<ArticleDeleteResponse>>() {}
        );
        
        if (response.hasErrors()) {
            throw new ShopifyApiException("Failed to delete article", response.getErrors());
        }
        
        if (response.getData().articleDelete.userErrors != null && 
            !response.getData().articleDelete.userErrors.isEmpty()) {
            throw new ShopifyApiException(
                    "Failed to delete article",
                    response.getData().articleDelete.userErrors
            );
        }
        
        return response.getData().articleDelete.deletedArticleId;
    }
    
    public ScriptTag createScriptTag(ShopifyAuthContext context, ScriptTagInput input) {
        ShopifyGraphQLClient.setAuthContext(context);
        
        Map<String, Object> variables = new HashMap<>();
        variables.put("input", input);
        
        GraphQLRequest request = GraphQLRequest.builder()
                .query(CREATE_SCRIPT_TAG_MUTATION)
                .variables(variables)
                .build();
        
        GraphQLResponse<ScriptTagCreateResponse> response = graphQLClient.execute(
                request,
                new TypeReference<GraphQLResponse<ScriptTagCreateResponse>>() {}
        );
        
        if (response.hasErrors()) {
            throw new ShopifyApiException("Failed to create script tag", response.getErrors());
        }
        
        if (response.getData().scriptTagCreate.userErrors != null && 
            !response.getData().scriptTagCreate.userErrors.isEmpty()) {
            throw new ShopifyApiException(
                    "Failed to create script tag",
                    response.getData().scriptTagCreate.userErrors
            );
        }
        
        return response.getData().scriptTagCreate.scriptTag;
    }
    
    public ScriptTag updateScriptTag(ShopifyAuthContext context, String id, ScriptTagInput input) {
        ShopifyGraphQLClient.setAuthContext(context);
        
        Map<String, Object> variables = new HashMap<>();
        variables.put("id", id);
        variables.put("input", input);
        
        GraphQLRequest request = GraphQLRequest.builder()
                .query(UPDATE_SCRIPT_TAG_MUTATION)
                .variables(variables)
                .build();
        
        GraphQLResponse<ScriptTagUpdateResponse> response = graphQLClient.execute(
                request,
                new TypeReference<GraphQLResponse<ScriptTagUpdateResponse>>() {}
        );
        
        if (response.hasErrors()) {
            throw new ShopifyApiException("Failed to update script tag", response.getErrors());
        }
        
        if (response.getData().scriptTagUpdate.userErrors != null && 
            !response.getData().scriptTagUpdate.userErrors.isEmpty()) {
            throw new ShopifyApiException(
                    "Failed to update script tag",
                    response.getData().scriptTagUpdate.userErrors
            );
        }
        
        return response.getData().scriptTagUpdate.scriptTag;
    }
    
    public String deleteScriptTag(ShopifyAuthContext context, String id) {
        ShopifyGraphQLClient.setAuthContext(context);
        
        Map<String, Object> variables = new HashMap<>();
        variables.put("id", id);
        
        GraphQLRequest request = GraphQLRequest.builder()
                .query(DELETE_SCRIPT_TAG_MUTATION)
                .variables(variables)
                .build();
        
        GraphQLResponse<ScriptTagDeleteResponse> response = graphQLClient.execute(
                request,
                new TypeReference<GraphQLResponse<ScriptTagDeleteResponse>>() {}
        );
        
        if (response.hasErrors()) {
            throw new ShopifyApiException("Failed to delete script tag", response.getErrors());
        }
        
        if (response.getData().scriptTagDelete.userErrors != null && 
            !response.getData().scriptTagDelete.userErrors.isEmpty()) {
            throw new ShopifyApiException(
                    "Failed to delete script tag",
                    response.getData().scriptTagDelete.userErrors
            );
        }
        
        return response.getData().scriptTagDelete.deletedScriptTagId;
    }
    
    public UrlRedirect createUrlRedirect(ShopifyAuthContext context, UrlRedirectInput urlRedirect) {
        ShopifyGraphQLClient.setAuthContext(context);
        
        Map<String, Object> variables = new HashMap<>();
        variables.put("urlRedirect", urlRedirect);
        
        GraphQLRequest request = GraphQLRequest.builder()
                .query(CREATE_URL_REDIRECT_MUTATION)
                .variables(variables)
                .build();
        
        GraphQLResponse<UrlRedirectCreateResponse> response = graphQLClient.execute(
                request,
                new TypeReference<GraphQLResponse<UrlRedirectCreateResponse>>() {}
        );
        
        if (response.hasErrors()) {
            throw new ShopifyApiException("Failed to create URL redirect", response.getErrors());
        }
        
        if (response.getData().urlRedirectCreate.userErrors != null && 
            !response.getData().urlRedirectCreate.userErrors.isEmpty()) {
            throw new ShopifyApiException(
                    "Failed to create URL redirect",
                    response.getData().urlRedirectCreate.userErrors
            );
        }
        
        return response.getData().urlRedirectCreate.urlRedirect;
    }
    
    public UrlRedirect updateUrlRedirect(ShopifyAuthContext context, String id, UrlRedirectInput urlRedirect) {
        ShopifyGraphQLClient.setAuthContext(context);
        
        Map<String, Object> variables = new HashMap<>();
        variables.put("id", id);
        variables.put("urlRedirect", urlRedirect);
        
        GraphQLRequest request = GraphQLRequest.builder()
                .query(UPDATE_URL_REDIRECT_MUTATION)
                .variables(variables)
                .build();
        
        GraphQLResponse<UrlRedirectUpdateResponse> response = graphQLClient.execute(
                request,
                new TypeReference<GraphQLResponse<UrlRedirectUpdateResponse>>() {}
        );
        
        if (response.hasErrors()) {
            throw new ShopifyApiException("Failed to update URL redirect", response.getErrors());
        }
        
        if (response.getData().urlRedirectUpdate.userErrors != null && 
            !response.getData().urlRedirectUpdate.userErrors.isEmpty()) {
            throw new ShopifyApiException(
                    "Failed to update URL redirect",
                    response.getData().urlRedirectUpdate.userErrors
            );
        }
        
        return response.getData().urlRedirectUpdate.urlRedirect;
    }
    
    public String deleteUrlRedirect(ShopifyAuthContext context, String id) {
        ShopifyGraphQLClient.setAuthContext(context);
        
        Map<String, Object> variables = new HashMap<>();
        variables.put("id", id);
        
        GraphQLRequest request = GraphQLRequest.builder()
                .query(DELETE_URL_REDIRECT_MUTATION)
                .variables(variables)
                .build();
        
        GraphQLResponse<UrlRedirectDeleteResponse> response = graphQLClient.execute(
                request,
                new TypeReference<GraphQLResponse<UrlRedirectDeleteResponse>>() {}
        );
        
        if (response.hasErrors()) {
            throw new ShopifyApiException("Failed to delete URL redirect", response.getErrors());
        }
        
        if (response.getData().urlRedirectDelete.userErrors != null && 
            !response.getData().urlRedirectDelete.userErrors.isEmpty()) {
            throw new ShopifyApiException(
                    "Failed to delete URL redirect",
                    response.getData().urlRedirectDelete.userErrors
            );
        }
        
        return response.getData().urlRedirectDelete.deletedUrlRedirectId;
    }
    
    // Response classes
    @Data
    private static class PagesResponse {
        private PageConnection pages;
    }
    
    @Data
    private static class PageResponse {
        private Page page;
    }
    
    @Data
    private static class BlogsResponse {
        private BlogConnection blogs;
    }
    
    @Data
    private static class ArticlesResponse {
        private ArticleConnection articles;
    }
    
    @Data
    private static class ThemesResponse {
        private OnlineStoreThemeConnection themes;
    }
    
    @Data
    private static class ScriptTagsResponse {
        private ScriptTagConnection scriptTags;
    }
    
    @Data
    private static class MenusResponse {
        private MenuConnection menus;
    }
    
    @Data
    private static class UrlRedirectsResponse {
        private UrlRedirectConnection urlRedirects;
    }
    
    @Data
    private static class PageCreateResponse {
        private PageCreateResult pageCreate;
    }
    
    @Data
    private static class PageCreateResult {
        private Page page;
        private List<UserError> userErrors;
    }
    
    @Data
    private static class PageUpdateResponse {
        private PageUpdateResult pageUpdate;
    }
    
    @Data
    private static class PageUpdateResult {
        private Page page;
        private List<UserError> userErrors;
    }
    
    @Data
    private static class PageDeleteResponse {
        private PageDeleteResult pageDelete;
    }
    
    @Data
    private static class PageDeleteResult {
        private String deletedPageId;
        private List<UserError> userErrors;
    }
    
    @Data
    private static class BlogCreateResponse {
        private BlogCreateResult blogCreate;
    }
    
    @Data
    private static class BlogCreateResult {
        private Blog blog;
        private List<UserError> userErrors;
    }
    
    @Data
    private static class BlogUpdateResponse {
        private BlogUpdateResult blogUpdate;
    }
    
    @Data
    private static class BlogUpdateResult {
        private Blog blog;
        private List<UserError> userErrors;
    }
    
    @Data
    private static class BlogDeleteResponse {
        private BlogDeleteResult blogDelete;
    }
    
    @Data
    private static class BlogDeleteResult {
        private String deletedBlogId;
        private List<UserError> userErrors;
    }
    
    @Data
    private static class ArticleCreateResponse {
        private ArticleCreateResult articleCreate;
    }
    
    @Data
    private static class ArticleCreateResult {
        private Article article;
        private List<UserError> userErrors;
    }
    
    @Data
    private static class ArticleUpdateResponse {
        private ArticleUpdateResult articleUpdate;
    }
    
    @Data
    private static class ArticleUpdateResult {
        private Article article;
        private List<UserError> userErrors;
    }
    
    @Data
    private static class ArticleDeleteResponse {
        private ArticleDeleteResult articleDelete;
    }
    
    @Data
    private static class ArticleDeleteResult {
        private String deletedArticleId;
        private List<UserError> userErrors;
    }
    
    @Data
    private static class ScriptTagCreateResponse {
        private ScriptTagCreateResult scriptTagCreate;
    }
    
    @Data
    private static class ScriptTagCreateResult {
        private ScriptTag scriptTag;
        private List<UserError> userErrors;
    }
    
    @Data
    private static class ScriptTagUpdateResponse {
        private ScriptTagUpdateResult scriptTagUpdate;
    }
    
    @Data
    private static class ScriptTagUpdateResult {
        private ScriptTag scriptTag;
        private List<UserError> userErrors;
    }
    
    @Data
    private static class ScriptTagDeleteResponse {
        private ScriptTagDeleteResult scriptTagDelete;
    }
    
    @Data
    private static class ScriptTagDeleteResult {
        private String deletedScriptTagId;
        private List<UserError> userErrors;
    }
    
    @Data
    private static class UrlRedirectCreateResponse {
        private UrlRedirectCreateResult urlRedirectCreate;
    }
    
    @Data
    private static class UrlRedirectCreateResult {
        private UrlRedirect urlRedirect;
        private List<UserError> userErrors;
    }
    
    @Data
    private static class UrlRedirectUpdateResponse {
        private UrlRedirectUpdateResult urlRedirectUpdate;
    }
    
    @Data
    private static class UrlRedirectUpdateResult {
        private UrlRedirect urlRedirect;
        private List<UserError> userErrors;
    }
    
    @Data
    private static class UrlRedirectDeleteResponse {
        private UrlRedirectDeleteResult urlRedirectDelete;
    }
    
    @Data
    private static class UrlRedirectDeleteResult {
        private String deletedUrlRedirectId;
        private List<UserError> userErrors;
    }
    
    // Enums
    public enum PageSortKeys {
        ID,
        TITLE,
        UPDATED_AT,
        RELEVANCE
    }
    
    public enum BlogSortKeys {
        HANDLE,
        ID,
        TITLE,
        UPDATED_AT,
        RELEVANCE
    }
    
    public enum ArticleSortKeys {
        AUTHOR,
        BLOG_TITLE,
        ID,
        PUBLISHED_AT,
        TITLE,
        UPDATED_AT,
        RELEVANCE
    }
}