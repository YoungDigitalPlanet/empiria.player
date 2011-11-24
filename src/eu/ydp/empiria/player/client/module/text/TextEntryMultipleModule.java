package eu.ydp.empiria.player.client.module.text;

import java.util.Vector;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.dom.client.Document;
import com.google.gwt.json.client.JSONArray;
import com.google.gwt.json.client.JSONString;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.InlineHTML;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.xml.client.Element;
import com.google.gwt.xml.client.NodeList;

import eu.ydp.empiria.player.client.components.ElementWrapperWidget;
import eu.ydp.empiria.player.client.controller.events.internal.InternalEvent;
import eu.ydp.empiria.player.client.controller.events.internal.InternalEventTrigger;
import eu.ydp.empiria.player.client.controller.variables.objects.response.Response;
import eu.ydp.empiria.player.client.model.IModuleCreator;
import eu.ydp.empiria.player.client.model.feedback.InlineFeedback;
import eu.ydp.empiria.player.client.module.IInteractionModule;
import eu.ydp.empiria.player.client.module.IModuleEventsListener;
import eu.ydp.empiria.player.client.module.JsSocketFactory;
import eu.ydp.empiria.player.client.module.ModuleInteractionEventsListener;
import eu.ydp.empiria.player.client.module.ModuleSocket;
import eu.ydp.empiria.player.client.util.xml.XMLConverter;
import eu.ydp.empiria.player.client.util.xml.XMLUtils;

public class TextEntryMultipleModule extends Composite implements IInteractionModule {

	/** response processing interface */
	private Response response;
	/** response processing interface */
	private String responseIdentifier;
	/** module state changed listener */
	private ModuleInteractionEventsListener stateListener;
	/** list of entry boxes */
	private Vector<TextBox> textEntrys;
	/** list of entry boxes */
	private Vector<InlineHTML> textEntryWrappers;
	/** determines if the module is currently locked */
	private boolean locked = false;
	/** determines if the module is currently in ShowAnswers mode */
	private boolean showingAnswers = false;
	
	
	public TextEntryMultipleModule(Element element, ModuleSocket moduleSocket, IModuleEventsListener moduleEventsListener){
		
		responseIdentifier = XMLUtils.getAttributeAsString(element, "responseIdentifier"); 
		response = moduleSocket.getResponse(responseIdentifier);
		stateListener = moduleEventsListener;
		
		textEntrys = new Vector<TextBox>();
		textEntryWrappers = new Vector<InlineHTML>();

		Vector<String> ignoredTags = new Vector<String>();
		ignoredTags.add("feedbackInline");
		
		com.google.gwt.dom.client.Element dom = XMLConverter.getDOM(element, moduleSocket, moduleEventsListener, new IModuleCreator() {
			
			@Override
			public boolean isSupported(String name) {
				return name.equals("textEntry");
			}
			
			@Override
			public com.google.gwt.dom.client.Element createModule(Element element1,
					ModuleSocket moduleSocket,
					IModuleEventsListener moduleEventsListener) {
				TextBox tmpTB = new TextBox();
				tmpTB.setStyleName("qp-textentrymultiple-textbox");
				tmpTB.getElement().setId(Document.get().createUniqueId());
				if (element1.hasAttribute("expectedLength"))
					tmpTB.setMaxLength( Integer.parseInt(element1.getAttribute("expectedLength")) );

				NodeList childNodes = element1.getChildNodes();
				for (int f = 0 ; f < childNodes.getLength() ; f ++){
					if (childNodes.item(f).getNodeName().compareTo("feedbackInline") == 0)
						moduleSocket.add(new InlineFeedback(tmpTB, childNodes.item(f), moduleEventsListener));
				}
				
				textEntrys.add(tmpTB);
				
				InlineHTML tmpIH = new InlineHTML();
				tmpIH.setStyleName("qp-textentrymultiple-text");
				tmpIH.getElement().appendChild(tmpTB.getElement());
				
				textEntryWrappers.add(tmpIH);
				
				return tmpIH.getElement();
			}
		}, ignoredTags);
		
		initWidget(new ElementWrapperWidget(dom));
		
		setStyleName("qp-textentrymultiple");
	}

	@Override
	public void onOwnerAttached() {
		updateResponse(false);

	}

	@Override
	public String getIdentifier() {
		return responseIdentifier;
	}

	@Override
	public void lock(boolean l) {
		locked = l;
		for (TextBox tb : textEntrys){
			tb.setEnabled(!locked);
		}
	}

	@Override
	public void markAnswers(boolean mark) {
		if (mark){
			Vector<Boolean> evaluated = response.evaluateAnswer();
			for (int a = 0 ; a < evaluated.size() &&  a < textEntrys.size() ; a ++ ){
				if (evaluated.get(a)){
					textEntryWrappers.get(a).setStyleName("qp-textentrymultiple-text-correct");
				} else {
					textEntryWrappers.get(a).setStyleName("qp-textentrymultiple-text-wrong");
				}
			}
		} else {

			for (int a = 0 ; a < textEntrys.size() ; a ++ ){
				textEntryWrappers.get(a).setStyleName("qp-textentrymultiple-text");
			}
		}
	}

	@Override
	public void reset() {
		for (int t = 0 ; t < textEntrys.size() ; t ++){
			textEntrys.get(t).setText("");
			textEntryWrappers.get(t).setStyleName("qp-textentrymultiple-text");
		}
		updateResponse(false);
	}

	@Override
	public void showCorrectAnswers(boolean show) {
		if (show  &&  !showingAnswers){
			
			showingAnswers = true;

			for (int c = 0 ; c < response.correctAnswers.size() ; c ++){
				textEntrys.get(c).setText(response.correctAnswers.get(c));
			}
		} else if (!show  &&  showingAnswers){

			for (int c = 0 ; c < response.correctAnswers.size() ; c ++){
				textEntrys.get(c).setText(response.values.get(c));
			}
			
			showingAnswers = false;
		}

	}
		
	public JavaScriptObject getJsSocket(){
		return JsSocketFactory.createSocketObject(this);
	}

	@Override
	public JSONArray getState() {
		JSONArray newState = new JSONArray(); 
		for (TextBox tb : textEntrys){
			newState.set(newState.size(), new JSONString(tb.getText()));
		}
		return newState;
	}

	@Override
	public void setState(JSONArray newState) {
		for (int s = 0 ; s < newState.size() ; s ++){
			textEntrys.get(s).setText( newState.get(s).isString().stringValue() );
		}
		updateResponse(false);
	}

	@Override
	public Vector<InternalEventTrigger> getTriggers() {
		Vector<InternalEventTrigger> v = new Vector<InternalEventTrigger>();
		for (TextBox tb : textEntrys){
			v.add(new InternalEventTrigger(tb.getElement().getId(), Event.ONCHANGE));
		}
		return v;
	}

	@Override
	public void handleEvent(String tagID, InternalEvent event) {
		if (locked)
			return;
		updateResponse(true);
	}

	
	private void updateResponse(boolean userInteract){
		if (showingAnswers)
			return;
		
		Vector<String> currResponseValues = new Vector<String>();
		for (TextBox tb : textEntrys){
			currResponseValues.add(tb.getText());
		}
		
		if (!response.compare(currResponseValues)  ||  !response.isInitialized()){
			response.set(currResponseValues);
			stateListener.onStateChanged(userInteract, this);
		}
	}
}
