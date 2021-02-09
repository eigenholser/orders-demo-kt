package com.zyx2.ordersdemokt

abstract class Observer {
    protected abstract var subject: Subject
    abstract fun update(): Unit
}