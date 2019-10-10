package com.psi.home.data.app

import com.psi.home.data.raw.ApiInfo

data class Psi(
    val updateTime: Long?,
    val regionReadings: List<RegionReading>,
    val apiInfo: ApiInfo
)