package com.zyx2.ordersdemokt

import kotlin.collections.ArrayDeque

class EventStream: Subject() {
    private val events: ArrayDeque<String> = ArrayDeque<String>()

    override fun setState(message: String): Unit {
        events.add(message)
        this.notifyObservers()
    }

    override fun getState(): String {
        return events.last()
    }
}