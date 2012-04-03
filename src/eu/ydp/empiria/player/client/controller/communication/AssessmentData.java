package eu.ydp.empiria.player.client.controller.communication;

import eu.ydp.empiria.player.client.util.xml.document.XMLData;

public class AssessmentData {
	
	private XMLData data;
	
	private XMLData skinData;
	
	public AssessmentData(XMLData data, XMLData skinData){
		this.data = data;
		this.skinData = skinData;
	}
	
	public XMLData getData(){
		return data;
	}
	
	public XMLData getSkinData(){
		return skinData;
	}
	
	public boolean useSkin(){
		return skinData != null;
	}
	
}
