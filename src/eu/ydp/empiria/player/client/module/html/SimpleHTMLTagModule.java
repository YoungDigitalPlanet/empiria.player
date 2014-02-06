package eu.ydp.empiria.player.client.module.html;

import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.xml.client.Element;

import eu.ydp.empiria.player.client.module.Factory;
import eu.ydp.empiria.player.client.module.SimpleModuleBase;

public class SimpleHTMLTagModule extends SimpleModuleBase implements Factory<SimpleHTMLTagModule> {

	HTMLPanel htmlElement;

	@Override
	public void initModule(Element element) {
		String value = "";
		if (element.getFirstChild() != null && element.getFirstChild().getNodeValue() != null) {
			value = element.getFirstChild().getNodeValue();
		}
		htmlElement = new HTMLPanel(element.getTagName(), value);
	}

	@Override
	public Widget getView() {
		return htmlElement;
	}

	@Override
	public SimpleHTMLTagModule getNewInstance() {
		return new SimpleHTMLTagModule();
	}

}
