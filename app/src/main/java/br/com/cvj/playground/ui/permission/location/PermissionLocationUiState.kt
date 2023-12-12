package br.com.cvj.playground.ui.permission.location

sealed class PermissionLocationUiState {
    object Initial : PermissionLocationUiState()
    data class PermissionsAllowed(
        val permissions: Map<String, Boolean>
    ) : PermissionLocationUiState()

    data class PermissionDenied(
        val permissions: Map<String, Boolean>
    ) : PermissionLocationUiState()
}