package pivotal.io.ankopirun.widgets.runlist

import org.junit.Test
import org.mockito.Mockito.*
import pivotal.io.ankopirun.models.Run
import pivotal.io.ankopirun.repositories.RunRepository
import pivotal.io.ankopirun.views.RunListView
import rx.Observable
import rx.schedulers.Schedulers

class RunListPresenterImplTest {

    @Test
    fun testListenUpdatesView() {
        val runs = listOf(Run(), Run())
        val repo = mock(RunRepository::class.java).apply {
            `when`(getAddedRuns()).thenReturn(Observable.from(runs))
        }
        val scheduler = Schedulers.immediate()
        val presenter = RunListPresenterImpl(repo, scheduler, scheduler).apply {
            view = mock(RunListView::class.java)
        }

        presenter.listen()

        verify(presenter.view, times(2))?.addRun(Run())
    }
}


