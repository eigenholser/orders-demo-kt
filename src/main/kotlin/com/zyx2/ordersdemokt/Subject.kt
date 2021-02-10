package com.zyx2.ordersdemokt

import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

abstract class Subject {
    private val observers: MutableList<Observer> = ArrayList<Observer>()

    abstract fun setState(state: String): Unit
    abstract fun getState(): String

    public fun attach(observer: Observer): Unit {
        observers.add(observer)
    }

    public fun detach(observer: Observer): Unit {
        observers.remove(observer)
    }

    fun notifyObservers(): Unit {
        println(observers)
        observers.forEach { it.update() }
    }

    fun timestamp(): String {
        val currentTime = LocalDateTime.now()
        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS")
        return currentTime.format(formatter)
    }
}