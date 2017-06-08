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

import com.google.inject.Inject;
import eu.ydp.empiria.player.client.module.feedback.FeedbackStyleNameConstants;
import javax.inject.Singleton;

@Singleton
public class FeedbackMarkStyleProvider {

    @Inject
    private FeedbackStyleNameConstants styleNames;

    public String getStyleName(FeedbackMark mark) {

        switch (mark) {
        case ALL_OK:
            return styleNames.QP_FEEDBACK_ALLOK();
        case OK:
            return styleNames.QP_FEEDBACK_OK();
        case WRONG:
            return styleNames.QP_FEEDBACK_WRONG();
        default:
            return "";
        }
    }
}
