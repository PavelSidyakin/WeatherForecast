package com.example.module_injector

interface ComponentHolder<C : BaseAPI, D : BaseDependencies> {
    fun get(dependencyProvider: () -> D): C
}

interface BaseDependencies

interface BaseAPI