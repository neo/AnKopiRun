package pivotal.io.ankopirun.widgets.orderlist

import android.support.v4.content.ContextCompat
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import org.jetbrains.anko.find
import pivotal.io.ankopirun.ORDER_LIST_COLORS
import pivotal.io.ankopirun.R
import pivotal.io.ankopirun.models.Order

class OrderListAdapter : RecyclerView.Adapter<OrderListAdapter.ViewHolder>() {

    private val mDataset: List<Order> = listOf(
            Order("kopi o gao gao gao", "AS", "11"),
            Order("milo dinosaur gao gao", "BT", "5"),
            Order("milo gozilla gaiju", "BA", "5"),
            Order("long black", "NT", "3"),
            Order("cappucino", "AY", "4"),
            Order("lattee", "RR", "6"))

    override fun getItemCount(): Int {
        return mDataset.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.apply {
            nameTextView.apply {
                setText(mDataset.get(position).requesterName)
                setBackgroundColor(ContextCompat.getColor(context, ORDER_LIST_COLORS.get(position % ORDER_LIST_COLORS.size)))
            }
            itemTextView.apply {
                setText(mDataset.get(position).description)
            }
            quantityTextView.apply {
                setText("x ${mDataset.get(position).runUuid}") // <-- TODO: CHANGE THIS ZOMG
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
}