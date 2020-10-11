package com.example.module_injector

interface ComponentHolder<C : BaseAPI, D : BaseDependencies> {
    var dependencyProvider: (() -> D)?
    fun get(): C
}

interface BaseDependencies

interface BaseAPI