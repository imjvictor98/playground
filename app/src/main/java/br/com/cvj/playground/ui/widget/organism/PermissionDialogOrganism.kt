package br.com.cvj.playground.ui.widget.organism

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

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
            if (isPermanentlyDeclined) {
                onGoToAppSettingsClick()
            } else {
                onOkClick()
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
