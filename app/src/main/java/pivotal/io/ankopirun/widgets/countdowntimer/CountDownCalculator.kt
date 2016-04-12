package pivotal.io.ankopirun.widgets.countdowntimer

import pivotal.io.ankopirun.models.Run

class CountDownCalculator(val run: Run, val currentTimeStamp: Long, val clockSkew: Long = 0) {

    fun durationInMilliseconds(): Long {
        return run.duration * 1000 - ((currentTimeStamp - clockSkew) - run.startTime)
    }

}