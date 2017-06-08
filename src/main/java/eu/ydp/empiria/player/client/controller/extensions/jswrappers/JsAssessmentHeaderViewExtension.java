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
import com.google.gwt.dom.client.Element;
import com.google.gwt.user.client.ui.Widget;
import eu.ydp.empiria.player.client.components.ElementWrapperWidget;
import eu.ydp.empiria.player.client.controller.extensions.ExtensionType;
import eu.ydp.empiria.player.client.controller.extensions.types.AssessmentHeaderViewExtension;
import eu.ydp.empiria.player.client.view.sockets.ViewSocket;

public class JsAssessmentHeaderViewExtension extends AbstractJsExtension implements AssessmentHeaderViewExtension {

    @Override
    public ExtensionType getType() {
        return ExtensionType.EXTENSION_VIEW_ASSESSMENT_HEADER;
    }

    @Override
    public void init() {// NOPMD

    }

    @Override
    public ViewSocket getAssessmentHeaderViewSocket() {
        Element element = getViewJs(extensionJsObject);
        Widget widget = null;
        if (element != null) {
            widget = new ElementWrapperWidget(element);
        }

        final Widget widget2 = widget;

        return new ViewSocket() {
            @Override
            public Widget getView() {
                return widget2;
            }
        };
    }

    private native Element getViewJs(JavaScriptObject extension)/*-{
        if (typeof extension.getAssessmentHeaderView == 'function')
            return extension.getAssessmentHeaderView();
        return null;
    }-*/;

}
