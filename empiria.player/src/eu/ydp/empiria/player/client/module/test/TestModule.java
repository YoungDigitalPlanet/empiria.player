package eu.ydp.empiria.player.client.module.test;

import java.util.HashMap;
import java.util.Vector;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.dom.client.Document;
import com.google.gwt.json.client.JSONArray;
import com.google.gwt.json.client.JSONParser;
import com.google.gwt.json.client.JSONString;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.xml.client.Element;

import eu.ydp.empiria.player.client.controller.events.internal.InternalEvent;
import eu.ydp.empiria.player.client.controller.events.internal.InternalEventTrigger;
import eu.ydp.empiria.player.client.module.IInteractionModule;
import eu.ydp.empiria.player.client.module.ModuleInteractionEventsListener;
import eu.ydp.empiria.player.client.module.ModuleSocket;
import eu.ydp.empiria.player.client.module.order.dndcomponent.DragContainerPanel;
import eu.ydp.empiria.player.client.module.order.dndcomponent.DragMode;

public class TestModule extends Composite implements IInteractionModule {

	public TestModule(Element element, ModuleSocket moduleSocket, ModuleInteractionEventsListener stateChangedListener){
		
		String dm = element.getAttribute("dragMode");
		
		
		widgets = new Vector<Widget>();
		
		panel = new DragContainerPanel();
		//panel.setSize("200", "200");
		panel.setStylePrimaryName("qp-test-container");
		panel.getElement().setId(Document.get().createUniqueId());
		if (dm.toLowerCase().compareTo("vertical") == 0){
			panel.setDragMode(DragMode.VERTICAL);
		} else if (dm.toLowerCase().compareTo("horizontal") == 0){
			panel.setDragMode(DragMode.HORIZONTAL);
		} else if (dm.toLowerCase().compareTo("free") == 0){
			panel.setDragMode(DragMode.FREE);
		}


		child0 = new SimplePanel();
		child0.setStylePrimaryName("qp-test-element");
		child0.getElement().setId(Document.get().createUniqueId());
		child1 = new SimplePanel();
		child1.setStylePrimaryName("qp-test-element");
		child1.getElement().setId(Document.get().createUniqueId());
		child2 = new SimplePanel();
		child2.setStylePrimaryName("qp-test-element");
		child2.getElement().setId(Document.get().createUniqueId());
	
		label = new Label();
		label.setText("info");
		
		vp = new VerticalPanel();
		vp.add(panel);
		vp.add(label);
	
		initWidget(vp);
	}
	
	private HashMap<String, Integer> tagIdMap;
	private Vector<Widget> widgets;

	
	private DragContainerPanel panel;
	private SimplePanel child1;
	private SimplePanel child2;
	private SimplePanel child0;
	private VerticalPanel vp;
	private Label label;

	@Override
	public void onOwnerAttached() {	
		

		Vector<String> v1 = new Vector<String>();
		v1.add("asd");
		v1.add("qwe");
		
		Vector<String> v2 = new Vector<String>();
		v2.add("][p");
		v2.add("132");
		
		JSONArray ar1 = new JSONArray();
		ar1.set(0, new JSONString(v1.get(0)));
		ar1.set(1, new JSONString(v1.get(1)));
		
		JSONArray ar2 = new JSONArray();
		ar2.set(0, new JSONString(v2.get(0)));
		ar2.set(1, new JSONString(v2.get(1)));
		
		JSONArray ar = new JSONArray();
		ar.set(0, ar1);
		ar.set(1, ar2);
		
		String s = ar.toString();
		
		JSONArray aro  = (JSONArray) JSONParser.parse(s);
		
		JSONArray aro1 = (JSONArray) aro.get(1);
		JSONString vo1s =  (JSONString) aro1.get(1);
		String vo1 = vo1s.stringValue();
		vo1 += "";
		
		widgets.add(child0);
		widgets.add(child1);
		widgets.add(child2);
		
		tagIdMap.put(child0.getElement().getId(), 0);
		tagIdMap.put(child1.getElement().getId(), 1);
		tagIdMap.put(child2.getElement().getId(), 2);
		
		panel.add(child0);
		panel.add(child1);
		panel.add(child2);
		
		panel.setAutoSize();
		
	}
	
	@Override
	public void markAnswers(boolean mark) {
		// TODO Auto-generated method stub

	}	

	@Override
	public void reset() {
		// TODO Auto-generated method stub

	}

	@Override
	public void showCorrectAnswers(boolean show) {
		// TODO Auto-generated method stub

	}
		
	public JavaScriptObject getJsSocket(){
		return createJsSocket();
	}
	
	private native JavaScriptObject createJsSocket()/*-{
		var jso = {};
		return jso;
	}-*/;

	@Override
	public JSONArray getState() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setState(JSONArray newState) {
		// TODO Auto-generated method stub

	}

	@Override
	public Vector<InternalEventTrigger> getTriggers() {
		Vector<InternalEventTrigger> t = new Vector<InternalEventTrigger>(0);
		t.add(new InternalEventTrigger(child1.getElement().getId(), Event.ONMOUSEDOWN));
		t.add(new InternalEventTrigger(child1.getElement().getId(), Event.ONMOUSEUP));
		t.add(new InternalEventTrigger(child1.getElement().getId(), Event.ONMOUSEMOVE));
		t.add(new InternalEventTrigger(child2.getElement().getId(), Event.ONMOUSEDOWN));
		t.add(new InternalEventTrigger(child2.getElement().getId(), Event.ONMOUSEUP));
		t.add(new InternalEventTrigger(child2.getElement().getId(), Event.ONMOUSEMOVE));
		t.add(new InternalEventTrigger(child0.getElement().getId(), Event.ONMOUSEDOWN));
		t.add(new InternalEventTrigger(child0.getElement().getId(), Event.ONMOUSEUP));
		t.add(new InternalEventTrigger(child0.getElement().getId(), Event.ONMOUSEMOVE));
		t.add(new InternalEventTrigger(panel.getElement().getId(), Event.ONMOUSEUP));
		t.add(new InternalEventTrigger(panel.getElement().getId(), Event.ONMOUSEMOVE));
		//t.add(new InternalEventTrigger(panel.getElement().getId(), Event.ONMOUSEOUT));
		//t.add(new InternalEventTrigger(panel.getElement().getId(), Event.ONMOUSEOVER));
		return t;
	}

	@Override
	public void handleEvent(String tagId, InternalEvent event) {
		if (event.getTypeInt() == Event.ONMOUSEDOWN){
			int currWidgetIndex = tagIdMap.get(tagId);
			panel.startDrag(currWidgetIndex, event.getClientX(), event.getClientY());
		} else if (event.getTypeInt() == Event.ONMOUSEMOVE){
			label.setText("mouse: x="+event.getClientX() + " y=" + event.getClientY());
			panel.drag(event.getClientX(), event.getClientY());
		} else if (event.getTypeInt() == Event.ONMOUSEUP || event.getTypeInt() == Event.ONMOUSEOUT  || event.getTypeInt() == Event.ONMOUSEOVER){
			panel.stopDrag();
		}
			
		

	}

	@Override
	public void lock(boolean l) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String getIdentifier() {
		// TODO Auto-generated method stub
		return null;
	}

}
