package eu.ydp.empiria.player.client.module.textentry;

import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.user.client.ui.HasWidgets;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import eu.ydp.empiria.player.client.module.dragdrop.SourcelistClient;
import eu.ydp.gwtutil.client.NumberUtils;
import eu.ydp.gwtutil.client.StringUtils;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Map;

import static eu.ydp.empiria.player.client.resources.EmpiriaStyleNameConstants.EMPIRIA_TEXTENTRY_GAP_FONT_SIZE;
import static eu.ydp.empiria.player.client.resources.EmpiriaStyleNameConstants.EMPIRIA_TEXTENTRY_GAP_WIDTH;

public class TextEntryGapModule extends TextEntryGapBase implements SourcelistClient {

	private static final String INITIAL_TEXT_ATTR = "initialText";

	protected Map<String, String> styles;

	@Inject
	TextEntryModulePresenter textEntryPresenter;
	protected Integer fontSize = 16;
	private String initialText;

	@PostConstruct
	@Override
	public void postConstruct() {
		this.presenter = textEntryPresenter;
		super.postConstruct();
	}

	@Override
	public void installViews(List<HasWidgets> placeholders) {
		styles = styleSocket.getStyles(getModuleElement());

		setFontSize(styles);
		setDimensions(styles);
		setMaxlengthBinding(styles, getModuleElement());
		setWidthBinding(styles, getModuleElement());

		installViewPanel(placeholders.get(0));

		initReplacements(styles);
		parseInitialText();
		fillGapWithInitial();
	}

	private void parseInitialText() {
		String initialTextAttr = getElementAttributeValue(INITIAL_TEXT_ATTR);
		initialText = (initialTextAttr != null ? initialTextAttr : StringUtils.EMPTY_STRING);
	}

	private void fillGapWithInitial() {
		textEntryPresenter.setText(initialText);
	}

	@Override
	public void reset() {
		super.reset();
		fillGapWithInitial();
	}

	@Override
	protected void setPreviousAnswer() {
		presenter.setText(preparePreviousAnswer());
	}

	public String preparePreviousAnswer() {
		String valueFormResponse = getCurrentResponseValue();
		return valueFormResponse.isEmpty() ? initialText : valueFormResponse;
	}

	@Override
	public void onSetUp() {
		updateResponse(false);
		registerBindingContexts();
	}

	protected void setFontSize(Map<String, String> styles) {
		if (styles.containsKey(EMPIRIA_TEXTENTRY_GAP_FONT_SIZE)) {
			fontSize = NumberUtils.tryParseInt(styles.get(EMPIRIA_TEXTENTRY_GAP_FONT_SIZE), null);
		}

		presenter.setFontSize(fontSize, Unit.PX);
	}

	protected void setDimensions(Map<String, String> styles) {
		if (styles.containsKey(EMPIRIA_TEXTENTRY_GAP_WIDTH)) {
			Integer gapWidth = NumberUtils.tryParseInt(styles.get(EMPIRIA_TEXTENTRY_GAP_WIDTH), null);
			presenter.setWidth(gapWidth, Unit.PX);
		}
	}

	private void installViewPanel(HasWidgets placeholder) {
		applyIdAndClassToView((Widget) presenter.getContainer());
		presenter.installViewInContainer(placeholder);
	}

	@Override
	public boolean isIgnored() {
		return "true".equals(getElementAttributeValue(IGNORED_ATTR));
	}
}