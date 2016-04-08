package pivotal.io.ankopirun.repositories

import pivotal.io.ankopirun.models.Run

interface RunRepository {

    fun create(run: Run)

    fun lastRun() :Run
}

