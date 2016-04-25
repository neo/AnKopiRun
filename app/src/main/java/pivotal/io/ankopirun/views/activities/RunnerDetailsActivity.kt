package pivotal.io.ankopirun.views.activities

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.Button
import android.widget.EditText
import com.jakewharton.rxbinding.widget.RxTextView
import org.jetbrains.anko.find
import org.jetbrains.anko.startActivity
import pivotal.io.ankopirun.R
import pivotal.io.ankopirun.RUNNER_NAME
import rx.lang.kotlin.subscribeWith

class RunnerDetailsActivity : AppCompatActivity() {
    lateinit var submitNameButton: Button
    lateinit var nameField: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_runner_details)

        initialiseElements()
    }

    private fun initialiseElements() {
        submitNameButton = find<Button>(R.id.submit_name_btn).apply {
            setOnClickListener {
                startActivity<RunnerLocationActivity>(RUNNER_NAME to nameField.text.toString())
            }
            isEnabled = false
        }

        nameField = find<EditText>(R.id.name)
        RxTextView.textChanges(nameField).subscribeWith {
            onNext {
                submitNameButton.isEnabled = it.isNotEmpty()
            }
        }
    }
}
