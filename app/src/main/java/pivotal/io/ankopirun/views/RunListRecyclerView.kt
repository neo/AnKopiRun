package pivotal.io.ankopirun.views

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.util.AttributeSet
import pivotal.io.ankopirun.models.Order
import pivotal.io.ankopirun.models.Run
import pivotal.io.ankopirun.widgets.orderlist.OrderListAdapter
import pivotal.io.ankopirun.widgets.orderlist.RunListAdapter

class RunListRecyclerView(context: Context?, attrs: AttributeSet?)
    : RecyclerView(context, attrs), RunListView {

    override fun addRun(run: Run) {
        (adapter as RunListAdapter).add(run)
        adapter.notifyDataSetChanged()
    }

}
