package eu.ydp.empiria.player.client.module.mathjax.interaction;

import com.google.common.collect.Maps;
import com.google.gwt.dom.client.Element;
import eu.ydp.empiria.player.client.module.math.MathGap;

import java.util.Map;

public class MathJaxGapContainer {

	private final Map<String, MathGap> mathGaps = Maps.newHashMap();

	public MathJaxGapContainer() {
		initJavaScriptApi();
	}

	private native void initJavaScriptApi() /*-{
        var that = this;
        $wnd.MathJax.Hub.getMathGap = function (identifier) {
            return that.@eu.ydp.empiria.player.client.module.mathjax.interaction.MathJaxGapContainer::getMathGapElement(Ljava/lang/String;)(identifier);
        };
    }-*/;

	public Element getMathGapElement(String identifier) {
		MathGap gap = mathGaps.get(identifier);
		return gap.getContainer().getElement();
	}

	public void addMathGap(MathGap mathGap) {
		mathGaps.put(mathGap.getIdentifier(), mathGap);
	}
}
