package com.psi.home.data

import com.psi.home.data.raw.RawPsi
import com.psi.home.data.remote.HomeApi
import io.reactivex.Single

interface HomeRepository {
    fun getData(): Single<Data>
    fun getRawPsi(dateTime: String): Single<RawPsi>
}

class HomeRepositoryImpl(
    private val homeApi: HomeApi
): HomeRepository {
    override fun getData(): Single<Data> = homeApi.getData()
    override fun getRawPsi(dateTime: String): Single<RawPsi> = homeApi.fetchPsi(dateTime)
}