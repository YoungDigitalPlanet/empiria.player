package eu.ydp.empiria.player.client.module.math;

import java.util.List;
import java.util.Map;
import java.util.Vector;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.json.client.JSONArray;
import com.google.gwt.json.client.JSONString;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HasWidgets;
import com.google.gwt.xml.client.Element;
import com.mathplayer.player.MathPlayerManager;
import com.mathplayer.player.geom.Font;
import com.mathplayer.player.interaction.InteractionManager;

import eu.ydp.empiria.player.client.controller.variables.objects.response.Response;
import eu.ydp.empiria.player.client.module.IInteractionModule;
import eu.ydp.empiria.player.client.module.ModuleJsSocketFactory;
import eu.ydp.empiria.player.client.module.ModuleSocket;
import eu.ydp.empiria.player.client.module.listener.ModuleInteractionListener;
import eu.ydp.empiria.player.client.util.IntegerUtils;
import eu.ydp.empiria.player.client.util.xml.XMLUtils;

public class MathModule implements IInteractionModule {

	protected ModuleSocket moduleSocket;
	protected ModuleInteractionListener moduleInteractionListener;
	
	protected Element element;
	protected FlowPanel mainPanel;
	protected InteractionManager interactionManager;
	
	protected String responseIdentifier;
	protected Response response;
	
	protected boolean showingAnswer = false;
	protected boolean markingAnswer = false;
	protected boolean locked = false;

	@Override
	public void initModule(ModuleSocket moduleSocket, ModuleInteractionListener moduleInteractionListener) {
		this.moduleSocket = moduleSocket;
		this.moduleInteractionListener = moduleInteractionListener;
	}

	@Override
	public void addElement(Element element) {
		this.element = element;
	}

	@Override
	public void installViews(List<HasWidgets> placeholders) {

		responseIdentifier = XMLUtils.getAttributeAsString(element, "responseIdentifier");
		response = moduleSocket.getResponse(responseIdentifier);
		mainPanel = new FlowPanel();
		mainPanel.setStyleName("qp-mathinteraction");
		placeholders.get(0).add(mainPanel);
	}

	@Override
	public void onBodyLoad() {		
		MathPlayerManager mpm = new MathPlayerManager();
		Map<String, String> styles = moduleSocket.getStyles(element);
		int fontSize = 16;
		String fontName = "Arial";
		boolean fontBold = false;
		boolean fontItalic = false;
		Integer gapWidth = null;
		Integer gapHeight = null;
		if (styles.containsKey("-empiria-math-font-size")){			
			fontSize = IntegerUtils.tryParseInt(styles.get("-empiria-math-font-size"));
		}
		if (styles.containsKey("-empiria-math-font-family")){			
			fontName = styles.get("-empiria-math-font-family");
		}
		if (styles.containsKey("-empiria-math-font-weight")){			
			fontBold = styles.get("-empiria-math-font-weight").toLowerCase().equals("bold");
		}
		if (styles.containsKey("-empiria-math-font-style")){			
			fontItalic = styles.get("-empiria-math-font-style").toLowerCase().equals("italic");
		}
		if (styles.containsKey("-empiria-math-gap-width")){			
			gapWidth = IntegerUtils.tryParseInt(styles.get("-empiria-math-gap-width"));
		}
		if (styles.containsKey("-empiria-math-gap-height")){			
			gapHeight = IntegerUtils.tryParseInt(styles.get("-empiria-math-gap-height"));
		}
		Font f = new Font(fontSize, fontName, fontBold, fontItalic);
		mpm.setFont(f);
		if (gapWidth != null  &&  gapWidth > 0)
			mpm.setGapWidth(gapWidth);
		if (gapHeight != null  &&  gapHeight > 0)
			mpm.setGapHeight(gapHeight);
		interactionManager = mpm.createMath(element.getChildNodes().toString(), mainPanel);
		
		
		updateResponse(false);

		for (int i = 0 ; i < interactionManager.getGapsCount() ; i ++){
			interactionManager.getGapAt(i).addChangeHandler(new ChangeHandler() {
				
				@Override
				public void onChange(ChangeEvent arg0) {
					updateResponse(true);
				}
			});
		}
	}

	@Override
	public void onBodyUnload() {
	}
	
	@Override
	public void markAnswers(boolean mark) {
		if (mark  && !markingAnswer){
			Vector<Boolean> evaluation = response.evaluateAnswer();
			
			for (int i = 0 ; i < evaluation.size()  &&  i < interactionManager.getGapsCount() ; i ++){
				if ("".equals( response.values.get(i)) ){
					interactionManager.markGap(i, false, false);
				} else if (evaluation.get(i)){
					interactionManager.markGap(i, true, false);
				} else {
					interactionManager.markGap(i,  false, true);
				}
			}
			
		} else  if (!mark && markingAnswer){
			for (int i = 0 ; i < interactionManager.getGapsCount() ; i ++){
				interactionManager.unmarkGap(i);				
			}
		}
		markingAnswer = mark;
	}

	@Override
	public void showCorrectAnswers(boolean show) {
		if (show  &&  !showingAnswer){
			for (int i = 0 ; i < interactionManager.getGapsCount() && i < response.correctAnswers.size() ; i ++){
				interactionManager.getGapAt(i).setText( response.correctAnswers.get(i) );
			}			
		} else if (!show  &&  showingAnswer){
			for (int i = 0 ; i < interactionManager.getGapsCount() && i < response.values.size() ; i ++){
				interactionManager.getGapAt(i).setText( response.values.get(i) );
			}						
		}
		showingAnswer = show;
	}

	@Override
	public void lock(boolean lk) {
		locked = lk;
		for (int i = 0 ; i < interactionManager.getGapsCount() ; i ++){
			interactionManager.getGapAt(i).setEnabled(!lk);
		}
	}

	@Override
	public void reset() {
		for (int i = 0 ; i < interactionManager.getGapsCount() ; i ++){
			interactionManager.getGapAt(i).setText("");
		}
		updateResponse(false);
	}

	@Override
	public JSONArray getState() {
		JSONArray arr = new JSONArray();
		for (int i = 0 ; i < response.values.size() ; i ++){
			JSONString val = new JSONString(response.values.get(i));
			arr.set(i,  val);
		}
		return arr;
	}

	@Override
	public void setState(JSONArray newState) {
		if (newState.isArray() != null){
			for (int i = 0 ; i < interactionManager.getGapsCount() ; i ++){
				interactionManager.getGapAt(i).setText(newState.get(i).isString().stringValue());
			}
		}
		updateResponse(false);
	}

	@Override
	public JavaScriptObject getJsSocket() {
		return ModuleJsSocketFactory.createSocketObject(this);
	}

	@Override
	public String getIdentifier() {
		return responseIdentifier;
	}
	

	private void updateResponse(boolean userInteract){
		if (showingAnswer)
			return;
		
		response.values.clear();
		for (int i = 0 ; i < interactionManager.getGapsCount() ; i ++){
			response.values.add( interactionManager.getGapAt(i).getText() );
		}
		moduleInteractionListener.onStateChanged(userInteract, this);
	}

}
