package br.com.cvj.playground.ui.permission.location

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import br.com.cvj.playground.ui.home.HomeActivity
import br.com.cvj.playground.ui.theme.PlaygroundTheme
import br.com.cvj.playground.ui.widget.organism.LocationPermissionTextProvider
import br.com.cvj.playground.ui.widget.organism.PermissionDialogOrganism
import br.com.cvj.playground.ui.widget.organism.RequestLocationOrganism
import br.com.cvj.playground.util.extension.openAppSettings
import br.com.cvj.playground.util.helper.PermissionHelper

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
    val dialogQueue = viewModel.visiblePermissionDialogQueue


    val permissionsLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestMultiplePermissions(),
        onResult = { perms ->
            PermissionHelper.locationPermissions.forEach { permission ->
                viewModel.onPermissionResult(
                    permission = permission,
                    isGranted = perms[permission] == true,
                    onGranted = {
                        Intent(activity, HomeActivity::class.java).also {
                            activity.startActivity(it)
                            activity.finish()
                        }
                    }
                )
            }
        }
    )

    RequestLocationOrganism(onClick = {
        permissionsLauncher.launch(PermissionHelper.locationPermissions)
    })

    dialogQueue
        .reversed()
        .forEach { permission ->
            PermissionDialogOrganism(
                permissionTextProvider = when (permission) {
                    Manifest.permission.ACCESS_COARSE_LOCATION,
                    Manifest.permission.ACCESS_FINE_LOCATION-> {
                        LocationPermissionTextProvider()
                    }
                    else -> return@forEach
                },
                isPermanentlyDeclined = !activity.shouldShowRequestPermissionRationale(
                    permission
                ),
                onDismiss = viewModel::dismissDialog,
                onOkClick = {
                    viewModel.dismissDialog()
                    permissionsLauncher.launch(
                        arrayOf(permission)
                    )
                },
                onGoToAppSettingsClick = {
                    activity.openAppSettings()
                }
            )
        }
}

@Composable
@Preview
fun PermissionLocationScreenPreview() {
    PlaygroundTheme {
        PermissionLocationScreen()
    }
}