package pivotal.io.ankopirun.widgets.countdowntimer

class CountDownTimerImpl(val tick: Long = 100) : CountDownTimer {

    private var onFinishHandler: (() -> Unit) = {}
    private var onTickHandlers: MutableList<((t: Long) -> Unit)> = mutableListOf()
    private var timer: android.os.CountDownTimer? = null

    override fun setOnFinishHandler(handler: () -> Unit) {
        onFinishHandler = handler
    }

    override fun addOnTickHandler(handler: (Long) -> Unit) {
        onTickHandlers.add(handler)
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
                onTickHandlers.forEach { it(millisUntilFinished) }
            }
        }.start()
    }
}
