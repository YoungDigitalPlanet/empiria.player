package eu.ydp.empiria.player.client.module.math;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import com.google.gwt.user.client.ui.AbsolutePanel;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.xml.client.Element;
import com.google.gwt.xml.client.Node;
import com.google.gwt.xml.client.NodeList;
import com.mathplayer.player.MathPlayerManager;
import com.mathplayer.player.geom.Color;
import com.mathplayer.player.geom.Font;
import com.mathplayer.player.geom.Point;
import com.mathplayer.player.interaction.GapIdentifier;
import com.mathplayer.player.interaction.InteractionManager;
import com.mathplayer.player.model.interaction.CustomFieldDescription;

import eu.ydp.empiria.player.client.components.ExListBox;
import eu.ydp.empiria.player.client.controller.variables.objects.response.Response;
import eu.ydp.empiria.player.client.module.IModule;
import eu.ydp.empiria.player.client.module.ModuleSocket;
import eu.ydp.empiria.player.client.module.ModuleTagName;
import eu.ydp.empiria.player.client.util.IntegerUtils;
import eu.ydp.empiria.player.client.util.XMLUtils;
import eu.ydp.empiria.player.client.util.geom.Size;

public class MathModuleHelper {

	private Element moduleElement;
	private ModuleSocket moduleSocket;
	private Response response;
	private List<MathGap> gaps;
	private Map<String, String> styles;
	private List<Boolean> subsups = new ArrayList<Boolean>();
	
	private int fontSize = 16;
	private String fontName = "Arial";
	private boolean fontBold = false;
	private boolean fontItalic = false;
	private String fontColor = "#000000";
	private Integer textEntryGapWidth = 36;
	private Integer textEntryGapHeight = 14;
	private Integer textEntryFontSize = 18;
	private Integer textEntrySubSupWidth;
	private Integer textEntrySubSupHeight;
	private Integer textEntrySubSupFontSize;
	private Integer inlineChoiceWidth = 48;
	private Integer inlineChoiceHeight = 24;	
	
	private MathPlayerManager mpm = new MathPlayerManager();
	private InteractionManager interactionManager;
	
	private Size actualTextEntryGapSize;
	private IModule module;
	private Map<String, Size> actualSizes;

	public MathModuleHelper(Element moduleElement, ModuleSocket moduleSocket, Response response, IModule module){
		this.moduleElement = moduleElement;
		this.moduleSocket = moduleSocket;
		this.response = response;
		this.module = module; 
	}
	
	private enum GapType{
		TEXT_ENTRY(){
			public String getName(){
				return "text-entry";
			}
		}, 
		INLINE_CHOICE(){
			public String getName(){
				return "inline-choice";
			}
		
		};
		public abstract String getName();
	}
	
	public List<MathGap> initGaps(){
		
		NodeList gapNodes = moduleElement.getElementsByTagName("gap");
		gaps = new ArrayList<MathGap>();
		
		for (int i = 0 ; i < gapNodes.getLength() ; i ++){
			Element currElement = (Element)gapNodes.item(i);
			
			if (currElement.hasAttribute("type")  &&  GapType.INLINE_CHOICE.getName().equals( currElement.getAttribute("type") )){

				ExListBox listBox = new ExListBox();
				listBox.setSelectedIndex(-1);
								
				
				NodeList optionNodes = currElement.getElementsByTagName("inlineChoice");
				ArrayList<String> listBoxIdentifiers = new ArrayList<String>();
				
				for (int o = 0 ; o < optionNodes.getLength() ; o ++){
					String currId = ((Element)optionNodes.item(o)).getAttribute("identifier");
					listBoxIdentifiers.add(currId);
					Widget baseBody = moduleSocket.getInlineBodyGeneratorSocket().generateInlineBody(optionNodes.item(o));
					Widget popupBody = moduleSocket.getInlineBodyGeneratorSocket().generateInlineBody(optionNodes.item(o));
					listBox.addOption(baseBody, popupBody);
				}
				
				gaps.add(new InlineChoiceGap(listBox, listBoxIdentifiers));
				
				
			} else if (currElement.hasAttribute("type")  &&  GapType.TEXT_ENTRY.getName().equals( currElement.getAttribute("type") )){
				
				TextEntryGap teg = new TextEntryGap();	
				int fontSize;
				int gapWidth;
				int gapHeight;
				if (!subsups.get(i)){
					fontSize = textEntryFontSize;
					gapWidth = textEntryGapWidth;
					gapHeight = textEntryGapHeight;
				}else {
					fontSize = textEntrySubSupFontSize;
					gapWidth = textEntrySubSupWidth;
					gapHeight = textEntrySubSupHeight;
				}
				String groupName = null;
				for (String currGroupName : response.groups.keySet()){
					if (response.groups.get(currGroupName).contains(i)){
						groupName = currGroupName;
						break;
					}
				}
				teg.init(currElement, moduleSocket, module, response.correctAnswers.getResponseValue(i).getAnswers(), groupName, fontSize);
				teg.setGapWidth(gapWidth);
				teg.setGapHeight(gapHeight);
				gaps.add(teg);
			}
		}
		return gaps;
	}
	
