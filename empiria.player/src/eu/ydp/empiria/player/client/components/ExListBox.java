package eu.ydp.empiria.player.client.components;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.PushButton;
import com.google.gwt.user.client.ui.Widget;

public class ExListBox extends FlowPanel {

	
	protected PopupPanel popupContainer;
	protected Panel popupOuterContainer;
	protected Panel popupOuterHeader;
	protected Panel popupOuterFooter;
	protected Panel popupInnerContainer;
	protected Panel popupContents;
	
	protected Panel baseContainer;
	protected Panel baseContents;
	protected PushButton baseButton;
	
	protected List<Widget> popupBodies;
	protected List<Widget> baseBodies;
	protected List<ExListBoxOption> options;
	
	protected int selectedIndex = 0;
	protected boolean enabled = true;
	
	protected ExListBoxChangeListener listener;

	
	public ExListBox(){
		super();
		
		setStyleName("qp-exlistbox");
		
		popupContents = new FlowPanel();
		popupContents.setStyleName("qp-exlistbox-popup-contents");
		popupOuterHeader = new FlowPanel();
		popupOuterHeader.setStyleName("qp-exlistbox-popup-outer-header");
		popupOuterFooter = new FlowPanel();
		popupOuterFooter.setStyleName("qp-exlistbox-popup-outer-footer");
		
		popupInnerContainer = new FlowPanel();
		popupInnerContainer.setStyleName("qp-exlistbox-popup-inner-container");
		popupInnerContainer.add(popupContents);
		
		popupOuterContainer = new FlowPanel();
		popupOuterContainer.setStyleName("qp-exlistbox-popup-outer-container");
		popupOuterContainer.add(popupOuterHeader);
		popupOuterContainer.add(popupInnerContainer);
		popupOuterContainer.add(popupOuterFooter);
		
		popupContainer = new PopupPanel(true);
		popupContainer.setAnimationEnabled(true);
		popupContainer.setStyleName("qp-exlistbox-popup-container");
		popupContainer.add(popupOuterContainer);
		
		baseButton = new PushButton("select");
		baseButton.setStyleName("qp-exlistbox-base-button");
		
		baseContents = new FlowPanel();
		baseContents.setStyleName("qp-exlistbox-base-contents");
		
		baseContainer = new FlowPanel();
		baseContainer.setStyleName("qp-exlistbox-base-container");
		baseContainer.addDomHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent arg0) {
				if (enabled){
					updateOptionButtonsSelection();
					popupContainer.show();
					updatePosition();
				}
			}
		}, ClickEvent.getType());
		
		baseContainer.add(baseContents);
		baseContainer.add(baseButton);
		add(baseContainer);
		
		options = new ArrayList<ExListBoxOption>();
		baseBodies = new ArrayList<Widget>();
		popupBodies = new ArrayList<Widget>();
				
	}
	
	public void addOption(Widget baseBody, Widget popupBody){
		baseBodies.add(baseBody);
		popupBodies.add(popupBody);
		final ExListBoxOption currOption = new ExListBoxOption(baseBody, popupBody);
		options.add(currOption);
		popupContents.add(currOption.getPopupBody());
		currOption.getPopupBody().addDomHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent arg0) {
				selectedIndex = options.indexOf(currOption);
				setSelectedBaseBody();
				listener.onChange();
			}
		}, ClickEvent.getType());
	}
	
	protected void setSelectedBaseBody(){
		baseContents.clear();
		if (selectedIndex >=0  &&  selectedIndex < options.size()){
			baseContents.add(options.get(selectedIndex).getBaseBody());
		}
		popupContainer.hide();		
	}

	protected void updatePosition(){
		int mountingPointX = 0;
		int mountingPointY = 0;
		int MARGIN = 8;
		
		mountingPointX = baseContainer.getAbsoluteLeft() + baseContainer.getOffsetWidth()/2 - popupContainer.getOffsetWidth()/2;
		mountingPointY = baseContainer.getAbsoluteTop() - popupContainer.getOffsetHeight();

		if (mountingPointX < MARGIN){
			mountingPointX = MARGIN;
		} else if (mountingPointX + popupContainer.getOffsetWidth() > Window.getClientWidth() - MARGIN){
			mountingPointX = Window.getClientWidth() - popupContainer.getOffsetWidth() - MARGIN;
		}
		if (mountingPointY + popupContainer.getOffsetHeight() < MARGIN){
			mountingPointY = MARGIN;
		} else if (mountingPointY + popupContainer.getOffsetWidth() > Window.getClientHeight() - MARGIN){
			mountingPointY = Window.getClientHeight() - popupContainer.getOffsetHeight() - MARGIN;
		}
		
		popupContainer.setPopupPosition(mountingPointX, mountingPointY);
		
	}
	
	protected void updateOptionButtonsSelection(){

		for (int i = 0 ; i < options.size() ; i ++){
			options.get(i).setSelected(i == selectedIndex);
		}
	}
	
	public void setChangeListener(ExListBoxChangeListener listener){
		this.listener = listener;
	}
	
	public int getSelectedIndex(){
		return selectedIndex;
	}
	
	public void setSelectedIndex(int index){
		selectedIndex = index;
		setSelectedBaseBody();
	}
	
	public void setEnabled(boolean enabled){
		this.enabled = enabled;
	}

}
