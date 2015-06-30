package eu.ydp.empiria.player.client.components.animation.swiffy;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Document;
import com.google.gwt.dom.client.Element;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.IsWidget;

public class SwiffyObject {

    private static final String SWIFFY_RUNTIME_JS = "swiffy/runtime.js";
    private String animationJsUrl;
    private FlowPanel container = new FlowPanel();
    private String iframeId;
    private boolean animationLoaded;
    private boolean runtimeLoaded;

    void setAnimationUrl(String animationUrl) {
        this.animationJsUrl = animationUrl;
    }

    public void start() {
        iframeId = Document.get().createUniqueId();
        appendScriptsInIframe(iframeId, animationJsUrl, GWT.getModuleBaseURL() + SWIFFY_RUNTIME_JS);
    }

    public void destroy() {
        container.removeFromParent();
        swiffyDestroy(iframeId);
        container = null;
    }

    public IsWidget getWidget() {
        return container;
    }

    private void onAnimationJsLoaded() {
        animationLoaded = true;
        if (runtimeLoaded) {
            startAnimation();
        }
    }

    private void onRuntimeJsLoaded() {
        runtimeLoaded = true;
        if (animationLoaded) {
            startAnimation();
        }
    }

    private void startAnimation() {
        createSwiffyObject(iframeId, container.getElement());
        swiffyStart(iframeId);
    }

    private native void appendScriptsInIframe(String iframeId, String animationJsUrl, String runtimeUrl)/*-{
        var createScript = function (doc, url, callback) {
            var scriptElement = doc.createElement('script');
            scriptElement.setAttribute('src', url);
            scriptElement.onload = function () {
                callback();
                scriptElement.onload = null;
            }

            return scriptElement;
        }

        var iframe = $doc.createElement("IFRAME");
        iframe.setAttribute("id", iframeId);
        iframe
            .setAttribute(
            "style",
            "position: absolute; width: 0px; height: 0px; border: medium none; left: -1000px; top: -1000px;");
        $doc.body.appendChild(iframe);

        var ifrDoc = iframe.contentWindow.document;
        ifrDoc.open();
        ifrDoc.write('<html><head><\/head><body><\/body><\/html>');
        ifrDoc.close();

        var that = this;

        var scriptElementRt = createScript(
            ifrDoc,
            runtimeUrl,
            function () {
                that.@eu.ydp.empiria.player.client.components.animation.swiffy.SwiffyObject::onRuntimeJsLoaded()();
            });

        var scriptElementAnim = createScript(
            ifrDoc,
            animationJsUrl,
            function () {
                that.@eu.ydp.empiria.player.client.components.animation.swiffy.SwiffyObject::onAnimationJsLoaded()();
            });

        ifrDoc.getElementsByTagName("head")[0].appendChild(scriptElementRt);
        ifrDoc.getElementsByTagName("head")[0].appendChild(scriptElementAnim);
    }-*/;

    private native void createSwiffyObject(String iframeId, Element container)/*-{
        var iframe = $doc.getElementById(iframeId);
        iframe.contentWindow.swiffyObject = iframe.contentWindow
            .getSwiffyObject();
        iframe.contentWindow.swiffyStage = new iframe.contentWindow.swiffy.Stage(
            container, iframe.contentWindow.swiffyObject);
    }-*/;

    private native void swiffyStart(String iframeId)/*-{
        var iframe = $doc.getElementById(iframeId);
        iframe.contentWindow.swiffyStage.start();
    }-*/;

    private native void swiffyDestroy(String iframeId)/*-{
        var iframe = $doc.getElementById(iframeId);
        var ifrParent = iframe.parentNode;
        ifrParent.removeChild(iframe);
    }-*/;
}
