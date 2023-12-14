package br.com.cvj.playground.util.helper

import android.content.res.Resources
import android.util.Log
import androidx.annotation.RawRes
import java.io.BufferedReader
import java.io.InputStream
import java.io.InputStreamReader
import java.io.Reader
import java.io.StringWriter
import java.io.Writer


object RawHelper {
    fun jsonToString(resources: Resources, @RawRes rawFile: Int): String {
        val `is`: InputStream = resources.openRawResource(rawFile)
        val writer: Writer = StringWriter()
        val buffer = CharArray(1024)
        return try {
            val reader: Reader = BufferedReader(InputStreamReader(`is`, "UTF-8"))
            var n: Int
            while (reader.read(buffer).also { n = it } != -1) {
                writer.write(buffer, 0, n)
            }
            writer.toString()
        } catch (e: Exception) {
            Log.e("RawHelper", e.toString())
            String()
        } finally {
            `is`.close()
        }
    }
}