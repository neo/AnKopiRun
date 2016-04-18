package pivotal.io.ankopirun.widgets.runlist

import pivotal.io.ankopirun.views.RunListView

interface RunListPresenter {

    var view: RunListView?

    fun clearList()

    fun listen()
}
