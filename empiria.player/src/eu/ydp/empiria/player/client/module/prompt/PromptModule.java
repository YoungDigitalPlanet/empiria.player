package eu.ydp.empiria.player.client.module.prompt;

import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.xml.client.Element;

import eu.ydp.empiria.player.client.module.CommonsFactory;

public class PromptModule extends Composite{

	public PromptModule(Element node){
		Widget w = CommonsFactory.getPromptView(node);
		initWidget(w);
	}
}
