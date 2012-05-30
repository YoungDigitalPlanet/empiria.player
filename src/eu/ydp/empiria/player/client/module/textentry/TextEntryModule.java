package eu.ydp.empiria.player.client.module.textentry;

import java.io.Serializable;
import java.util.List;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.json.client.JSONArray;
import com.google.gwt.json.client.JSONString;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HasWidgets;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.xml.client.Element;
import com.google.gwt.xml.client.NodeList;

import eu.ydp.empiria.player.client.controller.events.interaction.InteractionEventsListener;
import eu.ydp.empiria.player.client.controller.events.interaction.StateChangedInteractionEvent;
import eu.ydp.empiria.player.client.controller.feedback.InlineFeedback;
import eu.ydp.empiria.player.client.controller.variables.objects.response.Response;
import eu.ydp.empiria.player.client.module.Factory;
import eu.ydp.empiria.player.client.module.IActivity;
import eu.ydp.empiria.player.client.module.IStateful;
import eu.ydp.empiria.player.client.module.ModuleJsSocketFactory;
import eu.ydp.empiria.player.client.module.ModuleSocket;
import eu.ydp.empiria.player.client.module.OneViewInteractionModuleBase;
import eu.ydp.empiria.player.client.util.xml.XMLUtils;

public class TextEntryModule extends OneViewInteractionModuleBase implements Factory<TextEntryModule>{

	/** text box control */
	private TextBox textBox;
	/** Last selected value */
	private String	lastValue = null;
	private boolean showingAnswers = false;

	protected Panel moduleWidget;

	/**
	 * constructor
	 * @param moduleSocket
	 */
	public TextEntryModule(){

	}
	@Override
	public void installViews(List<HasWidgets> placeholders) {

		textBox = new TextBox();
		if (getModuleElement().hasAttribute("expectedLength"))
			textBox.setMaxLength(XMLUtils.getAttributeAsInt(getModuleElement(), "expectedLength"));

		textBox.addChangeHandler(new ChangeHandler() {
			@Override
			public void onChange(ChangeEvent event) {
				onTextBoxChange();
			}
		});

		
		if (!getResponse().correctAnswers.get(0).matches(".*[^0-9].*"))
			textBox.getElement().setAttribute("type", "number");

		Panel spanPrefix = new FlowPanel();
		spanPrefix.setStyleName("qp-text-textentry-prefix");
		Panel spanSufix = new FlowPanel();
		spanSufix.setStyleName("qp-text-textentry-sufix");
		Panel spanContent = new FlowPanel();
		spanContent.setStyleName("qp-text-textentry-content");
		spanContent.add(textBox);

		moduleWidget = new FlowPanel();

		moduleWidget.add(spanPrefix);
		moduleWidget.add(spanContent);
		moduleWidget.add(spanSufix);
		moduleWidget.setStyleName("qp-text-textentry");
		applyIdAndClassToView(moduleWidget);

		placeholders.get(0).add(moduleWidget);

		NodeList inlineFeedbackNodes = getModuleElement().getElementsByTagName("feedbackInline");
		for (int f = 0 ; f < inlineFeedbackNodes.getLength() ; f ++){
			getModuleSocket().addInlineFeedback(new InlineFeedback(moduleWidget, inlineFeedbackNodes.item(f), getModuleSocket(), getInteractionEventsListener()));
		}
	}

	// ------------------------ INTERFACES ------------------------


	@Override
	public void onBodyLoad() {
	}

	@Override
	public void onBodyUnload() {

	}

	@Override
	public void onSetUp() {
		updateResponse(false);
	}

	@Override
	public void onStart() {

	}

	@Override
	public void onClose() {
	}

	@Override
	public void lock(boolean l) {
		textBox.setEnabled(!l);
	}

	/**
	 * @see IActivity#markAnswers()
	 */
	public void markAnswers(boolean mark) {
		if (mark){
			textBox.setEnabled(false);
			if (textBox.getText().length() > 0){
				if( getResponse().isCorrectAnswer(lastValue) )
					moduleWidget.setStyleName("qp-text-textentry-correct");
				else
					moduleWidget.setStyleName("qp-text-textentry-wrong");
			} else {
				moduleWidget.setStyleName("qp-text-textentry-none");
			}
		} else {
			textBox.setEnabled(true);
			moduleWidget.setStyleName("qp-text-textentry");
		}
	}

	/**
	 * @see IActivity#reset()
	 */
	public void reset() {
		markAnswers(false);
		showCorrectAnswers(false);
		lock(false);
		textBox.setText("");
		updateResponse(false);
	}

	/**
	 * @see IActivity#showCorrectAnswers()
	 */
	public void showCorrectAnswers(boolean show) {
		if (show  &&  !showingAnswers){
			showingAnswers = true;
			textBox.setText(getResponse().correctAnswers.get(0));
		} else if (!show  &&  showingAnswers) {
			textBox.setText((getResponse().values.size()>0) ? getResponse().values.get(0) : "");
			showingAnswers = false;
		}
	}

	public JavaScriptObject getJsSocket(){
		return ModuleJsSocketFactory.createSocketObject(this);
	}

  /**
   * @see IStateful#getState()
   */
  public JSONArray getState() {
	  JSONArray jsonArr = new JSONArray();

	  String stateString = "";

	  if (getResponse().values.size() > 0)
		  stateString = getResponse().values.get(0);

	  jsonArr.set(0, new JSONString(stateString));

	  return jsonArr;
  }

  /**
   * @see IStateful#setState(Serializable)
   */
  public void setState(JSONArray newState) {

		String state = "";

		if (newState == null){
		} else if (newState.size() == 0){
		} else if (newState.get(0).isString() == null){
		} else {
			state = newState.get(0).isString().stringValue();
			lastValue = null;
		}

		textBox.setText(state);

		updateResponse(false);

  }

	private void updateResponse(boolean userInteract){
		if (showingAnswers)
			return;

		if(lastValue != null)
			getResponse().remove(lastValue);

		lastValue = textBox.getText();
		getResponse().add(lastValue);
		getInteractionEventsListener().onStateChanged(new StateChangedInteractionEvent(userInteract, this));

	}

	protected void onTextBoxChange(){
		updateResponse(true);
	}

	@Override
	public TextEntryModule getNewInstance() {
		return new TextEntryModule();
	}
}
