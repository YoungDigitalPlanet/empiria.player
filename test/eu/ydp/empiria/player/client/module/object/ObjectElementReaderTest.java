package eu.ydp.empiria.player.client.module.object;

import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.runners.MockitoJUnitRunner;

import com.google.gwt.xml.client.Element;

@RunWith(MockitoJUnitRunner.class)
public class ObjectElementReaderTest {

	@InjectMocks
	private ObjectElementReader testObj;

	@Test
	public void shouldReturnType() {
		// given
		final String expected = "elementType";
		Element element = MockElementFluentBuilder.newNode().withName("any").withAttribute("type", expected).build();

		// when
		String actual = testObj.getElementType(element);

		// then
		assertEquals(expected, actual);
	}

	@Test
	public void shouldReturnAudioType() {
		// given
		final String expected = "audio";
		Element element = MockElementFluentBuilder.newNode().withName("audioPlayer").withAttribute("type", "any").build();

		// when
		String actual = testObj.getElementType(element);

		// then
		assertEquals(expected, actual);

	}

	@Test
	public void shouldReturnNarrationTexts() {
		// given
		Element text1 = MockElementFluentBuilder.newText("foo").build();
		Element text2 = MockElementFluentBuilder.newText("bar").build();

		Element narrationText1 = MockElementFluentBuilder.newNode().withChildren(text1, text2).build();

		Element text3 = MockElementFluentBuilder.newText("baz").build();
		Element text4 = MockElementFluentBuilder.newText("qux").build();

		Element narrationText2 = MockElementFluentBuilder.newNode().withChildren(text3, text4).build();

		Element input = MockElementFluentBuilder.newNode().withChildrenTags("narrationScript", narrationText1, narrationText2).build();

		// when
		String actual = testObj.getNarrationText(input);

		// then
		assertEquals("foo bar baz qux ", actual);

	}
}
