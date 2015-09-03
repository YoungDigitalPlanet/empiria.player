package eu.ydp.empiria.player.client.module.texteditor;

import com.google.gwt.json.client.JSONArray;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.xml.client.Element;
import com.google.inject.Inject;
import eu.ydp.empiria.player.client.controller.workmode.WorkModePreviewClient;
import eu.ydp.empiria.player.client.module.core.flow.Activity;
import eu.ydp.empiria.player.client.module.core.flow.LifecycleModule;
import eu.ydp.empiria.player.client.module.core.base.SimpleModuleBase;
import eu.ydp.empiria.player.client.module.core.flow.StatefulModule;
import eu.ydp.empiria.player.client.module.texteditor.model.TextEditorModel;
import eu.ydp.empiria.player.client.module.texteditor.model.TextEditorModelEncoder;
import eu.ydp.empiria.player.client.module.texteditor.presenter.TextEditorPresenter;
import eu.ydp.empiria.player.client.module.texteditor.structure.TextEditorBean;
import eu.ydp.gwtutil.client.gin.scopes.module.ModuleScoped;

public class TextEditorModule extends SimpleModuleBase implements WorkModePreviewClient, Activity,
        LifecycleModule, StatefulModule {

    private final TextEditorPresenter presenter;
    private final TextEditorModelEncoder textEditorModelEncoder;
    private final TextEditorBean textEditorBean;

    @Inject
    public TextEditorModule(@ModuleScoped TextEditorPresenter presenter, TextEditorModelEncoder textEditorModelEncoder, TextEditorBean textEditorBean) {
        this.presenter = presenter;
        this.textEditorModelEncoder = textEditorModelEncoder;
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
        TextEditorModel emptyTextEditorModel = TextEditorModel.createEmpty();
        presenter.setTextEditorModel(emptyTextEditorModel);
    }

    @Override
    public JSONArray getState() {
        TextEditorModel textEditorModel = presenter.getTextEditorModel();
        return textEditorModelEncoder.encodeModel(textEditorModel);
    }

    @Override
    public void setState(JSONArray newState) {
        TextEditorModel textEditorModel = textEditorModelEncoder.decodeModel(newState);
        presenter.setTextEditorModel(textEditorModel);
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
    public void enablePreviewMode() {
        presenter.enablePreviewMode();
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
