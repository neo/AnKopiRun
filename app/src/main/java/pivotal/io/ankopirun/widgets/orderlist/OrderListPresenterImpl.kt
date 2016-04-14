package pivotal.io.ankopirun.widgets.orderlist

import pivotal.io.ankopirun.repositories.OrderRepository
import pivotal.io.ankopirun.views.OrderListView
import rx.Scheduler
import rx.lang.kotlin.subscribeWith

class OrderListPresenterImpl(val orderRepository: OrderRepository,
                             val ioScheduler: Scheduler,
                             val mainThreadScheduler: Scheduler) : OrderListPresenter {

    override var view: OrderListView? = null
        set(value) {
            field = value
        }

    override fun populateOrderList(runUuid: String) {
        orderRepository.getOrders(runUuid)
                .subscribeOn(ioScheduler)
                .observeOn(mainThreadScheduler)
                .subscribeWith {
                    onNext {
                        it.forEach { o -> view?.addOrder(o) }
                    }
                }
    }
}

