package pivotal.io.ankopirun.views.activities


import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.TextView
import org.jetbrains.anko.find
import pivotal.io.ankopirun.App
import pivotal.io.ankopirun.R
import pivotal.io.ankopirun.presenters.OrderDetailsPresenter
import pivotal.io.ankopirun.repositories.RunRepository
import pivotal.io.ankopirun.views.TimerView
import pivotal.io.ankopirun.widgets.CountDownTimer
import javax.inject.Inject

class OrderDetailsActivity : AppCompatActivity(), TimerView {

    lateinit var timerText: TextView

    @Inject
    lateinit var countDownTimer : CountDownTimer

    @Inject
    lateinit var runRepository : RunRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_order_details)

        (application as App).component.inject(this)

        timerText = find(R.id.countdown_timer)

        val presenter = OrderDetailsPresenter(this, countDownTimer, runRepository)
    }

    override fun setTimerText(tick: Long) {
        timerText.text = "$tick"
    }
}
