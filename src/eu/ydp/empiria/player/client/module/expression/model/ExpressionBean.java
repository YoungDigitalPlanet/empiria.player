package eu.ydp.empiria.player.client.module.expression.model;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlValue;

import com.google.common.collect.Multiset;

import eu.ydp.empiria.player.client.controller.variables.objects.response.Response;
import eu.ydp.empiria.player.client.module.expression.ExpressionMode;

@XmlAccessorType(XmlAccessType.NONE)
@XmlRootElement(name = "expression")
public class ExpressionBean {

	@XmlValue
	private String template;

	@XmlAttribute
	private ExpressionMode mode;

	@XmlTransient
	private final List<Response> responses = new ArrayList<Response>();

	@XmlTransient
	private Multiset<Multiset<String>> corectResponses;

	public String getTemplate() {
		return template;
	}

	public void setTemplate(String template) {
		this.template = template;
	}

	public List<Response> getResponses() {
		return responses;
	}

	public ExpressionMode getMode() {
		return mode;
	}

	public void setMode(ExpressionMode mode) {
		if (mode == null) {
			this.mode = ExpressionMode.DEFAULT;
		} else {
			this.mode = mode;
		}
	}

	public Multiset<Multiset<String>> getCorectResponses() {
		return corectResponses;
	}

	public void setCorectResponses(Multiset<Multiset<String>> corectResponses) {
		this.corectResponses = corectResponses;
	}

}
