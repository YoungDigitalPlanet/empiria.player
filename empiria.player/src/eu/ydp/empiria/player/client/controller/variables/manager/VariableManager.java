package eu.ydp.empiria.player.client.controller.variables.manager;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import com.google.gwt.xml.client.Node;
import com.google.gwt.xml.client.NodeList;

import eu.ydp.empiria.player.client.controller.variables.IVariableCreator;
import eu.ydp.empiria.player.client.controller.variables.VariablePossessorBase;
import eu.ydp.empiria.player.client.controller.variables.objects.Variable;
import eu.ydp.empiria.player.client.controller.variables.objects.outcome.Outcome;


public class VariableManager<V extends Variable> extends VariablePossessorBase<V> {
	
	public VariableManager(NodeList responseDeclarationNodes, IVariableCreator<V> variableCreator){
		super();
		Node node;
		String currIdentifier;
		
		if (responseDeclarationNodes != null){
			for(int i = 0; i < responseDeclarationNodes.getLength(); i++){
		    	node = responseDeclarationNodes.item(i);
		    	if (node.getNodeType() == Node.ELEMENT_NODE){
			    	currIdentifier = node.getAttributes().getNamedItem("identifier").getNodeValue();
			    	V var = variableCreator.createVariable(node);
			    	if (var != null)
			    		variables.put(currIdentifier, var);
		    	}
		    }
		}
	}
	
}
