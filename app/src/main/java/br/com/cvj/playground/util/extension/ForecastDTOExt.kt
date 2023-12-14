package br.com.cvj.playground.util.extension

import br.com.cvj.playground.domain.model.forecast.ForecastDTO

fun ForecastDTO.getDateForTab() = forecastDay?.date?.getDate("yyyy-MM-dd")?.getByCalendar(
    DateCalendar.DAY_REDUCE,
    DateCalendar.DAY,
    DateCalendar.MONTH,
    separator = listOf(", ", " ")
)
