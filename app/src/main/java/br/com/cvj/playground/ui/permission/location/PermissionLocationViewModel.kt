package br.com.cvj.playground.ui.permission.location

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel

class PermissionLocationViewModel : ViewModel() {
    val visiblePermissionDialogQueue = mutableStateListOf<String>()

    fun dismissDialog() {
        visiblePermissionDialogQueue.removeFirst()
    }

    fun onPermissionResult(
        permission: String,
        isGranted: Boolean,
        onGranted: () -> Unit,
    ) {
        if (!isGranted && !visiblePermissionDialogQueue.contains(permission)) {
            visiblePermissionDialogQueue.add(permission)
        } else {
            onGranted()
        }
    }
}