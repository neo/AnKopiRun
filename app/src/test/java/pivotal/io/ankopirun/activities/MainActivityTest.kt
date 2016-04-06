package pivotal.io.ankopirun.activities

import android.content.Intent
import android.os.Build
import org.junit.Assert.assertEquals
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.Robolectric
import org.robolectric.RobolectricGradleTestRunner
import org.robolectric.annotation.Config
import org.robolectric.internal.ShadowExtractor
import org.robolectric.shadows.ShadowActivity
import pivotal.io.ankopirun.BuildConfig
import pivotal.io.ankopirun.MainActivity
import pivotal.io.ankopirun.RunnerDetailsActivity

@RunWith(RobolectricGradleTestRunner::class)
@Config(constants = BuildConfig::class,
        sdk = intArrayOf(Build.VERSION_CODES.LOLLIPOP),
        manifest = Config.NONE)
class MainActivityTest {
    @Test
    fun clickingOnCreateButtonStartsRunnerDetailsActivity() {
        val mainActivity = Robolectric.setupActivity(MainActivity::class.java)

        mainActivity.createButton.performClick()

        val shadowMainActivity = ShadowExtractor.extract(mainActivity) as ShadowActivity
        val actualIntent = shadowMainActivity.nextStartedActivity
        val expectedIntent = Intent(mainActivity, RunnerDetailsActivity::class.java)

        assertEquals(expectedIntent, actualIntent)
    }
}