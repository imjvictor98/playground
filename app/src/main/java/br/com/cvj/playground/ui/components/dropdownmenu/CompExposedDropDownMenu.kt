package br.com.cvj.playground.ui.components.dropdownmenu

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.width
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.toSize

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun <T> CompExposedDropDownMenu(
    state: CompExposedDropDownMenuState<T>,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier) {
        Box {
            OutlinedTextField(
                value = state.value.toString(),
                onValueChange = {},
                label = {
                    Text(text = "Escolha a cidade")
                },
                trailingIcon = {
                    Icon(
                        painter = painterResource(id = state.icon),
                        contentDescription = null,
                        Modifier.clickable {
                            state.onEnabled(!state.enabled)
                        }
                    )
                },
                modifier = Modifier.onGloballyPositioned {
                    state.onSize(it.size.toSize())
                },
                readOnly = true,
            )
            DropdownMenu(
                expanded = state.enabled,
                onDismissRequest = { state.onEnabled(false) },
                modifier = Modifier.width(with(LocalDensity.current) {
                    state.size.width.toDp()
                })
            ) {
                state.items.forEachIndexed { index, option ->
                    DropdownMenuItem(
                        text = { Text(option.toString()) },
                        onClick = {
                            state.onSelectedIndex(index)
                            state.onEnabled(false)
                        }
                    )
                }
            }
        }
    }
}

@Composable
@Preview(showBackground = true, backgroundColor = 0xFFF)
fun CompExposedDropDownMenuPreview() {
    CompExposedDropDownMenu(
        state = rememberCompExposedDropDownMenuState(
            iconEnabled = android.R.drawable.arrow_up_float,
            iconDisabled = android.R.drawable.arrow_down_float,
            items = arrayListOf("Marijuana", "Cannabis", "Cocaine")
        )
    )
}
