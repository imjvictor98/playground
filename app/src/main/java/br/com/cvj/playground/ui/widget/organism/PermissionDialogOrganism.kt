package br.com.cvj.playground.ui.widget.organism

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Call
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import br.com.cvj.playground.util.extension.callPhone

@Composable
fun PermissionDialogOrganism(
    permissionTextProvider: PermissionTextProvider,
    isPermanentlyDeclined: Boolean,
    onDismiss: () -> Unit,
    onOkClick: () -> Unit,
    onGoToAppSettingsClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    AlertDialog(
        onDismissRequest = {},

        confirmButton = {
            Button(
                contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
                modifier = Modifier.padding(end = 8.dp),
                shape = RoundedCornerShape(24.dp),
                onClick = {
                    if (isPermanentlyDeclined) {
                        onGoToAppSettingsClick()
                    } else {
                        onOkClick()
                    }
                }
            ) {
                Text(modifier = Modifier.padding(horizontal = 8.dp), text = if (isPermanentlyDeclined) "Ir para configurações" else "Ok")
            }
        },
        dismissButton = { onDismiss() },
        text = { Text(text = permissionTextProvider.getDescription(isPermanentlyDeclined = isPermanentlyDeclined)) },
        modifier = modifier
    )
}

interface PermissionTextProvider {
    fun getDescription(isPermanentlyDeclined: Boolean): String
}

class LocationPermissionTextProvider: PermissionTextProvider {
    override fun getDescription(isPermanentlyDeclined: Boolean): String {
        return if(isPermanentlyDeclined) {
            "Parece que você bloqueou as permissões de localizacão permanentemente" +
                    ", precisamos da permissão para que possamos obter o clima na sua região."
        } else {
            "O app precisa da permissão de localização para mostrar o clima na sua região."
        }
    }
}
