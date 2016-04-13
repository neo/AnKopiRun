package pivotal.io.ankopirun.widgets.countdowntimer

import org.junit.Assert.assertEquals
import org.junit.Test

class CountDownPresenterTest {

    @Test
    fun formatsTimeInMillisecondsToMinutesAndSeconds() {
        class Presenter : CountDownPresenter {
            override fun startCountDown(durationInMilliseconds: Long) {
            }

            override fun stopCountDown() {
            }
        }

        val presenter = Presenter()
        val remainingTimeInMilliseconds = 450992L

        assertEquals("7:30", presenter.format(remainingTimeInMilliseconds))
    }

}
