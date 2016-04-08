package pivotal.io.ankopirun.di.modules

import dagger.Module
import dagger.Provides
import pivotal.io.ankopirun.repositories.FirebaseRunRepository
import pivotal.io.ankopirun.repositories.RunRepository
import pivotal.io.ankopirun.widgets.countdowntimer.CountDownTimer
import pivotal.io.ankopirun.widgets.countdowntimer.CountDownTimerImpl
import javax.inject.Singleton

@Module
class RunModule {
    val baseUrl = "https://resplendent-inferno-9623.firebaseio.com"

    @Singleton
    @Provides
    fun runRepository() : RunRepository {
        return FirebaseRunRepository(baseUrl)
    }

    @Singleton
    @Provides
    fun countDownTimer() : CountDownTimer {
        return CountDownTimerImpl()
    }
}
