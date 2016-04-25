package pivotal.io.ankopirun.widgets.runlist

import pivotal.io.ankopirun.models.Run
import pivotal.io.ankopirun.repositories.RunRepository
import pivotal.io.ankopirun.views.RunListView
import rx.Scheduler
import rx.Subscriber
import rx.exceptions.OnErrorNotImplementedException

class RunListPresenterImpl(val runRepository: RunRepository,
                           val ioScheduler: Scheduler,
                           val mainThreadScheduler: Scheduler) : RunListPresenter {

    var subscriber: Subscriber<Run>? = null

    override fun listen() {
        subscriber?.unsubscribe()
        subscriber = createRunSubscriber()
        runRepository.getAddedRuns()
                .subscribeOn(ioScheduler)
                .observeOn(mainThreadScheduler)
                .subscribe(subscriber)
    }

    override fun clearList() {
        view?.clearRuns()
    }

    override var view: RunListView? = null
        set(value) {
            field = value
        }

    fun createRunSubscriber(): Subscriber<Run> {
        return object : Subscriber<Run>() {
            override fun onCompleted() {
                /** this will never be called ! **/
            }

            override fun onError(e: Throwable) {
                OnErrorNotImplementedException(e)
            }

            override fun onNext(t: Run) {
                view?.addRun(t)
            }
        }
    }
}

