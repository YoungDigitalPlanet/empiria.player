package eu.ydp.empiria.player.client.module;

import java.util.List;

import com.google.gwt.user.client.ui.HasWidgets;
import com.google.gwt.xml.client.Element;

public interface IMultiViewModule {
	
	public void addElement(Element element);
	
	public void installViews(List<HasWidgets> placeholders);
	
}
