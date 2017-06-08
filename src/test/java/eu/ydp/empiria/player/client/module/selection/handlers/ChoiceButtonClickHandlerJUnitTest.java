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

package eu.ydp.empiria.player.client.module.selection.handlers;

import com.google.gwt.event.dom.client.ClickEvent;
import eu.ydp.empiria.player.client.module.selection.controller.GroupAnswersController;
import eu.ydp.empiria.player.client.module.selection.presenter.SelectionModulePresenter;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class ChoiceButtonClickHandlerJUnitTest {

    private ChoiceButtonClickHandler handler;
    private GroupAnswersController groupAnswerController;
    private String buttonId;
    private SelectionModulePresenter selectionModulePresenter;

    @Before
    public void setUp() throws Exception {
        groupAnswerController = mock(GroupAnswersController.class);
        selectionModulePresenter = mock(SelectionModulePresenter.class);
        handler = new ChoiceButtonClickHandler(groupAnswerController, buttonId, selectionModulePresenter);
    }

    @After
    public void tearDown() throws Exception {
        Mockito.verifyNoMoreInteractions(groupAnswerController, selectionModulePresenter);
    }

    @Test
    public void testOnClick() {
        ClickEvent event = mock(ClickEvent.class);

        // then
        handler.onClick(event);

        verify(groupAnswerController).selectToggleAnswer(buttonId);
        verify(selectionModulePresenter).updateGroupAnswerView(groupAnswerController);
    }

}
