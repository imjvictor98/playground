package br.com.cvj.playground.ui.weather.resume

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import br.com.cvj.playground.R
import br.com.cvj.playground.databinding.FragmentWeatherResumeBinding
import br.com.cvj.playground.ui.base.BaseFragment


class WeatherResumeFragment : BaseFragment<IWeatherResumeContract.Presenter, FragmentWeatherResumeBinding>(), IWeatherResumeContract.View {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        setBinding(FragmentWeatherResumeBinding.inflate(inflater, container, false))
        return mBinding?.root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setPresenter(WeatherResumePresenter(this))
    }

    override fun beforeDestroyView() {
    }
}