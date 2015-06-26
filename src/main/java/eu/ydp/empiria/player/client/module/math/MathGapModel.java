package eu.ydp.empiria.player.client.module.math;

import java.util.Map;

import com.google.common.collect.Maps;
import eu.ydp.gwtutil.client.StringUtils;

public class MathGapModel {

	protected String uid;
	protected int index;

	protected Map<String, String> mathStyles;

	public String getUid() {
		return (uid == null) ? StringUtils.EMPTY_STRING : uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}

	public Map<String, String> getMathStyles() {
		if(mathStyles == null) {
			mathStyles = Maps.newHashMap();
		}
		return mathStyles;
	}

	public void setMathStyles(Map<String, String> mathStyles) {
		this.mathStyles = mathStyles;
	}

	public boolean containsStyle(String key) {
		return mathStyles.containsKey(key);
	}

	public String getStyle(String key) {
		return mathStyles.get(key);
	}
}
