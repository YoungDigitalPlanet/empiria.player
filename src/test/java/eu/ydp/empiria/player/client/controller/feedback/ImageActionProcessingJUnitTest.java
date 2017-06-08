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

package eu.ydp.empiria.player.client.controller.feedback;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import com.google.common.collect.Lists;
import eu.ydp.empiria.player.client.controller.feedback.processor.ImageActionProcessor;
import eu.ydp.empiria.player.client.controller.feedback.structure.action.*;

import java.util.List;

import eu.ydp.empiria.player.client.module.core.base.HasChildren;
import eu.ydp.empiria.player.client.module.core.base.IModule;
import org.junit.Test;
import org.mockito.ArgumentCaptor;

public class ImageActionProcessingJUnitTest extends ProcessingFeedbackActionTestBase {

    private ImageActionProcessor imageProcessor;

    @Test
    public void shouldProcessSingleImageAction() { // given
        List<FeedbackAction> actions = ActionListBuilder.create().addUrlAction(ActionType.NARRATION, "good.mp3").addUrlAction(ActionType.IMAGE, "good.jpg")
                                                        .getList();

        initializeWithActions(actions);
        initializeModuleHierarchyWithImageProcessor();

        // when
        processor.processActions(source);

        // then
        ArgumentCaptor<FeedbackAction> argument = ArgumentCaptor.forClass(FeedbackAction.class);
        verify(imageProcessor).processSingleAction(argument.capture(), eq(FeedbackMark.OK));
        FeedbackAction processedAction = argument.getValue();

        assertThat(argument.getAllValues().size(), is(equalTo(1)));
        assertThat(processedAction, is(instanceOf(ShowUrlAction.class)));
        assertThat(((ShowUrlAction) processedAction).getHref(), is(equalTo("good.jpg")));
        assertThat(collector.getActions().size(), is(equalTo(0)));
    }

    @Test
    public void shouldProcessManyImageActions() {
        // given
        List<FeedbackAction> actions = ActionListBuilder.create().addUrlAction(ActionType.NARRATION, "good.mp3").addUrlAction(ActionType.IMAGE, "good.jpg")
                                                        .addUrlAction(ActionType.NARRATION, "allok.mp3").addUrlAction(ActionType.IMAGE, "bad.jpg").getList();

        initializeWithActions(actions);
        initializeModuleHierarchyWithImageProcessor();

        // when
        processor.processActions(source);

        // then
        ArgumentCaptor<FeedbackAction> argument = ArgumentCaptor.forClass(FeedbackAction.class);
        verify(imageProcessor, times(2)).processSingleAction(argument.capture(), eq(FeedbackMark.OK));
        assertThat(collector.getActions().size(), is(equalTo(0)));
        List<FeedbackAction> processedActions = argument.getAllValues();

        FeedbackAction processedAction1 = processedActions.get(0);
        assertThat(processedAction1, is(instanceOf(ShowUrlAction.class)));
        assertThat(((ShowUrlAction) processedAction1).getHref(), is(equalTo("good.jpg")));

        FeedbackAction processedAction2 = processedActions.get(1);
        assertThat(processedAction2, is(instanceOf(ShowUrlAction.class)));
        assertThat(((ShowUrlAction) processedAction2).getHref(), is(equalTo("bad.jpg")));
    }

    private void initializeModuleHierarchyWithImageProcessor() {
        HasChildren parentModule = mock(HasChildren.class);
        imageProcessor = spy(injector.getInstance(ImageActionProcessor.class));

        when(source.getParentModule()).thenReturn(parentModule);
        when(parentModule.getChildren()).thenReturn(Lists.newArrayList(mock(IModule.class), mock(IModule.class), source, imageProcessor));
    }

}
