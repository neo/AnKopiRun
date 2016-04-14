package pivotal.io.ankopirun.views.activities

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import pivotal.io.ankopirun.App
import pivotal.io.ankopirun.R
import pivotal.io.ankopirun.widgets.runlist.RunListPresenter
import javax.inject.Inject

class JoinRunActivity : AppCompatActivity() {

    @Inject
    lateinit var runListPresenter: RunListPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_join_run)

        (application as App).component.inject(this)
    }

    override fun onResume() {
        super.onResume()
        runListPresenter.populateRunList()
    }
}
