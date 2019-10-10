package com.psi.home.data.remote

import com.psi.home.data.Data
import com.psi.home.data.raw.RawPsi
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface HomeApi {
    @GET("raw/CdtWSPYU")
    fun getData(): Single<Data>

    @GET("v1/environment/psi")
    fun fetchPsi(@Query("date_time") dateTime: String): Single<RawPsi>
}