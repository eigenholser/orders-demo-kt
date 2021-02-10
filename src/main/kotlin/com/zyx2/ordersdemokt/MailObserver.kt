package com.zyx2.ordersdemokt

class MailObserver(override var subject: Subject): Observer() {

    init {
        this.subject = subject
        subject.attach(this)
    }

    public fun addMessage(message: String): Unit {
        subject.setState(message)
    }

    override fun update() {
        println("MAIL SERVICE ${subject.getState()}")
    }
}