package eu.ydp.empiria.player.client.module.math;

import java.util.Map;

import com.google.gwt.user.client.ui.Widget;
import eu.ydp.empiria.player.client.module.IUniqueModule;

public interface MathGap extends IUniqueModule{
	Widget getContainer();

	String getUid();

	void setGapWidth(int gapWidth);

	void setGapHeight(int gapHeight);

	void setGapFontSize(int gapFontSize);

	void setMathStyles(Map<String, String> mathStyles);
}