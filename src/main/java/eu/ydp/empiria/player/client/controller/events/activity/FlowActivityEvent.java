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

package eu.ydp.empiria.player.client.controller.events.activity;

import com.google.gwt.core.client.JavaScriptObject;
import eu.ydp.empiria.player.client.module.containers.group.GroupIdentifier;

public class FlowActivityEvent {

    protected FlowActivityEventType type;
    protected GroupIdentifier groupIdentifier;

    public FlowActivityEvent(FlowActivityEventType type, GroupIdentifier groupId) {
        this.type = type;
        this.groupIdentifier = groupId;
    }

    public FlowActivityEventType getType() {
        return type;
    }

    public GroupIdentifier getGroupIdentifier() {
        return groupIdentifier;
    }

    public static FlowActivityEvent fromJsObject(JavaScriptObject jsObject) {
        String currTypeString = getTypeJs(jsObject);
        final String groupIdentifierString = getGroupIdentifierJs(jsObject); // NOPMD
        if (currTypeString != null) {
            currTypeString = currTypeString.trim().toUpperCase();
            for (FlowActivityEventType currType : FlowActivityEventType.values()) {
                if (currType.toString().equals(currTypeString)) {
                    return new FlowActivityEvent(currType, new GroupIdentifier() { // NOPMD

                        @Override
                        public String getIdentifier() {
                            return groupIdentifierString;
                        }
                    });
                }
            }
        }
        return null;
    }

    private static native String getTypeJs(JavaScriptObject jsObject)/*-{
        if (typeof jsObject.type == 'string') {
            return jsObject.type;
        }
        return "";
    }-*/;

    private static native String getGroupIdentifierJs(JavaScriptObject jsObject)/*-{
        if (typeof jsObject.groupIdentifier == 'string') {
            return jsObject.groupIdentifier;
        }
        return "";
    }-*/;

}
