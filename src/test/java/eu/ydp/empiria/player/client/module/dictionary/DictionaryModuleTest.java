package eu.ydp.empiria.player.client.module.dictionary;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import com.google.gwt.xml.client.Element;

import eu.ydp.empiria.player.client.module.dictionary.view.DictionaryButtonView;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class DictionaryModuleTest {

	@InjectMocks
	private DictionaryModule testObj;

	@Mock
	private DictionaryButtonView dictionaryButtonView;
	@Mock
	private DictionaryPresenter presenter;

	@Test
	public void shouldBindPresenterOnInit() {
		// given
		Element element = mock(Element.class);

		// when
		testObj.initModule(element);

		// then
		verify(presenter).bindUi();
	}
}
