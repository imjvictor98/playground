package br.com.cvj.playground.model

import com.squareup.moshi.Json

@Json(name = "error")
data class MBaseModel(
    val code: Int = -1,
    val message: String? = null
)