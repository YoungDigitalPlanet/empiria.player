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

import com.google.gwt.core.client.JavaScriptObject;

import java.util.ArrayList;
import java.util.List;

public class DisplayOptions extends DisplayContentOptions {

    public DisplayOptions() {
        super();
    }

    public DisplayOptions(List<String> tags) {
        ignoredTags = tags;
    }

    public static DisplayOptions fromJsObject(JavaScriptObject o) {

        if (o == null)
            return new DisplayOptions();

        JavaScriptObject is = decodeDisplayOptionsObjectGetIgnoredSections(o);

        List<String> tags2Ignore = new ArrayList<String>();
        int arrayLength = decodeDisplayOptionsObjectArrayLength(is);
        for (int i = 0; i < arrayLength; i++) {
            tags2Ignore.add(decodeDisplayOptionsObjectArrayItem(is, i));
        }

        return new DisplayOptions(tags2Ignore);
    }

    private native static JavaScriptObject decodeDisplayOptionsObjectGetIgnoredSections(JavaScriptObject obj)/*-{
        if (typeof obj.ignoredSections == 'object') {
            return obj.ignoredSections;
        }
        return [];
    }-*/;

    private native static int decodeDisplayOptionsObjectArrayLength(JavaScriptObject obj)/*-{
        return obj.length;
    }-*/;

    private native static String decodeDisplayOptionsObjectArrayItem(JavaScriptObject obj, int index)/*-{
        return obj[index];
    }-*/;
}
