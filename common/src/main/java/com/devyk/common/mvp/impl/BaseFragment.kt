package com.bennyhuo.mvp.impl

import android.os.Bundle
import androidx.fragment.app.Fragment
import com.bennyhuo.mvp.IMvpView
import com.bennyhuo.mvp.IPresenter
import java.lang.reflect.ParameterizedType
import java.lang.reflect.Type
import kotlin.reflect.KClass
import kotlin.reflect.full.isSubclassOf
import kotlin.reflect.full.primaryConstructor
import kotlin.reflect.jvm.jvmErasure

abstract class BaseFragment<out P: BasePresenter<BaseFragment<P>>>: IMvpView<P>, Fragment() {
    override val p: P

    init {
        p = createpKt()
        p.v = this
    }

    private fun createpKt(): P {
        sequence {
            var thisClass: KClass<*> = this@BaseFragment::class
            while (true){
                yield(thisClass.supertypes)
                thisClass = thisClass.supertypes.firstOrNull()?.jvmErasure?: break
            }
        }.flatMap {
            it.flatMap { it.arguments }.asSequence()
        }.first {
            it.type?.jvmErasure?.isSubclassOf(IPresenter::class) ?: false
        }.let {
            return it.type!!.jvmErasure.primaryConstructor!!.call() as P
        }
    }

    private fun createp(): P {
        sequence<Type> {
            var thisClass: Class<*> = this@BaseFragment.javaClass
            while (true) {
                yield(thisClass.genericSuperclass!!)
                thisClass = thisClass.superclass ?: break
            }
        }.filter {
            it is ParameterizedType
        }.flatMap {
            (it as ParameterizedType).actualTypeArguments.asSequence()
        }.first {
            it is Class<*> && IPresenter::class.java.isAssignableFrom(it)
        }.let {
            return (it as Class<P>).newInstance()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        p.onCreate(savedInstanceState)
    }

    override fun onStart() {
        super.onStart()
        p.onStart()
    }

    override fun onResume() {
        super.onResume()
        p.onResume()
    }

    override fun onPause() {
        super.onPause()
        p.onPause()
    }

    override fun onStop() {
        super.onStop()
        p.onStop()
    }

    override fun onDestroy() {
        p.onDestroy()
        super.onDestroy()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        p.onSaveInstanceState(outState)
    }

    override fun onViewStateRestored(savedInstanceState: Bundle?) {
        super.onViewStateRestored(savedInstanceState)
        p.onViewStateRestored(savedInstanceState)
    }
}