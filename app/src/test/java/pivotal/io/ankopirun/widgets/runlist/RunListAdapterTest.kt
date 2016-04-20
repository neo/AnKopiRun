package pivotal.io.ankopirun.widgets.runlist

import android.content.Intent
import android.view.ViewGroup
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.robolectric.RuntimeEnvironment
import org.robolectric.internal.ShadowExtractor
import org.robolectric.shadows.ShadowApplication
import pivotal.io.ankopirun.RUN
import pivotal.io.ankopirun.RUN_TYPE
import pivotal.io.ankopirun.RobolectricTest
import pivotal.io.ankopirun.models.Run
import pivotal.io.ankopirun.views.activities.CreateOrderActivity
import pivotal.io.ankopirun.views.activities.OrderDetailsActivity

class RunListAdapterTest: RobolectricTest() {
    lateinit var runListAdapter: RunListAdapter
    val inactiveRun = Run(duration= 1, startTime = 10)
    val activeRun = Run(duration = 1, startTime = System.currentTimeMillis() + 100000)
    val context = RuntimeEnvironment.application
    val viewGroup = object : ViewGroup(context) {
        override fun onLayout(changed: Boolean, l: Int, t: Int, r: Int, b: Int) {
        }
    }

    @Before
    fun setUp() {
        runListAdapter = RunListAdapter()
    }

    @Test
    fun inactiveAndActiveRunsAreAddedToTheRightLocation() {
        runListAdapter.add(inactiveRun)
        assertEquals(runListAdapter.mDataset.indexOf(RunListAdapter.RunRow(inactiveRun)), 2)

        runListAdapter.add(activeRun)
        assertEquals(runListAdapter.mDataset.indexOf(RunListAdapter.RunRow(inactiveRun)), 3)
        assertEquals(runListAdapter.mDataset.indexOf(RunListAdapter.RunRow(activeRun)), 1)
    }

    @Test
    fun clickingOnActiveRunGoesToCreateOrderActivity() {
        runListAdapter.add(activeRun)
        val viewHolder = runListAdapter.onCreateViewHolder(viewGroup, RUN_TYPE)
        runListAdapter.onBindViewHolder(viewHolder, 1)

        viewHolder.itemView.performClick()

        val shadow = ShadowExtractor.extract(viewHolder.itemView.context) as ShadowApplication
        val expectedIntent = Intent(context, CreateOrderActivity::class.java)
        expectedIntent.putExtra(RUN, activeRun)
        assertEquals(shadow.nextStartedActivity, expectedIntent)
    }

    @Test
    fun clickingOnInactiveRunGoesToCreateOrderActivity() {
        runListAdapter.add(inactiveRun)
        val viewHolder = runListAdapter.onCreateViewHolder(viewGroup, RUN_TYPE)
        runListAdapter.onBindViewHolder(viewHolder, 2)

        viewHolder.itemView.performClick()

        val shadow = ShadowExtractor.extract(viewHolder.itemView.context) as ShadowApplication
        val expectedIntent = Intent(context, OrderDetailsActivity::class.java)
        expectedIntent.putExtra(RUN, inactiveRun)
        assertEquals(shadow.nextStartedActivity, expectedIntent)
    }
}