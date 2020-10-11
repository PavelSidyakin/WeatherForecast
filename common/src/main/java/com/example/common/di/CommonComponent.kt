package com.example.common.di

import com.example.common.CommonApi
import com.example.common.CommonDependencies
import com.example.common.logs.Logger
import dagger.Component
import javax.inject.Singleton

@Component(dependencies = [CommonDependencies::class], modules = [CommonModule::class])
@Singleton
internal interface CommonComponent : CommonApi {

    val logger: Logger

    companion object {
        fun initAndGet(commonDependencies: CommonDependencies): CommonComponent {
            return DaggerCommonComponent.builder()
                .commonDependencies(commonDependencies)
                .build()
        }
    }
}
