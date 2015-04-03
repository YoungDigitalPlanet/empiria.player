package eu.ydp.empiria.player.client.controller.session;

import java.util.*;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.json.client.*;
import com.google.inject.Inject;

import eu.ydp.empiria.player.client.controller.communication.InitialData;
import eu.ydp.empiria.player.client.controller.events.interaction.StateChangedInteractionEvent;
import eu.ydp.empiria.player.client.controller.session.datasockets.*;
import eu.ydp.empiria.player.client.controller.session.datasupplier.SessionDataSupplier;
import eu.ydp.empiria.player.client.controller.session.sockets.*;
import eu.ydp.empiria.player.client.controller.variables.VariableProviderSocket;
import eu.ydp.empiria.player.client.controller.variables.objects.outcome.Outcome;
import eu.ydp.empiria.player.client.controller.variables.storage.assessment.AssessmentVariableStorageImpl;
import eu.ydp.empiria.player.client.module.IStateful;
import eu.ydp.empiria.player.client.util.events.bus.EventsBus;
import eu.ydp.empiria.player.client.util.events.scope.CurrentPageScope;
import eu.ydp.empiria.player.client.util.events.state.*;

public class SessionDataManager implements SessionSocket, IStateful, SessionDataSupplier, SessionDataSocket {

	private ItemSessionData[] itemSessionDatas;
	private final AssessmentVariableStorageImpl variableProvider;
	private final EventsBus eventsBus;

	@Inject
	public SessionDataManager(AssessmentVariableStorageImpl variableProvider, EventsBus eventsBus) {
		this.variableProvider = variableProvider;
		this.eventsBus = eventsBus;
	}

	public void init(int itemsCount, InitialData data) {
		if (itemSessionDatas == null) {
			itemSessionDatas = new ItemSessionData[itemsCount];
			for (int i = 0; i < itemsCount; i++) {
				itemSessionDatas[i] = new ItemSessionData();
				updateItemVariables(i, data.getItemInitialData(i).getOutcomes());
			}
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
		if (itemSessionDatas[itemIndex] != null) {
			return itemSessionDatas[itemIndex].getItemBodyState();
		} else {
			return new JSONArray();
		}
	}

	@Override
	public void setState(int itemIndex, JSONArray st) {
		if (itemSessionDatas[itemIndex] == null) {
			itemSessionDatas[itemIndex] = new ItemSessionData();
		}
		itemSessionDatas[itemIndex].setItemBodyState(st);

	}

	public void updateItemVariables(int itemIndex, Map<String, Outcome> variablesMap) {
		if (itemSessionDatas[itemIndex] != null) {
			itemSessionDatas[itemIndex].updateVariables(variablesMap);
		}
	}

	@Override
	public Map<String, Outcome> getOutcomeVariablesMap(int itemIndex) {
		if (itemSessionDatas[itemIndex] != null) {
			return itemSessionDatas[itemIndex].getOutcomeVariablesMap();
		}
		return new HashMap<>();
	}

	@Override
	public void setState(JSONArray statesArr) {
		try {
			JSONArray itemStates = (JSONArray) statesArr.get(0);
			for (int i = 0; i < itemSessionDatas.length; i++) {
				if (itemStates.get(i).isArray() instanceof JSONArray) {
					itemSessionDatas[i] = new ItemSessionData();
					itemSessionDatas[i].setState(itemStates.get(i));
				} else {
					itemSessionDatas[i] = null;
				}
			}
			eventsBus.fireEvent(new StateChangeEvent(StateChangeEventTypes.STATE_CHANGED, new StateChangedInteractionEvent(false, false, null)),
					new CurrentPageScope());
		} catch (Exception e) {
		}
	}

	@Override
	public JSONArray getState() {
		JSONArray itemStates = new JSONArray();
		int counter = 0;
		for (ItemSessionData isd : itemSessionDatas) {
			if (isd != null) {
				itemStates.set(counter++, isd.getState());
			} else {
				itemStates.set(counter++, JSONNull.getInstance());
			}
		}
		JSONArray mainState = new JSONArray();
		mainState.set(0, itemStates);
		return mainState;
	}

	@Override
	public void beginItemSession(int itemIndex) {
		if (itemSessionDatas[itemIndex] == null) {
			itemSessionDatas[itemIndex] = new ItemSessionData();
		}
		itemSessionDatas[itemIndex].begin();

	}

	@Override
	public void endItemSession(int itemIndex) {
		itemSessionDatas[itemIndex].end();

	}

	@Override
	public int getTimeAssessmentTotal() {
		if (itemSessionDatas == null) {
			return 0;
		}

		int count = 0;

		for (int i = 0; i < itemSessionDatas.length; i++) {
			if (itemSessionDatas[i] != null) {
				count += itemSessionDatas[i].getActualTime();
			}
		}

		return count;
	}

	@Override
	public JavaScriptObject getJsSocket() {
		return createAssessmentSessionDataJsSocket();
	}

	private native JavaScriptObject createAssessmentSessionDataJsSocket()/*-{
		var socket = [];
		var instance = this;
		socket.getVariableManagerSocket = function() {
			return instance.@eu.ydp.empiria.player.client.controller.session.SessionDataManager::getVariableProviderJsSocket()();
		}
		socket.getTime = function() {
			return instance.@eu.ydp.empiria.player.client.controller.session.SessionDataManager::getTimeAssessmentTotal()();
		}
		return socket
	}-*/;

	private JavaScriptObject getVariableProviderJsSocket() {
		return variableProvider.getJsSocket();
	}

	@Override
	public SessionDataSocket getAssessmentSessionDataSocket() {
		return this;
	}

	@Override
	public ItemSessionDataSocket getItemSessionDataSocket(int index) {
		if (index < itemSessionDatas.length) {
			return itemSessionDatas[index].getItemSessionDataSocket();
		}
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

	public void resetLessonsState() {
		for (ItemSessionData itemSessionData : itemSessionDatas) {
			itemSessionData.resetItemState();
		}
	}

}