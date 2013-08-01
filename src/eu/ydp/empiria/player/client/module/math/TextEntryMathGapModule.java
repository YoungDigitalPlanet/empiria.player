package eu.ydp.empiria.player.client.module.math;

import java.util.List;
import java.util.Map;

import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.user.client.ui.HasWidgets;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.xml.client.Element;
import com.google.gwt.xml.client.Node;
import com.google.inject.Inject;

import eu.ydp.empiria.player.client.gin.scopes.page.PageScoped;
import eu.ydp.empiria.player.client.module.ModuleTagName;
import eu.ydp.empiria.player.client.module.ResponseSocket;
import eu.ydp.empiria.player.client.module.dragdrop.SourcelistClient;
import eu.ydp.empiria.player.client.module.dragdrop.SourcelistManager;
import eu.ydp.empiria.player.client.module.gap.TextEntryGapBase;
import eu.ydp.empiria.player.client.module.gap.TextEntryPresenterUnlocker;
import eu.ydp.empiria.player.client.module.textentry.DragContentController;
import eu.ydp.empiria.player.client.resources.EmpiriaStyleNameConstants;
import eu.ydp.empiria.player.client.resources.EmpiriaTagConstants;
import eu.ydp.empiria.player.client.style.StyleSocket;
import eu.ydp.gwtutil.client.NumberUtils;
import eu.ydp.gwtutil.client.xml.XMLUtils;

public class TextEntryMathGapModule extends TextEntryGapBase implements MathGap, SourcelistClient {

	@Inject
	MathGapModel mathGapModel;
	
	@Inject
	public TextEntryMathGapModule(TextEntryMathGapModulePresenter presenter,
			StyleSocket styleSocket, 
			@PageScoped SourcelistManager sourcelistManager,
			DragContentController dragContentController,
			@PageScoped ResponseSocket responseSocket) {
		super(presenter, styleSocket, sourcelistManager, dragContentController, responseSocket);
	}

	@Override
	public void installViews(List<HasWidgets> placeholders) {
		installViewInPlaceholder(placeholders.get(0));
		addPlayerEventHandlers();
		
		String uid = getElementAttributeValue(EmpiriaTagConstants.ATTR_UID);
		mathGapModel.setUid(uid);
		
		applyIdAndClassToView((Widget) presenter.getContainer());
		initStyles();
	}

	@Override
	public String getUid() {
		return mathGapModel.getUid();
	}

	@Override
	public void setIndex(int index) {
		mathGapModel.getUid();
	}

	@Override
	protected TextEntryPresenterUnlocker getTextEntryGapPresenter() {
		return (TextEntryPresenterUnlocker) presenter;
	}

	public boolean isSubOrSup(Element node, Node parentNode) {
		Node prevNode = node;
		boolean subsupParentFound = false;

		if (node.hasAttribute(EmpiriaTagConstants.ATTR_UID)) {
			while (parentNode != null && !isMathInteractionNode(parentNode)) {
				if (isSubOrSupElement(parentNode) && !XMLUtils.getFirstChildElement((Element) parentNode).equals(prevNode)) {
					subsupParentFound = true;
					break;
				}
				prevNode = parentNode;
				parentNode = parentNode.getParentNode();
			}
		}

		return subsupParentFound;
	}

	public Widget getContainer() {
		return (Widget) presenter.getContainer();
	}
	public void setGapHeight(int gapHeight) {
		presenter.setHeight(gapHeight, Unit.PX);
	}
	
	public int getGapHeight() {
		return presenter.getOffsetHeight();
	}
	
	public void setGapWidth(int gapWidth) {
		presenter.setWidth(gapWidth, Unit.PX);
	}
	
	public int getGapWidth() {
		return presenter.getOffsetWidth();
	}

	public void setGapFontSize(int gapFontSize) {
		presenter.setFontSize(gapFontSize, Unit.PX);
	}

	public void setMathStyles(Map<String, String> mathStyles) {
		mathGapModel.setMathStyles(mathStyles);
	}
	
