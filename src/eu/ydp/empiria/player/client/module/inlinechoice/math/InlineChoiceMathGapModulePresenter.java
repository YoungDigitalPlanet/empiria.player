package eu.ydp.empiria.player.client.module.inlinechoice.math;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiTemplate;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HasWidgets;
import com.google.gwt.user.client.ui.UIObject;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import com.google.inject.Provider;
import eu.ydp.empiria.player.client.controller.multiview.touch.SwypeBlocker;
import eu.ydp.empiria.player.client.module.gap.GapModulePesenterBase;
import eu.ydp.empiria.player.client.module.gap.GapModulePresenter;
import eu.ydp.gwtutil.client.components.exlistbox.ExListBox;
import eu.ydp.gwtutil.client.components.exlistbox.IsExListBox;

public class InlineChoiceMathGapModulePresenter extends GapModulePesenterBase {

	@UiTemplate("InlineChoiceMathGap.ui.xml")
	interface InlineChoiceGapModuleUiBinder extends UiBinder<Widget, InlineChoiceMathGapModulePresenter> {
	}

	;

	private final InlineChoiceGapModuleUiBinder uiBinder = GWT.create(InlineChoiceGapModuleUiBinder.class);

	@UiField
	protected FlowPanel mainPanel;
	@UiField(provided = true)
	protected ExListBox listBox;

	@Inject
	public InlineChoiceMathGapModulePresenter(SwypeBlocker swypeBlocker, Provider<ExListBox> exListBoxProvider) {
		listBox = exListBoxProvider.get();
		uiBinder.createAndBindUi(this);
		swypeBlocker.addBlockOnOpenCloseHandler(listBox);
	}

	@Override
	public HasWidgets getContainer() {
		return mainPanel;
	}

	@Override
	public void installViewInContainer(HasWidgets container) {
		container.add(mainPanel);
	}

	@Override
	public void setViewEnabled(boolean enabled) {
		listBox.setEnabled(enabled);
	}

	@Override
	public void setMarkMode(String mode) {
		mainPanel.addStyleDependentName(mode);
	}

	@Override
	public void removeMarking() {
		mainPanel.removeStyleDependentName(GapModulePresenter.NONE);
		mainPanel.removeStyleDependentName(GapModulePresenter.CORRECT);
		mainPanel.removeStyleDependentName(GapModulePresenter.WRONG);
	}

	public IsExListBox getListBox() {
		return listBox;
	}

	@Override
	public UIObject getComponent() {
		return listBox;
	}

	@Override
	public void setText(String text) {
	}

	@Override
	// czy inlinechoice moze miec ustawione maxlength w stylach? jeśli nie to
	// można to wywalić -> GapBase.setMaxlengthBinding()
	public void setMaxLength(int length) {
	}
}
