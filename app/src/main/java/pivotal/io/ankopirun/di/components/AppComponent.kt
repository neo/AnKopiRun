package pivotal.io.ankopirun.di.components

import dagger.Component
import pivotal.io.ankopirun.di.modules.AndroidModule
import pivotal.io.ankopirun.di.modules.OrderModule
import pivotal.io.ankopirun.di.modules.RunModule
import javax.inject.Singleton

@Component(modules = arrayOf(
        AndroidModule::class,
        RunModule::class,
        OrderModule::class))
@Singleton
interface AppComponent: BaseComponent {

}
