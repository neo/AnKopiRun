package pivotal.io.ankopirun.repositories

import pivotal.io.ankopirun.models.Order
import pivotal.io.ankopirun.models.Run
import rx.Observable

interface RunRepository {

    fun createRun(run: Run): Observable<Run>

    fun lastRun(): Observable<Run>

    fun clockSkew(): Observable<Long>

    fun getOrders(): Observable<List<Order>>

    fun getRun(runUuid: String): Observable<Run>

    fun getRuns(): Observable<List<Run>>
}

