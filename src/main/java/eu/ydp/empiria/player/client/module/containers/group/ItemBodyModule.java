package eu.ydp.empiria.player.client.module.containers.group;

import eu.ydp.empiria.player.client.module.binding.gapmaxlength.GapMaxlengthBindingManager;
import eu.ydp.empiria.player.client.module.binding.gapwidth.GapWidthBindingManager;

public class ItemBodyModule extends GroupModuleBase {

    public ItemBodyModule() {
        setContainerStyleName("qp-item-body");
    }

    @Override
    protected GapWidthBindingManager createGapWidthBindingManager() {
        return new GapWidthBindingManager(false);
    }

    @Override
    protected GapMaxlengthBindingManager createGapMaxlengthBindingManager() {
        return new GapMaxlengthBindingManager(false);
    }
}
