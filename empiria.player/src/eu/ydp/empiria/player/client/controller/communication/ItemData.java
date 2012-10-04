package eu.ydp.empiria.player.client.controller.communication;

import eu.ydp.empiria.player.client.util.file.xml.XmlData;

public class ItemData {

	public ItemData(int index, XmlData d){
		itemIndex = index;
		data = d;
		errorMessage = "";
	}

	public ItemData(int index, String err){
		itemIndex = index;
		errorMessage = err;
	}
	
	public int itemIndex;
	public String errorMessage;
	public XmlData data;
	public ItemActivityOptions activityOptions;

}
