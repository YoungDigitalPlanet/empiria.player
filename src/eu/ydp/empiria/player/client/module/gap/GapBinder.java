package eu.ydp.empiria.player.client.module.gap;

import static eu.ydp.empiria.player.client.resources.EmpiriaStyleNameConstants.EMPIRIA_MATH_GAP_MAXLENGTH;
import static eu.ydp.empiria.player.client.resources.EmpiriaStyleNameConstants.EMPIRIA_MATH_GAP_WIDTH_ALIGN;
import static eu.ydp.empiria.player.client.resources.EmpiriaStyleNameConstants.EMPIRIA_TEXTENTRY_GAP_MAXLENGTH;
import static eu.ydp.empiria.player.client.resources.EmpiriaStyleNameConstants.EMPIRIA_TEXTENTRY_GAP_WIDTH_ALIGN;

import java.util.Map;

import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.xml.client.Element;

import eu.ydp.empiria.player.client.controller.variables.objects.response.CorrectAnswers;
import eu.ydp.empiria.player.client.module.binding.Bindable;
import eu.ydp.empiria.player.client.module.binding.BindingContext;
import eu.ydp.empiria.player.client.module.binding.BindingGroupIdentifier;
import eu.ydp.empiria.player.client.module.binding.BindingType;
import eu.ydp.empiria.player.client.module.binding.BindingUtil;
import eu.ydp.empiria.player.client.module.binding.BindingValue;
import eu.ydp.empiria.player.client.module.binding.DefaultBindingGroupIdentifier;
import eu.ydp.empiria.player.client.module.binding.gapmaxlength.GapMaxlengthBindingContext;
import eu.ydp.empiria.player.client.module.binding.gapmaxlength.GapMaxlengthBindingValue;
import eu.ydp.empiria.player.client.module.binding.gapwidth.GapWidthBindingContext;
import eu.ydp.empiria.player.client.module.binding.gapwidth.GapWidthBindingValue;
import eu.ydp.empiria.player.client.module.binding.gapwidth.GapWidthMode;
import eu.ydp.empiria.player.client.resources.EmpiriaTagConstants;
import eu.ydp.gwtutil.client.StringUtils;
import eu.ydp.gwtutil.client.xml.XMLUtils;

public class GapBinder implements Bindable {
	
	protected DefaultBindingGroupIdentifier widthBindingIdentifier;
	protected DefaultBindingGroupIdentifier maxlengthBindingIdentifier;
	protected BindingContext widthBindingContext;
	protected BindingContext maxlengthBindingContext;
	protected String maxLength = StringUtils.EMPTY_STRING;
	protected GapBase gapBase;
	
	public void setGapBase(GapBase gapBase) {
		this.gapBase = gapBase;
	}
	
	public int getLongestAnswerLength() {
		int longestLength = 0;

		CorrectAnswers correctAnswers = gapBase.getModuleResponse().correctAnswers;
		for (int i = 0; i < correctAnswers.getAnswersCount(); i++) {
			for (String a : correctAnswers.getResponseValue(i).getAnswers()){
				if (a.length() > longestLength) {
					longestLength = a.length();
				}
			}
		}

		return longestLength;
	}

	public BindingGroupIdentifier getBindingGroupIdentifier(BindingType bindingType) {
		BindingGroupIdentifier groupIndentifier = null;

		if (bindingType == BindingType.GAP_WIDTHS){
			groupIndentifier = widthBindingIdentifier;
		}

		if (bindingType == BindingType.GAP_MAXLENGHTS) {
			groupIndentifier = maxlengthBindingIdentifier;
		}

		return groupIndentifier;
	}

	public BindingValue getBindingValue(BindingType bindingType) {
		BindingValue bindingValue = null;

		if (bindingType == BindingType.GAP_WIDTHS) {
			bindingValue = new GapWidthBindingValue(getLongestAnswerLength());
		}

		if (bindingType == BindingType.GAP_MAXLENGHTS) {
			bindingValue = new GapMaxlengthBindingValue(getLongestAnswerLength());
		}

		return bindingValue;
	}
	
