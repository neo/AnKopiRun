package pivotal.io.ankopirun

import android.app.Application
import com.firebase.client.Firebase

class App : Application() {
    override fun onCreate() {
        super.onCreate()

        setUpFirebase()
    }

    private fun setUpFirebase() {
        Firebase.setAndroidContext(this);
    }
}