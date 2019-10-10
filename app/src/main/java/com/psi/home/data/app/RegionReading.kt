package com.psi.home.data.app

import com.psi.home.data.raw.IndexId
import com.psi.home.data.raw.Region

data class RegionReading(
    val region: Region,
    val readings: List<Reading>
)

data class Reading(
    val data: Map<IndexId, Double>,
    val unixTime: Long
)
