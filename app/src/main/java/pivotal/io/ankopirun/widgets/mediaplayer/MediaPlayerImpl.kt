package pivotal.io.ankopirun.widgets.mediaplayer

import android.content.Context
import kotlin.properties.ReadOnlyProperty
import kotlin.reflect.KProperty

class MediaPlayerImpl(context: Context, resId: Int) : MediaPlayer {
    val mediaPlayer: android.media.MediaPlayer by LazyDelegate(context, resId)

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

class LazyDelegate(val context: Context, val resId: Int)
    : ReadOnlyProperty<MediaPlayerImpl, android.media.MediaPlayer> {

    override fun getValue(thisRef: MediaPlayerImpl, property: KProperty<*>): android.media.MediaPlayer {
        return android.media.MediaPlayer.create(context, resId)
    }
}
