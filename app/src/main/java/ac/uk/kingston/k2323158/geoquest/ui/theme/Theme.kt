package ac.uk.kingston.k2323158.geoquest.ui.theme

import android.app.Activity
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

// 1. Define ONE single color scheme based exactly on your prototype
private val PrototypeColorScheme = lightColorScheme(
    primary = DarkForestGreen,
    secondary = DarkForestGreen,
    tertiary = DarkForestGreen,
    background = LightTanBackground,
    surface = LightTanBackground,
    onPrimary = WhiteText,
    onBackground = DarkGrayText,
    onSurface = DarkGrayText
)

@Composable
fun GeoQuestTheme(
    content: @Composable () -> Unit
) {
    val colorScheme = PrototypeColorScheme

    // 3. Color the top status bar
    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            window.statusBarColor = colorScheme.primary.toArgb()
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = false
        }
    }

    // 4. Apply the theme
    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}