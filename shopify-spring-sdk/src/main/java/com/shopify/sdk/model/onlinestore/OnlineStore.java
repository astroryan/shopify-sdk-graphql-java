package com.shopify.sdk.model.onlinestore;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.shopify.sdk.model.common.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Page {
    
    @JsonProperty("id")
    private ID id;
    
    @JsonProperty("author")
    private String author;
    
    @JsonProperty("body")
    private String body;
    
    @JsonProperty("bodySummary")
    private String bodySummary;
    
    @JsonProperty("createdAt")
    private DateTime createdAt;
    
    @JsonProperty("handle")
    private String handle;
    
    @JsonProperty("isPublished")
    private Boolean isPublished;
    
    @JsonProperty("metafield")
    private Metafield metafield;
    
    @JsonProperty("metafields")
    private MetafieldConnection metafields;
    
    @JsonProperty("onlineStorePreviewUrl")
    private String onlineStorePreviewUrl;
    
    @JsonProperty("onlineStoreUrl")
    private String onlineStoreUrl;
    
    @JsonProperty("publishedAt")
    private DateTime publishedAt;
    
    @JsonProperty("seo")
    private SEO seo;
    
    @JsonProperty("templateSuffix")
    private String templateSuffix;
    
    @JsonProperty("title")
    private String title;
    
    @JsonProperty("translations")
    private List<Translation> translations;
    
    @JsonProperty("updatedAt")
    private DateTime updatedAt;
}

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SEO {
    
    @JsonProperty("description")
    private String description;
    
    @JsonProperty("title")
    private String title;
}

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Blog {
    
    @JsonProperty("id")
    private ID id;
    
    @JsonProperty("articles")
    private ArticleConnection articles;
    
    @JsonProperty("authors")
    private List<ArticleAuthor> authors;
    
    @JsonProperty("commentPolicy")
    private CommentPolicy commentPolicy;
    
    @JsonProperty("createdAt")
    private DateTime createdAt;
    
    @JsonProperty("feedburner")
    private BlogFeedburner feedburner;
    
    @JsonProperty("feedburnerLocation")
    private String feedburnerLocation;
    
    @JsonProperty("handle")
    private String handle;
    
    @JsonProperty("isModerated")
    private Boolean isModerated;
    
    @JsonProperty("metafield")
    private Metafield metafield;
    
    @JsonProperty("metafields")
    private MetafieldConnection metafields;
    
    @JsonProperty("onlineStorePreviewUrl")
    private String onlineStorePreviewUrl;
    
    @JsonProperty("onlineStoreUrl")
    private String onlineStoreUrl;
    
    @JsonProperty("seo")
    private SEO seo;
    
    @JsonProperty("tags")
    private List<String> tags;
    
    @JsonProperty("templateSuffix")
    private String templateSuffix;
    
    @JsonProperty("title")
    private String title;
    
    @JsonProperty("updatedAt")
    private DateTime updatedAt;
}

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BlogFeedburner {
    
    @JsonProperty("feedburnerUrl")
    private String feedburnerUrl;
}

public enum CommentPolicy {
    NONE,
    MODERATE,
    OPEN,
    CLOSED
}

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Article {
    
    @JsonProperty("id")
    private ID id;
    
    @JsonProperty("author")
    private ArticleAuthor author;
    
    @JsonProperty("authorV2")
    private ArticleAuthor authorV2;
    
    @JsonProperty("blog")
    private Blog blog;
    
    @JsonProperty("comments")
    private CommentConnection comments;
    
    @JsonProperty("content")
    private String content;
    
    @JsonProperty("contentHtml")
    private String contentHtml;
    
    @JsonProperty("createdAt")
    private DateTime createdAt;
    
    @JsonProperty("excerpt")
    private String excerpt;
    
    @JsonProperty("handle")
    private String handle;
    
    @JsonProperty("image")
    private Image image;
    
    @JsonProperty("isPublished")
    private Boolean isPublished;
    
    @JsonProperty("metafield")
    private Metafield metafield;
    
    @JsonProperty("metafields")
    private MetafieldConnection metafields;
    
    @JsonProperty("onlineStorePreviewUrl")
    private String onlineStorePreviewUrl;
    
    @JsonProperty("onlineStoreUrl")
    private String onlineStoreUrl;
    
    @JsonProperty("publishedAt")
    private DateTime publishedAt;
    
    @JsonProperty("seo")
    private SEO seo;
    
    @JsonProperty("summary")
    private String summary;
    
    @JsonProperty("tags")
    private List<String> tags;
    
    @JsonProperty("templateSuffix")
    private String templateSuffix;
    
    @JsonProperty("title")
    private String title;
    
    @JsonProperty("updatedAt")
    private DateTime updatedAt;
}

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ArticleAuthor {
    
    @JsonProperty("bio")
    private String bio;
    
    @JsonProperty("displayName")
    private String displayName;
    
    @JsonProperty("email")
    private String email;
    
    @JsonProperty("firstName")
    private String firstName;
    
    @JsonProperty("image")
    private Image image;
    
    @JsonProperty("lastName")
    private String lastName;
}

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Comment {
    
    @JsonProperty("id")
    private ID id;
    
    @JsonProperty("article")
    private Article article;
    
    @JsonProperty("author")
    private CommentAuthor author;
    
    @JsonProperty("content")
    private String content;
    
    @JsonProperty("contentHtml")
    private String contentHtml;
    
    @JsonProperty("createdAt")
    private DateTime createdAt;
    
    @JsonProperty("status")
    private CommentStatus status;
    
    @JsonProperty("updatedAt")
    private DateTime updatedAt;
}

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CommentAuthor {
    
    @JsonProperty("displayName")
    private String displayName;
    
    @JsonProperty("email")
    private String email;
}

