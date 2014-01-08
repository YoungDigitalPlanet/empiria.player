package eu.ydp.empiria.player.client.module.ordering;

import static org.mockito.Mockito.*;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import eu.ydp.empiria.player.client.module.ordering.drag.DragController;
import eu.ydp.empiria.player.client.module.ordering.presenter.OrderInteractionPresenter;
import eu.ydp.empiria.player.client.module.ordering.structure.OrderInteractionOrientation;

@RunWith(MockitoJUnitRunner.class)
public class DragInitializingCommandTest {

	@InjectMocks
	private DragInitializingCommand testObj;
	@Mock
	private DragController dragController;
	@Mock
	private OrderInteractionPresenter presenter;

	@Test
	public void shouldInitializeDrag() {
		// given
		when(presenter.getOrientation()).thenReturn(OrderInteractionOrientation.VERTICAL);

		// when
		testObj.execute();

		// then
		verify(dragController).init(OrderInteractionOrientation.VERTICAL);
	}
}
