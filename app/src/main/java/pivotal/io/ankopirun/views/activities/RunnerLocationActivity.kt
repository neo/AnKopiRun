package pivotal.io.ankopirun.views.activities

import android.os.Bundle

import android.support.v7.app.AppCompatActivity
import android.widget.Button
import android.widget.EditText
import com.jakewharton.rxbinding.widget.RxTextView
import org.jetbrains.anko.find
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.toast
import pivotal.io.ankopirun.App
import pivotal.io.ankopirun.R
import pivotal.io.ankopirun.RUNNER_NAME
import pivotal.io.ankopirun.RUN_UUID
import pivotal.io.ankopirun.models.Run
import pivotal.io.ankopirun.repositories.RunRepository
import rx.Observable
import rx.Scheduler
import rx.lang.kotlin.subscribeWith
import rx.schedulers.Schedulers
import javax.inject.Inject
import javax.inject.Named


class RunnerLocationActivity : AppCompatActivity() {
    lateinit var submitLocationButton: Button
    lateinit var locationField: EditText
    lateinit var runnerName: String

    @Inject
    lateinit var runRepository: RunRepository

    @field:[Inject Named("io")]
    lateinit var io: Scheduler

    @field:[Inject Named("mainThread")]
    lateinit var mainThread: Scheduler

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_runner_location)

        (application as App).component.inject(this)

        retrieveName()
        initialiseElements()
    }

    private fun retrieveName() {
        runnerName = intent.extras.getString(RUNNER_NAME, "")
    }

    private fun initialiseElements() {
        submitLocationButton = find<Button>(R.id.submit_location_btn).apply {
            setOnClickListener {
                createRun()
            }
        }

        locationField = find<EditText>(R.id.location)
        RxTextView.textChanges(locationField).subscribeWith {
            onNext {
                submitLocationButton.isEnabled = it.isNotEmpty()
            }
        }
    }

    fun createRun() {
        val run = Run(runnerName, locationField.text.toString())
        runRepository.createRun(run)
            .subscribeOn(Schedulers.immediate())
            .observeOn(Schedulers.immediate())
            .subscribeWith {
                onNext {
                    startActivity<OrderDetailsActivity>(RUN_UUID to it.id)
                }

                onError {
                    toast(it.message.orEmpty())
                }
            }
    }
}
