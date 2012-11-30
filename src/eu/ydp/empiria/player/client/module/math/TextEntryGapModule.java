package eu.ydp.empiria.player.client.module.math;

import java.util.List;
import java.util.Map;

import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.event.dom.client.BlurEvent;
import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.user.client.ui.HasWidgets;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.xml.client.Element;
import com.google.gwt.xml.client.Node;
import com.google.inject.Inject;
import com.google.inject.Provider;

import eu.ydp.empiria.player.client.controller.variables.objects.response.Response;
import eu.ydp.empiria.player.client.controller.variables.objects.response.ResponseValue;
import eu.ydp.empiria.player.client.gin.factory.TextEntryModuleFactory;
import eu.ydp.empiria.player.client.module.Factory;
import eu.ydp.empiria.player.client.module.IModule;
import eu.ydp.empiria.player.client.module.ModuleTagName;
import eu.ydp.empiria.player.client.module.gap.GapBase;
import eu.ydp.empiria.player.client.resources.EmpiriaStyleNameConstants;
import eu.ydp.empiria.player.client.resources.EmpiriaTagConstants;
import eu.ydp.gwtutil.client.NumberUtils;
import eu.ydp.gwtutil.client.xml.XMLUtils;

public class TextEntryGapModule extends GapBase implements MathGap, Factory<TextEntryGapModule> {

	protected String uid;

	protected int index;

	protected MathModule parentMathModule;

	protected Map<String, String> styles;

	protected Map<String, String> mathStyles;

	@Inject
	private Provider<TextEntryGapModule> moduleFactory;

	@Inject
	public TextEntryGapModule(TextEntryModuleFactory moduleFactory){
		this.presenter = moduleFactory.getTextEntryGapModulePresenter(this);
		presenter.addPresenterHandler(new PresenterHandler() {
			@Override
			public void onChange(ChangeEvent event) {
				updateResponse();
			}

			@Override
			public void onBlur(BlurEvent event) {
				updateResponse();
			}
		});

	}

	@Override
	public void installViews(List<HasWidgets> placeholders) {
		HasWidgets placeholder = placeholders.get(0);
		presenter.installViewInContainer(((HasWidgets) ((Widget) placeholder).getParent()));
		addPlayerEventHandlers();
		loadElementProperties();
		styles = getModuleSocket().getStyles(getModuleElement());

		setDimensions();
		setMaxlengthBinding(mathStyles, getModuleElement());
		setWidthBinding(mathStyles, getModuleElement());
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
		return node.getNodeName().equals("msub")
				|| node.getNodeName().equals("msup")
				|| node.getNodeName().equals("msubsup")
				|| node.getNodeName().equals("mmultiscripts");
	}

	protected boolean isMathInteractionNode(Node node) {
		return node.getNodeName().equals(ModuleTagName.MATH_INTERACTION.tagName());
	}

	protected void setDimensions() {
		if (styles.containsKey(EmpiriaStyleNameConstants.EMPIRIA_MATH_GAP_WIDTH)) {
			setGapWidth(NumberUtils.tryParseInt(styles.get(EmpiriaStyleNameConstants.EMPIRIA_MATH_GAP_WIDTH)));
		} else if (mathStyles.containsKey(EmpiriaStyleNameConstants.EMPIRIA_MATH_GAP_WIDTH)) {
			setGapWidth(NumberUtils.tryParseInt(mathStyles.get(EmpiriaStyleNameConstants.EMPIRIA_MATH_GAP_WIDTH)));
		}

		if (styles.containsKey(EmpiriaStyleNameConstants.EMPIRIA_MATH_GAP_HEIGHT)) {
			setGapHeight(NumberUtils.tryParseInt(styles.get(EmpiriaStyleNameConstants.EMPIRIA_MATH_GAP_HEIGHT)));
		} else if (mathStyles.containsKey(EmpiriaStyleNameConstants.EMPIRIA_MATH_GAP_HEIGHT)) {
			setGapHeight(NumberUtils.tryParseInt(mathStyles.get(EmpiriaStyleNameConstants.EMPIRIA_MATH_GAP_HEIGHT)));
		}

		if (styles.containsKey(EmpiriaStyleNameConstants.EMPIRIA_MATH_GAP_FONT_SIZE)) {
			setGapFontSize(NumberUtils.tryParseInt(styles.get(EmpiriaStyleNameConstants.EMPIRIA_MATH_GAP_FONT_SIZE)));
		} else if (mathStyles.containsKey(EmpiriaStyleNameConstants.EMPIRIA_MATH_GAP_FONT_SIZE)) {
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
	public void setGapWidth(int gapWidth) {
		presenter.setWidth(gapWidth, Unit.PX);
	}

	public int getGapWidth() {
		return presenter.getOffsetWidth();
	}

	@Override
	public void setGapHeight(int gapHeight) {
		presenter.setHeight(gapHeight, Unit.PX);
	}

	public int getGapHeight() {
		return presenter.getOffsetHeight();
	}

	@Override
	public void setGapFontSize(int gapFontSize) {
		presenter.setFontSize(gapFontSize, Unit.PX);
	}

	private void loadElementProperties(){
		uid = getElementAttributeValue(EmpiriaTagConstants.ATTR_UID);
	}

	@Override
	protected Response findResponse() {
		return (getParentMathModule() == null) ? null : getParentMathModule().getResponse();
	}

	private MathModule getParentMathModule() {
		if (parentMathModule == null) {
			IModule parent = this;

			do {
				parent = getModuleSocket().getParent(parent);
			} while ( !(parent instanceof MathModule) );

			parentMathModule = (parent == null) ? null : (MathModule)parent;
		}

		return parentMathModule;
	}

	@Override
	public String getUid(){
		return (uid == null) ? EMPTY_STRING : uid;
	}

	@Override
	public String getCorrectAnswer() {
		List<String> correctAnswers = getCorrectResponseValue().getAnswers();
		return correctAnswers.get(0);
	}

	@Override
	protected void setCorrectAnswer() {
		presenter.setText(getCorrectAnswer());
	}

	@Override
	protected void setPreviousAnswer() {
		presenter.setText(getCurrentResponseValue());
	}

	private ResponseValue getCorrectResponseValue(){
		return getResponse().correctAnswers.getResponseValue(index);
	}

	@Override
	protected String getCurrentResponseValue() {
		return getResponse().values.get(index);
	}
	
	protected List<Boolean> getEvaluatedResponse() {
		return getModuleSocket().evaluateResponse(getResponse());
	}

	@Override
	protected boolean isResponseCorrect() {
		boolean isCorrect = false;
		List<Boolean> evaluations = getEvaluatedResponse();
		
		if (index < evaluations.size()) {
			isCorrect = evaluations.get(index);
		}
		
		return isCorrect;
	}
	
	@Override
	protected void updateResponse() {
		getParentMathModule().updateResponseAfterUserAction();
	}

	@Override
	public String getValue() {
		return presenter.getText();
	}

	@Override
	public void setIndex(int index) {
		this.index = index;
	}

	@Override
	public int getIndex() {
		return index;
	}

	@Override
	public Widget getContainer() {
		return (Widget) presenter.getContainer();
	}

	@Override
	public void setMathStyles(Map<String, String> mathStyles) {
		this.mathStyles = mathStyles;
	}

	@Override
	public TextEntryGapModule getNewInstance() {
		return moduleFactory.get();
	}

	@Override
	public void setUpGap() {
		registerBindingContexts();
	}

	@Override
	public void startGap() {
		setBindingValues();
	}
}
