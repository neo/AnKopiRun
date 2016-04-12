package pivotal.io.ankopirun.repositories

import com.firebase.client.Firebase
import com.firebase.client.ServerValue
import com.soikonomakis.rxfirebase.RxFirebase
import pivotal.io.ankopirun.models.Order
import pivotal.io.ankopirun.models.Run
import rx.Observable

class FirebaseRunRepository(val baseUrl: String) : RunRepository {

    override fun create(run: Run) {
        val ref = Firebase("$baseUrl/runs")
        val runRef = ref.push()
        runRef.setValue(run)
        runRef.child("startTime").setValue(ServerValue.TIMESTAMP)
    }

    override fun lastRun(): Observable<Run> {
        val ref = Firebase("$baseUrl/runs")

        return RxFirebase.getInstance()
                .observeSingleValue(ref)
                .map {
                    it.children.last().getValue(Run::class.java)
                }.first()
    }

    override fun clockSkew(): Observable<Long> {
        val ref = Firebase("$baseUrl/.info/serverTimeOffset")

        return RxFirebase.getInstance()
                .observeValueEvent(ref)
                .map { it.getValue(Long::class.java) }
    }

    override fun getOrders(): Observable<List<Order>> {
        throw UnsupportedOperationException()
    }
}
