package com.example.common

import com.example.common.coroutine_utils.DispatcherProvider
import com.example.common.di.CommonComponent
import com.example.common.logs.Logger
import com.example.common.time_utils.TimeProvider
import com.example.module_injector.BaseAPI
import com.example.module_injector.BaseDependencies
import com.example.module_injector.ComponentHolder
import java.lang.ref.WeakReference

object CommonComponentHolder : ComponentHolder<CommonApi, CommonDependencies> {

    internal lateinit var commonComponentRef: WeakReference<CommonComponent>

    override fun get(dependencyProvider: () -> CommonDependencies): CommonApi {
        var commonComponent: CommonComponent?

        synchronized(this) {
            if (commonComponentRef.get() == null) {
                commonComponentRef = WeakReference(CommonComponent.initAndGet(dependencyProvider()))
            }
            commonComponent = commonComponentRef.get()
        }

        return commonComponent ?: throw IllegalStateException("Component is not initialized")
    }
}

interface CommonDependencies : BaseDependencies {
    val logger: Logger
}

interface CommonApi : BaseAPI {
    val dispatcherProvider: DispatcherProvider
    val timeProvider: TimeProvider
}

