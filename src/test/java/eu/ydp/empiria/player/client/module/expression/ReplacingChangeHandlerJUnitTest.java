/*
 * Copyright 2017 Young Digital Planet S.A.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */

package eu.ydp.empiria.player.client.module.expression;

import com.google.common.collect.ImmutableMap;
import com.google.gwt.event.dom.client.KeyPressEvent;
import com.google.gwt.user.client.ui.HasValue;
import com.google.gwt.user.client.ui.IsWidget;
import eu.ydp.empiria.player.client.components.event.InputEventListener;
import eu.ydp.empiria.player.client.components.event.InputEventRegistrar;
import eu.ydp.gwtutil.client.Wrapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.*;

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

    private static interface TextBoxMock extends IsWidget, HasValue<String> {
    }
}
