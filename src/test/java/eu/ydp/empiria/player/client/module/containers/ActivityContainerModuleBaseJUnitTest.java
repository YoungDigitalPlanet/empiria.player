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

package eu.ydp.empiria.player.client.module.containers;

import com.google.common.collect.Lists;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.xml.client.Element;
import eu.ydp.empiria.player.client.module.ModuleSocket;
import eu.ydp.empiria.player.client.module.core.base.IModule;
import eu.ydp.empiria.player.client.util.events.internal.bus.EventsBus;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.mockito.Mockito.*;

public class ActivityContainerModuleBaseJUnitTest {

    private AbstractActivityContainerModuleBase activityContainerMock;

    @Test
    public void resetShouldExitShowingAnswers() {
        activityContainerMock.showCorrectAnswers(true);
        activityContainerMock.reset();

        verify(activityContainerMock).doShowCorrectAnswers(false);
    }

    @Test
    public void showCorrectAnswersShouldCallDoShowCorrectAnswersWhenNotInAnswerMode() {
        activityContainerMock.showCorrectAnswers(true);
        activityContainerMock.showCorrectAnswers(true);
        activityContainerMock.showCorrectAnswers(false);
        activityContainerMock.showCorrectAnswers(false);

        verify(activityContainerMock, times(1)).doShowCorrectAnswers(true);
        verify(activityContainerMock, times(1)).doShowCorrectAnswers(false);
    }

    @Test
    public void markAnswersShouldCallDoMarkAnswersWhenNotMarkingAnswers() {
        activityContainerMock.markAnswers(true);
        activityContainerMock.markAnswers(true);
        activityContainerMock.markAnswers(false);
        activityContainerMock.markAnswers(false);

        verify(activityContainerMock, times(1)).doMarkAnswers(true);
        verify(activityContainerMock, times(1)).doMarkAnswers(false);
    }

    @Test
    public void markAnswersCallsDisableShowCorrectAnswersWhenShowingAnswers() {
        activityContainerMock.showCorrectAnswers(true);
        activityContainerMock.markAnswers(true);

        verify(activityContainerMock, times(1)).showCorrectAnswers(true);
        verify(activityContainerMock, times(1)).doShowCorrectAnswers(true);
        verify(activityContainerMock, times(1)).markAnswers(true);

        verify(activityContainerMock, times(1)).showCorrectAnswers(false);
        verify(activityContainerMock, times(1)).doMarkAnswers(true);
        verify(activityContainerMock, times(1)).doShowCorrectAnswers(false);
        verify(activityContainerMock, times(2)).markAnswers(false);

        verify(activityContainerMock, times(0)).doMarkAnswers(false);
    }

    @Test
    public void showCorrectAnswersCallsDisablemarkAnswersWhenMarkingAnswers() {
        activityContainerMock.markAnswers(true);
        activityContainerMock.showCorrectAnswers(true);

        verify(activityContainerMock, times(1)).markAnswers(true);
        verify(activityContainerMock, times(1)).showCorrectAnswers(true);

        verify(activityContainerMock, times(1)).markAnswers(false);
        verify(activityContainerMock, times(1)).doMarkAnswers(false);
        verify(activityContainerMock, times(2)).showCorrectAnswers(false);

        verify(activityContainerMock, times(0)).doShowCorrectAnswers(false);
    }

    @Test
    public void markAnswersFalseDoNotDisablesShowingAnswers() {
        activityContainerMock.showCorrectAnswers(true);
        activityContainerMock.markAnswers(false);

        verify(activityContainerMock, times(0)).doShowCorrectAnswers(false);
    }

    @Test
    public void showAnswersFalseDoNotDisablesMarkingAnswers() {
        activityContainerMock.markAnswers(true);
        activityContainerMock.showCorrectAnswers(false);

        verify(activityContainerMock, times(0)).doMarkAnswers(false);
    }

    @Before
    public void createMockActivityContainerModuleBase() {
        activityContainerMock = spy(new AbstractActivityContainerModuleBase() {
            @Override
            public void initModule(Element element) {
            }

            @Override
            public Widget getView() {
                return null;
            }

            @Override
            public List<IModule> getChildren() {
                return Lists.newArrayList();
            }
        });
        activityContainerMock.initModule(mock(ModuleSocket.class), mock(EventsBus.class));
    }
}
