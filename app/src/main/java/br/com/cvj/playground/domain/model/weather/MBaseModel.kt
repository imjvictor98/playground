package br.com.cvj.playground.domain.model.weather

import com.squareup.moshi.Json

@Json(name = "error")
data class MBaseModel(
    val code: Int = -1,
    val message: String? = null
)