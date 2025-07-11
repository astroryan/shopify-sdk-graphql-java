package com.shopify.sdk.model.checkout;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.shopify.sdk.model.common.ID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CheckoutBrandingSettings {
    
    @JsonProperty("customizations")
    private CheckoutBrandingCustomizations customizations;
    
    @JsonProperty("designSystem")
    private CheckoutBrandingDesignSystem designSystem;
}

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CheckoutBrandingCustomizations {
    
    @JsonProperty("buyerJourney")
    private CheckoutBrandingBuyerJourney buyerJourney;
    
    @JsonProperty("cartLink")
    private CheckoutBrandingCartLink cartLink;
    
    @JsonProperty("checkbox")
    private CheckoutBrandingCheckbox checkbox;
    
    @JsonProperty("choiceList")
    private CheckoutBrandingChoiceList choiceList;
    
    @JsonProperty("control")
    private CheckoutBrandingControl control;
    
    @JsonProperty("favicon")
    private CheckoutBrandingImage favicon;
    
    @JsonProperty("global")
    private CheckoutBrandingGlobal global;
    
    @JsonProperty("header")
    private CheckoutBrandingHeader header;
    
    @JsonProperty("headingLevel1")
    private CheckoutBrandingHeadingLevel headingLevel1;
    
    @JsonProperty("headingLevel2")
    private CheckoutBrandingHeadingLevel headingLevel2;
    
    @JsonProperty("main")
    private CheckoutBrandingMain main;
    
    @JsonProperty("merchandiseThumbnail")
    private CheckoutBrandingMerchandiseThumbnail merchandiseThumbnail;
    
    @JsonProperty("orderSummary")
    private CheckoutBrandingOrderSummary orderSummary;
    
    @JsonProperty("primaryButton")
    private CheckoutBrandingButton primaryButton;
    
    @JsonProperty("secondaryButton")
    private CheckoutBrandingButton secondaryButton;
    
    @JsonProperty("select")
    private CheckoutBrandingSelect select;
    
    @JsonProperty("textField")
    private CheckoutBrandingTextField textField;
}

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CheckoutBrandingDesignSystem {
    
    @JsonProperty("colors")
    private CheckoutBrandingColors colors;
    
    @JsonProperty("cornerRadius")
    private CheckoutBrandingCornerRadius cornerRadius;
    
    @JsonProperty("typography")
    private CheckoutBrandingTypography typography;
}

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CheckoutBrandingColors {
    
    @JsonProperty("global")
    private CheckoutBrandingColorGroup global;
    
    @JsonProperty("schemes")
    private CheckoutBrandingColorSchemes schemes;
}

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CheckoutBrandingColorGroup {
    
    @JsonProperty("accent")
    private String accent;
    
    @JsonProperty("brand")
    private String brand;
    
    @JsonProperty("critical")
    private String critical;
    
    @JsonProperty("decorative")
    private String decorative;
    
    @JsonProperty("info")
    private String info;
    
    @JsonProperty("success")
    private String success;
    
    @JsonProperty("warning")
    private String warning;
}

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CheckoutBrandingColorSchemes {
    
    @JsonProperty("scheme1")
    private CheckoutBrandingColorScheme scheme1;
    
    @JsonProperty("scheme2")
    private CheckoutBrandingColorScheme scheme2;
}

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CheckoutBrandingColorScheme {
    
    @JsonProperty("base")
    private CheckoutBrandingColorRoles base;
    
    @JsonProperty("control")
    private CheckoutBrandingColorRoles control;
    
    @JsonProperty("primaryButton")
    private CheckoutBrandingColorRoles primaryButton;
    
    @JsonProperty("secondaryButton")
    private CheckoutBrandingColorRoles secondaryButton;
}

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CheckoutBrandingColorRoles {
    
    @JsonProperty("accent")
    private String accent;
    
    @JsonProperty("background")
    private String background;
    
    @JsonProperty("border")
    private String border;
    
    @JsonProperty("decorative")
    private String decorative;
    
    @JsonProperty("hover")
    private String hover;
    
    @JsonProperty("icon")
    private String icon;
    
    @JsonProperty("selected")
    private String selected;
    
    @JsonProperty("text")
    private String text;
}

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CheckoutBrandingCornerRadius {
    
    @JsonProperty("base")
    private CheckoutBrandingCornerRadiusVariables base;
    
    @JsonProperty("large")
    private CheckoutBrandingCornerRadiusVariables large;
    
    @JsonProperty("small")
    private CheckoutBrandingCornerRadiusVariables small;
}

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CheckoutBrandingCornerRadiusVariables {
    
    @JsonProperty("borderRadius")
    private Integer borderRadius;
}

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CheckoutBrandingTypography {
    
    @JsonProperty("primary")
    private CheckoutBrandingFontGroup primary;
    
    @JsonProperty("secondary")
    private CheckoutBrandingFontGroup secondary;
    
    @JsonProperty("size")
    private CheckoutBrandingFontSize size;
}

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CheckoutBrandingFontGroup {
    
    @JsonProperty("base")
    private CheckoutBrandingFont base;
    
    @JsonProperty("bold")
    private CheckoutBrandingFont bold;
    
    @JsonProperty("loadingStrategy")
    private CheckoutBrandingFontLoadingStrategy loadingStrategy;
    
    @JsonProperty("name")
    private String name;
}

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CheckoutBrandingFont {
    
    @JsonProperty("sources")
    private String sources;
    
    @JsonProperty("weight")
    private Integer weight;
}

