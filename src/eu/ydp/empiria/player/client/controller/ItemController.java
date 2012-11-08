package eu.ydp.empiria.player.client.controller;

import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.Widget;

import eu.ydp.empiria.player.client.controller.body.IPlayerContainersAccessor;
import eu.ydp.empiria.player.client.controller.body.ModuleHandlerManager;
import eu.ydp.empiria.player.client.controller.communication.DisplayContentOptions;
import eu.ydp.empiria.player.client.controller.communication.ItemData;
import eu.ydp.empiria.player.client.controller.communication.sockets.ItemInterferenceSocket;
import eu.ydp.empiria.player.client.controller.events.activity.FlowActivityEvent;
import eu.ydp.empiria.player.client.controller.events.activity.FlowActivityEventType;
import eu.ydp.empiria.player.client.controller.events.interaction.InteractionEventsSocket;
import eu.ydp.empiria.player.client.controller.events.interaction.StateChangedInteractionEvent;
import eu.ydp.empiria.player.client.controller.flow.IFlowSocket;
import eu.ydp.empiria.player.client.controller.flow.processing.events.ActivityProcessingEvent;
import eu.ydp.empiria.player.client.controller.log.OperationLogEvent;
import eu.ydp.empiria.player.client.controller.log.OperationLogManager;
import eu.ydp.empiria.player.client.controller.session.sockets.ItemSessionSocket;
import eu.ydp.empiria.player.client.gin.PlayerGinjector;
import eu.ydp.empiria.player.client.module.ParenthoodSocket;
import eu.ydp.empiria.player.client.module.registry.ModulesRegistrySocket;
import eu.ydp.empiria.player.client.resources.StyleNameConstants;
import eu.ydp.empiria.player.client.style.StyleSocket;
import eu.ydp.empiria.player.client.util.events.bus.EventsBus;
import eu.ydp.empiria.player.client.util.events.page.PageEvent;
import eu.ydp.empiria.player.client.util.events.page.PageEventHandler;
import eu.ydp.empiria.player.client.util.events.page.PageEventTypes;
import eu.ydp.empiria.player.client.util.events.scope.CurrentPageScope;
import eu.ydp.empiria.player.client.util.events.state.StateChangeEvent;
import eu.ydp.empiria.player.client.util.events.state.StateChangeEventHandler;
import eu.ydp.empiria.player.client.util.events.state.StateChangeEventTypes;
import eu.ydp.empiria.player.client.view.item.ItemViewCarrier;
import eu.ydp.empiria.player.client.view.item.ItemViewSocket;

public class ItemController implements PageEventHandler, StateChangeEventHandler {

	@SuppressWarnings("PMD")
	public ItemController(ItemViewSocket ivs, IFlowSocket fs, InteractionEventsSocket is, ItemSessionSocket iss, ModulesRegistrySocket mrs, ModuleHandlerManager moduleHandlerManager) {
		itemViewSocket = ivs;
		itemSessionSocket = iss;
		interactionSocket = is;
		modulesRegistrySocket = mrs;
		this.moduleHandlerManager = moduleHandlerManager;
	}

	protected Item item;

	private int itemIndex;

	private final ItemViewSocket itemViewSocket;
	private final ItemSessionSocket itemSessionSocket;
	private final InteractionEventsSocket interactionSocket;
	private final ModulesRegistrySocket modulesRegistrySocket; // NOPMD
	private final StyleNameConstants styleNames = PlayerGinjector.INSTANCE.getStyleNameConstants();
	private final EventsBus eventsBus = PlayerGinjector.INSTANCE.getEventsBus();
	private StyleSocket styleSocket;
	private final ModuleHandlerManager moduleHandlerManager;

	IPlayerContainersAccessor accessor;

	public void setStyleSocket(StyleSocket styleSocket) {
		this.styleSocket = styleSocket;
	}

	// private ItemActivityIncidentsStats activityIncidentsStats;

