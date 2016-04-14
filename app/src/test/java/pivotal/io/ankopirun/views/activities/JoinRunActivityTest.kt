package pivotal.io.ankopirun.views.activities

import org.junit.Test
import org.mockito.Mockito.*
import org.robolectric.Robolectric
import pivotal.io.ankopirun.RobolectricTest
import pivotal.io.ankopirun.widgets.runlist.RunListPresenter


class JoinRunActivityTest : RobolectricTest() {

    @Test
    fun resumingActivityListsRuns() {
        val activityController = Robolectric.buildActivity(JoinRunActivity::class.java)
        val activity = activityController.create().get().apply {
            runListPresenter = mock(RunListPresenter::class.java)
        }

        verifyZeroInteractions(activity.runListPresenter)

        activityController.resume()

        verify(activity.runListPresenter).populateRunList()
    }
}

