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

package eu.ydp.empiria.player.client.controller.extensions;

public enum ExtensionType {
    // PROCESSORS
    EXTENSION_PROCESSOR_FLOW_REQUEST, EXTENSION_PROCESSOR_MEDIA,
    // LISTENERS
    EXTENSION_LISTENER_DELIVERY_EVENTS,
    // SOCKET USERS - CLIENT
    EXTENSION_SOCKET_USER_STYLE_CLIENT, EXTENSION_SOCKET_USER_SESSION_DATA_CLIENT, EXTENSION_SOCKET_USER_DATA_SOURCE_DATA_CLIENT, EXTENSION_SOCKET_USER_FLOW_DATA_CLIENT,
    // SOCKET USERS - INVOKER
    EXTENSION_SOCKET_USER_FLOW_COMMAND, EXTENSION_SOCKET_USER_FLOW_REQUEST, EXTENSION_SOCKET_USER_INTERFERENCE_PAGE, EXTENSION_SOCKET_USER_INTERACTION_EVENT, EXTENSION_SOCKET_USER_DELIVERY_ENGINE,
    // VIEW
    EXTENSION_VIEW_ASSESSMENT_HEADER, EXTENSION_VIEW_ASSESSMENT_FOOTER, EXTENSION_VIEW_PLAYER_HEADER, EXTENSION_VIEW_PLAYER_FOOTER,
    // CLIENTS
    EXTENSION_CLIENT_STATEFUL,
    // OTHER
    EXTENSION_PLAYER_JS_OBJECT_USER,
    // MODULE (not defined yet)
    EXTENSION_MODULE, EXTENSION_TUTOR, EXTENSION_BONUS, EXTENSION_PROGRESS_BONUS, MULTITYPE;

    public static ExtensionType fromString(String typeString) {
        ExtensionType exType = null;
        for (ExtensionType currValue : values()) {
            if (currValue.toString().equalsIgnoreCase(typeString)) {
                exType = currValue;
                break;
            }
        }
        return exType;
    }
}
