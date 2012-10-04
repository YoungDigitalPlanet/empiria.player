package eu.ydp.empiria.player.client.module;

import com.google.gwt.xml.client.Element;

public abstract class OneViewInteractionModuleBase extends InteractionModuleBase {

	private Element moduleElement;

	@Override
	public void addElement(Element element) {
		moduleElement = element;
		findResponse();
		readAttributes(element);
	}

	protected Element getModuleElement() {
		return moduleElement;
	}

	protected final void findResponse(){
		super.findResponse(moduleElement);
	}

}
