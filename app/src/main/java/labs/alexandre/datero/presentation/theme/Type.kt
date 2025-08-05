package labs.alexandre.datero.presentation.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.PlatformTextStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import labs.alexandre.datero.R

val poppinsFontFamily = FontFamily(
    Font(R.font.poppins_bold, FontWeight.Bold),
    Font(R.font.poppins_semibold, FontWeight.SemiBold),
    Font(R.font.poppins_medium, FontWeight.Medium),
)

val Typography = Typography(
    displayLarge = TextStyle(
        fontFamily = poppinsFontFamily,
        fontWeight = FontWeight.Bold,
        fontSize = 52.sp,
        platformStyle = PlatformTextStyle(
            includeFontPadding = false
        )
    ),
    displayMedium = TextStyle(
        fontFamily = poppinsFontFamily,
        fontWeight = FontWeight.SemiBold,
        fontSize = 45.sp,
        platformStyle = PlatformTextStyle(
            includeFontPadding = false
        )
    ),
    displaySmall = TextStyle(
        fontFamily = poppinsFontFamily,
        fontWeight = FontWeight.Medium,
        fontSize = 36.sp,
        platformStyle = PlatformTextStyle(
            includeFontPadding = false
        )
    ),
    headlineLarge = TextStyle(
        fontFamily = poppinsFontFamily,
        fontWeight = FontWeight.Bold,
        fontSize = 30.sp,
        platformStyle = PlatformTextStyle(
            includeFontPadding = false
        )
    ),
    headlineMedium = TextStyle(
        fontFamily = poppinsFontFamily,
        fontWeight = FontWeight.SemiBold,
        fontSize = 26.sp,
        platformStyle = PlatformTextStyle(
            includeFontPadding = false
        )
    ),
    headlineSmall = TextStyle(
        fontFamily = poppinsFontFamily,
        fontWeight = FontWeight.SemiBold,
        fontSize = 20.sp,
        platformStyle = PlatformTextStyle(
            includeFontPadding = false
        )
    ),
    titleLarge = TextStyle(
        fontFamily = poppinsFontFamily,
        fontWeight = FontWeight.SemiBold,
        fontSize = 22.sp,
        platformStyle = PlatformTextStyle(
            includeFontPadding = false
        )
    ),
    titleMedium = TextStyle(
        fontFamily = poppinsFontFamily,
        fontWeight = FontWeight.SemiBold,
        fontSize = 16.sp,
        platformStyle = PlatformTextStyle(
            includeFontPadding = false
        )
    ),
    titleSmall = TextStyle(
        fontFamily = poppinsFontFamily,
        fontWeight = FontWeight.SemiBold,
        fontSize = 16.sp,
        platformStyle = PlatformTextStyle(
            includeFontPadding = false
        )
    ),
    bodyLarge = TextStyle(
        fontFamily = poppinsFontFamily,
        fontWeight = FontWeight.Medium,
        fontSize = 16.sp,
        platformStyle = PlatformTextStyle(
            includeFontPadding = false
        )
    ),
    bodyMedium = TextStyle(
        fontFamily = poppinsFontFamily,
        fontWeight = FontWeight.Medium,
        fontSize = 14.sp,
        platformStyle = PlatformTextStyle(
            includeFontPadding = false
        )
    ),
    bodySmall = TextStyle(
        fontFamily = poppinsFontFamily,
        fontWeight = FontWeight.Medium,
        fontSize = 12.sp,
        platformStyle = PlatformTextStyle(
            includeFontPadding = false
        )
    ),
    labelLarge = TextStyle(
        fontFamily = poppinsFontFamily,
        fontWeight = FontWeight.SemiBold,
        fontSize = 16.sp,
        platformStyle = PlatformTextStyle(
            includeFontPadding = false
        )
    ),
    labelMedium = TextStyle(
        fontFamily = poppinsFontFamily,
        fontWeight = FontWeight.SemiBold,
        fontSize = 14.sp,
        platformStyle = PlatformTextStyle(
            includeFontPadding = false,
        ),
    ),
    labelSmall = TextStyle(
        fontFamily = poppinsFontFamily,
        fontWeight = FontWeight.SemiBold,
        fontSize = 13.sp,
        platformStyle = PlatformTextStyle(
            includeFontPadding = false
        )
    )
)