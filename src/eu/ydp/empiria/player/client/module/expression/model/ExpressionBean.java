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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((corectResponses == null) ? 0 : corectResponses.hashCode());
		result = prime * result + ((mode == null) ? 0 : mode.hashCode());
		result = prime * result + ((responses == null) ? 0 : responses.hashCode());
		result = prime * result + ((template == null) ? 0 : template.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (!(obj instanceof ExpressionBean)) {
			return false;
		}
		ExpressionBean other = (ExpressionBean) obj;
		if (corectResponses == null) {
			if (other.corectResponses != null) {
				return false;
			}
		} else if (!corectResponses.equals(other.corectResponses)) {
			return false;
		}
		if (mode != other.mode) {
			return false;
		}
		if (responses == null) {
			if (other.responses != null) {
				return false;
			}
		} else if (!responses.equals(other.responses)) {
			return false;
		}
		if (template == null) {
			if (other.template != null) {
				return false;
			}
		} else if (!template.equals(other.template)) {
			return false;
		}
		return true;
	}
	
	

}
