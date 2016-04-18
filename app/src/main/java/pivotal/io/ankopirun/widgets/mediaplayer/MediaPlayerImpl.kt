package pivotal.io.ankopirun.widgets.mediaplayer

import android.content.Context

class MediaPlayerImpl(context: Context, resId: Int) : MediaPlayer {
    lateinit var mediaPlayer: android.media.MediaPlayer

    init {
        mediaPlayer = android.media.MediaPlayer.create(context, resId)
    }

    override fun play() {
        if (mediaPlayer.isPlaying) return
        mediaPlayer.start()
    }

    override fun stop() {
        if (!mediaPlayer.isPlaying) return
        mediaPlayer.apply {
            stop()
            prepare()
            seekTo(0)
        }
    }

}

