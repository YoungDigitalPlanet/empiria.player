package eu.ydp.empiria.player.client.controller.variables;

import java.util.Set;

import com.google.common.base.Joiner;
import com.google.gwt.core.client.JavaScriptObject;

import eu.ydp.empiria.player.client.controller.communication.sockets.JsSocketHolder;
import eu.ydp.empiria.player.client.controller.variables.objects.Variable;

public abstract class VariableProviderBase implements JsSocketHolder, VariableProviderSocket {

	protected String getVariableIdentifiersShort(Set<String> identifiersSet){
		return Joiner.on(';').join(identifiersSet);
	}

	public String getVariableValueShort(Variable var){
		if (var != null) {
			return var.getValuesShort();
		}
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
		Variable variableValue = getVariableValue(identifier);
		return getVariableValueShort(variableValue);
	}

}
