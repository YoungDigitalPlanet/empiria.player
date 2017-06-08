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

import com.google.inject.Inject;
import eu.ydp.empiria.player.client.module.draggap.DragGapStyleNameConstants;
import eu.ydp.empiria.player.client.module.selection.model.UserAnswerType;
import eu.ydp.gwtutil.client.StringUtils;

public class DragGapStylesProvider {

    private final DragGapStyleNameConstants styleNameConstants;

    @Inject
    public DragGapStylesProvider(DragGapStyleNameConstants styleNameConstants) {
        this.styleNameConstants = styleNameConstants;
    }

    public String getCorrectGapStyleName(UserAnswerType type) {
        switch (type) {
            case CORRECT:
                return styleNameConstants.QP_DRAG_GAP_CORRECT();
            case WRONG:
                return styleNameConstants.QP_DRAG_GAP_WRONG();
            case DEFAULT:
                return styleNameConstants.QP_DRAG_GAP_DEFAULT();
            case NONE:
                return styleNameConstants.QP_DRAG_GAP_NONE();
            default:
                return StringUtils.EMPTY_STRING;
        }
    }
}
