package br.com.cvj.playground.util.extension


import android.graphics.drawable.BitmapDrawable
import android.widget.ImageView
import androidx.annotation.DrawableRes
import androidx.appcompat.widget.AppCompatImageView
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.toBitmap
import com.bumptech.glide.Glide

fun ImageView.setImageUrl(url: String, @DrawableRes placeholderLoading: Int) {
    val placeholderBitmap =
        ContextCompat.getDrawable(this.context, placeholderLoading)?.toBitmap(200, 200)

    Glide
        .with(this)
        .load(url)
        .placeholder(BitmapDrawable(resources, placeholderBitmap))
        .into(this)
}

fun AppCompatImageView.setImageUrl(url: String, @DrawableRes placeholderLoading: Int) {
    val placeholderBitmap =
        ContextCompat.getDrawable(this.context, placeholderLoading)?.toBitmap(200, 200)

    Glide
        .with(this)
        .load(url)
        .placeholder(BitmapDrawable(resources, placeholderBitmap))
        .into(this)
}