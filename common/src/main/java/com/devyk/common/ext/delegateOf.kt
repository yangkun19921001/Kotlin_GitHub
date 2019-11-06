package com.devyk.common.ext

import kotlin.jvm.internal.PropertyReference
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.*

fun <T> delegateOf(setter: ((T) -> Unit)? = null, defaultValue: T? = null): ReadWriteProperty<Any, T> =
    ObjectPropertyDelegateNoGetter0(setter, defaultValue)

fun <T> delegateOf(
    getter: (() -> T),
    setter: ((T) -> Unit)? = null,
    defaultValue: T? = null
): ReadWriteProperty<Any, T> = ObjectPropertyDelegate0(getter, setter, defaultValue)

fun <T, R> delegateWithReceiverOf(
    receiver: R,
    setter: ((R, T) -> Unit)? = null,
    defaultValue: T? = null
): ReadWriteProperty<Any, T> = ObjectPropertyDelegateNoGetter1(receiver, setter, defaultValue)

fun <T, R> delegateWithReceiverOf(
    receiver: R,
    getter: ((R) -> T),
    setter: ((R, T) -> Unit)? = null,
    defaultValue: T? = null
): ReadWriteProperty<Any, T> = ObjectPropertyDelegate1(receiver, getter, setter, defaultValue)

fun <T> KProperty0<T>.delegator(defaultValue: T? = null): ReadWriteProperty<Any, T> =
    ObjectPropertyDelegate0(propertyRef = this as PropertyReference, defaultValue = defaultValue)

fun <T, R> KProperty1<R, T>.delegator(receiver: R, defaultValue: T? = null): ReadWriteProperty<Any, T> =
    ObjectPropertyDelegate1(receiver, property = this, defaultValue = defaultValue)

@JvmName("delegatorGetter")
fun <T> KFunction0<T>.delegator(defaultValue: T? = null): ReadWriteProperty<Any, T> =
    ObjectPropertyDelegate0(this, defaultValue = defaultValue)

@JvmName("delegatorGetter")
fun <T, R> KFunction1<R, T>.delegator(receiver: R, defaultValue: T? = null): ReadWriteProperty<Any, T> =
    ObjectPropertyDelegate1(receiver, this, defaultValue = defaultValue)

fun <T> KFunction1<T, Unit>.delegator(defaultValue: T? = null): ReadWriteProperty<Any, T> =
    ObjectPropertyDelegateNoGetter0(setter = this, defaultValue = defaultValue)

fun <T, R> KFunction2<R, T, Unit>.delegator(receiver: R, defaultValue: T? = null): ReadWriteProperty<Any, T> =
    ObjectPropertyDelegateNoGetter1(receiver, setter = this, defaultValue = defaultValue)

private class ObjectPropertyDelegateNoGetter0<T>(val setter: ((T) -> Unit)? = null, defaultValue: T? = null) :
    ReadWriteProperty<Any, T> {
    private var value: T? = defaultValue

    init {
        defaultValue?.let { setter?.invoke(it) }
    }

    override operator fun getValue(thisRef: Any, property: KProperty<*>): T {
        return value!!
    }

    override operator fun setValue(thisRef: Any, property: KProperty<*>, value: T) {
        setter?.invoke(value)
        this.value = value
    }
}

private class ObjectPropertyDelegate0<T>(
    val getter: (() -> T),
    val setter: ((T) -> Unit)? = null,
    defaultValue: T? = null
) : ReadWriteProperty<Any, T> {

    constructor(propertyRef: PropertyReference, defaultValue: T? = null)
            : this(
        (propertyRef as KProperty0<T>)::get,
        if (propertyRef is KMutableProperty0<*>) (propertyRef as KMutableProperty0<T>)::set else null,
        defaultValue
    )

    init {
        defaultValue?.let { setter?.invoke(it) }
    }

    override operator fun getValue(thisRef: Any, property: KProperty<*>): T {
        return getter.invoke()
    }

    override operator fun setValue(thisRef: Any, property: KProperty<*>, value: T) {
        setter?.invoke(value)
    }
}

private class ObjectPropertyDelegateNoGetter1<T, R>(
    val receiver: R,
    val setter: ((R, T) -> Unit)? = null,
    defaultValue: T? = null
) : ReadWriteProperty<Any, T> {
    private var value: T? = defaultValue

    init {
        defaultValue?.let { setter?.invoke(receiver, it) }
    }

    override operator fun getValue(thisRef: Any, property: KProperty<*>): T {
        return value!!
    }

    override operator fun setValue(thisRef: Any, property: KProperty<*>, value: T) {
        setter?.invoke(receiver, value)
        this.value = value
    }
}

private class ObjectPropertyDelegate1<T, R>(
    val receiver: R,
    val getter: ((R) -> T),
    val setter: ((R, T) -> Unit)? = null,
    defaultValue: T? = null
) : ReadWriteProperty<Any, T> {

    constructor(target: R, property: KProperty1<R, T>, defaultValue: T? = null)
            : this(
        target,
        property,
        if (property is KMutableProperty1<*, *>) (property as KMutableProperty1<R, T>)::set else null,
        defaultValue
    )

    init {
        defaultValue?.let { setter?.invoke(receiver, it) }
    }

    override operator fun getValue(thisRef: Any, property: KProperty<*>): T {
        return getter.invoke(receiver)
    }

    override operator fun setValue(thisRef: Any, property: KProperty<*>, value: T) {
        setter?.invoke(receiver, value)
    }
}