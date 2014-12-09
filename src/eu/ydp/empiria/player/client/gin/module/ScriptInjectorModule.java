package eu.ydp.empiria.player.client.gin.module;

import com.google.gwt.inject.client.AbstractGinModule;
import com.google.inject.Singleton;
import eu.ydp.empiria.player.client.scripts.*;

public class ScriptInjectorModule extends AbstractGinModule {

	@Override
	protected void configure() {
		bind(ScriptInjectorDescriptor.class).in(Singleton.class);
		bind(ScriptsLoader.class).in(Singleton.class);
	}
}
