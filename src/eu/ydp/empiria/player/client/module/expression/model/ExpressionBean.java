package eu.ydp.empiria.player.client.module.expression.model;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlValue;

import eu.ydp.empiria.player.client.controller.variables.objects.response.Response;

@XmlAccessorType(XmlAccessType.NONE)
@XmlRootElement(name = "expression")
public class ExpressionBean {
	
	@XmlValue
	private String template;

	@XmlTransient
	private List<Response> responses = new ArrayList<Response>();
	
	public String getTemplate() {
		return template;
	}

	public void setTemplate(String template) {
		this.template = template;
	}

	public List<Response> getResponses() {
		return responses;
	}
}
