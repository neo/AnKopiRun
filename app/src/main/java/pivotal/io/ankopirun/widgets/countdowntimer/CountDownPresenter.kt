package pivotal.io.ankopirun.widgets.countdowntimer

import pivotal.io.ankopirun.views.TimerView
import java.util.concurrent.TimeUnit

interface CountDownPresenter {

    var view: TimerView?

    fun startCountDown(durationInMilliseconds: Long)

    fun stopCountDown()

    fun format(milliseconds: Long): String {
        val minTime = if (milliseconds > 0) milliseconds else 0
        return String.format("%d:%02d",
                TimeUnit.MILLISECONDS.toMinutes(minTime),
                TimeUnit.MILLISECONDS.toSeconds(minTime) -
                        TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(minTime))
        );
    }

}

