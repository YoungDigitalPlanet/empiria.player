package eu.ydp.empiria.player.client.module.textentry;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.event.dom.client.BlurEvent;
import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiTemplate;
import com.google.gwt.user.client.ui.HasWidgets;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import com.google.inject.assistedinject.Assisted;

import eu.ydp.empiria.player.client.module.IModule;
import eu.ydp.empiria.player.client.module.gap.GapBase.PresenterHandler;
import eu.ydp.empiria.player.client.module.gap.GapModulePresenter;
import eu.ydp.empiria.player.client.resources.StyleNameConstants;
import eu.ydp.empiria.player.client.util.dom.drag.DragDropHelper;
import eu.ydp.empiria.player.client.util.dom.drag.DroppableObject;
import eu.ydp.gwtutil.client.components.exlistbox.ExListBox;

public class TextEntryModulePresenter implements GapModulePresenter,ChangeHandler {

	@UiTemplate("TextEntryModule.ui.xml")
	interface TextEntryModuleUiBinder extends UiBinder<Widget, TextEntryModulePresenter>{};

	private final TextEntryModuleUiBinder uiBinder = GWT.create(TextEntryModuleUiBinder.class);

	@UiField(provided=true)
	protected Widget textBoxWidget;

	protected TextBox textBox;

	@UiField
	protected Panel moduleWidget;

	private PresenterHandler changeHandler;

	@Inject
	private StyleNameConstants styleNames;

	@Inject
	public TextEntryModulePresenter(@Assisted("imodule") IModule parentModule, DragDropHelper dragDropHelper){
		DroppableObject<TextBox> droppable = dragDropHelper.enableDropForWidget(new TextBox(),parentModule);
		textBoxWidget= droppable.getDroppableWidget();
		textBox = droppable.getOriginalWidget();
		uiBinder.createAndBindUi(this);
		textBox.addChangeHandler(this);

	}

	@Override
	public void onChange(ChangeEvent event) {
		if(changeHandler != null){
			changeHandler.onChange(event);
		}
	}

	protected void handleBlur(BlurEvent event){
		if(changeHandler != null){
			changeHandler.onBlur(event);
		}
	}

	@Override
	public void setWidth(double value, Unit unit) {
		if (textBox != null) {
			textBox.setWidth(value + unit.getType());
		}
	}

	@Override
	public int getOffsetWidth() {
		return textBox.getOffsetWidth();
	}

	@Override
	public void setHeight(double value, Unit unit) {
		textBox.setHeight(value + unit.getType());
	}

	@Override
	public int getOffsetHeight() {
		return textBox.getOffsetHeight();
	}

	@Override
	public void setMaxLength(int length) {
		if (textBox != null) {
			textBox.setMaxLength(length);
		}
	}

	@Override
	public void setFontSize(double value, Unit unit) {
		if (textBox != null) {
			textBox.getElement().getStyle().setFontSize(value, unit);
		}
	}

	@Override
	public int getFontSize() {
		return Integer.parseInt(textBox.getElement().getStyle().getFontSize().replace("px", ""));
	}

	@Override
	public void setText(String text) {
		textBox.setValue(text, true);
	}

	@Override
	public String getText() {
		return textBox.getText();
	}

	@Override
	public HasWidgets getContainer() {
		return moduleWidget;
	}

	@Override
	public void installViewInContainer(HasWidgets container) {
		container.add(moduleWidget);
	}

	@Override
	public void setViewEnabled(boolean enabled) {
		textBox.setEnabled(enabled);
	}

	@Override
	public void setMarkMode(String mode) {
		String markStyleName;

		if(GapModulePresenter.CORRECT.equals(mode)) {
			markStyleName = styleNames.QP_TEXT_TEXTENTRY_CORRECT();
		} else if(GapModulePresenter.WRONG.equals(mode)){
			markStyleName = styleNames.QP_TEXT_TEXTENTRY_WRONG();
		}else{
			markStyleName = styleNames.QP_TEXT_TEXTENTRY_NONE();
		}

		moduleWidget.setStyleName(markStyleName);
	}

	@Override
	public void removeMarking() {
		moduleWidget.setStyleName(styleNames.QP_TEXT_TEXTENTRY());
	}

	@Override
	public void addPresenterHandler(PresenterHandler handler) {
		changeHandler = handler;
	}

	@Override
	public void removeFocusFromTextField() {
		textBox.getElement().blur();
	}

	@Override
	public ExListBox getListBox() {
		return null;
	}
}
