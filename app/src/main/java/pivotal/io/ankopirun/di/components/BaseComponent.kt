package pivotal.io.ankopirun.di.components

import pivotal.io.ankopirun.views.activities.CreateOrderActivity
import pivotal.io.ankopirun.views.activities.JoinRunActivity
import pivotal.io.ankopirun.views.activities.OrderDetailsActivity
import pivotal.io.ankopirun.views.activities.RunnerLocationActivity

interface BaseComponent {
    fun inject(activity: RunnerLocationActivity)

    fun inject(activity: OrderDetailsActivity)

    fun inject(activity: CreateOrderActivity)

    fun inject(activity: JoinRunActivity)
}
