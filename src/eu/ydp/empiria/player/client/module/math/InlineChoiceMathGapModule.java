package eu.ydp.empiria.player.client.module.math;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;

import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.user.client.ui.HasWidgets;
import com.google.gwt.user.client.ui.InlineHTML;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.xml.client.Element;
import com.google.gwt.xml.client.NodeList;
import com.google.inject.Inject;

import eu.ydp.empiria.player.client.gin.factory.PageScopeFactory;
import eu.ydp.empiria.player.client.gin.scopes.page.PageScoped;
import eu.ydp.empiria.player.client.module.ModuleSocket;
import eu.ydp.empiria.player.client.module.ResponseSocket;
import eu.ydp.empiria.player.client.module.gap.GapBase;
import eu.ydp.empiria.player.client.resources.EmpiriaStyleNameConstants;
import eu.ydp.empiria.player.client.resources.EmpiriaTagConstants;
import eu.ydp.empiria.player.client.resources.StyleNameConstants;
import eu.ydp.empiria.player.client.style.StyleSocket;
import eu.ydp.empiria.player.client.util.events.bus.EventsBus;
import eu.ydp.empiria.player.client.util.events.player.PlayerEvent;
import eu.ydp.empiria.player.client.util.events.player.PlayerEventHandler;
import eu.ydp.empiria.player.client.util.events.player.PlayerEventTypes;
import eu.ydp.gwtutil.client.NumberUtils;
import eu.ydp.gwtutil.client.components.exlistbox.ExListBoxChangeListener;
import eu.ydp.gwtutil.client.components.exlistbox.IsExListBox;

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

	private ResponseSocket responseSocket;
	
	@Inject
	MathGapModel mathGapModel;
	
	@Inject
	public InlineChoiceMathGapModule(
			InlineChoiceMathGapModulePresenter presenter,
			@PageScoped ResponseSocket responseSocket) {
		this.presenter = presenter;
		this.responseSocket = responseSocket;

		getListBox().setChangeListener(new ExListBoxChangeListener() {
			@Override
			public void onChange() {
				updateResponse(true);
			}
		});
	}

	@PostConstruct
	public void postConstruct() {
		eventsBus.addHandler(PlayerEvent.getType(PlayerEventTypes.PAGE_SWIPE_STARTED), this, pageScopeFactory.getCurrentPageScope());
	}
	
	@Override
	protected ResponseSocket getResponseSocket() {
		return responseSocket;
	}

	protected IsExListBox getListBox() {
		return presenter.getListBox();
	}

	@Override
	public void installViews(List<HasWidgets> placeholders) {
		HasWidgets placeholder = placeholders.get(0);
		presenter.installViewInContainer(((HasWidgets) ((Widget) placeholder).getParent()));

		String uid = getElementAttributeValue(EmpiriaTagConstants.ATTR_UID);
		mathGapModel.setUid(uid);

		Map<String, String> styles = styleSocket.getStyles(getModuleElement());
		mathGapModel.getMathStyles().putAll(styles);
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

	protected void initStyles() {
		if (mathGapModel.getMathStyles().containsKey(EmpiriaStyleNameConstants.EMPIRIA_MATH_DROP_WIDTH)) {
			presenter.setWidth(NumberUtils.tryParseInt(mathGapModel.getMathStyles().get(EmpiriaStyleNameConstants.EMPIRIA_MATH_DROP_WIDTH)), Unit.PX);
		}

		if (mathGapModel.getMathStyles().containsKey(EmpiriaStyleNameConstants.EMPIRIA_MATH_DROP_HEIGHT)) {
			presenter.setHeight(NumberUtils.tryParseInt(mathGapModel.getMathStyles().get(EmpiriaStyleNameConstants.EMPIRIA_MATH_DROP_HEIGHT)), Unit.PX);
		}

		if (mathGapModel.getMathStyles().containsKey(EmpiriaStyleNameConstants.EMPIRIA_MATH_INLINECHOICE_EMPTY_OPTION)) {
			hasEmptyOption = mathGapModel.getMathStyles().get(EmpiriaStyleNameConstants.EMPIRIA_MATH_INLINECHOICE_EMPTY_OPTION).equalsIgnoreCase(
					EmpiriaStyleNameConstants.VALUE_SHOW);
		}
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
	public void reset() {
		getListBox().setSelectedIndex((hasEmptyOption) ? 0 : -1);
		updateResponse(true);
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
	public void onPlayerEvent(PlayerEvent event) {
		if (event.getType() == PlayerEventTypes.PAGE_SWIPE_STARTED) {
			getListBox().hidePopup();
		}
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

	
	private void updateResponse(boolean userInteract) {
		updateResponse(userInteract, false);
	}

	@Override
	public String getUid() {
		return mathGapModel.getUid();
	}

	@Override
	public void setIndex(int index) {
		mathGapModel.getUid();
	}
}
