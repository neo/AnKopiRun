package pivotal.io.ankopirun.repositories

import pivotal.io.ankopirun.models.Order
import rx.Observable

interface OrderRepository {

    fun getAddedOrders(runUuid: String): Observable<Order>

    fun createOrder(order: Order)
}

