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
