package eu.ydp.empiria.player.client.module.math;

import java.util.Map;

import eu.ydp.gwtutil.client.StringUtils;

public class MathGapModel {
	
	protected String uid;
	protected int index;
	
	protected MathModule parentMathModule;
	protected Map<String, String> mathStyles;
	
	public String getUid() {
		return (uid == null) ? StringUtils.EMPTY_STRING : uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}
	
	public void setIndex(int index) {
		this.index = index;
	}

	public int getIndex() {
		return index;
	}
	
	public Map<String, String> getMathStyles() {
		return mathStyles;
	}
	
	public void setMathStyles(Map<String, String> mathStyles) {
		this.mathStyles = mathStyles;
	}
}
