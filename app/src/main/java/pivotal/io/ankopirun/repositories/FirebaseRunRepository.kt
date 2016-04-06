package pivotal.io.ankopirun.repositories

import com.firebase.client.Firebase
import pivotal.io.ankopirun.models.Run

class FirebaseRunRepository(val baseUrl : String) : RunRepository {

    override fun create(run: Run) {
        val ref = Firebase("$baseUrl/runs")
        ref.push().setValue(run)
    }
}
