package com.psi.home.domain

import com.psi.commons.DateTimeUtil
import com.psi.home.data.HomeRepository
import com.psi.home.data.app.Psi
import io.reactivex.Single

interface GetPsiInteractor {
    fun getPsi(): Single<Psi>
}

class GetPsiInteractorImpl(
    private val repository: HomeRepository,
    private val dateTimeUtil: DateTimeUtil,
    private val rawToAppDataConverter: RawToAppDataConverter
): GetPsiInteractor {
    override fun getPsi(): Single<Psi> =
        repository.getRawPsi(dateTimeUtil.provideCurrentTime())
            .map { rawToAppDataConverter.rawPsiToAppPsi(it) }
}