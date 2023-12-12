package br.com.cvj.playground.ui.permission.location

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.viewmodel.compose.viewModel
import br.com.cvj.playground.R
import br.com.cvj.playground.ui.home.HomeActivity
import br.com.cvj.playground.ui.theme.PlaygroundTheme
import br.com.cvj.playground.ui.widget.organism.RequestLocationOrganism
import br.com.cvj.playground.util.extension.openAppSettings
import br.com.cvj.playground.util.helper.PermissionHelper
import timber.log.Timber

class PermissionLocation : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PlaygroundTheme {
                PermissionLocationScreen()
            }
        }
    }
}

@Composable
fun PermissionLocationScreen() {
    val activity = (LocalContext.current as Activity)
    val viewModel = viewModel<PermissionLocationViewModel>()
    val permissionLocationUiState = viewModel.permissionLocationUiState.collectAsState()
    val showAlertDialog = remember { mutableStateOf(true) }
    val locationPermissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestMultiplePermissions(),
        onResult = { permissions ->
            viewModel.onLocationPermissionResult(permissions)
            Timber.d("Permission result: %s", permissions.toString())
        }
    )

    fun requestLocationPermissions() {
        showAlertDialog.value = true
        viewModel.onLocationDialogDismissed()
        locationPermissionLauncher.launch(PermissionHelper.locationPermissions)
    }

    RequestLocationOrganism(
        description = R.string.activity_permission_location_info_text,
        image = R.drawable.img_location,
        btnText = R.string.activity_permission_location_request_btn,
        onClick = {
            requestLocationPermissions()
        })


    when (val state = permissionLocationUiState.value) {
        is PermissionLocationUiState.Initial -> {
            // do nothing
        }

        is PermissionLocationUiState.PermissionsAllowed -> {
            HomeActivity.startActivity(activity)
            activity.finish()
        }

        is PermissionLocationUiState.PermissionDenied -> {
            if (activity.shouldShowRequestPermissionRationale(state.permissions.keys.first())) {
                if (showAlertDialog.value) {
                    AlertDialog(
                        onDismissRequest = {
                            showAlertDialog.value = false
                        },
                        title = { Text(text = activity.getString(R.string.activity_permission_location_title)) },
                        text = { Text(text = activity.getString(R.string.activity_permission_location_info_text)) },
                        confirmButton = {
                            TextButton(
                                onClick = {
                                    requestLocationPermissions()
                                    showAlertDialog.value = false
                                }
                            ) {
                                Text(text = activity.getString(R.string.activity_permission_location_request_btn))
                            }
                        }
                    )
                }
            } else {
                if (showAlertDialog.value) {
                    AlertDialog(
                        onDismissRequest = {
                            showAlertDialog.value = false
                        },
                        title = { Text(text = activity.getString(R.string.activity_permission_location_title)) },
                        text = { Text(text = activity.getString(R.string.activity_permission_location_redirect_settings_info_text)) },
                        confirmButton = {
                            TextButton(
                                onClick = {
                                    activity.openAppSettings()
                                    showAlertDialog.value = false
                                }
                            ) {
                                Text(text = activity.getString(R.string.activity_settings_btn))
                            }
                        }
                    )
                }
            }
        }
    }

}

@Composable
@Preview
fun PermissionLocationScreenPreview() {
    PlaygroundTheme {
        PermissionLocationScreen()
    }
}