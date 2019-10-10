package com.psi.home.presentation

import android.content.res.Resources
import com.psi.R

fun indexIdToTitle(resources: Resources): Map<String, String> {
    return hashMapOf(
        "o3_sub_index" to resources.getString(R.string.o3_sub_index),
        "o3_eight_hour_max" to resources.getString(R.string.o3_eight_hour_max),
        "pm10_twenty_four_hourly" to resources.getString(R.string.pm10_twenty_four_hourly),
        "pm10_sub_index" to resources.getString(R.string.pm10_sub_index),
        "co_sub_index" to resources.getString(R.string.co_sub_index),
        "co_eight_hour_max" to resources.getString(R.string.co_eight_hour_max),
        "pm25_twenty_four_hourly" to resources.getString(R.string.pm25_twenty_four_hourly),
        "pm25_sub_index" to resources.getString(R.string.pm25_sub_index),
        "so2_sub_index" to resources.getString(R.string.so2_sub_index),
        "so2_twenty_four_hourly" to resources.getString(R.string.so2_twenty_four_hourly),
        "no2_one_hour_max" to resources.getString(R.string.no2_one_hour_max),
        "psi_twenty_four_hourly" to resources.getString(R.string.psi_twenty_four_hourly)
    )
}