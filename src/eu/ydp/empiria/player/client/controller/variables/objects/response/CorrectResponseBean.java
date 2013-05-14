package eu.ydp.empiria.player.client.controller.variables.objects.response;

import java.util.List;

import javax.xml.bind.annotation.XmlElement;

public class CorrectResponseBean {

	@XmlElement(name = "value")
	private List<ValueBean> values;

	public List<ValueBean> getValues() {
		return values;
	}

	public void setValues(List<ValueBean> values) {
		this.values = values;
	}

}
