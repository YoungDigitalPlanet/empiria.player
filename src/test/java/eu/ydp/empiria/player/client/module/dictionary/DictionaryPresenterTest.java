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

package eu.ydp.empiria.player.client.module.dictionary;

import com.google.gwt.dom.client.NativeEvent;
import eu.ydp.empiria.player.client.module.dictionary.external.controller.MainController;
import eu.ydp.empiria.player.client.module.dictionary.view.DictionaryButtonView;
import eu.ydp.empiria.player.client.module.dictionary.view.DictionaryPopupView;
import eu.ydp.gwtutil.client.event.factory.Command;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class DictionaryPresenterTest {
    @InjectMocks
    private DictionaryPresenter testObj;

    @Mock
    private DictionaryButtonView dictionaryButtonView;
    @Mock
    private DictionaryPopupView dictionaryPopupView;
    @Mock
    private NativeEvent event;
    @Mock
    private MainController mainController;

    @Captor
    private ArgumentCaptor<Command> clickCaptor;

    @Test
    public void shouldShowPopupOnClick() {
        // given
        testObj.bindUi();
        verify(dictionaryButtonView).addHandler(clickCaptor.capture());

        // when
        clickCaptor.getValue().execute(event);

        // then
        verify(dictionaryPopupView).show();
    }

    @Test
    public void shouldHidePopupOnCloseButtonClick() {
        // given
        testObj.bindUi();
        verify(dictionaryPopupView).addHandler(clickCaptor.capture());

        // when
        clickCaptor.getValue().execute(event);

        // then
        verify(dictionaryPopupView).hide();
    }
}