	public void initStyles(){
		
		styles = moduleSocket.getStyles(moduleElement);
		
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
		
		if (styles.containsKey("-empiria-math-color")){
			fontColor = styles.get("-empiria-math-color").toUpperCase();
		}
		
		if (styles.containsKey("-empiria-math-gap-width")){
			textEntryGapWidth = IntegerUtils.tryParseInt(styles.get("-empiria-math-gap-width"));
		}
		
		if (styles.containsKey("-empiria-math-gap-height")){
			textEntryGapHeight = IntegerUtils.tryParseInt(styles.get("-empiria-math-gap-height"));
		}
		
		if (styles.containsKey("-empiria-math-gap-font-size")){
			textEntryFontSize = IntegerUtils.tryParseInt(styles.get("-empiria-math-gap-font-size"));
		}
		
		if (styles.containsKey("-empiria-math-gap-subsup-width")){
			textEntrySubSupWidth = IntegerUtils.tryParseInt(styles.get("-empiria-math-gap-subsup-width"));
		} else {
			textEntrySubSupWidth = (int) (textEntryGapWidth * 0.7);
		}
			
		if (styles.containsKey("-empiria-math-gap-subsup-height")){
			textEntrySubSupHeight = IntegerUtils.tryParseInt(styles.get("-empiria-math-gap-subsup-height"));
		} else {
			textEntrySubSupHeight = (int) (textEntryGapHeight * 0.7);
		}
		
		if (styles.containsKey("-empiria-math-gap-subsup-font-size")){
			textEntrySubSupFontSize = IntegerUtils.tryParseInt(styles.get("-empiria-math-gap-subsup-font-size"));
		} else {
			textEntrySubSupFontSize = (int) (textEntryFontSize * 0.7);
		}
		
		if (styles.containsKey("-empiria-math-drop-width")){			
			inlineChoiceWidth = IntegerUtils.tryParseInt(styles.get("-empiria-math-drop-width"));
		}
		
		if (styles.containsKey("-empiria-math-drop-height")){			
			inlineChoiceHeight = IntegerUtils.tryParseInt(styles.get("-empiria-math-drop-height"));
		}
	}
	
	
	public void initGapsProperties(){
		
		NodeList gapNodes = moduleElement.getElementsByTagName("gap");
		
		for (int i = 0 ; i < gapNodes.getLength() ; i ++){
			Element currElement = (Element)gapNodes.item(i);
			
			Node node = currElement.getParentNode();
			Node prevNode = currElement;
			boolean uidFound = false;
			
			while (node != null  &&  !node.getNodeName().equals(ModuleTagName.MATH_INTERACTION.tagName())){
				if (node.getNodeName().equals("msub")  ||  node.getNodeName().equals("msup") ||  node.getNodeName().equals("msubsup") ||  node.getNodeName().equals("mmultiscripts")){
					if (!XMLUtils.getFirstChildElement((Element)node).equals(prevNode)){
						subsups.add(true);
						uidFound = true;
						break;
					}
				}
				prevNode = node;
				node = node.getParentNode();
			}
			if (!uidFound)
				subsups.add(false);
				
		}		
	}
	
