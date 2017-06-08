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

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.json.client.JSONArray;
import com.google.inject.Inject;
import com.google.inject.Provider;
import eu.ydp.empiria.player.client.controller.extensions.internal.DefaultAssessmentFooterViewExtension;
import eu.ydp.empiria.player.client.controller.extensions.internal.DefaultAssessmentHeaderViewExtension;
import eu.ydp.empiria.player.client.controller.extensions.internal.sound.DefaultMediaProcessorExtension;
import eu.ydp.empiria.player.client.controller.extensions.internal.sound.external.ExternalMediaProcessor;
import eu.ydp.empiria.player.client.controller.extensions.jswrappers.*;
import eu.ydp.empiria.player.client.controller.extensions.types.StatefulExtension;
import eu.ydp.empiria.player.client.module.core.flow.Stateful;

import java.util.ArrayList;
import java.util.List;

public class ExtensionsManager implements Stateful {

    private List<Extension> extensions;

    private final Provider<ExternalMediaProcessor> externalMediaProcessor;
    private final Provider<DefaultMediaProcessorExtension> defaultMediaProcessor;
    private final Provider<JsStyleSocketUserExtension> jsStyleSocketUserExtensionProvider;
    private final Provider<JsMediaProcessorExtension> jsMediaProcessorExtensionProvider;
    private final Provider<JsInteractionEventSocketUserExtension> jsInteractionEventSocketUserExtensionProvider;

    @Inject
    public ExtensionsManager(Provider<ExternalMediaProcessor> externalMediaProcessor, Provider<DefaultMediaProcessorExtension> defaultMediaProcessor,
                             Provider<JsStyleSocketUserExtension> jsStyleSocketUserExtensionProvider, Provider<JsMediaProcessorExtension> jsMediaProcessorExtensionProvider, Provider<JsInteractionEventSocketUserExtension> jsInteractionEventSocketUserExtensionProvider) {
        this.externalMediaProcessor = externalMediaProcessor;
        this.defaultMediaProcessor = defaultMediaProcessor;
        this.jsStyleSocketUserExtensionProvider = jsStyleSocketUserExtensionProvider;
        this.jsMediaProcessorExtensionProvider = jsMediaProcessorExtensionProvider;
        this.jsInteractionEventSocketUserExtensionProvider = jsInteractionEventSocketUserExtensionProvider;
        extensions = new ArrayList<>();
    }

    public void init() {

        for (Extension ext : extensions) {
            ext.init();
        }
    }

    private Extension getExtensionInstance(ExtensionType exType) {
        Extension currExt = null;
        if (exType != null) {
            switch (exType) {
                case EXTENSION_PROCESSOR_FLOW_REQUEST:
                    currExt = new JsFlowRequestProcessorExtension();
                    break;
                case EXTENSION_PROCESSOR_MEDIA:
                    currExt = jsMediaProcessorExtensionProvider.get();
                    break;
                case EXTENSION_LISTENER_DELIVERY_EVENTS:
                    currExt = new JsDeliveryEventsListenerExtension();
                    break;
                case EXTENSION_SOCKET_USER_STYLE_CLIENT:
                    currExt = jsStyleSocketUserExtensionProvider.get();
                    break;
                case EXTENSION_SOCKET_USER_SESSION_DATA_CLIENT:
                    currExt = new JsSessionDataSocketUserExtension();
                    break;
                case EXTENSION_SOCKET_USER_DATA_SOURCE_DATA_CLIENT:
                    currExt = new JsDataSourceDataSocketUserExtension();
                    break;
                case EXTENSION_SOCKET_USER_FLOW_DATA_CLIENT:
                    currExt = new JsFlowDataSocketUserExtension();
                    break;
                case EXTENSION_SOCKET_USER_FLOW_COMMAND:
                    currExt = new JsFlowCommandSocketUserExtension();
                    break;
                case EXTENSION_SOCKET_USER_FLOW_REQUEST:
                    currExt = new JsFlowRequestSocketUserExtension();
                    break;
                case EXTENSION_SOCKET_USER_INTERFERENCE_PAGE:
                    currExt = new JsPageInterferenceSocketUserExtension();
                    break;
                case EXTENSION_SOCKET_USER_INTERACTION_EVENT:
                    currExt = jsInteractionEventSocketUserExtensionProvider.get();
                    break;
                case EXTENSION_VIEW_ASSESSMENT_HEADER:
                    currExt = new JsAssessmentHeaderViewExtension();
                    break;
                case EXTENSION_VIEW_ASSESSMENT_FOOTER:
                    currExt = new JsAssessmentFooterViewExtension();
                    break;
                case EXTENSION_CLIENT_STATEFUL:
                    currExt = new JsStatefulExtension();
                    break;
                case EXTENSION_PLAYER_JS_OBJECT_USER:
                    currExt = new JsPlayerJsObjectUserExtension();
                    break;
                case EXTENSION_TUTOR:
                    currExt = new JsTutorExtension();
                    break;
                case EXTENSION_BONUS:
                    currExt = new JsBonusExtension();
                    break;
                case EXTENSION_PROGRESS_BONUS:
                    currExt = new JsProgressBonusExtension();
                    break;
                default:
                    break;
            }
        }
        return currExt;
    }

    public List<Extension> addExtension(JavaScriptObject extensionJsObject) {
        String extType = getFieldType(extensionJsObject);
        List<Extension> currExtensions = new ArrayList<>();
        if (extType != null) {
            String[] extTypes = extType.split(",");

            for (String currExtTypeString : extTypes) {
                ExtensionType currExtType = ExtensionType.fromString(currExtTypeString.trim());
                Extension currExt = getExtensionInstance(currExtType);
                if (currExt instanceof JsExtension) {
                    ((JsExtension) currExt).initJs(extensionJsObject);
                    extensions.add(currExt);
                    currExtensions.add(currExt);
                }
            }
        }
        return currExtensions;
    }

    public Extension addExtension(Extension extension) {
        extensions.add(extension);
        return extension;

    }

    private native String getFieldType(JavaScriptObject jsObject)/*-{
        if (typeof jsObject.getType == 'function')
            return jsObject.getType();
        return null;
    }-*/;

    public List<Extension> getExtensions() {
        return extensions;
    }

    public Extension getInternaleExtensionByName(String name) {
        Extension retValue = null;
        if ("DefaultAssessmentHeaderViewExtension".equals(name)) {
            retValue = new DefaultAssessmentHeaderViewExtension();
        }
        if ("DefaultAssessmentFooterViewExtension".equals(name)) {
            retValue = new DefaultAssessmentFooterViewExtension();
        }
        if ("DefaultSoundProcessorExtension".equals(name)) {
            retValue = defaultMediaProcessor.get();
        }
        if ("ExternalMediaProcessorExtension".equals(name)) {
            retValue = externalMediaProcessor.get();
        }
        return retValue;
    }

    @Override
    public JSONArray getState() {
        JSONArray arr = new JSONArray();
        for (Extension ext : extensions) {
            if (ext instanceof StatefulExtension) {
                arr.set(arr.size(), ((StatefulExtension) ext).getState());
            }
        }
        return arr;
    }

    @Override
    public void setState(JSONArray newState) {
        int counter = 0;
        for (Extension ext : extensions) {
            if (ext instanceof StatefulExtension && counter < newState.size()) {
                ((StatefulExtension) ext).setState(newState.get(counter).isArray());
                counter++;
            }
        }
    }

}
