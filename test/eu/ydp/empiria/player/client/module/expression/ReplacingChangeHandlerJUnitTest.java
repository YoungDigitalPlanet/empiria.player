package eu.ydp.empiria.player.client.module.expression;

import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Map;

import org.junit.Test;

import com.google.common.collect.ImmutableMap;
import com.google.gwt.event.dom.client.KeyPressEvent;
import com.google.gwt.user.client.ui.HasValue;



public class ReplacingChangeHandlerJUnitTest {

	private ReplacingChangeHandler handler = new ReplacingChangeHandler();

	@SuppressWarnings("unchecked")
	@Test
	public void replace_foundElement() {
		// given
		HasValue<String> hasValue = mock(HasValue.class);
		when(hasValue.getValue()).thenReturn("");
		Map<String, String> replacements = ImmutableMap.of("a", "b", "c", "d");
		handler.init(hasValue, replacements);
		
		KeyPressEvent event = mock(KeyPressEvent.class);
		when(event.getCharCode()).thenReturn("a".charAt(0));
		
		// when
		handler.onKeyPress(event);
		
		// then
		verify(hasValue).setValue("b");
	}
	
	@SuppressWarnings("unchecked")
	@Test
	public void replace_foundElement_notEmptyGap() {
		// given
		HasValue<String> hasValue = mock(HasValue.class);
		when(hasValue.getValue()).thenReturn("x");
		Map<String, String> replacements = ImmutableMap.of("a", "b", "c", "d");
		handler.init(hasValue, replacements);
		
		KeyPressEvent event = mock(KeyPressEvent.class);
		when(event.getCharCode()).thenReturn("a".charAt(0));
		
		// when
		handler.onKeyPress(mock(KeyPressEvent.class));
		
		// then
		verify(hasValue, never()).setValue(anyString());
	}
	
	@SuppressWarnings("unchecked")
	@Test
	public void replace_notFoundElement() {
		// given
		HasValue<String> hasValue = mock(HasValue.class);
		when(hasValue.getValue()).thenReturn("");
		Map<String, String> replacements = ImmutableMap.of("a", "b", "c", "d");
		handler.init(hasValue, replacements);
		
		KeyPressEvent event = mock(KeyPressEvent.class);
		when(event.getCharCode()).thenReturn("x".charAt(0));
		
		// when
		handler.onKeyPress(event);
		
		// then
		verify(hasValue, never()).setValue(anyString());
	}
}
