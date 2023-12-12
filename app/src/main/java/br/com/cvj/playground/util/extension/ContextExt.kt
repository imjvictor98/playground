package br.com.cvj.playground.util.extension

import android.content.Context
import android.content.Intent
import android.net.Uri


fun Context.shareToMaps(mapsUrl: String) {
    try {
        Intent(Intent.ACTION_VIEW).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK
            data = Uri.parse(mapsUrl)
            startActivity(this)
        }
    } catch (e: Exception) {
        e.printStackTrace()
    }
}

fun Context.shareLink(url: String) {
    try {
        Intent(Intent.ACTION_SEND).apply {
            putExtra(Intent.EXTRA_TEXT, url)
            type = "text/plain"

            Intent.createChooser(this, null).also {
                it.flags = Intent.FLAG_ACTIVITY_NEW_TASK
                startActivity(it)
            }
        }
    } catch (e: Exception) {
        e.printStackTrace()
    }
}

fun Context.callPhone(phone: String) {
    try {
        Intent(Intent.ACTION_DIAL, Uri.fromParts("tel", phone, null)).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK
            startActivity(this)
        }
    } catch (e: Exception) {
        e.printStackTrace()
    }
}