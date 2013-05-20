package eu.ydp.empiria.player.client.module.connection.presenter;

import static org.mockito.Matchers.*;
import static org.mockito.Mockito.*;

import java.util.HashMap;
import java.util.Map;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.google.gwt.dom.client.Node;
import com.google.gwt.dom.client.NodeList;
import com.google.gwt.dom.client.Style;
import com.google.gwt.junit.GWTMockUtilities;
import com.google.gwt.xml.client.Document;
import com.google.gwt.xml.client.Element;

import eu.ydp.empiria.player.client.module.connection.exception.CssStyleException;
import eu.ydp.empiria.player.client.resources.StyleNameConstants;
import eu.ydp.empiria.player.client.style.StyleSocket;
import eu.ydp.empiria.player.client.util.style.CssHelper;
import eu.ydp.gwtutil.client.xml.XMLParser;
import eu.ydp.gwtutil.junit.runners.PrepareForTest;

@SuppressWarnings("PMD")
@PrepareForTest({ NodeList.class, Node.class, Style.class, CssHelper.class })
public class ConnectionStyleCheckerJUnitTest {

	@Mock
	private CssHelper cssHelper;
	@Mock
	private StyleNameConstants styleNames;
	@Mock
	private XMLParser xmlParser;
	@Mock
	private StyleSocket styleSocket;

	private ConnectionStyleChecker instance;
	private Map<String, String> styles = new HashMap<String, String>();

	@Before
	public void before() {
		MockitoAnnotations.initMocks(this);

		instance = new ConnectionStyleChecker(styleSocket, xmlParser, styleNames, cssHelper);

	}

	@BeforeClass
	public static void disarm() {
		GWTMockUtilities.disarm();
	}

	@AfterClass
	public static void rearm() {
		GWTMockUtilities.restore();
	}

	@Test
	public void testIsStylesAreCorrect() {
		styles = new HashMap<String, String>();
		styles.put("display", "inline");
		styles.put("width", "20px");

		Document document = mock(Document.class);
		Element element = mock(Element.class);
		when(document.getDocumentElement()).thenReturn(element);
		when(xmlParser.parse("<root><styleTest class=\"styleTest\"/></root>")).thenReturn(document);

		instance.cssClassNames.add("styleTest");

		instance.areStylesCorrectThrowsExceptionWhenNot(null);
	}

	@SuppressWarnings("unchecked")
	@Test(expected = CssStyleException.class)
	public void testIsStylesAreNotCorrect() {
		styles.put("display", "table-cell");
		styles.put("width", "20px");

		Document document = mock(Document.class);
		Element element = mock(Element.class);
		when(document.getDocumentElement()).thenReturn(element);
		when(xmlParser.parse("<root><styleTest class=\"styleTest\"/></root>")).thenReturn(document);

		instance.cssClassNames.add("styleTest");
		when(cssHelper.checkIfEquals(any(Map.class), eq("display"), eq("table-cell"))).thenReturn(true);

		instance.areStylesCorrectThrowsExceptionWhenNot(null);
	}
}
