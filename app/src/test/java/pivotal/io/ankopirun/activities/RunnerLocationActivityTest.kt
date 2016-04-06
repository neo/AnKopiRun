package pivotal.io.ankopirun.activities

import android.content.Intent
import com.natpryce.hamkrest.assertion.assertThat
import com.natpryce.hamkrest.isEmptyString
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.mockito.ArgumentCaptor
import org.mockito.Mockito
import org.mockito.Mockito.*
import org.robolectric.Robolectric
import pivotal.io.ankopirun.RUNNER_NAME
import pivotal.io.ankopirun.RobolectricTest
import pivotal.io.ankopirun.activities.RunnerLocationActivity
import pivotal.io.ankopirun.models.Run
import pivotal.io.ankopirun.repositories.RunRepository

class RunnerLocationActivityTest : RobolectricTest() {
    lateinit var activity: RunnerLocationActivity

    @Before
    fun setUp() {
        activity = Robolectric.buildActivity(RunnerLocationActivity::class.java)
                .newIntent(Intent().putExtra(RUNNER_NAME, "Herp derp"))
                .create()
                .get()
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
        val runRepository = mock(RunRepository::class.java)
//        activity.runRepository = runRepository
//
//        activity.submitLocationButton.performClick()
//
//        val argument = ArgumentCaptor.forClass(Run::class.java)
//        verify(runRepository).create(argument.capture())
//
//        val expectedRun = Run("herp", "derp")
//        assertEquals(expectedRun, argument)
    }
}