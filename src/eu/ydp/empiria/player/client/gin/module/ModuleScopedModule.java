package eu.ydp.empiria.player.client.gin.module;

import com.google.gwt.inject.client.AbstractGinModule;
import com.google.inject.Singleton;

import eu.ydp.empiria.player.client.gin.scopes.module.ModuleScopeStack;

public class ModuleScopedModule extends AbstractGinModule{

	@Override
	protected void configure() {
		bind(ModuleScopeStack.class).in(Singleton.class);
	}

}
