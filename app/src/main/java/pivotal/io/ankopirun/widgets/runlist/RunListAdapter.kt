package pivotal.io.ankopirun.widgets.runlist

import android.support.v4.content.ContextCompat
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import org.jetbrains.anko.find
import org.jetbrains.anko.startActivity
import pivotal.io.ankopirun.ORDER_LIST_COLORS
import pivotal.io.ankopirun.R
import pivotal.io.ankopirun.RUN
import pivotal.io.ankopirun.models.Run
import pivotal.io.ankopirun.views.activities.CreateOrderActivity

class RunListAdapter : RecyclerView.Adapter<RunListAdapter.ViewHolder>() {

    private var mDataset = mutableListOf<Run>()

    override fun getItemCount(): Int {
        return mDataset.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val run = mDataset.get(position)

        holder.itemView.apply {
            setOnClickListener {
                context.startActivity<CreateOrderActivity>(RUN to run)
            }
        }

        holder.apply {
            nameTextView.apply {
                val name = run.name
                text = name.substring(0, Math.min(2, name.length)).padEnd(1, '☕')
                setBackgroundColor(ContextCompat.getColor(context,
                        ORDER_LIST_COLORS.get(position % ORDER_LIST_COLORS.size)))
            }
            locationTextView.apply {
                text = run.location
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder? {
        val v = LayoutInflater.from(parent.getContext()).inflate(R.layout.run_list_row, parent, false) as LinearLayout
        val vh = ViewHolder(v)
        return vh
    }

    class ViewHolder(v: View) : RecyclerView.ViewHolder(v) {
        var nameTextView: TextView
        var locationTextView: TextView

        init {
            nameTextView = v.find(R.id.name)
            locationTextView = v.find(R.id.location)
        }
    }

    fun add(run: Run) {
        mDataset.add(0, run)
    }

    fun clear() {
        mDataset.clear()
    }
}