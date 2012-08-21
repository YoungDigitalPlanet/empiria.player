package eu.ydp.empiria.player.client.module.button;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.ui.PushButton;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.xml.client.Element;

import eu.ydp.empiria.player.client.controller.events.delivery.DeliveryEvent;
import eu.ydp.empiria.player.client.controller.events.delivery.DeliveryEventType;
import eu.ydp.empiria.player.client.module.ControlModule;
import eu.ydp.empiria.player.client.module.ISimpleModule;
import eu.ydp.empiria.player.client.util.events.player.PlayerEvent;
import eu.ydp.empiria.player.client.util.events.player.PlayerEventHandler;
import eu.ydp.empiria.player.client.util.events.player.PlayerEventTypes;
import eu.ydp.empiria.player.client.util.events.scope.CurrentPageScope;

public class NavigationButtonModule extends ControlModule implements ISimpleModule, PlayerEventHandler {

	private PushButton button;
	private boolean enabled = true;
	private final NavigationButtonDirection direction;

	public NavigationButtonModule(NavigationButtonDirection dir) {
		direction = dir;
	}

	@Override
	public void initModule(Element element) {
		eventsBus.addHandler(PlayerEvent.getTypes(PlayerEventTypes.PAGE_LOADED, PlayerEventTypes.BEFORE_FLOW), this, new CurrentPageScope());
	}

	private boolean isEnd() {
		boolean retValue = false;
		if (direction.equals(NavigationButtonDirection.PREVIOUS)) {
			retValue = (flowDataSupplier.getCurrentPageIndex() == 0);
		} else if (direction.equals(NavigationButtonDirection.NEXT)) {
			retValue = isLastPage();
		}
		return retValue;
	}

	private void setStyleName(){
		String currentStyleName = getCurrentStyleName(isEnabled());
		if (currentStyleName != null) {
			button.setStylePrimaryName(currentStyleName);
		}
	}

	@Override
	public void onDeliveryEvent(DeliveryEvent flowEvent) {
		if (flowEvent.getType().equals(DeliveryEventType.ASSESSMENT_STARTED)) {
			setEnabled(!isEnd());
			setStyleName();
		}
		if (flowEvent.getType().equals(DeliveryEventType.TEST_PAGE_LOADED)) {
			setStyleName();
		}
	}

	public void setEnabled(boolean isEnabled) {
		this.enabled = isEnabled;
	}

	public boolean isEnabled() {
		return enabled;
	}

	@Override
	public Widget getView() {
		if (button == null) {
			button = new PushButton();
			button.setStyleName(getStyleName());
			button.addClickHandler(new ClickHandler() {

				@Override
				public void onClick(ClickEvent event) {
					if (isEnabled() && !isEnd()) {
						flowRequestInvoker.invokeRequest(NavigationButtonDirection.getRequest(direction));
					}
				}
			});
		}

		return button;
	}

	private String getStyleName() {
		return "qp-" + NavigationButtonDirection.getName(direction) + "-button";
	}

	private Boolean isLastPage() {
		return (flowDataSupplier.getCurrentPageIndex() == dataSourceSupplier.getItemsCount() - 1);
	}

	private String getCurrentStyleName(Boolean isEnabled) {
		String styleName = null;

		if (isEnabled) {
			styleName = getStyleName();
		} else {
			styleName = getStyleName() + "-disabled";
		}

		return styleName;
	}

	@Override
	public void onPlayerEvent(PlayerEvent event) {
		if (event.getType() == PlayerEventTypes.PAGE_LOADED) {
			Timer timer = new Timer() {
				@Override
				public void run() {
					setEnabled(true && !isEnd());
					setStyleName();
				}
			};
			timer.schedule(300);
		} else if (event.getType() == PlayerEventTypes.BEFORE_FLOW) {
			setEnabled(false);
			setStyleName();
		}

	}
}
