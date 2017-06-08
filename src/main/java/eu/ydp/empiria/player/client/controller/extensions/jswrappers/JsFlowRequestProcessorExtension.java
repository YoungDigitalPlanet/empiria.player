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
import eu.ydp.empiria.player.client.controller.extensions.types.FlowRequestProcessorExtension;
import eu.ydp.empiria.player.client.controller.flow.request.IFlowRequest;

public class JsFlowRequestProcessorExtension extends AbstractJsExtension implements FlowRequestProcessorExtension {

    @Override
    public ExtensionType getType() {
        return ExtensionType.EXTENSION_PROCESSOR_FLOW_REQUEST;
    }

    @Override
    public void init() {
    }

    @Override
    public boolean isRequestSupported(IFlowRequest request) {
        return isRequestSupportedJs(extensionJsObject, request.getName());
    }

    private native boolean isRequestSupportedJs(JavaScriptObject extenstionObject, String requestName)/*-{
        return extenstionObject.isFlowRequestSupported(requestName);
    }-*/;

    @Override
    public void processRequest(IFlowRequest request) {
        processRequestJs(extensionJsObject, request.toJsObject());
    }

    private native void processRequestJs(JavaScriptObject extenstionObject, JavaScriptObject requestJsObject)/*-{
        extenstionObject.processFlowRequest(requestJsObject);
    }-*/;

}
