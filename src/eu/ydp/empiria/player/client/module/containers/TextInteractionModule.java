package eu.ydp.empiria.player.client.module.containers;


public class TextInteractionModule extends ContainerModuleBase<TextInteractionModule> {

	public TextInteractionModule(){
		super();
		panel.setStyleName("qp-textinteraction");
	}

	@Override
	public TextInteractionModule getNewInstance() {
		return new TextInteractionModule();
	}

}
