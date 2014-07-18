package eu.ydp.empiria.player.client.module.texteditor.view;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiTemplate;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.Widget;

public class TextEditorViewImpl extends Composite implements TextEditorView {

	private static TextEditorViewUiBinder uiBinder = GWT.create(TextEditorViewUiBinder.class);

	@UiTemplate("TextEditorView.ui.xml")
	interface TextEditorViewUiBinder extends UiBinder<Widget, TextEditorViewImpl> {

	}

	@UiField
	Panel mainPanel;

	@UiField
	TextArea textEditor;

	public void init() {
		initWidget(uiBinder.createAndBindUi(this));
	}

	@Override
	public Widget asWidget() {
		return mainPanel;
	}

	@Override
	public void lock() {
		textEditor.setEnabled(false);
	}
}