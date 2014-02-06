package eu.ydp.empiria.player.client.module.flash;

import com.google.gwt.dom.client.Document;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.xml.client.Element;

import eu.ydp.empiria.player.client.module.Factory;
import eu.ydp.empiria.player.client.module.SimpleModuleBase;

public class FlashModule extends SimpleModuleBase implements Factory<FlashModule> {

	public HTMLPanel panel;

	public FlashModule() {
		panel = new HTMLPanel("object", "");
	}

	@Override
	protected void initModule(Element element) {
		String src = element.getAttribute("src");
		int lastSlash = ((src.lastIndexOf("/") > src.lastIndexOf("\\")) ? src.lastIndexOf("/") : src.lastIndexOf("\\"));
		String basePath = src.substring(0, lastSlash + 1);
		com.google.gwt.dom.client.Element paramNameElement = Document.get().createElement("param");
		paramNameElement.setAttribute("name", "movie");
		paramNameElement.setAttribute("value", src);
		com.google.gwt.dom.client.Element paramBaseElement = Document.get().createElement("param");
		paramBaseElement.setAttribute("name", "base");
		paramBaseElement.setAttribute("value", basePath);
		com.google.gwt.dom.client.Element embedElement = Document.get().createElement("embed");
		embedElement.setAttribute("src", src);
		embedElement.setAttribute("base", basePath);
		panel.getElement().appendChild(paramNameElement);
		panel.getElement().appendChild(paramBaseElement);
		panel.getElement().appendChild(embedElement);

	}

	@Override
	public Widget getView() {
		return panel;
	}

	@Override
	public FlashModule getNewInstance() {
		return new FlashModule();
	}

}
