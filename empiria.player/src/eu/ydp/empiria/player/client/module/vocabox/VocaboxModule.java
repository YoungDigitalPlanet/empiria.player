package eu.ydp.empiria.player.client.module.vocabox;

import java.util.Vector;

import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.InlineLabel;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.xml.client.Element;
import com.google.gwt.xml.client.NodeList;

import eu.ydp.empiria.player.client.controller.events.internal.InternalEvent;
import eu.ydp.empiria.player.client.controller.events.internal.InternalEventTrigger;
import eu.ydp.empiria.player.client.module.IBrowserEventHandler;

public class VocaboxModule extends Composite implements IBrowserEventHandler {

	private FlowPanel containerPanel;
	private FlowPanel contentsPanel;
	private Vector<VocaItem> items;
	
	public VocaboxModule(Element element){
		
		items = new Vector<VocaItem>();
		
		containerPanel = new FlowPanel();
		containerPanel.setStyleName("qp-vocabox-container");
		
		contentsPanel = new FlowPanel();
		contentsPanel.setStyleName("qp-vocabox-contents");
		containerPanel.add(contentsPanel);
		
		initItems(contentsPanel, element.getElementsByTagName("vocaItem"));
		
		initWidget(containerPanel);
	}
	
	private void initItems(Panel parent, NodeList itemNodes){
		for (int i = 0 ; i < itemNodes.getLength() ; i ++){
			VocaItem tmpItem = new VocaItem(((Element)itemNodes.item(i)));
			items.add(tmpItem);
			contentsPanel.add(tmpItem);
			contentsPanel.add(new InlineLabel(" "));
		}
	}

	@Override
	public Vector<InternalEventTrigger> getTriggers() {
		
		Vector<InternalEventTrigger> triggers = new Vector<InternalEventTrigger>();
		
		for (VocaItem vi : items){
			triggers.add(new InternalEventTrigger(vi.getId(), Event.ONMOUSEUP));
		}
		
		return triggers;
	}

	@Override
	public void handleEvent(String tagID, InternalEvent event) {

		for (VocaItem vi : items){
			if (vi.getId().equals(tagID)){
				vi.play();
				break;
			}
		}
	}
}
