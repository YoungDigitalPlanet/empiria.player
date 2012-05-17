package eu.ydp.empiria.player.client.module.containers;


public class ItemBodyModule extends ContainerModuleBase<ItemBodyModule> {

	public ItemBodyModule(){
		super();
		panel.setStyleName("qp-item-body");
	}

	@Override
	public ItemBodyModule getNewInstance() {
		return new ItemBodyModule();
	}
}
