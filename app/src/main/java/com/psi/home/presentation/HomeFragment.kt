package com.psi.home.presentation

import android.graphics.drawable.Drawable
import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.TextView
import androidx.lifecycle.Observer
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.psi.R
import com.psi.app.di.AppComponentProvider
import com.psi.home.di.DaggerHomeComponent
import javax.inject.Inject
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.maps.android.ui.IconGenerator


class HomeFragment : Fragment() {

    companion object {
        fun newInstance() = HomeFragment()
    }

    @Inject lateinit var viewModel: HomeViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.main_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setupDI()
        view?.run {
            setupDropList(this, viewModel)
            bindToViewModel(this, viewModel)
        }
        viewModel.onAttach()
    }

    override fun onDestroy() {
        super.onDestroy()
        viewModel.onDestroy()
    }

    private fun setupDI() {
        DaggerHomeComponent.builder()
            .bindHomeFragment(this)
            .parentComponent((activity as AppComponentProvider).provide())
            .build()
            .inject(this)
    }

    private fun bindToViewModel(view: View, viewModel: HomeViewModel) {
        val messageTV = view.findViewById<TextView>(R.id.message)
        viewModel.message.observe(this, Observer {
            messageTV.text = it
        })

        val mapView = view.findViewById<MapView>(R.id.map)
        viewModel.indexInfors.observe(this, Observer {
            mapView.setMarker(it.mapNotNull { indexInfor ->
                val iconGenerator = IconGenerator(context).apply {
                    setBackground(getMarkerBackground())
                    setTextAppearance(R.style.markerStyle)
                }
                MarkerOptions().icon(BitmapDescriptorFactory
                    .fromBitmap(iconGenerator.makeIcon(indexInfor.value.toString())))
                    .position(LatLng(indexInfor.lat, indexInfor.lng))
            })
        })
    }

    private fun getMarkerBackground(): Drawable {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            resources.getDrawable(R.drawable.bg_marker, context?.theme)
        } else {
            @Suppress("DEPRECATION")
            resources.getDrawable(R.drawable.bg_marker)
        }
    }

    private fun setupDropList(view: View, viewModel: HomeViewModel) {
        val indexSelection = view.findViewById<Spinner>(R.id.indexSelection)

        val dropDownList = context?.resources?.let { indexIdToTitle(it) } ?: emptyMap()
        val adapter = context?.let {
            ArrayAdapter<String>(it, android.R.layout.simple_spinner_item, dropDownList.values.toMutableList())
        }
        adapter?.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

        indexSelection.adapter = adapter
        indexSelection.onItemSelectedListener = object: AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {
                // Do nothting
            }

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                viewModel.onSelectIndex(dropDownList.keys.toList()[position])
            }
        }
    }
}
