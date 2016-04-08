package pivotal.io.ankopirun.widgets.countdowntimer

import org.junit.Assert.assertEquals
import org.junit.Test
import pivotal.io.ankopirun.models.Run

class CountDownCalculatorTest {

    @Test
    fun computesDurationInMilliseconds() {
        val lastRun = Run("Herp derp", "The Plain", duration = 600, startTime = 1460000000000)
        val currentTimeStamp = 1460000005000

        val presenter = CountDownCalculator(lastRun, currentTimeStamp)

        assertEquals(595000, presenter.durationInMilliseconds())
    }

}



