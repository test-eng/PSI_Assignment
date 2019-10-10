package com.psi.home.di

import androidx.lifecycle.ViewModelProviders
import com.psi.commons.DateTimeUtil
import com.psi.commons.DateTimeUtilImpl
import com.psi.commons.SchedulerProvider
import com.psi.commons.SchedulerProviderImpl
import com.psi.home.data.HomeRepository
import com.psi.home.data.HomeRepositoryImpl
import com.psi.home.data.remote.HomeApi
import com.psi.home.domain.GetPsiInteractor
import com.psi.home.domain.GetPsiInteractorImpl
import com.psi.home.domain.RawToAppDataConverter
import com.psi.home.domain.RawToAppDataConverterImpl
import com.psi.home.presentation.HomeFragment
import com.psi.home.presentation.HomeViewModel
import dagger.BindsInstance
import dagger.Component
import dagger.Module
import dagger.Provides
import io.reactivex.disposables.CompositeDisposable
import retrofit2.Retrofit
import javax.inject.Scope

@HomeScope @Component(
    modules = [HomeModule::class],
    dependencies = [HomeComponentParent::class]
)
interface HomeComponent {
    fun inject(homeFragment: HomeFragment)

    @Component.Builder
    interface Builder {
        @BindsInstance fun bindHomeFragment(homeFragment: HomeFragment): Builder
        fun parentComponent(homeComponentParent: HomeComponentParent): Builder
        fun build(): HomeComponent
    }
}

@Module
object HomeModule {
    @HomeScope @JvmStatic @Provides
    fun homeViewModel(
        getPsiInteractor: GetPsiInteractor,
        schedulerProvider: SchedulerProvider,
        homeFragment: HomeFragment): HomeViewModel = ViewModelProviders.of(homeFragment).get(HomeViewModel::class.java)
            .apply {
                this.getPsiInteractor = getPsiInteractor
                this.schedulerProvider = schedulerProvider
                this.disposableBag = CompositeDisposable()
            }

    @HomeScope @JvmStatic @Provides
    fun getPsiInteractor(homeRepository: HomeRepository, dateTimeUtil: DateTimeUtil, rawToAppDataConverter: RawToAppDataConverter): GetPsiInteractor
            = GetPsiInteractorImpl(homeRepository, dateTimeUtil, rawToAppDataConverter)

    @HomeScope @JvmStatic @Provides
    fun schedulerProvider(): SchedulerProvider = SchedulerProviderImpl()

    @HomeScope @JvmStatic @Provides
    fun rawToAppDataConverter(dateTimeUtil: DateTimeUtil): RawToAppDataConverter = RawToAppDataConverterImpl(dateTimeUtil)

    @HomeScope @JvmStatic @Provides
    fun homeRepository(homeApi: HomeApi): HomeRepository = HomeRepositoryImpl(homeApi)

    @HomeScope @JvmStatic @Provides
    fun homeApi(retrofit: Retrofit): HomeApi = retrofit.create(HomeApi::class.java)

    @HomeScope @JvmStatic @Provides
    fun dateTimeUtil(): DateTimeUtil = DateTimeUtilImpl()
}

interface HomeComponentParent {
    fun retrofit(): Retrofit
}

@Scope @Retention(AnnotationRetention.RUNTIME)
annotation class HomeScope
