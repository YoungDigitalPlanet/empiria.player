package eu.ydp.empiria.player.client.module.tutor.actions;

import com.google.common.collect.ImmutableSet;
import com.google.inject.Inject;
import eu.ydp.gwtutil.client.gin.scopes.module.ModuleScoped;

import java.util.Set;

public class OutcomeDrivenActionTypeProvider {

    @Inject
    @ModuleScoped
    private OnPageAllOkAction pageAllOk;
    @Inject
    @ModuleScoped
    private OnOkAction onOk;
    @Inject
    @ModuleScoped
    private OnWrongAction onWrong;

    public Set<OutcomeDrivenAction> getActions() {
        return ImmutableSet.of(pageAllOk, onOk, onWrong);
    }
}
