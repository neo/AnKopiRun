package pivotal.io.ankopirun.widgets.runlist

import org.junit.Assert.assertEquals
import org.junit.Test
import pivotal.io.ankopirun.models.Run

class RunListAdapterTest {
    @Test
    fun inactiveAndActiveRunsAreAddedToTheRightLocation() {
        val runListAdapter = RunListAdapter()
        val inactiveRun = Run(duration=1, startTime = 0 )
        val activeRun = Run(duration = 1, startTime = System.currentTimeMillis() + 100000)

        runListAdapter.add(inactiveRun)
        assertEquals(runListAdapter.mDataset.indexOf(RunListAdapter.RunRow(inactiveRun)), 2)

        runListAdapter.add(activeRun)
        assertEquals(runListAdapter.mDataset.indexOf(RunListAdapter.RunRow(inactiveRun)), 3)
        assertEquals(runListAdapter.mDataset.indexOf(RunListAdapter.RunRow(activeRun)), 1)
    }
}