package br.com.cvj.playground.util.extension

fun Comparable<Boolean>.asDecision() = if (this == true) "yes" else "no"