package com.gongracr.ghreposloader.ui.theme

import androidx.compose.runtime.Immutable
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Immutable
data class AppDimensions(
    // Spacing
    val spacing0x: Dp,
    val spacing1x: Dp,
    val spacing2x: Dp,
    val spacing4x: Dp,
    val spacing6x: Dp,
    val spacing8x: Dp,
    val spacing12x: Dp,
    val spacing16x: Dp,
    val spacing18x: Dp,
    val spacing20x: Dp,
    val spacing24x: Dp,
    val spacing28x: Dp,
    val spacing32x: Dp,
    val spacing40x: Dp,
    val spacing48x: Dp,
    val spacing56x: Dp,
    val spacing64x: Dp,
    val spacing72x: Dp,
    val spacing80x: Dp,
    val spacing100x: Dp,
    val spacing200x: Dp,
    // Top bar
    val topBarIconPadding: Dp,
    val topBarSearchFieldHeight: Dp,
    val topBarIconSize: Dp,
    val searchBarIconSize: Dp,
    val searchBarTextSize: TextUnit,
    val normalTopBarHeight: Dp,
    val smallTopBarHeight: Dp,
    // Project cards
    val compactProjectCardPadding: Dp,
    val compactProjectCardImageSize: Dp,
    val detailedProjectCardPadding: Dp,
    val detailedProjectCardImageSize: Dp,
)

private val DefaultPhonePortraitAppDimensions: AppDimensions = AppDimensions(
    topBarIconPadding = 20.dp,
    normalTopBarHeight = 56.dp,
    smallTopBarHeight = 48.dp,
    topBarSearchFieldHeight = 56.dp,
    topBarIconSize = 30.dp,
    searchBarTextSize = 16.sp,
    searchBarIconSize = 24.dp,
    spacing0x = 0.dp,
    spacing1x = 1.dp,
    spacing2x = 2.dp,
    spacing4x = 4.dp,
    spacing6x = 6.dp,
    spacing8x = 8.dp,
    spacing12x = 12.dp,
    spacing16x = 16.dp,
    spacing18x = 18.dp,
    spacing20x = 20.dp,
    spacing24x = 24.dp,
    spacing28x = 28.dp,
    spacing32x = 32.dp,
    spacing40x = 40.dp,
    spacing48x = 48.dp,
    spacing56x = 56.dp,
    spacing64x = 64.dp,
    spacing72x = 72.dp,
    spacing80x = 80.dp,
    spacing100x = 100.dp,
    spacing200x = 200.dp,
    compactProjectCardPadding = 16.dp,
    compactProjectCardImageSize = 100.dp,
    detailedProjectCardPadding = 16.dp,
    detailedProjectCardImageSize = 200.dp
)

private val DefaultPhoneLandscapeAppDimensions: AppDimensions = DefaultPhonePortraitAppDimensions

private val DefaultPhoneOrientationDependentAppDimensions: OrientationDependent<AppDimensions> = OrientationDependent(
    portrait = DefaultPhonePortraitAppDimensions,
    landscape = DefaultPhoneLandscapeAppDimensions
)

val AppDimensionsTypes: ScreenSizeDependent<OrientationDependent<AppDimensions>> = ScreenSizeDependent(
    compactPhone = DefaultPhoneOrientationDependentAppDimensions,
    defaultPhone = DefaultPhoneOrientationDependentAppDimensions,
    tablet7 = DefaultPhoneOrientationDependentAppDimensions,
    tablet10 = DefaultPhoneOrientationDependentAppDimensions
)