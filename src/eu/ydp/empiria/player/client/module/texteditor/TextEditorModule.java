package eu.ydp.empiria.player.client.module.texteditor;

import com.google.gwt.json.client.JSONArray;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.xml.client.Element;
import com.google.inject.Inject;
import eu.ydp.empiria.player.client.module.*;
import eu.ydp.empiria.player.client.module.texteditor.presenter.TextEditorPresenter;
import eu.ydp.gwtutil.client.gin.scopes.module.ModuleScoped;

public class TextEditorModule extends SimpleModuleBase implements IStateful, IUniqueModule, IActivity, ILifecycleModule {

	private final TextEditorPresenter presenter;
	private final StateEncoder stateEncoder;

	@Inject
	public TextEditorModule(@ModuleScoped TextEditorPresenter presenter, StateEncoder stateEncoder) {
		this.presenter = presenter;
		this.stateEncoder = stateEncoder;
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
	}

	@Override
	public JSONArray getState() {
		String content = presenter.getContent();
		return stateEncoder.encodeState(content);
	}

	@Override
	public void setState(JSONArray newState) {
		String state = stateEncoder.decodeState(newState);
		presenter.setContent(state);
	}

	@Override
	public void markAnswers(boolean mark) {
		lock(mark);
	}

	@Override
	public void showCorrectAnswers(boolean show) {
		lock(show);
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
		presenter.convertEditor();
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
