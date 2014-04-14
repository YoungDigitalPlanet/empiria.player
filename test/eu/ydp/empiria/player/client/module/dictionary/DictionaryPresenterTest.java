package eu.ydp.empiria.player.client.module.dictionary;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;

import eu.ydp.empiria.player.client.module.dictionary.view.DictionaryButtonView;
import eu.ydp.empiria.player.client.module.dictionary.view.DictionaryPopupView;

import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class DictionaryPresenterTest {
	@InjectMocks
	private DictionaryPresenter testObj;

	@Mock
	private DictionaryButtonView dictionaryButtonView;
	@Mock
	private DictionaryPopupView dictionaryPopupView;
	@Mock
	private ClickEvent event;

	@Captor
	private ArgumentCaptor<ClickHandler> clickCaptor;

	@Test
	public void shouldShowPopupOnClick() {
		// given
		testObj.bindUi();
		verify(dictionaryButtonView).addClickHandler(clickCaptor.capture());

		// when
		clickCaptor.getValue().onClick(event);

		// then
		verify(dictionaryPopupView).show();
	}

	@Test
	public void shouldHidePopupOnCloseButtonClick() {
		// given
		testObj.bindUi();
		verify(dictionaryPopupView).addClickHandler(clickCaptor.capture());

		// when
		clickCaptor.getValue().onClick(event);

		// then
		verify(dictionaryPopupView).hide();
	}
}
