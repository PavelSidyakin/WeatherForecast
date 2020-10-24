package com.example.weatherforecast

import android.app.Application
import com.example.city_list.CityListApi
import com.example.city_list.CityListComponentHolder
import com.example.city_list.CityListDependencies
import com.example.city_list.CityListFragmentFactory
import com.example.city_list.CityListMonitor
import com.example.city_list.domain.GeneralSettingsRepository
import com.example.city_list.domain.WeatherOfflineRepository
import com.example.city_list.domain.WeatherOnlineRepository
import com.example.common.CommonApi
import com.example.common.CommonComponentHolder
import com.example.common.CommonDependencies
import com.example.common.coroutine_utils.DispatcherProvider
import com.example.common.logs.Logger
import com.example.common.time_utils.TimeProvider
import com.example.main_screen.MainScreenComponentHolder
import com.example.main_screen.MainScreenDependencies
import com.example.module_injector.BaseDependencies
import com.example.module_injector.BaseDependencyHolder
import com.example.module_injector.DependencyHolder0
import com.example.module_injector.DependencyHolder1
import com.example.module_injector.DependencyHolder2
import com.example.module_injector.DependencyHolder3
import com.example.weather_details.WeatherDetailsApi
import com.example.weather_details.WeatherDetailsComponentHolder
import com.example.weather_details.WeatherDetailsDependencies
import com.example.weather_details.WeatherDetailsFragmentFactory
import com.example.weather_details.domain.WeatherDetailsRepository
import com.example.weatherforecast.common.ApplicationProvider
import com.example.weatherforecast.common.ApplicationProviderImpl
import com.example.weatherforecast.di.app.AppApi
import com.example.weatherforecast.di.app.AppComponentHolder
import com.example.weatherforecast.di.app.AppDependencies


class TheApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        AppComponentHolder.dependencyProvider = {
            class AppComponentDependencyHolder(
                override val block: (BaseDependencyHolder<AppDependencies>) -> AppDependencies
            ) : DependencyHolder0<AppDependencies>()

            AppComponentDependencyHolder { deps ->
                object : AppDependencies {
                    override val appProvider: ApplicationProvider = ApplicationProviderImpl(this@TheApplication)
                    override val dependencyHolder: BaseDependencyHolder<out BaseDependencies> = deps
                }
            }.dependencies
        }

        CommonComponentHolder.dependencyProvider = {
            class CommonComponentDependencyHolder(
                override val block: (BaseDependencyHolder<CommonDependencies>, api1: AppApi) -> CommonDependencies
            ) : DependencyHolder1<AppApi, CommonDependencies>(api1 = AppComponentHolder.get())

            CommonComponentDependencyHolder { dependencyHolder, appApi ->
                object : CommonDependencies {
                    override val logger: Logger = appApi.logger
                    override val dependencyHolder: BaseDependencyHolder<out BaseDependencies> = dependencyHolder
                }
            }.dependencies
        }

        CityListComponentHolder.dependencyProvider = {
            class CityListComponentDependencyHolder(
                override val block: (BaseDependencyHolder<CityListDependencies>, AppApi, CommonApi) -> CityListDependencies
            ) : DependencyHolder2<AppApi, CommonApi, CityListDependencies>(
                api1 = AppComponentHolder.get(),
                api2 = CommonComponentHolder.get()
            )

            CityListComponentDependencyHolder { dependencyHolder, appApi: AppApi, commonApi: CommonApi ->
                object : CityListDependencies {
                    override val dispatcherProvider: DispatcherProvider = commonApi.dispatcherProvider
                    override val timeProvider: TimeProvider = commonApi.timeProvider
                    override val generalSettingsRepository: GeneralSettingsRepository = appApi.generalSettingsRepositoryImpl
                    override val weatherOfflineRepository: WeatherOfflineRepository = appApi.weatherOfflineRepositoryImpl
                    override val weatherOnlineRepository: WeatherOnlineRepository = appApi.weatherOnlineRepositoryImpl
                    override val dependencyHolder: BaseDependencyHolder<out BaseDependencies> = dependencyHolder
                }
            }.dependencies
        }

        WeatherDetailsComponentHolder.dependencyProvider = {
            class WeatherDetailsComponentDependencyHolder(
                override val block: (BaseDependencyHolder<WeatherDetailsDependencies>, AppApi, CommonApi) -> WeatherDetailsDependencies
            ) : DependencyHolder2<AppApi, CommonApi, WeatherDetailsDependencies>(
                api1 = AppComponentHolder.get(),
                api2 = CommonComponentHolder.get()
            )

            WeatherDetailsComponentDependencyHolder { dependencyHolder, appApi: AppApi, commonApi: CommonApi ->
                object : WeatherDetailsDependencies {
                    override val dispatcherProvider: DispatcherProvider = commonApi.dispatcherProvider
                    override val weatherDetailsRepository: WeatherDetailsRepository = appApi.weatherOfflineRepositoryImpl
                    override val dependencyHolder: BaseDependencyHolder<out BaseDependencies> = dependencyHolder
                }
            }.dependencies
        }

        MainScreenComponentHolder.dependencyProvider = {
            class MainScreenComponentDependencyHolder(
                override val block: (BaseDependencyHolder<MainScreenDependencies>, CityListApi, WeatherDetailsApi, CommonApi) -> MainScreenDependencies
            ) : DependencyHolder3<CityListApi, WeatherDetailsApi, CommonApi, MainScreenDependencies>(
                api1 = CityListComponentHolder.get(),
                api2 = WeatherDetailsComponentHolder.get(),
                api3 = CommonComponentHolder.get()
            )

            MainScreenComponentDependencyHolder { dependencyHolder, cityListApi: CityListApi, weatherDetailsApi: WeatherDetailsApi, commonApi: CommonApi ->
                object : MainScreenDependencies {
                    override val cityListFragmentFactory: CityListFragmentFactory = cityListApi.cityListFragmentFactory
                    override val weatherDetailsFragmentFactory: WeatherDetailsFragmentFactory = weatherDetailsApi.weatherDetailsFragmentFactory
                    override val cityListMonitor: CityListMonitor = cityListApi.cityListMonitor
                    override val dispatcherProvider: DispatcherProvider = commonApi.dispatcherProvider
                    override val dependencyHolder: BaseDependencyHolder<out BaseDependencies> = dependencyHolder
                }
            }.dependencies
        }
    }
}