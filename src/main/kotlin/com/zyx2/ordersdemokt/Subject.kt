package com.zyx2.ordersdemokt

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
        observers.forEach { it.update() }
    }
}