public enum CommentStatus {
    PENDING,
    PUBLISHED,
    REMOVED,
    SPAM
}

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OnlineStoreTheme {
    
    @JsonProperty("id")
    private ID id;
    
    @JsonProperty("createdAt")
    private DateTime createdAt;
    
    @JsonProperty("files")
    private OnlineStoreThemeFileConnection files;
    
    @JsonProperty("name")
    private String name;
    
    @JsonProperty("previewable")
    private Boolean previewable;
    
    @JsonProperty("processingErrors")
    private List<String> processingErrors;
    
    @JsonProperty("role")
    private ThemeRole role;
    
    @JsonProperty("themeStore")
    private Boolean themeStore;
    
    @JsonProperty("updatedAt")
    private DateTime updatedAt;
}

public enum ThemeRole {
    MAIN,
    UNPUBLISHED,
    DEMO
}

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OnlineStoreThemeFile {
    
    @JsonProperty("body")
    private OnlineStoreThemeFileBody body;
    
    @JsonProperty("checksumMd5")
    private String checksumMd5;
    
    @JsonProperty("contentType")
    private ThemeFileContentType contentType;
    
    @JsonProperty("createdAt")
    private DateTime createdAt;
    
    @JsonProperty("filename")
    private String filename;
    
    @JsonProperty("size")
    private Long size;
    
    @JsonProperty("updatedAt")
    private DateTime updatedAt;
}

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OnlineStoreThemeFileBody {
    
    @JsonProperty("type")
    private ThemeFileBodyType type;
    
    @JsonProperty("url")
    private String url;
}

public enum ThemeFileBodyType {
    IMAGE,
    JSON,
    LIQUID,
    TEXT
}

public enum ThemeFileContentType {
    IMAGE,
    JSON,
    LIQUID,
    TEXT
}

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ScriptTag {
    
    @JsonProperty("id")
    private ID id;
    
    @JsonProperty("cache")
    private Boolean cache;
    
    @JsonProperty("createdAt")
    private DateTime createdAt;
    
    @JsonProperty("displayScope")
    private ScriptTagDisplayScope displayScope;
    
    @JsonProperty("src")
    private String src;
    
    @JsonProperty("updatedAt")
    private DateTime updatedAt;
}

public enum ScriptTagDisplayScope {
    ALL,
    ONLINE_STORE,
    ORDER_STATUS
}

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Menu {
    
    @JsonProperty("id")
    private ID id;
    
    @JsonProperty("handle")
    private String handle;
    
    @JsonProperty("isDefault")
    private Boolean isDefault;
    
    @JsonProperty("items")
    private List<MenuItem> items;
    
    @JsonProperty("itemsCount")
    private Integer itemsCount;
    
    @JsonProperty("title")
    private String title;
}

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MenuItem {
    
    @JsonProperty("id")
    private ID id;
    
    @JsonProperty("items")
    private List<MenuItem> items;
    
    @JsonProperty("resource")
    private MenuItemResource resource;
    
    @JsonProperty("resourceId")
    private ID resourceId;
    
    @JsonProperty("tags")
    private List<String> tags;
    
    @JsonProperty("title")
    private String title;
    
    @JsonProperty("type")
    private MenuItemType type;
    
    @JsonProperty("url")
    private String url;
}

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MenuItemResource {
    
    @JsonProperty("__typename")
    private String typename;
}

public enum MenuItemType {
    BLOG,
    CATALOG,
    COLLECTION,
    COLLECTIONS,
    FRONTPAGE,
    HTTP,
    LINK,
    PAGE,
    PRODUCT,
    SEARCH,
    SHOP_POLICY
}

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UrlRedirect {
    
    @JsonProperty("id")
    private ID id;
    
    @JsonProperty("path")
    private String path;
    
    @JsonProperty("target")
    private String target;
}

