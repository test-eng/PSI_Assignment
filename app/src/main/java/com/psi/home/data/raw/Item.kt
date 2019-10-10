package com.psi.home.data.raw

import com.squareup.moshi.Json

data class Item(
    @field:Json(name = "timestamp") val timestamp: String,
    @field:Json(name = "update_timestamp") val updateTimestamp: String,
    @field:Json(name = "readings") val reading: Map<IndexId, Map<RegionId, Double>>
)

typealias RegionId = String
typealias IndexId = String