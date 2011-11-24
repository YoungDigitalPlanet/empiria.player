package eu.ydp.empiria.player.client.view.item;

import com.google.gwt.dom.client.Element;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.ui.Widget;

import eu.ydp.empiria.player.client.controller.events.internal.InternalEvent;
import eu.ydp.empiria.player.client.controller.events.internal.InternalEventsListener;
import eu.ydp.empiria.player.client.controller.events.widgets.WidgetWorkflowListener;

public class ItemBodyView extends Widget {

	protected InternalEventsListener internalEventsListener;
	protected WidgetWorkflowListener widgetWorkflowListener;
	
	public ItemBodyView(Element element, InternalEventsListener iel, WidgetWorkflowListener wwl){
		internalEventsListener = iel;
		widgetWorkflowListener = wwl;
		
		setElement(element);
		
		setStyleName("qp-text-module");
		this.sinkEvents(Event.ONCHANGE);
		this.sinkEvents(Event.ONMOUSEDOWN);
		this.sinkEvents(Event.ONMOUSEUP);
		this.sinkEvents(Event.ONMOUSEMOVE);
		this.sinkEvents(Event.ONMOUSEOUT);
	}

	/**
	 * Catch inner controls events.
	 * Since events are not fired for internal Widgets. All events should be handled in this function
	 */
	public void onBrowserEvent(Event event){
		super.onBrowserEvent(event);

		internalEventsListener.onInternalEvent(new InternalEvent(event));

	}
	
	@Override
	public void onLoad(){
		super.onLoad();
		widgetWorkflowListener.onLoad();
	}
}
