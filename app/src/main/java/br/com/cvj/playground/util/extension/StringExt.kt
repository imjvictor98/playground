package br.com.cvj.playground.util.extension

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Patterns
import okio.IOException
import timber.log.Timber
import java.io.BufferedInputStream
import java.io.InputStream
import java.net.URL
import java.net.URLConnection
import java.text.Normalizer
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.regex.Pattern


fun String.toBitMap(): Bitmap? {
    var bm: Bitmap? = null
    try {
        val aURL = URL(this)
        val conn: URLConnection = aURL.openConnection()
        conn.connect()
        val `is`: InputStream = conn.getInputStream()
        val bis = BufferedInputStream(`is`)
        bm = BitmapFactory.decodeStream(bis)
        bis.close()
        `is`.close()
    } catch (e: IOException) {
        Timber.e("Error load bitmap", "Error getting bitmap", e)
    }
    return bm
}

fun String.applyScheme(scheme: String = "https"): String {
    return if (this.startsWith(scheme)) {
        this
    } else {
        "${scheme}:${this}"
    }
}

fun String.getDate(format: String = ""): Date? {
    return try {
        val sdf = SimpleDateFormat(format, Locale.getDefault())
        val dateNoQuotes = this.replace("'","")
        sdf.parse(dateNoQuotes)
    } catch (e: Exception) {
        return null
    }
}

fun String.isPhoneNumber(): Boolean {
    return Patterns.PHONE.matcher(this).matches()
}

fun String.removeAccents(): String {
    val normalizer: String = Normalizer.normalize(this, Normalizer.Form.NFD)
    val pattern: Pattern = Pattern.compile("\\p{InCombiningDiacriticalMarks}+")
    return pattern.matcher(normalizer).replaceAll("")
}