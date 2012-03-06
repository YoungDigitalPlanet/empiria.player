package eu.ydp.empiria.player.client.module.test.navigation;

import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.xml.client.Element;

import eu.ydp.empiria.player.client.controller.events.delivery.DeliveryEvent;
import eu.ydp.empiria.player.client.controller.events.delivery.DeliveryEventType;
import eu.ydp.empiria.player.client.controller.flow.request.FlowRequest;
import eu.ydp.empiria.player.client.module.ISimpleModule;
import eu.ydp.empiria.player.client.module.ModuleSocket;
import eu.ydp.empiria.player.client.module.controls.ControlModule;
import eu.ydp.empiria.player.client.module.listener.ModuleInteractionListener;
import eu.ydp.empiria.player.client.module.test.navigation.pagesswitch.IPagesSwitchWidget;
import eu.ydp.empiria.player.client.module.test.navigation.pagesswitch.PagesSwitchListBox;
import eu.ydp.empiria.player.client.style.StyleUtil;

public class PagesSwitch extends ControlModule implements ISimpleModule, ChangeHandler {

	protected IPagesSwitchWidget switchWidget;

	@Override
	public void initModule(Element element, ModuleSocket ms,
			ModuleInteractionListener mil) {
	}

	@Override
	public Widget getView() {
		if (switchWidget == null) {
			switchWidget = new PagesSwitchListBox();
			switchWidget.addChangeHandler(this);
			switchWidget.setItemsCount(dataSourceSupplier.getItemsCount());
			
			((Widget)switchWidget).setStyleName(getStyleName());
		}

		return (Widget) switchWidget;
	}

	@Override
	public void onChange(ChangeEvent event) {
		flowRequestInvoker.invokeRequest(
				new FlowRequest.NavigateGotoItem(
							switchWidget.getCurrentIndex()
						)
				);
	}

	@Override
	public void onDeliveryEvent(DeliveryEvent flowEvent) {
		if (flowEvent.getType() == DeliveryEventType.TEST_PAGE_LOADED) {
			switchWidget.setCurrentIndex(
					flowDataSupplier.getCurrentPageIndex()
				);
		}
	}
	
	protected String getStyleName(){
		return StyleUtil.getStyleName("page-counter-list");
	}
}