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

    private var componentRef: WeakReference<CommonComponent>? = null

    override var dependencyProvider: (() -> CommonDependencies)? = null

    internal fun getComponent(): CommonComponent {
        var component: CommonComponent? = null

        synchronized(this) {
            dependencyProvider?.let { dependencyProvider ->
                if (componentRef?.get() == null) {
                    componentRef =
                        WeakReference(CommonComponent.initAndGet(dependencyProvider()))
                }
                component = componentRef?.get()
            } ?: throw IllegalStateException("dependencyProvider is not initialized")
        }

        return component ?: throw IllegalStateException("Component is not initialized")
    }

    override fun get(): CommonApi {
        return getComponent()
    }
}

interface CommonDependencies : BaseDependencies {
    val logger: Logger
}

interface CommonApi : BaseAPI {
    val dispatcherProvider: DispatcherProvider
    val timeProvider: TimeProvider
}

