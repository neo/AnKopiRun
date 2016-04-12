package pivotal.io.ankopirun.widgets.countdowntimer

import java.util.concurrent.TimeUnit

interface CountDownPresenter {

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

