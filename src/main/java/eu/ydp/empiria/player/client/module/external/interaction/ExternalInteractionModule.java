package eu.ydp.empiria.player.client.module.external.interaction;

import com.google.gwt.json.client.JSONArray;
import com.google.inject.Inject;
import eu.ydp.empiria.player.client.module.core.base.AbstractInteractionModule;
import eu.ydp.empiria.player.client.module.external.common.ExternalFolderNameProvider;
import eu.ydp.empiria.player.client.module.external.common.ExternalPaths;
import eu.ydp.empiria.player.client.module.external.interaction.structure.ExternalInteractionModuleBean;
import eu.ydp.empiria.player.client.module.external.interaction.structure.ExternalInteractionModuleStructure;
import eu.ydp.empiria.player.client.resources.EmpiriaPaths;
import eu.ydp.gwtutil.client.gin.scopes.module.ModuleScoped;

public class ExternalInteractionModule
        extends AbstractInteractionModule<ExternalInteractionResponseModel, ExternalInteractionModuleBean>
        implements ExternalFolderNameProvider {

    private final ExternalInteractionModulePresenter presenter;
    private final ExternalInteractionResponseModel externalInteractionResponseModel;
    private final ExternalInteractionModuleStructure externalInteractionModuleStructure;
    private final ExternalPaths externalPaths;
    private final EmpiriaPaths empiriaPaths;

    @Inject
    public ExternalInteractionModule(ExternalInteractionModulePresenter presenter, @ModuleScoped ExternalInteractionResponseModel model,
                                     @ModuleScoped ExternalInteractionModuleStructure structure,
                                     @ModuleScoped ExternalPaths externalPaths, EmpiriaPaths empiriaPaths) {
        this.presenter = presenter;
        this.externalInteractionResponseModel = model;
        this.externalInteractionModuleStructure = structure;
        this.externalPaths = externalPaths;
        this.empiriaPaths = empiriaPaths;
    }

    @Override
    protected ExternalInteractionModulePresenter getPresenter() {
        return presenter;
    }

    @Override
    protected void initalizeModule() {
        externalInteractionResponseModel.setResponseModelChange(this);
        externalPaths.setExternalFolderNameProvider(this);
    }

    @Override
    protected ExternalInteractionResponseModel getResponseModel() {
        return externalInteractionResponseModel;
    }

    @Override
    protected ExternalInteractionModuleStructure getStructure() {
        return externalInteractionModuleStructure;
    }

    @Override
    public JSONArray getState() {
        return presenter.getState();
    }

    @Override
    public void setState(JSONArray stateAndStructure) {
        presenter.setState(stateAndStructure);
    }

    @Override
    public String getExternalFolderName() {
        return externalInteractionModuleStructure.getBean().getSrc();
    }

    @Override
    public String getExternalRelativePath(String file) {
        return empiriaPaths.getMediaFilePath(file);
    }
}
