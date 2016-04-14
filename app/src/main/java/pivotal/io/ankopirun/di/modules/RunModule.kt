package pivotal.io.ankopirun.di.modules

import dagger.Module
import dagger.Provides
import pivotal.io.ankopirun.repositories.FirebaseRunRepository
import pivotal.io.ankopirun.repositories.RunRepository
import pivotal.io.ankopirun.widgets.countdowntimer.CountDownTimer
import pivotal.io.ankopirun.widgets.countdowntimer.CountDownTimerImpl
import pivotal.io.ankopirun.widgets.runlist.RunListPresenter
import pivotal.io.ankopirun.widgets.runlist.RunListPresenterImpl
import rx.Scheduler
import rx.schedulers.Schedulers
import javax.inject.Named
import javax.inject.Singleton

@Module
class RunModule {
    val baseUrl = "https://resplendent-inferno-9623.firebaseio.com"

    @Singleton
    @Provides
    fun runRepository(): RunRepository {
        return FirebaseRunRepository(baseUrl)
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
}
