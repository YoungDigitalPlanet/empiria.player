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
public class OrderInteractionModuleTest {

	@InjectMocks
	private OrderInteractionModule testObj;
	@Mock
	private OrderInteractionModuleModel moduleModel;
	@Mock
	private OrderInteractionPresenter orderInteractionPresenter;
	@Mock
	private DragController dragController;

	@Test
	public void shouldInitDragOnStart() {
		// when
		testObj.onStart();

		// then
		verify(dragController).init(any(OrderInteractionOrientation.class));
	}

	@Test
	public void shouldInitializeModuleModel() {
		// given

		// when
		testObj.initalizeModule();

		// then
		verify(moduleModel).initialize(testObj);
	}
}
