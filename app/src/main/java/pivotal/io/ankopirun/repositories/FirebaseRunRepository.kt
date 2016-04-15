package pivotal.io.ankopirun.repositories

import com.firebase.client.Firebase
import com.firebase.client.ServerValue
import com.soikonomakis.rxfirebase.RxFirebase
import pivotal.io.ankopirun.models.Order
import pivotal.io.ankopirun.models.Run
import rx.Observable
import rx.lang.kotlin.subscriber

class FirebaseRunRepository(val baseUrl: String) : RunRepository {

    override fun createRun(run: Run): Observable<Run> {
        val ref = Firebase("$baseUrl/runs")
        val runRef = ref.push()

        runRef.setValue(run)

        return observeCompleteEvent(runRef.child("startTime"), ServerValue.TIMESTAMP)
                .flatMap { getRun(runRef.key) }
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

    override fun getRun(runUuid: String): Observable<Run> {
        val ref = Firebase("$baseUrl/runs")
        val query = ref.orderByKey().equalTo(runUuid)

        return RxFirebase.getInstance()
                .observeValueEvent(query)
                .map { it.children.first().getValue(Run::class.java) }
    }

    internal fun observeCompleteEvent(ref: Firebase, value: Any): Observable<Boolean> {
        return Observable.create { subscriber ->
            ref.setValue(value, Firebase.CompletionListener { firebaseError, firebase ->
                if (firebaseError == null) {
                    subscriber.onNext(true)
                } else {
                    subscriber.onError(firebaseError.toException())
                }
            })
        }
    }

}