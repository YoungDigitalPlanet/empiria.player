package eu.ydp.empiria.player.client.module.inlinechoice.math;

import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.json.client.JSONArray;
import com.google.gwt.user.client.ui.*;
import com.google.gwt.xml.client.*;
import com.google.inject.Inject;
import eu.ydp.empiria.player.client.gin.factory.PageScopeFactory;
import eu.ydp.empiria.player.client.gin.scopes.page.PageScoped;
import eu.ydp.empiria.player.client.module.*;
import eu.ydp.empiria.player.client.module.gap.GapBase;
import eu.ydp.empiria.player.client.module.math.*;
import eu.ydp.empiria.player.client.resources.*;
import eu.ydp.empiria.player.client.style.StyleSocket;
import eu.ydp.empiria.player.client.util.events.bus.EventsBus;
import eu.ydp.empiria.player.client.util.events.player.*;
import eu.ydp.gwtutil.client.NumberUtils;
import eu.ydp.gwtutil.client.components.exlistbox.*;
import eu.ydp.gwtutil.client.gin.scopes.module.ModuleScoped;
import java.util.*;
import javax.annotation.PostConstruct;

public class InlineChoiceMathGapModule extends GapBase implements MathGap, PlayerEventHandler {

	protected List<String> options;

	protected boolean hasEmptyOption = false;

	@Inject
	protected StyleNameConstants styleNames;

	@Inject
	protected EventsBus eventsBus;

	@Inject
	protected PageScopeFactory pageScopeFactory;
	@Inject
	private StyleSocket styleSocket;

	@Inject
	@PageScoped
	private ResponseSocket responseSocket;

	@Inject
	@ModuleScoped
	MathGapModel mathGapModel;

	@Inject
	private InlineChoiceMathGapModulePresenter inlineChoicePresenter;

	@PostConstruct
	public void postConstruct() {
		this.presenter = inlineChoicePresenter;

		getListBox().setChangeListener(new ExListBoxChangeListener() {
			@Override
			public void onChange() {
				updateResponse(true);
			}
		});
		eventsBus.addHandler(PlayerEvent.getType(PlayerEventTypes.PAGE_SWIPE_STARTED), this, pageScopeFactory.getCurrentPageScope());
	}

	@Override
	protected ResponseSocket getResponseSocket() {
		return responseSocket;
	}

	protected IsExListBox getListBox() {
		return inlineChoicePresenter.getListBox();
	}

	@Override
	public void installViews(List<HasWidgets> placeholders) {
		HasWidgets placeholder = placeholders.get(0);
		presenter.installViewInContainer(((HasWidgets) ((Widget) placeholder).getParent()));

		String uid = getElementAttributeValue(EmpiriaTagConstants.ATTR_UID);
		mathGapModel.setUid(uid);

		Map<String, String> styles = styleSocket.getStyles(getModuleElement());
		mathGapModel.getMathStyles().putAll(styles);
		initParametersBasedOnMathStyles();

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

	List<String> createOptions(Element moduleElement, ModuleSocket moduleSocket) {
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

	protected void initParametersBasedOnMathStyles() {
		initDimensionBasedOnMathStyles();
		checkHasEmptyOptionBasedOnMathStyles();
	}

	private void checkHasEmptyOptionBasedOnMathStyles() {
		if (mathGapModel.containsStyle(EmpiriaStyleNameConstants.EMPIRIA_MATH_INLINECHOICE_EMPTY_OPTION)) {
			hasEmptyOption = mathGapModel.getStyle(EmpiriaStyleNameConstants.EMPIRIA_MATH_INLINECHOICE_EMPTY_OPTION).equalsIgnoreCase(
					EmpiriaStyleNameConstants.VALUE_SHOW);
		}
	}

	private void initDimensionBasedOnMathStyles() {
		if (mathGapModel.containsStyle(EmpiriaStyleNameConstants.EMPIRIA_MATH_DROP_WIDTH)) {
			presenter.setWidth(NumberUtils.tryParseInt(mathGapModel.getStyle(EmpiriaStyleNameConstants.EMPIRIA_MATH_DROP_WIDTH)), Unit.PX);
		}
		if (mathGapModel.containsStyle(EmpiriaStyleNameConstants.EMPIRIA_MATH_DROP_HEIGHT)) {
			presenter.setHeight(NumberUtils.tryParseInt(mathGapModel.getStyle(EmpiriaStyleNameConstants.EMPIRIA_MATH_DROP_HEIGHT)), Unit.PX);
		}
	}

	public void setValue(String valueIdentifier) {
		getListBox().setSelectedIndex(indexInternalToView(valueIdentifier));
	}

	@Override
	public void reset() {
		getListBox().setSelectedIndex((hasEmptyOption) ? 0 : -1);
		updateResponse(true);
	}

	@Override
	public void onPlayerEvent(PlayerEvent event) {
		if (event.getType() == PlayerEventTypes.PAGE_SWIPE_STARTED) {
			getListBox().hidePopup();
		}
	}

	@Override
	public Widget getContainer() {
		return (Widget) presenter.getContainer();
	}

	@Override
	public void setGapHeight(int gapHeight) {
		presenter.setHeight(gapHeight, Unit.PX);
	}

	public int getGapHeight() {
		return presenter.getOffsetHeight();
	}

	@Override
	public void setGapWidth(int gapWidth) {
		presenter.setWidth(gapWidth, Unit.PX);
	}

	public int getGapWidth() {
		return presenter.getOffsetWidth();
	}

	@Override
	public void setGapFontSize(int gapFontSize) {
		presenter.setFontSize(gapFontSize, Unit.PX);
	}

	@Override
	public void setMathStyles(Map<String, String> mathStyles) {
		mathGapModel.setMathStyles(mathStyles);
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
	public void setState(JSONArray newState) {
		super.setState(newState);
	}

	@Override
	protected void setCorrectAnswer() {
		setValue(getCorrectAnswer());
	}

	@Override
	protected void setPreviousAnswer() {
		setValue(getCurrentResponseValue());
	}

	@Override
	protected void updateResponse(boolean userInteract, boolean isReset) {
		if (showingAnswer) {
			return;
		}

		if (getResponse() != null) {
			if (lastValue != null) {
				getResponse().remove(lastValue);
			}

			String currentValue = getSelectedOption();
			getResponse().add(currentValue);
			lastValue = currentValue;

			fireStateChanged(userInteract, isReset);
		}
	}

	private String getSelectedOption() {
		int selectedOption = getListBox().getSelectedIndex() - 1;

		if (isNotEmptyOption(selectedOption)) {
			return options.get(selectedOption);
		} else {
			return "";
		}
	}

	private boolean isNotEmptyOption(int selectedIndex) {
		return selectedIndex > -1;
	}

	private void updateResponse(boolean userInteract) {
		updateResponse(userInteract, false);
	}

	private int indexInternalToView(String valueIdentifier) {
		int valueIndex = options.indexOf(valueIdentifier);
		if (hasEmptyOption) {
			valueIndex++;
		}
		return valueIndex;
	}
}