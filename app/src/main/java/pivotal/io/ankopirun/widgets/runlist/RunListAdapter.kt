package pivotal.io.ankopirun.widgets.runlist

import android.support.annotation.VisibleForTesting
import android.support.v4.content.ContextCompat
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import org.jetbrains.anko.find
import org.jetbrains.anko.startActivity
import pivotal.io.ankopirun.*
import pivotal.io.ankopirun.models.Run
import pivotal.io.ankopirun.views.activities.CreateOrderActivity
import pivotal.io.ankopirun.views.activities.OrderDetailsActivity

class RunListAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    @VisibleForTesting
    var mDataset = mutableListOf<Row>(Section("Active"), Section("Inactive"))
    private var activeIndex = 1
    private var inactiveIndex = 2

    override fun getItemCount(): Int {
        return mDataset.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is ViewHolder) {
            val run = (mDataset.get(position) as RunRow).run

            holder.itemView.apply {
                setOnClickListener {
                    if (run.isInactive(System.currentTimeMillis(), 0)) {
                        context.startActivity<OrderDetailsActivity>(RUN to run)
                    } else {
                        context.startActivity<CreateOrderActivity>(RUN to run)
                    }
                }
            }

            holder.apply {
                nameTextView.apply {
                    val name = run.runnerName
                    text = name.substring(0, Math.min(2, name.length)).padEnd(1, '☕')
                    setBackgroundColor(ContextCompat.getColor(context,
                            ORDER_LIST_COLORS.get(position % ORDER_LIST_COLORS.size)))
                }
                locationTextView.apply {
                    text = run.description
                }
            }
        } else if (holder is SectionViewHolder) {
            val section = mDataset.get(position) as Section

            holder.sectionTextView.apply {
                text = section.title
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.getContext())

        when (viewType) {
            SECTION_TYPE ->
            return SectionViewHolder(layoutInflater.inflate(R.layout.run_list_section_row, parent, false) as LinearLayout)
            else ->
                return ViewHolder(layoutInflater.inflate(R.layout.run_list_row, parent, false) as LinearLayout)
        }
    }

    override fun getItemViewType(position: Int): Int {
        return mDataset.get(position).getType()
    }

    fun add(run: Run) {
        if (run.isInactive(System.currentTimeMillis(), 0)) {
            mDataset.add(inactiveIndex, RunRow(run))
        } else {
            mDataset.add(activeIndex, RunRow(run))
            inactiveIndex++
        }
    }

    fun clear() {
        resetDataset()
    }

    private fun resetDataset() {
        mDataset = mutableListOf<Row>(Section("Active"), Section("Inactive"))
        activeIndex = 1
        inactiveIndex = 2
    }

    data class Section(val title: String) : Row {
        override fun getType() = SECTION_TYPE
    }

    data class RunRow(val run: Run) : Row {
        override fun getType() = RUN_TYPE
    }

    interface Row {
        fun getType(): Int
    }

    class ViewHolder(v: View) : RecyclerView.ViewHolder(v) {
        var nameTextView: TextView
        var locationTextView: TextView

        init {
            nameTextView = v.find(R.id.name)
            locationTextView = v.find(R.id.location)
        }
    }

    class SectionViewHolder(v: View) : RecyclerView.ViewHolder(v) {
        var sectionTextView: TextView

        init {
            sectionTextView = v.find(R.id.section)
        }
    }
}
