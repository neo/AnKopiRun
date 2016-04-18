package pivotal.io.ankopirun.di.modules

import dagger.Module
import dagger.Provides
import pivotal.io.ankopirun.repositories.FirebaseOrderRepository
import pivotal.io.ankopirun.repositories.OrderRepository
import pivotal.io.ankopirun.widgets.orderlist.OrderListPresenter
import pivotal.io.ankopirun.widgets.orderlist.OrderListPresenterImpl
import pivotal.io.ankopirun.widgets.runlist.RunListPresenter
import rx.Scheduler
import javax.inject.Named
import javax.inject.Singleton

@Module
class OrderModule {
    // TODO: Change the URL
    val baseUrl = "https://resplendent-inferno-9623.firebaseio.com"

    @Singleton
    @Provides
    fun orderRepository() : OrderRepository {
        return FirebaseOrderRepository(baseUrl)
    }

    @Singleton
    @Provides
    fun orderListPresenter(orderRepository: OrderRepository,
                           @Named("io") ioScheduler: Scheduler,
                           @Named("mainThread") mainThreadSchduler: Scheduler): OrderListPresenter {

        return OrderListPresenterImpl(orderRepository, ioScheduler, mainThreadSchduler)
    }

}
