package eu.ydp.empiria.player.client.module.tutor;

import static eu.ydp.empiria.player.client.util.events.state.StateChangeEventTypes.OUTCOME_STATE_CHANGED;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Answers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.runners.MockitoJUnitRunner;
import org.mockito.stubbing.Answer;

import com.google.gwt.xml.client.Element;

import eu.ydp.empiria.player.client.gin.factory.PageScopeFactory;
import eu.ydp.empiria.player.client.module.tutor.presenter.TutorPresenter;
import eu.ydp.empiria.player.client.module.tutor.view.TutorView;
import eu.ydp.empiria.player.client.util.events.bus.EventsBus;
import eu.ydp.empiria.player.client.util.events.player.PlayerEvent;
import eu.ydp.empiria.player.client.util.events.player.PlayerEventHandler;
import eu.ydp.empiria.player.client.util.events.player.PlayerEventTypes;
import eu.ydp.empiria.player.client.util.events.scope.CurrentPageScope;
import eu.ydp.empiria.player.client.util.events.scope.EventScope;
import eu.ydp.empiria.player.client.util.events.state.StateChangeEvent;
import eu.ydp.empiria.player.client.util.events.state.StateChangeEventHandler;
import eu.ydp.empiria.player.client.util.events.state.StateChangeEventTypes;
import eu.ydp.gwtutil.client.event.EventHandler;
import eu.ydp.gwtutil.client.event.EventImpl;
import eu.ydp.gwtutil.client.event.EventImpl.Type;

@RunWith(MockitoJUnitRunner.class)
public class TutorModuleTest {

	@InjectMocks
	private TutorModule tutorModule;
	@Mock
	private EventsBus eventsBus;
	@Mock
	private TutorPresenter presenter;
	@Mock
	private TutorView view;
	@Mock
	private ActionEventGenerator eventGenerator;
	@Mock(answer = Answers.RETURNS_MOCKS)
	private PageScopeFactory eventScopeFactory;

	private StateChangeEventHandler stateChangeHandler;
	private final Type<StateChangeEventHandler, StateChangeEventTypes> STATE_CHANGED_TYPE = StateChangeEvent.getType(OUTCOME_STATE_CHANGED);
	
	private PlayerEventHandler pageUnloadedHandler;
	private final Type<PlayerEventHandler, PlayerEventTypes> PAGE_UNLOADED_TYPE = PlayerEvent.getType(PlayerEventTypes.PAGE_UNLOADED);
	
	private PlayerEventHandler testPageLoadedHandler;
	private final Type<PlayerEventHandler, PlayerEventTypes> TEST_PAGE_LOADED_TYPE = PlayerEvent.getType(PlayerEventTypes.TEST_PAGE_LOADED);

	private TutorEventHandler tutorEventHandler;
	private final Type<TutorEventHandler, TutorEventTypes> TUTOR_CHANGED = TutorEvent.getType(TutorEventTypes.TUTOR_CHANGED);
	
	private final CurrentPageScope singlePageScope = mock(CurrentPageScope.class);

	@Test
	public void init() {
		// when
		tutorModule.initModule(mock(Element.class));

		// then
		verify(presenter).init();
	}

	@Test
	public void stateChangedEvent() {
		// given
		initEventHandlersInterception();
		mockScopeFactoryToReturnSinglePageScope();
		tutorModule.initModule(mock(Element.class));

		// when
		stateChangeHandler.onStateChange(mock(StateChangeEvent.class));

		// then
		verify(eventGenerator).stateChanged();
	}

	@Test
	public void pageUnloadedEvent() {
		// given
		initEventHandlersInterception();
		mockScopeFactoryToReturnSinglePageScope();
		tutorModule.initModule(mock(Element.class));

		// when
		pageUnloadedHandler.onPlayerEvent(mock(PlayerEvent.class));

		// then
		verify(eventGenerator).stop();
	}

	@Test
	public void testPageLoadedEvent() {
		// given
		initEventHandlersInterception();
		mockScopeFactoryToReturnSinglePageScope();
		tutorModule.initModule(mock(Element.class));

		// when
		testPageLoadedHandler.onPlayerEvent(mock(PlayerEvent.class));

		// then
		verify(eventGenerator).start();
	}
	
	@Test
	public void testTutorChangedEvent() {
		//given
		initEventHandlersInterception();
		mockScopeFactoryToReturnSinglePageScope();
		tutorModule.initModule(mock(Element.class));
		TutorEvent tutorEvent = mock(TutorEvent.class);
		
		//when
		tutorEventHandler.onTutorChanged(tutorEvent);
		
		//then
		verify(eventGenerator).tutorChanged(any(Integer.class));
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	private void initEventHandlersInterception() {
		doAnswer(new Answer<Void>() {

			@Override
			public Void answer(InvocationOnMock invocation) throws Throwable {
				EventImpl.Type type = (Type) invocation.getArguments()[0];
				Object handler = invocation.getArguments()[1];
				
				if (handler instanceof StateChangeEventHandler  &&  type == STATE_CHANGED_TYPE) {
					stateChangeHandler = (StateChangeEventHandler) handler;
				} else if (handler instanceof PlayerEventHandler && type == PAGE_UNLOADED_TYPE) {
					pageUnloadedHandler = (PlayerEventHandler) handler;
				} else if (handler instanceof PlayerEventHandler && type == TEST_PAGE_LOADED_TYPE) {
					testPageLoadedHandler = (PlayerEventHandler) handler;
				} else if(handler instanceof TutorEventHandler && type == TUTOR_CHANGED) {
					tutorEventHandler = (TutorEventHandler) handler;
				}
				
				return null;
			}
		}).when(eventsBus).addHandler(any(EventImpl.Type.class), any(EventHandler.class), any(EventScope.class));
	}

	private void mockScopeFactoryToReturnSinglePageScope() {
		when(eventScopeFactory.getCurrentPageScope()).thenReturn(singlePageScope);
	}
}
