package br.com.cvj.playground.ui.main

import android.content.Context
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import br.com.cvj.playground.R
import br.com.cvj.playground.databinding.ItemForecastBinding
import br.com.cvj.playground.domain.model.forecast.Hour
import br.com.cvj.playground.ui.BaseRecyclerViewAdapter
import br.com.cvj.playground.ui.BaseViewHolder
import br.com.cvj.playground.util.extension.applyScheme
import br.com.cvj.playground.util.extension.format
import br.com.cvj.playground.util.extension.isEqualsToCurrent
import br.com.cvj.playground.util.extension.setImageUrl

class ForecastAdapter(items: List<Hour>): BaseRecyclerViewAdapter<Hour>(items.toMutableList()) {
    override fun getViewHolder(view: View): BaseViewHolder<Hour> {
        return ViewHolder(ItemForecastBinding.bind(view))
    }

    override fun getLayoutRes() = R.layout.item_forecast

    inner class ViewHolder(itemView: ItemForecastBinding): BaseViewHolder<Hour>(itemView.root) {
        private val description: TextView = itemView.itemForecastCondition
        private val icon: ImageView = itemView.itemForecastIcon
        private val hour: TextView = itemView.itemForecastHour
        private val contextView: Context = itemView.root.context

        override fun bind(data: Hour) {
            bindData(data)
        }

        override fun bindData(item: Hour) {
            item.condition?.icon?.let { url ->
               icon.setImageUrl(url.applyScheme(), R.color.black)
            }

            item.condition?.text.let {
                description.text = it
            }

            item.time?.let {
                if (it.isEqualsToCurrent("HH")) {
                    hour.text = contextView.getString(R.string.now)
                } else {
                    hour.text = it.format("HH:mm")
                }
            }
        }
    }
}