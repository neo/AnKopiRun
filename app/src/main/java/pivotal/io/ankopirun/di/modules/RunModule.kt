package pivotal.io.ankopirun.di.modules

import android.content.Context
import dagger.Module
import dagger.Provides
import pivotal.io.ankopirun.BASE_URL
import pivotal.io.ankopirun.R
import pivotal.io.ankopirun.repositories.FirebaseRunRepository
import pivotal.io.ankopirun.repositories.RunRepository
import pivotal.io.ankopirun.widgets.countdowntimer.CountDownPresenter
import pivotal.io.ankopirun.widgets.countdowntimer.CountDownPresenterImpl
import pivotal.io.ankopirun.widgets.countdowntimer.CountDownTimer
import pivotal.io.ankopirun.widgets.countdowntimer.CountDownTimerImpl
import pivotal.io.ankopirun.widgets.mediaplayer.MediaPlayer
import pivotal.io.ankopirun.widgets.mediaplayer.MediaPlayerImpl
import pivotal.io.ankopirun.widgets.runlist.RunListPresenter
import pivotal.io.ankopirun.widgets.runlist.RunListPresenterImpl
import rx.Scheduler
import javax.inject.Named
import javax.inject.Singleton

@Module
class RunModule {

    @Singleton
    @Provides
    fun runRepository(): RunRepository {
        return FirebaseRunRepository(BASE_URL)
    }

    @Singleton
    @Provides
    fun countDownTimer(): CountDownTimer {
        return CountDownTimerImpl()
    }

    @Singleton
    @Provides
    fun runListPresenter(runRepository: RunRepository,
                         @Named("io") ioScheduler: Scheduler,
                         @Named("mainThread") mainThreadSchduler: Scheduler): RunListPresenter {

        return RunListPresenterImpl(runRepository, ioScheduler, mainThreadSchduler)
    }

    @Singleton
    @Provides
    fun mediaPlayer(context: Context) : MediaPlayer {
        return MediaPlayerImpl(context, R.raw.anthem)
    }

    @Singleton
    @Provides
    fun countDownPresenter(countDownTimer: CountDownTimer, mediaPlayer: MediaPlayer) : CountDownPresenter {
       return CountDownPresenterImpl(countDownTimer, mediaPlayer)
    }
}
