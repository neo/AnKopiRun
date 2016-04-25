package pivotal.io.ankopirun.views

import pivotal.io.ankopirun.models.Order

interface OrderListView {
    fun addOrder(order: Order)
}
