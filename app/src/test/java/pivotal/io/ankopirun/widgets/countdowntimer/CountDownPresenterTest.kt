package pivotal.io.ankopirun.widgets.countdowntimer

import org.junit.Assert.assertEquals
import org.junit.Test

class CountDownPresenterTest {

    @Test
    fun formatsTimeInMillisecondsToMinutesAndSeconds() {
        val presenter = CountDownPresenter()
        val remainingTimeInMilliseconds = 450992L

        assertEquals("7:30", presenter.format(remainingTimeInMilliseconds))
    }
}
