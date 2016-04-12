package pivotal.io.ankopirun.presenters

import pivotal.io.ankopirun.views.TimerView
import pivotal.io.ankopirun.widgets.countdowntimer.CountDownCalculator
import pivotal.io.ankopirun.widgets.countdowntimer.CountDownPresenter
import pivotal.io.ankopirun.widgets.countdowntimer.CountDownTimer

class OrderDetailsPresenter(val countDownTimer: CountDownTimer) : CountDownPresenter {

    var view: TimerView? = null

    override fun startCountDown(durationInMilliseconds: Long) {
        countDownTimer.apply {
            setOnTickHandler {
                tick -> view?.setTimerText(tick)
            }
        }.start(durationInMilliseconds)
        view?.setTimerText(durationInMilliseconds)
    }

    override fun stopCountDown() {
        countDownTimer.cancel()
    }

}

