package com.psi.home.data

import com.squareup.moshi.Json

data class Data(
    @field:Json(name = "message") val msg: String
)