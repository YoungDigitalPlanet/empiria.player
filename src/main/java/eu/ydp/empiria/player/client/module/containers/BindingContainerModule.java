package eu.ydp.empiria.player.client.module.containers;

import eu.ydp.empiria.player.client.module.binding.BindingManager;
import eu.ydp.empiria.player.client.module.binding.BindingProxy;
import eu.ydp.empiria.player.client.module.binding.BindingType;
import eu.ydp.empiria.player.client.module.binding.gapmaxlength.GapMaxlengthBindingManager;
import eu.ydp.empiria.player.client.module.binding.gapwidth.GapWidthBindingManager;

public abstract class BindingContainerModule extends SimpleContainerModuleBase implements BindingProxy {

    private GapWidthBindingManager gapWidthBindingManager;
    private GapMaxlengthBindingManager gapMaxlengthBindingManager;

    @Override
    public BindingManager getBindingManager(BindingType bindingType) {
        if (bindingType == BindingType.GAP_WIDTHS) {
            if (gapWidthBindingManager == null)
                gapWidthBindingManager = createGapWidthBindingManager();
            return gapWidthBindingManager;
        }

        if (bindingType == BindingType.GAP_MAXLENGHTS) {
            if (gapMaxlengthBindingManager == null)
                gapMaxlengthBindingManager = createGapMaxlengthBindingManager();
            return gapMaxlengthBindingManager;
        }

        return null;
    }

    protected GapWidthBindingManager createGapWidthBindingManager() {
        return new GapWidthBindingManager(true);
    }

    protected GapMaxlengthBindingManager createGapMaxlengthBindingManager() {
        return new GapMaxlengthBindingManager(true);
    }
}
