package pivotal.io.ankopirun.widgets.countdowntimer

import pivotal.io.ankopirun.views.TimerView

class CountDownPresenterImpl(val countDownTimer: CountDownTimer) : CountDownPresenter {

    override var view: TimerView? = null
        set(value) {
            field = value
        }

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

