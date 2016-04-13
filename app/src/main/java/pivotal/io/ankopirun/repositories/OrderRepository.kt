package pivotal.io.ankopirun.repositories

import pivotal.io.ankopirun.models.Order
import rx.Observable

interface OrderRepository {

    fun getOrders(runUuid: String): Observable<List<Order>>

    fun createOrder(order: Order)
}

