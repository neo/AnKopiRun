package pivotal.io.ankopirun.views.activities

import android.content.Intent
import com.natpryce.hamkrest.assertion.assertThat
import com.natpryce.hamkrest.isEmptyString
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito
import org.mockito.Mockito.mock
import org.mockito.Mockito.verify
import org.robolectric.Robolectric
import org.robolectric.internal.ShadowExtractor
import org.robolectric.shadows.ShadowActivity
import pivotal.io.ankopirun.RUN
import pivotal.io.ankopirun.RUNNER_NAME
import pivotal.io.ankopirun.RobolectricTest
import pivotal.io.ankopirun.models.Run
import pivotal.io.ankopirun.repositories.RunRepository
import rx.Observable
import rx.schedulers.Schedulers

class RunnerLocationActivityTest : RobolectricTest() {
    lateinit var activity: RunnerLocationActivity
    lateinit var mockRunRepository: RunRepository

    @Before
    fun setUp() {
        activity = Robolectric.buildActivity(RunnerLocationActivity::class.java)
                .withIntent(Intent().putExtra(RUNNER_NAME, "Herp derp"))
                .create()
                .get()

        val run = Run("Herp derp", "The Plain")

        mockRunRepository = mock(RunRepository::class.java).apply {
            Mockito.`when`(createRun(run))
                    .thenReturn(Observable.just(run.copy(id = "RUN_UUID")))
        }

        activity.apply {
            runRepository = mockRunRepository
            io = Schedulers.immediate()
            mainThread = Schedulers.immediate()
        }
    }

    @Test
    fun submitLocationButtonIsDisabledIfLocationIsEmpty() {
        assertThat(activity.locationField.text, isEmptyString)

        assertFalse(activity.submitLocationButton.isEnabled)
    }

    @Test
    fun submitLocationButtonIsEnabledIfLocationIsNotEmpty() {
        activity.locationField.setText("The Plain")

        assertTrue(activity.submitLocationButton.isEnabled)
    }

    @Test
    fun clickingOnSubmitLocationButtonCreatesRun() {
        activity.locationField.setText("The Plain")

        activity.submitLocationButton.performClick()

        verify(mockRunRepository).createRun(Run("Herp derp", "The Plain"))
    }

    @Test
    fun clickingOnSubmitLocationButtonStartsOrderDetailsActivity() {
        activity.locationField.setText("The Plain")

        activity.submitLocationButton.performClick()

        val shadowActivity = ShadowExtractor.extract(activity) as ShadowActivity
        val actualIntent = shadowActivity.nextStartedActivity
        val expectedIntent = Intent(activity, OrderDetailsActivity::class.java).apply {
            putExtra(RUN, Run("Herp derp", "The Plain", id = "RUN_UUID"))
        }
        assertEquals(expectedIntent, actualIntent)
    }
}