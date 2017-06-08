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
import eu.ydp.empiria.player.client.controller.extensions.internal.tutor.TutorConfig;
import eu.ydp.empiria.player.client.controller.extensions.internal.tutor.js.TutorConfigJs;
import eu.ydp.empiria.player.client.controller.extensions.types.TutorExtension;

public class JsTutorExtension extends AbstractJsExtension implements TutorExtension {

    private JavaScriptObject playerJsObject;

    @Override
    public void init() {
    }

    @Override
    public String getTutorId() {
        return getTutorIdNative(extensionJsObject);
    }

    private final native String getTutorIdNative(JavaScriptObject extensionJsObject)/*-{
        return extensionJsObject.getTutorId();
    }-*/;

    @Override
    public TutorConfig getTutorConfig() {
        JavaScriptObject tutorConfigJso = getTutorConfigNative(extensionJsObject);
        TutorConfigJs tutorConfigJs = tutorConfigJso.cast();
        return new TutorConfig(tutorConfigJs);
    }

    private final native JavaScriptObject getTutorConfigNative(JavaScriptObject extensionJsObject)/*-{
        return extensionJsObject.getTutorConfig();
    }-*/;

    @Override
    public ExtensionType getType() {
        return ExtensionType.EXTENSION_TUTOR;
    }

}
