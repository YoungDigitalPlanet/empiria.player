package eu.ydp.empiria.player.client.gin.scopes.module;

import com.google.gwt.xml.client.Element;

public class ModuleCreationContext {

	private final Element xmlElement;

	public ModuleCreationContext(Element xmlElement) {
		this.xmlElement = xmlElement;
	}

	public Element getXmlElement() {
		return xmlElement;
	}
	
}
