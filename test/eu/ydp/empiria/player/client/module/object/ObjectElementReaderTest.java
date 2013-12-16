package eu.ydp.empiria.player.client.module.object;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

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
		Element element = mock(Element.class);
		when(element.getAttribute("type")).thenReturn(expected);
		
		// when
		String actual = testObj.getElementType(element);
		
		// then
		assertEquals(expected, actual);
	}

}
