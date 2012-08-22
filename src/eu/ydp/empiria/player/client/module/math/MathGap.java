package eu.ydp.empiria.player.client.module.math;

import com.google.gwt.user.client.ui.Widget;

public interface MathGap {
	
	public String getValue();
	public void setValue(String value);
	public void setEnabled(boolean enabled);
	public Widget getContainer();
	public void reset();
	void mark(boolean correct, boolean wrong);
	void unmark();
	
}