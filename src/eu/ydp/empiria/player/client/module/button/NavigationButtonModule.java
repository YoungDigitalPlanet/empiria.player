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
import eu.ydp.empiria.player.client.util.xml.XMLUtils;

public class NavigationButtonModule extends ControlModule implements ISimpleModule{
	
	private PushButton button;
	
	private NavigationButtonDirection direction;
	
	private String userStyleClass;
	
	private String moduleId;
	
	public NavigationButtonModule(NavigationButtonDirection dir){
		direction = dir;
	}
	
	@Override
	public void initModule(Element element, ModuleSocket ms,
								ModuleInteractionListener mil) {
		userStyleClass = XMLUtils.getAttributeAsString(element, "class");
		moduleId = XMLUtils.getAttributeAsString(element, "id");
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
			addUserStyle(button);
			setModuleId(button);
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
	
	private void addUserStyle(Widget view){
		if(userStyleClass != null && userStyleClass.trim().length() > 0)
			view.addStyleName(userStyleClass);
	}
	
	private void setModuleId(Widget view){
		if(moduleId != null && moduleId.trim().length() > 0)
			view.getElement().setId(moduleId);
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
