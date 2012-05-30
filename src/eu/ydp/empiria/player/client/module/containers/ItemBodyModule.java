package eu.ydp.empiria.player.client.module.containers;


public class ItemBodyModule extends SimpleContainerModuleBase<ItemBodyModule> {

	public ItemBodyModule(){
		super();
		panel.setStyleName("qp-item-body");
	}

	@Override
	public ItemBodyModule getNewInstance() {
		return new ItemBodyModule();
	}
}
