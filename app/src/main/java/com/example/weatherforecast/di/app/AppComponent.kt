package com.example.weatherforecast.di.app

import com.example.weatherforecast.TheApplication
import dagger.Component
import javax.inject.Singleton

@Component(
    dependencies = [AppDependencies::class],
    modules = [ AppModule::class ]
)
@Singleton
interface AppComponent : AppApi {
    fun inject(theApplication: TheApplication)

    interface Builder {
        fun build(): AppComponent
    }

    companion object {
        fun initAndGet(dependencies: AppDependencies): AppComponent {
            return DaggerAppComponent.builder()
                .appDependencies(dependencies)
                .build()
        }

    }
}