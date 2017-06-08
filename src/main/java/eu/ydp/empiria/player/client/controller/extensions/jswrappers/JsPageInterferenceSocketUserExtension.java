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

package eu.ydp.empiria.player.client.controller.extensions.jswrappers;

import com.google.gwt.core.client.JavaScriptObject;
import eu.ydp.empiria.player.client.controller.communication.sockets.PageInterferenceSocket;
import eu.ydp.empiria.player.client.controller.extensions.ExtensionType;
import eu.ydp.empiria.player.client.controller.extensions.types.PageInterferenceSocketUserExtension;

public class JsPageInterferenceSocketUserExtension extends AbstractJsExtension implements PageInterferenceSocketUserExtension {

    protected PageInterferenceSocket pageInterferenceSocket;
    protected JavaScriptObject assessmentInterferenceSocketJs;

    @Override
    public ExtensionType getType() {
        return ExtensionType.EXTENSION_SOCKET_USER_INTERFERENCE_PAGE;
    }

    @Override
    public void init() {
        assessmentInterferenceSocketJs = createPageInterferenceSocketJs(pageInterferenceSocket.getJsSocket());
        setInterferenceSocketJs(extensionJsObject, assessmentInterferenceSocketJs);
    }

    @Override
    public void setPageInterferenceSocket(PageInterferenceSocket pcs) {
        pageInterferenceSocket = pcs;
    }

    private native JavaScriptObject createPageInterferenceSocketJs(JavaScriptObject socketJs)/*-{
        var instance = this;
        var socket = [];
        var ais = socketJs;
        socket.getPageInterferenceSocket = function () {
            return ais;
        }
        return socket;
    }-*/;

    private native void setInterferenceSocketJs(JavaScriptObject extension, JavaScriptObject socket)/*-{
        if (typeof extension.setInterferenceSocket == 'function') {
            extension.setInterferenceSocket(socket);
        }
    }-*/;

}
