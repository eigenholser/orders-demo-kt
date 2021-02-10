package com.zyx2.ordersdemokt

import org.springframework.stereotype.Service
import java.math.BigDecimal
import javax.annotation.PostConstruct
import kotlin.collections.HashMap

@Service
class OrderService {
    private val priceTable = HashMap<String, BigDecimal>()
    private val priceDiscountTable = HashMap<String, Float>()
    private val discountThresholdTable = HashMap<String, Int>()
    private val inventoryTable = HashMap<String, Int>()
    private val SIMPLE_OFFER = true
    private var mailObserver: MailObserver? = null
    private var eventObserver: EventObserver? = null

    @PostConstruct
    fun init(): Unit {
        priceTable["apple"] = "0.60".toBigDecimal()
        priceTable["orange"] = "0.25".toBigDecimal()

        priceDiscountTable["apple"] = 0.50f
        priceDiscountTable["orange"] = 0.67f

        discountThresholdTable["apple"] = 2
        discountThresholdTable["orange"] = 3

        inventoryTable["apple"] = 10
        inventoryTable["orange"] = 10

        val eventStream = EventStream()
        eventObserver = EventObserver(eventStream)
        mailObserver = MailObserver(eventStream)

    }

    fun checkout(order: ShopOrder): OrderCheckout {
        val items =
                order.items.filter {
                    priceTable.contains(it)
                }.toList()
        val cart = items.distinct().map { it to 0 }.toMap().toMutableMap()
        items.map { cart[it]?.plus(1)?.let { it1 -> cart.put(it, it1) } }

        if (!checkInventory(cart)) {
            mailObserver?.addMessage("Order Failed: Insufficient Inventory: $cart")
            throw Exception("Insufficient Inventory: $cart")
        }

        adjustInventory(cart)

        val orderSubtotals = cart.map { computeSubtotal(it.key, it.value) }
        val orderTotal = orderSubtotals.fold(BigDecimal.ZERO, BigDecimal::add)
        mailObserver?.addMessage("Order Successful: $cart, \$$orderTotal")
        return OrderCheckout(cart, orderTotal)
    }

    fun computeSubtotal(item: String, quantity: Int): BigDecimal {
        var subtotal: BigDecimal = BigDecimal.ZERO
        if (SIMPLE_OFFER) {
            val discountBundles = (quantity / discountThresholdTable[item]!!).toInt()
            val remainder = quantity % discountThresholdTable[item]!!
            subtotal = priceTable[item]?.multiply(remainder.toBigDecimal())!!
                    .add(priceTable[item]?.multiply(discountBundles.toBigDecimal())!!)
        } else {
            subtotal = priceTable[item]?.multiply(quantity.toBigDecimal())!!
        }
        return subtotal
    }

    fun checkInventory(cart: MutableMap<String, Int>): Boolean {
        val availableItems = cart.filterKeys { inventoryTable[it]!!.compareTo(cart[it]!!) > -1 }
        return availableItems.size == cart.size
    }

    fun adjustInventory(cart: MutableMap<String, Int>): Unit {
        for ((k, v) in cart) {
            inventoryTable[k]?.minus(v)?.let { it -> inventoryTable.put(k, it) }
        }
    }
}


















