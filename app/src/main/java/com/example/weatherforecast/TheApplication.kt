package com.example.weatherforecast

import android.app.Application
import com.example.city_list.CityListFeatureApi
import com.example.city_list.CityListComponentHolder
import com.example.city_list.CityListFeatureDependencies
import com.example.city_list.CityListFragmentFactory
import com.example.city_list.CityListMonitor
import com.example.city_list.domain.GeneralSettingsRepository
import com.example.city_list.domain.WeatherOfflineRepository
import com.example.city_list.domain.WeatherOnlineRepository
import com.example.common.CommonFeatureApi
import com.example.common.CommonComponentHolder
import com.example.common.CommonFeatureDependencies
import com.example.common.coroutine_utils.DispatcherProvider
import com.example.common.logs.Logger
import com.example.common.time_utils.TimeProvider
import com.example.main_screen.MainScreenComponentHolder
import com.example.main_screen.MainScreenFeatureDependencies
import com.example.module_injector.BaseFeatureDependencies
import com.example.module_injector.BaseDependencyHolder
import com.example.module_injector.DependencyHolder0
import com.example.module_injector.DependencyHolder1
import com.example.module_injector.DependencyHolder2
import com.example.module_injector.DependencyHolder3
import com.example.weather_details.WeatherDetailsFeatureApi
import com.example.weather_details.WeatherDetailsComponentHolder
import com.example.weather_details.WeatherDetailsFeatureDependencies
import com.example.weather_details.WeatherDetailsFragmentFactory
import com.example.weather_details.domain.WeatherDetailsRepository
import com.example.weatherforecast.common.ApplicationProvider
import com.example.weatherforecast.common.ApplicationProviderImpl
import com.example.weatherforecast.di.app.AppFeatureApi
import com.example.weatherforecast.di.app.AppComponentHolder
import com.example.weatherforecast.di.app.AppFeatureDependencies


class TheApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        MainScreenComponentHolder.dependencyProvider = {
            class MainScreenComponentDependencyHolder(
                override val block: (BaseDependencyHolder<MainScreenFeatureDependencies>, CityListFeatureApi, WeatherDetailsFeatureApi, CommonFeatureApi) -> MainScreenFeatureDependencies
            ) : DependencyHolder3<CityListFeatureApi, WeatherDetailsFeatureApi, CommonFeatureApi, MainScreenFeatureDependencies>(
                api1 = CityListComponentHolder.get(),
                api2 = WeatherDetailsComponentHolder.get(),
                api3 = CommonComponentHolder.get()
            )

            MainScreenComponentDependencyHolder { dependencyHolder, cityListApi: CityListFeatureApi, weatherDetailsApi: WeatherDetailsFeatureApi, commonApi: CommonFeatureApi ->
                object : MainScreenFeatureDependencies {
                    override val cityListFragmentFactory: CityListFragmentFactory = cityListApi.cityListFragmentFactory
                    override val weatherDetailsFragmentFactory: WeatherDetailsFragmentFactory = weatherDetailsApi.weatherDetailsFragmentFactory
                    override val cityListMonitor: CityListMonitor = cityListApi.cityListMonitor
                    override val dispatcherProvider: DispatcherProvider = commonApi.dispatcherProvider
                    override val dependencyHolder: BaseDependencyHolder<out BaseFeatureDependencies> = dependencyHolder
                }
            }.dependencies
        }

        AppComponentHolder.dependencyProvider = {
            class AppComponentDependencyHolder(
                override val block: (BaseDependencyHolder<AppFeatureDependencies>) -> AppFeatureDependencies
            ) : DependencyHolder0<AppFeatureDependencies>()

            AppComponentDependencyHolder { deps ->
                object : AppFeatureDependencies {
                    override val appProvider: ApplicationProvider = ApplicationProviderImpl(this@TheApplication)
                    override val dependencyHolder: BaseDependencyHolder<out BaseFeatureDependencies> = deps
                }
            }.dependencies
        }

        CommonComponentHolder.dependencyProvider = {
            class CommonComponentDependencyHolder(
                override val block: (BaseDependencyHolder<CommonFeatureDependencies>, api1: AppFeatureApi) -> CommonFeatureDependencies
            ) : DependencyHolder1<AppFeatureApi, CommonFeatureDependencies>(api1 = AppComponentHolder.get())

            CommonComponentDependencyHolder { dependencyHolder, appApi ->
                object : CommonFeatureDependencies {
                    override val logger: Logger = appApi.logger
                    override val dependencyHolder: BaseDependencyHolder<out BaseFeatureDependencies> = dependencyHolder
                }
            }.dependencies
        }

        CityListComponentHolder.dependencyProvider = {
            class CityListComponentDependencyHolder(
                override val block: (BaseDependencyHolder<CityListFeatureDependencies>, AppFeatureApi, CommonFeatureApi) -> CityListFeatureDependencies
            ) : DependencyHolder2<AppFeatureApi, CommonFeatureApi, CityListFeatureDependencies>(
                api1 = AppComponentHolder.get(),
                api2 = CommonComponentHolder.get()
            )

            CityListComponentDependencyHolder { dependencyHolder, appApi: AppFeatureApi, commonApi: CommonFeatureApi ->
                object : CityListFeatureDependencies {
                    override val dispatcherProvider: DispatcherProvider = commonApi.dispatcherProvider
                    override fun getTimeProvider(): TimeProvider = commonApi.timeProvider
                    override val generalSettingsRepository: GeneralSettingsRepository = appApi.generalSettingsRepositoryImpl
                    override val weatherOfflineRepository: WeatherOfflineRepository = appApi.weatherOfflineRepositoryImpl
                    override val weatherOnlineRepository: WeatherOnlineRepository = appApi.weatherOnlineRepositoryImpl
                    override val dependencyHolder: BaseDependencyHolder<out BaseFeatureDependencies> = dependencyHolder
                }
            }.dependencies
        }

        WeatherDetailsComponentHolder.dependencyProvider = {
            class WeatherDetailsComponentDependencyHolder(
                override val block: (BaseDependencyHolder<WeatherDetailsFeatureDependencies>, AppFeatureApi, CommonFeatureApi) -> WeatherDetailsFeatureDependencies
            ) : DependencyHolder2<AppFeatureApi, CommonFeatureApi, WeatherDetailsFeatureDependencies>(
                api1 = AppComponentHolder.get(),
                api2 = CommonComponentHolder.get()
            )

            WeatherDetailsComponentDependencyHolder { dependencyHolder, appApi: AppFeatureApi, commonApi: CommonFeatureApi ->
                object : WeatherDetailsFeatureDependencies {
                    override val dispatcherProvider: DispatcherProvider = commonApi.dispatcherProvider
                    override val weatherDetailsRepository: WeatherDetailsRepository = appApi.weatherOfflineRepositoryImpl
                    override val dependencyHolder: BaseDependencyHolder<out BaseFeatureDependencies> = dependencyHolder
                }
            }.dependencies
        }
    }
}