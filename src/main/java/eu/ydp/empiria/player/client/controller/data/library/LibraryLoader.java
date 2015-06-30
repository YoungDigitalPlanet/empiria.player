package eu.ydp.empiria.player.client.controller.data.library;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.dom.client.Document;
import com.google.gwt.user.client.Timer;
import com.google.gwt.xml.client.Element;
import com.google.gwt.xml.client.Node;
import com.google.gwt.xml.client.NodeList;
import eu.ydp.empiria.player.client.util.file.xml.XmlData;

import java.util.ArrayList;
import java.util.List;

public class LibraryLoader {

    protected List<LibraryExtension> extensionCreators;
    protected int extensionsToLoad;
    protected int extensionsLoaded;
    protected LibraryLoaderListener listener;
    protected boolean awaitingExtensions;

    public LibraryLoader(LibraryLoaderListener listener) {
        this.listener = listener;
        extensionCreators = new ArrayList<LibraryExtension>();
        awaitingExtensions = false;
    }

    public void load(XmlData data) {
        NodeList importNodes = data.getDocument().getElementsByTagName("import");

        List<String> srcs = new ArrayList<String>();

        for (int i = 0; i < importNodes.getLength(); i++) {
            if (importNodes.item(i).getNodeType() == Node.ELEMENT_NODE) {
                String src = ((Element) importNodes.item(i)).getAttribute("src");
                String name = ((Element) importNodes.item(i)).getAttribute("name");
                if (src != null && !"".equals(src) && !srcs.contains(src)) {
                    srcs.add(fixLink(src, data.getBaseURL()));
                } else if (name != null && !"".equals(name)) {
                    extensionCreators.add(new LibraryInternalExtension(name));
                }
            }
        }
        extensionsToLoad = srcs.size();
        extensionsLoaded = 0;
        if (srcs.size() > 0) {
            initJsApi();

            Timer timeoutTimer = new Timer() {

                @Override
                public void run() {
                    onTimeout();
                }
            };
            awaitingExtensions = true;
            for (String src : srcs) {
                appendJs(src);
            }
            timeoutTimer.schedule(5000);
        } else {
            listener.onExtensionsLoadFinished();
        }
    }

    private String fixLink(String url, String base) {
        String resolvedUrl;
        if (url.contains("://") || url.startsWith("/")) {
            resolvedUrl = url;
        } else {
            resolvedUrl = base + url;
        }
        return resolvedUrl;
    }

    private native void initJsApi()/*-{
        var instance = this;
        $wnd.empiriaOnExtensionLoaded = function (extensionCreator) {
            instance.@eu.ydp.empiria.player.client.controller.data.library.LibraryLoader::onExtensionLoaded(Lcom/google/gwt/core/client/JavaScriptObject;)(extensionCreator);
        }
    }-*/;

    private void onExtensionLoaded(JavaScriptObject extensionCreator) {
        if (awaitingExtensions) {
            extensionCreators.add(new LibraryExternalExtension(extensionCreator));
            extensionsLoaded++;
            checkAllExtensionsLoaded();
        }
    }

    ;

    protected void onTimeout() {
        if (awaitingExtensions)
            onExtensionsLoadFinished();
    }

    protected void checkAllExtensionsLoaded() {
        if (extensionsToLoad == extensionsLoaded) {
            onExtensionsLoadFinished();
        }
    }

    protected void onExtensionsLoadFinished() {
        awaitingExtensions = false;
        listener.onExtensionsLoadFinished();
    }

    protected void appendJs(String url) {
        com.google.gwt.dom.client.Element jsElement = Document.get().createElement("script");
        jsElement.setAttribute("type", "text/javascript");
        jsElement.setAttribute("src", url);
        Document.get().getElementsByTagName("head").getItem(0).appendChild(jsElement);
    }

    public List<LibraryExtension> getExtensionCreators() {
        return extensionCreators;
    }
}
