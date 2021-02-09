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
    private val SIMPLE_OFFER = true

    @PostConstruct
    fun init(): Unit {
        priceTable["apple"] = "0.60".toBigDecimal()
        priceTable["orange"] = "0.25".toBigDecimal()

        priceDiscountTable["apple"] = 0.50f
        priceDiscountTable["orange"] = 0.67f

        discountThresholdTable["apple"] = 2
        discountThresholdTable["orange"] = 3
    }

    fun checkout(order: ShopOrder): OrderCheckout {
        val items =
                order.items.filter {
                    priceTable.contains(it)
                }.toList()
        val cart = items.distinct().map { it to 0 }.toMap().toMutableMap()
        items.map { cart[it]?.plus(1)?.let { it1 -> cart.put(it, it1) } }

        val orderSubtotals = cart.map { computeSubtotal(it.key, it.value) }
        val orderTotal = orderSubtotals.fold(BigDecimal.ZERO, BigDecimal::add)

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
}
