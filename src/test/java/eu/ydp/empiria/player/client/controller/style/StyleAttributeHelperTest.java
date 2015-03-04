package eu.ydp.empiria.player.client.controller.style;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.*;

import java.util.HashMap;
import java.util.Map;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InOrder;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.google.gwt.xml.client.Document;
import com.google.gwt.xml.client.Element;

import eu.ydp.empiria.player.client.AbstractTestBase;
import eu.ydp.empiria.player.client.style.StyleSocket;
import eu.ydp.gwtutil.client.util.BooleanUtils;
import eu.ydp.gwtutil.client.xml.XMLParser;

public class StyleAttributeHelperTest extends AbstractTestBase {

	private Map<String, String> hashMap;

	@Mock
	private XMLParser xmlParser;
	@Mock
	private StyleSocket styleSocket;

	private StyleSocketAttributeHelper instance;

	@Before
	public void before() {
		MockitoAnnotations.initMocks(this);

		instance = new StyleSocketAttributeHelper(xmlParser, new BooleanUtils(), styleSocket);
	}

	@After
	public void after() {
		verifyNoMoreInteractions(styleSocket, xmlParser);
	}

	@Test
	public void testGetBoolean_false() {

		Document document = mock(Document.class);
		Element firstChild = mock(Element.class);
		Element element = mock(Element.class);
		when(document.getDocumentElement()).thenReturn(element);
		when(element.getFirstChild()).thenReturn(firstChild);
		String xmlConntent = "<root><nodeName class=\"nodeName\"/></root>";
		when(xmlParser.parse(xmlConntent)).thenReturn(document);
		Map<String, String> map = new HashMap<String, String>();
		map.put("attribute", "false");
		when(styleSocket.getStyles(firstChild)).thenReturn(map);

		boolean attribute = instance.getBoolean("nodeName", "attribute");

		assertFalse(attribute);

		InOrder inOrder = inOrder(styleSocket, xmlParser);
		inOrder.verify(xmlParser).parse(xmlConntent);
		inOrder.verify(styleSocket).getStyles(firstChild);

	}

	@Test
	public void testGetBoolean_true() {

		Document document = mock(Document.class);
		Element firstChild = mock(Element.class);
		Element element = mock(Element.class);
		when(document.getDocumentElement()).thenReturn(element);
		when(element.getFirstChild()).thenReturn(firstChild);
		String xmlConntent = "<root><nodeName class=\"nodeName\"/></root>";
		when(xmlParser.parse(xmlConntent)).thenReturn(document);
		Map<String, String> map = new HashMap<String, String>();
		map.put("attribute", "true");
		when(styleSocket.getStyles(firstChild)).thenReturn(map);

		boolean attribute = instance.getBoolean("nodeName", "attribute");

		assertTrue(attribute);

		InOrder inOrder = inOrder(styleSocket, xmlParser);
		inOrder.verify(xmlParser).parse(xmlConntent);
		inOrder.verify(styleSocket).getStyles(firstChild);
	}
}
