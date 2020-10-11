package com.example.common

import com.example.common.coroutine_utils.DispatcherProvider
import com.example.common.di.CommonComponent
import com.example.common.logs.Logger
import com.example.common.time_utils.TimeProvider
import com.example.module_injector.BaseAPI
import com.example.module_injector.BaseDependencies
import com.example.module_injector.ComponentHolder
import com.example.module_injector.ComponentHolderDelegate

object CommonComponentHolder : ComponentHolder<CommonApi, CommonDependencies> {
    private val componentHolderDelegate = ComponentHolderDelegate<
            CommonApi,
            CommonDependencies,
            CommonComponent> { dependencies: CommonDependencies ->
        CommonComponent.initAndGet(dependencies)
    }

    internal fun getComponent(): CommonComponent = componentHolderDelegate.getComponentImpl()

    override var dependencyProvider: (() -> CommonDependencies)? by componentHolderDelegate::dependencyProvider

    override fun get(): CommonApi = componentHolderDelegate.get()
}

interface CommonDependencies : BaseDependencies {
    val logger: Logger
}

interface CommonApi : BaseAPI {
    val dispatcherProvider: DispatcherProvider
    val timeProvider: TimeProvider
}

