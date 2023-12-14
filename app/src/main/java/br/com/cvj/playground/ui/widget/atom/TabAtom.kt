package br.com.cvj.playground.ui.widget.atom

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Tab
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun TabAtom(
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
    selected: Boolean,
    text: @Composable (() -> Unit)?
) {
    Tab(selected = selected,
        onClick = onClick,
        modifier = Modifier
        .padding(8.dp)
        .then(modifier),
        text = text
    )
}