package br.com.cvj.playground

import android.os.Bundle

class MainActivity: BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        R.string.weather_api_key
    }
}