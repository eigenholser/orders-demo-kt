package com.zyx2.ordersdemokt

import org.springframework.stereotype.Service
import java.math.BigDecimal
import javax.annotation.PostConstruct

@Service
class OrderService {
    private val priceTable: HashMap<String, BigDecimal> = HashMap<String, BigDecimal>()

    @PostConstruct
    fun init(): Unit {
        priceTable["apple"] = "0.60".toBigDecimal()
        priceTable["orange"] = "0.25".toBigDecimal()
    }

    fun checkout(order: ShopOrder): OrderCheckout {
        var orderTotal: BigDecimal = BigDecimal.ZERO.setScale(2)
        val items: ArrayList<String> = ArrayList<String>()

        for (item in order.items) {
            if (priceTable.containsKey(item)) {
                orderTotal = orderTotal.add(priceTable[item])
                items.add(item)
            }
        }
        return OrderCheckout(items, orderTotal)
    }
}
