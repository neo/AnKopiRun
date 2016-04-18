package pivotal.io.ankopirun.widgets.countdowntimer

import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.Mockito.verify
import pivotal.io.ankopirun.models.Run
import pivotal.io.ankopirun.views.TimerView
import pivotal.io.ankopirun.widgets.countdowntimer.CountDownCalculator
import pivotal.io.ankopirun.widgets.countdowntimer.CountDownPresenterImpl
import pivotal.io.ankopirun.widgets.countdowntimer.CountDownTimer
import pivotal.io.ankopirun.widgets.mediaplayer.MediaPlayer

class CountDownPresenterImplTest {

    @Test
    fun startsTimerWithDurationAndUpdatesTimerDisplayWhenCountdownStarts() {
        val countDownTimer = mock(CountDownTimer::class.java)
        val mockView = mock(TimerView::class.java)
        val run = Run("Herp derp", "The Plain", duration = 4000, startTime = 1459999675460)
        val calculator = CountDownCalculator(run, 1459999678460)
        val mediaPlayer = mock(MediaPlayer::class.java)
        var presenter = CountDownPresenterImpl(countDownTimer, mediaPlayer).apply {
            view = mockView
        }

        presenter.startCountDown(calculator.durationInMilliseconds())

        val durationInMilliseconds = run.duration * 1000 - 3000
        verify(countDownTimer).start(durationInMilliseconds)
        verify(mockView).setTimerText(durationInMilliseconds)
    }
}