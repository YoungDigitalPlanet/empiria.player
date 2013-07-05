package eu.ydp.empiria.player.client.module.textentry;

import javax.annotation.PostConstruct;

import com.google.common.base.Optional;
import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.event.dom.client.DropEvent;
import com.google.gwt.event.dom.client.DropHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiTemplate;
import com.google.gwt.user.client.ui.HasWidgets;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;

import eu.ydp.empiria.player.client.module.draggap.view.DragDataObjectFromEventExtractor;
import eu.ydp.empiria.player.client.module.expression.ExpressionReplacer;
import eu.ydp.empiria.player.client.module.expression.TextBoxExpressionReplacer;
import eu.ydp.empiria.player.client.module.gap.DropZoneGuardian;
import eu.ydp.empiria.player.client.module.gap.GapBase.PresenterHandler;
import eu.ydp.empiria.player.client.module.gap.GapDropHandler;
import eu.ydp.empiria.player.client.module.gap.GapModulePresenter;
import eu.ydp.empiria.player.client.resources.StyleNameConstants;
import eu.ydp.empiria.player.client.util.dom.drag.DragDataObject;
import eu.ydp.empiria.player.client.util.dom.drag.DragDropHelper;
import eu.ydp.empiria.player.client.util.dom.drag.DroppableObject;
import eu.ydp.gwtutil.client.components.exlistbox.IsExListBox;

public class TextEntryModulePresenter implements GapModulePresenter {

	@UiTemplate("TextEntryModule.ui.xml")
	interface TextEntryModuleUiBinder extends UiBinder<Widget, TextEntryModulePresenter> {
	};

	private final TextEntryModuleUiBinder uiBinder = GWT.create(TextEntryModuleUiBinder.class);

	@UiField(provided = true)
	protected Widget textBoxWidget;

	protected TextBox textBox;

	@UiField
	protected Panel moduleWidget;

	@Inject
	private StyleNameConstants styleNames;

	@Inject
	private TextBoxChangeHandler textBoxChangeHandler;

	@Inject
	private TextBoxExpressionReplacer expressionReplacer;

	@Inject
	private DragDataObjectFromEventExtractor dataObjectFromEventExtractor;

	private DroppableObject<TextBox> droppable;
	private DropZoneGuardian dropZoneGuardian;

	@Inject
	private DragDropHelper dragDropHelper;

	@PostConstruct
	public void postConstruct(){
		droppable = dragDropHelper.enableDropForWidget(new TextBox());
		textBoxWidget = droppable.getDroppableWidget();
		textBox = droppable.getOriginalWidget();
		uiBinder.createAndBindUi(this);

		dropZoneGuardian = new DropZoneGuardian(droppable, moduleWidget, styleNames);
	}



	@Override
	public void addDomHandlerOnObjectDrop(final GapDropHandler dragGapDropHandler) {
		droppable.addDropHandler(new DropHandler() {
			@Override
			public void onDrop(DropEvent event) {
				Optional<DragDataObject> objectFromEvent = dataObjectFromEventExtractor.extractDroppedObjectFromEvent(event);
				if(objectFromEvent.isPresent()){
					dragGapDropHandler.onDrop(objectFromEvent.get());
				}
			}
		});
	}

	@Override
	public void setWidth(double value, Unit unit) {
		textBox.setWidth(value + unit.getType());
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
		textBox.setMaxLength(length);

	}

	@Override
	public void setFontSize(double value, Unit unit) {
		textBox.getElement().getStyle().setFontSize(value, unit);
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

		moduleWidget.setStyleName( styleNames.QP_TEXTENTRY(), true );
	}

	@Override
	public void setViewEnabled(boolean enabled) {
		textBox.setEnabled(enabled);
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

	@Override
	public void removeFocusFromTextField() {
		textBox.getElement().blur();
	}

	@Override
	public IsExListBox getListBox() {
		return null;
	}

	@Override
	public void makeExpressionReplacements(ExpressionReplacer replacer) {
		expressionReplacer.makeReplacements(textBox, replacer);
	}

	public void lockDragZone() {
		dropZoneGuardian.lockDropZone();
	}

	public void unlockDragZone() {
		dropZoneGuardian.unlockDropZone();
	}
}
