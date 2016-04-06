package pivotal.io.ankopirun.di.components

import pivotal.io.ankopirun.activities.RunnerLocationActivity

interface BaseComponent {
    fun inject(activity: RunnerLocationActivity)
}