// Connection types
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PageConnection {
    
    @JsonProperty("edges")
    private List<PageEdge> edges;
    
    @JsonProperty("pageInfo")
    private PageInfo pageInfo;
}

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PageEdge {
    
    @JsonProperty("node")
    private Page node;
    
    @JsonProperty("cursor")
    private String cursor;
}

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BlogConnection {
    
    @JsonProperty("edges")
    private List<BlogEdge> edges;
    
    @JsonProperty("pageInfo")
    private PageInfo pageInfo;
}

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BlogEdge {
    
    @JsonProperty("node")
    private Blog node;
    
    @JsonProperty("cursor")
    private String cursor;
}

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ArticleConnection {
    
    @JsonProperty("edges")
    private List<ArticleEdge> edges;
    
    @JsonProperty("pageInfo")
    private PageInfo pageInfo;
}

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ArticleEdge {
    
    @JsonProperty("node")
    private Article node;
    
    @JsonProperty("cursor")
    private String cursor;
}

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CommentConnection {
    
    @JsonProperty("edges")
    private List<CommentEdge> edges;
    
    @JsonProperty("pageInfo")
    private PageInfo pageInfo;
}

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CommentEdge {
    
    @JsonProperty("node")
    private Comment node;
    
    @JsonProperty("cursor")
    private String cursor;
}

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OnlineStoreThemeConnection {
    
    @JsonProperty("edges")
    private List<OnlineStoreThemeEdge> edges;
    
    @JsonProperty("pageInfo")
    private PageInfo pageInfo;
}

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OnlineStoreThemeEdge {
    
    @JsonProperty("node")
    private OnlineStoreTheme node;
    
    @JsonProperty("cursor")
    private String cursor;
}

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OnlineStoreThemeFileConnection {
    
    @JsonProperty("edges")
    private List<OnlineStoreThemeFileEdge> edges;
    
    @JsonProperty("pageInfo")
    private PageInfo pageInfo;
}

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OnlineStoreThemeFileEdge {
    
    @JsonProperty("node")
    private OnlineStoreThemeFile node;
    
    @JsonProperty("cursor")
    private String cursor;
}

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ScriptTagConnection {
    
    @JsonProperty("edges")
    private List<ScriptTagEdge> edges;
    
    @JsonProperty("pageInfo")
    private PageInfo pageInfo;
}

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ScriptTagEdge {
    
    @JsonProperty("node")
    private ScriptTag node;
    
    @JsonProperty("cursor")
    private String cursor;
}

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MenuConnection {
    
    @JsonProperty("edges")
    private List<MenuEdge> edges;
    
    @JsonProperty("pageInfo")
    private PageInfo pageInfo;
}

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MenuEdge {
    
    @JsonProperty("node")
    private Menu node;
    
    @JsonProperty("cursor")
    private String cursor;
}

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UrlRedirectConnection {
    
    @JsonProperty("edges")
    private List<UrlRedirectEdge> edges;
    
    @JsonProperty("pageInfo")
    private PageInfo pageInfo;
}

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UrlRedirectEdge {
    
    @JsonProperty("node")
    private UrlRedirect node;
    
    @JsonProperty("cursor")
    private String cursor;
}

