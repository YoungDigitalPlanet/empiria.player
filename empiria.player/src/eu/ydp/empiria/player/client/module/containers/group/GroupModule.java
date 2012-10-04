package eu.ydp.empiria.player.client.module.containers.group;


public class GroupModule extends GroupModuleBase<GroupModule> {

	public GroupModule(){
		super();
		panel.setStyleName("qp-group");
	}

	@Override
	public GroupModule getNewInstance() {
		return new GroupModule();
	}

}
