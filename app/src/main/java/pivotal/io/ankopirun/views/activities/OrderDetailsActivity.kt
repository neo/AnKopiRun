package pivotal.io.ankopirun.views.activities


import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.widget.TextView
import org.jetbrains.anko.find
import pivotal.io.ankopirun.App
import pivotal.io.ankopirun.R
import pivotal.io.ankopirun.models.Order
import pivotal.io.ankopirun.repositories.OrderRepository
import pivotal.io.ankopirun.widgets.countdowntimer.CountDownPresenterImpl
import pivotal.io.ankopirun.repositories.RunRepository
import pivotal.io.ankopirun.views.OrderListView
import pivotal.io.ankopirun.views.TimerView
import pivotal.io.ankopirun.widgets.countdowntimer.CountDownCalculator
import pivotal.io.ankopirun.widgets.countdowntimer.CountDownPresenter
import pivotal.io.ankopirun.widgets.countdowntimer.CountDownTimer
import pivotal.io.ankopirun.widgets.orderlist.OrderListPresenter
import pivotal.io.ankopirun.widgets.orderlist.OrderListPresenterImpl
import rx.Scheduler
import rx.lang.kotlin.subscribeWith
import javax.inject.Inject
import javax.inject.Named

class OrderDetailsActivity : AppCompatActivity(), TimerView, OrderListView {
    val TAG = lazy { this.localClassName }

    lateinit var timerText: TextView

    @Inject
    lateinit var countDownTimer: CountDownTimer

    @Inject
    lateinit var runRepository: RunRepository

    @field:[Inject Named("io")]
    lateinit var io: Scheduler

    @field:[Inject Named("mainThread")]
    lateinit var mainThread: Scheduler

    lateinit var countDownPresenter: CountDownPresenter
    lateinit var orderListPresenter: OrderListPresenter

    @Inject
    lateinit var orderRepository: OrderRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_order_details)

        (application as App).component.inject(this)

        timerText = find(R.id.countdown_timer)

        // TODO: Dagger this.
        countDownPresenter = CountDownPresenterImpl(countDownTimer).apply {
            view = this@OrderDetailsActivity
        }

        // TODO: Dagger this.
        orderListPresenter = OrderListPresenterImpl(orderRepository, io, mainThread).apply {
            view = this@OrderDetailsActivity
        }
    }

    override fun onResume() {
        super.onResume()

        runRepository.clockSkew()
                .zipWith(runRepository.lastRun(), { clockSkew, run -> Pair(clockSkew, run) })
                .subscribeOn(io)
                .observeOn(mainThread)
                .subscribeWith {
                    onNext {
                        val (clockSkew, run) = it
                        val calculator = CountDownCalculator(run,
                                System.currentTimeMillis(),
                                clockSkew)
                        countDownPresenter.startCountDown(calculator.durationInMilliseconds())
                    }

                    onError {
                        Log.d(TAG.value, it.message)
                    }
                }

        orderListPresenter.populateOrderList()
    }

    override fun onPause() {
        super.onPause()
        countDownPresenter.stopCountDown()
    }

    override fun setTimerText(tick: Long) {
        // TODO: Don't allow negative value for timer
        timerText.text = countDownPresenter.format(tick)
    }

    override fun addOrder(order: Order) {
        Log.d("OHAI", order.toString())
    }

}