	protected boolean isSubOrSupElement(Node node) {
		return node.getNodeName().equals("msub") || node.getNodeName().equals("msup") || node.getNodeName().equals("msubsup")
				|| node.getNodeName().equals("mmultiscripts");
	}

	protected boolean isMathInteractionNode(Node node) {
		return node.getNodeName().equals(ModuleTagName.MATH_INTERACTION.tagName());
	}

	protected void setDimensions() {
		if (mathGapModel.getMathStyles().containsKey(EmpiriaStyleNameConstants.EMPIRIA_MATH_GAP_WIDTH)) {
			setGapWidth(NumberUtils.tryParseInt(mathGapModel.getMathStyles().get(EmpiriaStyleNameConstants.EMPIRIA_MATH_GAP_WIDTH)));
		}

		if (mathGapModel.getMathStyles().containsKey(EmpiriaStyleNameConstants.EMPIRIA_MATH_GAP_HEIGHT)) {
			setGapHeight(NumberUtils.tryParseInt(mathGapModel.getMathStyles().get(EmpiriaStyleNameConstants.EMPIRIA_MATH_GAP_HEIGHT)));
		}

		if (mathGapModel.getMathStyles().containsKey(EmpiriaStyleNameConstants.EMPIRIA_MATH_GAP_FONT_SIZE)) {
			setGapFontSize(NumberUtils.tryParseInt(mathGapModel.getMathStyles().get(EmpiriaStyleNameConstants.EMPIRIA_MATH_GAP_FONT_SIZE)));
		}

		if (isSubOrSup(getModuleElement(), getModuleElement().getParentNode())) {
			if (mathGapModel.getMathStyles().containsKey(EmpiriaStyleNameConstants.EMPIRIA_MATH_GAP_SUBSUP_WIDTH)) {
				setGapWidth(NumberUtils.tryParseInt(mathGapModel.getMathStyles().get(EmpiriaStyleNameConstants.EMPIRIA_MATH_GAP_SUBSUP_WIDTH)));
			} else {
				setGapWidth((int) (getGapWidth() * 0.7));
			}

			if (mathGapModel.getMathStyles().containsKey(EmpiriaStyleNameConstants.EMPIRIA_MATH_GAP_SUBSUP_HEIGHT)) {
				setGapHeight(NumberUtils.tryParseInt(mathGapModel.getMathStyles().get(EmpiriaStyleNameConstants.EMPIRIA_MATH_GAP_SUBSUP_HEIGHT)));
			} else {
				setGapHeight((int) (getGapWidth() * 0.7));
			}

			if (mathGapModel.getMathStyles().containsKey(EmpiriaStyleNameConstants.EMPIRIA_MATH_GAP_SUBSUP_FONT_SIZE)) {
				setGapFontSize(NumberUtils.tryParseInt(mathGapModel.getMathStyles().get(EmpiriaStyleNameConstants.EMPIRIA_MATH_GAP_SUBSUP_FONT_SIZE)));
			} else {
				setGapFontSize((int) (getGapWidth() * 0.7));
			}
		}
	}
	
	private void initStyles() {
		readStyles();
		updateStyles();
	}

	private void readStyles() {
		Map<String, String> styles = styleSocket.getStyles(getModuleElement());
		mathGapModel.getMathStyles().putAll(styles);
	}

	private void updateStyles() {
		setDimensions();
		setMaxlengthBinding(mathGapModel.getMathStyles(), getModuleElement());
		setWidthBinding(mathGapModel.getMathStyles(), getModuleElement());
		initReplacements(mathGapModel.getMathStyles());
	}


	private void installViewInPlaceholder(HasWidgets placeholder) {
		Widget placeholderWidget = (Widget) placeholder;
		HasWidgets placeholderParent = (HasWidgets) placeholderWidget.getParent();
		presenter.installViewInContainer(placeholderParent);
		placeholderWidget.removeFromParent();
	}
}