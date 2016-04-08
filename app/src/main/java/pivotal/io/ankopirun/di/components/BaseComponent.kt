package pivotal.io.ankopirun.di.components

import pivotal.io.ankopirun.views.activities.OrderDetailsActivity
import pivotal.io.ankopirun.views.activities.RunnerLocationActivity

interface BaseComponent {
    fun inject(activity: RunnerLocationActivity)
}