	public void init(ItemData data, DisplayContentOptions options) {
		try {
			// Rejestrowanie na wszystkie eventy Page dawniej FLOW
			eventsBus.addHandler(PageEvent.getTypes(PageEventTypes.values()), this, new CurrentPageScope());
			eventsBus.addHandler(StateChangeEvent.getType(StateChangeEventTypes.STATE_CHANGED), this,new CurrentPageScope());
			if (data.data == null) {
				throw new Exception("Item data is null");// NOPMD
			}
			itemIndex = data.itemIndex;
			item = new Item(data.data, options, interactionSocket, styleSocket, modulesRegistrySocket, itemSessionSocket.getOutcomeVariablesMap(itemIndex), moduleHandlerManager);
			getAccessor().registerItemBodyContainer(itemIndex, item.getContentView());
			item.setState(itemSessionSocket.getState(itemIndex));
			itemViewSocket.setItemView(getItemViewCarrier(item, data, options.useSkin()));
			itemSessionSocket.beginItemSession(itemIndex);
			item.setUp();
			item.start();
		} catch (Exception e) {
			item = null;
			itemViewSocket.setItemView(new ItemViewCarrier(data.errorMessage.length() > 0 ? data.errorMessage : e.getClass().getName() + "<br/>" + e.getMessage() + "<br/>"
					+ e.getStackTrace()));
			//e.printStackTrace();
			OperationLogManager.logEvent(OperationLogEvent.DISPLAY_ITEM_FAILED);
			e.printStackTrace();
		}

	}

	public void close() {
		if (item != null) {
			item.close();
			itemSessionSocket.setState(itemIndex, item.getState());
			itemSessionSocket.endItemSession(itemIndex);
			//FIXME dorobic derejestracje ? czy mechanizm na poziomie eventsBus
			//interactionSocket.removeStateChangedInteractionEventsListener(this);
		}

	}

	@Override
	public void onStateChange(StateChangeEvent event) {
		if (event.getType() == StateChangeEventTypes.STATE_CHANGED && event.getValue() instanceof StateChangedInteractionEvent) {
			StateChangedInteractionEvent scie = (StateChangedInteractionEvent) event.getValue();
			item.process(scie.isUserInteract(), scie.getSender());
			// STATE
			itemSessionSocket.setState(itemIndex, item.getState());
			// item.updateItemSession(itemIndex, itemSessionSocket);
		}
	}

	public void checkItem() {
		if (item != null) {
			item.checkItem();
		}
	}

	public ItemInterferenceSocket getItemSocket() {
		return item;
	}

	protected ItemViewCarrier getItemViewCarrier(Item item, ItemData itemData, boolean useSkin) {
		ItemViewCarrier carrier = null;

		if (useSkin) {
			carrier = new ItemViewCarrier(item.getContentView());
		} else {
			String index = String.valueOf(itemData.itemIndex + 1);
			Widget titleWidget = createTitleWidget(index, item.getTitle());

			carrier = new ItemViewCarrier(titleWidget, item.getContentView(), item.getFeedbackView(), item.getScoreView());
		}

		return carrier;
	}

	protected Widget createTitleWidget(String index, String text) {
		Panel titlePanel = new FlowPanel();
		titlePanel.setStyleName(styleNames.QP_ITEM_TITLE());
		Label indexLabel = new Label(index + ". ");
		indexLabel.setStyleName(styleNames.QP_ITEM_TITLE_INDEX());
		Label textLabel = new Label(text);
		textLabel.setStyleName(styleNames.QP_ITEM_TITLE_TEXT());
		titlePanel.add(indexLabel);
		titlePanel.add(textLabel);
		return titlePanel;
	}

	public void setAssessmentParenthoodSocket(ParenthoodSocket parenthoodSocket) {
		if (item != null) {
			item.setAssessmentParenthoodSocket(parenthoodSocket);
		}
	}

	@Override
	public void onPageEvent(PageEvent event) {
		//wymuszone kompatibilnoscia wsteczna
		FlowActivityEvent newEvent;
		if (event.getValue() instanceof ActivityProcessingEvent) {
			newEvent = new FlowActivityEvent(FlowActivityEventType.valueOf(event.getType().name()), ((ActivityProcessingEvent) event.getValue()).getGroupIdentifier());
		} else {
			newEvent = new FlowActivityEvent(FlowActivityEventType.valueOf(event.getType().name()), null);
		}
		item.handleFlowActivityEvent(newEvent);

	}

	private IPlayerContainersAccessor getAccessor() {
		if (accessor == null){
			accessor = PlayerGinjector.INSTANCE.getPlayerContainersAccessor();
		}
		return accessor;
	}

	/**
	 * Checks whether the item body contains at least one interactive module
	 *
	 * @return boolean
	 */
	public boolean hasInteractiveModules() {
		return (item != null && item.hasInteractiveModules());
	}
}
