package pivotal.io.ankopirun.widgets.countdowntimer

import org.junit.Assert.assertEquals
import org.junit.Test
import pivotal.io.ankopirun.models.Run

class CountDownCalculatorTest {

    @Test
    fun computesDurationInMilliseconds() {
        val lastRun = Run("Herp derp", "The Plain", duration = 600, startTime = 1460000000000)
        val currentTimeStamp = 1460000005000

        val countDownCalculator = CountDownCalculator(lastRun, currentTimeStamp)

        assertEquals(595000, countDownCalculator.durationInMilliseconds())
    }

    @Test
    fun computesRemainingDurationWithSkew() {
        val serverTime = 1460000000000L
        val localTime = 1460010000000L
        val skew = -10000000L
        val lastRun = Run("Herp derp", "The Plain", duration = 600, startTime = serverTime)
        val currentTimeStamp = localTime

        val countDownCalculator = CountDownCalculator(lastRun, currentTimeStamp, skew)

        assertEquals(600000, countDownCalculator.durationInMilliseconds())
    }
}



