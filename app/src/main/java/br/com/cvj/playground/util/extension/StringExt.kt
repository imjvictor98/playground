package br.com.cvj.playground.util.extension

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Log
import okio.IOException
import java.io.BufferedInputStream
import java.io.InputStream
import java.net.URL
import java.net.URLConnection


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
        Log.e("Error load bitmap", "Error getting bitmap", e)
    }
    return bm
}