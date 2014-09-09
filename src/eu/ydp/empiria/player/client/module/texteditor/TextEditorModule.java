package eu.ydp.empiria.player.client.module.texteditor;

import com.google.gwt.json.client.JSONArray;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.xml.client.Element;
import com.google.inject.Inject;
import eu.ydp.empiria.player.client.module.*;
import eu.ydp.empiria.player.client.module.texteditor.model.Model;
import eu.ydp.empiria.player.client.module.texteditor.model.ModelEncoder;
import eu.ydp.empiria.player.client.module.texteditor.presenter.TextEditorPresenter;
import eu.ydp.empiria.player.client.module.texteditor.structure.TextEditorBean;
import eu.ydp.gwtutil.client.gin.scopes.module.ModuleScoped;

public class TextEditorModule extends SimpleModuleBase implements IStateful, IUniqueModule, IActivity, ILifecycleModule {

	private final TextEditorPresenter presenter;
	private final ModelEncoder modelEncoder;
	private final TextEditorBean textEditorBean;

	@Inject
	public TextEditorModule(@ModuleScoped TextEditorPresenter presenter, ModelEncoder modelEncoder, TextEditorBean textEditorBean) {
		this.presenter = presenter;
		this.modelEncoder = modelEncoder;
		this.textEditorBean = textEditorBean;
	}

	@Override
	protected void initModule(Element element) {
		presenter.init(getModuleId());
	}

	@Override
	public void lock(boolean lock) {
		if (lock) {
			presenter.lock();
		} else {
			presenter.unlock();
		}
	}

	@Override
	public void reset() {
		Model emptyModel = Model.createEmpty();
		presenter.setModel(emptyModel);
	}

	@Override
	public JSONArray getState() {
		Model model = presenter.getModel();
		return modelEncoder.encodeModel(model);
	}

	@Override
	public void setState(JSONArray newState) {
		Model model = modelEncoder.decodeModel(newState);
		presenter.setModel(model);
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
		return textEditorBean.getIdentifier();
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
