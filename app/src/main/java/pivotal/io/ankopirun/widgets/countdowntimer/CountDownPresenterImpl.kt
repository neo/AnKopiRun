package pivotal.io.ankopirun.widgets.countdowntimer

import pivotal.io.ankopirun.views.TimerView
import pivotal.io.ankopirun.widgets.mediaplayer.MediaPlayer

class CountDownPresenterImpl(val countDownTimer: CountDownTimer, val mediaPlayer: MediaPlayer) : CountDownPresenter {

    override var view: TimerView? = null
        set(value) {
            field = value
        }

    override fun startCountDown(durationInMilliseconds: Long) {

        countDownTimer.apply {
            addOnTickHandler {
                tick -> view?.setTimerText(tick)
            }

            addOnTickHandler {
                tick -> if (tick < 18000) mediaPlayer.play()
            }

            setOnFinishHandler {
                mediaPlayer.stop()
            }

        }.start(durationInMilliseconds)
        view?.setTimerText(durationInMilliseconds)
    }

    override fun stopCountDown() {
        countDownTimer.cancel()
    }

}

