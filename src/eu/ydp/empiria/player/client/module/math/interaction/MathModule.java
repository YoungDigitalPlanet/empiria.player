package eu.ydp.empiria.player.client.module.math.interaction;

import java.util.Vector;

import pl.smath.expression.model.Term;
import pl.smath.expression.parser.ExpressionParser;
import pl.smath.expression.parser.ExpressionParserException;
import pl.smath.renderer.renderer.TermRendererException;
import pl.smath.renderer.renderer.TermWidgetFactory;
import pl.smath.renderer.utils.InteractionManager;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.dom.client.Document;
import com.google.gwt.json.client.JSONArray;
import com.google.gwt.json.client.JSONString;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.xml.client.Element;
import com.google.gwt.xml.client.Node;

import eu.ydp.empiria.player.client.controller.events.internal.InternalEvent;
import eu.ydp.empiria.player.client.controller.events.internal.InternalEventTrigger;
import eu.ydp.empiria.player.client.controller.variables.objects.response.Response;
import eu.ydp.empiria.player.client.module.IInteractionModule;
import eu.ydp.empiria.player.client.module.ModuleEventsListener;
import eu.ydp.empiria.player.client.module.JsSocketFactory;
import eu.ydp.empiria.player.client.module.ModuleSocket;
import eu.ydp.empiria.player.client.util.xml.XMLUtils;

public class MathModule extends Widget implements IInteractionModule {

	public MathModule(Element element, ModuleSocket moduleSocket, ModuleEventsListener moduleEventsListener){

		responseIdentifier = XMLUtils.getAttributeAsString(element, "responseIdentifier");
		response = moduleSocket.getResponse(responseIdentifier);
		this.moduleEventsListener = moduleEventsListener;

		term = null;
		container = new FlowPanel();
		container.setStyleName("qp-math-inline");
		try {
			for (int n = 0 ; n < element.getChildNodes().getLength() ; n ++){
				if (element.getChildNodes().item(n).getNodeType() == Node.ELEMENT_NODE){
					term = (new ExpressionParser()).processMathML((Element)element.getChildNodes().item(n));
					break;
				}
			}
			int fromIndex = 0;
			while ((fromIndex = element.toString().indexOf("<gap/>", fromIndex+1)) != -1){
				gapsCount++;
			}
			
			gapsIds = new Vector<String>();
			for (int g = 0 ; g < gapsCount ; g ++)
				gapsIds.add(Document.get().createUniqueId());
		} catch (ExpressionParserException e1) {
		}
		
		setElement(container.getElement());
	}
	
	private ModuleEventsListener moduleEventsListener;
	/** response identifier */
	private String responseIdentifier;
	/** response processing interface */
	private Response response;

	private Panel container;
	private Term term;
	private InteractionManager interactionManager;
	private boolean showingAnswers = false;
	

	private int gapsCount;
	private Vector<String> gapsIds;
	
	@Override
	public void onOwnerAttached() {

		interactionManager = new InteractionManager(container);
		try {
			if (term != null){
				TermWidgetFactory twf = new TermWidgetFactory();
				twf.setFontHeight("22px");
				twf.createWidget(term, interactionManager);
			}
		} catch (TermRendererException e) {
		}
		
		interactionManager.process();

		for (int i = 0 ; i < interactionManager.getGapsCount() ; i ++){
			interactionManager.getGapAt(i).getElement().setId(gapsIds.get(i));
		}
		updateResponse(false);
	}
	
	@Override
	public String getIdentifier() {
		return responseIdentifier;
	}

	@Override
	public void lock(boolean l) {
		for (int i = 0 ; i < interactionManager.getGapsCount() ; i ++){
			interactionManager.getGapAt(i).setEnabled(!l);
		}
	}

	@Override
	public void markAnswers(boolean mark) {

		if (mark){
			
			Vector<Boolean> evaluation = response.evaluateAnswer();
			
			for (int e = 0 ; e < evaluation.size() ; e ++ ){
				if (evaluation.get(e))
					interactionManager.getGapAt(e).setStyleName("smath-gap-correct");
				else
					interactionManager.getGapAt(e).setStyleName("smath-gap-wrong");
			}
		} else {
			for (int i = 0 ; i < interactionManager.getGapsCount() ; i ++){			
				interactionManager.getGapAt(i).setStyleName("smath-gap");
			}
		}
	}

	@Override
	public void reset() {
		for (int i = 0 ; i < interactionManager.getGapsCount() ; i ++){
			interactionManager.getGapAt(i).setText("");
		}
	}

	@Override
	public void showCorrectAnswers(boolean show) {
		if (show  &&  !showingAnswers){
			showingAnswers = true;
		} else if (!show  &&  showingAnswers){
			showingAnswers = false;
		}

	}
		
	public JavaScriptObject getJsSocket(){
		return JsSocketFactory.createSocketObject(this);
	}

	@Override
	public JSONArray getState() {
		JSONArray newState = new JSONArray();
		
		for (int i = 0 ; i < interactionManager.getGapsCount() ; i ++){
			newState.set(i, new JSONString( interactionManager.getGapAt(i).getText() ) );
		}
		return newState;
	}

	@Override
	public void setState(JSONArray newState) {
		for (int i = 0 ; i < interactionManager.getGapsCount() ; i ++){
			interactionManager.getGapAt(i).setText(newState.get(i).isString().stringValue());
		}
		updateResponse(false);
	}

	@Override
	public Vector<InternalEventTrigger> getTriggers() {

		Vector<InternalEventTrigger> ids = new Vector<InternalEventTrigger>();

		for (int i = 0 ; i < gapsCount ; i ++){
			ids.add(new InternalEventTrigger(gapsIds.get(i), Event.ONCHANGE));
		}
		return ids;
	}

	@Override
	public void handleEvent(String tagID, InternalEvent event) {
		updateResponse(true);

	}

	
	private void updateResponse(boolean userInteract){
		if (showingAnswers)
			return;
		

		Vector<String> currResponseValues = new Vector<String>();
		
		for (int i = 0 ; i < interactionManager.getGapsCount() ; i ++){
			currResponseValues.add(interactionManager.getGapAt(i).getText());
		}

		if (!response.compare(currResponseValues)){
			response.set(currResponseValues);
			moduleEventsListener.onStateChanged(userInteract, this);
		}
	
	}


}
