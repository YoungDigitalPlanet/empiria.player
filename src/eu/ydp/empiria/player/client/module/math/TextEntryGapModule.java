package eu.ydp.empiria.player.client.module.math;

import java.util.List;
import java.util.Map;

import com.google.gwt.event.dom.client.BlurEvent;
import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.user.client.ui.HasWidgets;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.xml.client.Element;
import com.google.gwt.xml.client.Node;
import com.google.inject.Inject;

import eu.ydp.empiria.player.client.gin.factory.TextEntryModuleFactory;
import eu.ydp.empiria.player.client.module.ModuleTagName;
import eu.ydp.empiria.player.client.module.dragdrop.DataObject;
import eu.ydp.empiria.player.client.module.dragdrop.SourcelistClient;
import eu.ydp.empiria.player.client.module.dragdrop.SourcelistManager;
import eu.ydp.empiria.player.client.resources.EmpiriaStyleNameConstants;
import eu.ydp.empiria.player.client.resources.EmpiriaTagConstants;
import eu.ydp.empiria.player.client.style.StyleSocket;
import eu.ydp.gwtutil.client.NumberUtils;
import eu.ydp.gwtutil.client.xml.XMLUtils;

public class TextEntryGapModule extends MathGapBase implements MathGap, SourcelistClient {


	private final SourcelistManager sourcelistManager;

	private final StyleSocket styleSocket;

	@Inject
	public TextEntryGapModule(TextEntryModuleFactory moduleFactory, StyleSocket styleSocket,final SourcelistManager sourcelistManager) {
		this.styleSocket = styleSocket;
		this.sourcelistManager = sourcelistManager;

		presenter = moduleFactory.getTextEntryGapModulePresenter(this);
		PresenterHandler presenterHandler = new PresenterHandler() {
			@Override
			public void onChange(ChangeEvent event) {
				updateResponse(true);
				sourcelistManager.onUserValueChanged();
			}

			@Override
			public void onBlur(BlurEvent event) {
				if (isMobileUserAgent()) {
					updateResponse(true);
				}
			}
		};
		presenter.addPresenterHandler(presenterHandler);
		sourcelistManager.registerModule(this);
	}

	@Override
	public void installViews(List<HasWidgets> placeholders) {
		installViewInPlaceholder(placeholders.get(0));
		addPlayerEventHandlers();
		loadUID();
		applyIdAndClassToView((Widget) presenter.getContainer());
		initStyles();
	}

	private void initStyles() {
		readStyles();
		updateStyles();
	}

	private void readStyles() {
		Map<String, String> styles = styleSocket.getStyles(getModuleElement());
		mathStyles.putAll(styles);
	}

	private void updateStyles() {
		setDimensions();
		setMaxlengthBinding(mathStyles, getModuleElement());
		setWidthBinding(mathStyles, getModuleElement());
		initReplacements(mathStyles);
	}

	private void installViewInPlaceholder(HasWidgets placeholder) {
		Widget placeholderWidget = (Widget) placeholder;
		HasWidgets placeholderParent = (HasWidgets) placeholderWidget.getParent();
		presenter.installViewInContainer(placeholderParent);
		placeholderWidget.removeFromParent();
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

	protected boolean isSubOrSupElement(Node node) {
		return node.getNodeName().equals("msub") || node.getNodeName().equals("msup") || node.getNodeName().equals("msubsup")
				|| node.getNodeName().equals("mmultiscripts");
	}

	protected boolean isMathInteractionNode(Node node) {
		return node.getNodeName().equals(ModuleTagName.MATH_INTERACTION.tagName());
	}

	protected void setDimensions() {
		if (mathStyles.containsKey(EmpiriaStyleNameConstants.EMPIRIA_MATH_GAP_WIDTH)) {
			setGapWidth(NumberUtils.tryParseInt(mathStyles.get(EmpiriaStyleNameConstants.EMPIRIA_MATH_GAP_WIDTH)));
		}

		if (mathStyles.containsKey(EmpiriaStyleNameConstants.EMPIRIA_MATH_GAP_HEIGHT)) {
			setGapHeight(NumberUtils.tryParseInt(mathStyles.get(EmpiriaStyleNameConstants.EMPIRIA_MATH_GAP_HEIGHT)));
		}

		if (mathStyles.containsKey(EmpiriaStyleNameConstants.EMPIRIA_MATH_GAP_FONT_SIZE)) {
			setGapFontSize(NumberUtils.tryParseInt(mathStyles.get(EmpiriaStyleNameConstants.EMPIRIA_MATH_GAP_FONT_SIZE)));
		}

		if (isSubOrSup(getModuleElement(), getModuleElement().getParentNode())) {
			if (mathStyles.containsKey(EmpiriaStyleNameConstants.EMPIRIA_MATH_GAP_SUBSUP_WIDTH)) {
				setGapWidth(NumberUtils.tryParseInt(mathStyles.get(EmpiriaStyleNameConstants.EMPIRIA_MATH_GAP_SUBSUP_WIDTH)));
			} else {
				setGapWidth((int) (getGapWidth() * 0.7));
			}

			if (mathStyles.containsKey(EmpiriaStyleNameConstants.EMPIRIA_MATH_GAP_SUBSUP_HEIGHT)) {
				setGapHeight(NumberUtils.tryParseInt(mathStyles.get(EmpiriaStyleNameConstants.EMPIRIA_MATH_GAP_SUBSUP_HEIGHT)));
			} else {
				setGapHeight((int) (getGapWidth() * 0.7));
			}

			if (mathStyles.containsKey(EmpiriaStyleNameConstants.EMPIRIA_MATH_GAP_SUBSUP_FONT_SIZE)) {
				setGapFontSize(NumberUtils.tryParseInt(mathStyles.get(EmpiriaStyleNameConstants.EMPIRIA_MATH_GAP_SUBSUP_FONT_SIZE)));
			} else {
				setGapFontSize((int) (getGapWidth() * 0.7));
			}
		}
	}

	@Override
	protected void setCorrectAnswer() {
		String correctAnswer = getCorrectAnswer();
		String replaced = gapExpressionReplacer.ensureReplacement(correctAnswer);
		presenter.setText(replaced);
	}

	@Override
	protected void setPreviousAnswer() {
		presenter.setText(getCurrentResponseValue());
	}

	@Override
	public String getValue() {
		return presenter.getText();
	}

	public void setUpGap() {
		registerBindingContexts();
	}

	public void startGap() {
		setBindingValues();
	}

	@Override
	public String getDragItemId() {
		return presenter.getText();
	}

	@Override
	public void setDragItem(String itemId) {
		DataObject dataObject = sourcelistManager.getValue(itemId, getModuleId());
		presenter.setText(dataObject.getItemId());
	}

	@Override
	public void removeDragItem() {
		presenter.setText("");
	}

	@Override
	public void lockDragZone() {
		getTextEntryGapPresenter().lockDragZone();
	}

	@Override
	public void unlockDragZone() {
		getTextEntryGapPresenter().unlockDragZone();
	}

	TextEntryGapModulePresenter getTextEntryGapPresenter() {
		return (TextEntryGapModulePresenter)presenter;
	}
}
