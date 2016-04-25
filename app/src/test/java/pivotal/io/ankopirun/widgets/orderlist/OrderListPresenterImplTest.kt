package pivotal.io.ankopirun.widgets.orderlist

import org.junit.Test
import org.mockito.Mockito.*
import pivotal.io.ankopirun.models.Order
import pivotal.io.ankopirun.repositories.OrderRepository
import pivotal.io.ankopirun.views.OrderListView
import rx.Observable
import rx.schedulers.Schedulers

class OrderListPresenterImplTest {

    @Test
    fun listensForOrderUpdatesAndAddsToList() {
        val mockView = mock(OrderListView::class.java)
        val listOfOrders = listOf(Order(), Order(), Order())
        val runUuid = "runUuid"
        val mockRepo = mock(OrderRepository::class.java).apply {
            `when`(getAddedOrders(runUuid)).thenReturn(Observable.from(listOfOrders))
        }
        val scheduler = Schedulers.immediate()
        val presenter = OrderListPresenterImpl(mockRepo, scheduler, scheduler).apply {
            view = mockView
        }

        presenter.listen(runUuid)

        verify(mockView, times(3)).addOrder(Order())
    }
}
