package eu.ydp.empiria.player.client.module.match;

import java.util.Vector;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.dom.client.Document;
import com.google.gwt.json.client.JSONArray;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.xml.client.Element;

import eu.ydp.empiria.player.client.controller.events.internal.InternalEvent;
import eu.ydp.empiria.player.client.controller.events.internal.InternalEventTrigger;
import eu.ydp.empiria.player.client.controller.variables.objects.response.Response;
import eu.ydp.empiria.player.client.module.CommonsFactory;
import eu.ydp.empiria.player.client.module.IInteractionModule;
import eu.ydp.empiria.player.client.module.IModuleEventsListener;
import eu.ydp.empiria.player.client.module.JsSocketFactory;
import eu.ydp.empiria.player.client.module.ModuleSocket;
import eu.ydp.empiria.player.client.module.match.area.MatchContainer;
import eu.ydp.empiria.player.client.util.xml.XMLUtils;

public class MatchModule extends Composite implements IInteractionModule {

	/** response id */
	//private String responseIdentifier;
	/** The main panel for the module */
	private VerticalPanel mainPanel;
	/** The main panel for the module */
	private MatchContainer container;
	private String containerId;
	private String responseIdentifier;
	
	private boolean locked = false;
	
	public MatchModule(Element element, ModuleSocket moduleSocket, IModuleEventsListener moduleEventsListener){

		/** Shuffle? */
		boolean shuffle = XMLUtils.getAttributeAsBoolean(element, "shuffle");
		int maxAssociations = XMLUtils.getAttributeAsInt(element, "maxAssociations");

		responseIdentifier = XMLUtils.getAttributeAsString(element, "responseIdentifier");
		Response response = moduleSocket.getResponse(responseIdentifier);
				
		container = new MatchContainer(element, shuffle, maxAssociations, moduleSocket, response, moduleEventsListener, this);
		containerId = Document.get().createUniqueId();
		container.getElement().setId(containerId);
		container.setStylePrimaryName("qp-match-container");
		
		mainPanel = new VerticalPanel();
		mainPanel.add(CommonsFactory.getPromptView(XMLUtils.getFirstElementWithTagName(element, "prompt")));
		mainPanel.add(container);
		mainPanel.setStylePrimaryName("qp-match-module");

		initWidget(mainPanel);
	}

	@Override
	public void onOwnerAttached() {
		container.init();
		container.onOwnerAttached();
	}

	@Override
	public void markAnswers(boolean mark) {
		if (mark){
			locked = true;
			container.markAnswers();
		} else {
			container.unmark();
			locked = false;
		}

	}

	@Override
	public void showCorrectAnswers(boolean show) {
		container.showCorrectAnswers(show);

	}

	@Override
	public void reset() {
		container.reset();

	}

	@Override
	public void lock(boolean l) {
		locked = l;
		
	}
		
	public JavaScriptObject getJsSocket(){
		return JsSocketFactory.createSocketObject(this);
	}

	@Override
	public JSONArray getState() {
		return container.getState();
	}

	@Override
	public void setState(JSONArray newState) {
		container.setState(newState);
	}

	@Override
	public Vector<InternalEventTrigger> getTriggers() {

		Vector<InternalEventTrigger> ids = new Vector<InternalEventTrigger>();

		ids.add(new InternalEventTrigger(container.area.areaCoverId, Event.ONMOUSEDOWN));
		ids.add(new InternalEventTrigger(container.area.areaCoverId, Event.ONMOUSEMOVE));
		ids.add(new InternalEventTrigger(container.area.areaCoverId, Event.ONMOUSEUP));


		ids.add(new InternalEventTrigger(container.area.landId, Event.ONMOUSEDOWN));
		ids.add(new InternalEventTrigger(container.area.landId, Event.ONMOUSEMOVE));
		ids.add(new InternalEventTrigger(container.area.landId, Event.ONMOUSEUP));
		
		ids.add(new InternalEventTrigger(containerId, Event.ONMOUSEOUT));
		
		return ids;
	}

	@Override
	public void handleEvent(String tagID, InternalEvent event) {
		if (locked)
			return;
		
		if (event.getTypeInt() == Event.ONMOUSEDOWN){
			container.startDrag(tagID, event.getClientX() + Window.getScrollLeft(), event.getClientY() + Window.getScrollTop());
		} else if (event.getTypeInt() == Event.ONMOUSEUP){
			container.endDrag(tagID, event.getClientX() + Window.getScrollLeft(), event.getClientY() + Window.getScrollTop());
		} else if (event.getTypeInt() == Event.ONMOUSEMOVE){
			container.processDrag(event.getClientX() + Window.getScrollLeft(), event.getClientY() + Window.getScrollTop());
			event.stopPropagation();
		}  else if (event.getTypeInt() == Event.ONMOUSEOUT){
			container.endDrag("", -1, -1);
			
		}  

	}

	@Override
	public String getIdentifier() {
		return responseIdentifier;
	}
}
