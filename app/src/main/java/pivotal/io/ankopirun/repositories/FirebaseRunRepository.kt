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
                    val run = it.children.last()
                    run.getValue(Run::class.java).apply {
                        id = run.key
                    }
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

    override fun getRuns(): Observable<List<Run>> {
        val ref = Firebase("$baseUrl/runs")

        return RxFirebase.getInstance()
                .observeValueEvent(ref)
                .map {
                    snapshot ->
                    snapshot.children.map {
                        it.getValue(Run::class.java).apply { id = it.key }
                    }
                }
    }

}
