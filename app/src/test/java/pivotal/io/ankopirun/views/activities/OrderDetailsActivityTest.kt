package pivotal.io.ankopirun.views.activities

import org.junit.Test
import org.mockito.Mockito.*
import org.robolectric.Robolectric
import pivotal.io.ankopirun.RobolectricTest
import pivotal.io.ankopirun.models.Order
import pivotal.io.ankopirun.models.Run
import pivotal.io.ankopirun.repositories.RunRepository
import pivotal.io.ankopirun.widgets.countdowntimer.CountDownPresenter
import pivotal.io.ankopirun.widgets.orderlist.OrderListPresenter
import rx.Observable
import rx.schedulers.Schedulers

class OrderDetailsActivityTest : RobolectricTest() {

    @Test
    fun resumingActivityStartsCountDownTimer() {
        val activityController = Robolectric.buildActivity(OrderDetailsActivity::class.java)
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
    fun pausingActivityStopsCountDownTimer() {
        val activityController = Robolectric.buildActivity(OrderDetailsActivity::class.java)
        val activity = activityController.create().get().apply {
            countDownPresenter = mock(CountDownPresenter::class.java)
        }

        activityController.pause()

        verify(activity.countDownPresenter).stopCountDown()
    }

    @Test
    fun resumingActivityStartsGetsOrders() {
        val listOfOrders = listOf(Order(), Order(), Order())
        val activityController = Robolectric.buildActivity(OrderDetailsActivity::class.java)
        val activity = activityController.create().get().apply {
            countDownPresenter = mock(CountDownPresenter::class.java)
            runRepository = mock(RunRepository::class.java).apply {
                `when`(lastRun()).thenReturn(Observable.just(Run()))
                `when`(clockSkew()).thenReturn(Observable.just(1))
                `when`(getOrders()).thenReturn(Observable.just(listOfOrders))
            }
            orderListPresenter = mock(OrderListPresenter::class.java)
            mainThread = Schedulers.immediate()
            io = Schedulers.immediate()
        }

        activityController.resume()

        verify(activity.orderListPresenter).populateOrderList()
    }
}