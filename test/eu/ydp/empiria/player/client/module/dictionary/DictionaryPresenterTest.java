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

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class DictionaryPresenterTest {
	@InjectMocks
	private DictionaryPresenter testObj;

	@Mock
	private DictionaryButtonView dictionaryButtonView;
	@Mock
	private DictionaryPopupPresenter dictionaryPopupPresenter;
	@Captor
	private ArgumentCaptor<ClickHandler> clickCaptor;

	@Test
	public void shouldBindPopupPresenter() {
		// when
		testObj.bindUi();

		// then
		verify(dictionaryPopupPresenter).bindUi();
	}

	@Test
	public void shouldShowPopupOnClick() {
		// given
		ClickEvent event = mock(ClickEvent.class);

		// when
		testObj.bindUi();
		// then
		verify(dictionaryButtonView).addClickHandler(clickCaptor.capture());

		clickCaptor.getValue().onClick(event);
		verify(dictionaryPopupPresenter).show();
	}
}
