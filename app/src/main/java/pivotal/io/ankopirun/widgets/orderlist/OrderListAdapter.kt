package pivotal.io.ankopirun.widgets.orderlist

import android.support.v4.content.ContextCompat
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import org.jetbrains.anko.*
import pivotal.io.ankopirun.ORDER_LIST_COLORS
import pivotal.io.ankopirun.R
import pivotal.io.ankopirun.models.Order

class OrderListAdapter : RecyclerView.Adapter<OrderListAdapter.ViewHolder>() {

    private var mDataset = mutableListOf<Order>()

    override fun getItemCount(): Int {
        return mDataset.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val order = mDataset.get(position)

        holder.apply {
            nameTextView.apply {
                val name = order.requesterName
                text = name.substring(0, Math.min(2, name.length)).padEnd(1, '☕')
                setBackgroundColor(ContextCompat.getColor(context,
                        ORDER_LIST_COLORS.get(position % ORDER_LIST_COLORS.size)))
            }
            itemTextView.apply {
                text = order.description
            }
            quantityTextView.apply {
                text = ""
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder? {
        val v = LayoutInflater.from(parent.getContext()).inflate(R.layout.order_list_row, parent, false) as LinearLayout
        val vh = ViewHolder(v)
        return vh
    }

    class ViewHolder(v: View) : RecyclerView.ViewHolder(v) {
        var nameTextView: TextView
        var itemTextView: TextView
        var quantityTextView: TextView

        init {
            nameTextView = v.find(R.id.name)
            itemTextView = v.find(R.id.item)
            quantityTextView = v.find(R.id.quantity)
        }
    }

    fun add(order: Order) {
        mDataset.add(order)
    }
}