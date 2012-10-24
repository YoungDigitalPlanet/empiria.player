package eu.ydp.empiria.player.client.module.math;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Style;
import com.google.gwt.dom.client.Style.Overflow;
import com.google.gwt.dom.client.Style.Position;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiTemplate;
import com.google.gwt.user.client.ui.AbsolutePanel;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HasWidgets;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.xml.client.Element;
import com.google.gwt.xml.client.NodeList;
import com.mathplayer.player.MathPlayerManager;
import com.mathplayer.player.geom.Color;
import com.mathplayer.player.geom.Font;
import com.mathplayer.player.geom.Point;
import com.mathplayer.player.interaction.GapIdentifier;
import com.mathplayer.player.interaction.InteractionManager;
import com.mathplayer.player.model.interaction.CustomFieldDescription;

import eu.ydp.empiria.player.client.controller.body.BodyGeneratorSocket;
import eu.ydp.empiria.player.client.controller.events.interaction.InteractionEventsListener;
import eu.ydp.empiria.player.client.controller.events.interaction.StateChangedInteractionEvent;
import eu.ydp.empiria.player.client.controller.variables.objects.response.Response;
import eu.ydp.empiria.player.client.gin.PlayerGinjector;
import eu.ydp.empiria.player.client.module.Factory;
import eu.ydp.empiria.player.client.module.ILifecycleModule;
import eu.ydp.empiria.player.client.module.IModule;
import eu.ydp.empiria.player.client.module.IUniqueModule;
import eu.ydp.empiria.player.client.module.ModuleSocket;
import eu.ydp.empiria.player.client.module.containers.AbstractActivityContainerModuleBase;
import eu.ydp.empiria.player.client.resources.EmpiriaStyleNameConstants;
import eu.ydp.empiria.player.client.resources.EmpiriaTagConstants;
import eu.ydp.empiria.player.client.util.events.bus.EventsBus;
import eu.ydp.empiria.player.client.util.events.scope.CurrentPageScope;
import eu.ydp.empiria.player.client.util.events.state.StateChangeEvent;
import eu.ydp.empiria.player.client.util.events.state.StateChangeEventTypes;
import eu.ydp.gwtutil.client.NumberUtils;
import eu.ydp.gwtutil.client.xml.XMLUtils;

public class MathModule extends AbstractActivityContainerModuleBase implements Factory<MathModule>, ILifecycleModule, IUniqueModule {
	
	private static MathModuleViewUiBinder uiBinder = GWT.create(MathModuleViewUiBinder.class);

	@UiTemplate("MathModuleView.ui.xml")
	interface MathModuleViewUiBinder extends UiBinder<Widget, MathModule> {};
	
	@UiField
	protected AbsolutePanel outerPanel;
	
	@UiField
	protected FlowPanel mainPanel;
	
	@UiField
	protected AbsolutePanel gapsPanel;
	
	@UiField
	protected FlowPanel placeholder;
	
	private int fontSize = 16;
	
	private String fontName = "Arial";
	
	private boolean fontBold = false;
	
	private boolean fontItalic = false;
	
	private String fontColor = "#000000";
	
	private Map<String, String> styles;
	
	private Element moduleElement;
	
	private List<MathGap> mathGaps;

	private MathPlayerManager mathManager;
	
	private InteractionManager mathInteractionManager;
	
	private Response response;
	
	private String responseIdentifier;
	
	private final EventsBus eventsBus = PlayerGinjector.INSTANCE.getEventsBus();
	
	public MathModule(){
		uiBinder.createAndBindUi(this);
	}
	
	@Override
	public void initModule(Element element, ModuleSocket moduleSocket, InteractionEventsListener interactionListener, BodyGeneratorSocket bodyGenerator) {
		super.initModule(element, moduleSocket, interactionListener, bodyGenerator);
		
		moduleElement = element;
		styles = getModuleSocket().getStyles(moduleElement);
		
		readAttributes(element);
		setResponse();
		initStyles(styles);
		initializePanels();
		initializeMathPlayer();
		generateGaps(bodyGenerator);
		setIndexesOnGaps();
		setGapMathStyles();
	}
	
