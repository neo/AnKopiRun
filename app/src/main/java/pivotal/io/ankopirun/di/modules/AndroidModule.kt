package pivotal.io.ankopirun.di.modules

import android.app.Application
import android.content.Context
import dagger.Module
import dagger.Provides
import rx.Scheduler
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers
import javax.inject.Named
import javax.inject.Singleton

@Module
class AndroidModule(val app: Application) {

    @Singleton
    @Provides
    fun context() : Context {
        return app.applicationContext
    }

    @Singleton
    @Provides
    @Named("io")
    fun io() : Scheduler {
        return Schedulers.io()
    }

    @Singleton
    @Provides
    @Named("mainThread")
    fun mainThread() : Scheduler {
        return AndroidSchedulers.mainThread()
    }
}
