package eu.ydp.empiria.player.client.module.draggap.structure;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

import eu.ydp.empiria.player.client.structure.ModuleBean;
import eu.ydp.gwtutil.client.StringUtils;

@XmlRootElement(name = "dragInteraction")
@XmlAccessorType(XmlAccessType.NONE)
public class DragGapBean extends ModuleBean {

	@XmlAttribute(name = "name")
	private String name = StringUtils.EMPTY_STRING;;
	
	@XmlAttribute(name = "expressionMode")
	private String expressionMode = StringUtils.EMPTY_STRING;
	
	@XmlAttribute(name = "widthBindingGroup")
	private String widthBindingGroup = StringUtils.EMPTY_STRING;
	
	@XmlAttribute(name = "responseIdentifier")
	private String responseIdentifier = StringUtils.EMPTY_STRING;
	
	public String getName() {
		return name;
	}

	public String getExpressionMode() {
		return expressionMode;
	}

	public String getWidthBindingGroup() {
		return widthBindingGroup;
	}

	public String getResponseIdentifier() {
		return responseIdentifier;
	}

}
