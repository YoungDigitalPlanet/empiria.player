package eu.ydp.empiria.player.client.controller.variables;

import java.util.Set;

import com.google.gwt.core.client.JavaScriptObject;

import eu.ydp.empiria.player.client.controller.communication.sockets.JsSocketHolder;
import eu.ydp.empiria.player.client.controller.variables.objects.Variable;

public abstract class VariableProviderBase implements JsSocketHolder, VariableProviderSocket {
	
	protected String getVariableIdentifiersShort(Set<String> identifiersSet){
		String[] identifiers = identifiersSet.toArray(new String[0]);
		String all = "";
		for (int i = 0 ; i < identifiers.length ; i ++){
			all += identifiers[i];
			if (i < identifiers.length-1)
				all += ";";
		}
		return all;
	}
	
	public String getVariableValueShort(Variable var){
		if (var != null)
			return var.getValuesShort();
		return "";
	}
	
	@Override
	public JavaScriptObject getJsSocket() {
		return createJsSocket();
	}
	
	protected native JavaScriptObject createJsSocket()/*-{
		var socket = [];
		var instance = this;
		socket.getVariableIdentifiers = function(){
			return instance.@eu.ydp.empiria.player.client.controller.variables.VariableProviderBase::getVariableIdentifiersJs()();
		}
		socket.getVariableValue = function(identifier){
			return instance.@eu.ydp.empiria.player.client.controller.variables.VariableProviderBase::getVariableValuesShortJs(Ljava/lang/String;)(identifier);
		}
		return socket;
	}-*/;
	
	private String getVariableIdentifiersJs(){
		return getVariableIdentifiersShort(getVariableIdentifiers());
	}
	
	private String getVariableValuesShortJs(String identifier){
		Variable v = getVariableValue(identifier);
		return getVariableValueShort(v);
	}
	
}
