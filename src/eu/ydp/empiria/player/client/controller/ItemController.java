package eu.ydp.empiria.player.client.controller;

import eu.ydp.empiria.player.client.controller.communication.DisplayContentOptions;
import eu.ydp.empiria.player.client.controller.communication.ItemData;
import eu.ydp.empiria.player.client.controller.communication.sockets.ItemInterferenceSocket;
import eu.ydp.empiria.player.client.controller.events.activity.FlowActivityEvent;
import eu.ydp.empiria.player.client.controller.events.activity.FlowActivityEventsHandler;
import eu.ydp.empiria.player.client.controller.events.interaction.InteractionEventType;
import eu.ydp.empiria.player.client.controller.events.interaction.InteractionEventsSocket;
import eu.ydp.empiria.player.client.controller.events.interaction.StateChangedInteractionEvent;
import eu.ydp.empiria.player.client.controller.events.interaction.StateChangedInteractionEventListener;
import eu.ydp.empiria.player.client.controller.flow.IFlowSocket;
import eu.ydp.empiria.player.client.controller.log.OperationLogEvent;
import eu.ydp.empiria.player.client.controller.log.OperationLogManager;
import eu.ydp.empiria.player.client.controller.session.sockets.ItemSessionSocket;
import eu.ydp.empiria.player.client.model.Item;
import eu.ydp.empiria.player.client.model.ItemVariablesAccessor;
import eu.ydp.empiria.player.client.style.StyleSocket;
import eu.ydp.empiria.player.client.view.item.ItemViewCarrier;
import eu.ydp.empiria.player.client.view.item.ItemViewSocket;

public class ItemController implements StateChangedInteractionEventListener, FlowActivityEventsHandler {

	public ItemController(ItemViewSocket ivs, IFlowSocket fs, InteractionEventsSocket is, ItemSessionSocket iss){
		itemViewSocket = ivs;
		flowSocket = fs;
		itemSessionSocket = iss;
		interactionSocket = is;
		
	}
	
	private Item item;
	
	private int itemIndex;
	
	private ItemViewSocket itemViewSocket;
	private ItemSessionSocket itemSessionSocket;
	private IFlowSocket flowSocket;
	private InteractionEventsSocket interactionSocket;

	private StyleSocket styleSocket;
	public void setStyleSocket( StyleSocket ss) {
		styleSocket = ss;
	}
	
	//private ItemActivityIncidentsStats activityIncidentsStats;
	
	public void init(ItemData data, DisplayContentOptions options){
		try {
			if (data.data == null)
				throw new Exception("Item data is null");
			interactionSocket.addStateChangedInteractionEventsListener(this);
			item = new Item(data.data, options, interactionSocket, styleSocket);
			itemIndex = data.itemIndex;
			item.setState(itemSessionSocket.getState(itemIndex));
			itemViewSocket.setItemView(new ItemViewCarrier(String.valueOf(itemIndex+1) + ". " + item.getTitle(), item.getContentView(), item.getFeedbackView(), item.getScoreView()));
			itemSessionSocket.beginItemSession(itemIndex);
			item.updateItemSession(itemIndex, itemSessionSocket);
		} catch (Exception e) {
			item = null;
			itemViewSocket.setItemView(new ItemViewCarrier(data.errorMessage.length() > 0 ? data.errorMessage : e.getClass().getName() + "<br/>" + e.getMessage() + "<br/>" + e.getStackTrace()));
			e.printStackTrace();
			OperationLogManager.logEvent(OperationLogEvent.DISPLAY_ITEM_FAILED);
		}
		
	}
	
	public void close(){
		if (item != null){
			item.close();
			itemSessionSocket.setState(itemIndex, item.getState());
			itemSessionSocket.endItemSession(itemIndex);		
			interactionSocket.removeStateChangedInteractionEventsListener(this);	
		}

	}

	@Override
	public void onStateChanged(StateChangedInteractionEvent event) {
		if (event.getType() == InteractionEventType.STATE_CHANGED  &&  event instanceof StateChangedInteractionEvent){
			StateChangedInteractionEvent scie = (StateChangedInteractionEvent)event;
			item.process(scie.isUserInteract(), scie.getSender() != null ? scie.getSender().getIdentifier() : "");
			// STATE
			itemSessionSocket.setState(itemIndex, item.getState());		
			
			// RESULT & MISTAKES
			item.updateItemSession(itemIndex, itemSessionSocket);
	//		itemSessionSocket.setSessionResult(itemIndex, item.getResult());
	//		int itemMistakes = item.getMistakesCount();
	//		if (itemMistakes > 0)
	//			itemSessionSocket.addSessionMistake(itemIndex);
		}
	}
	
	public void handleFlowActivityEvent(FlowActivityEvent event){
		if (item != null){
			item.handleFlowActivityEvent(event);
		}
	}
	
	public void checkItem(){
		if(item != null)
			item.checkItem();
	}

	public ItemVariablesAccessor getItemVariablesAccessor(){
		return item;
	}
	
	public ItemInterferenceSocket getItemSocket(){
		return item;
	}
}
