package br.com.cvj.playground.util.extension

import androidx.annotation.FontRes
import androidx.appcompat.widget.SearchView
import androidx.appcompat.widget.SearchView.SearchAutoComplete
import androidx.core.content.res.ResourcesCompat
import br.com.cvj.playground.R


fun SearchView.applyStyles(
    @FontRes font: Int = R.font.mazzardm_regular,
    textSize: Int = 16
) {
    apply {
        applyCustomFont(font)
        applyCustomTextSize(textSize)
    }
}
fun SearchView.applyCustomFont(@FontRes font: Int = R.font.mazzardm_regular) {
    val typeface = ResourcesCompat.getFont(this.context, font)
    this.findViewById<SearchAutoComplete>(androidx.appcompat.R.id.search_src_text)
        .typeface = typeface
}

fun SearchView.applyCustomTextSize(size: Int = 16) {
    this.findViewById<SearchAutoComplete>(androidx.appcompat.R.id.search_src_text)
        .textSize = size.toFloat()
}

