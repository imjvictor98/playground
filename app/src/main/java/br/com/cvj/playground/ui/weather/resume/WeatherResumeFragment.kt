package br.com.cvj.playground.ui.weather.resume

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import br.com.cvj.playground.R
import br.com.cvj.playground.databinding.FragmentWeatherResumeBinding
import br.com.cvj.playground.domain.model.forecast.DayByTypeDTO
import br.com.cvj.playground.domain.model.forecast.ForecastDTO
import br.com.cvj.playground.domain.model.forecast.Hour
import br.com.cvj.playground.ui.base.BaseFragment
import br.com.cvj.playground.util.extension.setImageUrl
import br.com.cvj.playground.util.extension.visible


class WeatherResumeFragment :
    BaseFragment<IWeatherResumeContract.Presenter, FragmentWeatherResumeBinding>(),
    IWeatherResumeContract.View {

    companion object {
        private const val FORECAST_EXTRA = "FORECAST_EXTRA"
        fun getInstance(forecast: ForecastDTO? = null): WeatherResumeFragment {
            val fragment = WeatherResumeFragment()

            Bundle().apply {
                putSerializable(FORECAST_EXTRA, forecast)
                fragment.arguments = this
            }

            return fragment
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return layoutInflater.inflate(R.layout.fragment_weather_resume, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setBinding(FragmentWeatherResumeBinding.bind(requireView()))
        setPresenter(WeatherResumePresenter(this))
        getExtras()
    }

    override fun displayTemperature(temperature: String) {
        mBinding?.fragmentWeatherResumeTemp?.apply {
            visible()
            text = temperature
        }
    }

    override fun displayCondition(imageUrl: String) {
        mBinding?.fragmentWeatherResumeImage?.setImageUrl(imageUrl, R.drawable.ic_weather_example)
    }

    override fun displayDaysProbabilityList(list: List<DayByTypeDTO>) {
        mBinding?.fragmentWeatherResumeDaysConditionRv?.adapter = WeatherResumeDayAdapter(list)
    }

    override fun displayForecastList(list: List<Hour>) {
        mBinding?.fragmentWeatherResumeForecastRv?.adapter = WeatherResumeForecastAdapter(list)
    }

    override fun scrollToCurrentHour(position: Int) {
        mBinding?.fragmentWeatherResumeForecastRv?.scrollToPosition(position)
    }

    override fun beforeDestroyView() {
    }

    private fun getExtras() {
        val extras = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            arguments?.getSerializable(FORECAST_EXTRA, ForecastDTO::class.java)
        } else {
            arguments?.get(FORECAST_EXTRA) as ForecastDTO
        }

        if (extras != null) {
            mPresenter?.start(extras)
        }
    }
}