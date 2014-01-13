package eu.ydp.empiria.player.client.module.textentry;

import static org.mockito.Matchers.*;
import static org.mockito.Mockito.*;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import com.google.gwt.junit.GWTMockUtilities;
import com.google.inject.Provider;

import eu.ydp.empiria.player.client.util.events.bus.EventsBus;
import eu.ydp.empiria.player.client.util.events.player.PlayerEvent;
import eu.ydp.empiria.player.client.util.events.player.PlayerEventHandler;
import eu.ydp.empiria.player.client.util.events.player.PlayerEventTypes;
import eu.ydp.empiria.player.client.util.events.scope.CurrentPageScope;
import eu.ydp.gwtutil.client.event.EventImpl.Type;
import eu.ydp.gwtutil.junit.runners.ExMockRunner;

@RunWith(ExMockRunner.class)
public class TextEntryGapBaseTest {

	@InjectMocks
	private TextEntryGapBaseMock testObj;
	@Mock
	private EventsBus eventsBus;
	@Mock
	private Provider<CurrentPageScope> pageScopeProvider;
	@Mock
	private CurrentPageScope currentPageScope;

	private Type<PlayerEventHandler, PlayerEventTypes> eventType;
	private PlayerEventHandler playerEventHandler;

	@BeforeClass
	public static void before() {
		GWTMockUtilities.disarm();
	}

	@Before
	public void setup() {
		testObj = spy(new TextEntryGapBaseMock());
		MockitoAnnotations.initMocks(this);
		preparePageScope();
		prepareHandler();
	}

	@Test
	public void shouldNotFireStateChangedEventOnBeforeFlow() {
		// given
		testObj.setPresenter(mock(TextEntryGapModulePresenterBase.class));
		testObj.addPlayerEventHandlers();

		// when
		playerEventHandler.onPlayerEvent(new PlayerEvent(PlayerEventTypes.BEFORE_FLOW));

		// then
		verify(testObj, never()).updateResponse(anyBoolean(), anyBoolean());

	}

	private void preparePageScope() {
		currentPageScope = mock(CurrentPageScope.class);
		when(pageScopeProvider.get()).thenReturn(currentPageScope);
	}

	private void prepareHandler() {
		eventType = PlayerEvent.getType(PlayerEventTypes.BEFORE_FLOW);

		Answer<Void> answer = new Answer<Void>() {
			@Override
			public Void answer(InvocationOnMock invocation) throws Throwable {
				playerEventHandler = (PlayerEventHandler) invocation.getArguments()[1];
				return null;
			}

		};
		doAnswer(answer).when(eventsBus).addHandler(eq(eventType), any(PlayerEventHandler.class), eq(currentPageScope));
	}

	@AfterClass
	public static void after() {
		GWTMockUtilities.restore();
	}
}
