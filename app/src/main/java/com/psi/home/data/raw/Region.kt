package com.psi.home.data.raw

import com.squareup.moshi.Json

data class Region(
    @field:Json(name = "name") val id: String,
    @field:Json(name = "label_location") val location: Location
)

data class Location(
    @field:Json(name = "latitude") val latitude: Double,
    @field:Json(name = "longitude") val longitude: Double
)