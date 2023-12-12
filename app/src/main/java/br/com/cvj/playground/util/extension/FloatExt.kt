package br.com.cvj.playground.util.extension

fun Float.toPrecision(precision: Int) = "%.${precision}f".format(this)