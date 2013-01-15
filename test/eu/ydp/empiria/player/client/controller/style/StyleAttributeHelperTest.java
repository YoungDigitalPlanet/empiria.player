package eu.ydp.empiria.player.client.controller.style;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Map;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import com.google.gwt.xml.client.Element;

import eu.ydp.empiria.player.client.AbstractTestBase;
import eu.ydp.empiria.player.client.style.StyleSocket;

public class StyleAttributeHelperTest extends AbstractTestBase {

	private StyleSocket styleSocket;
	private Map<String, String> hashMap;
	private StyleSocketAttributeHelper instance;

	@Before
	public void before() {
		styleSocket = mock(StyleSocket.class);
		hashMap = mock(Map.class);
		when(styleSocket.getStyles(Mockito.any(Element.class))).thenReturn(hashMap);
		instance = spy(injector.getInstance(StyleSocketAttributeHelper.class));
	}

	@Test
	public void testGetString() {
		String value = "test";
		when(hashMap.get(Mockito.any())).thenReturn(value);
		String attribute = instance.getString(styleSocket, "nodeName", "attribute");
		verify(styleSocket).getStyles(Mockito.any(Element.class));
		verify(hashMap).get(Mockito.eq("attribute"));
		assertEquals(value, attribute);
	}

	@Test
	public void testGetBoolean() {
		when(hashMap.get(Mockito.any())).thenReturn("xxx");
		boolean attribute = instance.getBoolean(styleSocket, "nodeName", "attribute");
		assertEquals(false, attribute);
		when(hashMap.get(Mockito.any())).thenReturn("false");
		attribute = instance.getBoolean(styleSocket, "nodeName", "attribute");
		assertEquals(false, attribute);
		when(hashMap.get(Mockito.any())).thenReturn("true");
		attribute = instance.getBoolean(styleSocket, "nodeName", "attribute");
		assertEquals(true, attribute);
		when(hashMap.get(Mockito.any())).thenReturn("0");
		attribute = instance.getBoolean(styleSocket, "nodeName", "attribute");
		assertEquals(false, attribute);
		when(hashMap.get(Mockito.any())).thenReturn("1");
		attribute = instance.getBoolean(styleSocket, "nodeName", "attribute");
		assertEquals(true, attribute);
		when(hashMap.get(Mockito.any())).thenReturn("1.0");
		attribute = instance.getBoolean(styleSocket, "nodeName", "attribute");
		assertEquals(false, attribute);
	}













}
