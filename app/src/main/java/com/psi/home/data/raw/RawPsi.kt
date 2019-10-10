package com.psi.home.data.raw

import com.squareup.moshi.Json

data class RawPsi(
    @field:Json(name = "region_metadata") val regions: List<Region>,
    @field:Json(name = "items") val items: List<Item>,
    @field:Json(name = "api_info") val apiInfo: ApiInfo
)

data class ApiInfo(
    @field:Json(name = "status") val status: String
)