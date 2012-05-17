package eu.ydp.empiria.player.client.module.containers.group;

public class ItemBodyModule extends GroupModuleBase<ItemBodyModule> {

	public ItemBodyModule(){
		super();
		panel.setStyleName("qp-item-body");
	}

	@Override
	public ItemBodyModule getNewInstance() {
		return new ItemBodyModule();
	}
}
