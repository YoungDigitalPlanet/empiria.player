package eu.ydp.empiria.player.client.controller.body;

import com.google.gwt.user.client.ui.HasWidgets;
import com.google.gwt.xml.client.Element;

public interface ModulesInstalatorSocket {

	public boolean isModuleSupported(String nodeName);
	
	public boolean isMultiViewModule(String nodeName);
	
	public void registerModuleView(Element element, HasWidgets parent);
	
	public void createSingleViewModule(Element element, HasWidgets parent, BodyGeneratorSocket moduleGeneratorSocket);
}
