package eu.ydp.empiria.player.client.controller.feedback;

import com.google.gwt.user.client.ui.ComplexPanel;
import com.google.gwt.user.client.ui.Widget;

public interface IItemFeedback {

	public Widget getView();
	public String getVariableIdentifier();
	public String getSenderIdentifier();
	public String getValue();
	public boolean hasHTMLContent();
	public boolean hasSoundContent();
	public void processSound();
	public boolean showOnMatch();
	public void show(ComplexPanel parent);
	public void hide(ComplexPanel parent);
}
