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
import com.google.gwt.xml.client.Element;
import com.google.gwt.xml.client.XMLParser;
import com.google.inject.Inject;
import eu.ydp.empiria.player.client.controller.extensions.ExtensionType;
import eu.ydp.empiria.player.client.controller.extensions.types.StyleSocketUserExtension;
import eu.ydp.empiria.player.client.style.StyleSocket;
import eu.ydp.empiria.player.client.util.js.JSArrayUtils;

import java.util.Map;

public class JsStyleSocketUserExtension extends AbstractJsExtension implements StyleSocketUserExtension {

    protected JavaScriptObject styleSocketJs;

    private final StyleSocket styleSocket;

    @Inject
    public JsStyleSocketUserExtension(StyleSocket styleSocket) {
        super();
        this.styleSocket = styleSocket;
    }

    @Override
    public ExtensionType getType() {
        return ExtensionType.EXTENSION_SOCKET_USER_STYLE_CLIENT;
    }

    @Override
    public void init() {
        styleSocketJs = createStyleSocketJs();
        setStyleSocketJs(extensionJsObject, styleSocketJs);
    }

    private native JavaScriptObject createStyleSocketJs()/*-{
        var instance = this;
        var socket = [];
        socket.getStyle = function (element) {
            return instance.@eu.ydp.empiria.player.client.controller.extensions.jswrappers.JsStyleSocketUserExtension::getStyle(Lcom/google/gwt/dom/client/Element;)(element);
        }
        return socket;
    }-*/;

    private native void setStyleSocketJs(JavaScriptObject extension, JavaScriptObject socket)/*-{
        if (typeof extension.setStyleSocket == 'function') {
            extension.setStyleSocket(socket);
        }
    }-*/;

    private JavaScriptObject getStyle(com.google.gwt.dom.client.Element element) {

        Element xmlElement = XMLParser.createDocument().createElement(element.getNodeName().toLowerCase());
        Map<String, String> styles = styleSocket.getStyles(xmlElement);
        JavaScriptObject stylesJsArray = JavaScriptObject.createObject();

        for (String currKey : styles.keySet()) {
            JSArrayUtils.fillArray(stylesJsArray, currKey, styles.get(currKey));
        }

        return stylesJsArray;
    }

}
