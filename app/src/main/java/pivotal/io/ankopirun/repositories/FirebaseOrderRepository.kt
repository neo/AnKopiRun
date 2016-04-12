package pivotal.io.ankopirun.repositories

import com.firebase.client.Firebase
import com.soikonomakis.rxfirebase.RxFirebase
import pivotal.io.ankopirun.models.Order
import rx.Observable

class FirebaseOrderRepository(val baseUrl: String) : OrderRepository {

    // TODO: need to filter by the run id
    override fun getOrders(): Observable<List<Order>> {
        // val ref = Firebase("$baseUrl/orders")
        // TODO: CHANGE THIS BACK!!!!!
        val ref = Firebase("https://kopi-run.firebaseio.com/orders")

        return RxFirebase.getInstance()
                .observeValueEvent(ref)
                .map { it.children.map { it.getValue(Order::class.java) } }
    }
}

