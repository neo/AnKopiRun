package pivotal.io.ankopirun.views.activities

import android.content.Intent
import android.view.View
import android.widget.TextView
import org.jetbrains.anko.find
import org.junit.Test
import org.mockito.Mockito.*
import org.robolectric.Robolectric
import pivotal.io.ankopirun.R
import pivotal.io.ankopirun.RUN
import pivotal.io.ankopirun.RobolectricTest
import pivotal.io.ankopirun.models.Run
import pivotal.io.ankopirun.repositories.RunRepository
import pivotal.io.ankopirun.widgets.countdowntimer.CountDownPresenter
import pivotal.io.ankopirun.widgets.orderlist.OrderListPresenter
import rx.Observable
import rx.schedulers.Schedulers
import org.junit.Assert.assertEquals
import org.robolectric.util.ActivityController

class OrderDetailsActivityTest : RobolectricTest() {

    @Test
    fun resumingActivityStartsCountDownTimer() {
        val intent = Intent().putExtra(RUN, Run(id = "id"))
        val activityController = Robolectric.buildActivity(OrderDetailsActivity::class.java).withIntent(intent)
        val activity = createActivityWithMocks(activityController)

        verifyZeroInteractions(activity.countDownPresenter)
        activityController.resume()

        verify(activity.countDownPresenter).startCountDown(anyLong())
    }

    @Test
    fun pausingActivityStopsCountDownTimer() {
        val activityController = Robolectric.buildActivity(OrderDetailsActivity::class.java)
        val activity = activityController.create().get().apply {
            countDownPresenter = mock(CountDownPresenter::class.java)
        }

        activityController.pause()

        verify(activity.countDownPresenter).stopCountDown()
    }

    @Test
    fun resumingActivityUpdatesOrderList() {
        val intent = Intent().putExtra(RUN, Run(id = "id"))
        val activityController = Robolectric.buildActivity(OrderDetailsActivity::class.java).withIntent(intent)
        val activity = createActivityWithMocks(activityController)

        activityController.resume()

        verify(activity.orderListPresenter).listen("id")
    }

    @Test
    fun resumingActivityOnAnInactiveRunHidesIncomingOrdersTitle() {
        val inactiveRun = Run(createdAt = 10)
        val intent = Intent().putExtra(RUN, inactiveRun)
        val activityController = Robolectric.buildActivity(OrderDetailsActivity::class.java).withIntent(intent)
        val activity = createActivityWithMocks(activityController)

        activityController.resume()

        var subtitle = activity.find<TextView>(R.id.subtitle)
        assertEquals(subtitle.visibility, View.INVISIBLE)
    }

    fun createActivityWithMocks(controller: ActivityController<OrderDetailsActivity>): OrderDetailsActivity {
        return controller.create().get().apply {
            countDownPresenter = mock(CountDownPresenter::class.java)
            runRepository = mock(RunRepository::class.java).apply {
                `when`(getRun("id")).thenReturn(Observable.just(Run("id")))
                `when`(clockSkew()).thenReturn(Observable.just(1))
            }
            orderListPresenter = mock(OrderListPresenter::class.java)
            mainThread = Schedulers.immediate()
            io = Schedulers.immediate()
        }
    }
}
