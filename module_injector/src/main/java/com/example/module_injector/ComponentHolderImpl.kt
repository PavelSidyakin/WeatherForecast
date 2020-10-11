package com.example.module_injector

import java.lang.ref.WeakReference

//class ComponentHolderDelegate<A : BaseAPI, D : BaseDependencies, C : A> : ComponentHolder<A, D> {
//
//    lateinit var componentRef: WeakReference<C>
//
//    override var dependencyProvider: (() -> D)? = null
//
//    override fun get(): A {
//        var component: C? = null
//
//        synchronized(this) {
//            dependencyProvider?.let { dependencyProvider ->
//                if (componentRef.get() == null) {
//                    componentRef =
//                        WeakReference(initAndGetComponent(dependencyProvider()))
//                }
//                component = componentRef.get()
//            } ?: dependencyProvider
//        }
//
//        return component ?: throw IllegalStateException("Component is not initialized")
//    }
//
//    //protected fun initAndGetComponent(dependencies: D): C
//}

