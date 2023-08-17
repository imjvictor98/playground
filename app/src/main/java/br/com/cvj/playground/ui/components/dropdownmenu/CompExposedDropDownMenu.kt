package br.com.cvj.playground.ui.components.dropdownmenu

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import br.com.cvj.playground.R
import br.com.cvj.playground.ui.theme.White

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun <T> CompExposedDropDownMenu(
    state: CompExposedDropDownMenuState<T>,
    onOptionSelected: (option: T) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }

    Box(modifier = Modifier
        .fillMaxWidth()
        .padding(16.dp)) {
        OutlinedTextField(
            value = state.value.toString(),
            onValueChange = {},
            label = {
                // Seu rótulo aqui
            },
            trailingIcon = {
                Box(
                    modifier = Modifier.clickable {
                        expanded = !expanded
                    }
                ) {
                    // Use seu ícone aqui
                }
            },
            modifier = Modifier.fillMaxWidth(),
            readOnly = true
        )
        if (expanded) {
            DropdownMenu(
                modifier = Modifier.fillMaxWidth(),
                expanded = expanded,
                onDismissRequest = { expanded = false }
            ) {
                LazyColumn(
                    modifier = Modifier.fillMaxWidth(),
                ) {
                    itemsIndexed(state.items) { index, option ->
                        DropdownMenuItem(
                            text = { option.toString() },
                            onClick = {
                                state.onSelectedIndex(index)
                                expanded = false
                                onOptionSelected(option)
                            }
                        )
                    }
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
            iconEnabled = R.drawable.arrow_drop_up_fill_24_black,
            iconDisabled = R.drawable.arrow_drop_down_fill_24_black,
            items = arrayListOf("Marijuana", "Cannabis", "Cocaine"),
        ),
        onOptionSelected = {}
    )
}
