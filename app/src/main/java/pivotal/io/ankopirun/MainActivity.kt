package pivotal.io.ankopirun

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import org.jetbrains.anko.find
import org.jetbrains.anko.startActivity


class MainActivity : AppCompatActivity() {
    lateinit var createButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initialiseElements()
    }

    private fun initialiseElements() {
        createButton = find<Button>(R.id.create_btn).apply {
            setOnClickListener { startActivity<RunnerDetailsActivity>() }
        }
    }
}
