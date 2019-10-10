package com.psi.home.presentation

import android.content.Context
import android.util.AttributeSet
import android.widget.FrameLayout
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.addTo
import io.reactivex.subjects.BehaviorSubject
import io.reactivex.subjects.Subject


class MapView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr) {
    private val SINGAPORE = LatLng(1.3521, 103.8198)
    private val disposableBag = CompositeDisposable()
    private val mapSubject: Subject<GoogleMap> by lazy { BehaviorSubject.create<GoogleMap>() }

    init {
        val mapFragment = SupportMapFragment.newInstance()
        if (!isInEditMode) {
            (context as AppCompatActivity).supportFragmentManager.beginTransaction()
                .replace(id, mapFragment)
                .commit()
        }
        mapFragment.getMapAsync(mapSubject::onNext)
        defaultFocus()
    }

    private fun defaultFocus() {
        mapSubject.subscribe { map ->
            map.moveCamera(CameraUpdateFactory.newLatLngZoom(SINGAPORE, 10f))
        }.addTo(disposableBag)
    }

    fun setMarker(markerOptionsList: List<MarkerOptions>) {
        mapSubject.subscribe {  map ->
            map.clear()
            markerOptionsList.forEach {
                map.addMarker(it)
            }
        }.addTo(disposableBag)
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        disposableBag.dispose()
    }
}