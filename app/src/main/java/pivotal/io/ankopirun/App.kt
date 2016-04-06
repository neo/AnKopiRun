package pivotal.io.ankopirun

import android.app.Application
import com.firebase.client.Firebase
import pivotal.io.ankopirun.di.components.AppComponent
import pivotal.io.ankopirun.di.components.DaggerAppComponent
import pivotal.io.ankopirun.di.modules.AndroidModule
import pivotal.io.ankopirun.di.modules.RunModule

class App : Application() {
    lateinit var component: AppComponent

    override fun onCreate() {
        super.onCreate()

        component = DaggerAppComponent
                .builder()
                .androidModule(AndroidModule(this))
                .runModule(RunModule())
                .build()

        setUpFirebase()
    }

    private fun setUpFirebase() {
        Firebase.setAndroidContext(this)
    }
}