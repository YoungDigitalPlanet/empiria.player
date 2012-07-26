package eu.ydp.empiria.player.client.module.math;

import java.util.List;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.json.client.JSONArray;
import com.google.gwt.json.client.JSONString;
import com.google.gwt.user.client.ui.AbsolutePanel;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HasWidgets;

import eu.ydp.empiria.player.client.PlayerGinjector;
import eu.ydp.empiria.player.client.components.ExListBoxChangeListener;
import eu.ydp.empiria.player.client.module.Factory;
import eu.ydp.empiria.player.client.module.ModuleJsSocketFactory;
import eu.ydp.empiria.player.client.module.OneViewInteractionModuleBase;
import eu.ydp.empiria.player.client.module.binding.BindingGroupIdentifier;
import eu.ydp.empiria.player.client.module.binding.DefaultBindingGroupIdentifier;
import eu.ydp.empiria.player.client.util.events.bus.EventsBus;
import eu.ydp.empiria.player.client.util.events.player.PlayerEvent;
import eu.ydp.empiria.player.client.util.events.player.PlayerEventHandler;
import eu.ydp.empiria.player.client.util.events.player.PlayerEventTypes;

public class MathModule extends OneViewInteractionModuleBase implements Factory<MathModule> {

	protected AbsolutePanel outerPanel;
	protected FlowPanel mainPanel;
	protected AbsolutePanel listBoxesLayer;
	private EventsBus eventsBus = PlayerGinjector.INSTANCE.getEventsBus();

	protected List<MathGap> gaps;
	protected BindingGroupIdentifier widthBindingIdentifier;

	protected boolean showingAnswer = false;
	protected boolean markingAnswer = false;
	protected boolean locked = false;

	private MathModuleHelper helper;
	
	public MathModule(){
		super();
		eventsBus.addHandler(PlayerEvent.getType(PlayerEventTypes.BEFORE_FLOW), new PlayerEventHandler() {
			
			@Override
			public void onPlayerEvent(PlayerEvent event) {
				if(event.getType()==PlayerEventTypes.BEFORE_FLOW){
					updateResponse(false);
				}
			}
		});
	}

	@Override
	public void installViews(List<HasWidgets> placeholders) {
		
		outerPanel = new AbsolutePanel();
		outerPanel.setStyleName("qp-mathinteraction");
		mainPanel = new FlowPanel();
		mainPanel.setStyleName("qp-mathinteraction-inner");
		outerPanel.add(mainPanel, 0, 0);
		
		applyIdAndClassToView(mainPanel);
		placeholders.get(0).add(outerPanel);		

		if (getModuleElement().hasAttribute("widthBindingGroup")){
			widthBindingIdentifier = new DefaultBindingGroupIdentifier(getModuleElement().getAttribute("widthBindingGroup"));
		}
		
		helper = new MathModuleHelper(getModuleElement(), getModuleSocket(), getResponse(), this);
		
		helper.initStyles();
		
		helper.initGapsProperties();
		
		gaps = helper.initGaps();
		
		initGapListeners();
	}

	@Override
	public void onBodyLoad() {
	}

	@Override
	public void onBodyUnload() {
	}

	@Override
	public void onSetUp() {
		
		listBoxesLayer = new AbsolutePanel();
		listBoxesLayer.setStyleName("qp-mathinteraction-gaps");
		outerPanel.add(listBoxesLayer);		
		helper.placeGaps(listBoxesLayer);
		
		updateResponse(false);
	}

	private void initGapListeners(){
		for (MathGap gap : gaps){
			if (gap instanceof TextEntryGap){
				((TextEntryGap)gap).getTextBox().addChangeHandler(new ChangeHandler() {
					
					@Override
					public void onChange(ChangeEvent event) {
						updateResponse(true);
					}
				});
			} else if (gap instanceof InlineChoiceGap){
				((InlineChoiceGap)gap).getListBox().setChangeListener(new ExListBoxChangeListener() {
					
					@Override
					public void onChange() {
						updateResponse(true);
					}
				});
			}
		}
	}
	
	@Override
	public void onStart() {
		
		helper.calculateActualSizes();
		
		helper.setSizes();
		
		helper.initMath(mainPanel);

		listBoxesLayer.setWidth(String.valueOf(mainPanel.getOffsetWidth()) + "px");
		listBoxesLayer.setHeight(String.valueOf(mainPanel.getOffsetHeight()) + "px");

		helper.positionGaps(listBoxesLayer);	
	}

	@Override
	public void onClose() {
	}

	@Override
	public void markAnswers(boolean mark) {
		if (mark  && !markingAnswer){
			List<Boolean> evaluation = getModuleSocket().evaluateResponse(getResponse());

			for (int i = 0 ; i < evaluation.size()  &&  i < gaps.size() ; i ++){
				if ("".equals( getResponse().values.get(i)) ){
					gaps.get(i).mark(false, false);
				} else if (evaluation.get(i)){
					gaps.get(i).mark(true, false);
				} else {
					gaps.get(i).mark(false, true);
				}
			}

		} else  if (!mark && markingAnswer){

			for (int i = 0 ; i < gaps.size() ; i ++){
				gaps.get(i).unmark();
			}
		}
		markingAnswer = mark;
	}

	@Override
	public void showCorrectAnswers(boolean show) {
		if (show  &&  !showingAnswer){
			for (int i = 0 ; i < getResponse().correctAnswers.getResponseValuesCount() ; i ++){
				gaps.get(i).setValue( getResponse().correctAnswers.getResponseValue(i).getAnswers().get(0) );
			}
		} else if (!show  &&  showingAnswer){
			for (int i = 0 ; i < getResponse().values.size() ; i ++){
				gaps.get(i).setValue( getResponse().values.get(i) );
			}
		}
		showingAnswer = show;
	}

	@Override
	public void lock(boolean lk) {
		locked = lk;
		for (int i = 0 ; i < gaps.size() ; i ++){
			gaps.get(i).setEnabled(!lk);
		}
	}

	@Override
	public void reset() {
		for (int i = 0 ; i < gaps.size() ; i ++){
			gaps.get(i).reset();
		}
		updateResponse(false);
	}

	@Override
	public JSONArray getState() {
		JSONArray arr = new JSONArray();
		for (int i = 0 ; i < getResponse().values.size() ; i ++){
			JSONString val = new JSONString(getResponse().values.get(i));
			arr.set(i,  val);
		}
		return arr;
	}

	@Override
	public void setState(JSONArray newState) {
		if (newState.isArray() != null){
			for (int i = 0 ; i < gaps.size() ; i ++){
				gaps.get(i).setValue(newState.get(i).isString().stringValue());
			}
		}
		updateResponse(false);
	}

	@Override
	public JavaScriptObject getJsSocket() {
		return ModuleJsSocketFactory.createSocketObject(this);
	}

	private void updateResponse(boolean userInteract){
		if (showingAnswer)
			return;
		if (getResponse() != null){
			getResponse().values.clear();
			for (int i = 0 ; i < gaps.size() ; i ++){
				getResponse().values.add( gaps.get(i).getValue() );
			}
			fireStateChanged(userInteract);
		}
	}

	@Override
	public MathModule getNewInstance() {
		return new MathModule();
	}
	
}
