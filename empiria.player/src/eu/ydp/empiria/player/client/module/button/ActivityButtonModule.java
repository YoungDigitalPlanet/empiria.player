package eu.ydp.empiria.player.client.module.button;

import java.util.Stack;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.PushButton;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.xml.client.Element;

import eu.ydp.empiria.player.client.controller.events.delivery.DeliveryEvent;
import eu.ydp.empiria.player.client.module.ControlModule;
import eu.ydp.empiria.player.client.module.IGroup;
import eu.ydp.empiria.player.client.module.IModule;
import eu.ydp.empiria.player.client.module.ISimpleModule;
import eu.ydp.empiria.player.client.module.ModuleSocket;
import eu.ydp.empiria.player.client.module.containers.group.GroupIdentifier;
import eu.ydp.empiria.player.client.module.listener.ModuleInteractionListener;

public abstract class ActivityButtonModule extends ControlModule implements ISimpleModule {

	protected PushButton button;
	protected ModuleSocket moduleSocket;
	protected boolean isEnabled = true;
	
	public ActivityButtonModule(){
		button = new PushButton();
		updateStyleName();
		button.addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				invokeRequest();
			}
		});
	}
	
	@Override
	public void initModule(Element element, ModuleSocket ms, ModuleInteractionListener mil) {
		moduleSocket = ms;
	}

	@Override
	public Widget getView() {
		return button;
	}
	
	protected GroupIdentifier getCurrentGroupIdentifier(){
		return moduleSocket.getParentGroupIdentifier(this);
	}
	
	protected boolean currentGroupIsConcerned(GroupIdentifier gi){
		Stack<IModule> parentsHierarchy = moduleSocket.getParentsHierarchy(this);
		for (IModule currModule : parentsHierarchy){
			if (currModule instanceof IGroup){
				if ( ((IGroup)currModule).getGroupIdentifier().equals(gi) ){
					return true;
				}
			}
		}
		return false;
	}
	
	protected abstract void invokeRequest();

	protected void updateStyleName(){
		button.setStyleName(getCurrentStyleName(isEnabled));
	}
	
	protected String getCurrentStyleName(boolean isEnabled){
		String styleName = null;
		
		if(isEnabled){
			styleName = getStyleName();
		}else{
			styleName = getStyleName() + "-disabled";
		}
		
		return styleName;
	}

	protected abstract String getStyleName();
	
}
