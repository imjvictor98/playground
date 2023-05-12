package br.com.cvj.playground.util.extension

fun Int?.getZeroStartPaddingString(): String? {
    return if (this in 1..9) {
        this?.toString()?.padStart(2, '0')
    } else {
        this.toString()
    }
}