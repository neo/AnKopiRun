package pivotal.io.ankopirun.repositories

import pivotal.io.ankopirun.models.Run
import rx.Observable

interface RunRepository {

    fun create(run: Run)

    fun lastRun(): Observable<Run>

    fun clockSkew(): Observable<Long>
}

