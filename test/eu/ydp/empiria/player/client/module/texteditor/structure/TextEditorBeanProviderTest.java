package eu.ydp.empiria.player.client.module.texteditor.structure;

import com.google.gwt.xml.client.Element;
import com.google.inject.Provider;
import com.peterfranza.gwt.jaxb.client.parser.JAXBParser;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.fest.assertions.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class TextEditorBeanProviderTest {

	@InjectMocks
	private TextEditorBeanProvider testObj;

	@Mock
	private TextEditorJAXBParserFactory jaxbFactory;
	@Mock
	private Provider<Element> elementProvider;
	@Mock
	private JAXBParser<TextEditorBean> parser;
	@Mock
	private Element element;

	@Test
	public void shouldParseElementContent() {
		// given
		final String CONTENT = "CONTENT";
		TextEditorBean expected = new TextEditorBean();

		when(elementProvider.get()).thenReturn(element);
		when(element.toString()).thenReturn(CONTENT);
		when(jaxbFactory.create()).thenReturn(parser);
		when(parser.parse(CONTENT)).thenReturn(expected);

		// when
		TextEditorBean actual = testObj.get();

		// then
		assertThat(actual).isEqualTo(expected);
	}
}