package eu.ydp.empiria.player.client.gin.module;

import com.google.gwt.inject.client.AbstractGinModule;
import com.google.inject.Singleton;

import eu.ydp.gwtcreatejs.client.scripts.SynchronousScriptsLoader;
import eu.ydp.gwtutil.client.inject.ScriptInjectorWrapper;
import eu.ydp.gwtutil.client.util.paths.UrlConverter;

public class ScriptInjectorModule extends AbstractGinModule {

	@Override
	protected void configure() {
		bind(SynchronousScriptsLoader.class).in(Singleton.class);
		bind(UrlConverter.class).in(Singleton.class);
		bind(ScriptInjectorWrapper.class).in(Singleton.class);
	}
}
