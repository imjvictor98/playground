package br.com.cvj.playground.util.extension

import android.location.Location

fun Location.getLatLongAsString() = "${latitude},${longitude}"