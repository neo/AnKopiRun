package pivotal.io.ankopirun.widgets.countdowntimer

import pivotal.io.ankopirun.views.TimerView
import pivotal.io.ankopirun.widgets.countdowntimer.CountDownCalculator
import pivotal.io.ankopirun.widgets.countdowntimer.CountDownPresenter
import pivotal.io.ankopirun.widgets.countdowntimer.CountDownTimer

class CountDownPresenterImpl(val countDownTimer: CountDownTimer) : CountDownPresenter {

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
