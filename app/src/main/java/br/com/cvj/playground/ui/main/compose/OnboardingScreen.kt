package br.com.cvj.playground.ui.main.compose

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import br.com.cvj.playground.ui.main.compose.ui.theme.PlaygroundTheme

@Composable
fun OnBoardingScreen(
    onContinueClicked: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.fillMaxSize().background(MaterialTheme.colorScheme.background),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Bem-vindo ao Codelab Básico", style = TextStyle(fontStyle = FontStyle.Normal))
        ElevatedButton(
            shape = RoundedCornerShape(4.dp),
            modifier = Modifier
                .padding(vertical = 24.dp)
                .width(180.dp),
            colors = ButtonDefaults.elevatedButtonColors(Color.DarkGray, Color.White),
            onClick = onContinueClicked
        ) {
            Text("Continuar")
        }
    }

}

@Preview(showBackground = true, widthDp = 320, heightDp = 320)
@Composable
fun OnBoardingScreenPreview() {
    PlaygroundTheme {
        OnBoardingScreen(onContinueClicked = {})
    }
}