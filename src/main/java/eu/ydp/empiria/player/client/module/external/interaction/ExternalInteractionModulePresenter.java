package eu.ydp.empiria.player.client.module.external.interaction;

import com.google.common.base.Optional;
import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.json.client.JSONArray;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import eu.ydp.empiria.player.client.module.ActivityPresenter;
import eu.ydp.empiria.player.client.module.ModuleSocket;
import eu.ydp.empiria.player.client.module.core.answer.MarkAnswersMode;
import eu.ydp.empiria.player.client.module.core.answer.MarkAnswersType;
import eu.ydp.empiria.player.client.module.core.answer.ShowAnswersType;
import eu.ydp.empiria.player.client.module.external.common.ExternalFrameLoadHandler;
import eu.ydp.empiria.player.client.module.external.common.ExternalPaths;
import eu.ydp.empiria.player.client.module.external.common.state.ExternalStateEncoder;
import eu.ydp.empiria.player.client.module.external.common.state.ExternalStateSaver;
import eu.ydp.empiria.player.client.module.external.common.view.ExternalView;
import eu.ydp.empiria.player.client.module.external.interaction.api.ExternalApiProvider;
import eu.ydp.empiria.player.client.module.external.interaction.api.ExternalInteractionApi;
import eu.ydp.empiria.player.client.module.external.interaction.api.ExternalInteractionEmpiriaApi;
import eu.ydp.empiria.player.client.module.external.interaction.structure.ExternalInteractionModuleBean;
import eu.ydp.gwtutil.client.gin.scopes.module.ModuleScoped;

public class ExternalInteractionModulePresenter
        implements ActivityPresenter<ExternalInteractionResponseModel, ExternalInteractionModuleBean>, ExternalFrameLoadHandler<ExternalInteractionApi> {

    private final ExternalView<ExternalInteractionApi, ExternalInteractionEmpiriaApi> view;
    private final ExternalInteractionEmpiriaApi empiriaApi;
    private final ExternalStateSaver stateSaver;
    private final ExternalStateEncoder stateEncoder;
    private final ExternalPaths externalPaths;
    private final ExternalApiProvider externalApi;

    @Inject
    public ExternalInteractionModulePresenter(ExternalView<ExternalInteractionApi, ExternalInteractionEmpiriaApi> view,
                                              @ModuleScoped ExternalPaths externalPaths,
                                              @ModuleScoped ExternalInteractionEmpiriaApi empiriaApi, @ModuleScoped ExternalStateSaver stateSaver, ExternalStateEncoder stateEncoder, @ModuleScoped ExternalApiProvider externalApi) {
        this.externalPaths = externalPaths;
        this.view = view;
        this.empiriaApi = empiriaApi;
        this.stateSaver = stateSaver;
        this.stateEncoder = stateEncoder;
        this.externalApi = externalApi;
    }

    @Override
    public void bindView() {
        String externalModuleFilePath = externalPaths.getExternalEntryPointPath();
        view.init(empiriaApi, this, externalModuleFilePath);
    }

    @Override
    public void reset() {
        externalApi.getExternalApi().reset();
    }

    @Override
    public void setModel(ExternalInteractionResponseModel model) {
    }

    @Override
    public void setModuleSocket(ModuleSocket socket) {
    }

    @Override
    public void setBean(ExternalInteractionModuleBean externalInteractionModuleBean) {
    }

    @Override
    public void setLocked(boolean locked) {
        if (locked) {
            lock();
        } else {
            unlock();
        }
    }

    @Override
    public void markAnswers(MarkAnswersType type, MarkAnswersMode mode) {
        switch (mode) {
            case MARK:
                markAnswers(type);
                break;
            case UNMARK:
                unmarkAnswers(type);
                break;
        }
    }

    @Override
    public void showAnswers(ShowAnswersType mode) {
        switch (mode) {
            case CORRECT:
                externalApi.getExternalApi().showCorrectAnswers();
                break;
            case USER:
                externalApi.getExternalApi().hideCorrectAnswers();
                break;
        }
    }

    @Override
    public Widget asWidget() {
        return view.asWidget();
    }

    @Override
    public void onExternalModuleLoaded(ExternalInteractionApi externalObject) {
        this.externalApi.setExternalApi(externalObject);

        Optional<JavaScriptObject> externalState = stateSaver.getExternalState();
        if (externalState.isPresent()) {
            externalObject.setStateOnExternal(externalState.get());
        }
    }

    public void setState(JSONArray stateAndStructure) {
        JavaScriptObject state = stateEncoder.decodeState(stateAndStructure);
        stateSaver.setExternalState(state);
    }

    private void lock() {
        externalApi.getExternalApi().lock();
    }

    private void unlock() {
        externalApi.getExternalApi().unlock();
    }

    private void markAnswers(MarkAnswersType type) {
        switch (type) {
            case CORRECT:
                externalApi.getExternalApi().markCorrectAnswers();
                break;
            case WRONG:
                externalApi.getExternalApi().markWrongAnswers();
                break;
        }
    }

    private void unmarkAnswers(MarkAnswersType type) {
        switch (type) {
            case CORRECT:
                externalApi.getExternalApi().unmarkCorrectAnswers();
                break;
            case WRONG:
                externalApi.getExternalApi().unmarkWrongAnswers();
                break;
        }
    }
}
