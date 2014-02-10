package eu.ydp.empiria.player.client.gin.module;

import com.google.gwt.inject.client.AbstractGinModule;

import eu.ydp.empiria.player.client.module.button.download.view.ButtonModuleView;
import eu.ydp.empiria.player.client.module.button.download.view.ButtonModuleViewImpl;

public class ButtonGinModule extends AbstractGinModule {

	@Override
	protected void configure() {
		bind(ButtonModuleView.class).to(ButtonModuleViewImpl.class);
	}

}