	private void setIndexesOnGaps() {
		int index = 0;
		
		for (MathGap gap: getMathGaps()) {
			gap.setIndex(index++);
		}
	}
	
	protected void setGapMathStyles() {
		for (MathGap gap: getMathGaps()) {
			gap.setMathStyles(styles);
		}
	}

	private void setResponse(){
		response = findResponseFromModuleElement();
	}
	
	private Response findResponseFromModuleElement(){
		responseIdentifier = XMLUtils.getAttributeAsString(moduleElement, EmpiriaTagConstants.ATTR_RESPONSE_IDENTIFIER);
		return getModuleSocket().getResponse(responseIdentifier);
	}

	@Override
	public HasWidgets getContainer() {
		return placeholder;
	}

	@Override
	public Widget getView() {
		return placeholder;
	}

	@Override
	public MathModule getNewInstance() {
		return new MathModule();
	}
	
	public void initStyles(Map<String, String> styles) {
		if (styles.containsKey(EmpiriaStyleNameConstants.EMPIRIA_MATH_FONT_SIZE)) {
			fontSize = NumberUtils.tryParseInt(styles.get(EmpiriaStyleNameConstants.EMPIRIA_MATH_FONT_SIZE));
		}

		if (styles.containsKey(EmpiriaStyleNameConstants.EMPIRIA_MATH_FONT_FAMILY)) {
			fontName = styles.get(EmpiriaStyleNameConstants.EMPIRIA_MATH_FONT_FAMILY);
		}

		if (styles.containsKey(EmpiriaStyleNameConstants.EMPIRIA_MATH_FONT_WEIGHT)) {
			fontBold = styles.get(EmpiriaStyleNameConstants.EMPIRIA_MATH_FONT_WEIGHT).equalsIgnoreCase(EmpiriaStyleNameConstants.VALUE_BOLD);
		}

		if (styles.containsKey(EmpiriaStyleNameConstants.EMPIRIA_MATH_FONT_STYLE)) {
			fontItalic = styles.get(EmpiriaStyleNameConstants.EMPIRIA_MATH_FONT_STYLE).equalsIgnoreCase(EmpiriaStyleNameConstants.VALUE_ITALIC);
		}

		if (styles.containsKey(EmpiriaStyleNameConstants.EMPIRIA_MATH_COLOR)) {
			fontColor = styles.get(EmpiriaStyleNameConstants.EMPIRIA_MATH_COLOR).toUpperCase();
		}
	}
	
	@Override
	public void onBodyLoad() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onBodyUnload() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onSetUp() {
		for (MathGap gap: getMathGaps()) {
			gap.setUpGap();
		}
		
		placeGaps();		
		updateResponseWihtoutUserAction();
	}

	@Override
	public void onStart() {
		for (MathGap gap: getMathGaps()) {
			gap.startGap();
		}
		
		setSizeOfGapDummies();
		
		mathInteractionManager = createMath();
		gapsPanel.setWidth(mainPanel.getOffsetWidth() + "px");
		gapsPanel.setHeight(mainPanel.getOffsetHeight() + "px");
		
		positionGaps();
	}
	
	private void positionGaps() {		
		List<CustomFieldDescription> customFieldDescriptions = mathInteractionManager.getCustomFieldDescriptions();
		Iterator<CustomFieldDescription> customFieldDescriptionsIterator = customFieldDescriptions.iterator();
		
		for(MathGap gap: getMathGaps()){
			if (!customFieldDescriptionsIterator.hasNext()) {
				break;
			}
			
			Point position = customFieldDescriptionsIterator.next().getPosition();
			gapsPanel.setWidgetPosition(gap.getContainer(), position.x, position.y);
		}
	}

