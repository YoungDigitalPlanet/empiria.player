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

package eu.ydp.empiria.player.client.controller.communication;

import java.util.ArrayList;
import java.util.List;

public class DisplayContentOptions {

    public DisplayContentOptions() {
        ignoredTags = new ArrayList<String>();
        ignoredInlineTags = new ArrayList<String>();
        ignoredInlineTags.add("feedbackInline");
        useSkin = false;
    }

    protected List<String> ignoredTags;
    protected List<String> ignoredInlineTags;
    protected boolean useSkin;

    public List<String> getIgnoredTags() {
        return ignoredTags;
    }

    public List<String> getIgnoredInlineTags() {
        return ignoredInlineTags;
    }

    public void useSkin(boolean value) {
        useSkin = value;
    }

    public boolean useSkin() {
        return useSkin;
    }

}
