package pivotal.io.ankopirun.presenters

import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.Mockito.verify
import pivotal.io.ankopirun.models.Run
import pivotal.io.ankopirun.views.TimerView
import pivotal.io.ankopirun.widgets.countdowntimer.CountDownCalculator
import pivotal.io.ankopirun.widgets.countdowntimer.CountDownTimer

class OrderDetailsPresenterTest {
    @Test
    fun startsTimerWithDuration() {
        val countDownTimer = mock(CountDownTimer::class.java)
        val view = mock(TimerView::class.java)
        val run = Run("Herp derp", "The Plain", duration = 4000, startTime = 1459999675460)
        val calculator = CountDownCalculator(run, 1459999678460)
        var presenter = OrderDetailsPresenter(view, countDownTimer, calculator)

        presenter.startCountdown()

        verify(countDownTimer).start(run.duration * 1000 - 3000)
    }
}