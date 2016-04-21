package pivotal.io.ankopirun.di.modules

import dagger.Module
import dagger.Provides
import pivotal.io.ankopirun.BASE_URL
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
    @Singleton
    @Provides
    fun orderRepository() : OrderRepository {
        return FirebaseOrderRepository(BASE_URL)
    }

    @Singleton
    @Provides
    fun orderListPresenter(orderRepository: OrderRepository,
                           @Named("io") ioScheduler: Scheduler,
                           @Named("mainThread") mainThreadSchduler: Scheduler): OrderListPresenter {

        return OrderListPresenterImpl(orderRepository, ioScheduler, mainThreadSchduler)
    }

}
