package eu.ydp.empiria.player.client.module.button;


import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.PushButton;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.xml.client.Element;

import eu.ydp.empiria.player.client.controller.events.delivery.DeliveryEvent;
import eu.ydp.empiria.player.client.controller.events.delivery.DeliveryEventType;
import eu.ydp.empiria.player.client.module.ControlModule;
import eu.ydp.empiria.player.client.module.ISimpleModule;
import eu.ydp.empiria.player.client.module.ModuleSocket;
import eu.ydp.empiria.player.client.module.listener.ModuleInteractionListener;

public class NavigationButtonModule extends ControlModule implements ISimpleModule{
	
	private PushButton button;
	
	private NavigationButtonDirection direction;
	
	public NavigationButtonModule(NavigationButtonDirection dir){
		direction = dir;
	}
	
	@Override
	public void initModule(Element element, ModuleSocket ms,
								ModuleInteractionListener mil) {
	}
	
	@Override
	public void onDeliveryEvent(DeliveryEvent flowEvent) {
		String currentStyleName = null;
		Boolean isEnabled = true;
		
		if(flowEvent.getType().equals(DeliveryEventType.TEST_PAGE_LOADED)){
			if(direction.equals(NavigationButtonDirection.PREVIOUS)){
				isEnabled = (flowDataSupplier.getCurrentPageIndex() != 0);
			}else if(direction.equals(NavigationButtonDirection.NEXT)){
				isEnabled = !isLastPage();
			}
			
			currentStyleName = getCurrentStyleName(isEnabled);
			
			if(currentStyleName != null)
				button.setStylePrimaryName(currentStyleName);
		}
	}
	
	@Override
	public Widget getView() {
		if(button == null){
			button = new PushButton();
			button.setStyleName(getStyleName());
			button.addClickHandler(new ClickHandler() {
				
				@Override
				public void onClick(ClickEvent event) {
					flowRequestInvoker.invokeRequest(NavigationButtonDirection.getRequest(direction));
				}
			});
		}
		
		return button;
	}
	
	private String getStyleName(){
		return "qp-" + NavigationButtonDirection.getName(direction) + "-button";
	}
	
	private Boolean isLastPage(){
		return (flowDataSupplier.getCurrentPageIndex() == dataSourceSupplier.getItemsCount() - 1);
	}
	
	private String getCurrentStyleName(Boolean isEnabled){
		String styleName = null;
		
		if(isEnabled){
			styleName = getStyleName();
		}else{
			styleName = getStyleName() + "-disabled";
		}
		
		return styleName;
	}
}