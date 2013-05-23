package eu.ydp.empiria.player.client.module.ordering.presenter;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import static org.mockito.Mockito.verify;


public class OrderItemClickListenerImplJUnitTest {

	private OrderItemClickListenerImpl clickListenerImpl;
	private OrderInteractionPresenter orderInteractionPresenter;
	
	@Before
	public void setUp() throws Exception {
		orderInteractionPresenter = Mockito.mock(OrderInteractionPresenter.class);
		clickListenerImpl = new OrderItemClickListenerImpl(orderInteractionPresenter);
	}
	
	@Test
	public void shouldCallPresenterWhenCalled() throws Exception {
		String childId = "childId";
		
		clickListenerImpl.itemClicked(childId);
		
		verify(orderInteractionPresenter).itemClicked(childId);
	}

}
