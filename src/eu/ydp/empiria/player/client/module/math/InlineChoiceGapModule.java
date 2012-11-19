package eu.ydp.empiria.player.client.module.math;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.user.client.ui.HasWidgets;
import com.google.gwt.user.client.ui.InlineHTML;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.xml.client.Element;
import com.google.gwt.xml.client.NodeList;
import com.google.inject.Inject;

import eu.ydp.empiria.player.client.components.ExListBox;
import eu.ydp.empiria.player.client.components.ExListBoxChangeListener;
import eu.ydp.empiria.player.client.controller.variables.objects.response.Response;
import eu.ydp.empiria.player.client.controller.variables.objects.response.ResponseValue;
import eu.ydp.empiria.player.client.gin.factory.ModuleFactory;
import eu.ydp.empiria.player.client.module.Factory;
import eu.ydp.empiria.player.client.module.IModule;
import eu.ydp.empiria.player.client.module.ModuleSocket;
import eu.ydp.empiria.player.client.module.gap.GapBase;
import eu.ydp.empiria.player.client.resources.EmpiriaStyleNameConstants;
import eu.ydp.empiria.player.client.resources.EmpiriaTagConstants;
import eu.ydp.empiria.player.client.resources.StyleNameConstants;
import eu.ydp.gwtutil.client.NumberUtils;

public class InlineChoiceGapModule extends GapBase implements MathGap, Factory<InlineChoiceGapModule> {

	protected String uid;

	protected int index;

	protected List<String> options;

	protected MathModule parentMathModule;

	protected Map<String, String> styles;

	protected Map<String, String> mathStyles;

	protected boolean hasEmptyOption = false;

	@Inject
	protected StyleNameConstants styleNames;

	@Inject
	protected ModuleFactory moduleFactory;

	@Inject
	public InlineChoiceGapModule(InlineChoiceGapModulePresenter presenter) {
		this.presenter = presenter;
		getListBox().setChangeListener(new ExListBoxChangeListener() {
			@Override
			public void onChange() {
				updateResponse();
			}
		});
	}


	protected ExListBox getListBox() {
		return presenter.getListBox();
	}

	@Override
	public void installViews(List<HasWidgets> placeholders) {
		HasWidgets placeholder = placeholders.get(0);
		presenter.installViewInContainer(((HasWidgets) ((Widget) placeholder).getParent()));

		loadElementProperties();

		styles = getModuleSocket().getStyles(getModuleElement());
		initStyles();

		setListBoxEmptyOption();
		options = createOptions(getModuleElement(), getModuleSocket());
	}

	protected void setListBoxEmptyOption() {
		if (hasEmptyOption) {
			Widget emptyOptionInBody = new InlineHTML(GapBase.INLINE_HTML_NBSP);
			emptyOptionInBody.setStyleName(styleNames.QP_MATH_CHOICE_POPUP_OPTION_EMPTY());
			Widget emptyOptionInPopup = new InlineHTML(GapBase.INLINE_HTML_NBSP);
			emptyOptionInPopup.setStyleName(styleNames.QP_MATH_CHOICE_POPUP_OPTION_EMPTY());
			getListBox().addOption(emptyOptionInBody, emptyOptionInPopup);
			getListBox().setSelectedIndex(0);
		} else {
			getListBox().setSelectedIndex(-1);
		}
	}

	List<String> createOptions(Element moduleElement, ModuleSocket moduleSocket){
		NodeList optionNodes = moduleElement.getElementsByTagName(EmpiriaTagConstants.NAME_INLINE_CHOICE);
		List<String> options = new ArrayList<String>();

		for (int o = 0; o < optionNodes.getLength(); o++) {
			String currId = ((Element) optionNodes.item(o)).getAttribute(EmpiriaTagConstants.ATTR_IDENTIFIER);
			options.add(currId);
			Widget baseBody = moduleSocket.getInlineBodyGeneratorSocket().generateInlineBody(optionNodes.item(o));
			Widget popupBody = moduleSocket.getInlineBodyGeneratorSocket().generateInlineBody(optionNodes.item(o));
			getListBox().addOption(baseBody, popupBody);
		}

		return options;
	}

