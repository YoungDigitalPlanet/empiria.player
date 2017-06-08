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

package eu.ydp.empiria.player.client.controller.style;

import com.google.inject.Inject;

import java.util.ArrayList;
import java.util.List;

public class StyleLinkManager {

    @Inject
    private StyleLinkAppender styleLinkAppender;

    private final List<String> solidStyles = new ArrayList<String>();

    public void registerAssessmentStyles(List<String> styleLinks) {
        doRegisterStyleLinks(styleLinks, false);
    }

    public void registerItemStyles(List<String> styleLinks) {
        doRegisterStyleLinks(styleLinks, true);
    }

    private void doRegisterStyleLinks(List<String> styleLinks, boolean areRemovable) {
        for (String link : styleLinks) {
            addStyleIfNotPresent(link);
        }
    }

    private void addStyleIfNotPresent(String link) {
        if (!solidStyles.contains(link)) {
            styleLinkAppender.appendStyleLink(link);

            solidStyles.add(link);
        }
    }
}
