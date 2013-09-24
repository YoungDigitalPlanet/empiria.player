package eu.ydp.empiria.player.client.components.animation.swiffy;

import com.google.gwt.dom.client.Element;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.inject.Inject;

import eu.ydp.empiria.player.client.gin.binding.UniqueId;

public class SwiffyObject {

	private final SwiffyRuntimeLoadHandler runtimeLoadHandler = new SwiffyRuntimeLoadHandler() {
		@Override
		public void onLoad() {
			startAnimation();
		}
	};

	private String animationJsUrl;
	private FlowPanel container = new FlowPanel();
	@Inject @UniqueId private String iframeId;
	@Inject private SwiffyRuntimeLoader runtimeLoader;

	void setAnimationUrl(String animationUrl) {
		this.animationJsUrl = animationUrl;
	}

	public void start() {
		runtimeLoader.addRuntimeLoadHandler(runtimeLoadHandler);
	}

	private void startAnimation() {
		appendScriptElement(iframeId, animationJsUrl);
	}

	public void destroy() {
		runtimeLoader.removeRuntimeLoadHandler(runtimeLoadHandler);
		container.removeFromParent();
		swiffyDestroy(iframeId);
		container = null;
	}

	public IsWidget getWidget() {
		return container;
	}

	private void onAnimationJsLoaded() {
		createSwiffyObject(iframeId, container.getElement());
		swiffyStart(iframeId);
	}

	private native void appendScriptElement(String iframeId, String animationJsUrl)/*-{
		var iframe = $doc.createElement("IFRAME");
		$doc.body.appendChild(iframe);
		iframe.setAttribute("id", iframeId);
		iframe.setAttribute("style","position: absolute; width: 0px; height: 0px; border: medium none; left: -1000px; top: -1000px;");
		var ifrDoc = iframe.contentWindow.document;
		ifrDoc.open();
		ifrDoc.write('<html><head><script src="'+ animationJsUrl+ '" type="text/javascript"></script><\/head><body><\/body><\/html>');
		ifrDoc.close();
		var that = this;
		var scriptElement = ifrDoc.getElementsByTagName("head")[0].childNodes[0];
		scriptElement.onload = function() {
			that.@eu.ydp.empiria.player.client.components.animation.swiffy.SwiffyObject::onAnimationJsLoaded()();
			scriptElement.onload = null;
		}
	}-*/;

	private native void createSwiffyObject(String iframeId, Element container)/*-{
		var iframe = $doc.getElementById(iframeId);
		iframe.contentWindow.swiffyObject = iframe.contentWindow.getSwiffyObject();
	    iframe.contentWindow.swiffyStage = new window.swiffy.Stage(container, iframe.contentWindow.swiffyObject);
	}-*/;

	private native void swiffyStart(String iframeId)/*-{
		 var iframe = $doc.getElementById(iframeId)
		 iframe.contentWindow.swiffyStage.start();
	}-*/;

	private native void swiffyDestroy(String iframeId)/*-{
		try {
			var iframe = $doc.getElementById(iframeId);
			iframe.contentWindow.swiffyStage.destroy();
			var ifrParent = iframe.parentNode;
			ifrParent.removeChild(iframe);
		} catch (e) {
			console.log(e);
		}
	}-*/;

}
