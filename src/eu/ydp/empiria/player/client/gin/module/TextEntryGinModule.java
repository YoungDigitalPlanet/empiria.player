package eu.ydp.empiria.player.client.gin.module;

import com.google.gwt.inject.client.AbstractGinModule;
import com.google.gwt.inject.client.assistedinject.GinFactoryModuleBuilder;

import eu.ydp.empiria.player.client.gin.factory.TextEntryModuleFactory;

public class TextEntryGinModule extends AbstractGinModule {
	@Override
	protected void configure() {
		install(new GinFactoryModuleBuilder().build(TextEntryModuleFactory.class));
	}
}
