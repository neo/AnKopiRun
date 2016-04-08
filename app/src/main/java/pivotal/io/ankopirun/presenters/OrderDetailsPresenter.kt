package pivotal.io.ankopirun.presenters

import pivotal.io.ankopirun.views.TimerView
import pivotal.io.ankopirun.widgets.countdowntimer.CountDownCalculator
import pivotal.io.ankopirun.widgets.countdowntimer.CountDownTimer

class OrderDetailsPresenter(val view: TimerView,
                            val countDownTimer: CountDownTimer,
                            val countDownCalculator: CountDownCalculator) {

    fun startCountdown() {
        countDownTimer.apply {
            setOnTickHandler { tick -> view.setTimerText(tick) }
            start(countDownCalculator.durationInMilliseconds())
        }
    }
}