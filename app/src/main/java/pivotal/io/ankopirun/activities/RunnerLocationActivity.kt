package pivotal.io.ankopirun.activities

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import com.firebase.client.Firebase
import com.jakewharton.rxbinding.widget.RxTextView
import org.jetbrains.anko.find
import org.jetbrains.anko.startActivity
import pivotal.io.ankopirun.R
import pivotal.io.ankopirun.RUNNER_NAME
import pivotal.io.ankopirun.models.Run
import pivotal.io.ankopirun.repositories.RunRepository
import rx.lang.kotlin.subscribeWith

class RunnerLocationActivity : AppCompatActivity() {
    lateinit var submitLocationButton: Button
    lateinit var locationField: EditText
    lateinit var runnerName : String

    lateinit var runRepository: RunRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_runner_location)

        retrieveName()
        initialiseElements()
    }

    private fun retrieveName() {
        runnerName = intent.extras.getString(RUNNER_NAME, "")
    }

    private fun initialiseElements() {
        submitLocationButton = find<Button>(R.id.submit_location_btn).apply {
            setOnClickListener { createRun() }
        }

        locationField = find<EditText>(R.id.location)
        RxTextView.textChanges(locationField).subscribeWith {
            onNext {
                submitLocationButton.isEnabled = it.isNotEmpty()
            }
        }
    }

    private fun createRun() {
        val run = Run(runnerName, locationField.text.toString())
        runRepository.create(run)
    }
}
