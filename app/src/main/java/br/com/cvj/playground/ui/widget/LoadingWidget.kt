package br.com.cvj.playground.ui.widget

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.view.animation.AlphaAnimation
import android.view.animation.Animation
import android.view.animation.DecelerateInterpolator
import android.widget.FrameLayout
import android.widget.LinearLayout
import br.com.cvj.playground.R
import br.com.cvj.playground.databinding.WidgetLoadingBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class LoadingWidget(context: Context, attrs: AttributeSet): LinearLayout(context, attrs) {
    private var binding: WidgetLoadingBinding? = null
    private var mJob: Job? = null
    private val images = listOf(
        Pair(R.drawable.weather_loading_1, "Relaxa, já estamos buscando as informações do clima pra você!"),
        Pair(R.drawable.weather_loading_6, "Segura a ansiedade que estamos trabalhando forte pra te entregar a previsão mais de boa"),
        Pair(R.drawable.weather_loading_3, "Estamos pedindo ajuda pros nossos metereólogos pra te trazer as infos mais cabulosas"),
        Pair(R.drawable.weather_loading_4, "Nosso app tá buscando as informações do tempo pra você. Já, já a gente te fala se vai dar praia ou não!"),
        Pair(R.drawable.weather_loading_5, "Tá quase lá, só mais um pouquinho enquanto nosso app traz as informações fresquinhas do clima"),
        Pair(R.drawable.weather_loading_2, "Nosso app tá suando a camisa para buscar a melhor previsão do tempo para você. Obrigado por esperar!"),
    )

    private val fadeIn: Animation = AlphaAnimation(0F, 1F).apply {
        interpolator = DecelerateInterpolator()
        duration = 500;
    }

    init {
        binding = WidgetLoadingBinding.inflate(LayoutInflater.from(context))
        addView(binding?.root)
        doLoading()
    }

    private fun doLoading() {
        var index = 0
        mJob = CoroutineScope(Dispatchers.Main).launch {
            while (true) {
                if (index == images.indices.last()) {
                    index = 0
                }
                binding?.widgetLoadingImage?.apply {
                    if (index != 0) {
                        startAnimation(fadeIn)
                    }
                    setImageResource(images[index].first)
                }
                binding?.widgetLoadingText?.text = images[index].second
                index++
                delay(4000)
            }
        }
    }

    override fun onWindowVisibilityChanged(visibility: Int) {
        super.onWindowVisibilityChanged(visibility)
        if (visibility == View.GONE) {
            mJob?.cancel()
        } else {
            if (mJob?.isActive == false || mJob == null) {
                doLoading()
            }
        }
    }
}