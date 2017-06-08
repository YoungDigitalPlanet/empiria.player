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

package eu.ydp.empiria.player.client.module.colorfill.presenter;

import com.google.common.collect.Lists;
import eu.ydp.empiria.player.client.module.colorfill.structure.Area;
import eu.ydp.empiria.player.client.module.colorfill.view.ColorfillInteractionView;
import eu.ydp.empiria.player.client.module.model.color.ColorModel;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.List;

import static org.fest.assertions.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ResponseAnswerByViewBuilderTest {

    private ResponseAnswerByViewBuilder answerByViewBuilder;
    private ResponseUserAnswersConverter responseUserAnswersConverter;
    @InjectMocks
    private ColorfillInteractionViewColors interactionViewColors;
    @Mock
    private ColorfillInteractionView interactionView;

    @Before
    public void setUp() throws Exception {
        responseUserAnswersConverter = new ResponseUserAnswersConverter();
        answerByViewBuilder = new ResponseAnswerByViewBuilder(interactionViewColors, responseUserAnswersConverter);
    }

    @Test
    public void shouldGrabColorsOfAllAreasAndBuildResponseFromColloredOnes() throws Exception {
        // when
        Area area1 = new Area(1, 1);
        Area notColoredArea = new Area(2, 2);
        Area area3 = new Area(3, 3);
        List<Area> areas = Lists.newArrayList(area1, notColoredArea, area3);

        ColorModel color1 = ColorModel.createFromRgbString("ff0000");
        when(interactionView.getColor(area1)).thenReturn(color1);

        ColorModel transparentColor = ColorModel.createFromRgba(0, 0, 0, 0);
        when(interactionView.getColor(notColoredArea)).thenReturn(transparentColor);

        ColorModel color3 = ColorModel.createFromRgbString("0000ff");
        when(interactionView.getColor(area3)).thenReturn(color3);

        // when
        List<String> userAnswers = answerByViewBuilder.buildNewResponseAnswersByCurrentImage(areas);

        // then
        assertThat(userAnswers).containsOnly("1,1 ff0000", "3,3 0000ff");
    }
}
