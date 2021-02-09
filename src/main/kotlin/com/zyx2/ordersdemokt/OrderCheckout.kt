package com.zyx2.ordersdemokt

import java.math.BigDecimal

data class OrderCheckout(var items: Map<String, Int>, var total: BigDecimal) {

}
