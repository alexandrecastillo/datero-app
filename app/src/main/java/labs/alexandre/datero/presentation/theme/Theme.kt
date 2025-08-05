package labs.alexandre.datero.presentation.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable

private val DarkColorScheme = darkColorScheme(
    primary = Gray50,
    onPrimary = Black70,
    secondary = Gray80,
    onSecondary = Black90,
    primaryContainer = Gray50,
    onPrimaryContainer = Black90,
    secondaryContainer = Gray80,
    onSecondaryContainer = White80,
    background = Black70,
    onBackground = White80,
    surface = Black70,
    onSurface = White80,
    surfaceContainer = Black70,
    onSurfaceVariant = White80,
    surfaceContainerLow = Black70,
    surfaceContainerHigh = Black70,
    surfaceContainerHighest = Black70,
)

private val LightColorScheme = lightColorScheme(
    primary = Black100,
    onPrimary = White100,
    primaryContainer = Black100,
    onPrimaryContainer = White100,
    secondary = Gray40,
    onSecondary = Black100,
    background = White100,
    onBackground = Black100,
    secondaryContainer = Gray30,
    onSecondaryContainer = Black100,
    surface = White100,
    onSurface = Black100,
    surfaceContainer = White100,
    onSurfaceVariant = Black70,
    surfaceContainerLow = White100,
    surfaceContainerHigh = White100,
    surfaceContainerHighest = White100
)

@Composable
fun DateroTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}