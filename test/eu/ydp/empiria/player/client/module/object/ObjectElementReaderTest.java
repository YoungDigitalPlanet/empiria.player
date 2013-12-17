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
		Element element = newNode().withTag("any").withAttribute("type", expected).build();

		// when
		String actual = testObj.getElementType(element);

		// then
		assertEquals(expected, actual);
	}

	@Test
	public void shouldReturnAudioType() {
		// given
		final String expected = "audio";
		Element element = newNode().withTag("audioPlayer").withAttribute("type", "any").build();

		// when
		String actual = testObj.getElementType(element);

		// then
		assertEquals(expected, actual);
	}

	@Test
	public void shouldReturnNarrationTexts() {
		// given
		Element input = narrationTextNode();

		// when
		String actual = testObj.getNarrationText(input);

		// then
		assertEquals("foo bar baz qux ", actual);
	}

	@Test
	public void shouldReturnDefaultTemplate() {
		// given
		Element defaultTemplateElement = newNode().withTag("template").build();
		Element fullscreenTemplateElement = newNode().withTag("template").withAttribute("type", "fullscreen").build();
		
		Element input = newNode().withChildrenTags("template", defaultTemplateElement, fullscreenTemplateElement).build();
		
		// when
		Element actual = testObj.getDefaultTemplate(input);
		
		// then
		assertEquals(defaultTemplateElement, actual);
	}
	
	@Test
	public void shouldReturnFullscreenTemplate() {
		// given
		Element defaultTemplateElement = newNode().withTag("template").build();
		Element fullscreenTemplateElement = newNode().withTag("template").withAttribute("type", "fullscreen").build();
		
		Element input = newNode().withChildrenTags("template", defaultTemplateElement, fullscreenTemplateElement).build();
		
		// when
		Element actual = testObj.getFullscreenTemplate(input);
		
		// then
		assertEquals(fullscreenTemplateElement, actual);
	}
	
	@Test
	public void shouldReturnWidthFromAttribute() {
		// given
		Element element = newNode().withAttribute("width", "100").build();
		
		// when
		int actual = testObj.getWidthOrDefault(element, -100);
		
		// then
		assertEquals(100, actual);
	}
	
	@Test
	public void shouldReturnDefaultWidthIfZero() {
		// given
		final int expected = 100;
		Element element = newNode().build();
		
		// when
		int actual = testObj.getWidthOrDefault(element, expected);
		
		// then
		assertEquals(expected, actual);
	}

	@Test
	public void shouldReturnHeightFromAttribute() {
		// given
		Element element = newNode().withAttribute("height", "100").build();
		
		// when
		int actual = testObj.getHeightOrDefault(element, -100);
		
		// then
		assertEquals(100, actual);
	}
	
	@Test
	public void shouldReturnDefaultHeightIfZero() {
		// given
		final int expected = 100;
		Element element = newNode().build();
		
		// when
		int actual = testObj.getHeightOrDefault(element, expected);
		
		// then
		assertEquals(expected, actual);
	}

	private Element narrationTextNode() {
		Element text1 = newText("foo").build();
		Element text2 = newText("bar").build();
		Element narrationText1 = newNode().withChildren(text1, text2).build();

		Element text3 = newText("baz").build();
		Element text4 = newText("qux").build();
		Element narrationText2 = newNode().withChildren(text3, text4).build();

		Element commentNode = newComment().withValue("someComment").build();
		
		Element input = newNode().withChildrenTags("narrationScript", narrationText1, narrationText2, commentNode).build();
		return input;
	}
}
