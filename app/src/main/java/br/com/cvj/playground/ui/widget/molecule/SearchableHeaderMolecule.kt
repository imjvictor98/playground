package br.com.cvj.playground.ui.widget.molecule

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.KeyboardArrowDown
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import br.com.cvj.playground.ui.theme.Colors
import br.com.cvj.playground.ui.widget.atom.TitleAtom

@Composable
fun SearchableHeaderMolecule(
    modifier: Modifier = Modifier,
    text: String,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .then(modifier),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        TitleAtom(text = text, modifier = Modifier.padding(end = 8.dp))

        IconButton(
            modifier = Modifier.size(32.dp),
            enabled = true,
            onClick = onClick) {
            Icon(
                Icons.Outlined.Search,
                contentDescription = "Search",
                tint = Colors.White,
            )
        }

    }
}

@Composable
@Preview(showBackground = true, backgroundColor = 0x333)
fun SearchableHeaderMoleculePreview() {
    SearchableHeaderMolecule(text = "Hello World", onClick = {})
}