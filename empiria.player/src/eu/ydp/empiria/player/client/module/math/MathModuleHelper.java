package eu.ydp.empiria.player.client.module.math;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import com.google.gwt.user.client.ui.AbsolutePanel;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.xml.client.Element;
import com.google.gwt.xml.client.NodeList;
import com.mathplayer.player.MathPlayerManager;
import com.mathplayer.player.geom.Color;
import com.mathplayer.player.geom.Font;
import com.mathplayer.player.geom.Point;
import com.mathplayer.player.interaction.GapIdentifier;
import com.mathplayer.player.interaction.InteractionManager;
import com.mathplayer.player.model.interaction.CustomFieldDescription;

import eu.ydp.empiria.player.client.PlayerGinjector;
import eu.ydp.empiria.player.client.controller.variables.objects.response.Response;
import eu.ydp.empiria.player.client.module.IModule;
import eu.ydp.empiria.player.client.module.ModuleSocket;
import eu.ydp.empiria.player.client.resources.EmpiriaStyleNameConstants;
import eu.ydp.empiria.player.client.resources.EmpiriaTagConstants;
import eu.ydp.empiria.player.client.resources.StyleNameConstants;
import eu.ydp.empiria.player.client.util.geom.Size;
import eu.ydp.gwtutil.client.NumberUtils;

public class MathModuleHelper {
		
	private final Element moduleElement;
	private final ModuleSocket moduleSocket;
	private final Response response;
	private List<MathGap> gaps;

	private int fontSize = 16;
	private String fontName = "Arial";
	private boolean fontBold = false;
	private boolean fontItalic = false;
	private String fontColor = "#000000";
	
	private final MathPlayerManager mpm = createMathPlayerManager();
	
	private InteractionManager interactionManager;
	
	private final IModule module;
	private Map<String, Size> actualSizes;
	protected boolean markingAnswer = false;
	protected boolean showingAnswer = false;

	protected StyleNameConstants styleNames = getPlayerGinjectorInstance().getStyleNameConstants();

	public MathModuleHelper(Element moduleElement, ModuleSocket moduleSocket, Response response, IModule module) {
		this.moduleElement = moduleElement;
		this.moduleSocket = moduleSocket;
		this.response = response;
		this.module = module;
		
		initStyles();
	}

	enum GapType {
		TEXT_ENTRY() {
			@Override
			public String getName() {
				return "text-entry";
			}
		},
		INLINE_CHOICE() {
			@Override
			public String getName() {
				return "inline-choice";
			}

		};
		
		public abstract String getName();
		
		public static GapType getByName(String searchedName){
			GapType searchedType = null;
			
			for(GapType typeValue: values()){
				if(typeValue.getName().equals(searchedName)){
					searchedType = typeValue;
					break;
				}
			}
			
			return searchedType;
		}
	}

	public List<MathGap> initGaps() {
		NodeList gapNodes = moduleElement.getElementsByTagName(EmpiriaTagConstants.NAME_GAP);
		gaps = new ArrayList<MathGap>();

		for (int i = 0; i < gapNodes.getLength(); i++) {
			Element currElement = (Element) gapNodes.item(i);
			GapType gapType = getGapType(currElement);
			Map<String, String> styles = moduleSocket.getStyles(moduleElement);
			
			if (gapType == GapType.INLINE_CHOICE) {
				InlineChoiceGap icg = createInlineChoiceGap(currElement, moduleSocket, styles); 
				gaps.add(icg);
			} else if (gapType == GapType.TEXT_ENTRY) {
				TextEntryGap teg = new TextEntryGap();
				teg.init(currElement, moduleSocket, module, response, i, styles);
				gaps.add(teg);
			}
		}
		return gaps;
	}

