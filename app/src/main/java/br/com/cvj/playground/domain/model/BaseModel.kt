package br.com.cvj.playground.domain.model

import com.squareup.moshi.Json

@Json(name = "error")
data class BaseModel(
    val code: Int = -1,
    val message: String? = null
)