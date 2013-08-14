package eu.ydp.empiria.player.client.module.math;

import java.util.Map;

import com.google.gwt.user.client.ui.Widget;

public interface MathGap {
	public Widget getContainer();
	public String getUid();
	public void setIndex(int index);
	public void setGapWidth(int gapWidth);
	public void setGapHeight(int gapHeight);
	public void setGapFontSize(int gapFontSize);
	public void setMathStyles(Map<String, String> mathStyles);
}