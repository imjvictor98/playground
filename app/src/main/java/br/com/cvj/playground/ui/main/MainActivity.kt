package br.com.cvj.playground.ui.main

import android.content.Context
import android.content.Intent
import android.location.Location
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.adapter.FragmentStateAdapter
import br.com.cvj.playground.R
import br.com.cvj.playground.data.network.ApiFactory
import br.com.cvj.playground.databinding.ActivityMainBinding
import br.com.cvj.playground.databinding.TabItemActiveBinding
import br.com.cvj.playground.databinding.TabItemInactiveBinding
import br.com.cvj.playground.domain.model.forecast.Hour
import br.com.cvj.playground.ui.base.BaseActivity
import br.com.cvj.playground.ui.main.compose.MainComposeActivity
import br.com.cvj.playground.ui.permission.location.PermissionLocationActivity
import br.com.cvj.playground.ui.weather.resume.WeatherResumeFragment
import br.com.cvj.playground.util.extension.setImageUrl
import br.com.cvj.playground.util.helper.LocationHelper
import br.com.cvj.playground.util.helper.PermissionHelper
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

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
    private val mRecyclerViewPool = RecyclerView.RecycledViewPool()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setBinding(ActivityMainBinding.inflate(layoutInflater))
        setContentView(mBinding.root)
        setPresenter(MainPresenter(this, ApiFactory.getWeatherServices(mContext)))

        init()

        val customTabAdapter = WeatherViewPagerAdapter(this)
        mBinding.activityMainViewpager2.adapter = customTabAdapter

        mBinding.activityMainTabLayout.addOnTabSelectedListener(object :
            TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                tab?.customView = customTabAdapter.getTabView(
                    tab?.position ?: throw Exception("Invalid position at TabLayout"),
                    mBinding.activityMainTabLayout
                )
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {
                tab?.customView = customTabAdapter.getTabView(
                    tab?.position ?: throw Exception("Invalid position at TabLayout"),
                    mBinding.activityMainTabLayout
                )
            }

            override fun onTabReselected(tab: TabLayout.Tab?) {}

        })

        TabLayoutMediator(
            mBinding.activityMainTabLayout,
            mBinding.activityMainViewpager2
        ) { _, _ -> }.attach()

        mBinding.activityMainTemperatureAutoRenew.apply {
            setOnLongClickListener {
                startActivity(
                    Intent(
                        this@MainActivity,
                        MainComposeActivity::class.java
                    ).apply { flags = Intent.FLAG_ACTIVITY_NEW_TASK })
                true
            }
            setOnClickListener { _ ->
                mLocation.let {
                    mPresenter?.requestWeather(it)
                    mPresenter?.requestForecast(it)
                } ?: run {
                    init()
                }
            }
        }
    }

    override fun displayWeatherImage(url: String) {
        mBinding.activityMainConditionWeatherImg.setImageUrl(
            url,
            R.drawable.ic_placeholder_rainbow
        )
    }

    override fun displayWeatherText(weather: String) {
        mBinding.activityMainConditionWeather.text = weather
    }

    override fun displayTemperature(temperature: String) {
        mBinding.activityMainTemperatureWeather.text = temperature
    }

    override fun displayCurrentLocation(location: String) {
        mBinding.activityMainLocationWeather.text = location
        mBinding.activityMainCardLocationCountry.text = location
    }

    override fun displayWeatherCondition(condition: String) {
        mBinding.activityMainConditionWeather.text = condition
    }

    override fun setForecastList(list: List<Hour>) {
        mBinding.activityMainForecastList.setRecycledViewPool(mRecyclerViewPool)
        mBinding.activityMainForecastList.adapter = ForecastAdapter(list)
    }

    override fun scrollToCurrentForecast(position: Int) {
        mBinding.activityMainForecastList.scrollToPosition(position)
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

    inner class WeatherViewPagerAdapter(fragmentActivity: FragmentActivity) :
        FragmentStateAdapter(fragmentActivity) {
        override fun getItemCount(): Int {
            return 2
        }

        override fun createFragment(position: Int): Fragment {
            return WeatherResumeFragment()
        }

        fun getTabView(position: Int, tabLayout: TabLayout): View {
            return if (position == tabLayout.selectedTabPosition) {
                val view = LayoutInflater.from(mContext).inflate(R.layout.tab_item_active, null)
                view.findViewById<TextView>(R.id.tab_item_active_title).text = "Selected"
                view
            } else {
                val view = LayoutInflater.from(mContext).inflate(R.layout.tab_item_inactive, null)
                view.findViewById<TextView>(R.id.tab_item_inactive_title).text = "Unselected"
                view
            }
        }
    }
}