package eu.ydp.empiria.player.client.module.external.common.state;

import com.google.common.base.Optional;
import com.google.gwt.core.client.JavaScriptObject;
import eu.ydp.empiria.player.client.module.external.common.api.ExternalApi;
import eu.ydp.gwtutil.client.gin.scopes.module.ModuleScoped;

import javax.inject.Inject;

public class ExternalStateSetter {

    private final ExternalStateSaver stateSaver;
    private final ExternalFrameObjectFixer frameObjectFixer;

    @Inject
    public ExternalStateSetter(@ModuleScoped ExternalStateSaver stateSaver, ExternalFrameObjectFixer frameObjectFixer) {
        this.stateSaver = stateSaver;
        this.frameObjectFixer = frameObjectFixer;
    }

    public void setSavedStateInExternal(ExternalApi externalObject) {

        Optional<JavaScriptObject> externalState = stateSaver.getExternalState();
        if (externalState.isPresent()) {
            JavaScriptObject fixedState = frameObjectFixer.fix(externalState.get());
            externalObject.setStateOnExternal(fixedState);
        }
    }
}
