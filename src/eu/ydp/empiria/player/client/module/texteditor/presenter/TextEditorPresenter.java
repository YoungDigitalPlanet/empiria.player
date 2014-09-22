package eu.ydp.empiria.player.client.module.texteditor.presenter;

import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;

import eu.ydp.empiria.player.client.module.texteditor.model.TextEditorModel;
import eu.ydp.empiria.player.client.module.texteditor.view.TextEditorView;
import eu.ydp.empiria.player.client.module.texteditor.wrapper.TextEditorJSWrapper;
import eu.ydp.gwtutil.client.gin.scopes.module.ModuleScoped;

public class TextEditorPresenter {

	private final TextEditorView view;
	private final TextEditorJSWrapper textEditorJSWrapper;
	private String moduleId;

	@Inject
	public TextEditorPresenter(@ModuleScoped TextEditorView view, TextEditorJSWrapper textEditorJSWrapper) {
		this.view = view;
		this.textEditorJSWrapper = textEditorJSWrapper;
	}

	public void init(String moduleId) {
		this.moduleId = moduleId;
		view.init();
	}

	public void convertEditor() {
		textEditorJSWrapper.convert(moduleId);
	}

	public void setTextEditorModel(TextEditorModel textEditorModel) {
		setContent(textEditorModel.getContent());
	}

	public TextEditorModel getTextEditorModel() {
		return new TextEditorModel(getContent());
	}

	private void setContent(String text) {
		textEditorJSWrapper.setContent(moduleId, text);
	}

	private String getContent() {
		return textEditorJSWrapper.getContent(moduleId);
	}

	public void lock() {
		textEditorJSWrapper.lock(moduleId);
		view.lock();
	}

	public void unlock() {
		textEditorJSWrapper.unlock(moduleId);
		view.unlock();
	}

	public void enablePreviewMode() {
		textEditorJSWrapper.lock(moduleId);
		view.enablePreviewMode();
	}

	public void enableTestSubmittedMode() {
		textEditorJSWrapper.lock(moduleId);
		view.enableTestSubmittedMode();
	}

	public void disableTestSubmittedMode() {
		textEditorJSWrapper.unlock(moduleId);
		view.disableTestSubmittedMode();
	}

	public Widget getView() {
		return view.asWidget();
	}
}
