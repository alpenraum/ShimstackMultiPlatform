package com.alpenraum.shimstack.base.di

import com.alpenraum.shimstack.Greeting
import com.alpenraum.shimstack.base.logger.ShimstackLogger
import org.koin.core.parameter.parametersOf
import org.koin.core.scope.Scope
import org.koin.dsl.module

fun shimstackModule() =
    module {
        single { Greeting(get()) }

        factory { ShimstackLogger(tag = null) }
    }

private inline fun <reified T> Scope.getWith(vararg params: Any?): T = get(parameters = { parametersOf(*params) })
