package eu.ydp.empiria.player.client.module.mathjax.interaction;

import com.google.common.collect.Maps;
import com.google.gwt.dom.client.Element;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Singleton;
import eu.ydp.empiria.player.client.module.math.MathGap;

import java.util.Map;

@Singleton
public class MathJaxGapContainer {

    private final Map<String, Widget> mathGaps = Maps.newHashMap();

    public MathJaxGapContainer() {
        initJavaScriptApi();
    }

    private native void initJavaScriptApi() /*-{
        var that = this;
        getMathGap = function (identifier) {
            return that.@MathJaxGapContainer::getMathGapElement(*)(identifier);
        };
    }-*/;

    public Element getMathGapElement(String identifier) {
        Widget gap = mathGaps.get(identifier);
        return gap.getElement();
    }

    public void addMathGap(MathGap mathGap) {
        mathGaps.put(mathGap.getIdentifier(), mathGap.getContainer());
    }

    public void addMathGap(Widget mathGap, String id) {
        mathGaps.put(id, mathGap);
    }
}
