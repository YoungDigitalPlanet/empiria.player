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

package eu.ydp.empiria.player.client.controller.events.interaction;

import com.google.gwt.core.client.JavaScriptObject;

import java.util.Map;

public abstract class InteractionEvent {

    public abstract InteractionEventType getType();

    public abstract Map<String, Object> getParams();

    public static InteractionEvent fromJsObject(JavaScriptObject jsObject) {
        String type = getJsObjectType(jsObject);

        if (type.equals(InteractionEventType.STATE_CHANGED.toString())) {
            try {
                boolean userInteract = getJsObjectUserInteract(jsObject);
                JavaScriptObject senderJs = getJsObjectSender(jsObject);
                return new StateChangedInteractionEvent(userInteract, false, null);
            } catch (Exception e) {
            }
        }

        if (type.equals(InteractionEventType.FEEDBACK_SOUND.toString())) {
            try {
                String url = getJsObjectUrl(jsObject);
                return new FeedbackInteractionSoundEvent(url);
            } catch (Exception e) {
            }
        }

        if (type.equals(InteractionEventType.FEEDBACK_MUTE.toString())) {
            try {
                boolean mute = getJsObjectMute(jsObject);
                return new FeedbackInteractionMuteEvent(mute);
            } catch (Exception e) {
            }
        }

        return null;
    }

    private static native String getJsObjectType(JavaScriptObject obj)/*-{
        if (typeof obj.type == 'string')
            return obj.type;
        return "";
    }-*/;

    private static native JavaScriptObject getJsObjectSender(JavaScriptObject obj)/*-{
        if (typeof obj.sender == 'object')
            return obj.sender;
        return [];
    }-*/;

    private static native boolean getJsObjectUserInteract(JavaScriptObject obj)/*-{
        if (typeof obj.userInteract == 'boolean')
            return obj.userInteract;
        return "";
    }-*/;

    private static native String getJsObjectUrl(JavaScriptObject obj)/*-{
        if (typeof obj.url == 'string')
            return obj.url;
        return "";
    }-*/;

    private static native boolean getJsObjectMute(JavaScriptObject obj)/*-{
        if (typeof obj.mute == 'boolean')
            return obj.mute;
        return false;
    }-*/;
}
