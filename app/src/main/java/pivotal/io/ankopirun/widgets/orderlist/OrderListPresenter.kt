package pivotal.io.ankopirun.widgets.orderlist

import pivotal.io.ankopirun.views.OrderListView

interface OrderListPresenter {

    var view: OrderListView?

    fun listen(runUuid: String)

}