public enum CheckoutBrandingFontLoadingStrategy {
    AUTO,
    BLOCK,
    SWAP,
    FALLBACK,
    OPTIONAL
}

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CheckoutBrandingFontSize {
    
    @JsonProperty("base")
    private Integer base;
    
    @JsonProperty("ratio")
    private Double ratio;
}

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CheckoutBrandingBuyerJourney {
    
    @JsonProperty("visibility")
    private CheckoutBrandingVisibility visibility;
}

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CheckoutBrandingCartLink {
    
    @JsonProperty("visibility")
    private CheckoutBrandingVisibility visibility;
}

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CheckoutBrandingCheckbox {
    
    @JsonProperty("cornerRadius")
    private CheckoutBrandingCornerRadiusVariables cornerRadius;
}

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CheckoutBrandingChoiceList {
    
    @JsonProperty("group")
    private CheckoutBrandingChoiceListGroup group;
}

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CheckoutBrandingChoiceListGroup {
    
    @JsonProperty("spacing")
    private CheckoutBrandingSpacingKeyword spacing;
}

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CheckoutBrandingControl {
    
    @JsonProperty("border")
    private CheckoutBrandingBorder border;
    
    @JsonProperty("color")
    private CheckoutBrandingColorSelection color;
    
    @JsonProperty("cornerRadius")
    private CheckoutBrandingCornerRadiusVariables cornerRadius;
    
    @JsonProperty("labelPosition")
    private CheckoutBrandingLabelPosition labelPosition;
}

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CheckoutBrandingImage {
    
    @JsonProperty("mediaContentType")
    private String mediaContentType;
    
    @JsonProperty("originalSrc")
    private String originalSrc;
}

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CheckoutBrandingGlobal {
    
    @JsonProperty("cornerRadius")
    private CheckoutBrandingCornerRadiusVariables cornerRadius;
    
    @JsonProperty("typography")
    private CheckoutBrandingGlobalTypography typography;
}

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CheckoutBrandingGlobalTypography {
    
    @JsonProperty("kerning")
    private CheckoutBrandingKerning kerning;
    
    @JsonProperty("letterCase")
    private CheckoutBrandingLetterCase letterCase;
}

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CheckoutBrandingHeader {
    
    @JsonProperty("alignment")
    private CheckoutBrandingHorizontalAlignment alignment;
    
    @JsonProperty("banner")
    private CheckoutBrandingImage banner;
    
    @JsonProperty("logo")
    private CheckoutBrandingLogo logo;
    
    @JsonProperty("position")
    private CheckoutBrandingLogoPosition position;
}

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CheckoutBrandingLogo {
    
    @JsonProperty("image")
    private CheckoutBrandingImage image;
    
    @JsonProperty("maxWidth")
    private Integer maxWidth;
}

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CheckoutBrandingHeadingLevel {
    
    @JsonProperty("typography")
    private CheckoutBrandingHeadingLevelTypography typography;
}

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CheckoutBrandingHeadingLevelTypography {
    
    @JsonProperty("font")
    private CheckoutBrandingFontSelection font;
    
    @JsonProperty("kerning")
    private CheckoutBrandingKerning kerning;
    
    @JsonProperty("letterCase")
    private CheckoutBrandingLetterCase letterCase;
    
    @JsonProperty("size")
    private CheckoutBrandingFontSizeSelection size;
    
    @JsonProperty("weight")
    private CheckoutBrandingFontWeight weight;
}

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CheckoutBrandingMain {
    
    @JsonProperty("backgroundImage")
    private CheckoutBrandingBackgroundImage backgroundImage;
    
    @JsonProperty("colorScheme")
    private CheckoutBrandingColorSchemeSelection colorScheme;
}

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CheckoutBrandingBackgroundImage {
    
    @JsonProperty("image")
    private CheckoutBrandingImage image;
}

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CheckoutBrandingMerchandiseThumbnail {
    
    @JsonProperty("border")
    private CheckoutBrandingSimpleBorder border;
    
    @JsonProperty("cornerRadius")
    private CheckoutBrandingCornerRadiusVariables cornerRadius;
}

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CheckoutBrandingOrderSummary {
    
    @JsonProperty("backgroundImage")
    private CheckoutBrandingBackgroundImage backgroundImage;
    
    @JsonProperty("colorScheme")
    private CheckoutBrandingColorSchemeSelection colorScheme;
}

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CheckoutBrandingButton {
    
    @JsonProperty("background")
    private CheckoutBrandingButtonBackground background;
    
    @JsonProperty("blockPadding")
    private CheckoutBrandingSpacingKeyword blockPadding;
    
    @JsonProperty("border")
    private CheckoutBrandingBorder border;
    
    @JsonProperty("cornerRadius")
    private CheckoutBrandingCornerRadiusVariables cornerRadius;
    
    @JsonProperty("inlinePadding")
    private CheckoutBrandingSpacingKeyword inlinePadding;
    
    @JsonProperty("typography")
    private CheckoutBrandingButtonTypography typography;
}

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CheckoutBrandingSelect {
    
    @JsonProperty("border")
    private CheckoutBrandingBorder border;
    
    @JsonProperty("typography")
    private CheckoutBrandingSelectTypography typography;
}

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CheckoutBrandingTextField {
    
    @JsonProperty("border")
    private CheckoutBrandingBorder border;
    
    @JsonProperty("typography")
    private CheckoutBrandingTextFieldTypography typography;
}

