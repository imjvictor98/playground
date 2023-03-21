package br.com.cvj.playground.ui.main.compose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import br.com.cvj.playground.ui.main.compose.ui.theme.PlaygroundTheme

class MainComposeActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PlaygroundTheme {
                MyApp(modifier = Modifier.fillMaxSize())
            }
        }
    }
}

@Composable
private fun MyApp(modifier: Modifier = Modifier) {
    //Ancestral comum - Hoisted
    var shouldShowOnBoarding by remember {
        mutableStateOf(true)
    }

    Surface(
        modifier = modifier.fillMaxHeight(),
        color = Color(0xFFC6C6C6)
    ) {
        if (shouldShowOnBoarding) {
            OnBoardingScreen(onContinueClicked = { shouldShowOnBoarding = false })
        } else {
            Greetings()
        }
    }
}

@Composable
private fun Greetings(
    modifier: Modifier = Modifier,
    names: List<String> = listOf("Mundo", "Compose")
) {
    Column(modifier = modifier.padding(4.dp)) {
        names.forEach { name ->
            Greeting(name = name)
        }
    }
}

@Composable
private fun Greeting(name: String) {
    //A tela no compose é refeita a toda interação com o usuário
    //Remember torna o objeto protegido contra a recomposição(recompose)
    val expanded = remember {
        mutableStateOf(false)
    }

    val extraPadding = if (expanded.value) 48.dp else 0.dp

    Surface(
        shape = RoundedCornerShape(4.dp),
        color = MaterialTheme.colorScheme.primary,
        modifier = Modifier.padding(vertical = 4.dp, horizontal = 8.dp)
    ) {
        Row(modifier = Modifier.padding(24.dp)) {
            Column(
                modifier = Modifier
                    .weight(1F)
                    .padding(bottom = extraPadding)
            ) {
                Text(text = "Olá,")
                Text(text = name)
            }

            ElevatedButton(
                shape = RoundedCornerShape(4.dp),
                onClick = { expanded.value = !expanded.value }
            ) {
                Text(if (expanded.value) "Mostrar menos" else "Mostrar mais")
            }
        }
    }
}

@Preview(showBackground = true, widthDp = 320, name = "User Flow")
@Composable
private fun DefaultPreview() {
    PlaygroundTheme {
        MyApp(Modifier.fillMaxSize())
    }
}

@Preview(name = "Buttons")
@Composable
private fun ButtonsPreview() {
    PlaygroundTheme {
        Greetings()
    }
}