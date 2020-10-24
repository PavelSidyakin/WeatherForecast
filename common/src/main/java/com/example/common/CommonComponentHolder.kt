package com.example.common

import com.example.common.coroutine_utils.DispatcherProvider
import com.example.common.di.CommonComponent
import com.example.common.logs.Logger
import com.example.common.time_utils.TimeProvider
import com.example.module_injector.BaseFeatureAPI
import com.example.module_injector.BaseFeatureDependencies
import com.example.module_injector.ComponentHolder
import com.example.module_injector.ComponentHolderDelegate

object CommonComponentHolder : ComponentHolder<CommonFeatureApi, CommonFeatureDependencies> {
    private val componentHolderDelegate = ComponentHolderDelegate<
            CommonFeatureApi,
            CommonFeatureDependencies,
            CommonComponent> { dependencies: CommonFeatureDependencies ->
        CommonComponent.initAndGet(dependencies)
    }

    internal fun getComponent(): CommonComponent = componentHolderDelegate.getComponentImpl()

    override var dependencyProvider: (() -> CommonFeatureDependencies)? by componentHolderDelegate::dependencyProvider

    override fun get(): CommonFeatureApi = componentHolderDelegate.get()
}

interface CommonFeatureDependencies : BaseFeatureDependencies {
    val logger: Logger
}

interface CommonFeatureApi : BaseFeatureAPI {
    val dispatcherProvider: DispatcherProvider
    val timeProvider: TimeProvider
}

