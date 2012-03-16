package eu.ydp.empiria.player.client.controller.variables.storage.assessment;

import java.util.HashSet;
import java.util.Set;
import com.google.gwt.core.client.JavaScriptObject;
import eu.ydp.empiria.player.client.controller.communication.sockets.JsSocketHolder;
import eu.ydp.empiria.player.client.controller.session.datasockets.ItemsCollectionSessionDataSocket;
import eu.ydp.empiria.player.client.controller.variables.VariableProviderBase;
import eu.ydp.empiria.player.client.controller.variables.objects.BaseType;
import eu.ydp.empiria.player.client.controller.variables.objects.Cardinality;
import eu.ydp.empiria.player.client.controller.variables.objects.Variable;
import eu.ydp.empiria.player.client.controller.variables.objects.outcome.Outcome;
import eu.ydp.empiria.player.client.util.IntegerUtils;
import eu.ydp.empiria.player.client.util.StringUtils;

public class AssessmentVariableStorageImpl extends VariableProviderBase  implements JsSocketHolder {
	
	protected Set<String> identifiers;
	protected ItemsCollectionSessionDataSocket itemsCollectionSessionDataSocket;
	
	public AssessmentVariableStorageImpl(){
		identifiers = new HashSet<String>();
	}
	
	public void init(ItemsCollectionSessionDataSocket icsds){
		itemsCollectionSessionDataSocket = icsds;
		ensureVariables();
	}

	protected void ensureVariables(){
		ensureVariable("DONE");
		ensureVariable("TODO");
		ensureVariable("CHECKS");
		ensureVariable("SHOW_ANSWERS");
		ensureVariable("RESET");
		ensureVariable("MISTAKES");
		ensureVariable("VISITED");
	}
	
	private void ensureVariable(String identifier){
		if (!identifiers.contains(identifier))
			identifiers.add(identifier);
	}
	
	@Override
	public Set<String> getVariableIdentifiers(){
		return identifiers;
	}
	
	@Override
	public Variable getVariableValue(String identifier){
		if (identifiers.contains(identifier)){
			int value = getOutcomeVariableAssessmentTotal(identifier);
			Outcome o = new Outcome(identifier, Cardinality.SINGLE, BaseType.INTEGER, String.valueOf(value) );
			return o;
		}
		return null;
	}

	protected int getOutcomeVariableAssessmentTotal(String identifier) {
		int total = 0;
		Variable currVar;
		String currValue;
		int currValueInt;
		for (int i = 0 ; i < itemsCollectionSessionDataSocket.getItemSessionDataSocketsCount() ; i ++){
			if (itemsCollectionSessionDataSocket.getItemSessionDataSocket(i) != null){
				currVar = itemsCollectionSessionDataSocket.getItemSessionDataSocket(i).getVariableProviderSocket().getVariableValue(identifier);
				if (currVar == null)
					continue;
				currValue = currVar.getValuesShort();
				if (currValue != null  &&  currValue.length() > 0){
					if (currValue.matches("^[0-9]+$")){
						currValueInt = IntegerUtils.tryParseInt( currValue );
						total += currValueInt;
					} else if ("TRUE".equals(currValue.toUpperCase())){
						total += 1;
					}
				}
			}
		}
		return total;
	}

//	@Override
//	public JavaScriptObject getJsSocket() {
//		return createJsSocket();
//	}
//
//	protected native JavaScriptObject createJsSocket()/*-{
//		var socket = [];
//		var instance = this;
//		socket.getVariableIdentifiers = function(){
//			return instance.@eu.ydp.empiria.player.client.controller.variables.storage.assessment.AssessmentVariableStorageImpl::getAssessmentVariableIdentifiersShort()();
//		}
//		socket.getVariableValue = function(identifier){
//			return instance.@eu.ydp.empiria.player.client.controller.variables.storage.assessment.AssessmentVariableStorageImpl::getAssessmentVariableValuesShort(Ljava/lang/String;)(identifier);
//		}
//		return socket;
//	}-*/;
//
//	private String getAssessmentVariableValuesShort(String key) {
//		Outcome o = getVariableValue(key);
//		if (o != null)
//			return o.getValuesShort();
//		return "";
//	}
//	
//	private String getAssessmentVariableIdentifiersShort(){
//		String stringShort = StringUtils.setToStringShort(identifiers);		
//		return stringShort;
//	}

	
}
