package br.com.cvj.playground.util.extension

import android.view.View

fun View.visible() {
    this.visibility = View.VISIBLE
}
fun View.gone() {
    this.visibility = View.GONE
}