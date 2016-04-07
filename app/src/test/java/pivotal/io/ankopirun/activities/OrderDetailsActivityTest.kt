package pivotal.io.ankopirun.activities

import org.junit.Test
import org.mockito.Mockito.*
import org.robolectric.Robolectric
import pivotal.io.ankopirun.RobolectricTest
import pivotal.io.ankopirun.widgets.CountDownTimer

class OrderDetailsActivityTest: RobolectricTest() {

    @Test
    fun resumingActivityStartsCountDownTimer() {
        val activityController = Robolectric.buildActivity(OrderDetailsActivity::class.java)
        val activity = activityController.create().get()
        activity.countDownTimer = mock(CountDownTimer::class.java)
        activityController.resume()

        verify(activity.countDownTimer).start(anyLong())
    }
}