// Enums
public enum CheckoutBrandingVisibility {
    HIDDEN,
    VISIBLE
}

public enum CheckoutBrandingSpacingKeyword {
    NONE,
    EXTRA_TIGHT,
    TIGHT,
    BASE,
    LOOSE,
    EXTRA_LOOSE
}

public enum CheckoutBrandingLabelPosition {
    INSIDE,
    OUTSIDE
}

public enum CheckoutBrandingKerning {
    BASE,
    EXTRA_TIGHT,
    LOOSE
}

public enum CheckoutBrandingLetterCase {
    LOWER,
    NONE,
    TITLE,
    UPPER
}

public enum CheckoutBrandingHorizontalAlignment {
    START,
    CENTER,
    END
}

public enum CheckoutBrandingLogoPosition {
    START,
    CENTER,
    END,
    INLINE_START,
    INLINE_CENTER,
    INLINE_END
}

public enum CheckoutBrandingFontWeight {
    BASE,
    BOLD
}

public enum CheckoutBrandingColorSchemeSelection {
    SCHEME1,
    SCHEME2,
    TRANSPARENT
}

public enum CheckoutBrandingColorSelection {
    COLOR1,
    COLOR2,
    TRANSPARENT
}

public enum CheckoutBrandingFontSelection {
    PRIMARY,
    SECONDARY
}

public enum CheckoutBrandingFontSizeSelection {
    BASE,
    MEDIUM,
    LARGE,
    EXTRA_LARGE
}

public enum CheckoutBrandingBackgroundStyle {
    NONE,
    SOLID
}

public enum CheckoutBrandingBorderStyle {
    NONE,
    SOLID
}

public enum CheckoutBrandingBorderWidth {
    BASE,
    MEDIUM,
    THICK
}

