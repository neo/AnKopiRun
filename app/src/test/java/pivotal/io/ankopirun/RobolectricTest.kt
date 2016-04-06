package pivotal.io.ankopirun

import android.os.Build
import org.junit.Ignore
import org.junit.runner.RunWith
import org.robolectric.RobolectricGradleTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricGradleTestRunner::class)
@Config(constants = BuildConfig::class,
        sdk = intArrayOf(Build.VERSION_CODES.LOLLIPOP),
        manifest = Config.NONE)
@Ignore
open class RobolectricTest