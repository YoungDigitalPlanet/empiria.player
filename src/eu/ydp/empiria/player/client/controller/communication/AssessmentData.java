package eu.ydp.empiria.player.client.controller.communication;

import eu.ydp.empiria.player.client.util.file.xml.XmlData;

public class AssessmentData {

	private XmlData data;

	private XmlData skinData;

	public AssessmentData(XmlData data, XmlData skinData) {
		this.data = data;
		this.skinData = skinData;
	}

	public XmlData getData() {
		return data;
	}

	public XmlData getSkinData() {
		return skinData;
	}

	public boolean useSkin() {
		return skinData != null;
	}

}
