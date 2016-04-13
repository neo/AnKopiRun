package pivotal.io.ankopirun.di.modules

import dagger.Module
import dagger.Provides
import pivotal.io.ankopirun.repositories.FirebaseOrderRepository
import pivotal.io.ankopirun.repositories.OrderRepository
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
}
