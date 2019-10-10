package com.psi.home.domain

import android.util.Log
import com.psi.commons.DateTimeUtil
import com.psi.home.data.app.Psi
import com.psi.home.data.app.Reading
import com.psi.home.data.app.RegionReading
import com.psi.home.data.raw.*

interface RawToAppDataConverter {
    fun rawPsiToAppPsi(from: RawPsi): Psi
}

class RawToAppDataConverterImpl(
    private val dateTimeUtil: DateTimeUtil
): RawToAppDataConverter {
    override fun rawPsiToAppPsi(from: RawPsi): Psi {
        return Psi(
            updateTime = nextUpdateTime(from.items),
            regionReadings = getRegionReading(from),
            apiInfo = from.apiInfo
        )
    }

    private fun getRegionReading(from: RawPsi): List<RegionReading> {
        return from.regions.map { region ->
            val readings: List<Reading> = from.items.map {
                val map = hashMapOf<IndexId, Double>()
                for ((indexId, regionMap) in it.reading) {
                    map[indexId] = regionMap[region.id]?:0.0
                }
                Reading(
                    data = map,
                    unixTime = dateTimeUtil.toUnixTime(it.timestamp)
                )
            }
            RegionReading(region, readings)
        }
    }

    private fun nextUpdateTime(items: List<Item>): Long? {
        return items.map { dateTimeUtil.toUnixTime(it.updateTimestamp) }
            .sorted()
            .firstOrNull { it > dateTimeUtil.provideCurrentUnixTime() }
    }
}