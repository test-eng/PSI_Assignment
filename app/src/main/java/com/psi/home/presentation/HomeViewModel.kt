package com.psi.home.presentation

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.psi.commons.DEFAULT_ON_ERROR
import com.psi.commons.SchedulerProvider
import com.psi.home.data.app.IndexInfor
import com.psi.home.data.raw.IndexId
import com.psi.home.domain.GetPsiInteractor
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.addTo
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.subjects.BehaviorSubject
import io.reactivex.subjects.Subject

class HomeViewModel : ViewModel() {
    val message: MutableLiveData<String> = MutableLiveData()
    val indexInfors: MutableLiveData<List<IndexInfor>> = MutableLiveData()
    private val selectingIndex: Subject<IndexId> = BehaviorSubject.create()

    lateinit var schedulerProvider: SchedulerProvider
    lateinit var getPsiInteractor: GetPsiInteractor
    lateinit var disposableBag: CompositeDisposable

    fun onAttach() {
        getPsiInteractor.getPsi()
            .flatMapObservable { psi ->
                selectingIndex.map { indexId ->
                    psi.regionReadings.map {
                        IndexInfor(
                            lat = it.region.location.latitude,
                            lng = it.region.location.longitude,
                            value = it.readings[0].data[indexId]?:0.0
                        )
                    }
                }
            }
            .subscribeOn(schedulerProvider.io())
            .observeOn(schedulerProvider.mainThread())
            .subscribeBy(DEFAULT_ON_ERROR) {
                indexInfors.value = it
            }.addTo(disposableBag)
    }

    fun onDestroy() {
        disposableBag.dispose()
    }

    fun onSelectIndex(id: IndexId) {
        selectingIndex.onNext(id)
    }
}
