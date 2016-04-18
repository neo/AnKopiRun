package pivotal.io.ankopirun.views.activities

import org.junit.Test
import org.mockito.Mockito.*
import org.robolectric.Robolectric
import pivotal.io.ankopirun.RobolectricTest
import pivotal.io.ankopirun.widgets.runlist.RunListPresenter


class JoinRunActivityTest : RobolectricTest() {

    @Test
    fun resumingActivityListensForUpdates() {
        val activityController = Robolectric.buildActivity(JoinRunActivity::class.java)
        val activity = activityController.create().get().apply {
            runListPresenter = mock(RunListPresenter::class.java)
        }

        activityController.resume()

        verify(activity.runListPresenter).listen()
    }

    @Test
    fun pausingActivityClearsList() {
        val activityController = Robolectric.buildActivity(JoinRunActivity::class.java)
        val activity = activityController.create().resume().get().apply {
            runListPresenter = mock(RunListPresenter::class.java)
        }

        activityController.pause()

        verify(activity.runListPresenter).clearList()
    }
}

