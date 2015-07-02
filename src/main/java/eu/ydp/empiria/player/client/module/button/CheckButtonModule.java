package eu.ydp.empiria.player.client.module.button;

import com.google.gwt.xml.client.Element;
import com.google.inject.Inject;

import eu.ydp.empiria.player.client.controller.events.delivery.DeliveryEvent;
import eu.ydp.empiria.player.client.controller.flow.request.FlowRequest;
import eu.ydp.empiria.player.client.module.containers.group.GroupIdentifier;
import eu.ydp.empiria.player.client.util.events.internal.bus.EventsBus;
import eu.ydp.empiria.player.client.util.events.internal.player.PlayerEvent;
import eu.ydp.empiria.player.client.util.events.internal.player.PlayerEventHandler;
import eu.ydp.empiria.player.client.util.events.internal.player.PlayerEventTypes;

public class CheckButtonModule extends AbstractActivityButtonModule implements PlayerEventHandler {

	@Inject
	protected EventsBus eventsBus;

	@Override
	public void initModule(Element element) {
		super.initModule(element);
		eventsBus.addHandler(PlayerEvent.getType(PlayerEventTypes.PAGE_CHANGING), this);
	}

	protected boolean isSelected = false;

	@Override
	public void onDeliveryEvent(DeliveryEvent flowEvent) {
		Object groupIdentifier = flowEvent.getParams().get("groupIdentifier");
		switch (flowEvent.getType()) {
		case CHECK:
			if (groupIdentifier == null || currentGroupIsConcerned((GroupIdentifier) groupIdentifier)) {
				isSelected = true;
			}
			break;
		case CONTINUE:
		case SHOW_ANSWERS:
		case RESET:
			if (groupIdentifier == null || currentGroupIsConcerned((GroupIdentifier) groupIdentifier)) {
				isSelected = false;
			}
			break;
		default:
			break;
		}
		updateStyleName();
	}

	@Override
	public void onPlayerEvent(PlayerEvent event) {
		if (isSelected && event.getType() == PlayerEventTypes.PAGE_CHANGING) {
			flowRequestInvoker.invokeRequest(new FlowRequest.Continue(getCurrentGroupIdentifier()));
		}
	}

	@Override
	protected void invokeRequest() {
		if (isSelected) {
			flowRequestInvoker.invokeRequest(new FlowRequest.Continue(getCurrentGroupIdentifier()));
		} else {
			flowRequestInvoker.invokeRequest(new FlowRequest.Check(getCurrentGroupIdentifier()));
		}
	}

	@Override
	protected String getStyleName() {
		return "qp-" + (isSelected ? "continue" : "markall") + "-button";
	}

}
