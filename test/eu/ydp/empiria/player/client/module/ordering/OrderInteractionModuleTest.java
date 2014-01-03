package eu.ydp.empiria.player.client.module.ordering;

import static org.mockito.Mockito.*;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import eu.ydp.empiria.player.client.module.ordering.presenter.OrderInteractionPresenter;
import eu.ydp.gwtutil.client.scheduler.Scheduler;

@RunWith(MockitoJUnitRunner.class)
public class OrderInteractionModuleTest {

	@InjectMocks
	private OrderInteractionModule testObj;
	@Mock
	private OrderInteractionModuleModel moduleModel;
	@Mock
	private OrderInteractionPresenter orderInteractionPresenter;
	@Mock
	private DragInitializingCommand dragInitializingCommand;
	@Mock
	private Scheduler scheduler;

	@Test
	public void shouldInitDragOnStart() {
		// when
		testObj.onStart();

		// then
		verify(scheduler).scheduleDeferred(dragInitializingCommand);
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
