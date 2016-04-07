package pivotal.io.ankopirun.activities


import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.TextView
import org.jetbrains.anko.find
import pivotal.io.ankopirun.R
import pivotal.io.ankopirun.widgets.CountDownTimer
import pivotal.io.ankopirun.widgets.CountDownTimerImpl

class OrderDetailsActivity : AppCompatActivity() {
    lateinit var countDownTimer: CountDownTimer
    lateinit var timerText: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_order_details)

        timerText = find(R.id.countdown_timer)
        countDownTimer = CountDownTimerImpl(10)
    }

    override fun onResume() {
        super.onResume()
        startTimer()
    }

    fun startTimer() {
        countDownTimer.apply {
            setOnFinishHandler { timerText.text = "0" }
            setOnTickHandler { tick -> timerText.text = "$tick" }
        }.start(5 * 60 * 1000)
    }
}
