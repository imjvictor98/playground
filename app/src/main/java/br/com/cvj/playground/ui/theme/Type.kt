package br.com.cvj.playground.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import br.com.cvj.playground.R

val hirukoProFamily = FontFamily(
    Font(R.font.hirukopro_regular, FontWeight.Normal),
    Font(R.font.hirukopro_bold, FontWeight.Bold),
)

val mazzardDmFamily = FontFamily(
    Font(R.font.mazzardm_regular, FontWeight.Normal),
    Font(R.font.mazzardm_medium, FontWeight.Medium),
    Font(R.font.mazzardm_bold, FontWeight.Bold),
)

val Typography = Typography(
    labelMedium = TextStyle(
        fontFamily = mazzardDmFamily,
        fontWeight = FontWeight.Medium,
        fontSize = 12.sp,
        lineHeight = 14.sp
    ),
    bodyLarge = TextStyle(
        fontFamily = mazzardDmFamily,
        fontWeight = FontWeight.Normal,
        fontSize = 24.sp,
        lineHeight = 24.sp,
        letterSpacing = 0.5.sp
    ),
    titleLarge = TextStyle(
        fontFamily = mazzardDmFamily,
        fontWeight = FontWeight.Bold,
        fontSize = 32.sp,
        lineHeight = 28.sp,
        letterSpacing = 0.96.sp
    )
)