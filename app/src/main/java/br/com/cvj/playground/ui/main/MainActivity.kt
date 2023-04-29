package br.com.cvj.playground.ui.main

import android.content.Context
import android.content.Intent
import android.location.Location
import android.os.Bundle
import br.com.cvj.playground.R
import br.com.cvj.playground.data.network.ApiFactory
import br.com.cvj.playground.databinding.ActivityMainBinding
import br.com.cvj.playground.domain.model.forecast.ResponseForecast
import br.com.cvj.playground.ui.BaseActivity
import br.com.cvj.playground.ui.main.compose.MainComposeActivity
import br.com.cvj.playground.ui.permission.location.PermissionLocationActivity
import br.com.cvj.playground.util.extension.setImageUrl
import br.com.cvj.playground.util.helper.LocationHelper
import br.com.cvj.playground.util.helper.PermissionHelper

class MainActivity : BaseActivity<IMainContract.Presenter, ActivityMainBinding>(),
    IMainContract.View {
    companion object {
        fun start(context: Context) {
            Intent(context, MainActivity::class.java).apply {
                context.startActivity(this)
            }
        }
    }

    private var mLocation: Location? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setBinding(ActivityMainBinding.inflate(layoutInflater))
        setContentView(mBinding?.root)
        setPresenter(MainPresenter(this, ApiFactory.getWeatherServices(mContext)))

        init()

        mBinding?.activityMainTemperatureAutoRenew?.apply {
            setOnLongClickListener {
                startActivity(
                    Intent(
                        this@MainActivity,
                        MainComposeActivity::class.java
                    ).apply { flags = Intent.FLAG_ACTIVITY_NEW_TASK })
                true
            }
            setOnClickListener {
                mLocation?.let {
                    mPresenter?.requestWeather(it)
                    mPresenter?.requestForecast(it)
                } ?: run {
                    init()
                }
            }
        }
    }

    override fun displayWeatherImage(url: String) {
        mBinding?.activityMainConditionWeatherImg?.setImageUrl(
            url,
            R.drawable.ic_placeholder_rainbow
        )
    }

    override fun displayWeatherText(weather: String) {
        mBinding?.activityMainConditionWeather?.text = weather
    }

    override fun displayTemperature(temperature: String) {
        mBinding?.activityMainTemperatureWeather?.text = temperature
    }

    override fun displayCurrentLocation(location: String) {
        mBinding?.activityMainLocationWeather?.text = location
        mBinding?.activityMainCardLocationCountry?.text = location
    }

    override fun displayWeatherCondition(condition: String) {
        mBinding?.activityMainConditionWeather?.text = condition
    }

    override fun setForecastList(list: List<ResponseForecast>) {
        mBinding?.activityMainForecastList?.adapter = ForecastAdapter(list)
    }

    override fun beforeDestroyView() {}

    private fun init() {
        if (!PermissionHelper.hasLocationPermissions(mContext)) {
            PermissionLocationActivity.start(mContext)
            finish()
        } else {
            LocationHelper.getLastLocation(mContext)?.addOnSuccessListener {
                mPresenter?.requestWeather(it)
                mPresenter?.requestForecast(it)
                mLocation = it
            }
        }
    }
}