	public void markAnswers(List<MathGap> gaps, boolean mark) {
		if (mark  && !markingAnswer){			
			List<Boolean> evaluation = moduleSocket.evaluateResponse(response);

			for (int i = 0 ; i < evaluation.size()  &&  i < gaps.size() ; i ++){
				if ("".equals( response.values.get(i)) ){
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
	
	public void showCorrectAnswers(List<MathGap> gaps, boolean show) {
		if (show  &&  !showingAnswer){
			for (int i = 0 ; i < response.correctAnswers.getResponseValuesCount() ; i ++){
				gaps.get(i).setValue( response.correctAnswers.getResponseValue(i).getAnswers().get(0) );
			}
		} else if (!show  &&  showingAnswer){
			for (int i = 0 ; i < response.values.size() ; i ++){
				gaps.get(i).setValue(response.values.get(i));
			}
		}
		showingAnswer = show;
	}	
	
	public boolean updateResponse(List<MathGap> gaps, boolean userInteract){
		boolean updated = false;
		
		if (!showingAnswer && response != null){
			response.values.clear();
			for (int i = 0 ; i < gaps.size() ; i ++){
				response.values.add( gaps.get(i).getValue() );
			}
			updated = true;
		}
		return updated;
	}	

	public void calculateActualSizes() {
		actualSizes = new HashMap<String, Size>();

		for (int i = 0; i < gaps.size(); i++) {
			if (gaps.get(i) instanceof TextEntryGap) {
				TextEntryGap teg = (TextEntryGap) gaps.get(i);
				((TextEntryGap) gaps.get(i)).updateGapWidth();
				if (!"".equals(teg.getUid())) {
					actualSizes.put(teg.getUid(), new Size(teg.getOffsetWidth(), teg.getOffsetHeight()));
				}
			}
		}
		
		setSizes();
	}

	public void initMath(Panel panel) {
		Integer fontColorInt = NumberUtils.tryParseInt(fontColor.trim().substring(1), 16, 0);
		Font font = new Font(fontSize, fontName, fontItalic, fontBold, new Color(fontColorInt / (256 * 256), fontColorInt / 256 % 256, fontColorInt % 256));
		mpm.setFont(font);

		interactionManager = mpm.createMath(moduleElement.getChildNodes().toString(), panel);
	}

	public void placeGaps(AbsolutePanel listBoxesLayer) {
		for (int i = 0; i < gaps.size(); i++) {
			if (gaps.get(i) instanceof InlineChoiceGap) {
				InlineChoiceGap icg = (InlineChoiceGap) gaps.get(i);
				listBoxesLayer.add(icg.getContainer());
			} else if (gaps.get(i) instanceof TextEntryGap) {
				TextEntryGap teg = (TextEntryGap) gaps.get(i);
				listBoxesLayer.add(teg.getContainer(), 0, 0);
			}
		}
	}

	public void positionGaps(AbsolutePanel listBoxesLayer) {
		Vector<CustomFieldDescription> customFieldDescriptions = interactionManager.getCustomFieldDescriptions();
		Iterator<CustomFieldDescription> customFieldDescriptionsIterator = customFieldDescriptions.iterator();

		for (int i = 0; i < gaps.size(); i++) {
			if (!customFieldDescriptionsIterator.hasNext()) {
				break;
			}
			Point position = customFieldDescriptionsIterator.next().getPosition();
			if (gaps.get(i) instanceof InlineChoiceGap) {
				listBoxesLayer.setWidgetPosition(gaps.get(i).getContainer(), position.x, position.y);
			} else if (gaps.get(i) instanceof TextEntryGap) {
				listBoxesLayer.setWidgetPosition(gaps.get(i).getContainer(), position.x, position.y);
			}
		}
	}
	
	public MathPlayerManager createMathPlayerManager() {
		return new MathPlayerManager();
	}

	public InlineChoiceGap createInlineChoiceGap(Element moduleElement, ModuleSocket moduleSocket, Map<String, String> styles) {
		return new InlineChoiceGap(moduleElement, moduleSocket, styles);
	}
	
	protected PlayerGinjector getPlayerGinjectorInstance() {
		return PlayerGinjector.INSTANCE;
	}
	
	public void initStyles() {		
		Map<String, String> styles = moduleSocket.getStyles(moduleElement);

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
	
	private void setSizes() {
		Size actualTextEntryGapSize = null;
		Size actualInlineChoiceGapSize = null;
		
		for(MathGap gap: gaps){
			if (gap instanceof TextEntryGap) {
				TextEntryGap teg = (TextEntryGap) gap;
				
				if(actualTextEntryGapSize == null){
					actualTextEntryGapSize = TextEntryGap.getTextEntryGapActualSize(new Size(teg.getGapWidth(), teg.getGapHeight()));
				}
				
				if (!"".equals(teg.getUid())) {
					mpm.setCustomFieldWidth(GapIdentifier.createIdIdentifier(teg.getUid()), actualSizes.get(teg.getUid()).getWidth());
					if(teg.isSubOrSupParent()){
						mpm.setCustomFieldHeight(GapIdentifier.createIdIdentifier(teg.getUid()), actualSizes.get(teg.getUid()).getHeight());
					}
				}
			}else if(gap instanceof InlineChoiceGap){
				InlineChoiceGap icg = (InlineChoiceGap) gap;
				
				if(actualInlineChoiceGapSize == null){
					actualInlineChoiceGapSize = new Size(icg.getGapWidth(), icg.getGapHeight());
				}
			}
		}
		
		//FIXME get width from gaps
		//Size actualTextEntryGapSize = TextEntryGap.getTextEntryGapActualSize(new Size(textEntryGapWidth, textEntryGapHeight));
		
		if(actualTextEntryGapSize != null){
			mpm.setCustomFieldWidth(GapIdentifier.createTypeIdentifier(GapType.TEXT_ENTRY.getName()), actualTextEntryGapSize.getWidth());
			mpm.setCustomFieldHeight(GapIdentifier.createTypeIdentifier(GapType.TEXT_ENTRY.getName()), actualTextEntryGapSize.getHeight());
		}
		
		if(actualInlineChoiceGapSize != null){
			mpm.setCustomFieldWidth(GapIdentifier.createTypeIdentifier(GapType.INLINE_CHOICE.getName()), actualInlineChoiceGapSize.getWidth());
			mpm.setCustomFieldHeight(GapIdentifier.createTypeIdentifier(GapType.INLINE_CHOICE.getName()), actualInlineChoiceGapSize.getHeight());
		}
	}
	
	private GapType getGapType(Element element){
		GapType gapType = null;
		String attrTypeValue = element.getAttribute(EmpiriaTagConstants.ATTR_TYPE);
		
		if(attrTypeValue != null){
			gapType = GapType.getByName(attrTypeValue);
		}
		
		return gapType;
	}
		
}
