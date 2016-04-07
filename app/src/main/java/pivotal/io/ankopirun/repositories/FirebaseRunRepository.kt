package pivotal.io.ankopirun.repositories

import com.firebase.client.Firebase
import com.firebase.client.ServerValue
import pivotal.io.ankopirun.models.Run

class FirebaseRunRepository(val baseUrl : String) : RunRepository {

    override fun create(run: Run) {
        val ref = Firebase("$baseUrl/runs")
        val runRef = ref.push()
        runRef.setValue(run)
        runRef.child("startTime").setValue(ServerValue.TIMESTAMP)
    }
}
