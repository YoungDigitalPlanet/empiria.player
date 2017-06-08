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

package eu.ydp.empiria.player.client.module.button.download.presenter;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import eu.ydp.empiria.player.client.controller.assets.AssetOpenDelegatorService;
import eu.ydp.empiria.player.client.module.button.download.structure.ButtonBean;
import eu.ydp.empiria.player.client.module.button.download.view.ButtonModuleView;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.runners.MockitoJUnitRunner;
import org.mockito.stubbing.Answer;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class ButtonModulePresenterTest {
    private static final String ID = "idd";
    private static final String ALT = "alt";
    private static final String URL = "http";
    @Mock
    private ButtonModuleView view;
    @Mock
    private AssetOpenDelegatorService assetOpenDelegatorService;
    @InjectMocks
    private ButtonModulePresenter instance;
    private ButtonBean bean;
    private ClickHandler clickHandler;

    @Before
    public void before() {
        bean = new ButtonBean();
        bean.setHref(URL);
        bean.setAlt(ALT);
        bean.setId(ID);
    }

    @Test
    public void init() {
        instance.setBean(bean);
        instance.init();
        verify(view).setUrl(eq(URL));
        verify(view).setDescription(eq(ALT));
        verify(view).setId(eq(ID));
        verify(view).addAnchorClickHandler(any(ClickHandler.class));
        verifyNoMoreInteractions(view);
    }

    @Test
    public void shouldOpenURl() {
        // given
        doAnswer(new Answer<Void>() {
            @Override
            public Void answer(InvocationOnMock invocation) throws Throwable {
                clickHandler = (ClickHandler) invocation.getArguments()[0];
                return null;
            }
        }).when(view).addAnchorClickHandler(any(ClickHandler.class));

        instance.setBean(bean);
        instance.init();

        ClickEvent clickEvent = mock(ClickEvent.class);

        // when
        clickHandler.onClick(clickEvent);

        // then
        verify(assetOpenDelegatorService).open(URL);
        verify(clickEvent).preventDefault();
    }

    @Test
    public void asWidget() {
        instance.asWidget();
        verify(view).asWidget();
    }
}
