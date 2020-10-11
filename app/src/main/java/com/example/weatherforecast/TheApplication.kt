package com.example.weatherforecast

import android.app.Application
import com.example.city_list.CityListComponentHolder
import com.example.city_list.CityListDependencies
import com.example.city_list.CityListFragmentFactory
import com.example.city_list.CityListMonitor
import com.example.city_list.domain.GeneralSettingsRepository
import com.example.city_list.domain.WeatherOfflineRepository
import com.example.city_list.domain.WeatherOnlineRepository
import com.example.common.CommonComponentHolder
import com.example.common.CommonDependencies
import com.example.common.coroutine_utils.DispatcherProvider
import com.example.common.logs.Logger
import com.example.common.time_utils.TimeProvider
import com.example.main_screen.MainScreenComponentHolder
import com.example.main_screen.MainScreenDependencies
import com.example.weather_details.WeatherDetailsComponentHolder
import com.example.weather_details.WeatherDetailsDependencies
import com.example.weather_details.WeatherDetailsFragmentFactory
import com.example.weather_details.domain.WeatherDetailsRepository
import com.example.weatherforecast.common.ApplicationProvider
import com.example.weatherforecast.common.LoggerImpl
import com.example.weatherforecast.data.GeneralSettingsRepositoryImpl
import com.example.weatherforecast.data.WeatherOfflineRepositoryImpl
import com.example.weatherforecast.data.WeatherOnlineRepositoryImpl
import com.example.weatherforecast.di.app.AppComponent
import com.example.weatherforecast.di.app.DaggerAppComponent
import javax.inject.Inject


class TheApplication : Application() {
    @Inject
    lateinit var applicationProvider: ApplicationProvider

    override fun onCreate() {
        super.onCreate()

        appComponent = DaggerAppComponent.builder()
            .build()

        appComponent.inject(this)

        applicationProvider.init(this)

        CommonComponentHolder.dependencyProvider = {
            object : CommonDependencies {
                override val logger: Logger = LoggerImpl()
            }
        }

        CityListComponentHolder.dependencyProvider = {
            object : CityListDependencies {
                override val dispatcherProvider: DispatcherProvider = CommonComponentHolder.get().dispatcherProvider
                override val timeProvider: TimeProvider = CommonComponentHolder.get().timeProvider
                override val generalSettingsRepository: GeneralSettingsRepository = GeneralSettingsRepositoryImpl(applicationProvider)
                override val weatherOfflineRepository: WeatherOfflineRepository = WeatherOfflineRepositoryImpl(applicationProvider)
                override val weatherOnlineRepository: WeatherOnlineRepository = WeatherOnlineRepositoryImpl()
            }
        }

        WeatherDetailsComponentHolder.dependencyProvider = {
            object : WeatherDetailsDependencies {
                override val dispatcherProvider: DispatcherProvider = CommonComponentHolder.get().dispatcherProvider
                override val weatherDetailsRepository: WeatherDetailsRepository = WeatherOfflineRepositoryImpl(applicationProvider)
            }
        }

        MainScreenComponentHolder.dependencyProvider = {
            object: MainScreenDependencies {
                override val cityListFragmentFactory: CityListFragmentFactory = CityListComponentHolder.get().cityListFragmentFactory
                override val weatherDetailsFragmentFactory: WeatherDetailsFragmentFactory = WeatherDetailsComponentHolder.get().weatherDetailsFragmentFactory
                override val cityListMonitor: CityListMonitor = CityListComponentHolder.get().cityListMonitor
                override val dispatcherProvider: DispatcherProvider = CommonComponentHolder.get().dispatcherProvider
            }
        }
    }

    companion object {
        private const val TAG = "TheApplication"

        private lateinit var appComponent: AppComponent

        fun getAppComponent(): AppComponent {
            return appComponent
        }
    }
}