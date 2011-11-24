package eu.ydp.empiria.player.client.controller.session;

import java.util.Date;
import java.util.Map;
import java.util.Set;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.json.client.JSONArray;
import com.google.gwt.json.client.JSONNumber;
import com.google.gwt.json.client.JSONValue;

import eu.ydp.empiria.player.client.controller.session.datasockets.ItemSessionDataSocket;
import eu.ydp.empiria.player.client.controller.style.variables.VariableProviderSocket;
import eu.ydp.empiria.player.client.controller.variables.objects.BaseType;
import eu.ydp.empiria.player.client.controller.variables.objects.Cardinality;
import eu.ydp.empiria.player.client.controller.variables.objects.outcome.Outcome;
import eu.ydp.empiria.player.client.controller.variables.storage.item.ItemVariableStorageImpl;

public class ItemSessionData implements ItemSessionDataSocket {
	
	public long timeStarted;
	public int time;
	protected JSONArray itemBodyState;	
	protected ItemVariableStorageImpl<Outcome> variableStorage;

	public ItemSessionData(){
		timeStarted = 0;
		time = 0;
		itemBodyState = new JSONArray();
		variableStorage = new ItemVariableStorageImpl<Outcome>();
	}
	
	public void begin(){
		timeStarted = (long) ((new Date()).getTime() * 0.001);
		variableStorage.putVariable("VISITED", new Outcome("VISITED", Cardinality.SINGLE, BaseType.BOOLEAN, "TRUE"));
	}
	
	public void end(){
		time += ((long) ((new Date()).getTime() * 0.001) - timeStarted);
		timeStarted = 0;
	}
	
	public int getActualTime(){
		if (timeStarted != 0)
			return time + (int)((long) ((new Date()).getTime() * 0.001) - timeStarted);
		else if (time != 0)
			return time;
		else
			return 0;
	}
	
	public JSONValue getState(){
		JSONArray stateArr = new JSONArray();
		stateArr.set(0, itemBodyState);
		stateArr.set(1, variableStorage.toJSON());
		stateArr.set(2, new JSONNumber(getActualTime()));
		return stateArr;
	}
	
	public void setState(JSONValue value){
		JSONArray stateArr = (JSONArray)value;
		if (stateArr.size() == 3){
			itemBodyState = stateArr.get(0).isArray();
			variableStorage.fromJSON(stateArr.get(1));
			time = (int)stateArr.get(2).isNumber().doubleValue();
		}
	}

	public JSONArray getItemBodyState(){
		return itemBodyState;
	}
	public void setItemBodyState(JSONArray newState){
		itemBodyState = newState;
	}
	
	public void updateVariables(Map<String, Outcome> variablesMap){
		variableStorage.importFromMap(variablesMap);
	}
	
	public ItemSessionDataSocket getItemSessionDataSocket(){
		return this;
	}
	
	public JavaScriptObject getJsSocket(){
		return createJsObject();
	}
	
	private native JavaScriptObject createJsObject()/*-{
		var obj = [];
		var instance = this;
		obj.getVariableManagerSocket = function(){
			return instance.@eu.ydp.empiria.player.client.controller.session.ItemSessionData::getVariableStorageJsSocket()();
		}
		obj.getTime = function(){
			return instance.@eu.ydp.empiria.player.client.controller.session.ItemSessionData::getActualTime()();
		}
		return obj;
	}-*/;
	
	private JavaScriptObject getVariableStorageJsSocket(){
		return variableStorage.getJsSocket();
	}
	@Override
	public VariableProviderSocket getVariableProviderSocket() {
		return variableStorage;
	}
}
