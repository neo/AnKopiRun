package pivotal.io.ankopirun.views.activities

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import org.jetbrains.anko.find
import org.jetbrains.anko.startActivity
import pivotal.io.ankopirun.App
import pivotal.io.ankopirun.R
import pivotal.io.ankopirun.RUN
import pivotal.io.ankopirun.models.Order
import pivotal.io.ankopirun.models.Run
import pivotal.io.ankopirun.repositories.OrderRepository
import pivotal.io.ankopirun.repositories.RunRepository
import pivotal.io.ankopirun.views.TimerView
import pivotal.io.ankopirun.widgets.countdowntimer.CountDownCalculator
import pivotal.io.ankopirun.widgets.countdowntimer.CountDownPresenter
import pivotal.io.ankopirun.widgets.countdowntimer.CountDownTimer
import rx.Scheduler
import rx.lang.kotlin.subscribeWith
import javax.inject.Inject
import javax.inject.Named

class CreateOrderActivity : AppCompatActivity(), TimerView {
    val TAG = lazy { this.localClassName }

    @field:[Inject Named("io")]
    lateinit var io: Scheduler

    @field:[Inject Named("mainThread")]
    lateinit var mainThread: Scheduler

    @Inject
    lateinit var runRepository: RunRepository

    @Inject
    lateinit var countDownTimer: CountDownTimer

    @Inject
    lateinit var orderRepository: OrderRepository

    @Inject
    lateinit var countDownPresenter: CountDownPresenter

    lateinit var initials: EditText
    lateinit var orderDescription: EditText
    lateinit var createOrder: Button
    lateinit var timerText: TextView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_order)

        (application as App).component.inject(this)

        timerText = find(R.id.countdown_timer)

        initials = find<EditText>(R.id.initials)
        orderDescription = find<EditText>(R.id.order_description)
        createOrder = find<Button>(R.id.create_btn).apply {
            isEnabled = false
        }

        countDownPresenter.view = this@CreateOrderActivity
    }

    override fun onResume() {
        super.onResume()

        val run = intent.extras.getSerializable(RUN) as Run

        runRepository.clockSkew()
                .subscribeOn(io)
                .observeOn(mainThread)
                .subscribeWith {
                    onNext {
                        val calculator = CountDownCalculator(run, System.currentTimeMillis(), it)
                        countDownPresenter.startCountDown(calculator.durationInMilliseconds())
                        setupCreateOrderButton(run)
                    }

                    onError {
                        Log.d(TAG.value, it.message)
                    }
                }

    }

    private fun setupCreateOrderButton(run: Run) {
        createOrder.apply {
            isEnabled = true
            setOnClickListener {
                orderRepository.createOrder(
                        Order(orderDescription.text.toString(),
                                initials.text.toString(),
                                runUuid = run.id)
                )

                startActivity<OrderDetailsActivity>(RUN to run)
            }
        }
    }

    override fun onPause() {
        super.onPause()
        countDownPresenter.stopCountDown()
    }

    override fun setTimerText(tick: Long) {
        timerText.text = countDownPresenter.format(tick)
    }

}
