package eu.ydp.empiria.player.client.module.containers;

public class DivModule extends SimpleContainerModuleBase<DivModule> {

	public DivModule() {
		setContainerStyleName("qp-div");
	}

	@Override
	public DivModule getNewInstance() {
		return new DivModule();
	}

}
