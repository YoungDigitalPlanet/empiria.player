package eu.ydp.empiria.player.client.controller.extensions.jswrappers;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.dom.client.Element;
import com.google.gwt.user.client.ui.Widget;

import eu.ydp.empiria.player.client.components.ElementWrapperWidget;
import eu.ydp.empiria.player.client.controller.extensions.ExtensionType;
import eu.ydp.empiria.player.client.controller.extensions.types.AssessmentHeaderViewExtension;
import eu.ydp.empiria.player.client.view.sockets.ViewSocket;

public class JsAssessmentHeaderViewExtension extends JsExtension implements
		AssessmentHeaderViewExtension {

	@Override
	public ExtensionType getType() {
		return ExtensionType.EXTENSION_VIEW_ASSESSMENT_HEADER;
	}

	@Override
	public void init() {
		// TODO Auto-generated method stub

	}

	@Override
	public ViewSocket getAssessmentHeaderViewSocket() {
		Element e = getViewJs(extensionJsObject);
		Widget w = null;
		if (e != null)
			w = new ElementWrapperWidget(e);
		
		final Widget w2 = w;
		
		return new ViewSocket() {
			@Override
			public Widget getView() {
				return w2;
			}
		};
	}
	
	private native Element getViewJs(JavaScriptObject extension)/*-{
		if (typeof extension.getAssessmentHeaderView == 'function')
			return extension.getAssessmentHeaderView();
		return null;
	}-*/;

}
