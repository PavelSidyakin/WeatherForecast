package com.example.city_list.di

import com.example.city_list.CityListFeatureDependencies
import com.example.city_list.CityListFragmentFactory
import com.example.city_list.CityListMonitor
import com.example.city_list.domain.CityListObserverImpl
import com.example.city_list.domain.CityListUpdater
import com.example.city_list.domain.WeatherInteractor
import com.example.city_list.domain.WeatherInteractorImpl
import com.example.city_list.presentation.city_list.presenter.CityListPresenter
import com.example.city_list.presentation.city_list.view.CityListFragmentFactoryImpl
import org.koin.core.Koin
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import org.koin.core.definition.BeanDefinition
import org.koin.core.definition.Definition
import org.koin.core.definition.Definitions
import org.koin.core.module.Module
import org.koin.core.qualifier.Qualifier
import org.koin.core.scope.ScopeDefinition
import org.koin.dsl.binds
import org.koin.dsl.koinApplication
import org.koin.dsl.module
import kotlin.reflect.KCallable
import kotlin.reflect.KClass
import kotlin.reflect.KProperty1

fun Module.singleWithKClass(
    qualifier: Qualifier? = null,
    createdAtStart: Boolean = false,
    override: Boolean = false,
    clazz: KClass<*>,
    definition: Definition<*>,
): BeanDefinition<*> {
    val options = makeOptions(override, createdAtStart)
    val def = Definitions.createSingle(clazz = clazz, qualifier, definition, options, scopeQualifier = ScopeDefinition.ROOT_SCOPE_QUALIFIER)
    (this::class.members.find { it.name == "definitions" }?.call(this) as HashSet<BeanDefinition<*>>).add(def)
    return def
}

internal class CityListComponentImpl(private val dependencies: CityListFeatureDependencies) : CityListComponent, KoinComponent {
    val module: Module = module {
        dependencies::class.members
            .filterIsInstance<KProperty1<*, *>>()
            .forEach { dependencyGetter: KCallable<*> ->
                singleWithKClass(clazz = dependencyGetter.returnType.classifier as KClass<*>) { dependencyGetter.call(dependencies) }
            }

        single { CityListFragmentFactoryImpl() as CityListFragmentFactory }
        single { WeatherInteractorImpl(get(), get(), get(), get()) as WeatherInteractor }
        single { CityListObserverImpl() } binds arrayOf(CityListMonitor::class, CityListUpdater::class)
        factory { CityListPresenter(get(), get(), get()) }    }

    override val cityListPresenter: CityListPresenter by inject()
    override val cityListFragmentFactory: CityListFragmentFactory by inject()
    override val cityListMonitor: CityListMonitor by inject()

    private val cityListKoinApp = koinApplication {
        modules(module)
    }

    override fun getKoin(): Koin {
        return cityListKoinApp.koin
    }
}