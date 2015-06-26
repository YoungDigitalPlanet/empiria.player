package eu.ydp.empiria.player.client.controller;

import static eu.ydp.empiria.player.client.util.events.internal.state.StateChangeEventTypes.*;

import com.google.gwt.user.client.ui.*;
import com.google.inject.Inject;
import com.google.inject.assistedinject.Assisted;
import eu.ydp.empiria.player.client.controller.body.IPlayerContainersAccessor;
import eu.ydp.empiria.player.client.controller.communication.*;
import eu.ydp.empiria.player.client.controller.communication.sockets.ItemInterferenceSocket;
import eu.ydp.empiria.player.client.controller.events.activity.*;
import eu.ydp.empiria.player.client.controller.events.interaction.StateChangedInteractionEvent;
import eu.ydp.empiria.player.client.controller.flow.processing.events.ActivityProcessingEvent;
import eu.ydp.empiria.player.client.controller.log.*;
import eu.ydp.empiria.player.client.controller.session.sockets.ItemSessionSocket;
import eu.ydp.empiria.player.client.gin.scopes.page.PageScoped;
import eu.ydp.empiria.player.client.module.ParenthoodSocket;
import eu.ydp.empiria.player.client.resources.StyleNameConstants;
import eu.ydp.empiria.player.client.util.events.internal.bus.EventsBus;
import eu.ydp.empiria.player.client.util.events.internal.page.*;
import eu.ydp.empiria.player.client.util.events.internal.player.*;
import eu.ydp.empiria.player.client.util.events.internal.scope.CurrentPageScope;
import eu.ydp.empiria.player.client.util.events.internal.state.*;
import eu.ydp.empiria.player.client.view.item.*;

public class ItemController implements PageEventHandler, StateChangeEventHandler {

	@Inject
	private StyleNameConstants styleNames;
	@Inject
	private EventsBus eventsBus;
	@Inject
	@PageScoped
	private ItemData data;
	@Inject
	private IPlayerContainersAccessor accessor;

	private final AssessmentControllerFactory controllerFactory;
	private final ItemViewSocket itemViewSocket;
	private final ItemSessionSocket itemSessionSocket;
	Item item;
	private int itemIndex;

	@Inject
	public ItemController(@Assisted ItemViewSocket ivs, @Assisted ItemSessionSocket iss, AssessmentControllerFactory controllerFactory) {
		itemViewSocket = ivs;
		itemSessionSocket = iss;
		this.controllerFactory = controllerFactory;
	}

	public void init(DisplayContentOptions options) {
		try {
			// Rejestrowanie na wszystkie eventy Page dawniej FLOW
			eventsBus.addHandler(PageEvent.getTypes(PageEventTypes.values()), this, new CurrentPageScope());
			eventsBus.addHandler(StateChangeEvent.getType(StateChangeEventTypes.STATE_CHANGED), this, new CurrentPageScope());

			if (data.getData() == null) {
				throw new Exception("Item data is null");// NOPMD
			}
			itemIndex = data.itemIndex;
			item = controllerFactory.getItem(options, itemSessionSocket.getOutcomeVariablesMap(itemIndex), itemSessionSocket.getState(itemIndex));
			accessor.registerItemBodyContainer(itemIndex, item.getContentView());

			itemViewSocket.setItemView(getItemViewCarrier(item, data, options.useSkin()));
			item.setUp();
			item.start();
			eventsBus.fireEvent(new PlayerEvent(PlayerEventTypes.PAGE_CREATED_AND_STARTED), new CurrentPageScope());
		} catch (Exception e) {
			item = null;

			itemViewSocket.setItemView(new ItemViewCarrier(data.errorMessage.length() > 0 ? data.errorMessage : e.getClass().getName() + "<br/>"
					+ e.getMessage() + "<br/>" + e.getStackTrace()));

			OperationLogManager.logEvent(OperationLogEvent.DISPLAY_ITEM_FAILED);
			e.printStackTrace();
		}
	}

	public void onShow() {
		item.onShow();
	}

	public void close() {
		if (item != null) {
			item.close();
			itemSessionSocket.setState(itemIndex, item.getState());
			// FIXME dorobic derejestracje ? czy mechanizm na poziomie eventsBus
			// interactionSocket.removeStateChangedInteractionEventsListener(this);
		}

	}

	@Override
	public void onStateChange(StateChangeEvent event) {
		if (event.getType() == StateChangeEventTypes.STATE_CHANGED && event.getValue() instanceof StateChangedInteractionEvent) {
			StateChangedInteractionEvent scie = event.getValue();
			item.process(scie.isUserInteract(), scie.isReset(), scie.getSender());
			eventsBus.fireEvent(new StateChangeEvent(OUTCOME_STATE_CHANGED, scie), new CurrentPageScope());
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
		ItemViewCarrier carrier;

		if (useSkin) {
			carrier = new ItemViewCarrier(item.getContentView());
		} else {
			String index = String.valueOf(itemData.itemIndex + 1);
			Widget titleWidget = createTitleWidget(index, item.getTitle());

			carrier = new ItemViewCarrier(titleWidget, item.getContentView(), item.getScoreView());
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
		// wymuszone kompatibilnoscia wsteczna
		FlowActivityEvent newEvent;
		if (event.getValue() instanceof ActivityProcessingEvent) {
			newEvent = new FlowActivityEvent(FlowActivityEventType.valueOf(event.getType().name()),
					((ActivityProcessingEvent) event.getValue()).getGroupIdentifier());
		} else {
			newEvent = new FlowActivityEvent(FlowActivityEventType.valueOf(event.getType().name()), null);
		}
		item.handleFlowActivityEvent(newEvent);
	}

	/**
	 * Checks whether the item body contains at least one interactive module
	 * 
	 * @return boolean
	 */
	public boolean hasInteractiveModules() {
		return (item != null && item.hasInteractiveModules());
	}

	public void resetItem() {
		item.resetItem();
	}
}
