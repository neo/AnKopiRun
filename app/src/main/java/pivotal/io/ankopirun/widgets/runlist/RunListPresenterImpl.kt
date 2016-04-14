package pivotal.io.ankopirun.widgets.runlist

import pivotal.io.ankopirun.repositories.RunRepository
import pivotal.io.ankopirun.views.RunListView
import rx.Scheduler
import rx.lang.kotlin.subscribeWith

class RunListPresenterImpl(val runRepository: RunRepository,
                           val ioScheduler: Scheduler,
                           val mainThreadScheduler: Scheduler) : RunListPresenter {

    override var view: RunListView? = null
        set(value) {
            field = value
        }

    override fun populateRunList() {
        runRepository.getRuns()
            .subscribeOn(ioScheduler)
            .observeOn(mainThreadScheduler)
            .subscribeWith {
                onNext {
                    it.forEach {
                        run -> view?.addRun(run)
                    }
                }
            }
    }

}

