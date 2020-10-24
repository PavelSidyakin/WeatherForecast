package com.example.common.di

import com.example.common.CommonFeatureApi
import com.example.common.CommonFeatureDependencies
import com.example.common.logs.Logger
import dagger.Component
import javax.inject.Singleton

@Component(dependencies = [CommonFeatureDependencies::class], modules = [CommonModule::class])
@Singleton
internal interface CommonComponent : CommonFeatureApi {

    val logger: Logger

    companion object {
        fun initAndGet(commonDependencies: CommonFeatureDependencies): CommonComponent {
            return DaggerCommonComponent.builder()
                .commonFeatureDependencies(commonDependencies)
                .build()
        }
    }
}
