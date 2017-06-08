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

package eu.ydp.empiria.player.client.module.draggap.view;

import eu.ydp.empiria.player.client.module.draggap.DragGapStyleNameConstants;
import eu.ydp.empiria.player.client.module.selection.model.UserAnswerType;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import static org.fest.assertions.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

public class DragGapStylesProviderTest {

    private DragGapStylesProvider dragGapStylesProvider;
    private DragGapStyleNameConstants styleNameConstants;

    @Before
    public void setUp() throws Exception {
        styleNameConstants = Mockito.mock(DragGapStyleNameConstants.class);
        dragGapStylesProvider = new DragGapStylesProvider(styleNameConstants);
    }

    @Test
    public void shouldSetCorrectStyleWhenAnswerTypeIsCorrect() throws Exception {
        String expectedStyleName = "correct";
        when(styleNameConstants.QP_DRAG_GAP_CORRECT()).thenReturn(expectedStyleName);

        String styleName = dragGapStylesProvider.getCorrectGapStyleName(UserAnswerType.CORRECT);

        assertThat(styleName).isEqualTo(expectedStyleName);
    }

    @Test
    public void shouldSetWrongStyleWhenAnswerTypeIsWrong() throws Exception {
        String expectedStyleName = "wrong";
        when(styleNameConstants.QP_DRAG_GAP_WRONG()).thenReturn(expectedStyleName);

        String styleName = dragGapStylesProvider.getCorrectGapStyleName(UserAnswerType.WRONG);

        assertThat(styleName).isEqualTo(expectedStyleName);
    }

    @Test
    public void shouldSetDefaultStyleWhenAnswerTypeIsDefault() throws Exception {
        String expectedStyleName = "default";
        when(styleNameConstants.QP_DRAG_GAP_DEFAULT()).thenReturn(expectedStyleName);

        String styleName = dragGapStylesProvider.getCorrectGapStyleName(UserAnswerType.DEFAULT);

        assertThat(styleName).isEqualTo(expectedStyleName);
    }

    @Test
    public void shouldSetNoneStyleWhenAnswerTypeIsNone() throws Exception {
        String expectedStyleName = "none";
        when(styleNameConstants.QP_DRAG_GAP_NONE()).thenReturn(expectedStyleName);

        String styleName = dragGapStylesProvider.getCorrectGapStyleName(UserAnswerType.NONE);

        assertThat(styleName).isEqualTo(expectedStyleName);
    }

}