	public void calculateActualSizes(){
		
				actualSizes = new HashMap<String, Size>();
		
		for (int i = 0 ; i < gaps.size() ; i ++){
			if (gaps.get(i) instanceof TextEntryGap){
				TextEntryGap teg = (TextEntryGap)gaps.get(i);
				((TextEntryGap)gaps.get(i)).updateGapWidth();
				if (!"".equals(teg.getUid())){
					actualSizes.put(teg.getUid(), new Size(teg.getOffsetWidth(), teg.getOffsetHeight()));
				}
			}
		}
		
	}
	
	public void setSizes(){
		
		for (int i = 0 ; i < gaps.size() ; i ++){
			if (gaps.get(i) instanceof TextEntryGap){
				TextEntryGap teg = (TextEntryGap)gaps.get(i); 
				if ( !"".equals(teg.getUid())){
					mpm.setCustomFieldWidth(GapIdentifier.createIdIdentifier(teg.getUid()), actualSizes.get(teg.getUid()).getWidth());
					if (subsups.get(i)){
						mpm.setCustomFieldHeight(GapIdentifier.createIdIdentifier(teg.getUid()), actualSizes.get(teg.getUid()).getHeight());
					}
				}
			}
		}
		
		actualTextEntryGapSize = TextEntryGap.getTextEntryGapActualSize(new Size(textEntryGapWidth, textEntryGapHeight));
				
		mpm.setCustomFieldWidth(GapIdentifier.createTypeIdentifier(GapType.TEXT_ENTRY.getName()), actualTextEntryGapSize.getWidth());
		mpm.setCustomFieldHeight(GapIdentifier.createTypeIdentifier(GapType.TEXT_ENTRY.getName()), actualTextEntryGapSize.getHeight());

		mpm.setCustomFieldWidth(GapIdentifier.createTypeIdentifier(GapType.INLINE_CHOICE.getName()), inlineChoiceWidth);	
		mpm.setCustomFieldHeight(GapIdentifier.createTypeIdentifier(GapType.INLINE_CHOICE.getName()), inlineChoiceHeight);
	}

	public void initMath(Panel panel) {
		
		Integer fontColorInt = IntegerUtils.tryParseInt(fontColor.trim().substring(1), 16, 0); 
		Font f = new Font(fontSize, fontName, fontItalic, fontBold, new Color(fontColorInt / (256 * 256), fontColorInt / 256 % 256, fontColorInt % 256));
		mpm.setFont(f);
		
		interactionManager = mpm.createMath(moduleElement.getChildNodes().toString(), panel);
		
	}

	public void placeGaps(AbsolutePanel listBoxesLayer) {

		for (int i = 0 ; i < gaps.size() ; i ++){
			if (gaps.get(i) instanceof InlineChoiceGap){
				InlineChoiceGap icg = (InlineChoiceGap)gaps.get(i);
				icg.getListBox().setWidth(String.valueOf(inlineChoiceWidth) + "px");
				icg.getListBox().setHeight(String.valueOf(inlineChoiceHeight) + "px");
				listBoxesLayer.add(icg.getContainer());
				
			} else  if (gaps.get(i) instanceof TextEntryGap){
				TextEntryGap teg = (TextEntryGap)gaps.get(i);
				listBoxesLayer.add(teg.getContainer());
			}
		}
	}

	public void positionGaps(AbsolutePanel listBoxesLayer) {
		
		Vector<CustomFieldDescription> customFieldDescriptions = interactionManager.getCustomFieldDescriptions();		
		Iterator<CustomFieldDescription> customFieldDescriptionsIterator = customFieldDescriptions.iterator();

		for (int i = 0 ; i < gaps.size() ; i ++){
			if (!customFieldDescriptionsIterator.hasNext())
				break;
			Point position = customFieldDescriptionsIterator.next().getPosition();
			if (gaps.get(i) instanceof InlineChoiceGap){
				listBoxesLayer.setWidgetPosition(gaps.get(i).getContainer(), position.x, position.y);				
			} else  if (gaps.get(i) instanceof TextEntryGap){
				listBoxesLayer.setWidgetPosition(gaps.get(i).getContainer(), position.x, position.y);
			}

		}
	}
}
