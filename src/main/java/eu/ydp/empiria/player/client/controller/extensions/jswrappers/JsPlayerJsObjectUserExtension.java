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
import eu.ydp.empiria.player.client.controller.extensions.ExtensionType;
import eu.ydp.empiria.player.client.controller.extensions.types.PlayerJsObjectModifierExtension;

public class JsPlayerJsObjectUserExtension extends AbstractJsExtension implements PlayerJsObjectModifierExtension {

    protected JavaScriptObject playerJsObject;

    @Override
    public ExtensionType getType() {
        return ExtensionType.EXTENSION_PLAYER_JS_OBJECT_USER;
    }

    @Override
    public void init() {
        setPlayerJsObjectJs(extensionJsObject, playerJsObject);
    }

    @Override
    public void setPlayerJsObject(JavaScriptObject playerJsObject) {
        this.playerJsObject = playerJsObject;
    }

    private native void setPlayerJsObjectJs(JavaScriptObject extension, JavaScriptObject jsObject)/*-{
        if (typeof extension.setPlayerJsObject == 'function') {
            extension.setPlayerJsObject(jsObject);
        }
    }-*/;

}
