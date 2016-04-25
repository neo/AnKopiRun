package pivotal.io.ankopirun.views

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.util.AttributeSet
import pivotal.io.ankopirun.models.Order
import pivotal.io.ankopirun.widgets.orderlist.OrderListAdapter

class OrderListRecyclerView(context: Context?, attrs: AttributeSet?) : RecyclerView(context, attrs), OrderListView {

    override fun addOrder(order: Order) {
        (adapter as OrderListAdapter).add(order)
        adapter.notifyDataSetChanged()
    }

}