	private void setSizeOfGapDummies(){		
		for (MathGap gap: getMathGaps()) {
			GapIdentifier gapId = GapIdentifier.createIdIdentifier(gap.getUid());
			
			if(gapId != null) {
				int width = gap.getContainer().getOffsetWidth();
				int height = gap.getContainer().getOffsetHeight();
					
				mathManager.setCustomFieldWidth(gapId, width);
				//if(gap.isSubOrSupParent){
					mathManager.setCustomFieldHeight(gapId, height);
				//}
			}	
		}
		
		/*if(actualTextEntryGapSize != null){
			mpm.setCustomFieldWidth(GapIdentifier.createTypeIdentifier(GapType.TEXT_ENTRY.getName()), actualTextEntryGapSize.getWidth());
			mpm.setCustomFieldHeight(GapIdentifier.createTypeIdentifier(GapType.TEXT_ENTRY.getName()), actualTextEntryGapSize.getHeight());
		}
		
		if(actualInlineChoiceGapSize != null){
			mpm.setCustomFieldWidth(GapIdentifier.createTypeIdentifier(GapType.INLINE_CHOICE.getName()), actualInlineChoiceGapSize.getWidth());
			mpm.setCustomFieldHeight(GapIdentifier.createTypeIdentifier(GapType.INLINE_CHOICE.getName()), actualInlineChoiceGapSize.getHeight());
		}*/
	}

	@Override
	public void onClose() {
		// TODO Auto-generated method stub
		
	}
	
	private void placeGaps(){
		for (MathGap gap : getMathGaps()) {
			Widget gapContainer = gap.getContainer();
			Style gapStyle = gapContainer.getElement().getStyle();
			
			gapStyle.setTop(0, Unit.PX);
			gapStyle.setLeft(0, Unit.PX);
			gapStyle.setPosition(Position.ABSOLUTE);
		}
	}
	
	private void initializePanels() {
		applyIdAndClassToView(mainPanel);
		
		outerPanel.getElement().getStyle().setOverflow(Overflow.VISIBLE);
		gapsPanel.getElement().getStyle().setOverflow(Overflow.VISIBLE);
		
		Style mainPanelStyle = mainPanel.getElement().getStyle();
		mainPanelStyle.setPosition(Position.ABSOLUTE);
		mainPanelStyle.setTop(0, Unit.PX);
		mainPanelStyle.setLeft(0, Unit.PX);
	}
	
	private void initializeMathPlayer(){
		mathManager = new MathPlayerManager();
		Integer fontColorInt = NumberUtils.tryParseInt(fontColor.trim().substring(1), 16, 0);
		Font font = new Font(fontSize, fontName, fontItalic, fontBold, new Color(fontColorInt / (256 * 256), fontColorInt / 256 % 256, fontColorInt % 256));
		
		mathManager.setFont(font);
	}
	
	private InteractionManager createMath(){
		return mathManager.createMath(moduleElement.getChildNodes().toString(), mainPanel);
	}
	
	private void generateGaps(BodyGeneratorSocket bgs){
		NodeList gapsNodeList = moduleElement.getElementsByTagName(EmpiriaTagConstants.NAME_GAP);
		
		for(int i = 0; i < gapsNodeList.getLength(); i++){
			Element gapElement = (Element) gapsNodeList.item(i);
			bgs.processNode(gapElement, gapsPanel);
		}
	}
	
	private List<MathGap> getMathGaps(){
		if(mathGaps == null) {
			mathGaps = new ArrayList<MathGap>();
			
			for(IModule child: getModuleSocket().getChildren(this)) {
				if(child instanceof MathGap) {
					mathGaps.add((MathGap) child);
				}
			}
		}
		
		return mathGaps;
	}

	public Response getResponse() {
		return response;
	}
	
	public void updateResponseAfterUserAction(){
		updateResponse();
		fireStateChangedEvent(true);
	}
	
	public void updateResponseWihtoutUserAction(){
		updateResponse();
		fireStateChangedEvent(false);
	}
	
	private void updateResponse(){
		getResponse().values.clear();
		
		for(MathGap gap: getMathGaps()){
			getResponse().values.add(gap.getIndex(), gap.getValue());
		}
	}
	
	protected void fireStateChangedEvent(boolean userInteract){
		eventsBus.fireEvent(new StateChangeEvent(StateChangeEventTypes.STATE_CHANGED, new StateChangedInteractionEvent(userInteract, this)), new CurrentPageScope());
	}

	@Override
	public String getIdentifier() {
		return responseIdentifier;
	}
}
