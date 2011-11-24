package eu.ydp.empiria.player.client.controller.variables.processor.item;

import com.google.gwt.xml.client.NodeList;

public enum VariableProcessorTemplate {
	NONE, DEFAULT;
	
	public static VariableProcessor fromNode(NodeList responseProcessingNode){
		return new DefaultVariableProcessor();
		
	}
}