	protected void setWidthBinding(Map<String, String> styles, Element moduleElement) {
		GapWidthMode widthMode = getGapWidthMode(styles);

		if (widthMode == GapWidthMode.GROUP){
			widthBindingIdentifier = getBindindIdentifier(moduleElement);
		} else if (widthMode == GapWidthMode.GAP){
			if (gapBase.getModuleResponse().groups.size() > 0) {
				widthBindingIdentifier = new DefaultBindingGroupIdentifier(gapBase.getModuleResponse().groups.get(0));
			} else {
				int longestAnswer = getLongestAnswerLength();
				gapBase.presenter.setWidth((longestAnswer * gapBase.getFontSize()), Unit.PX);
			}
		}
	}

	protected GapWidthMode getGapWidthMode(Map<String, String> styles){
		GapWidthMode widthMode = null;
		String gapWidthAlign = StringUtils.EMPTY_STRING;

		if (styles.containsKey(EMPIRIA_TEXTENTRY_GAP_WIDTH_ALIGN)) {
			gapWidthAlign = styles.get(EMPIRIA_TEXTENTRY_GAP_WIDTH_ALIGN).trim().toUpperCase();
		} else if (styles.containsKey(EMPIRIA_MATH_GAP_WIDTH_ALIGN)) {
			gapWidthAlign = styles.get(EMPIRIA_MATH_GAP_WIDTH_ALIGN).trim().toUpperCase();
		}

		if ( !gapWidthAlign.equals(StringUtils.EMPTY_STRING) ) {
			widthMode = GapWidthMode.valueOf(gapWidthAlign);
		}

		return widthMode;
	}

	protected DefaultBindingGroupIdentifier getBindindIdentifier(Element moduleElement) {
		DefaultBindingGroupIdentifier bindingIdentifier;

		if (moduleElement.hasAttribute(EmpiriaTagConstants.ATTR_WIDTH_BINDING_GROUP)){
			bindingIdentifier = new DefaultBindingGroupIdentifier(moduleElement.getAttribute(EmpiriaTagConstants.ATTR_WIDTH_BINDING_GROUP));
		} else {
			bindingIdentifier = new DefaultBindingGroupIdentifier(StringUtils.EMPTY_STRING);
		}

		return bindingIdentifier;
	}

	protected void setMaxlengthBinding(Map<String, String> styles, Element moduleElement) {
		String gapMaxlength = StringUtils.EMPTY_STRING;

		if (styles.containsKey(EMPIRIA_TEXTENTRY_GAP_MAXLENGTH)) {
			gapMaxlength = styles.get(EMPIRIA_TEXTENTRY_GAP_MAXLENGTH).trim().toUpperCase();
		} else if (styles.containsKey(EMPIRIA_MATH_GAP_MAXLENGTH)) {
			gapMaxlength = styles.get(EMPIRIA_MATH_GAP_MAXLENGTH).trim().toUpperCase();
		}

		if ( !gapMaxlength.equals(StringUtils.EMPTY_STRING) ) {
			maxLength = gapMaxlength;

			if (maxLength.matches("ANSWER")) {
				maxlengthBindingIdentifier = getBindindIdentifier(moduleElement);
			} else {
				gapBase.presenter.setMaxLength(Integer.parseInt(maxLength));
			}
		}
		else if (gapBase.getModuleElement().hasAttribute("expectedLength")) {
			gapBase.presenter.setMaxLength(XMLUtils.getAttributeAsInt(gapBase.getModuleElement(), "expectedLength"));
		}
	}

	protected void registerBindingContexts() {
		if (widthBindingIdentifier != null) {
			widthBindingContext = BindingUtil.register(BindingType.GAP_WIDTHS, gapBase, gapBase);
		}

		if (maxlengthBindingIdentifier != null) {
			maxlengthBindingContext = BindingUtil.register(BindingType.GAP_MAXLENGHTS, gapBase, gapBase);
		}
	}

	protected void setBindingValues() {
		if (widthBindingContext != null) {
			int longestAnswerLength = ((GapWidthBindingContext) widthBindingContext).getGapWidthBindingOutcomeValue().getGapCharactersCount();
			int textBoxWidth = calculateTextBoxWidth(longestAnswerLength);
			gapBase.presenter.setWidth(textBoxWidth, Unit.PX);
		}

		if (maxlengthBindingContext != null) {
			int longestAnswerLength = ((GapMaxlengthBindingContext) maxlengthBindingContext).getGapMaxlengthBindingOutcomeValue().getGapCharactersCount();
			gapBase.presenter.setMaxLength(longestAnswerLength);
		}
	}
	
	protected int calculateTextBoxWidth(int longestAnswerLength) {
		return longestAnswerLength * gapBase.getFontSize();
	}
}