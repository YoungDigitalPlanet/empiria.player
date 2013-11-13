package eu.ydp.empiria.player.client.module.tutor;

import static org.mockito.Mockito.*;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import eu.ydp.empiria.player.client.module.EndHandler;

@RunWith(MockitoJUnitRunner.class)
public class SingleRunTutorEndHandlerTest {

	@InjectMocks
	private SingleRunTutorEndHandler testObj;
	
	@Mock
	private ActionExecutorService executorService;
	
	@Test
	public void shouldNotCallHandlerWhenItIsNotProvided() {
		// given
		EndHandler mockEndHandler = mock(EndHandler.class);
		
		// when
		testObj.onEnd(true);
		testObj.onEnd(true);
		
		// then
		verify(mockEndHandler, times(0)).onEnd();
	}

	@Test
	public void shouldCallTheEndOnlyOnce() {
		// given
		EndHandler mockEndHandler = mock(EndHandler.class);
		
		// when
		testObj.setEndHandler(mockEndHandler);
		testObj.onEnd(true);
		testObj.onEnd(true);
		
		// then
		verify(mockEndHandler, times(1)).onEnd();
	}

	@Test
	public void shouldCallTheEndAfterResettingHandler() {
		// given
		EndHandler mockEndHandler = mock(EndHandler.class);
		
		// when
		testObj.setEndHandler(mockEndHandler);
		testObj.onEnd(true);
		testObj.setEndHandler(mockEndHandler);
		testObj.onEnd(true);
		
		// then
		verify(mockEndHandler, times(2)).onEnd();
	}

	@Test
	public void shouldCalltheDefaultAction() {
		// when
		testObj.onEnd(true);
		
		// then
		verify(executorService, times(1)).execute(ActionType.DEFAULT, testObj);
	}

	@Test
	public void shouldNotCalltheDefaultAction() {
		// when
		testObj.onEnd(false);
		
		// then
		verify(executorService, times(0)).execute(ActionType.DEFAULT, testObj);
	}
}
