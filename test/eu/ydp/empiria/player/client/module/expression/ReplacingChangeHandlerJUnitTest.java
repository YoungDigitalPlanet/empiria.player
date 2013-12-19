package eu.ydp.empiria.player.client.module.expression;

import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import com.google.common.collect.ImmutableMap;
import com.google.gwt.event.dom.client.KeyPressEvent;
import com.google.gwt.user.client.ui.HasValue;
import com.google.gwt.user.client.ui.IsWidget;

import eu.ydp.empiria.player.client.components.event.InputEventListener;
import eu.ydp.empiria.player.client.components.event.InputEventRegistrar;
import eu.ydp.gwtutil.client.Wrapper;

@RunWith(MockitoJUnitRunner.class)
public class ReplacingChangeHandlerJUnitTest {

	@InjectMocks
	private ReplacingChangeHandler handler = new ReplacingChangeHandler();
	
	@Mock
	private InputEventRegistrar eventRegistrar;

	@Test
	public void replace_foundElement() {
		// given
		TextBoxMock hasValue = mock(TextBoxMock.class);
		when(hasValue.getValue()).thenReturn("a");
		ExpressionReplacer replacer = new ExpressionReplacer();
		replacer.useReplacements(ImmutableMap.of("a", "b", "c", "d"));
		
		KeyPressEvent event = mock(KeyPressEvent.class);
		when(event.getCharCode()).thenReturn("a".charAt(0));
		
		ArgumentCaptor<InputEventListener> listenerCaptor = ArgumentCaptor.forClass(InputEventListener.class);
		handler.init(Wrapper.of(hasValue), replacer);
		verify(eventRegistrar).registerInputHandler(eq(hasValue), listenerCaptor.capture());
		
		// when
		listenerCaptor.getValue().onInput();
		
		// then
		verify(hasValue).setValue("b", true);
	}
	
	@Test
	public void replace_foundOtherElement() {
		// given
		TextBoxMock hasValue = mock(TextBoxMock.class);
		when(hasValue.getValue()).thenReturn("x");
		ExpressionReplacer replacer = new ExpressionReplacer();
		replacer.useReplacements(ImmutableMap.of("a", "b", "c", "d"));
		
		ArgumentCaptor<InputEventListener> listenerCaptor = ArgumentCaptor.forClass(InputEventListener.class);
		handler.init(Wrapper.of(hasValue), replacer);
		verify(eventRegistrar).registerInputHandler(eq(hasValue), listenerCaptor.capture());
		
		// when
		listenerCaptor.getValue().onInput();
		
		// then
		verify(hasValue, never()).setValue(anyString());
	}
	
	@Test
	public void replace_emptyGap() {
		// given
		TextBoxMock hasValue = mock(TextBoxMock.class);
		when(hasValue.getValue()).thenReturn("");
		ExpressionReplacer replacer = new ExpressionReplacer();
		replacer.useReplacements(ImmutableMap.of("a", "b", "c", "d"));
		
		ArgumentCaptor<InputEventListener> listenerCaptor = ArgumentCaptor.forClass(InputEventListener.class);
		handler.init(Wrapper.of(hasValue), replacer);
		verify(eventRegistrar).registerInputHandler(eq(hasValue), listenerCaptor.capture());
		
		// when
		listenerCaptor.getValue().onInput();
		
		// then
		verify(hasValue, never()).setValue(anyString());
	}
	
	private static interface TextBoxMock extends IsWidget, HasValue<String> { }
}
