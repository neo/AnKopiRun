package pivotal.io.ankopirun.models

import org.junit.Assert.assertEquals
import org.junit.Test

class RunTest {
    @Test
    fun runIsInactiveWhenRunDurationHasPassed() {
        val run = Run(duration = 100, startTime = 10000000000)

        assertEquals(run.isInactive(10000099999, 0), false)
        assert(run.isInactive(10000200000, 0))
    }
}