package eu.ydp.empiria.player.client.module.texteditor.view;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiTemplate;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import eu.ydp.empiria.player.client.resources.StyleNameConstants;

public class TextEditorViewImpl extends Composite implements TextEditorView {

	private static TextEditorViewUiBinder uiBinder = GWT.create(TextEditorViewUiBinder.class);

	@UiTemplate("TextEditorView.ui.xml")
	interface TextEditorViewUiBinder extends UiBinder<Widget, TextEditorViewImpl> {
	}

	@UiField
	Panel mainPanel;

	@UiField
	TextArea textEditor;

	@Inject
	private StyleNameConstants styleNameConstants;

	@Override
	public void init() {
		initWidget(uiBinder.createAndBindUi(this));
	}

	@Override
	public void lock() {
		mainPanel.addStyleName(styleNameConstants.QP_TEXT_EDITOR_LOCKED());
	}

	@Override
	public void unlock() {
		mainPanel.removeStyleName(styleNameConstants.QP_TEXT_EDITOR_LOCKED());
	}

	@Override
	public void enablePreviewMode() {
		mainPanel.addStyleName(styleNameConstants.QP_MODULE_MODE_PREVIEW());
	}
}