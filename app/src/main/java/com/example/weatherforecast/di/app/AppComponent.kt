package com.example.weatherforecast.di.app

import com.example.weatherforecast.TheApplication
import dagger.Component
import javax.inject.Singleton

@Component(
    dependencies = [AppFeatureDependencies::class],
    modules = [ AppModule::class ]
)
@Singleton
interface AppComponent : AppFeatureApi {
    fun inject(theApplication: TheApplication)

    interface Builder {
        fun build(): AppComponent
    }

    companion object {
        fun initAndGet(dependencies: AppFeatureDependencies): AppComponent {
            return DaggerAppComponent.builder()
                .appFeatureDependencies(dependencies)
                .build()
        }

    }
}