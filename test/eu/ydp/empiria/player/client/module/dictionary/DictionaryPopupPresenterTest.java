package eu.ydp.empiria.player.client.module.dictionary;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;

import eu.ydp.empiria.player.client.module.dictionary.view.DictionaryPopupView;
import eu.ydp.gwtutil.client.proxy.RootPanelDelegate;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class DictionaryPopupPresenterTest {

	@InjectMocks
	private DictionaryPopupPresenter testObj;

	@Mock
	private DictionaryPopupView dictionaryPopupView;
	@Mock
	private RootPanelDelegate rootPanelDelegate;

	@Test
	public void shouldClosePopupOnClick() {
		// given
		ClickEvent event = mock(ClickEvent.class);
		ArgumentCaptor<ClickHandler> argument = ArgumentCaptor.forClass(ClickHandler.class);

		// when
		testObj.bindUi();

		// then
		verify(dictionaryPopupView).addClickHandler(argument.capture());

		argument.getValue().onClick(event);
		verify(dictionaryPopupView).hide();
	}

	@Test
	public void shouldDisplayView() {
		// when
		testObj.show();
		// then
		verify(dictionaryPopupView).show();
	}
}
