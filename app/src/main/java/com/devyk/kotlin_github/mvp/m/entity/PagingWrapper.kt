package com.devyk.kotlin_github.mvp.m.entity


abstract class PagingWrapper<T> {

    abstract fun getElements(): List<T>


}