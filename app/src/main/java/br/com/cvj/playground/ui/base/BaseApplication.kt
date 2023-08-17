package br.com.cvj.playground.ui.base

import android.app.Application
import br.com.cvj.playground.BuildConfig
import timber.log.Timber


class BaseApplication: Application() {
    override fun onCreate() {
        super.onCreate()
        plant()
    }

    private fun plant() {
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
    }
}