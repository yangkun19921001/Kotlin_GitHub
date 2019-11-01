package com.devyk.common.anno


@Retention(AnnotationRetention.BINARY)//注解被编译到二进制文件，但不会被加载到 JVM 中。 反射不可见。
@Target(AnnotationTarget.CLASS)//用于类上
annotation class PoKo //可以给一个类型进行注解，类、接口、对象、甚至注解类本身