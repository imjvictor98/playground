package br.com.cvj.playground.ui.weather.resume

import android.view.View
import br.com.cvj.playground.R
import br.com.cvj.playground.databinding.ItemDayConditionBinding
import br.com.cvj.playground.domain.model.forecast.DayByType
import br.com.cvj.playground.domain.model.forecast.DayByTypeDTO
import br.com.cvj.playground.ui.base.BaseRecyclerViewAdapter
import br.com.cvj.playground.ui.base.BaseViewHolder

class WeatherResumeDayAdapter(items: List<DayByTypeDTO>): BaseRecyclerViewAdapter<DayByTypeDTO>(items.toMutableList()) {
    override fun getViewHolder(view: View): BaseViewHolder<DayByTypeDTO> {
        return DayByTypeDTOViewHolder(ItemDayConditionBinding.bind(view))
    }

    override fun getLayoutRes(): Int {
        return R.layout.item_day_condition
    }

    inner class DayByTypeDTOViewHolder(private val binding: ItemDayConditionBinding): BaseViewHolder<DayByTypeDTO>(binding.root) {
        override fun bind(data: DayByTypeDTO) {
            bindData(data)
        }

        override fun bindData(item: DayByTypeDTO) {
            val context = binding.root.context

            when(item.type) {
                DayByType.WIND -> {
                    binding.itemDayConditionIcon.setImageResource(R.drawable.ic_wind_18dp_blue)
                    binding.itemDayConditionTitle.text = context.getString(R.string.weather_resume_wind)
                    binding.itemDayConditionDescription.text = context.getString(R.string.string_format_kmh, item.day.maxwindKph?.toInt().toString())
                }
                DayByType.HUMIDITY -> {
                    binding.itemDayConditionIcon.setImageResource(R.drawable.ic_humidity_18dp_blue)
                    binding.itemDayConditionTitle.text = context.getString(R.string.weather_resume_humidity)
                    binding.itemDayConditionDescription.text = context.getString(R.string.string_format_percent, item.day.avghumidity?.toInt().toString())
                }
                DayByType.CHANCE_OF_RAIN -> {
                    binding.itemDayConditionIcon.setImageResource(R.drawable.ic_chance_rain_18dp_blue)
                    binding.itemDayConditionTitle.text = context.getString(R.string.weather_resume_chance_of_rain)
                    binding.itemDayConditionDescription.text = context.getString(R.string.string_format_percent, item.day.dailyChanceOfRain.toString())
                }
            }
        }
    }
}