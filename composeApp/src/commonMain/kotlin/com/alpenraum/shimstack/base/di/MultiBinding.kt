package com.alpenraum.shimstack.base.di

import org.koin.core.Koin
import org.koin.core.module.Module
import org.koin.core.qualifier.Qualifier
import org.koin.core.qualifier.named
import org.koin.dsl.onClose
import org.koin.ext.getFullName

class Multibinding<K, V> : MutableMap<K, V> by mutableMapOf()

inline fun <reified K, reified V> Module.declareMultibinding() {
    single(qualifier = multibindingQualifier<K, V>()) {
        Multibinding<K, V>()
    }
}

inline fun <reified K, reified V> multibindingQualifier(suffix: String? = null): Qualifier =
    named("${K::class.getFullName()}_${V::class.getFullName()}${suffix?.let { "_$it" }}")

inline fun <reified K, reified V> Module.intoMultibinding(
    key: K,
    value: V,
) {
    var multibinding: Multibinding<K, V>? = null
    single(
        qualifier = named("${K::class.getFullName()}_${V::class.getFullName()}_$key"),
        createdAtStart = true,
    ) {
        multibinding = get(multibindingQualifier<K, V>())
        multibinding!![key] = value
    }.onClose {
        multibinding?.remove(key)
    }
}

inline fun <reified K, reified V> Koin.getMultibinding(): Map<K, V> =
    get<Multibinding<K, V>>(qualifier = multibindingQualifier<K, V>()).toMap()
