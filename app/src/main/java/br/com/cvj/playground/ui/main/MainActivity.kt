package br.com.cvj.playground.ui.main

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import br.com.cvj.playground.data.network.ApiFactory
import br.com.cvj.playground.databinding.ActivityMainBinding
import br.com.cvj.playground.ui.BaseActivity
import br.com.cvj.playground.ui.permission.location.PermissionLocationActivity
import br.com.cvj.playground.util.extension.toBitMap
import br.com.cvj.playground.util.helper.LocationHelper
import br.com.cvj.playground.util.helper.PermissionHelper

class MainActivity: BaseActivity<IMainContract.Presenter, ActivityMainBinding>(), IMainContract.View  {
    companion object {
        fun start(context: Context) {
            Intent(context, MainActivity::class.java).apply {
                context.startActivity(this)
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setBinding(ActivityMainBinding.inflate(layoutInflater))
        setContentView(mBinding?.root)
        setPresenter(MainPresenter(this, ApiFactory.getWeatherServices(mContext)))

        if (!PermissionHelper.hasLocationPermissions(mContext)) {
            PermissionLocationActivity.start(mContext)
            finish()
        } else {
            LocationHelper.getLastLocation(mContext)?.addOnSuccessListener {
                mPresenter?.requestWeather(it)
            }
        }
    }

    override fun displayWeatherImage(url: String) {
        mBinding?.activityMainConditionWeatherImg?.setImageURI(Uri.parse(url))
    }

    override fun displayWeatherText(weather: String) {
        mBinding?.activityMainConditionWeather?.text = weather
    }

    override fun displayTemperature(temperature: String) {
        mBinding?.activityMainTemperatureWeather?.text = temperature
    }

    override fun displayCurrentLocation(location: String) {
        mBinding?.activityMainLocationWeather?.text = location
    }

    override fun displayWeatherCondition(condition: String) {
        mBinding?.activityMainConditionWeather?.text = condition
    }

    override fun beforeDestroyView() {}
}