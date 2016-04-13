package pivotal.io.ankopirun.views.activities

import android.content.Intent
import android.widget.Button
import android.widget.EditText
import org.jetbrains.anko.find
import org.junit.Assert.assertEquals
import org.junit.Test
import org.mockito.Mockito.*
import org.robolectric.Robolectric
import org.robolectric.internal.ShadowExtractor
import org.robolectric.shadows.ShadowActivity
import pivotal.io.ankopirun.R
import pivotal.io.ankopirun.RobolectricTest
import pivotal.io.ankopirun.models.Order
import pivotal.io.ankopirun.models.Run
import pivotal.io.ankopirun.repositories.OrderRepository
import pivotal.io.ankopirun.repositories.RunRepository
import pivotal.io.ankopirun.widgets.countdowntimer.CountDownPresenter
import rx.Observable
import rx.schedulers.Schedulers

class CreateOrderActivityTest : RobolectricTest() {

    @Test
    fun resumingActivityStartsCountDownTimer() {
        val activityController = Robolectric.buildActivity(CreateOrderActivity::class.java)
        val activity = activityController.create().get().apply {
            countDownPresenter = mock(CountDownPresenter::class.java)
            runRepository = mock(RunRepository::class.java).apply {
                `when`(lastRun()).thenReturn(Observable.just(Run()))
                `when`(clockSkew()).thenReturn(Observable.just(1))
            }
            mainThread = Schedulers.immediate()
            io = Schedulers.immediate()
        }

        verifyZeroInteractions(activity.countDownPresenter)
        activityController.resume()

        verify(activity.countDownPresenter).startCountDown(anyLong())
    }

    @Test
    fun clickingOKButtonCreatesAnOrder() {
        val activityController = Robolectric.buildActivity(CreateOrderActivity::class.java)
        val activity = activityController.create().get().apply {
            runRepository = mock(RunRepository::class.java).apply {
                `when`(lastRun()).thenReturn(Observable.just(Run(id = "runUuid")))
                `when`(clockSkew()).thenReturn(Observable.just(1))
            }
            mainThread = Schedulers.immediate()
            io = Schedulers.immediate()

            find<EditText>(R.id.initials).apply {
                setText("BT")
            }

            find<EditText>(R.id.order_description).apply {
                setText("Mushroom Swiss")
            }

            orderRepository = mock(OrderRepository::class.java)
        }

        activityController.resume()
        activity.find<Button>(R.id.create_btn).performClick()

        verify(activity.orderRepository).createOrder(Order("Mushroom Swiss", "BT", "runUuid"))
    }

    @Test
    fun clickingOKButtonGoesToOrderDetailsActivity() {
        val activityController = Robolectric.buildActivity(CreateOrderActivity::class.java)
        val activity = activityController.create().get().apply {
            runRepository = mock(RunRepository::class.java).apply {
                `when`(lastRun()).thenReturn(Observable.just(Run(id = "runUuid")))
                `when`(clockSkew()).thenReturn(Observable.just(1))
            }
            mainThread = Schedulers.immediate()
            io = Schedulers.immediate()

            find<EditText>(R.id.initials).apply {
                setText("BT")
            }

            find<EditText>(R.id.order_description).apply {
                setText("Mushroom Swiss")
            }

            orderRepository = mock(OrderRepository::class.java)
        }

        activityController.resume()
        activity.find<Button>(R.id.create_btn).performClick()

        val shadowActivity = ShadowExtractor.extract(activity) as ShadowActivity
        val actualIntent = shadowActivity.nextStartedActivity
        val expectedIntent = Intent(activity, OrderDetailsActivity::class.java)

        assertEquals(expectedIntent, actualIntent)
    }

    @Test
    fun pausingActivityStopsCountDownTimer() {
        val activityController = Robolectric.buildActivity(CreateOrderActivity::class.java)
        val activity = activityController.create().get().apply {
            countDownPresenter = mock(CountDownPresenter::class.java)
        }

        activityController.pause()

        verify(activity.countDownPresenter).stopCountDown()
    }
}
