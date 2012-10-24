package eu.ydp.empiria.player.client.module.containers.group;

import eu.ydp.empiria.player.client.module.binding.gapmaxlength.GapMaxlengthBindingManager;
import eu.ydp.empiria.player.client.module.binding.gapwidth.GapWidthBindingManager;

public class ItemBodyModule extends GroupModuleBase<ItemBodyModule> {

	public ItemBodyModule(){
		super();
		panel.setStyleName("qp-item-body");
	}

	@Override
	public ItemBodyModule getNewInstance() {
		return new ItemBodyModule();
	}

	@Override
	protected GapWidthBindingManager createGapWidthBindingManager(){
		return new GapWidthBindingManager(false);
	}
	
	@Override
	protected GapMaxlengthBindingManager createGapMaxlengthBindingManager() {
		return new GapMaxlengthBindingManager(false);
	}
}
