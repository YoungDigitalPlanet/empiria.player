package eu.ydp.empiria.player.client.module.mediator.powerfeedback;

import static eu.ydp.empiria.player.client.util.events.state.StateChangeEventTypes.OUTCOME_STATE_CHANGED;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InOrder;
import org.mockito.Mockito;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import eu.ydp.empiria.player.client.controller.events.interaction.StateChangedInteractionEvent;
import eu.ydp.empiria.player.client.gin.factory.PageScopeFactory;
import eu.ydp.empiria.player.client.module.EndHandler;
import eu.ydp.empiria.player.client.module.IUniqueModule;
import eu.ydp.empiria.player.client.util.events.bus.EventsBus;
import eu.ydp.empiria.player.client.util.events.player.PlayerEvent;
import eu.ydp.empiria.player.client.util.events.player.PlayerEventHandler;
import eu.ydp.empiria.player.client.util.events.player.PlayerEventTypes;
import eu.ydp.empiria.player.client.util.events.scope.EventScope;
import eu.ydp.empiria.player.client.util.events.state.StateChangeEvent;
import eu.ydp.empiria.player.client.util.events.state.StateChangeEventHandler;
import eu.ydp.empiria.player.client.util.events.state.StateChangeEventTypes;
import eu.ydp.gwtutil.client.event.EventHandler;
import eu.ydp.gwtutil.client.event.EventImpl;
import eu.ydp.gwtutil.client.event.EventImpl.Type;

public class PowerFeedbackMediatorTest {

	private PowerFeedbackMediator mediator;
	private EventsBus eventsBus;
	private PageScopeFactory pageScopeFactory;
	private PowerFeedbackTutorClient tutor;
	private PowerFeedbackBonusClient bonus;
	private StateChangeEventHandler stateChangedHandler;
	private PlayerEventHandler testPageLoadedHandler;
	private PlayerEventHandler pageUnloadedhandler;

	@Before
	public void setUp() {
		initMocks();
		initEventHandlersInterception();
		mediator = new PowerFeedbackMediator(eventsBus, pageScopeFactory, new NullPowerFeedbackTutorClient(), new NullPowerFeedbackBonusClient());
	}

	@Test
	public void shouldInitTutor() {
		// given
		PlayerEvent event = mock(PlayerEvent.class);
		mediator.registerTutor(tutor);

		// when
		testPageLoadedHandler.onPlayerEvent(event);

		// then
		verify(tutor).initPowerFeedbackClient();
	}

	@Test
	public void shouldResetClients() {
		// given
		StateChangeEvent event = mockStateChangeEvent(false, true);
		mediator.registerBonus(bonus);
		mediator.registerTutor(tutor);

		// when
		stateChangedHandler.onStateChange(event);

		// then
		verify(bonus).resetPowerFeedback();
		verify(tutor).resetPowerFeedback();
	}

	@Test
	public void shouldNotifyClientsOnStateChanged_onlyTutor() {
		// given
		StateChangeEvent event = mockStateChangeEvent(true, false);
		mediator.registerTutor(tutor);

		// when
		stateChangedHandler.onStateChange(event);

		// then
		verify(tutor).processUserInteraction(any(EndHandler.class));
	}

	@Test
	public void shouldNotifyClientsOnStateChanged_onlyBonus() {
		// given
		StateChangeEvent event = mockStateChangeEvent(true, false);
		mediator.registerBonus(bonus);

		// when
		stateChangedHandler.onStateChange(event);

		// then
		verify(bonus).processUserInteraction();
	}

	@Test
	public void shouldNotifyClientsOnStateChanged_bothClients() {
		// given
		StateChangeEvent event = mockStateChangeEvent(true, false);
		doAnswer(new Answer<Void>() {

			@Override
			public Void answer(InvocationOnMock invocation) throws Throwable {
				EndHandler handler = (EndHandler) invocation.getArguments()[0];
				handler.onEnd();
				return null;
			}
		}).when(tutor).processUserInteraction(any(EndHandler.class));
		mediator.registerTutor(tutor);
		mediator.registerBonus(bonus);

		// when
		stateChangedHandler.onStateChange(event);

		// then
		InOrder inOrder = Mockito.inOrder(tutor, bonus);
		inOrder.verify(tutor).processUserInteraction(any(EndHandler.class));
		inOrder.verify(bonus).processUserInteraction();
	}

	@Test
	public void shouldTerminateClientsOnPageChange() {
		// given
		PlayerEvent event = mock(PlayerEvent.class);
		mediator.registerTutor(tutor);
		mediator.registerBonus(bonus);

		// when
		pageUnloadedhandler.onPlayerEvent(event);

		// then
		verify(bonus).terminatePowerFeedback();
		verify(tutor).terminatePowerFeedback();
	}

	private void initMocks() {
		eventsBus = mock(EventsBus.class);
		pageScopeFactory = mock(PageScopeFactory.class);
		tutor = mock(PowerFeedbackTutorClient.class);
		bonus = mock(PowerFeedbackBonusClient.class);
	}

	private StateChangeEvent mockStateChangeEvent(boolean isUserInteract, boolean isReset) {
		StateChangeEvent event = mock(StateChangeEvent.class);
		IUniqueModule sender = mock(IUniqueModule.class);
		StateChangedInteractionEvent scie = new StateChangedInteractionEvent(isUserInteract, isReset, sender);
		when(event.getValue()).thenReturn(scie);
		return event;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	private void initEventHandlersInterception() {
		doAnswer(new Answer<Void>() {

			final Type<StateChangeEventHandler, StateChangeEventTypes> STATE_CHANGED_TYPE = StateChangeEvent.getType(OUTCOME_STATE_CHANGED);
			final Type<PlayerEventHandler, PlayerEventTypes> PAGE_UNLOADED_TYPE = PlayerEvent.getType(PlayerEventTypes.PAGE_UNLOADED);
			final Type<PlayerEventHandler, PlayerEventTypes> TEST_PAGE_LOADED_TYPE = PlayerEvent.getType(PlayerEventTypes.TEST_PAGE_LOADED);

			@Override
			public Void answer(InvocationOnMock invocation) throws Throwable {
				EventImpl.Type type = (Type) invocation.getArguments()[0];
				Object handler = invocation.getArguments()[1];

				if (handler instanceof StateChangeEventHandler && type == STATE_CHANGED_TYPE) {
					stateChangedHandler = (StateChangeEventHandler) handler;
				} else if (handler instanceof PlayerEventHandler && type == PAGE_UNLOADED_TYPE) {
					pageUnloadedhandler = (PlayerEventHandler) handler;
				} else if (handler instanceof PlayerEventHandler && type == TEST_PAGE_LOADED_TYPE) {
					testPageLoadedHandler = (PlayerEventHandler) handler;
				}

				return null;
			}
		}).when(eventsBus).addHandler(any(EventImpl.Type.class), any(EventHandler.class), any(EventScope.class));
	}
}
