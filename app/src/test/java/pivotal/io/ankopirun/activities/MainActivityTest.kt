package pivotal.io.ankopirun.activities

import android.content.Intent
import org.junit.Assert.assertEquals
import org.junit.Test
import org.robolectric.Robolectric
import org.robolectric.internal.ShadowExtractor
import org.robolectric.shadows.ShadowActivity
import pivotal.io.ankopirun.activities.MainActivity
import pivotal.io.ankopirun.RobolectricTest
import pivotal.io.ankopirun.activities.RunnerDetailsActivity

class MainActivityTest : RobolectricTest() {

    @Test
    fun clickingOnCreateButtonStartsRunnerDetailsActivity() {
        val activity = Robolectric.setupActivity(MainActivity::class.java)

        activity.createButton.performClick()

        val shadowActivity = ShadowExtractor.extract(activity) as ShadowActivity
        val actualIntent = shadowActivity.nextStartedActivity
        val expectedIntent = Intent(activity, RunnerDetailsActivity::class.java)

        assertEquals(expectedIntent, actualIntent)
    }
}