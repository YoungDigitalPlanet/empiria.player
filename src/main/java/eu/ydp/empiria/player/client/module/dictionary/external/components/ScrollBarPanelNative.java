package eu.ydp.empiria.player.client.module.dictionary.external.components;

import com.google.gwt.dom.client.Element;

public class ScrollBarPanelNative {

    public native int getScrollTop(Element el)/*-{
        return el.scrollTop;
    }-*/;

    public native void setScrollTop(Element el, int value)/*-{
        el.scrollTop = value;
    }-*/;

    public native int getHeight(Element el)/*-{
        return el.scrollHeight;
    }-*/;

    public native void opacityto(com.google.gwt.dom.client.Element elm, int v)/*-{
        elm.style.opacity = v / 100;
        elm.style.MozOpacity = v / 100;
        elm.style.KhtmlOpacity = v / 100;
        elm.style.filter = " alpha(opacity =" + v + ")";
    }-*/;

    public native void fadeOutJs(com.google.gwt.dom.client.Element element, int fadeEffectTime)/*-{
        var instance = this;
        var _this = element;
        instance.@eu.ydp.empiria.player.client.module.dictionary.external.components.ScrollbarPanel::opacityto(Lcom/google/gwt/dom/client/Element;I)(_this, 100);
        var delay = fadeEffectTime;
        _this.style.zoom = 1; // for ie, set haslayout
        _this.style.display = "block";
        for (i = 0; i <= 100; i += 5) {
            (function (j) {
                setTimeout(function () {
                    j = 100 - j;
                    instance.@eu.ydp.empiria.player.client.module.dictionary.external.components.ScrollbarPanel::opacityto(Lcom/google/gwt/dom/client/Element;I)(_this, j);
                }, j * delay / 100);

            })(i);
        }
    }-*/;
}
