package com.psi.app.di

import com.psi.app.BASE_URL
import com.psi.app.MainActivity
import com.psi.app.network.RetrofitClient
import com.psi.home.di.HomeComponentParent
import dagger.BindsInstance
import dagger.Component
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import javax.inject.Scope

@AppScope @Component(modules = [AppModule::class])
interface AppComponent: HomeComponentParent {
    fun inject(main: MainActivity)

    @Component.Builder
    interface Builder {
        @BindsInstance fun bindMainActivity(main: MainActivity): Builder
        fun build(): AppComponent
    }
}

@Module
object AppModule {
    @AppScope @JvmStatic @Provides
    fun retrofit(): Retrofit = RetrofitClient().getRetrofit(BASE_URL)
}

@Scope @Retention(AnnotationRetention.RUNTIME)
annotation class AppScope

interface AppComponentProvider {
    fun provide(): AppComponent
}