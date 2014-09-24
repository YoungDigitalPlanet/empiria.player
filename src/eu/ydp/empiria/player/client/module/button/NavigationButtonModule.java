package eu.ydp.empiria.player.client.module.button;

import static eu.ydp.empiria.player.client.util.events.player.PlayerEventTypes.*;

import com.google.common.base.Optional;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.ui.PushButton;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.xml.client.Element;
import com.google.inject.Inject;
import com.google.inject.assistedinject.Assisted;

import eu.ydp.empiria.player.client.controller.events.delivery.DeliveryEvent;
import eu.ydp.empiria.player.client.controller.extensions.internal.workmode.PlayerWorkMode;
import eu.ydp.empiria.player.client.controller.extensions.internal.workmode.PlayerWorkModeService;
import eu.ydp.empiria.player.client.module.ControlModule;
import eu.ydp.empiria.player.client.module.ISimpleModule;
import eu.ydp.empiria.player.client.module.workmode.WorkModeSwitcher;
import eu.ydp.empiria.player.client.module.workmode.WorkModeTestClient;
import eu.ydp.empiria.player.client.resources.StyleNameConstants;
import eu.ydp.empiria.player.client.util.events.bus.EventsBus;
import eu.ydp.empiria.player.client.util.events.player.PlayerEvent;
import eu.ydp.empiria.player.client.util.events.player.PlayerEventHandler;
import eu.ydp.empiria.player.client.util.events.scope.CurrentPageScope;
import eu.ydp.gwtutil.client.ui.button.CustomPushButton;

public class NavigationButtonModule extends ControlModule implements ISimpleModule, PlayerEventHandler, WorkModeTestClient {

	private PushButton button;
	private boolean enabled = true;
	private final NavigationButtonDirection direction;

	protected EventsBus eventsBus;
	private final StyleNameConstants styleNameConstants;
	private final PlayerWorkModeService playerWorkModeService;

	@Inject
	public NavigationButtonModule(@Assisted NavigationButtonDirection dir, StyleNameConstants styleNameConstants, PlayerWorkModeService playerWorkModeService,
			EventsBus eventsBus) {
		direction = dir;
		this.eventsBus = eventsBus;
		this.styleNameConstants = styleNameConstants;
		this.playerWorkModeService = playerWorkModeService;
	}

	@Override
	public void initModule(Element element) {
		eventsBus.addHandler(PlayerEvent.getTypes(PAGE_LOADED, BEFORE_FLOW, PAGE_CHANGE), this, new CurrentPageScope());
	}

	private boolean isFirstPage() {
		return (flowDataSupplier.getCurrentPageIndex() == 0);
	}

	private boolean isEnd() {
		boolean retValue = false;
		if (direction.equals(NavigationButtonDirection.PREVIOUS)) {
			retValue = isFirstPage();
		} else if (direction.equals(NavigationButtonDirection.NEXT)) {
			retValue = isLastPage();
		}
		return retValue;
	}

	private void setStyleName() {
		String currentStyleName = getCurrentStyleName(isEnabled());
		if (currentStyleName != null) {
			button.setStylePrimaryName(currentStyleName);
		}
	}

	@Override
	public void onDeliveryEvent(DeliveryEvent flowEvent) {
		switch (flowEvent.getType()) {
		case ASSESSMENT_STARTED:
		case CONTINUE:
		case CHECK:
		case SHOW_ANSWERS:
			setEnabled(!isEnd());
		case TEST_PAGE_LOADED:
			setStyleName();
			break;
		case PAGE_LOADING:
			activateCorrectWorkMode();
			break;
		default:
			break;
		}
	}

	private void activateCorrectWorkMode() {
		PlayerWorkMode currentWorkMode = playerWorkModeService.getCurrentWorkMode();
		WorkModeSwitcher currentWorkModeSwitcher = currentWorkMode.getWorkModeSwitcher();
		Optional<PlayerWorkMode> optionalPreviousWorkMode = playerWorkModeService.getPreviousWorkMode();
		if (optionalPreviousWorkMode.isPresent()) {
			PlayerWorkMode previousWorkMode = optionalPreviousWorkMode.get();
			WorkModeSwitcher previousWorkModeSwitcher = previousWorkMode.getWorkModeSwitcher();
			previousWorkModeSwitcher.disable(this);
		}
		currentWorkModeSwitcher.enable(this);
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
			button = new CustomPushButton();
			button.setStyleName(getStyleName());
			button.addClickHandler(new ClickHandler() {

				@Override
				public void onClick(ClickEvent event) {
					if (isEnabled() && !isEnd()) {
						flowRequestInvoker.invokeRequest(direction.getRequest());
					}
				}
			});
		}

		return button;
	}

	private String getStyleName() {
		return "qp-" + direction.getName() + "-button";
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
		if (isEnabled()) {
			if (event.getType() == PAGE_LOADED) {
				Timer timer = new Timer() {
					@Override
					public void run() {
						setEnabled(true && !isEnd());
						setStyleName();
					}
				};
				timer.schedule(300);
			} else if (event.getType() == BEFORE_FLOW) {
				setEnabled(false);
				setStyleName();
			}
		}

	}

	@Override
	public void enableTestMode() {
		setEnabled(false);
		button.addStyleName(styleNameConstants.QP_MODULE_MODE_TEST());
	}

	@Override
	public void disableTestMode() {
		setEnabled(true);
		button.removeStyleName(styleNameConstants.QP_MODULE_MODE_TEST());
	}
}
