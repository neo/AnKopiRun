package pivotal.io.ankopirun.repositories

import com.firebase.client.Firebase
import com.soikonomakis.rxfirebase.RxFirebase
import pivotal.io.ankopirun.models.Order
import rx.Observable

class FirebaseOrderRepository(val baseUrl: String) : OrderRepository {
    override fun getAddedOrders(runUuid: String): Observable<Order> {
        val ref = Firebase("$baseUrl/orders")
        val query = ref.orderByChild("runUuid").equalTo(runUuid)

        return RxFirebase.getInstance()
                .observeChildAdded(query)
                .map { it.dataSnapshot.getValue(Order::class.java) }
    }

    override fun createOrder(order: Order) {
        val ref = Firebase("$baseUrl/orders")
        val orderRef = ref.push()
        orderRef.setValue(order)
    }
}

