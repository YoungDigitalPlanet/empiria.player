package eu.ydp.empiria.player.client.module.texteditor;

import com.google.gwt.json.client.JSONArray;
import com.google.gwt.json.client.JSONString;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.xml.client.Element;
import com.google.inject.Inject;
import eu.ydp.empiria.player.client.module.*;
import eu.ydp.empiria.player.client.module.texteditor.presenter.TextEditorPresenter;
import eu.ydp.gwtutil.client.gin.scopes.module.ModuleScoped;

public class TextEditorModule extends SimpleModuleBase implements IStateful, IUniqueModule, IActivity, ILifecycleModule {

	private final TextEditorPresenter presenter;

	@Inject
	public TextEditorModule(@ModuleScoped TextEditorPresenter presenter) {
		this.presenter = presenter;
	}

	@Override
	protected void initModule(Element element) {
		presenter.init(getModuleId());
	}

	@Override
	public void lock(boolean lo) {
		presenter.lock();
	}

	@Override
	public void reset() {
		presenter.setContent("");
	}

	@Override
	public JSONArray getState() {
		JSONArray state = new JSONArray();
		JSONString value = new JSONString(presenter.getContent());
		state.set(0, value);
		return state;
	}

	@Override
	public void setState(JSONArray newState) {
		String value = newState.get(0).toString();
		presenter.setContent(value);
	}

	@Override
	public void markAnswers(boolean mark) {

	}

	@Override
	public void showCorrectAnswers(boolean show) {

	}

	@Override
	public String getIdentifier() {
		return "textEditorId";
	}

	@Override
	public Widget getView() {
		return presenter.getView();
	}

	@Override
	public void onBodyLoad() {
		presenter.convertEditor();
	}

	@Override
	public void onBodyUnload() {
	}

	@Override
	public void onSetUp() {
	}

	@Override
	public void onStart() {
	}

	@Override
	public void onClose() {
	}
}
