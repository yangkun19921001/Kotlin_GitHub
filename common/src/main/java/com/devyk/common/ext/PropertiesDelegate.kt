package com.devyk.common.ext

import java.io.File
import java.net.URL
import java.util.*
import kotlin.reflect.KClass
import kotlin.reflect.KProperty
import kotlin.reflect.full.isSubclassOf

/**
 * <pre>
 *     author  : devyk on 2019-10-30 18:21
 *     blog    : https://juejin.im/user/578259398ac2470061f3a3fb/posts
 *     github  : https://github.com/yangkun19921001
 *     mailbox : yang1001yk@gmail.com
 *     desc    : This is PropertiesDelegate 读取配置文件封装
 * </pre>
 */
class PropertiesDelegate(private val path: String) {





    /**
     * 利用 lateinit 关键字，允许初始化一个非空属性
     */
    private lateinit var url :URL




    /**
     * 将 Properties 对象委托给 properties 属性 ，通过 by lazy 来延迟加载
     */
    private val properties :Properties by lazy {
        val pro = Properties()
        /**
         * run 函数
         */
        url.run {

            /**
             * use 是 Closeable 的扩展函数，内部已经调用 try 捕获异常了
             */
            javaClass.getResourceAsStream(path).use {
                pro.load(it)
            }
            javaClass.getResource(path)
        }

        //要求最后一行返回 Properties 对象
        pro
    }



    operator fun <T> getValue(thisRef:Any , property : KProperty<*>):T{

        /**
         * 根据 key 拿到 properties 值
         */
        val  value = properties[property.name]

        /**
         * 拿到当前的描述符号类型
         */
        val classOfT  =property.returnType.classifier as KClass<*>

        return when {
            Boolean::class == classOfT -> value.toString().toBoolean()
            Number::class.isSubclassOf(classOfT) -> {
                classOfT.javaObjectType.getDeclaredMethod("parse${classOfT.simpleName}",String::class.java).invoke(null,value)
            }
            String::class == classOfT ->value
            else -> throw IllegalAccessException("Unsupported type.")
        } as  T // as
    }

    operator fun <T> setValue(thisRef:Any,property:KProperty<*>,value:T){
        properties[property.name] = value.toString()
        File(url.toURI()).outputStream().use {
            properties.store(it,"");
        }
    }


    /**
     * 定义一个抽象管理配置关键
     */
    abstract class AbsProperties(path: String) {
        protected val PROPERTIES = PropertiesDelegate(path)
    }

}