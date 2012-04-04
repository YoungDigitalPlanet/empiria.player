package eu.ydp.empiria.player.client.controller;

import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.Widget;

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
import eu.ydp.empiria.player.client.module.ParenthoodSocket;
import eu.ydp.empiria.player.client.module.registry.ModulesRegistrySocket;
import eu.ydp.empiria.player.client.style.StyleSocket;
import eu.ydp.empiria.player.client.view.item.ItemViewCarrier;
import eu.ydp.empiria.player.client.view.item.ItemViewSocket;

public class ItemController implements StateChangedInteractionEventListener, FlowActivityEventsHandler {

	public ItemController(ItemViewSocket ivs, IFlowSocket fs, InteractionEventsSocket is, ItemSessionSocket iss, ModulesRegistrySocket mrs){
		itemViewSocket = ivs;
		flowSocket = fs;
		itemSessionSocket = iss;
		interactionSocket = is;
		modulesRegistrySocket = mrs;
		
	}
	
	private Item item;
	
	private int itemIndex;
	
	private ItemViewSocket itemViewSocket;
	private ItemSessionSocket itemSessionSocket;
	private IFlowSocket flowSocket;
	private InteractionEventsSocket interactionSocket;
	private ModulesRegistrySocket modulesRegistrySocket;

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
			itemIndex = data.itemIndex;
			item = new Item(data.data, options, interactionSocket, styleSocket, modulesRegistrySocket, itemSessionSocket.getOutcomeVariablesMap(itemIndex));
			item.setState(itemSessionSocket.getState(itemIndex));
			itemViewSocket.setItemView(getItemViewCarrier(item, data, options.useSkin()));
			itemSessionSocket.beginItemSession(itemIndex);
			item.setUp();
			item.start();
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
			
			//item.updateItemSession(itemIndex, itemSessionSocket);
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
	
	public ItemInterferenceSocket getItemSocket(){
		return item;
	}
	
	protected ItemViewCarrier getItemViewCarrier(Item item, ItemData itemData, boolean useSkin){
		ItemViewCarrier carrier = null;
		
		if(useSkin){
			carrier = new ItemViewCarrier(item.getContentView());
		}else{
			String index = String.valueOf(itemData.itemIndex + 1);
			Widget titleWidget = createTitleWidget(index, item.getTitle());
			
			carrier = new ItemViewCarrier(titleWidget, item.getContentView(), item.getFeedbackView(), item.getScoreView());
		}
		
		return carrier;
	}
	
	protected Widget createTitleWidget(String index, String text){
		Panel titlePanel = new FlowPanel();
		titlePanel.setStyleName("qp-item-title");
		Label indexLabel = new Label(index+". ");
		indexLabel.setStyleName("qp-item-title-index");
		Label textLabel = new Label(text);
		textLabel.setStyleName("qp-item-title-text");
		titlePanel.add(indexLabel);
		titlePanel.add(textLabel);
		return titlePanel;
	}

	public void setAssessmentParenthoodSocket(ParenthoodSocket parenthoodSocket) {
		if (item != null){
			item.setAssessmentParenthoodSocket(parenthoodSocket);
		}
	}
}
