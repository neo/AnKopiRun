package pivotal.io.ankopirun.views.activities


import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.widget.TextView
import org.jetbrains.anko.find
import pivotal.io.ankopirun.App
import pivotal.io.ankopirun.R
import pivotal.io.ankopirun.RUN_UUID
import pivotal.io.ankopirun.repositories.OrderRepository
import pivotal.io.ankopirun.repositories.RunRepository
import pivotal.io.ankopirun.views.OrderListRecyclerView
import pivotal.io.ankopirun.views.TimerView
import pivotal.io.ankopirun.widgets.countdowntimer.CountDownCalculator
import pivotal.io.ankopirun.widgets.countdowntimer.CountDownPresenter
import pivotal.io.ankopirun.widgets.countdowntimer.CountDownPresenterImpl
import pivotal.io.ankopirun.widgets.countdowntimer.CountDownTimer
import pivotal.io.ankopirun.widgets.orderlist.OrderListAdapter
import pivotal.io.ankopirun.widgets.orderlist.OrderListPresenter
import rx.Scheduler
import rx.lang.kotlin.subscribeWith
import javax.inject.Inject
import javax.inject.Named

class OrderDetailsActivity : AppCompatActivity(), TimerView {
    val TAG = lazy { this.localClassName }

    lateinit var timerText: TextView
    lateinit var orderList: OrderListRecyclerView

    lateinit var countDownPresenter: CountDownPresenter

    @Inject
    lateinit var orderListPresenter: OrderListPresenter

    @Inject
    lateinit var countDownTimer: CountDownTimer

    @Inject
    lateinit var runRepository: RunRepository

    @field:[Inject Named("io")]
    lateinit var io: Scheduler

    @field:[Inject Named("mainThread")]
    lateinit var mainThread: Scheduler

    @Inject
    lateinit var orderRepository: OrderRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_order_details)

        (application as App).component.inject(this)

        timerText = find(R.id.countdown_timer)
        orderList = find<OrderListRecyclerView>(R.id.order_list).apply {
            layoutManager = LinearLayoutManager(this@OrderDetailsActivity)
            setHasFixedSize(true)
            adapter = OrderListAdapter()
        }

        // TODO: Dagger this.
        countDownPresenter = CountDownPresenterImpl(countDownTimer).apply {
            view = this@OrderDetailsActivity
        }

        orderListPresenter.view = orderList
    }

    override fun onResume() {
        super.onResume()

        val runUuid = intent.extras.getString(RUN_UUID)

        runRepository.clockSkew()
                .zipWith(runRepository.getRun(runUuid), { clockSkew, run -> Pair(clockSkew, run) })
                .subscribeOn(io)
                .observeOn(mainThread)
                .subscribeWith {
                    onNext {
                        val (clockSkew, run) = it
                        val calculator = CountDownCalculator(run,
                                System.currentTimeMillis(),
                                clockSkew)
                        countDownPresenter.startCountDown(calculator.durationInMilliseconds())
                        orderListPresenter.populateOrderList(run.id)
                    }

                    onError {
                        Log.d(TAG.value, it.message)
                    }
                }
    }

    override fun onPause() {
        super.onPause()
        countDownPresenter.stopCountDown()
    }

    override fun setTimerText(tick: Long) {
        timerText.text = countDownPresenter.format(Math.max(0, tick))
    }
}
