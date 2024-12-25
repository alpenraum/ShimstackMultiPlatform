package com.alpenraum.shimstack.base.di

import org.koin.core.annotation.ComponentScan
import org.koin.core.annotation.Module
import org.koin.core.parameter.parametersOf
import org.koin.core.scope.Scope

@Module
@ComponentScan("com.alpenraum.shimstack")
class ShimstackGeneratedModule

private inline fun <reified T> Scope.getWith(vararg params: Any?): T = get(parameters = { parametersOf(*params) })