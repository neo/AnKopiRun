package pivotal.io.ankopirun.widgets.countdowntimer

import org.junit.Assert.assertEquals
import org.junit.Test
import pivotal.io.ankopirun.views.TimerView

class CountDownPresenterTest {

    @Test
    fun formatsTimeInMillisecondsToMinutesAndSeconds() {
        val presenter = Presenter()
        val remainingTimeInMilliseconds = 450992L

        assertEquals("7:30", presenter.format(remainingTimeInMilliseconds))
    }

    @Test
    fun formatsTimeInMillisecondsToAllZerosIfGivenValueIsNegative() {
        val presenter = Presenter()
        val remainingTimeInMilliseconds = -450992L

        assertEquals("0:00", presenter.format(remainingTimeInMilliseconds))
    }

    private class Presenter : CountDownPresenter {
        override var view: TimerView? = null
            set(value) {
                field = value
            }

        override fun startCountDown(durationInMilliseconds: Long) {
        }

        override fun stopCountDown() {
        }
    }
}