// Supporting classes
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CheckoutBrandingBorder {
    
    @JsonProperty("style")
    private CheckoutBrandingBorderStyle style;
    
    @JsonProperty("width")
    private CheckoutBrandingBorderWidth width;
}

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CheckoutBrandingSimpleBorder {
    
    @JsonProperty("style")
    private CheckoutBrandingBorderStyle style;
    
    @JsonProperty("width")
    private CheckoutBrandingBorderWidth width;
}

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CheckoutBrandingButtonBackground {
    
    @JsonProperty("style")
    private CheckoutBrandingBackgroundStyle style;
}

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CheckoutBrandingButtonTypography {
    
    @JsonProperty("font")
    private CheckoutBrandingFontSelection font;
    
    @JsonProperty("kerning")
    private CheckoutBrandingKerning kerning;
    
    @JsonProperty("letterCase")
    private CheckoutBrandingLetterCase letterCase;
    
    @JsonProperty("size")
    private CheckoutBrandingFontSizeSelection size;
    
    @JsonProperty("weight")
    private CheckoutBrandingFontWeight weight;
}

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CheckoutBrandingSelectTypography {
    
    @JsonProperty("font")
    private CheckoutBrandingFontSelection font;
    
    @JsonProperty("kerning")
    private CheckoutBrandingKerning kerning;
    
    @JsonProperty("letterCase")
    private CheckoutBrandingLetterCase letterCase;
    
    @JsonProperty("size")
    private CheckoutBrandingFontSizeSelection size;
}

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CheckoutBrandingTextFieldTypography {
    
    @JsonProperty("font")
    private CheckoutBrandingFontSelection font;
    
    @JsonProperty("kerning")
    private CheckoutBrandingKerning kerning;
    
    @JsonProperty("letterCase")
    private CheckoutBrandingLetterCase letterCase;
    
    @JsonProperty("size")
    private CheckoutBrandingFontSizeSelection size;
}

