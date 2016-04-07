package pivotal.io.ankopirun.widgets

class CountDownTimerImpl(val tick: Long) : CountDownTimer {

    private var onFinishHandler: (() -> Unit) = {}
    private var onTickHandler: ((t: Long) -> Unit) = { t -> Unit }
    private var timer: android.os.CountDownTimer? = null

    override fun setOnFinishHandler(handler: () -> Unit) {
        onFinishHandler = handler
    }

    override fun setOnTickHandler(handler: (Long) -> Unit) {
        onTickHandler = handler
    }


    override fun cancel() {
        timer?.cancel()
    }

    override fun start(durationInMilliseconds: Long) {
        timer = object : android.os.CountDownTimer(durationInMilliseconds, tick) {
            override fun onFinish() {
                onFinishHandler()
            }

            override fun onTick(millisUntilFinished: Long) {
                onTickHandler(millisUntilFinished)
            }
        }.start()
    }
}
