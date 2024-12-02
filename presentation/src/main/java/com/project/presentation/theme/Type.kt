package com.project.presentation.theme

import androidx.compose.runtime.Immutable
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import com.project.presentation.R

private val PretendFontFamily = FontFamily(
    Font(R.font.pretendard_bold, weight = FontWeight.Bold),
    Font(R.font.pretendard_semibold, weight = FontWeight.SemiBold),
    Font(R.font.pretendard_regular, weight = FontWeight.Normal),
)

val heymoonTypography = HeymoonTypography(
    textBold = TextStyle(
        fontFamily = PretendFontFamily,
        fontWeight = FontWeight.Bold,
    ),
    textSemiBold = TextStyle(
        fontFamily = PretendFontFamily,
        fontWeight = FontWeight.SemiBold,
    ),
    textRegular = TextStyle(
        fontFamily = PretendFontFamily,
        fontWeight = FontWeight.Normal,
    )
)

@Immutable
data class HeymoonTypography(
    val textBold: TextStyle,
    val textSemiBold: TextStyle,
    val textRegular: TextStyle
)