// Input classes for mutations
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CheckoutBrandingInput {
    
    @JsonProperty("customizations")
    private CheckoutBrandingCustomizationsInput customizations;
    
    @JsonProperty("designSystem")
    private CheckoutBrandingDesignSystemInput designSystem;
}

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CheckoutBrandingCustomizationsInput {
    
    @JsonProperty("favicon")
    private CheckoutBrandingImageInput favicon;
    
    @JsonProperty("global")
    private CheckoutBrandingGlobalInput global;
    
    @JsonProperty("header")
    private CheckoutBrandingHeaderInput header;
    
    @JsonProperty("main")
    private CheckoutBrandingMainInput main;
    
    @JsonProperty("orderSummary")
    private CheckoutBrandingOrderSummaryInput orderSummary;
}

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CheckoutBrandingDesignSystemInput {
    
    @JsonProperty("colors")
    private CheckoutBrandingColorsInput colors;
    
    @JsonProperty("cornerRadius")
    private CheckoutBrandingCornerRadiusInput cornerRadius;
    
    @JsonProperty("typography")
    private CheckoutBrandingTypographyInput typography;
}

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CheckoutBrandingImageInput {
    
    @JsonProperty("mediaImageId")
    private String mediaImageId;
}

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CheckoutBrandingGlobalInput {
    
    @JsonProperty("cornerRadius")
    private CheckoutBrandingCornerRadiusVariablesInput cornerRadius;
    
    @JsonProperty("typography")
    private CheckoutBrandingGlobalTypographyInput typography;
}

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CheckoutBrandingHeaderInput {
    
    @JsonProperty("alignment")
    private CheckoutBrandingHorizontalAlignment alignment;
    
    @JsonProperty("banner")
    private CheckoutBrandingImageInput banner;
    
    @JsonProperty("logo")
    private CheckoutBrandingLogoInput logo;
    
    @JsonProperty("position")
    private CheckoutBrandingLogoPosition position;
}

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CheckoutBrandingMainInput {
    
    @JsonProperty("backgroundImage")
    private CheckoutBrandingBackgroundImageInput backgroundImage;
    
    @JsonProperty("colorScheme")
    private CheckoutBrandingColorSchemeSelection colorScheme;
}

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CheckoutBrandingOrderSummaryInput {
    
    @JsonProperty("backgroundImage")
    private CheckoutBrandingBackgroundImageInput backgroundImage;
    
    @JsonProperty("colorScheme")
    private CheckoutBrandingColorSchemeSelection colorScheme;
}

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CheckoutBrandingColorsInput {
    
    @JsonProperty("global")
    private CheckoutBrandingColorGroupInput global;
    
    @JsonProperty("schemes")
    private CheckoutBrandingColorSchemesInput schemes;
}

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CheckoutBrandingCornerRadiusInput {
    
    @JsonProperty("base")
    private Integer base;
    
    @JsonProperty("large")
    private Integer large;
    
    @JsonProperty("small")
    private Integer small;
}

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CheckoutBrandingTypographyInput {
    
    @JsonProperty("primary")
    private CheckoutBrandingFontGroupInput primary;
    
    @JsonProperty("secondary")
    private CheckoutBrandingFontGroupInput secondary;
    
    @JsonProperty("size")
    private CheckoutBrandingFontSizeInput size;
}

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CheckoutBrandingColorGroupInput {
    
    @JsonProperty("accent")
    private String accent;
    
    @JsonProperty("brand")
    private String brand;
    
    @JsonProperty("critical")
    private String critical;
    
    @JsonProperty("decorative")
    private String decorative;
    
    @JsonProperty("info")
    private String info;
    
    @JsonProperty("success")
    private String success;
    
    @JsonProperty("warning")
    private String warning;
}

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CheckoutBrandingColorSchemesInput {
    
    @JsonProperty("scheme1")
    private CheckoutBrandingColorSchemeInput scheme1;
    
    @JsonProperty("scheme2")
    private CheckoutBrandingColorSchemeInput scheme2;
}

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CheckoutBrandingColorSchemeInput {
    
    @JsonProperty("base")
    private CheckoutBrandingColorRolesInput base;
    
    @JsonProperty("control")
    private CheckoutBrandingColorRolesInput control;
    
    @JsonProperty("primaryButton")
    private CheckoutBrandingColorRolesInput primaryButton;
    
    @JsonProperty("secondaryButton")
    private CheckoutBrandingColorRolesInput secondaryButton;
}

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CheckoutBrandingColorRolesInput {
    
    @JsonProperty("accent")
    private String accent;
    
    @JsonProperty("background")
    private String background;
    
    @JsonProperty("border")
    private String border;
    
    @JsonProperty("decorative")
    private String decorative;
    
    @JsonProperty("icon")
    private String icon;
    
    @JsonProperty("selected")
    private String selected;
    
    @JsonProperty("text")
    private String text;
}

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CheckoutBrandingCornerRadiusVariablesInput {
    
    @JsonProperty("borderRadius")
    private Integer borderRadius;
}

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CheckoutBrandingGlobalTypographyInput {
    
    @JsonProperty("kerning")
    private CheckoutBrandingKerning kerning;
    
    @JsonProperty("letterCase")
    private CheckoutBrandingLetterCase letterCase;
}

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CheckoutBrandingLogoInput {
    
    @JsonProperty("image")
    private CheckoutBrandingImageInput image;
    
    @JsonProperty("maxWidth")
    private Integer maxWidth;
}

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CheckoutBrandingBackgroundImageInput {
    
    @JsonProperty("image")
    private CheckoutBrandingImageInput image;
}

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CheckoutBrandingFontGroupInput {
    
    @JsonProperty("shopifyFontGroup")
    private CheckoutBrandingShopifyFontGroupInput shopifyFontGroup;
    
    @JsonProperty("customFontGroup")
    private CheckoutBrandingCustomFontGroupInput customFontGroup;
}

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CheckoutBrandingShopifyFontGroupInput {
    
    @JsonProperty("name")
    private String name;
}

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CheckoutBrandingCustomFontGroupInput {
    
    @JsonProperty("base")
    private CheckoutBrandingFontInput base;
    
    @JsonProperty("bold")
    private CheckoutBrandingFontInput bold;
    
    @JsonProperty("loadingStrategy")
    private CheckoutBrandingFontLoadingStrategy loadingStrategy;
}

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CheckoutBrandingFontInput {
    
    @JsonProperty("genericFileId")
    private String genericFileId;
    
    @JsonProperty("weight")
    private Integer weight;
}

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CheckoutBrandingFontSizeInput {
    
    @JsonProperty("base")
    private Integer base;
    
    @JsonProperty("ratio")
    private Double ratio;
}