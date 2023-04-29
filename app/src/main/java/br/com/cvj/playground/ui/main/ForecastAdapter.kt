package br.com.cvj.playground.ui.main

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import br.com.cvj.playground.R
import br.com.cvj.playground.databinding.ItemForecastBinding
import br.com.cvj.playground.domain.model.forecast.ResponseForecast
import br.com.cvj.playground.ui.BaseRecyclerViewAdapter
import br.com.cvj.playground.ui.BaseViewHolder
import br.com.cvj.playground.util.extension.setImageUrl

class ForecastAdapter(items: List<ResponseForecast>): BaseRecyclerViewAdapter<ResponseForecast>(items.toMutableList()) {
    override fun getViewHolder(view: View): BaseViewHolder<ResponseForecast> {
        return ViewHolder(ItemForecastBinding.bind(view))
    }

    override fun getLayoutRes() = R.layout.item_forecast

    inner class ViewHolder(itemView: ItemForecastBinding): BaseViewHolder<ResponseForecast>(itemView.root) {
        private val description: TextView = itemView.textView
        private val icon: ImageView = itemView.imageView

        override fun bind(data: ResponseForecast) {
            bindData(data)
        }

        override fun bindData(item: ResponseForecast) {
            item.current?.condition?.icon?.let {
               icon.setImageUrl("https:$it", R.color.black)
            }
            item.alerts?.alertList?.firstOrNull()?.event?.let {
                description.text = it
            }
        }
    }
}