	protected void initStyles() {
		if (styles.containsKey(EmpiriaStyleNameConstants.EMPIRIA_MATH_DROP_WIDTH)) {
			presenter.setWidth(NumberUtils.tryParseInt(styles.get(EmpiriaStyleNameConstants.EMPIRIA_MATH_DROP_WIDTH)), Unit.PX);
		} else if (mathStyles.containsKey(EmpiriaStyleNameConstants.EMPIRIA_MATH_DROP_WIDTH)) {
			presenter.setWidth(NumberUtils.tryParseInt(mathStyles.get(EmpiriaStyleNameConstants.EMPIRIA_MATH_DROP_WIDTH)), Unit.PX);
		}

		if (styles.containsKey(EmpiriaStyleNameConstants.EMPIRIA_MATH_DROP_HEIGHT)) {
			presenter.setHeight(NumberUtils.tryParseInt(styles.get(EmpiriaStyleNameConstants.EMPIRIA_MATH_DROP_HEIGHT)), Unit.PX);
		} else if (mathStyles.containsKey(EmpiriaStyleNameConstants.EMPIRIA_MATH_DROP_HEIGHT)) {
			presenter.setHeight(NumberUtils.tryParseInt(mathStyles.get(EmpiriaStyleNameConstants.EMPIRIA_MATH_DROP_HEIGHT)), Unit.PX);
		}

		if (mathStyles.containsKey(EmpiriaStyleNameConstants.EMPIRIA_MATH_INLINECHOICE_EMPTY_OPTION)) {
			hasEmptyOption = mathStyles.get(EmpiriaStyleNameConstants.EMPIRIA_MATH_INLINECHOICE_EMPTY_OPTION).equalsIgnoreCase(EmpiriaStyleNameConstants.VALUE_SHOW);
		}
	}

	@Override
	public String getValue() {
		String value = "";
		int valueIndex = indexViewToInternal();
		if (valueIndex >= 0) {
			value = options.get(valueIndex);
		}
		return value;
	}

	private int indexViewToInternal() {
		int valueIndex = getListBox().getSelectedIndex();
		if (hasEmptyOption) {
			valueIndex--;
		}
		return valueIndex;
	}

	public void setValue(String valueIdentifier) {
		getListBox().setSelectedIndex(indexInternalToView(valueIdentifier));
	}

	private int indexInternalToView(String valueIdentifier) {
		int valueIndex = options.indexOf(valueIdentifier);
		if (hasEmptyOption) {
			valueIndex++;
		}
		return valueIndex;
	}

	@Override
	protected Response findResponse() {
		return (getParentMathModule() == null) ? null : getParentMathModule().getResponse();
	}

	@Override
	public Widget getContainer() {
		return (Widget) presenter.getContainer();
	}

	private void loadElementProperties() {
		uid = getElementAttributeValue(EmpiriaTagConstants.ATTR_UID);
	}

	@Override
	public String getUid() {
		return (uid == null) ? EMPTY_STRING : uid;
	}

	@Override
	public void reset() {
		getListBox().setSelectedIndex((hasEmptyOption) ? 0 : -1);
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
	public void setGapWidth(int gapWidth) {
		presenter.setWidth(gapWidth, Unit.PX);
	}

	@Override
	public void setGapHeight(int gapHeight) {
		presenter.setHeight(gapHeight, Unit.PX);
	}

	@Override
	public void setGapFontSize(int gapFontSize) {
		presenter.setFontSize(gapFontSize, Unit.PX);
	}

	@Override
	public void setMathStyles(Map<String, String> mathStyles) {
		this.mathStyles = mathStyles;
	}

	@Override
	protected boolean isResponseCorrect() {
		List<Boolean> evaluations = getModuleSocket().evaluateResponse(getResponse());
		return (evaluations.size() <= index + 1) ? evaluations.get(index) : false;
	}

	@Override
	protected String getCurrentResponseValue() {
		return getResponse().values.get(index);
	}

	@Override
	public String getCorrectAnswer() {
		List<String> correctAnswers = getCorrectResponseValue().getAnswers();
		return correctAnswers.get(0);
	}

	@Override
	protected void setCorrectAnswer() {
		setValue(getCorrectAnswer());
	}

	@Override
	protected void setPreviousAnswer() {
		setValue(getCurrentResponseValue());
	}

	private ResponseValue getCorrectResponseValue(){
		return getResponse().correctAnswers.getResponseValue(index);
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
	protected void updateResponse() {
		getParentMathModule().updateResponseAfterUserAction();
	}

	@Override
	public InlineChoiceGapModule getNewInstance() {
		return moduleFactory.getInlineChoiceGapModule();
	}

	@Override
	public void setUpGap() {
	}

	@Override
	public void startGap() {
	}
}
