package com.psi.home.presentation

import androidx.lifecycle.MutableLiveData
import com.nhaarman.mockitokotlin2.*
import com.psi.commons.SchedulerProvider
import com.psi.home.data.app.IndexInfor
import com.psi.home.data.app.Psi
import com.psi.home.data.app.Reading
import com.psi.home.data.app.RegionReading
import com.psi.home.data.raw.ApiInfo
import com.psi.home.data.raw.Location
import com.psi.home.data.raw.Region
import com.psi.home.domain.GetPsiInteractor
import io.reactivex.Scheduler
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import org.junit.Assert.assertEquals
import org.junit.Test

import org.junit.Before
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import io.reactivex.disposables.CompositeDisposable
import org.junit.rules.TestRule
import org.junit.Rule



class HomeViewModelTest {
    private lateinit var scheduler: SchedulerProvider
    private lateinit var interactor: GetPsiInteractor
    private lateinit var indexList: MutableLiveData<List<IndexInfor>>
    private lateinit var viewModel: HomeViewModel

    @Rule @JvmField
    var rule: TestRule = InstantTaskExecutorRule()

    @Before
    fun setup() {
        scheduler = object: SchedulerProvider {
            override fun io(): Scheduler = Schedulers.trampoline()
            override fun mainThread(): Scheduler = Schedulers.trampoline()
        }
        interactor = mock()
        indexList = mock()
        viewModel = HomeViewModel().apply {
            this.schedulerProvider = scheduler
            this.getPsiInteractor = interactor
            this.disposableBag = CompositeDisposable()
        }
    }

    @Test
    fun onAttach() {
        doReturn(Single.just(Psi(
            updateTime = 0L,
            regionReadings = listOf(getMockRegionReading()),
            apiInfo = ApiInfo("healthy")
        ))).whenever(interactor).getPsi()

        viewModel.onSelectIndex("center")
        viewModel.onAttach()

        viewModel.indexInfors.observeForever {}
        assertEquals(listOf(IndexInfor(1.0, 2.0, 5.5)), viewModel.indexInfors.value)
    }
}

private fun getMockRegionReading(): RegionReading {
    val region = Region("center", Location(1.0, 2.0))
    val reading = Reading(
        data = mapOf("center" to 5.5),
        unixTime = 314
    )
    return RegionReading(region, listOf(reading))
}