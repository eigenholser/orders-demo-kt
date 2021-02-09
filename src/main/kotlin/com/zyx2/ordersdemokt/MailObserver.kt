package com.zyx2.ordersdemokt

import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class MailObserver(override var subject: Subject): Observer() {

    init {
        this.subject = subject
        subject.attach(this)
    }

    public fun addMessage(message: String): Unit {
        subject.setState(message)
    }

    override fun update() {
        val currentTime = LocalDateTime.now()
        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS")
        val formatted = currentTime.format(formatter)
        println("MAIL SERVICE [$formatted]: ${subject.getState()}")
    }
}