package pivotal.io.ankopirun.widgets.orderlist

import pivotal.io.ankopirun.repositories.OrderRepository
import pivotal.io.ankopirun.views.OrderListView
import rx.Scheduler
import rx.lang.kotlin.subscribeWith

class OrderListPresenterImpl(val orderRepository: OrderRepository,
                             val ioScheduler: Scheduler,
                             val mainThreadScheduler: Scheduler) : OrderListPresenter {
    var view: OrderListView? = null

    override fun populateOrderList() {
        orderRepository.getOrders()
                .subscribeOn(ioScheduler)
                .observeOn(mainThreadScheduler)
                .subscribeWith {
                    onNext {
                        it.forEach { o -> view?.addOrder(o) }
                    }
                }
    }
}

