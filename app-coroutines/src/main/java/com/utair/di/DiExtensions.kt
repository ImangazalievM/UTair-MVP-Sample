package com.utair.di

import com.utair.DI
import toothpick.Scope
import toothpick.Toothpick
import toothpick.config.Binding
import toothpick.config.Module
import kotlin.reflect.KClass

fun Any.objectScopeName() = "${javaClass.simpleName}_${hashCode()}"

fun getAppScope(): Scope = Toothpick.openScope(DI.APP_SCOPE)

inline fun <reified T>  Scope.get(): T = getInstance(T::class.java)

inline fun <reified T>  getGlobal(): T = getAppScope().get()

internal fun <T: Any> Module.bind(key: KClass<T>): Binding<T> {
    val binding = Binding(key.java)
    bindingSet.add(binding)
    return binding
}

internal fun <T: Any> Binding<T>.to(kclass: KClass<out T>): Binding<T>.BoundStateForClassBinding {
    return this.to(kclass.java)
}

fun Scope.installModule(moduleInstalling: Module.() -> Unit) {
    val module = object : Module() {}
    moduleInstalling(module)
    installModules(module)
}

inline fun <reified Name : Annotation, reified T> Module.namedBind(value: T) {
    bind(T::class.java)
            .withName(Name::class.java)
            .toInstance(value)
}

fun <T> Module.bindPrimitive(annotation: KClass<out Annotation>, value: T) {
    bind(PrimitiveWrapper::class.java)
            .withName(annotation.java)
            .toInstance(PrimitiveWrapper(value))
}

fun <T> Module.bindPrimitive(name: String, value: T) {
    bind(PrimitiveWrapper::class.java)
            .withName(name)
            .toInstance(PrimitiveWrapper(value))
}