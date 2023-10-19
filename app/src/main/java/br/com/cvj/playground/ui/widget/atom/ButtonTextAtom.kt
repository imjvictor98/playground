package br.com.cvj.playground.ui.widget.atom

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import br.com.cvj.playground.ui.theme.Colors
import br.com.cvj.playground.ui.theme.Typography

@Composable
fun ButtonTextAtom(modifier: Modifier = Modifier, text: String, textColor: Color = Colors.White, ) {
    Text(
        modifier = modifier,
        text = text,
        fontFamily = Typography.labelMedium.fontFamily,
        fontSize = 14.sp,
        color = textColor,
        textAlign = TextAlign.Center,
    )
}

@Composable
@Preview(showBackground = true, backgroundColor = 0x333)
fun ButtonTextAtomPreview() {
    ButtonTextAtom(text = "Hello, world", modifier = Modifier.padding(16.dp))
}
