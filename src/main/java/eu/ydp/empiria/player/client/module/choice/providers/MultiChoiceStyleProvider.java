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

package eu.ydp.empiria.player.client.module.choice.providers;

import com.google.inject.Inject;
import eu.ydp.empiria.player.client.module.choice.ChoiceStyleNameConstants;

public class MultiChoiceStyleProvider implements SimpleChoiceStyleProvider {

    private ChoiceStyleNameConstants styleNameConstants;

    @Inject
    public MultiChoiceStyleProvider(ChoiceStyleNameConstants styleNameConstants) {
        this.styleNameConstants = styleNameConstants;
    }

    @Override
    public String getMarkCorrectStyle() {
        return styleNameConstants.QP_CHOICE_BUTTON_MULTI_MARK_CORRECT();
    }

    @Override
    public String getMarkWrongStyle() {
        return styleNameConstants.QP_CHOICE_BUTTON_MULTI_MARK_WRONG();
    }

    @Override
    public String getInactiveStyle() {
        return styleNameConstants.QP_CHOICE_BUTTON_MULTI_INACTIVE();
    }

    @Override
    public String getResetStyle() {
        return styleNameConstants.QP_CHOICE_BUTTON_MULTI_MARK_NONE();
    }

    @Override
    public String getAnswereStyle() {
        return styleNameConstants.QP_CHOICE_BUTTON_MULTI_MARK();
    }
}
