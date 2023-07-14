package br.com.cvj.playground.util.extension

import java.text.DateFormatSymbols
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

enum class DateCalendar {
    DAY,
    DAY_REDUCE,
    MONTH,
    YEAR,
    HOUR_OF_DAY,
    MINUTE_OF_HOUR
}

enum class DateFormat(val format: String) {
    YearMonthDayHourMinute("yyyy-MM-dd HH:mm")
}
fun Date.toSimpleDateFormat(format: String = DateFormat.YearMonthDayHourMinute.format): SimpleDateFormat? {
    return try {
        val sdf = SimpleDateFormat(format, Locale.getDefault())
        sdf.format(this)
        sdf
    } catch (e: Exception) {
        null
    }
}

fun Date.format(format: String = DateFormat.YearMonthDayHourMinute.format): String {
    return SimpleDateFormat(format, Locale.getDefault()).format(this)
}

fun Date.isEqualsToCurrent(format: String = DateFormat.YearMonthDayHourMinute.format): Boolean {
    return format(format) == Date().format(format)
}

fun Date.getByCalendar(vararg order: DateCalendar, separator: List<String> = listOf(" ")): String {
    val formatted = StringBuilder()

    for (i in order.indices) {
        when (order[i]) {
            DateCalendar.DAY -> {
                val day = toSimpleDateFormat()?.calendar?.get(Calendar.DAY_OF_MONTH)
                formatted.append(day?.getZeroStartPaddingString())
            }
            DateCalendar.DAY_REDUCE -> {
                val day = toSimpleDateFormat()?.calendar?.get(Calendar.DAY_OF_WEEK)
                formatted.append(DateFormatSymbols.getInstance(Locale.getDefault()).shortWeekdays[day!!])
            }
            DateCalendar.MONTH -> {
                val month = toSimpleDateFormat()?.calendar?.getDisplayName(Calendar.MONTH, Calendar.SHORT, Locale.getDefault())
                formatted.append(month)
            }
            DateCalendar.YEAR -> {
                formatted.append(toSimpleDateFormat()?.calendar?.get(Calendar.YEAR))
            }
            DateCalendar.HOUR_OF_DAY -> {
                val hourOfDay = toSimpleDateFormat()?.calendar?.get(Calendar.HOUR_OF_DAY)
                formatted.append(hourOfDay?.getZeroStartPaddingString())
            }
            DateCalendar.MINUTE_OF_HOUR -> {
                val minuteOfHour = toSimpleDateFormat()?.calendar?.get(Calendar.MINUTE)
                formatted.append(minuteOfHour?.getZeroStartPaddingString())
            }
        }
        if (i != order.size - 1) {
            formatted.append(separator.getOrNull(i) ?: separator[0])
        }
    }

    return formatted.toString().replace(".", " ").uppercase()
}