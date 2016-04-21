package pivotal.io.ankopirun.repositories

import com.firebase.client.*
import com.soikonomakis.rxfirebase.RxFirebase
import pivotal.io.ankopirun.models.Run
import rx.Observable
import rx.subscriptions.Subscriptions

class FirebaseRunRepository(val baseUrl: String) : RunRepository {

    override fun createRun(run: Run): Observable<Run> {
        val ref = Firebase("$baseUrl/runs")
        val runRef = ref.push()

        runRef.setValue(run)

        return observeCompleteEvent(runRef.child("createdAt"), ServerValue.TIMESTAMP)
                .flatMap { getRun(runRef.key) }
    }

    override fun lastRun(): Observable<Run> {
        val ref = Firebase("$baseUrl/runs")

        return observeSingleValue(ref)
                .map {
                    val run = it.children.last()
                    run.getValue(Run::class.java).apply {
                        id = run.key
                    }
                }.first()
    }

    override fun clockSkew(): Observable<Long> {
        val ref = Firebase("$baseUrl/.info/serverTimeOffset")

        return observeSingleValue(ref)
                .map { it.getValue(Long::class.java) }
    }

    override fun getRun(runUuid: String): Observable<Run> {
        val ref = Firebase("$baseUrl/runs/$runUuid")

        return observeSingleValue(ref)
                .map {
                    it.getValue(Run::class.java).copy(id = runUuid)
                }
    }

    override fun getAddedRuns(): Observable<Run> {
        val ref = Firebase("$baseUrl/runs")

        return RxFirebase.getInstance()
                .observeChildAdded(ref)
                .map {
                    it.dataSnapshot.getValue(Run::class.java).apply {
                        id = it.dataSnapshot.key
                    }
                }
                .onBackpressureBuffer()
    }
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

internal fun observeSingleValue(ref: Query): Observable<DataSnapshot> {
    return Observable.create { subscriber ->
        val listener = object : ValueEventListener {
            override fun onCancelled(error: FirebaseError) {
                subscriber.onError(error.toException())
            }

            override fun onDataChange(snapshot: DataSnapshot) {
                subscriber.onNext(snapshot)
                subscriber.onCompleted()
            }
        }

        ref.addValueEventListener(listener)

        subscriber.add(Subscriptions.create {
            ref.removeEventListener(listener)
        })
    }
}
