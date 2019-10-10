package com.psi.app

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.psi.R
import com.psi.app.di.AppComponent
import com.psi.app.di.AppComponentProvider
import com.psi.app.di.DaggerAppComponent
import com.psi.home.presentation.HomeFragment

class MainActivity : AppCompatActivity(), AppComponentProvider {
    private lateinit var appComponent: AppComponent

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)
        setupDI()
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, HomeFragment.newInstance())
                .commitNow()
        }
    }

    private fun setupDI() {
        appComponent = DaggerAppComponent.builder().bindMainActivity(this).build()
        appComponent.inject(this)
    }

    override fun provide(): AppComponent = appComponent
}
