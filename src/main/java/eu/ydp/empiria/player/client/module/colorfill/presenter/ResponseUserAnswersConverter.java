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

import com.google.common.base.Function;
import com.google.common.collect.Lists;
import eu.ydp.empiria.player.client.module.colorfill.structure.Area;
import eu.ydp.empiria.player.client.module.model.color.ColorModel;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ResponseUserAnswersConverter {

    public String buildResponseAnswerFromAreaAndColor(Area area, ColorModel color) {
        StringBuilder answerBuilder = new StringBuilder();
        answerBuilder.append(area.getX());
        answerBuilder.append(",");
        answerBuilder.append(area.getY());
        answerBuilder.append(" ");
        answerBuilder.append(color.toStringRgb());

        return answerBuilder.toString();
    }

    public Map<Area, ColorModel> convertResponseAnswersToAreaColorMap(List<String> currentAnswers) {
        Map<Area, ColorModel> areasWithColors = new HashMap<Area, ColorModel>();
        for (String currentAnswer : currentAnswers) {
            Area area = getAreaFromAnswer(currentAnswer);
            ColorModel color = getColorFromAnswer(currentAnswer);
            areasWithColors.put(area, color);
        }
        return areasWithColors;
    }

    public List<Area> convertResponseAnswersToAreaList(List<String> currentAnswers) {
        return Lists.transform(currentAnswers, new Function<String, Area>() {
            @Override
            public Area apply(String currentAnswer) {
                return getAreaFromAnswer(currentAnswer);
            }
        });
    }

    private ColorModel getColorFromAnswer(String currentAnswer) {
        String[] splittedAnswer = currentAnswer.split(" ");
        String rbgString = splittedAnswer[1];
        ColorModel color = ColorModel.createFromRgbString(rbgString);
        return color;
    }

    private Area getAreaFromAnswer(String currentAnswer) {
        String[] splittedAnswer = currentAnswer.split(" ");
        String areaString = splittedAnswer[0];
        String[] splittedArea = areaString.split(",");

        int x = Integer.valueOf(splittedArea[0]);
        int y = Integer.valueOf(splittedArea[1]);
        return new Area(x, y);
    }
}
