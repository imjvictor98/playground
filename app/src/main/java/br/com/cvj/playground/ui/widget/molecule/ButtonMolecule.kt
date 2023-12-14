package br.com.cvj.playground.ui.widget.molecule

import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import br.com.cvj.playground.ui.theme.Colors
import br.com.cvj.playground.ui.widget.atom.ButtonTextAtom

@Composable
fun ButtonMolecule(
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
    text: String,
    color: Color = Colors.Salmon
) {
    Button(modifier = modifier, onClick = { onClick?.invoke() }, colors = ButtonDefaults.buttonColors(
        containerColor = color
    )) {
        ButtonTextAtom(text = text)
    }
}

@Composable
@Preview
fun ButtonMoleculePreview() {
    ButtonMolecule(text = "Toque aqui", onClick = {})
}