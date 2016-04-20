package pivotal.io.ankopirun.widgets.mediaplayer

import android.content.Context

class MediaPlayerImpl(context: Context, resId: Int) : MediaPlayer {
    val mediaPlayer: android.media.MediaPlayer by lazy { android.media.MediaPlayer.create(context, resId) }

    override fun play() {
        if (mediaPlayer.isPlaying) return
        mediaPlayer.start()
    }

    override fun stop() {
        if (!mediaPlayer.isPlaying) return

        mediaPlayer.apply {
            pause()
            seekTo(0)
        }
    }

}
