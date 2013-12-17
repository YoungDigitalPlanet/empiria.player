package eu.ydp.empiria.player.client.module.object;

import static eu.ydp.empiria.player.client.module.object.MockElementFluentBuilder.*;
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
		Element element = newNode().withName("any").withAttribute("type", expected).build();

		// when
		String actual = testObj.getElementType(element);

		// then
		assertEquals(expected, actual);
	}

	@Test
	public void shouldReturnAudioType() {
		// given
		final String expected = "audio";
		Element element = newNode().withName("audioPlayer").withAttribute("type", "any").build();

		// when
		String actual = testObj.getElementType(element);

		// then
		assertEquals(expected, actual);
	}

	@Test
	public void shouldReturnNarrationTexts() {
		// given
		Element text1 = newText("foo").build();
		Element text2 = newText("bar").build();
		Element narrationText1 = newNode().withChildren(text1, text2).build();

		Element text3 = newText("baz").build();
		Element text4 = newText("qux").build();
		Element narrationText2 = newNode().withChildren(text3, text4).build();

		Element input = newNode().withChildrenTags("narrationScript", narrationText1, narrationText2).build();

		// when
		String actual = testObj.getNarrationText(input);

		// then
		assertEquals("foo bar baz qux ", actual);
	}
}
