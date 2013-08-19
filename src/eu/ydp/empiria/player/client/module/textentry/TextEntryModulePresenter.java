package eu.ydp.empiria.player.client.module.textentry;

import javax.annotation.PostConstruct;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiTemplate;
import com.google.gwt.user.client.ui.HasWidgets;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;

import eu.ydp.empiria.player.client.module.gap.DropZoneGuardian;
import eu.ydp.empiria.player.client.module.gap.GapBase.PresenterHandler;
import eu.ydp.empiria.player.client.module.gap.GapModulePresenter;

public class TextEntryModulePresenter extends TextEntryGapModulePresenterBase {

	@UiTemplate("TextEntryModule.ui.xml")
	interface TextEntryModuleUiBinder extends UiBinder<Widget, TextEntryModulePresenter> {
	};

	private final TextEntryModuleUiBinder uiBinder = GWT.create(TextEntryModuleUiBinder.class);

	@UiField(provided = true)
	protected Widget textBoxWidget;
	@UiField
	protected Panel moduleWidget;
	@Inject
	private TextBoxChangeHandler textBoxChangeHandler;

	
	@PostConstruct
	public void postConstruct(){
		droppable = dragDropHelper.enableDropForWidget(new TextBox());
		textBoxWidget = droppable.getDroppableWidget();
		textBox = droppable.getOriginalWidget();
		uiBinder.createAndBindUi(this);

		dropZoneGuardian = new DropZoneGuardian(droppable, moduleWidget, styleNames);
	}

	@Override
	public void installViewInContainer(HasWidgets container) {
		container.add(moduleWidget);
		
		moduleWidget.setStyleName( styleNames.QP_TEXTENTRY(), true );
	}
	
	@Override
	public HasWidgets getContainer() {
		return moduleWidget;
	}

	@Override
	public void setMarkMode(String mode) {
		String markStyleName;

		if (GapModulePresenter.CORRECT.equals(mode)) {
			markStyleName = styleNames.QP_TEXT_TEXTENTRY_CORRECT();
		} else if (GapModulePresenter.WRONG.equals(mode)) {
			markStyleName = styleNames.QP_TEXT_TEXTENTRY_WRONG();
		} else {
			markStyleName = styleNames.QP_TEXT_TEXTENTRY_NONE();
		}

		moduleWidget.setStylePrimaryName(markStyleName);
	}

	@Override
	public void removeMarking() {
		moduleWidget.setStylePrimaryName(styleNames.QP_TEXT_TEXTENTRY());
	}

	
	@Override
	public void addPresenterHandler(PresenterHandler handler) {
		textBoxChangeHandler.bind(droppable, handler);
	}
}
