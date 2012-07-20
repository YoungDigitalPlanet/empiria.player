package eu.ydp.empiria.player.client.module.containers;

import eu.ydp.empiria.player.client.module.binding.BindingProxy;
import eu.ydp.empiria.player.client.module.binding.BindingType;
import eu.ydp.empiria.player.client.module.binding.gapwidth.GapWidthBindingManager;

public abstract class BindingContainerModule<T> extends SimpleContainerModuleBase<T> implements BindingProxy {

	private GapWidthBindingManager gapWidthBindingManager;

	@Override
	public GapWidthBindingManager getBindingManager(BindingType bindingType) {
		if (bindingType == BindingType.GAP_WIDTHS){
			if (gapWidthBindingManager == null)
				gapWidthBindingManager = createGapWidthBindingManager();
			return gapWidthBindingManager;
		}
		return null;
	}
	
	protected GapWidthBindingManager createGapWidthBindingManager(){
		return new GapWidthBindingManager(true);
	}
}
