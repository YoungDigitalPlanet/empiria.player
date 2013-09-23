package eu.ydp.empiria.player.client.components.animation.swiffy;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.FrameElement;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Frame;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.inject.Inject;

public class SwiffyObject {

	private final SwiffyRuntimeLoadHandler runtimeLoadHandler = new SwiffyRuntimeLoadHandler() {
		@Override
		public void onLoad() {
			startAnimation();
		}
	};

	private String animationJsUrl;
	private JavaScriptObject swiffyObject;
	private Frame iframe;
	private final FlowPanel container = new FlowPanel();
	@Inject private SwiffyRuntimeLoader runtimeLoader;

	void setAnimationUrl(String animationUrl) {
		this.animationJsUrl = animationUrl;
	}

	public void start() {
		runtimeLoader.addRuntimeLoadHandler(runtimeLoadHandler);
	}

	private void startAnimation() {
		iframe = createIframeElement();
		final FrameElement iframeElement = iframe.getElement().cast();
		appendScriptElement(iframeElement, animationJsUrl);
	}

	public void destroy() {
		runtimeLoader.removeRuntimeLoadHandler(runtimeLoadHandler);
		swiffyDestroy(swiffyObject, iframe.getElement());
	}

	public IsWidget getWidget() {
		return container;
	}

	private void onAnimationJsLoaded(final FrameElement iframeElement) {
		swiffyObject = createSwiffyObject(iframeElement, container.getElement());
		swiffyStart(swiffyObject);
	}

	private Frame createIframeElement() {
		Frame iframe = new Frame();
		final FrameElement iframeElement = iframe.getElement().cast();
		RootPanel.get().getElement().appendChild(iframeElement);
		return iframe;
	}

	private native void appendScriptElement(FrameElement iframe, String animationJsUrl)/*-{
		var ifrDoc = iframe.contentWindow.document;
		iframe.style = "position: absolute; width: 0px; height: 0px; border: medium none; left: -1000px; top: -1000px;";
		ifrDoc.open();
		ifrDoc.write('<html><head><script src="'+ animationJsUrl+ '" type="text/javascript"></script><\/head><body><\/body><\/html>');
		ifrDoc.close();
		var that = this;
		ifrDoc.getElementsByTagName("head")[0].childNodes[0].onload = function() {
			that.@eu.ydp.empiria.player.client.components.animation.swiffy.SwiffyObject::onAnimationJsLoaded(Lcom/google/gwt/dom/client/FrameElement;)(iframe);
		}
	}-*/;

	private native JavaScriptObject createSwiffyObject(JavaScriptObject iframe, Element container)/*-{
		var swiffyObject = iframe.contentWindow.getSwiffyObject();
		return new window.swiffy.Stage(container, swiffyObject);
	}-*/;

	private native void swiffyStart(JavaScriptObject swiffyObject)/*-{
		swiffyObject.start();
	}-*/;

	private native void swiffyDestroy(JavaScriptObject swiffyObject, JavaScriptObject iframe)/*-{
		try {
			var ifrParent = iframe.parentNode;
			ifrParent.removeChild(iframe);
			swiffyObject.destroy();
		} catch (e) {
		}
	}-*/;

}
