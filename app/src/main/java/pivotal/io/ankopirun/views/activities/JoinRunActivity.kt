package pivotal.io.ankopirun.views.activities

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import org.jetbrains.anko.find
import pivotal.io.ankopirun.App
import pivotal.io.ankopirun.R
import pivotal.io.ankopirun.views.RunListRecyclerView
import pivotal.io.ankopirun.widgets.runlist.RunListAdapter
import pivotal.io.ankopirun.widgets.runlist.RunListPresenter
import javax.inject.Inject

class JoinRunActivity : AppCompatActivity() {

    @Inject
    lateinit var runListPresenter: RunListPresenter

    lateinit var runList: RunListRecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_join_run)

        (application as App).component.inject(this)

        runList = find<RunListRecyclerView>(R.id.run_list).apply {
            layoutManager = LinearLayoutManager(this@JoinRunActivity)
            setHasFixedSize(true)
            adapter = RunListAdapter()
        }
    }

    override fun onResume() {
        super.onResume()

        runListPresenter.apply {
            view = runList
            populateRunList()
        }
    }
}
