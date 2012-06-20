package eu.ydp.empiria.player.client.module.pageswitch;

import com.google.gwt.core.client.JsArray;
import com.google.gwt.dom.client.Touch;
import com.google.gwt.event.dom.client.TouchEndEvent;
import com.google.gwt.event.dom.client.TouchEndHandler;
import com.google.gwt.event.dom.client.TouchMoveEvent;
import com.google.gwt.event.dom.client.TouchMoveHandler;
import com.google.gwt.event.dom.client.TouchStartEvent;
import com.google.gwt.event.dom.client.TouchStartHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.xml.client.Element;

import eu.ydp.empiria.player.client.controller.events.delivery.DeliveryEvent;
import eu.ydp.empiria.player.client.module.ControlModule;
import eu.ydp.empiria.player.client.module.button.NavigationButtonDirection;
import eu.ydp.gwtutil.client.util.UserAgentChecker;

public class TouchPageSwitch extends ControlModule {
	int start = 0;
	int end = 0;
	int startY = 0, endY = 0;
	final int swipeLength = 220;
	final int stackAndroidSwipeLength = 20;

	public TouchPageSwitch() {
		RootPanel.get().addDomHandler(new TouchEndHandler() {
			@Override
			public void onTouchEnd(TouchEndEvent event) {
				JsArray<Touch> touches = event.getChangedTouches();
				if (touches != null) {
					for (int x = 0; x < touches.length(); ++x) {
						Touch t = touches.get(x);
						end = t.getPageX();
						endY = t.getScreenY();
						break;
					}
				}
				if (end > 0 && (Window.getClientHeight()/2.5) > (startY > endY ? startY - endY : endY - startY)) {
					switchPage(swipeLength);
				}
			}
		}, TouchEndEvent.getType());
		RootPanel.get().addDomHandler(new TouchMoveHandler() {
			@Override
			public void onTouchMove(TouchMoveEvent event) {
				JsArray<Touch> touches = event.getTouches();
				if (touches != null) {
					for (int x = 0; x < touches.length();) {
						Touch t = touches.get(x);
						end = t.getPageX();
						endY = t.getScreenY();
						break;
					}
				}
				if (UserAgentChecker.isStackAndroidBrowser()) {
					if (end > 0) {
						switchPage(stackAndroidSwipeLength);
					}
				}

			}
		}, TouchMoveEvent.getType());

		RootPanel.get().addDomHandler(new TouchStartHandler() {
			@Override
			public void onTouchStart(TouchStartEvent event) {
				JsArray<Touch> touches = event.getTouches();
				if (touches != null) {
					for (int x = 0; x < touches.length();) {
						Touch t = touches.get(x);
						start = t.getPageX();
						startY = t.getScreenY();
						end = -1;
						break;
					}
				}
			}
		}, TouchStartEvent.getType());
	}

	private void switchPage(int swipeLength) {
		if (end > start && swipeLength < end - start) {
			flowRequestInvoker.invokeRequest(NavigationButtonDirection.getRequest(NavigationButtonDirection.PREVIOUS));
		} else if (start > end && swipeLength < start - end) {
			flowRequestInvoker.invokeRequest(NavigationButtonDirection.getRequest(NavigationButtonDirection.NEXT));
		}

	}

	@Override
	public Widget getView() {
		return null;
	}

	@Override
	public void onDeliveryEvent(DeliveryEvent flowEvent) {

	}

	@Override
	protected void initModule(Element element) {

	}

}
