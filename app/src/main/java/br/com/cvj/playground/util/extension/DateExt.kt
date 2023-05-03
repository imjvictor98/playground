package br.com.cvj.playground.util.extension

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

fun Date.toSimpleDateFormat(format: String = "yyyy-MM-dd HH:mm"): SimpleDateFormat? {
    return try {
        val sdf = SimpleDateFormat(format, Locale.getDefault())
        sdf.format(this)
        sdf
    } catch (e: Exception) {
        null
    }
}

fun Date.format(format: String = "yyyy-MM-dd HH:mm"): String {
    return SimpleDateFormat(format, Locale.getDefault()).format(this)
}

fun Date.isEqualsToCurrent(format: String = "yyyy-MM-dd HH:mm"): Boolean {
    return format(format) == Date().format(format)
}