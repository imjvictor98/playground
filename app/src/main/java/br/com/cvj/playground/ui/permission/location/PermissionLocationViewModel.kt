package br.com.cvj.playground.ui.permission.location

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class PermissionLocationViewModel : ViewModel() {
    private val _permissionLocationUiState: MutableStateFlow<PermissionLocationUiState> =
        MutableStateFlow(PermissionLocationUiState.Initial)

    val permissionLocationUiState: StateFlow<PermissionLocationUiState> = _permissionLocationUiState

    fun onLocationPermissionResult(permissions: Map<String, Boolean>) {
        val permissionsAllowed = permissions.filter { it.value }
        val permissionsDenied = permissions.filter { !it.value }

        if (permissionsAllowed.isNotEmpty()) {
            _permissionLocationUiState.value = PermissionLocationUiState.PermissionsAllowed(permissionsAllowed)
        } else {
            _permissionLocationUiState.value = PermissionLocationUiState.PermissionDenied(permissionsDenied)
        }
    }

    fun onLocationDialogDismissed() {
        _permissionLocationUiState.value = PermissionLocationUiState.Initial
    }
}