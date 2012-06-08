package eu.ydp.empiria.player.client.controller.communication;

import java.util.HashMap;
import java.util.Map;

import com.google.gwt.xml.client.NodeList;

import eu.ydp.empiria.player.client.controller.variables.objects.outcome.Outcome;
import eu.ydp.empiria.player.client.util.file.xml.XmlData;

public class InitialItemData {

	protected Map<String, Outcome> outcomes;
	
	public InitialItemData(XmlData itemData){

		NodeList outcomeDeclarationNodes = null;
		
		outcomes = new HashMap<String, Outcome>();
		
		if (itemData != null){
			outcomeDeclarationNodes = itemData.getDocument().getElementsByTagName("outcomeDeclaration");
		
			Outcome currOutcome;
			for (int i = 0 ; i < outcomeDeclarationNodes.getLength() ; i ++){
				currOutcome = new Outcome(outcomeDeclarationNodes.item(i));
				if (currOutcome != null){
					outcomes.put(currOutcome.identifier, currOutcome);
				}
			}
		}
	}
	
	public Map<String, Outcome> getOutcomes(){		
		return outcomes;
	}
	
}
