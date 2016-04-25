package pivotal.io.ankopirun.widgets.countdowntimer

interface CountDownTimer {

    fun setOnFinishHandler(handler: () -> Unit)

    fun addOnTickHandler(handler: (millisecondsLeft: Long) -> Unit)

    fun cancel()

    fun start(durationInMilliseconds: Long)
}

