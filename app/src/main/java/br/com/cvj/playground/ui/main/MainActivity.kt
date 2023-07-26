package br.com.cvj.playground.ui.main

import android.content.Context
import android.content.Intent
import android.location.Location
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import br.com.cvj.playground.data.network.ApiFactory
import br.com.cvj.playground.databinding.ActivityMainBinding
import br.com.cvj.playground.domain.model.forecast.ForecastDTO
import br.com.cvj.playground.domain.model.search.SearchCityItem
import br.com.cvj.playground.ui.base.BaseActivity
import br.com.cvj.playground.ui.permission.location.PermissionLocationActivity
import br.com.cvj.playground.ui.search.SearchActivity
import br.com.cvj.playground.ui.weather.resume.WeatherResumeFragment
import br.com.cvj.playground.util.extension.DateCalendar
import br.com.cvj.playground.util.extension.getByCalendar
import br.com.cvj.playground.util.extension.getDate
import br.com.cvj.playground.util.extension.gone
import br.com.cvj.playground.util.extension.serializable
import br.com.cvj.playground.util.extension.visible
import br.com.cvj.playground.util.helper.LocationHelper
import br.com.cvj.playground.util.helper.PermissionHelper
import com.google.android.material.tabs.TabLayoutMediator

class MainActivity : BaseActivity<IMainContract.Presenter, ActivityMainBinding>(),
    IMainContract.View {
    companion object {
        const val EXTRA_CITY_ITEM = "EXTRA_CITY_ITEM"
        const val EXTRA_CITY_ITEM_RESULT_CODE = 102
        fun start(context: Context) {
            Intent(context, MainActivity::class.java).apply {
                context.startActivity(this)
            }
        }
    }

    private var mLocation: Location? = null

    private val intentResultLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == EXTRA_CITY_ITEM_RESULT_CODE) {
                val resultValue: SearchCityItem? = result.data?.extras?.serializable(EXTRA_CITY_ITEM)
                init(resultValue)
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setBinding(ActivityMainBinding.inflate(layoutInflater))
        setContentView(mBinding.root)
        setPresenter(MainPresenter(this, ApiFactory.getWeatherServices(mContext)))

        init()
    }

    override fun setupPages(forecasts: List<ForecastDTO>) {
        val customTabAdapter = WeatherViewPagerAdapter(this, forecasts)
        mBinding.activityMainViewpager2.adapter = customTabAdapter

        TabLayoutMediator(
            mBinding.activityMainTabLayout,
            mBinding.activityMainViewpager2
        ) { tab, position ->
            tab.text = forecasts[position].forecastDay?.date?.getDate("yyyy-MM-dd")?.getByCalendar(
                DateCalendar.DAY_REDUCE,
                DateCalendar.DAY,
                DateCalendar.MONTH,
                separator = listOf(", ", " ")
            )
        }.attach()
    }

    override fun revealSearch() {
        mBinding.activityMainSearch.apply {
            visible()
            setOnClickListener {
                try {
                    Intent(context, SearchActivity::class.java).apply {
                        intentResultLauncher.launch(this)
                    }
                } catch (e: Exception) {
                    e.message
                }
            }
        }
    }

    override fun displayLoading() {
        mBinding.spinKit.visibility = View.VISIBLE
    }

    override fun hideLoading() {
        mBinding.spinKit.visibility = View.GONE
    }

    override fun displayCity(name: String) {
        mBinding.activityMainCity.apply {
            visible()
            text = name
        }
    }

    override fun hideCity() {
        mBinding.activityMainCity.gone()
    }

    override fun beforeDestroyView() {}

    private fun init(cityItem: SearchCityItem? = null) {
        cityItem?.let {
            val location = LocationHelper.getLocationByLatLong(
                cityItem.lat.toDouble(), cityItem.lng.toDouble()
            )
            mPresenter?.requestForecast(location)
            mLocation = location
            return
        }

        if (!PermissionHelper.hasLocationPermissions(mContext)) {
            PermissionLocationActivity.start(mContext)
            finish()
        } else {
            LocationHelper.getLastLocation(mContext)?.addOnSuccessListener {
                mPresenter?.requestForecast(it)
                mLocation = it
            }
        }

    }

    inner class WeatherViewPagerAdapter(
        fragmentActivity: FragmentActivity,
        private val forecasts: List<ForecastDTO>
    ) : FragmentStateAdapter(fragmentActivity) {
        override fun getItemCount(): Int {
            return forecasts.size
        }

        override fun createFragment(position: Int): Fragment {
            return WeatherResumeFragment.getInstance(forecasts[position])
        }
    }
}