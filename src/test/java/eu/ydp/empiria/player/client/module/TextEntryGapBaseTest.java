package eu.ydp.empiria.player.client.module;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import com.google.gwt.xml.client.Element;
import com.google.inject.Provider;

import eu.ydp.empiria.player.client.controller.variables.objects.response.ResponseBuilder;
import eu.ydp.empiria.player.client.module.dragdrop.SourcelistManager;
import eu.ydp.empiria.player.client.module.textentry.DragContentController;
import eu.ydp.empiria.player.client.module.textentry.TextEntryGapBase;
import eu.ydp.empiria.player.client.module.textentry.TextEntryGapModule;
import eu.ydp.empiria.player.client.module.textentry.TextEntryModulePresenter;
import eu.ydp.empiria.player.client.util.events.internal.bus.EventsBus;
import eu.ydp.empiria.player.client.util.events.internal.scope.CurrentPageScope;
import eu.ydp.empiria.player.client.util.events.internal.state.StateChangeEvent;

@RunWith(MockitoJUnitRunner.class)
public class TextEntryGapBaseTest {

	public static class TestClass extends TextEntryGapBase {
	};

	@InjectMocks
	private TextEntryGapModule testObj;

	@Mock
	private SourcelistManager sourcelistManager;
	@Mock
	private DragContentController dragContentController;
	@Mock
	private TextEntryModulePresenter textEntryPresenter;
	@Mock
	private ResponseSocket responseSocket;
	@Mock
	private EventsBus eventsBus;
	@Mock
	private Element element;
	@Mock
	private Provider<CurrentPageScope> providerCurrentPageScope;

	@Test
	public void shouldUpdateStateAfterDragTest() {
		// given
		when(responseSocket.getResponse(anyString())).thenReturn(new ResponseBuilder().build());
		testObj.setResponseFromElement(element);

		// when
		testObj.setDragItem("aa");

		// then
		verify(eventsBus).fireEvent(any(StateChangeEvent.class), any(CurrentPageScope.class));
	}

}
