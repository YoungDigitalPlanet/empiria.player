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

import eu.ydp.empiria.player.client.gin.scopes.page.PageScoped;
import eu.ydp.empiria.player.client.module.ModuleTagName;
import eu.ydp.empiria.player.client.module.dragdrop.SourcelistClient;
import eu.ydp.empiria.player.client.module.dragdrop.SourcelistItemValue;
import eu.ydp.empiria.player.client.module.dragdrop.SourcelistManager;
import eu.ydp.empiria.player.client.module.gap.GapDropHandler;
import eu.ydp.empiria.player.client.resources.EmpiriaStyleNameConstants;
import eu.ydp.empiria.player.client.resources.EmpiriaTagConstants;
import eu.ydp.empiria.player.client.style.StyleSocket;
import eu.ydp.empiria.player.client.util.dom.drag.DragDataObject;
import eu.ydp.gwtutil.client.NumberUtils;
import eu.ydp.gwtutil.client.xml.XMLUtils;

public class TextEntryGapModule extends MathGapBase implements MathGap, SourcelistClient {


	private final SourcelistManager sourcelistManager;

	private final StyleSocket styleSocket;

	@Inject
	public TextEntryGapModule(TextEntryGapModulePresenter presenter, StyleSocket styleSocket,@PageScoped final SourcelistManager sourcelistManager) {
		this.styleSocket = styleSocket;
		this.sourcelistManager = sourcelistManager;
		
		this.presenter = presenter;
		presenter.addPresenterHandler(new PresenterHandler() {
			@Override
			public void onChange(ChangeEvent event) {
				sourcelistManager.onUserValueChanged();
				updateResponse(true);
			}

			@Override
			public void onBlur(BlurEvent event) {
				if (isMobileUserAgent()) {
					sourcelistManager.onUserValueChanged();
					updateResponse(true);
				}
			}
		});
		presenter.addDomHandlerOnObjectDrop(new GapDropHandler() {

			@Override
			public void onDrop(DragDataObject dragDataObject) {
				String itemID = dragDataObject.getItemId();
				String sourceModuleId = dragDataObject.getSourceId();;
				String targetModuleId = getIdentifier();

				sourcelistManager.dragEnd(itemID, sourceModuleId,
						targetModuleId);
			}
		});
		
		sourcelistManager.registerModule(this);
	}

	@Override
	public void reset() {
		super.reset();
		sourcelistManager.onUserValueChanged();
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
		SourcelistItemValue itemValue = sourcelistManager.getValue(itemId, getIdentifier());
		presenter.setText(itemValue.getContent()); // TODO YPUB-5441 use factory to get value as string
	}

	@Override
	public void removeDragItem() {
		presenter.setText("");
	}

	private TextEntryGapModulePresenter getTextEntryGapPresenter() {
		return (TextEntryGapModulePresenter) presenter;
	}

	@Override
	public void lockDropZone() {
		getTextEntryGapPresenter().lockDragZone();
	}

	@Override
	public void unlockDropZone() {
		getTextEntryGapPresenter().unlockDragZone();
	}

	@Override
	public void setSize(HasDimensions size) {
		// intentionally empty - text gap does not fit its size
	}
}
