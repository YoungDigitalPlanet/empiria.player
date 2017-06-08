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

package eu.ydp.empiria.player.client.controller.multiview.animation;

import com.google.gwt.dom.client.Style;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.user.client.Element;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwtmockito.GwtMockitoTestRunner;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.fest.assertions.api.Assertions.assertThat;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.*;

@RunWith(GwtMockitoTestRunner.class)
public class NoAnimationTest {

    private final NoAnimation instance = new NoAnimation();
    @Mock
    private FlowPanel flowPanel;
    @Mock
    private Element element;
    @Mock
    private Style style;
    @Mock
    private AnimationEndCallback animationEndCallback;

    @Before
    public void before() {
        MockitoAnnotations.initMocks(this);
        doReturn(style).when(element).getStyle();
        doReturn(element).when(flowPanel).getElement();
    }

    @Test
    public void goTo() throws Exception {
        instance.goTo(flowPanel, 100, 90);
        verify(style).setLeft(eq(100d), eq(Unit.PCT));

        instance.goTo(flowPanel, 120, 9);
        verify(style).setLeft(eq(120d), eq(Unit.PCT));

    }

    @Test
    public void addAnimationEndCallback() throws Exception {
        instance.addAnimationEndCallback(animationEndCallback);
        instance.goTo(flowPanel, 100, 90);
        verify(animationEndCallback).onComplate(eq(100));
    }

    @Test
    public void removeAnimationEndCallback() throws Exception {
        instance.addAnimationEndCallback(animationEndCallback);
        instance.removeAnimationEndCallback(animationEndCallback);
        instance.goTo(flowPanel, 100, 90);
        verifyZeroInteractions(animationEndCallback);
    }

    @Test
    public void getPositionX() throws Exception {
        instance.goTo(flowPanel, 100, 90);
        assertThat(instance.getPositionX()).isEqualTo(100d);

        instance.goTo(flowPanel, 110, 90);
        assertThat(instance.getPositionX()).isEqualTo(110d);
    }

    @Test
    public void isRunning() throws Exception {
        assertThat(instance.isRunning()).isFalse();
        instance.goTo(flowPanel, 100, 90);
        assertThat(instance.isRunning()).isFalse();
    }

}
