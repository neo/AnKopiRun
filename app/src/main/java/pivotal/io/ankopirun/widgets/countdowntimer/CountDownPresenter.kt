package pivotal.io.ankopirun.widgets.countdowntimer

import pivotal.io.ankopirun.views.TimerView
import java.util.concurrent.TimeUnit

interface CountDownPresenter {

    var view: TimerView?

    fun startCountDown(durationInMilliseconds: Long)

    fun stopCountDown()

    fun format(milliseconds: Long): String {
        return String.format("%d:%02d",
                TimeUnit.MILLISECONDS.toMinutes(milliseconds),
                TimeUnit.MILLISECONDS.toSeconds(milliseconds) -
                        TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(milliseconds))
        );
    }

}