// Input classes
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PageCreateInput {
    
    @JsonProperty("author")
    private String author;
    
    @JsonProperty("body")
    private String body;
    
    @JsonProperty("handle")
    private String handle;
    
    @JsonProperty("isPublished")
    private Boolean isPublished;
    
    @JsonProperty("metafields")
    private List<MetafieldInput> metafields;
    
    @JsonProperty("publishedAt")
    private DateTime publishedAt;
    
    @JsonProperty("templateSuffix")
    private String templateSuffix;
    
    @JsonProperty("title")
    private String title;
}

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PageUpdateInput {
    
    @JsonProperty("author")
    private String author;
    
    @JsonProperty("body")
    private String body;
    
    @JsonProperty("handle")
    private String handle;
    
    @JsonProperty("isPublished")
    private Boolean isPublished;
    
    @JsonProperty("metafields")
    private List<MetafieldInput> metafields;
    
    @JsonProperty("publishedAt")
    private DateTime publishedAt;
    
    @JsonProperty("templateSuffix")
    private String templateSuffix;
    
    @JsonProperty("title")
    private String title;
}

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BlogCreateInput {
    
    @JsonProperty("commentPolicy")
    private CommentPolicy commentPolicy;
    
    @JsonProperty("handle")
    private String handle;
    
    @JsonProperty("metafields")
    private List<MetafieldInput> metafields;
    
    @JsonProperty("templateSuffix")
    private String templateSuffix;
    
    @JsonProperty("title")
    private String title;
}

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BlogUpdateInput {
    
    @JsonProperty("commentPolicy")
    private CommentPolicy commentPolicy;
    
    @JsonProperty("handle")
    private String handle;
    
    @JsonProperty("metafields")
    private List<MetafieldInput> metafields;
    
    @JsonProperty("templateSuffix")
    private String templateSuffix;
    
    @JsonProperty("title")
    private String title;
}

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ArticleCreateInput {
    
    @JsonProperty("authorV2")
    private ArticleAuthorInput authorV2;
    
    @JsonProperty("blogId")
    private ID blogId;
    
    @JsonProperty("content")
    private String content;
    
    @JsonProperty("contentHtml")
    private String contentHtml;
    
    @JsonProperty("excerpt")
    private String excerpt;
    
    @JsonProperty("handle")
    private String handle;
    
    @JsonProperty("image")
    private ImageInput image;
    
    @JsonProperty("isPublished")
    private Boolean isPublished;
    
    @JsonProperty("metafields")
    private List<MetafieldInput> metafields;
    
    @JsonProperty("publishedAt")
    private DateTime publishedAt;
    
    @JsonProperty("summary")
    private String summary;
    
    @JsonProperty("tags")
    private List<String> tags;
    
    @JsonProperty("templateSuffix")
    private String templateSuffix;
    
    @JsonProperty("title")
    private String title;
}

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ArticleUpdateInput {
    
    @JsonProperty("authorV2")
    private ArticleAuthorInput authorV2;
    
    @JsonProperty("content")
    private String content;
    
    @JsonProperty("contentHtml")
    private String contentHtml;
    
    @JsonProperty("excerpt")
    private String excerpt;
    
    @JsonProperty("handle")
    private String handle;
    
    @JsonProperty("image")
    private ImageInput image;
    
    @JsonProperty("isPublished")
    private Boolean isPublished;
    
    @JsonProperty("metafields")
    private List<MetafieldInput> metafields;
    
    @JsonProperty("publishedAt")
    private DateTime publishedAt;
    
    @JsonProperty("summary")
    private String summary;
    
    @JsonProperty("tags")
    private List<String> tags;
    
    @JsonProperty("templateSuffix")
    private String templateSuffix;
    
    @JsonProperty("title")
    private String title;
}

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ArticleAuthorInput {
    
    @JsonProperty("displayName")
    private String displayName;
}

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ImageInput {
    
    @JsonProperty("altText")
    private String altText;
    
    @JsonProperty("src")
    private String src;
}

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ScriptTagInput {
    
    @JsonProperty("cache")
    private Boolean cache;
    
    @JsonProperty("displayScope")
    private ScriptTagDisplayScope displayScope;
    
    @JsonProperty("src")
    private String src;
}

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UrlRedirectInput {
    
    @JsonProperty("path")
    private String path;
    
    @JsonProperty("target")
    private String target;
}

// Common model references
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Image {
    
    @JsonProperty("id")
    private ID id;
    
    @JsonProperty("altText")
    private String altText;
    
    @JsonProperty("height")
    private Integer height;
    
    @JsonProperty("metafield")
    private Metafield metafield;
    
    @JsonProperty("metafields")
    private MetafieldConnection metafields;
    
    @JsonProperty("url")
    private String url;
    
    @JsonProperty("width")
    private Integer width;
}

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Translation {
    
    @JsonProperty("key")
    private String key;
    
    @JsonProperty("locale")
    private String locale;
    
    @JsonProperty("outdated")
    private Boolean outdated;
    
    @JsonProperty("updatedAt")
    private DateTime updatedAt;
    
    @JsonProperty("value")
    private String value;
}

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Metafield {
    
    @JsonProperty("id")
    private ID id;
    
    @JsonProperty("namespace")
    private String namespace;
    
    @JsonProperty("key")
    private String key;
    
    @JsonProperty("value")
    private String value;
}

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MetafieldConnection {
    
    @JsonProperty("edges")
    private List<MetafieldEdge> edges;
    
    @JsonProperty("pageInfo")
    private PageInfo pageInfo;
}

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MetafieldEdge {
    
    @JsonProperty("node")
    private Metafield node;
    
    @JsonProperty("cursor")
    private String cursor;
}

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MetafieldInput {
    
    @JsonProperty("namespace")
    private String namespace;
    
    @JsonProperty("key")
    private String key;
    
    @JsonProperty("value")
    private String value;
    
    @JsonProperty("type")
    private String type;
}