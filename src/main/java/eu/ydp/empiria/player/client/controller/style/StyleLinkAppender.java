package eu.ydp.empiria.player.client.controller.style;

public class StyleLinkAppender {

    public void appendStyleLink(String link) {
        appendStyleLinkNative(link);
    }

    private native void appendStyleLinkNative(String link) /*-{
        var headID = $wnd.document.getElementsByTagName("head")[0];
        var cssNode = $wnd.document.createElement('link');
        cssNode.type = 'text/css';
        cssNode.rel = 'stylesheet';
        cssNode.href = link;
        cssNode.media = 'screen';
        headID.appendChild(cssNode);
    }-*/;
}
