package br.com.cvj.playground.ui.launch

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import br.com.cvj.playground.R
import br.com.cvj.playground.ui.permission.location.PermissionLocationActivity

class LaunchActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_launch)

        PermissionLocationActivity.start(this)
        finish()
    }
}