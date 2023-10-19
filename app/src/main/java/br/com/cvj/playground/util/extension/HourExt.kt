package br.com.cvj.playground.util.extension

import br.com.cvj.playground.domain.model.forecast.Hour

fun Hour.getCurrentHourIndex(hours: List<Hour>): Int {
    val current = hours.findLast { hour -> hour.time?.isEqualsToCurrent("HH") == true }
    return if (hours.indexOf(current) == -1) {
        -1
    } else {
        hours.indexOf(current)
    }
}