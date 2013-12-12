package eu.ydp.empiria.player.client.module.textentry.math;

import javax.annotation.PostConstruct;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.BlurEvent;
import com.google.gwt.event.dom.client.BlurHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiTemplate;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HasWidgets;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;

import eu.ydp.empiria.player.client.module.gap.DropZoneGuardian;
import eu.ydp.empiria.player.client.module.gap.GapBase.PresenterHandler;
import eu.ydp.empiria.player.client.module.gap.GapModulePresenter;
import eu.ydp.empiria.player.client.module.textentry.TextEntryGapModulePresenterBase;

public class TextEntryMathGapModulePresenter extends TextEntryGapModulePresenterBase implements BlurHandler {

	@UiTemplate("TextEntryMathGap.ui.xml")
	interface TextEntryGapModuleUiBinder extends UiBinder<Widget, TextEntryMathGapModulePresenter> {
	};

	private final TextEntryGapModuleUiBinder uiBinder = GWT.create(TextEntryGapModuleUiBinder.class);

	@UiField
	protected FlowPanel mainPanel;
	@UiField
	protected FlowPanel markPanel;
	@UiField(provided = true)
	protected Widget textBoxWidget;
	private BlurHandler changeHandler;
	
	
	@PostConstruct
	public void postConstruct() {
		droppable = dragDropHelper.enableDropForWidget(new TextBox());
		textBoxWidget = droppable.getDroppableWidget();
		textBox = droppable.getOriginalWidget();
		uiBinder.createAndBindUi(this);

		dropZoneGuardian = new DropZoneGuardian(droppable, mainPanel, styleNames);
		
		textBox.addBlurHandler(this);
	}
	
	@Override
	public void installViewInContainer(HasWidgets container) {
		container.add(mainPanel);
	}
	
	@Override
	public HasWidgets getContainer() {
		return mainPanel;
	}
	
	@Override
	public void setMarkMode(String mode) {
		markPanel.addStyleDependentName(mode);
	}
	
	@Override
	public void removeMarking() {
		markPanel.removeStyleDependentName(GapModulePresenter.NONE);
		markPanel.removeStyleDependentName(GapModulePresenter.CORRECT);
		markPanel.removeStyleDependentName(GapModulePresenter.WRONG);
	}
	
	@Override
	public void addPresenterHandler(PresenterHandler handler) {
		changeHandler = handler;
	}
	
	@Override
	public void onBlur(BlurEvent event) {
		if (changeHandler != null) {
			changeHandler.onBlur(event);
		}
	}
}
