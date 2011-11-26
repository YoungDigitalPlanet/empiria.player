package eu.ydp.empiria.player.client.controller.variables.manager;

import java.util.HashMap;
import java.util.Set;

import com.google.gwt.xml.client.Node;
import com.google.gwt.xml.client.NodeList;

import eu.ydp.empiria.player.client.controller.variables.IVariableCreator;
import eu.ydp.empiria.player.client.controller.variables.VariablePossessorBase;
import eu.ydp.empiria.player.client.controller.variables.objects.Variable;


public class VariableManager<V extends Variable> extends VariablePossessorBase<V> {

	public VariableManager(NodeList responseDeclarationNodes, IVariableCreator<V> variableCreator){

		Node node;
		String currIdentifier;
		
		if (responseDeclarationNodes != null){
			for(int i = 0; i < responseDeclarationNodes.getLength(); i++){
		    	node = responseDeclarationNodes.item(i);
		    	currIdentifier = node.getAttributes().getNamedItem("identifier").getNodeValue();
		    	variables.put(currIdentifier, variableCreator.createVariable(node));
		    }
		}
	}
	
	public HashMap<String, V> getVariablesMap(){
		return variables;
	}
	
	
}
