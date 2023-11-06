package com.gongracr.ghreposloader.ui.theme

import androidx.compose.material3.ColorScheme
import androidx.compose.runtime.Immutable
import androidx.compose.ui.graphics.Color

@Immutable
data class AppColorScheme(
    val primary: Color,
    val onPrimary: Color,
    val primaryContainer: Color,
    val onPrimaryContainer: Color,
    val inversePrimary: Color,
    val secondary: Color,
    val onSecondary: Color,
    val secondaryContainer: Color,
    val onSecondaryContainer: Color,
    val tertiary: Color,
    val onTertiary: Color,
    val tertiaryContainer: Color,
    val onTertiaryContainer: Color,
    val background: Color,
    val onBackground: Color,
    val surface: Color,
    val onSurface: Color,
    val surfaceVariant: Color,
    val onSurfaceVariant: Color,
    val surfaceTint: Color,
    val inverseSurface: Color,
    val inverseOnSurface: Color,
    val error: Color,
    val onError: Color,
    val errorContainer: Color,
    val onErrorContainer: Color,
    val outline: Color,
    val outlineVariant: Color,
    val scrim: Color,

    ) {
    fun toColorScheme(): ColorScheme = ColorScheme(
        primary = primary,
        onPrimary = onPrimary,
        primaryContainer = primaryContainer,
        onPrimaryContainer = onPrimaryContainer,
        inversePrimary = inversePrimary,
        secondary = secondary,
        onSecondary = onSecondary,
        secondaryContainer = secondaryContainer,
        onSecondaryContainer = onSecondaryContainer,
        tertiary = tertiary,
        onTertiary = onTertiary,
        tertiaryContainer = tertiaryContainer,
        onTertiaryContainer = onTertiaryContainer,
        background = background, onBackground = onBackground,
        surface = surface, onSurface = onSurface,
        surfaceVariant = surfaceVariant, onSurfaceVariant = onSurfaceVariant,
        surfaceTint = surfaceTint,
        inverseSurface = inverseSurface, inverseOnSurface = inverseOnSurface,
        error = error, onError = onError,
        errorContainer = errorContainer, onErrorContainer = onErrorContainer,
        outline = outline, outlineVariant = outlineVariant,
        scrim = scrim
    )
}

private val LightAppColorScheme: AppColorScheme = AppColorScheme(
    primary = DarkGreen,
    onPrimary = Color.White,
    primaryContainer = LightGreen,
    onPrimaryContainer = Green40,
    inversePrimary = DarkerGreen,
    secondary = DarkBlue,
    onSecondary = Color.White,
    secondaryContainer = PurpleGrey40,
    onSecondaryContainer = Color.White,
    tertiary = TurquoiseDark,
    onTertiary = Color.White,
    tertiaryContainer = Pink40,
    onTertiaryContainer = Color.White,
    background = LightOlive,
    onBackground = DarkerBlue,
    surface = Purple80,
    onSurface = DarkerGreen,
    surfaceVariant = Color.White,
    onSurfaceVariant = TurquoiseDark,
    surfaceTint = Color.White,
    inverseSurface = DarkerGreen,
    inverseOnSurface = LightGreen,
    error = Red,
    onError = Color.White,
    errorContainer = Red,
    onErrorContainer = Color.White,
    outline = Color.White,
    outlineVariant = Color.White,
    scrim = Color.White
)

private val DarkAppColorScheme: AppColorScheme = LightAppColorScheme.copy(
    primary = Color.White,
    onPrimary = DarkGreen,
    secondary = PurpleGrey40,
    onSecondary = Purple40,
    background = DarkGreen,
    onBackground = LightGreen,
    error = LightRed,
    onError = Red,
)

val AppColorSchemeTypes: ThemeDependent<AppColorScheme> = ThemeDependent(
    light = LightAppColorScheme,
    dark = DarkAppColorScheme
)