package br.com.cvj.playground.ui.home

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import br.com.cvj.playground.domain.model.search.SearchCityItem
import br.com.cvj.playground.ui.main.MainActivity
import br.com.cvj.playground.ui.permission.location.PermissionLocation
import br.com.cvj.playground.ui.theme.PlaygroundTheme
import br.com.cvj.playground.util.extension.serializable
import br.com.cvj.playground.util.helper.PermissionHelper
import kotlinx.coroutines.launch

class HomeActivity : ComponentActivity() {
    private val intentResultLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == MainActivity.EXTRA_CITY_ITEM_RESULT_CODE) {
                val resultValue: SearchCityItem? = result.data?.extras?.serializable(
                    MainActivity.EXTRA_CITY_ITEM
                )
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PlaygroundTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    HomeScreen()
                }
            }
        }

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.CREATED) {
                if (!PermissionHelper.hasLocationPermissions(this@HomeActivity)) {
                    Intent(this@HomeActivity, PermissionLocation::class.java).also {
                        intentResultLauncher.launch(it)
                    }
                }
            }
        }
    }
}