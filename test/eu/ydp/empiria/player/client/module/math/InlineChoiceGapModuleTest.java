package eu.ydp.empiria.player.client.module.math;

import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mockito.Mockito;

import com.google.gwt.junit.GWTMockUtilities;
import com.google.inject.Binder;
import com.google.inject.Module;

import eu.ydp.empiria.player.client.AbstractTestBaseWithoutAutoInjectorInit;
import eu.ydp.empiria.player.client.util.events.bus.EventsBus;
import eu.ydp.empiria.player.client.util.events.player.PlayerEvent;
import eu.ydp.empiria.player.client.util.events.player.PlayerEventTypes;
import eu.ydp.empiria.player.client.util.events.scope.EventScope;
import eu.ydp.gwtutil.client.components.exlistbox.ExListBox;

@SuppressWarnings("PMD")
public class InlineChoiceGapModuleTest extends AbstractTestBaseWithoutAutoInjectorInit {
	InlineChoiceMathGapModule instance;
	ExListBox listBox;
	EventsBus eventsBus;

	@BeforeClass
	public static void disarm() {
		GWTMockUtilities.disarm();
	}

	@AfterClass
	public static void rearm() {
		GWTMockUtilities.restore();
	}

	private static class CustomGuiceModule implements Module {
		private final InlineChoiceMathGapModulePresenter presenter;

		public CustomGuiceModule(InlineChoiceMathGapModulePresenter presenter) {
			this.presenter = presenter;
		}

		@Override
		public void configure(Binder binder) {
			binder.bind(InlineChoiceMathGapModulePresenter.class).toInstance(presenter);
		}
	}

	@Before
	public void before() {
		InlineChoiceMathGapModulePresenter presenter = mock(InlineChoiceMathGapModulePresenter.class);
		listBox = mock(ExListBox.class);
		doReturn(listBox).when(presenter).getListBox();
		setUp(new Class<?>[] {}, new Class<?>[] {}, new Class<?>[] { EventsBus.class }, new CustomGuiceModule(presenter));
		eventsBus = injector.getInstance(EventsBus.class);
		instance = injector.getInstance(InlineChoiceMathGapModule.class);
	}

	@Test
	public void testPostConstruct() {
		verify(eventsBus).addHandler(Mockito.eq(PlayerEvent.getType(PlayerEventTypes.PAGE_SWIPE_STARTED)), Mockito.eq(instance), Mockito.any(EventScope.class));
	}

	@Test
	public void testOnPlayerEvent() {
		PlayerEvent event = mock(PlayerEvent.class);
		when(event.getType()).thenReturn(PlayerEventTypes.PAGE_SWIPE_STARTED);
		instance.onPlayerEvent(event);

		verify(listBox).hidePopup();
		Set<PlayerEventTypes> types = new HashSet<PlayerEventTypes>(Arrays.asList(PlayerEventTypes.values()));
		types.remove(PlayerEventTypes.PAGE_SWIPE_STARTED);
		for (PlayerEventTypes type : types) {
			when(event.getType()).thenReturn(type);
			instance.onPlayerEvent(event);
		}
		verify(listBox).hidePopup();
	}

	@After
	public void after() {

	}

}
