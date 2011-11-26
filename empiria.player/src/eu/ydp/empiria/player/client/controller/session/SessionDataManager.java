package eu.ydp.empiria.player.client.controller.session;

import java.util.Map;
import java.util.Set;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.json.client.JSONArray;
import com.google.gwt.json.client.JSONNull;

import eu.ydp.empiria.player.client.controller.events.interaction.InteractionEventsListener;
import eu.ydp.empiria.player.client.controller.events.interaction.StateChangedInteractionEvent;
import eu.ydp.empiria.player.client.controller.session.datasockets.AssessmentSessionDataSocket;
import eu.ydp.empiria.player.client.controller.session.datasockets.ItemSessionDataSocket;
import eu.ydp.empiria.player.client.controller.session.datasockets.ItemsCollectionSessionDataSocket;
import eu.ydp.empiria.player.client.controller.session.datasockets.SessionDataSocket;
import eu.ydp.empiria.player.client.controller.session.datasupplier.ItemSessionDataSupplier;
import eu.ydp.empiria.player.client.controller.session.datasupplier.AssessmentSessionDataSupplier;
import eu.ydp.empiria.player.client.controller.session.datasupplier.SessionDataSupplier;
import eu.ydp.empiria.player.client.controller.session.sockets.ItemSessionSocket;
import eu.ydp.empiria.player.client.controller.session.sockets.PageSessionSocket;
import eu.ydp.empiria.player.client.controller.session.sockets.SessionSocket;
import eu.ydp.empiria.player.client.controller.variables.VariableProviderSocket;
import eu.ydp.empiria.player.client.controller.variables.objects.outcome.Outcome;
import eu.ydp.empiria.player.client.controller.variables.storage.assessment.AssessmentVariableStorageImpl;
import eu.ydp.empiria.player.client.module.IStateful;
import eu.ydp.empiria.player.client.util.StringUtils;

public class SessionDataManager implements SessionSocket, IStateful, SessionDataSupplier, SessionDataSocket {

	private ItemSessionData[] itemSessionDatas;
	private InteractionEventsListener listener;
	private AssessmentVariableStorageImpl variableProvider;
	
	public SessionDataManager(InteractionEventsListener l){
		listener = l;
		variableProvider = new AssessmentVariableStorageImpl();
	}
	
	public void init(int itemsCount){
		if (itemSessionDatas == null) {
			itemSessionDatas = new ItemSessionData[itemsCount];
			for (int i = 0 ; i < itemsCount ; i ++)
				itemSessionDatas[i] = new ItemSessionData();
		}
		variableProvider.init(this);
	}
	
	@Override
	public ItemSessionSocket getItemSessionSocket() {
		return this;
	}

	@Override
	public PageSessionSocket getPageSessionSocket() {
		return this;
	}


	@Override
	public JSONArray getState(int itemIndex) {
		if (itemSessionDatas[itemIndex] != null)
			return itemSessionDatas[itemIndex].getItemBodyState();
		else
			return new JSONArray();
	}

	@Override
	public void setState(int itemIndex, JSONArray st) {
		if (itemSessionDatas[itemIndex] == null)
			itemSessionDatas[itemIndex] = new ItemSessionData();
		itemSessionDatas[itemIndex].setItemBodyState(st);
		
	}

	@Override
	public void updateItemVariables(int itemIndex, Map<String, Outcome> variablesMap) {
		if (itemSessionDatas[itemIndex] != null)
			itemSessionDatas[itemIndex].updateVariables(variablesMap);
	}

	@Override
	public void setState(JSONArray statesArr) {
		try {			  
			JSONArray itemStates = (JSONArray)statesArr.get(0);
			for (int i = 0 ; i < itemSessionDatas.length ; i ++ ){
				if (itemStates.get(i).isArray() instanceof JSONArray){
					itemSessionDatas[i] = new ItemSessionData();  
					itemSessionDatas[i].setState(itemStates.get(i));
			  	} else {
			  		itemSessionDatas[i] = null;
			  	}
			}
			listener.onStateChanged(new StateChangedInteractionEvent(false, null));
		} catch (Exception e) {
		}
		
	}

	@Override
	public JSONArray getState() {
		JSONArray itemStates = new JSONArray();
		int counter = 0;
		for (ItemSessionData isd : itemSessionDatas){
			if (isd != null)
				itemStates.set(counter++, isd.getState());
			else
				itemStates.set(counter++, JSONNull.getInstance());
		}
		JSONArray mainState = new JSONArray();
		mainState.set(0, itemStates);
		return mainState;
	}
	
	private int tryParseInt(String s){
		try {
			return Integer.parseInt(s);
		} catch (Exception e) {
		}
		return 0;
	}

	@Override
	public void beginItemSession(int itemIndex) {
		if (itemSessionDatas[itemIndex] == null)
			itemSessionDatas[itemIndex] = new ItemSessionData();
		itemSessionDatas[itemIndex].begin();
		
	}

	@Override
	public void endItemSession(int itemIndex) {
		itemSessionDatas[itemIndex].end();
		
	}

	@Override
	public int getTimeAssessmentTotal(){
		if (itemSessionDatas == null)
			return 0;
		
		int count = 0;
		
		for (int i = 0 ; i < itemSessionDatas.length ; i ++){
			if (itemSessionDatas[i] != null){
				count += itemSessionDatas[i].getActualTime();
			}
		}
		
		return count;
	}
	/*
	public int getAssessmentTotalVariableSum(String name){
		
		int value = 0;
		
		for (int i = 0 ; i < itemSessionDatas.length ; i ++){
			try{
				Integer currValue = tryParseInt( itemSessionDatas[i].getStoredVariableValue0(name) );
				value += currValue;
			} catch (Exception e) {
			}
		}
		
		return value;
		
	}
	 */

	@Override
	public JavaScriptObject getJsSocket() {
		return createAssessmentSessionDataJsSocket();
	}
	
	private native JavaScriptObject createAssessmentSessionDataJsSocket()/*-{
		var socket = [];
		var instance = this;
		socket.getVariableManagerSocket = function(){
			return instance.@eu.ydp.empiria.player.client.controller.session.SessionDataManager::getVariableProviderJsSocket()();
		}
		socket.getTime = function(){
			return instance.@eu.ydp.empiria.player.client.controller.session.SessionDataManager::getTimeAssessmentTotal()();
		}
		return socket
	}-*/;
	
	private JavaScriptObject getVariableProviderJsSocket(){
		return variableProvider.getJsSocket();
	}
	
	public SessionDataSocket getAssessmentSessionDataSocket(){
		return this;
	}

	@Override
	public ItemSessionDataSocket getItemSessionDataSocket(int index) {
		if (index < itemSessionDatas.length)
			return itemSessionDatas[index].getItemSessionDataSocket();
		return null;
	}

	@Override
	public int getItemSessionDataSocketsCount() {
		return itemSessionDatas.length;
	}

	@Override
	public VariableProviderSocket getVariableProviderSocket() {
		return variableProvider;
	}


}
