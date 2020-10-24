package com.example.module_injector

interface BaseDependencies {
    val dependencyHolder: BaseDependencyHolder<out BaseDependencies>
}

interface BaseAPI

interface ComponentHolder<A : BaseAPI, D : BaseDependencies> {
    var dependencyProvider: (() -> D)?
    fun get(): A
}

