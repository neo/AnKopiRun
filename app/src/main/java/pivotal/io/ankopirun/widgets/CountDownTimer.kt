package pivotal.io.ankopirun.widgets

interface CountDownTimer {
    fun setOnFinishHandler(handler: () -> Unit)

    fun setOnTickHandler(handler: (millisecondsLeft: Long) -> Unit)

    fun cancel()

    fun start(durationInMilliseconds: Long)
}

