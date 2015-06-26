package eu.ydp.empiria.player.client.module.textentry.math;

import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.user.client.ui.HasWidgets;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import eu.ydp.empiria.player.client.module.dragdrop.SourcelistClient;
import eu.ydp.empiria.player.client.module.math.MathGap;
import eu.ydp.empiria.player.client.module.math.MathGapModel;
import eu.ydp.empiria.player.client.module.mathjax.interaction.MathJaxGapContainer;
import eu.ydp.empiria.player.client.module.textentry.TextEntryGapBase;
import eu.ydp.empiria.player.client.resources.EmpiriaStyleNameConstants;
import eu.ydp.empiria.player.client.resources.EmpiriaTagConstants;
import eu.ydp.gwtutil.client.NumberUtils;
import eu.ydp.gwtutil.client.gin.scopes.module.ModuleScoped;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Map;

public class TextEntryMathGapModule extends TextEntryGapBase implements MathGap, SourcelistClient {

	@Inject
	@ModuleScoped
	MathGapModel mathGapModel;

	@Inject
	TextEntryMathGapModulePresenter textEntryPresenter;
	@Inject
	private MathJaxGapContainer gapContainer;

	@Inject
	MathSubAndSupUtil subAndSupUtil;

	@PostConstruct
	@Override
	public void postConstruct() {
		this.presenter = textEntryPresenter;
		super.postConstruct();
	}

	@Override
	public void installViews(List<HasWidgets> placeholders) {
		installViewInPlaceholder(placeholders.get(0));

		String uid = getElementAttributeValue(EmpiriaTagConstants.ATTR_UID);
		mathGapModel.setUid(uid);

		applyIdAndClassToView((Widget) presenter.getContainer());
		initStyles();
		gapContainer.addMathGap(this);
	}

	@Override
	public void onSetUp() {
		super.onSetUp();
		registerBindingContexts();
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

	protected void setDimensions() {
		if (mathGapModel.containsStyle(EmpiriaStyleNameConstants.EMPIRIA_MATH_GAP_WIDTH)) {
			setGapWidth(NumberUtils.tryParseInt(mathGapModel.getStyle(EmpiriaStyleNameConstants.EMPIRIA_MATH_GAP_WIDTH)));
		}

		if (mathGapModel.containsStyle(EmpiriaStyleNameConstants.EMPIRIA_MATH_GAP_HEIGHT)) {
			setGapHeight(NumberUtils.tryParseInt(mathGapModel.getStyle(EmpiriaStyleNameConstants.EMPIRIA_MATH_GAP_HEIGHT)));
		}

		if (mathGapModel.containsStyle(EmpiriaStyleNameConstants.EMPIRIA_MATH_GAP_FONT_SIZE)) {
			setGapFontSize(NumberUtils.tryParseInt(mathGapModel.getStyle(EmpiriaStyleNameConstants.EMPIRIA_MATH_GAP_FONT_SIZE)));
		}

		if (subAndSupUtil.isSubOrSup(getModuleElement(), getModuleElement().getParentNode())) {
			if (mathGapModel.containsStyle(EmpiriaStyleNameConstants.EMPIRIA_MATH_GAP_SUBSUP_WIDTH)) {
				setGapWidth(NumberUtils.tryParseInt(mathGapModel.getStyle(EmpiriaStyleNameConstants.EMPIRIA_MATH_GAP_SUBSUP_WIDTH)));
			} else {
				setGapWidth((int) (getGapWidth() * 0.7));
			}

			if (mathGapModel.containsStyle(EmpiriaStyleNameConstants.EMPIRIA_MATH_GAP_SUBSUP_HEIGHT)) {
				setGapHeight(NumberUtils.tryParseInt(mathGapModel.getStyle(EmpiriaStyleNameConstants.EMPIRIA_MATH_GAP_SUBSUP_HEIGHT)));
			} else {
				setGapHeight((int) (getGapWidth() * 0.7));
			}

			if (mathGapModel.containsStyle(EmpiriaStyleNameConstants.EMPIRIA_MATH_GAP_SUBSUP_FONT_SIZE)) {
				setGapFontSize(NumberUtils.tryParseInt(mathGapModel.getStyle(EmpiriaStyleNameConstants.EMPIRIA_MATH_GAP_SUBSUP_FONT_SIZE)));
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
